"use client";
import { useState, useEffect } from "react";
import { Post } from "./post";
import { PostModal } from "./components/postModal";
import axios from "axios";
import { useAuth } from "@/context/AuthContext";

export function PostList() {
  const [isOpenPost, setIsOpenPost] = useState(false);
  const [selectedPost, setSelectedPost] = useState<any>(null);
  const [posts, setPosts] = useState<any[]>([]);
  const [cursor, setCursor] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const [hasMore, setHasMore] = useState(true);
  const { user } = useAuth();

  const fetchPosts = async () => {
    if (loading || !hasMore) return;
    setLoading(true);

    try {
      const res = await axios.get(`http://localhost:8080/api/blogs`, {
        params: {
          size: 10,
          cursor: cursor ?? undefined, // không gửi cursor ở lần đầu
        },
      });

      const newData = res.data?.data?.data ?? [];
      console.log(newData);
      const nextCursor = res.data?.data?.nextCursor ?? null;

      // append posts, tránh trùng
      setPosts((prev) => [
        ...prev,
        ...newData.filter((p) => !prev.some((x) => x.id === p.id)),
      ]);

      setCursor(nextCursor);
      if (!nextCursor) setHasMore(false); // hết bài
    } catch (err) {
      console.error("Failed to fetch posts:", err);
    } finally {
      setLoading(false);
    }
  };

  // fetch lần đầu và lần scroll
  useEffect(() => {
    fetchPosts();
  }, []);

  // infinite scroll
  useEffect(() => {
    const handleScroll = () => {
      if (
        window.innerHeight + window.scrollY >=
        document.body.offsetHeight - 400
      ) {
        if (!loading) fetchPosts();
      }
    };

    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, [cursor, loading]);

  const openPost = (post: any) => {
    setSelectedPost({
      ...post,
      username: post.userFullName,
      avatar:
        post.userAvatar ??
        "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
      images: post.mediaUrls ?? [],
      likes: post.likeCount,
    });
    setIsOpenPost(true);
  };

  return (
    <>
      {posts.map((p) => (
        <Post
          key={p.id}
          userId={p.userId}
          postId={p.id} // 👈 thêm blogId
          username={p.userFullName}
          avatar={
            p.userAvatar ??
            "https://cdn-icons-png.flaticon.com/512/9131/9131529.png"
          }
          images={p.mediaUrls ?? []}
          likes={p.likeCount}
          caption={p.caption}
          time={new Date(p.createdAt).toLocaleString()}
          onOpenPost={() => openPost(p)}
          userIdLogin={user?.id} // 👈 id user login
          onLikeUpdate={(id) => {
            setPosts((prev) =>
              prev.map((pp) =>
                pp.id === id ? { ...pp, likeCount: pp.likeCount + 1 } : pp
              )
            );
          }}
        />
      ))}

      {loading && <p className="text-center py-4">Loading...</p>}

      <PostModal
        blogId={selectedPost?.id}
        open={isOpenPost}
        onClose={() => setIsOpenPost(false)}
        post={selectedPost}
      />
    </>
  );
}
