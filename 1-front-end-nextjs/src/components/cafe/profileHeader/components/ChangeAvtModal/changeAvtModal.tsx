"use client";

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import "./changeAvtModal.css";
import { useAuth } from "@/context/AuthContext";
import { Button } from "@/components/ui/button";
import axios from "axios";
import { useRef, useState,useEffect } from "react";
import { useParams } from "next/navigation";

export function ChangeAvtModal({ open, onClose }) {
  const { user, token } = useAuth();
  const userId = user?.id;

  const fileInputRef = useRef<HTMLInputElement>(null);
  const [preview, setPreview] = useState<string | null>(null);
  const [file, setFile] = useState<File | null>(null);
  const [uploading, setUploading] = useState(false);
  const [realPageId, setRealPageId] = useState();
  const { pageId } = useParams();

  useEffect(() => {
    const fetchPage = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/pages/cafe-owner/${pageId}`
        );
        setRealPageId(res.data.data.pageId);
      } catch (err: any) {
        console.error("API error:", err.response?.status, err.message);
      }
    };
    fetchPage();
  }, [pageId]);

  const CLOUD_PRESET = "upload";
  const CLOUD_NAME = "dwdjlzl9h";

  const handlePickFile = () => {
    fileInputRef.current?.click();
  };

  const handleSelectFile = (e: React.ChangeEvent<HTMLInputElement>) => {
    const selected = e.target.files?.[0];
    if (!selected) return;

    setFile(selected);
    setPreview(URL.createObjectURL(selected));
  };

  // Upload ảnh lên cloudinary
  const handleUpload = async () => {
    if (!file) {
      alert("Please select an image first!");
      return;
    }

    setUploading(true);

    try {
      const formData = new FormData();
      formData.append("file", file);
      formData.append("upload_preset", CLOUD_PRESET);

      const res = await axios.post(
        `https://api.cloudinary.com/v1_1/${CLOUD_NAME}/image/upload`,
        formData
      );

      const url = res.data.secure_url;
      await updateAvatar(url);

      alert("Avatar updated successfully!");
      onClose();
    } catch (err) {
      console.error(err);
      alert("Upload image failed!");
    }

    setUploading(false);
  };

  // Gửi avatar url lên backend
  const updateAvatar = async (cloudUrl: string) => {
    return axios.patch(
      `http://localhost:8080/api/pages/${realPageId}`,
      { avatarUrl: cloudUrl },
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      }
    );
  };

  return (
    <Dialog open={open} onOpenChange={onClose}>
      <DialogContent className="ContainerModal">
        <DialogHeader className="HeaderContainer">
          <DialogTitle>Choose Image</DialogTitle>
        </DialogHeader>

        <div className="ChangeAvtCon">
          {/* Preview */}
          {preview && (
            <img src={preview} className="PreviewImage" alt="preview" />
          )}

          {/* Hidden file input */}
          <input
            type="file"
            ref={fileInputRef}
            accept="image/*"
            className="hidden"
            onChange={handleSelectFile}
          />

          <div className="flex gap-3">
            {/* Chọn ảnh */}
            <Button
              className="btn1"
              onClick={handlePickFile}
              disabled={uploading}
            >
              Select Avatar
            </Button>

            {/* Upload ảnh */}
            <Button
              className="btn1"
              onClick={handleUpload}
              disabled={uploading || !file}
            >
              {uploading ? "Uploading..." : "Upload"}
            </Button>
          </div>
        </div>
      </DialogContent>
    </Dialog>
  );
}
