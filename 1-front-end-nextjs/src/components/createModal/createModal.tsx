"use client";

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import "./createModal.css";
import { useState } from "react";
import { useAuth } from "@/context/AuthContext";
import axios from "axios";
import { useRouter } from "next/navigation";

export function CreateModal({
  open,
  onClose,
}: {
  open: boolean;
  onClose: () => void;
}) {

  const [isImg, setIsImg] = useState(false);
  const [caption, setcaption] = useState("");
  const [mediaFiles, setMediaFiles] = useState<File[]>([]);
  const [visibility, setvisibility] = useState("PUBLIC");
  const [allowComment, setallowComment] = useState(true);
  const [isPin, setisPin] = useState(false);
  const [locationId, setlocationId] = useState(null);
  const [loadingUp, setLoadingUp] = useState(false);

  const { user, token } = useAuth();
  const username = user?.username;
  const avatar = user?.avatar;
  const userId = user?.id;
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

      if (res.data.secure_url) {
        urls.push(res.data.secure_url);
      }
    }

    return urls;
  };


  const handlehandleCreateBlog = async () => {
    try {
      setLoadingUp(true);

      // 1. Upload ảnh -> nhận URL
      let mediaUrls: string[] = [];
      if (mediaFiles.length > 0) {
        mediaUrls = await uploadImages();
      }

      // 2. Gửi bài viết về API backend
      const res = await axios.post(
        "http://localhost:8080/api/blogs",
        {
          caption: caption,
          mediaUrls: mediaUrls,
          visibility: "PUBLIC",
          allowComment: true,
          isPin: false,
          locationId: null,
          userId: userId,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );


      console.log("Created Blog:", res.data.data);

      // Reset
      setcaption("");
      setMediaFiles([]);
      onClose();
    } catch (error) {
      console.error("Error:", error);
    } finally {
      setLoadingUp(false);
    }
  };

  const router = useRouter();
  return (
    <Dialog open={open} onOpenChange={onClose}>
      <DialogContent className="modalContainer">
        <DialogHeader className="modalHeader">
          <DialogTitle>Create new post</DialogTitle>
        </DialogHeader>

        <div className="modalBody">
          {/* LEFT */}
          <div className="createLeft">
            {/* ✅ Ảnh preview */}
            {!isImg && <input className="button11"
              type="file"
              accept="image/*"
              multiple
              onChange={(e) => {
                const files = Array.from(e.target.files || []);
                setMediaFiles(files);
                setIsImg(true);
              }}
            />}
            {isImg && (
              <div className="imgGrid">
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

          {/* RIGHT */}
          <div className="createRight">
            {/* Avatar + tên */}
            <div className="userRow">
              <img src={avatar} alt="avatar" className="userAvatar" />
              <span className="username">{username}</span>
            </div>

            {/* Caption */}
            <textarea
              placeholder="Write a caption..."
              className="captionInput"
              maxLength={2200}
              value={caption}
              onChange={(e) => setcaption(e.target.value)}
            ></textarea>

            {/* Add Location */}
            <div className="optionRow">
              <span>Add location</span>
            </div>

            {/* Add collaborators */}
            <div className="optionRow">
              <span>Add collaborators</span>
            </div>
            <div className="btnShare">
              <Button disabled={loadingUp} onClick={handlehandleCreateBlog} className="button">{loadingUp ? "Sharing..." : "Share"}</Button>
            </div>
          </div>
        </div>
      </DialogContent>
    </Dialog>
  );
}
