"use client";

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import "./createModal.css";
import { useState, useEffect } from "react";
import { useAuth } from "@/context/AuthContext";
import axios from "axios";
import { useRouter } from "next/navigation";
import { Switch } from "@/components/ui/switch";
import { Label } from "@/components/ui/label";
import { Check } from "lucide-react";

interface Reviewer {
  id: string;
  userId: string;
  userName: string;
  userAvatarUrl: string;
  type: "USER" | "PAGE";
}

export function CreateModal({
  open,
  onClose,
}: {
  open: boolean;
  onClose: () => void;
}) {
  const [isCfOwner, setIsCfOwner] = useState(false);
  const [isImg, setIsImg] = useState(false);
  const [caption, setcaption] = useState("");
  const [mediaFiles, setMediaFiles] = useState<File[]>([]);
  const [visibility, setvisibility] = useState("PUBLIC");
  const [allowComment, setallowComment] = useState(true);
  const [isPin, setisPin] = useState(false);
  const [locationId, setlocationId] = useState(null);
  const [loadingUp, setLoadingUp] = useState(false);
  const [isPostCf, setIsPostCf] = useState(false);
  const [pageId, setPageId] = useState("");
  const [cfOwnerId, setCfOwnerId] = useState("");
  
  const [uploadedUrls, setUploadedUrls] = useState<string[]>([]);
  const [isUploading, setIsUploading] = useState(false);
  const [isPredicting, setIsPredicting] = useState(false);
  const [aiCategories, setAiCategories] = useState<{name: string, prob: number}[]>([]);
  const [selectedCategory, setSelectedCategory] = useState<string>("");

  const { user, token } = useAuth();
  const username = user?.username;
  const avatar = user?.avatar;
  const userId = user?.id;

  const [reviewers, setReviewers] = useState<Reviewer[]>([]);
  const [selectedCollaborators, setSelectedCollaborators] = useState<
    Reviewer[]
  >([]);
  const [showCollaboratorsModal, setShowCollaboratorsModal] = useState(false);

  const router = useRouter();

  // ------------------- FETCH FOLLOWINGS -------------------
  useEffect(() => {
    if (!user?.id || !token || !showCollaboratorsModal) return;

    async function fetchFollowings() {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/follows/users/${userId}/following`,
          { headers: { Authorization: `Bearer ${token}` } }
        );

        const mappedData: Reviewer[] = res.data.data.map((item: any) => {
          if (item.followType === "USER") {
            return {
              id: item.followId,
              userId: item.userFollowedId,
              userName: item.userFollowedFullName,
              userAvatarUrl: item.userFollowedAvatar,
              type: "USER",
            };
          }

          return {
            id: item.followId,
            userId: item.pageFollowedId,
            userName: item.pageFollowedName,
            userAvatarUrl: item.pageFollowedAvatar,
            type: "PAGE",
          };
        });

        setReviewers(mappedData);
      } catch (error) {
        console.error("Fetch followings failed:", error);
      }
    }

    fetchFollowings();
  }, [user?.id, token, showCollaboratorsModal]);

  // ------------------- FETCH CAFE OWNER STATUS -------------------
  useEffect(() => {
    const fetchStatus = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/cafe-owners/user/${user?.id}/exists`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        );
        setIsCfOwner(res.data.data);
      } catch (err) {
        console.log("fetch status fail");
      }
    };

    fetchStatus();
  }, [open, user?.id, token]);

  // ------------------- FETCH CAFE OWNER ID -------------------
  useEffect(() => {
    const fetchCfOwnerId = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/cafe-owners/user/${user?.id}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        );
        setCfOwnerId(res.data.data.id);
      } catch (err) {
        console.log("fetch status fail");
      }
    };

    fetchCfOwnerId();
  }, [open, user?.id, token]);

  // ------------------- FETCH PAGE ID -------------------
  useEffect(() => {
    const fetchPage = async () => {
      try {
        if (!cfOwnerId) return;

        const res = await axios.get(
          `http://localhost:8080/api/pages/cafe-owner/${cfOwnerId}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        );
        setPageId(res.data.data.pageId);
      } catch (err) {
        console.log("fetch status fail");
      }
    };

    fetchPage();
  }, [cfOwnerId, token]);

  // ------------------- UPLOAD IMAGES -------------------
  const uploadImages = async () => {
    const urls: string[] = [];
    for (const file of mediaFiles) {
      const formData = new FormData();
      formData.append("file", file);
      formData.append("upload_preset", "upload");

      const res = await axios.post(
        "https://api.cloudinary.com/v1_1/dwdjlzl9h/image/upload",
        formData
      );

      if (res.data.secure_url) urls.push(res.data.secure_url);
    }
    return urls;
  };

  const resetForm = () => {
      setcaption("");
      setMediaFiles([]);
      setSelectedCollaborators([]);
      setUploadedUrls([]);
      setAiCategories([]);
      setSelectedCategory("");
      setIsImg(false);
  };

  // ------------------- CREATE BLOG AND TAG COLLABORATORS -------------------
  const handleSharePost = async () => {
    try {
      setLoadingUp(true);

      let mediaUrls: string[] = uploadedUrls;
      // Fallback in case upload didn't finish or skipped somehow
      if (mediaUrls.length === 0 && mediaFiles.length > 0) {
          mediaUrls = await uploadImages();
      }

      const payload: any = {
        caption,
        mediaUrls,
        visibility,
        allowComment,
        isPin,
        locationId,
        category: selectedCategory || null,
      };

      if (isPostCf) {
        payload.pageId = pageId;
        payload.userId = userId; // bài viết của quán
      } else {
        payload.userId = userId; // bài viết cá nhân
      }

      const blogRes = await axios.post(
        "http://localhost:8080/api/blogs",
        payload,
        { headers: { Authorization: `Bearer ${token}` } }
      );

      const blogId = blogRes.data.data.id;

      await Promise.all(
        selectedCollaborators.map((collab) => {
          const payload: any = {
            userId,
            blogIdTag: blogId,
          };

          if (collab.type === "USER") payload.userIdTag = collab.userId;
          if (collab.type === "PAGE") payload.pageTagId = collab.userId;

          return axios.post("http://localhost:8080/api/tags", payload, {
            headers: { Authorization: `Bearer ${token}` },
          });
        })
      );

      resetForm();
      onClose();
    } catch (err) {
      console.error(err);
    } finally {
      setLoadingUp(false);
    }
  };

  return (
    <Dialog open={open} onOpenChange={onClose}>
      <DialogContent className="modalContainer">
        <DialogHeader className="modalHeader">
          <DialogTitle>Tạo bài viết mới</DialogTitle>
        </DialogHeader>

        <div className="modalBody">
          <div className="createLeft">
            {!isImg && (
              <input
                className="button11"
                type="file"
                accept="image/*"
                multiple
                onChange={async (e) => {
                  const files = Array.from(e.target.files || []);
                  if (files.length === 0) return;
                  setMediaFiles(files);
                  setIsImg(true);
                  setIsUploading(true);
                  
                  // Upload to Cloudinary right away
                  const urls: string[] = [];
                  for (const file of files) {
                    const formData = new FormData();
                    formData.append("file", file);
                    formData.append("upload_preset", "upload");

                    try {
                      const res = await axios.post("https://api.cloudinary.com/v1_1/dwdjlzl9h/image/upload", formData);
                      if (res.data.secure_url) urls.push(res.data.secure_url);
                    } catch (e) {
                      console.error(e);
                    }
                  }
                  setUploadedUrls(urls);
                  setIsUploading(false);

                  // Call predict API
                  if (urls.length > 0) {
                     setIsPredicting(true);
                     try {
                        const predictRes = await axios.post("http://localhost:8080/api/blogs/predict", { urls }, {
                            headers: { Authorization: `Bearer ${token}` }
                        });
                        
                        const payload = predictRes.data?.data || predictRes.data;
                        if (payload?.details?.length > 0) {
                            // Sum probabilities across multiple images to get average
                            const aggDetails: Record<string, number> = {};
                            let validCats = 0;
                            payload.details.forEach((dt: any) => {
                                if (dt.categories_prob) {
                                    validCats++;
                                    Object.keys(dt.categories_prob).forEach(k => {
                                        aggDetails[k] = (aggDetails[k] || 0) + dt.categories_prob[k];
                                    });
                                }
                            });
                            
                            if (validCats > 0) {
                                const arr = Object.keys(aggDetails).map(k => ({
                                    name: k, 
                                    prob: aggDetails[k] / validCats
                                }));
                                arr.sort((a,b) => b.prob - a.prob);
                                setAiCategories(arr);
                            }
                        }
                     } catch(e) {
                        console.error("AI predict error", e);
                     } finally {
                        setIsPredicting(false);
                     }
                  }
                }}
              />
            )}

            {isImg && (
              <div
                className={`imgGrid ${
                  mediaFiles.length === 1
                    ? "oneImg"
                    : mediaFiles.length === 2
                    ? "twoImg"
                    : "multiImg"
                }`}
              >
                {mediaFiles.map((file, idx) => (
                  <div className="imgCon" key={idx}>
                    <img
                      src={URL.createObjectURL(file)}
                      className="previewImg"
                    />
                  </div>
                ))}
              </div>
            )}
          </div>

          <div className="createRight">
            <div className="userRow">
              <img src={avatar} alt="avatar" className="userAvatar" />
              <span className="username">{username}</span>
            </div>

            <textarea
              placeholder="Thêm mô tả..."
              className="captionInput"
              maxLength={2200}
              value={caption}
              onChange={(e) => setcaption(e.target.value)}
            />

            <div
              className="optionRow"
              onClick={() => setShowCollaboratorsModal(true)}
            >
              <span>Thêm người sáng tạo</span>
              <input
                type="text"
                readOnly
                value={selectedCollaborators.map((c) => c.userName).join(", ")}
                placeholder="Chọn một người..."
              />
            </div>

            {isCfOwner && (
              <div className="optionRow switchRow">
                <Label htmlFor="post-cf">Bài viết của quán cà phê</Label>
                <Switch
                  className="Switch"
                  id="post-cf"
                  checked={isPostCf}
                  onCheckedChange={(v) => setIsPostCf(v)}
                />
              </div>
            )}

            <div className="optionRow" style={{flexDirection: "column", alignItems: "flex-start"}}>
              <Label style={{marginBottom: "8px", fontWeight: "bold"}}>Nhãn chủ đề (AI phân tích)</Label>
              {isUploading ? (
                 <span className="text-xs text-blue-500 italic">Đang tải ảnh lên...</span>
              ) : isPredicting ? (
                 <span className="text-xs text-orange-500 italic">⏳ Đang phân tích AI...</span>
              ) : mediaFiles.length > 0 ? (
                 <div className="flex flex-col gap-2 w-full mt-2">
                   <div 
                     onClick={() => setSelectedCategory("")}
                     className={`cursor-pointer px-3 py-2 rounded border text-sm font-medium transition-colors ${selectedCategory === "" ? "bg-amber-100 border-amber-500 text-amber-800" : "bg-white hover:bg-gray-50 border-gray-200"}`}
                   >
                     ✨ Tự động (Mặc định: {aiCategories.length > 0 ? (
                        aiCategories[0].name === 'study_cafe' ? '📚 Học tập' :
                        aiCategories[0].name === 'pet_cafe' ? '🐶 Thú cưng' :
                        aiCategories[0].name === 'garden_cafe' ? '🌿 Sân vườn' :
                        aiCategories[0].name === 'aesthetic_cafe' ? '✨ Chụp ảnh' :
                        aiCategories[0].name === 'food_cafe' ? '🍕 Ăn uống' : aiCategories[0].name
                     ) + ` - ${Math.round(aiCategories[0].prob * 100)}%` : 'Đang phân tích...'})
                   </div>
                   <div className="flex flex-wrap gap-2">
                     {aiCategories.map(c => (
                       <div 
                         key={c.name}
                         onClick={() => setSelectedCategory(c.name)}
                         className={`cursor-pointer px-3 py-1.5 rounded-full border text-xs font-medium transition-colors ${selectedCategory === c.name ? "bg-blue-100 border-blue-500 text-blue-800" : "bg-white hover:bg-gray-100 border-gray-200"}`}
                       >
                         {c.name === 'study_cafe' ? '📚 Học tập' : 
                          c.name === 'pet_cafe' ? '🐶 Thú cưng' : 
                          c.name === 'garden_cafe' ? '🌿 Sân vườn' : 
                          c.name === 'aesthetic_cafe' ? '✨ Chụp ảnh' : 
                          c.name === 'food_cafe' ? '🍕 Ăn uống' : c.name} - ({Math.round(c.prob * 100)}%)
                       </div>
                     ))}
                   </div>
                 </div>
              ) : (
                <span className="text-xs text-gray-400">Tải ảnh lên để xem gợi ý AI</span>
              )}
            </div>

            <div className="btnShare flex gap-2">
              <Button disabled={loadingUp} variant="outline" className="flex-1 w-50" onClick={() => { resetForm(); onClose(); }}>
                Hủy
              </Button>
              <Button disabled={loadingUp} className="flex-1" onClick={handleSharePost}>
                {loadingUp ? "Đăng tải..." : "Chia sẻ"}
              </Button>
            </div>
          </div>
        </div>

        {showCollaboratorsModal && (
          <Dialog
            open={showCollaboratorsModal}
            onOpenChange={() => setShowCollaboratorsModal(false)}
          >
            <DialogContent className="collaboratorsDialogContent">
              <DialogHeader>
                <DialogTitle>Chọn người sáng tạo</DialogTitle>
              </DialogHeader>

              <div className="collaboratorsList">
                {reviewers.map((r) => (
                  <div
                    key={r.id}
                    className="collaboratorRow"
                    onClick={() => {
                      if (r.type !== "USER") return;
                      setSelectedCollaborators((prev) =>
                        prev.find((c) => c.id === r.id)
                          ? prev.filter((c) => c.id !== r.id)
                          : [...prev, r]
                      );
                    }}
                  >
                    <img
                      src={
                        r.userAvatarUrl ||
                        "https://cdn-icons-png.flaticon.com/512/9131/9131529.png"
                      }
                      width={30}
                      height={30}
                    />
                    <span>{r.userName}</span>
                    {selectedCollaborators.find((c) => c.id === r.id) && (
                      <Check />
                    )}
                  </div>
                ))}
              </div>
            </DialogContent>
          </Dialog>
        )}
      </DialogContent>
    </Dialog>
  );
}
