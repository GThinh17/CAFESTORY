"use client";

import { useEffect, useState, useRef } from "react";
import axios from "axios";
import { useParams } from "next/navigation";
import { ProfilePostTag } from "./profilePostTag";
import styles from "./profilePost.module.scss";
import { PostModal } from "../PostCf/components/postModal";
import { useAuth } from "@/context/AuthContext";

/* ===== SHARE FROM BACKEND ===== */
interface Share {
  shareId: string;
  blogTagId: string;
  caption: string; // caption share
  createdAt: string;
  userFullName: string;
  userAvatarUrl?: string;
}

/* ===== POST AFTER MERGE ===== */
interface Post {
  shareId: string;
  id: string; // blogId
  caption: string; // caption blog
  captionShare: string;
  mediaUrls: string[];
  likeCount: number;
  createdAt: string;
  userFullName: string;
  userShareFullName: string;
  userAvatar: string;
}

export function TaggedPostList() {
  const { userId } = useParams<{ userId: string }>();
  const { loading: authLoading } = useAuth();

  const [posts, setPosts] = useState<Post[]>([]);
  const [loading, setLoading] = useState(true);
  const [loadingMore, setLoadingMore] = useState(false);
  const [nextCursor, setNextCursor] = useState<string | null>(null);

  const [isOpenPost, setIsOpenPost] = useState(false);
  const [selectedPost, setSelectedPost] = useState<any>(null);

  const observerRef = useRef<HTMLDivElement>(null);

  /* ===============================
     FETCH SHARE + BLOG DETAIL
  =============================== */
  const fetchSharedPosts = async (cursor: string | null = null) => {
    const res = await axios.get(
      `http://localhost:8080/api/tags/by-useridtag/${userId}`,
      {
        params: {
          userId,
          size: 9,
          cursor: cursor ?? undefined,
        },
      }
    );

    const shares: Share[] = res.data.data || [];

    const posts: Post[] = await Promise.all(
      shares.map(async (share) => {
        const blogRes = await axios.get(
          `http://localhost:8080/api/blogs/${share.blogTagId}`
        );

        const blog = blogRes.data.data;

        return {
          shareId: share.shareId,
          id: blog.id,
          caption: blog.caption,
          captionShare: blog.caption,
          mediaUrls: blog.mediaUrls || [],
          likeCount: blog.likeCount || 0,
          createdAt: blog.createdAt,
          userFullName: blog.userFullName,
          userShareFullName: blog.userFullName,
          userAvatar:
            blog.userAvatar ??
            "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
        };
      })
    );

    return {
      posts,
      nextCursor: res.data.data?.nextCursor || null,
    };
  };

  /* ===============================
     INIT FETCH
  =============================== */
  useEffect(() => {
    if (!userId) return;

    setLoading(true);

    fetchSharedPosts()
      .then(({ posts, nextCursor }) => {
        setPosts(posts);
        setNextCursor(nextCursor);
      })
      .catch(console.error)
      .finally(() => setLoading(false));
  }, [userId]);

  /* ===============================
     INFINITE SCROLL
  =============================== */
  useEffect(() => {
    if (!observerRef.current || !nextCursor) return;

    const observer = new IntersectionObserver(
      (entries) => {
        if (entries[0].isIntersecting) {
          setLoadingMore(true);

          fetchSharedPosts(nextCursor)
            .then(({ posts: newPosts, nextCursor }) => {
              setPosts((prev) => {
                const filtered = newPosts.filter(
                  (p) => !prev.some((prevP) => prevP.shareId === p.shareId)
                );
                return [...prev, ...filtered];
              });
              setNextCursor(nextCursor);
            })
            .catch(console.error)
            .finally(() => setLoadingMore(false));
        }
      },
      { threshold: 1 }
    );

    observer.observe(observerRef.current);
    return () => observer.disconnect();
  }, [nextCursor]);

  /* ===============================
     OPEN MODAL
  =============================== */
  const openPost = (post: Post) => {
    setSelectedPost({
      ...post,
      images: post.mediaUrls,
      likes: post.likeCount,
      username: post.userFullName,
      avatar: post.userAvatar,
    });
    setIsOpenPost(true);
  };

  /* ===============================
     RENDER
  =============================== */
  if (authLoading || loading) return <div>Loading...</div>;
  if (posts.length === 0) return <div>No tagged posts</div>;

  return (
    <>
      <div className={styles.list}>
        {[...posts].reverse().map((p) => (
          <ProfilePostTag
            key={p.shareId}
            image={p.mediaUrls[0]}
            caption={p.caption}
            captionShare={p.captionShare}
            userShareFullName={p.userShareFullName}
            likes={p.likeCount}
            time={new Date(p.createdAt).toLocaleDateString()}
            onClick={() => openPost(p)}
          />
        ))}
      </div>

      {nextCursor && <div ref={observerRef} style={{ height: 1 }} />}
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
