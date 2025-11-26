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
  const [mediaUrls, setmediaUrls] = useState("");
  const [visibility, setvisibility] = useState("");
  const [isPin, setisPin] = useState("");
  const [locationId, setlocationId] = useState("");


  const { user, loading } = useAuth();
  const username = user?.username;
  const avatar = user?.avatar;
  const userId = user?.id;

  const handlehandleCreateBlog = async () => {
    try {
      const res = await axios.post("http://localhost:8080/api/blogs", {
        caption: caption,
        mediaUrls: mediaUrls,
        visibility: visibility,
        isPin: isPin,
        locationId: locationId,
        userId: userId,

      });
      router.push("/");
    }

    catch (err: any) {
      console.error(err);
      alert(err.response?.data?.message || "Vui lòng nhập đủ trường");
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
            {!isImg && <Button className="button" onClick={() => setIsImg(true)}>
              Choose image
            </Button>}
            {isImg && (
              <img src="/testPost.jpg" alt="preview" className="previewImg" />
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
              <Button className="button">Share</Button>
            </div>
          </div>
        </div>
      </DialogContent>
    </Dialog>
  );
}
