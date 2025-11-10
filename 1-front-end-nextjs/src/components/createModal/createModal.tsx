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

export function CreateModal({
  open,
  onClose,
}: {
  open: boolean;
  onClose: () => void;
}) {
  const [isImg, setIsImg] = useState(false);
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
              <img src="/testPost.jpg" alt="avatar" className="userAvatar" />
              <span className="username">thnhvu_2</span>
            </div>

            {/* Caption */}
            <textarea
              placeholder="Write a caption..."
              className="captionInput"
              maxLength={2200}
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
