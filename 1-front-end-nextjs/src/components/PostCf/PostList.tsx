"use client";
import { useState } from "react";
import { Post } from "./post";
import { PostModal } from "./components/postModal";

export function PostList() {
  const [isOpenPost, setIsOpenPost] = useState(false);
  const [selectedPost, setSelectedPost] = useState<any>(null);

  const posts = [
    {
      username: "gundam_among_us",
      avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
      image: "/testPost.jpg",
      likes: 42,
      caption: "HG Destiny Fighter Spec II & Zeus Silhouette...",
      time: "3d",
    },
    {
      username: "mecha_builds",
      avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
      image: "/testPost.jpg",
      likes: 87,
      caption:
        "Lorem ipsum dolor sit amet consectetur adipisicing elit. Molestias iure consequuntur...",
      time: "1d",
    },
    {
      username: "model_kit_fan",
      avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
      image: "/testPost2.jpg",
      likes: 103,
      caption:
        "Finally completed my RX-78 build 😍 Finally completed my RX-78 build 😍...",
      time: "5h",
    },
  ];

  const openPost = (post: any) => {
    setSelectedPost(post);
    setIsOpenPost(true);
  };

  return (
    <>
      <div className="post-list">
        {posts.map((p, index) => (
          <Post key={index} {...p} onOpenPost={() => openPost(p)} />
        ))}
      </div>

      <PostModal
        open={isOpenPost}
        onClose={() => setIsOpenPost(false)}
        post={selectedPost}
      />
    </>
  );
}
