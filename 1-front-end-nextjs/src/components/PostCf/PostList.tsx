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
          cursor: cursor ?? undefined,
        },
      });

      let newData = res.data?.data?.data ?? [];
      const nextCursor = res.data?.data?.nextCursor ?? null;

      // ====== 🔥 Fetch Page Info nếu có pageId ======
      const postsWithPageInfo = await Promise.all(
        newData.map(async (post: any) => {
          if (post.pageId) {
            try {
              const pageRes = await axios.get(
                `http://localhost:8080/api/pages/${post.pageId}`
              );
              const page = pageRes.data.data;

              return {
                ...post,
                displayName: page.pageName,
                displayAvatar: page.avatarUrl,
              };
            } catch (err) {
              console.error("Failed to fetch page info", err);
              return post;
            }
          }

          // nếu là post user thường
          return {
            ...post,
            displayName: post.userFullName,
            displayAvatar:
              post.userAvatar ??
              "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
          };
        })
      );

      // append posts, tránh trùng
      setPosts((prev) => [
        ...prev,
        ...postsWithPageInfo.filter((p) => !prev.some((x) => x.id === p.id)),
      ]);

      setCursor(nextCursor);
      if (!nextCursor) setHasMore(false);
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
      username: post.displayName,
      avatar: post.displayAvatar,
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
          postId={p.id}
          username={p.displayName}
          avatar={p.displayAvatar}
          images={p.mediaUrls ?? []}
          likes={p.likeCount}
          caption={p.caption}
          time={new Date(p.createdAt).toLocaleString()}
          onOpenPost={() => openPost(p)}
          userIdLogin={user?.id}
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
