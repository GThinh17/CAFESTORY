"use client";
import { useEffect, useState, useRef } from "react";
import { ProfilePost } from "./profilePost";
import styles from "./profilePost.module.scss";
import { useAuth } from "@/context/AuthContext";
import { useParams } from "next/navigation";

interface Post {
  id: string;
  caption: string;
  mediaUrls: string[];
  likeCount: number;
  createdAt: string;
}

export function ProfilePostList() {
  const [posts, setPosts] = useState<Post[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [loadingMore, setLoadingMore] = useState<boolean>(false);
  const [nextCursor, setNextCursor] = useState<string | null>(null);

  const { userId } = useParams();
  const { token, loading: authLoading } = useAuth();

  const observerRef = useRef<HTMLDivElement>(null);

  // Fetch initial posts
  useEffect(() => {
    async function fetchPosts(cursor: string | null = null) {
      try {
        const url = new URL("http://localhost:8080/api/blogs");
        url.searchParams.append("size", "9");
        url.searchParams.append("userId", userId);
        if (cursor) url.searchParams.append("cursor", cursor);

        const res = await fetch(url.toString(), {
          method: "GET",
        });
        const data = await res.json();
        const postData = data.data?.data?.data || [];
        setPosts((prev) => {
          // lọc post mới không trùng với các post đã có
          const newPosts = postData.filter(
            (p: Post) => !prev.some((prevP) => prevP.id === p.id)
          );
          return [...prev, ...newPosts];
        });

        setNextCursor(data.data?.data?.nextCursor || null);
      } catch (error) {
        console.error("Error fetching posts:", error);
      } finally {
        setLoading(false);
        setLoadingMore(false);
      }
    }

    fetchPosts();
  }, [userId, token]);

  // Infinite scroll
  useEffect(() => {
    if (!observerRef.current || !nextCursor) return;

    const observer = new IntersectionObserver(
      (entries) => {
        if (entries[0].isIntersecting && nextCursor) {
          setLoadingMore(true);
          // fetch thêm posts
          const fetchMore = async () => {
            try {
              const url = new URL("http://localhost:8080/api/blogs");
              url.searchParams.append("size", "9");
              url.searchParams.append("userId", userId);
              url.searchParams.append("cursor", nextCursor);

              const res = await fetch(url.toString(), {
                method: "GET",
              });

              const data = await res.json();

              // trong fetchMore
              const postData = data.data?.data?.data || [];
              setPosts((prev) => {
                const newPosts = postData.filter(
                  (p: Post) => !prev.some((prevP) => prevP.id === p.id)
                );
                return [...prev, ...newPosts];
              });
              setNextCursor(data.data?.data?.nextCursor || null);
            } catch (error) {
              console.error("Error fetching more posts:", error);
            } finally {
              setLoadingMore(false);
            }
          };
          fetchMore();
        }
      },
      { threshold: 1 }
    );

    observer.observe(observerRef.current);

    return () => observer.disconnect();
  }, [nextCursor, userId, token]);

  if (authLoading || loading) return <div>Loading...</div>;
  if (posts.length === 0) return <div>No post</div>;

  return (
    <>
      <div className={styles.list}>
        {posts.map((p) => (
          <ProfilePost
            key={p.id}
            image={p.mediaUrls[0]}
            caption={p.caption}
            likes={p.likeCount}
            time={new Date(p.createdAt).toLocaleDateString()}
          />
        ))}
      </div>
      {nextCursor && <div ref={observerRef} style={{ height: 1 }}></div>}
      {loadingMore && <div>Loading more posts...</div>}
    </>
  );
}
