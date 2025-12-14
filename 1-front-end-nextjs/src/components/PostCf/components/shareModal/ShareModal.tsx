"use client";

import { useState } from "react";
import axios from "axios";
import { X } from "lucide-react";
import { useAuth } from "@/context/AuthContext";
import "./shareModal.css";

interface ShareModalProps {
  open: boolean;
  onClose: () => void;
  blogId: string;
}

export function ShareModal({ open, onClose, blogId }: ShareModalProps) {
  const { user, token } = useAuth();
  const [caption, setCaption] = useState("");
  const [loading, setLoading] = useState(false);

  if (!open) return null;

  const handleShare = async () => {
    if (!caption.trim()) return;

    try {
      setLoading(true);

      await axios.post(
        "http://localhost:8080/api/shares",
        {
          userId: user?.id,
          blogId,
          caption,
          locationId: null,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );

      onClose();
      setCaption("");
    } catch (err) {
      console.error("Share failed:", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="share-overlay">
      <div className="share-modal">
        <div className="share-header">
          <span>Share</span>
          <X className="close-icon" onClick={onClose} />
        </div>

        <textarea
          placeholder="Write something..."
          value={caption}
          onChange={(e) => setCaption(e.target.value)}
        />

        <button
          className="share-btn"
          disabled={loading}
          onClick={handleShare}
        >
          {loading ? "Sharing..." : "Share"}
        </button>
      </div>
    </div>
  );
}
