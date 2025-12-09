"use client";

import { Dialog, DialogContent, DialogTitle } from "@/components/ui/dialog";
import "./postModal.css";
import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "../../ui/carousel";
import { useEffect, useState } from "react";
import { useAuth } from "@/context/AuthContext";
import axios from "axios";

interface PostModalProps {
  open: boolean;
  onClose: () => void;
  post: any;
  blogId: string;
}

export function PostModal({ open, onClose, post, blogId }: PostModalProps) {
  if (!post) return null;

  const [comments, setComments] = useState<any[]>([]);
  const [nextCursor, setNextCursor] = useState<string | null>(null);
  const [comment, setComment] = useState("");

  const { user, token } = useAuth();

  // ------------------------------------------
  // GET COMMENTS
  // ------------------------------------------
  const getComments = async (cursor?: string) => {
    try {
      const res = await axios.get("http://localhost:8080/api/comments", {
        params: {
          blogId: blogId,
          size: 12,
          cursor: cursor ? cursor : null,
        },
        headers: { Authorization: `Bearer ${token}` },
      });

      const newComments = res.data.data.data;
      const newCursor = res.data.data.nextCursor;

      if (!cursor) {
        setComments(newComments);
      } else {
        setComments((prev) => [...prev, ...newComments]);
      }

      setNextCursor(newCursor);
    } catch (error) {
      console.error("Failed to load comments", error);
    }
  };

  // Load comment mỗi khi mở modal
  useEffect(() => {
    if (open) {
      getComments(); // load trang đầu tiên
    }
  }, [open]);

  // ------------------------------------------
  // HANDLE SEND COMMENT
  // ------------------------------------------
  const handleComment = async () => {
    try {
      await axios.post(
        "http://localhost:8080/api/comments",
        {
          blogId: blogId,
          userId: user?.id,
          commentParentId: null,
          content: comment,
          commentImageId: null,
        },
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );

      setComment("");
      getComments(); // reload comments
    } catch (err: any) {
      console.error("Comment failed:", err.response?.data || err.message);
    }
  };

  return (
    <Dialog open={open} onOpenChange={onClose}>
      <DialogTitle></DialogTitle>

      <DialogContent className="PostModalCon">
        <div className="PostWrapper">
          {/* LEFT */}
          <div className="PostImage flex items-center justify-center bg-black">
            <Carousel className="w-full ">
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
              <div className="avatarHeader">
                <img className="Avatar" src={post.avatar} alt="avatar" />
                <span className="UserName">{post.username} : </span>
              </div>
              <div className="status">
                <span className="Author">{post.caption}</span>
              </div>
            </div>

            {/* Caption */}
            <div className="PostComments">
              {/* Render comments */}
              {[...comments].reverse().map((cmt) => (
                <div key={cmt.commentId} className="CommentItem">
                  <span className="CommentGuest">
                    {cmt.userFullName}: {cmt.content}
                  </span>
                </div>
              ))}

              {/* Load more */}
              {nextCursor && (
                <button
                  onClick={() => getComments(nextCursor!)}
                  className="LoadMoreBtn"
                >
                  Load more...
                </button>
              )}
            </div>

            {/* Comment input */}
            <div className="PostInput">
              <input
                placeholder="Add a comment..."
                value={comment}
                onChange={(e) => setComment(e.target.value)}
                onKeyDown={(e) => {
                  if (e.key === "Enter") {
                    e.preventDefault();
                    handleComment();
                  }
                }}
              />
              <button onClick={handleComment}>Send</button>
            </div>
          </div>
        </div>
      </DialogContent>
    </Dialog>
  );
}
