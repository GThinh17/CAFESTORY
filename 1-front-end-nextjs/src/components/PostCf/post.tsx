"use client";

import Image from "next/image";
import { useState } from "react";
import { Heart, MessageCircle, Send, MoreHorizontal } from "lucide-react";
import { Input } from "../ui/input";
import "./post.css";

export interface PostProps {
  username: string;
  avatar: string;
  image: string;
  likes: number;
  caption: string;
  time: string;
  onOpenPost: () => void;  // 👈 thêm vào
}

export function Post({
  username,
  avatar,
  image,
  likes,
  caption,
  time,
  onOpenPost,
}: PostProps) {
  const [expanded, setExpanded] = useState(false);

  return (
    <div className="post">
      {/* Header */}
      <div className="post-header">
        <div className="user-info">
          <Image
            src={avatar}
            alt="avatar"
            width={30}
            height={30}
            className="avatar"
          />
          <span className="username-post">{username}</span>
          <span className="dot">•</span>
          <span className="time">{time}</span>
        </div>
        <MoreHorizontal size={20} className="more-icon" />
      </div>

      {/* Image */}
      <div className="post-image">
        <Image
          src={image}
          alt={`${username}'s post`}
          width={470}
          height={470}
          className="main-image"
        />
      </div>

      {/* Actions */}
      <div className="post-actions">
        <div className="left-actions">
          <Heart size={24} className="icon" />
          <MessageCircle
            size={24}
            className="icon"
            onClick={onOpenPost}     // 👈 mở modal khi bấm comment
          />
          <Send size={24} className="icon" />
        </div>
      </div>

      {/* Likes */}
      <div className="likes">{likes} likes</div>

      {/* Caption */}
      <div className="caption-container">
        <div className={`caption ${expanded ? "expanded" : "clamped"}`}>
          <strong>{username}</strong> {caption}
        </div>

        {!expanded && caption.length > 80 && (
          <button className="more" onClick={() => setExpanded(true)}>
            more
          </button>
        )}
        {expanded && (
          <button className="less" onClick={() => setExpanded(false)}>
            less
          </button>
        )}
      </div>

      {/* Add Comment */}
      <div className="add-comment">
        <Input
          placeholder="Add a comment"
          onClick={onOpenPost}        // 👈 click input cũng mở modal
        />
      </div>
    </div>
  );
}
