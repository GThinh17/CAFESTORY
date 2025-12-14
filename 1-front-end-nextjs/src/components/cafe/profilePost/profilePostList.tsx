"use client";
import { useEffect, useState, useRef } from "react";
import { ProfilePost } from "./profilePost";
import styles from "./profilePost.module.scss";
import { useAuth } from "@/context/AuthContext";
import { useParams } from "next/navigation";
import { PostModal } from "../../PostCf/components/postModal";
import axios from "axios";

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
  const [isOpenPost, setIsOpenPost] = useState(false);
  const [selectedPost, setSelectedPost] = useState<any>(null);
  const [realPageId, setRealPageId] = useState("");
  const { pageId } = useParams();
  const [pageInfo, setPageInfo] = useState<any>(null);

  const { token, loading: authLoading } = useAuth();

  const observerRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const fetchPage = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/pages/cafe-owner/${pageId}`
        );
        setRealPageId(res.data.data.pageId);
        setPageInfo(res.data.data);
      } catch (err: any) {
        console.error("API error:", err.response?.status, err.message);
      }
    };
    fetchPage();
  }, [pageId]);

  useEffect(() => {
    async function fetchPosts() {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/blogs/page/${realPageId}`
        );

        console.log("Response:", res);

        const postData = res.data?.data?.content || [];

        // Lưu posts (lọc trùng nếu cần)
        setPosts(postData);
      } catch (error) {
        console.error("Error fetching posts:", error);
      } finally {
        setLoading(false);
        setLoadingMore(false);
      }
    }

    fetchPosts();
  }, [realPageId, token]);

  const openPost = (post: any) => {
    if (!pageInfo) return;

    setSelectedPost({
      ...post,
      username: pageInfo.pageName,
      avatar:
        pageInfo.avatarUrl ??
        "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
      images: post.mediaUrls ?? [],
      likes: post.likeCount,
    });

    setIsOpenPost(true);
  };

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
              url.searchParams.append("userId", pageId);
              url.searchParams.append("cursor", nextCursor);

              const res = await fetch(url.toString(), {
                method: "GET",
              });

              const data = await res.json();

              // trong fetchMore
              const postData = data.data?.data || [];
              setPosts((prev) => {
                const newPosts = postData.filter(
                  (p: Post) => !prev.some((prevP) => prevP.id === p.id)
                );
                return [...prev, ...newPosts];
              });
              setNextCursor(data.data?.nextCursor || null);
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
  }, [nextCursor, pageId, token]);

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
            onClick={() => openPost(p)}
          />
        ))}
      </div>
      {nextCursor && <div ref={observerRef} style={{ height: 1 }}></div>}
      {loadingMore && <div>Loading more posts...</div>}

      <PostModal
        blogId={selectedPost?.id}
        open={isOpenPost}
        onClose={() => setIsOpenPost(false)}
        post={selectedPost}
      />
    </>
  );
}
