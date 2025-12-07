"use client";

import Image from "next/image";
import { useState } from "react";
import { Heart, MessageCircle, Send, MoreHorizontal } from "lucide-react";
import { Input } from "../ui/input";
import Link from "next/link";

import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "../ui/carousel";

import "./post.css";

export interface PostProps {
  userId: string;
  username: string;
  avatar: string;
  images: string[];
  likes: number;
  caption: string;
  time: string;
  onOpenPost: () => void;
}

export function Post({
  userId,
  username,
  avatar,
  images,
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
          <Link href={`/profile/${userId}`}>
            <Image
              src={avatar}
              alt="avatar"
              width={30}
              height={30}
              className="avatar cursor-pointer"
            />
          </Link>
          <span className="username-post">{username}</span>
          <span className="dot">•</span>
          <span className="time">{time}</span>
        </div>
        <MoreHorizontal size={20} className="more-icon" />
      </div>

      {/* Carousel */}
      <div className="post-image relative">
        <Carousel className="w-full max-w-[470px] mx-auto">
          <CarouselContent>
            {images.map((img, i) => (
              <CarouselItem
                key={i}
                className="flex items-center justify-center"
              >
                <Image
                  src={img}
                  alt={`${username} image ${i + 1}`}
                  width={470}
                  height={470}
                  className="max-h-[470px] object-contain"
                />
              </CarouselItem>
            ))}
          </CarouselContent>

          {images.length > 1 && (
            <>
              <CarouselPrevious className="P" />
              <CarouselNext className="N" />
            </>
          )}
        </Carousel>
      </div>

      {/* Actions */}
      <div className="post-actions">
        <div className="left-actions">
          <Heart size={24} className="icon" />
          <MessageCircle size={24} className="icon" onClick={onOpenPost} />
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

      {/* Comment input */}
      <div className="add-comment">
        <Input placeholder="Add a comment" onClick={onOpenPost} />
      </div>
    </div>
  );
}
