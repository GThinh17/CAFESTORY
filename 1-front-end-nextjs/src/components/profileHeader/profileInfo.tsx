"use client";
import { useParams } from "next/navigation";
import React, { useEffect, useState } from "react";
import { ProfileHeader } from "./profileHeader";
import { useAuth } from "@/context/AuthContext";
import axios from "axios";

export function ProfileInfo() {
  const { userId } = useParams(); // <-- đây là userId từ URL
  const { user, loading } = useAuth();
  const [me, setMe] = useState<any>(null);
  const [count, setCount] = useState<number>(0);
  const [cfOwnerId, setCfOwnerId] = useState("");
  const [cfName, setCfName] = useState("");
  console.log(userId);

  useEffect(() => {
    const fetchCfOwnerId = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/cafe-owners/user/${userId}`
        );
        setCfOwnerId(res.data.data.id);
      } catch (err) {
        console.log("fetch status fail");
      }
    };
    fetchCfOwnerId();
  }, [userId]);

  useEffect(() => {
    const fetchCfName = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/pages/cafe-owner/${cfOwnerId}`
        );
        setCfName(res.data.data.pageName);
      } catch (err) {
        console.log("fetch status fail");
      }
    };
    fetchCfName();
  }, [cfOwnerId]);

  useEffect(() => {
    const fetchMe = async () => {
      try {
        const res = await axios.get(`http://localhost:8080/users/${userId}`);
        setMe(res.data.data);
      } catch (err: any) {
        console.error("API error:", err.response?.status, err.message);
      }
    };

    fetchMe();
  }, [userId]);

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/blogs?userId=${userId}`
        );

        const posts = res.data?.data?.data ?? [];

        // 🔥 Lọc các post có pageId === null
        const filteredPosts = posts.filter((p: any) => p.pageId === null);

        setCount(filteredPosts.length);
      } catch (err) {
        console.error("Failed to fetch posts:", err);
      }
    };

    fetchPosts();
  }, [userId]);

  if (loading) return null;

  return (
    <ProfileHeader
      pageName={cfName}
      cfOwnerId={cfOwnerId}
      username={me?.fullName ?? "Unknown"}
      verified
      following={me?.isFollowing ?? false}
      posts={count}
      followers={me?.followerCount ?? 0}
      followingCount={me?.followingCount}
      avatar={
        me?.avatar ?? "https://cdn-icons-png.flaticon.com/512/9131/9131529.png"
      }
      isMe={userId === user?.id}
      currentUserId={user?.id}
      profileUserId={userId}
    />
  );
}
