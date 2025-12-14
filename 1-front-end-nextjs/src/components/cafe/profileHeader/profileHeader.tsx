"use client";

import Image from "next/image";
import styles from "./profileHeader.module.css";
import Link from "next/link";
import { useState, useEffect } from "react";
import { Pin, MessageCircle, Plus, Search } from "lucide-react";
import { useParams } from "next/navigation";
import { useAuth } from "@/context/AuthContext";
import axios from "axios";
interface ProfileHeaderProps {
  username: string;
  verified?: boolean;
  following: boolean;
  posts: number;
  followers: string;
  followingCount: number;
  name: string;
  bio: string;
  website?: string;
  avatar: string;
  isMe?: boolean;
  description?: string;
  address?: string;
  currentUserId: string;
  profileUserId: string;
  backgroundImg: string;
  userId: string;
  cfOwnerName: String;
}

export default function ProfileHeader({
  username,
  userId,
  cfOwnerName,
  verified,
  following,
  description,
  address,
  posts,
  followingCount,
  avatar,
  isMe,
  backgroundImg,
}: ProfileHeaderProps) {
  const [realPageId, setRealPageId] = useState();
  const { user, token } = useAuth();
  const [localFollow, setLocalFollow] = useState(false);
  const { pageId } = useParams();

  useEffect(() => {
    const fetchPage = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/pages/cafe-owner/${pageId}`
        );
        setRealPageId(res.data.data.pageId);

        console.log("firstssssssssssst", res);
      } catch (err: any) {
        console.error("API error:", err.response?.status, err.message);
      }
    };
    fetchPage();
  }, [pageId]);
  console.log(pageId);

  useEffect(() => {
    if (!token) return;
    const fetchIsFollowed = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/follows/users/${user?.id}/following-page/${realPageId}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        );
        setLocalFollow(res.data.data);
      } catch (err) {
        console.error("Failed to fetch status followed:", err);
      }
    };

    fetchIsFollowed();
  }, [realPageId]);

  async function handleFollow() {
    try {
      await axios.post(
        "http://localhost:8080/api/follows",
        {
          followerId: user?.id,
          followType: "PAGE",
          followedUserId: "",
          followedPageId: realPageId,
        },
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );

      setLocalFollow(true);
    } catch (err) {
      console.error("Follow error", err);
    }
  }

  console.log("user:", user);
  console.log("userId:", user?.id);
  console.log("realPageId:", realPageId);

  async function handleUnfollow() {
    try {
      await axios.delete(
        `http://localhost:8080/api/follows/users/${user?.id}/following-page/${realPageId}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );

      setLocalFollow(false);
    } catch (err) {
      console.error("Unfollow error", err);
    }
  }

  return (
    <div className={styles.wrapper}>
      {/* COVER IMAGE */}
      <div
        className={styles.cover}
        style={{
          backgroundImage: `url(${backgroundImg})`,
        }}
      ></div>

      {/* BELOW COVER */}
      <div className={styles.bottomSection}>
        {/* LEFT: Avatar + Info */}
        <div className={styles.leftSide}>
          <div className={styles.avatarWrapper}>
            <Image
              src={
                avatar ||
                "https://cdn-icons-png.flaticon.com/512/9131/9131529.png"
              }
              alt="avatar"
              width={170}
              height={170}
              className={styles.avatar}
            />
          </div>

          <div className={styles.pageInfo}>
            <h1 className={styles.pageName}>{username}</h1>
            <p className={styles.subInfo}>
              {posts} post • {followingCount} followers
            </p>

            {address && (
              <Link
                href={`https://www.google.com/maps/search/?api=1&query=${encodeURIComponent(
                  address
                )}`}
                target="_blank"
                className={styles.address}
              >
                <Pin size={16} />
                {address}
              </Link>
            )}

            {description && <p className={styles.description}>{description}</p>}
            {userId && cfOwnerName && (
              <Link href={`/profile/${userId}`} className={styles.owner}>
                Owner: {cfOwnerName}
              </Link>
            )}
          </div>
        </div>

        {/* RIGHT: ACTION BUTTONS */}
        <div className={styles.rightSide}>
          {!isMe && (
            <>
              <button
                className={`${styles.btn} ${styles.msgBtn}`}
                onClick={localFollow ? handleUnfollow : handleFollow}
              >
                {localFollow ? "Following" : "Follow"}
              </button>

              <button className={`${styles.btn} ${styles.followBtn}`}>
                Messages
              </button>
            </>
          )}
        </div>
      </div>
    </div>
  );
}
