"use client";
import { useState, useEffect } from "react";
import { Post } from "./post";
import { PostModal } from "./components/postModal";
import axios from "axios";

export function PostList() {
  const [isOpenPost, setIsOpenPost] = useState(false);
  const [selectedPost, setSelectedPost] = useState<any>(null);
  const [posts, setPosts] = useState<any[]>([]);
  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const res = await axios.get("http://localhost:8080/api/blogs");
        const data = res.data?.data?.data?.data ?? []; // path theo JSON API bạn cung cấp
        setPosts(data);
      } catch (err) {
        console.error("Failed to fetch posts:", err);
      }
    };
    fetchPosts();
  }, []);
  const openPost = (post: any) => {
    setSelectedPost({
      ...post,
      username: post.userFullName,
      avatar:
        post.userAvatar ??
        "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
      image: post.mediaUrls?.[0] ?? "https://via.placeholder.com/500",
      likes: post.likeCount,
    });
    setIsOpenPost(true);
  };

  return (
    <>
      {posts.map((p, index) => (
        <Post
          key={p.id ?? index}
          username={p.userFullName}
          avatar={
            p.userAvatar ??
            "https://cdn-icons-png.flaticon.com/512/9131/9131529.png"
          }
          image={p.mediaUrls[0] ?? ""}
          likes={p.likeCount}
          caption={p.caption}
          time={new Date(p.createdAt).toLocaleString()}
          onOpenPost={() => openPost(p)}
        />
      ))}

      <PostModal
        open={isOpenPost}
        onClose={() => setIsOpenPost(false)}
        post={selectedPost}
      />
    </>
  );
}
