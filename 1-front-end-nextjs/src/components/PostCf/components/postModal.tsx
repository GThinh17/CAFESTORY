"use client";

import { Dialog, DialogContent } from "@/components/ui/dialog";
import "./postModal.css";
import { Heart, MessageCircle, Send, MoreHorizontal } from "lucide-react";

interface PostModalProps {
  open: boolean;
  onClose: () => void;
  post: any;
}

export function PostModal({ open, onClose, post }: PostModalProps) {
  if (!post) return null;

  return (
    <Dialog open={open} onOpenChange={onClose}>
      <DialogContent className="PostModalCon">
        <div className="PostWrapper">
          {/* LEFT */}
          <div className="PostImage">
            <img src={post.image} alt={post.username} />
          </div>

          {/* RIGHT */}
          <div className="PostInfo">
            {/* Header */}
            <div className="PostHeader">
              <img className="Avatar" src={post.avatar} alt="avatar" />
              <span className="UserName">{post.username}</span>
            </div>

            <div className="PostComments">
              <div className="CommentItem">
                <span className="CommentAuthor">
                  {post.username}: {post.caption}{" "}
                </span>
              </div>
            </div>

            {/* Likes */}
            <div className="PostActions">
              <Heart /> {post.likes} likes
            </div>

            {/* Input */}
            <div className="PostInput">
              <input placeholder="Add a comment..." />
              <button>Send</button>
            </div>
          </div>
        </div>
      </DialogContent>
    </Dialog>
  );
}
