import json
import os
from typing import List, Dict, Any
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import httpx
import torch
import torchvision.transforms as transforms
import torchvision.models as models
from PIL import Image
import io

app = FastAPI(title="AI Cafe Classification Service")

# Paths configuration
#MODEL_2CLASS_PATH = "../model_trained/2_class_model/best_resnet50_finetuned_binary.pth"
MODEL_2CLASS_PATH = "../model_trained/2_class_model/best_resnet50_finetuned_binary.pth"
MODEL_5CLASS_PATH = "../model_trained/5_class_model/best_effnetb0_finetuned_5class.pth"
LABEL_5CLASS_PATH = "../model_trained/5_class_model/index_to_label.json"

# Load labels
def load_labels(path: str) -> Dict[int, str]:
    try:
        with open(path, 'r', encoding='utf-8') as f:
            data = json.load(f)
            return {int(k): v for k, v in data.items()}
    except Exception as e:
        print(f"Warning: Could not load labels from {path}: {e}")
        # Default labels based on user's spec
        return {0: "pet_cafe", 1: "study_cafe", 2: "garden_cafe", 3: "aesthetic_cafe", 4: "food_cafe"}

LABELS_5CLASS = load_labels(LABEL_5CLASS_PATH)

device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
print(f"Using device: {device}")

# --- Initialize Models ---
# Model 1: ResNet50 (2 classes: 0=other, 1=cafe)
model_2class = None
try:
    if os.path.exists(MODEL_2CLASS_PATH):
        # We assume modifying the fc layer for 2 classes
        model_2class = models.resnet50()
        num_ftrs = model_2class.fc.in_features
        model_2class.fc = torch.nn.Sequential(
            torch.nn.Dropout(p=0.5),
            torch.nn.Linear(num_ftrs, 2)
        )
        
        # Load state dict (handle dict mapping or whole model)
        loaded_data = torch.load(MODEL_2CLASS_PATH, map_location=device)
        if isinstance(loaded_data, dict) and "state_dict" in loaded_data:
            model_2class.load_state_dict(loaded_data["state_dict"], strict=False)
        elif isinstance(loaded_data, dict):
            model_2class.load_state_dict(loaded_data, strict=False)
        else: # Attempt to use it directly if it's the whole model
            model_2class = loaded_data

        model_2class = model_2class.to(device)
        model_2class.eval()
        print(f"✅ Loaded 2-class model from {MODEL_2CLASS_PATH}")
    else:
        print(f"⚠️ Model path not found: {MODEL_2CLASS_PATH}. Will mock results.")
except Exception as e:
    print(f"❌ Error loading 2-class model: {e}")

# Model 2: EfficientNetB0 (5 classes)
model_5class = None
try:
    if os.path.exists(MODEL_5CLASS_PATH):
        model_5class = models.efficientnet_b0()
        num_ftrs = model_5class.classifier[1].in_features
        model_5class.classifier[1] = torch.nn.Linear(num_ftrs, 5)
        
        loaded_data = torch.load(MODEL_5CLASS_PATH, map_location=device)
        if isinstance(loaded_data, dict) and "state_dict" in loaded_data:
            model_5class.load_state_dict(loaded_data["state_dict"], strict=False)
        elif isinstance(loaded_data, dict):
            model_5class.load_state_dict(loaded_data, strict=False)
        else:
            model_5class = loaded_data

        model_5class = model_5class.to(device)
        model_5class.eval()
        print(f"✅ Loaded 5-class model from {MODEL_5CLASS_PATH}")
    else:
        print(f"⚠️ Model path not found: {MODEL_5CLASS_PATH}. Will mock results.")
except Exception as e:
    print(f"❌ Error loading 5-class model: {e}")

# Transform
transform = transforms.Compose([
    transforms.Resize((224, 224)),
    transforms.ToTensor(),
    transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225]),
])

class PredictRequest(BaseModel):
    urls: List[str]

@app.post("/api/v1/predict_urls")
async def predict_urls(req: PredictRequest):
    if not req.urls:
        raise HTTPException(status_code=400, detail="No URLs provided")
    
    results = []
    total_safe_score = 0.0
    valid_count = 0

    # Counts for categories voting
    category_votes: Dict[str, float] = {v: 0.0 for v in LABELS_5CLASS.values()}

    async with httpx.AsyncClient() as client:
        for url in req.urls:
            try:
                # Fetch image
                response = await client.get(url, timeout=10.0)
                response.raise_for_status()
                img = Image.open(io.BytesIO(response.content)).convert('RGB')
                tensor = transform(img).unsqueeze(0).to(device)

                is_cafe_prob = 0.5
                categories_prob = {v: 0.2 for v in LABELS_5CLASS.values()}

                with torch.no_grad():
                    # Predict 2-class
                    if model_2class:
                        out2 = model_2class(tensor)
                        probs2 = torch.softmax(out2, dim=1)
                        # Index 1 is cafe
                        is_cafe_prob = probs2[0][1].item()
                    else:
                        is_cafe_prob = 0.99  # Mock

                    # Predict 5-class
                    if model_5class:
                        out5 = model_5class(tensor)
                        probs5 = torch.softmax(out5, dim=1)[0].tolist()
                        categories_prob = {LABELS_5CLASS.get(i, f"class_{i}"): p for i, p in enumerate(probs5)}
                    else:
                        categories_prob["study_cafe"] = 0.8 # Mock
                
                results.append({
                    "url": url,
                    "is_cafe_prob": is_cafe_prob,
                    "categories_prob": categories_prob
                })

                total_safe_score += is_cafe_prob
                valid_count += 1
                
                # Accumulate category vote
                for cat, cp in categories_prob.items():
                    category_votes[cat] += cp

            except Exception as e:
                print(f"Error processing URL {url}: {e}")
                results.append({
                    "url": url,
                    "error": str(e)
                })

    if valid_count == 0:
        return {
            "average_safe_score": 0.0,
            "predicted_category": "unknown",
            "details": results
        }

    average_safe_score = total_safe_score / valid_count
    
    # Calculate best category
    best_category = "unknown"
    if valid_count > 0:
        best_category = max(category_votes.items(), key=lambda x: x[1])[0]

    return {
        "average_safe_score": average_safe_score,
        "predicted_category": best_category,
        "details": results
    }

if __name__ == "__main__":
    import uvicorn
    uvicorn.run("main:app", host="0.0.0.0", port=8000, reload=True)
