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
  const [userId, setUserId] = useState("");
  const [username, setUserName] = useState("");
  const [realPageId, setRealPageId] = useState("");
  const [me, setMe] = useState<any>(null);
  const [count, setCount] = useState<number>(0);

  useEffect(() => {
    const fetchUserId = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/cafe-owners/${pageId}`
        );

        setUserId(res.data.data.userId);
        setUserName(res.data.data.userName);
      } catch (err: any) {
        console.error("API error:", err.response?.status, err.message);
      }
    };

    fetchUserId();
  }, [pageId]);

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
  }, [user?.id, token]);

  useEffect(() => {
    const fetchPage = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/pages/cafe-owner/${pageId}`
        );
        setRealPageId(res.data.data.pageId);
      } catch (err: any) {
        console.error("API error:", err.response?.status, err.message);
      }
    };
    fetchPage();
  }, [pageId]);

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/blogs/page/${realPageId}`
        );
        console.log("ressss", res);

        setCount(res.data?.data?.totalElements);
      } catch (err) {
        console.error("Failed to fetch posts:", err);
      }
    };

    fetchPosts();
  }, [realPageId]);

  if (loading) return null;

  return (
    <ProfileHeader
      cfOwnerName={username}
      userId={userId}
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
      address={me?.location}
      backgroundImg={me?.coverUrl}
    />
  );
}
