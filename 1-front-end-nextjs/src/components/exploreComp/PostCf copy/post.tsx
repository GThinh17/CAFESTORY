"use client";

import Image from "next/image";
import { useState } from "react";
import { Heart, MessageCircle, Send, MoreHorizontal } from "lucide-react";
import { Input } from "../../ui/input";
import Link from "next/link";
import axios from "axios";
import { useEffect } from "react";
import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "@/components/ui/carousel";
import "./post.css";
import { useAuth } from "@/context/AuthContext";
import { ShareModal } from "./components/shareModal/ShareModal";

export interface PostProps {
  userId: string;
  username: string;
  avatar: string;
  images: string[];
  likes: number;
  caption: string;
  time: string;
  postId: string; // 👈 blogId
  userIdLogin: string; // 👈 id user login
  onOpenPost: () => void;
  onLikeUpdate: (postId: string) => void; // 👈 update UI
}

export function Post({
  userId,
  username,
  avatar,
  images,
  likes,
  caption,
  time,
  postId,
  userIdLogin,
  onOpenPost,
  onLikeUpdate,
}: PostProps) {
  const [localLikes, setLocalLikes] = useState(likes);
  const { token, user } = useAuth();
  const [isLiked, setIsLiked] = useState(false);
  const [openShare, setOpenShare] = useState(false);
  const [isMe, setIsMe] = useState(user?.id === userId);

  ////////////////////////////// LIKE ////////////////////////////////////////////
  const handleLike = async () => {
    if (isLiked) {
      // Update UI ngay
      setIsLiked(false);
      setLocalLikes((prev) => prev - 1);
      onLikeUpdate(postId, false);

      try {
        await axios.delete("http://localhost:8080/api/blog-likes", {
          params: { blogId: postId, userId: user?.id },
          headers: { Authorization: `Bearer ${token}` },
        });
      } catch (error) {
        console.error("Unlike failed:", error);

        // rollback UI
        setIsLiked(true);
        setLocalLikes((prev) => prev + 1);
      }

      return;
    }

    setIsLiked(true);
    setLocalLikes((prev) => prev + 1);
    onLikeUpdate(postId); // update danh sách bài viết

    try {
      await axios.post(
        "http://localhost:8080/api/blog-likes",
        {
          userId: user?.id,
          blogId: postId,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );
    } catch (error) {
      console.error("Like failed:", error);
      // rollback nếu thích
      setIsLiked(false);
      setLocalLikes((prev) => prev - 1);
    }
  };
  ///////////////////////////////////////////////////////////////////////////////

  /////////////////////////////////// Checked Like /////////////////////////////
  useEffect(() => {
    if (!postId || !user?.id) return;

    const checkLiked = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/blog-likes/is-liked`,
          {
            params: { blogId: postId, userId: user.id },
            headers: { Authorization: `Bearer ${token}` },
          }
        );

        setIsLiked(res.data.data === true);
      } catch (err) {
        console.error("Error checking like:", err);
      }
    };

    checkLiked();
  }, [postId, user?.id]);
  //////////////////////////////////////////////////////////////////////////////

  return (
    <>
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
                className="avatar1 cursor-pointer"
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
            <Heart
              size={24}
              className={`icon ${isLiked ? "fill-red-500 text-red-500" : ""}`}
              onClick={handleLike} // 👈 LIKE HERE
            />
            <MessageCircle size={24} className="icon" onClick={onOpenPost} />
            {!isMe && (
              <Send
                size={24}
                className="icon"
                onClick={() => setOpenShare(true)}
              />
            )}
          </div>
        </div>

        {/* Likes */}
        <div className="likes">{localLikes} likes</div>

        {/* Caption */}
        <div className="caption-container">
          <div className="caption">
            <strong>{username}</strong> {caption}
          </div>
        </div>

        {/* Comment input */}
        <div className="add-comment">
          <Input placeholder="Add a comment" onClick={onOpenPost} />
        </div>
      </div>
      <ShareModal
        open={openShare}
        onClose={() => setOpenShare(false)}
        blogId={postId}
      />
    </>
  );
}
