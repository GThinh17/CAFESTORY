"use client";

import { Dialog, DialogContent } from "@/components/ui/dialog";
import "./postModal.css";
import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "../../ui/carousel";
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
          <div className="PostImage flex items-center justify-center bg-black">
            <Carousel className="w-full max-w-[500px]">
              <CarouselContent>
                {post.images.map((img: string, i: number) => (
                  <CarouselItem
                    key={i}
                    className="flex items-center justify-center"
                  >
                    <img
                      src={img}
                      alt={`image ${i + 1}`}
                      className="max-h-[500px] object-contain"
                    />
                  </CarouselItem>
                ))}
              </CarouselContent>

              {post.images.length > 1 && (
                <>
                  <CarouselPrevious className="left-2 bg-black/40 text-white hover:bg-black/60" />
                  <CarouselNext className="right-2 bg-black/40 text-white hover:bg-black/60" />
                </>
              )}
            </Carousel>
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
