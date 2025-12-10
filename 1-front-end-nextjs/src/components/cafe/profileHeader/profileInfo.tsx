"use client";
import { useParams } from "next/navigation";
import React, { useEffect, useState } from "react";
import ProfileHeader from "./profileHeader";
import { useAuth } from "@/context/AuthContext";
import axios from "axios";

export function ProfileInfo() {
  const { pageId } = useParams();
  const { user, loading, token } = useAuth();
  const [cfOwnerId, setCfOwnerId] = useState("");
  const [me, setMe] = useState<any>(null);
  const [count, setCount] = useState<number>(0);
  
  console.log(pageId);
  useEffect(() => {
    const fetchMe = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/pages/cafe-owner/${pageId}`
        );
        setMe(res.data.data);
      } catch (err: any) {
        console.error("API error:", err.response?.status, err.message);
      }
    };

    fetchMe();
  }, [pageId]);

  useEffect(() => {
    const fetchCfOwnerId = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/cafe-owners/user/${user?.id}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        );

        setCfOwnerId(res.data.data.id);
      } catch (err) {
        console.log("fetch status fail");
      }
    };

    fetchCfOwnerId();
  }, [open, user?.id, token]);

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/blogs?userpageId=${pageId}`
        );
        const posts = res.data?.data?.data ?? [];
        setCount(posts.length);
      } catch (err) {
        console.error("Failed to fetch posts:", err);
      }
    };

    fetchPosts();
  }, [me?.pageid, pageId]);

  if (loading) return null;

  return (
    <ProfileHeader
      username={me?.pageName ?? "Unknown"}
      following={me?.isFollowing ?? false}
      posts={count}
      followingCount={me?.followingCount}
      avatar={
        me?.avatarUrl ??
        "https://cdn-icons-png.flaticon.com/512/9131/9131529.png"
      }
      isMe={pageId === cfOwnerId}
      description={me?.description}
      address={me?.slug}
      backgroundImg={me?.coverUrl}
      currentUserpageId={cfOwnerId}
      profileUserpageId={pageId}
    />
  );
}
