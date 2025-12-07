"use client";

import Image from "next/image";
import styles from "./profileHeader.module.css";
import Link from "next/link";
import { useState } from "react";
import { useRouter } from "next/navigation";
import { ProfileModal } from "./components/profileModal";
import { Check } from "lucide-react";
import axios from "axios";
import { useAuth } from "@/context/AuthContext";
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
  currentUserId: string;
  profileUserId: string;
}

export function ProfileHeader({
  username,
  verified,
  following,
  posts,
  followers,
  followingCount,
  avatar,
  isMe,
  currentUserId,
  profileUserId,
}: ProfileHeaderProps) {
  const router = useRouter();
  const { token } = useAuth();
  const [isProfile, setIsProfile] = useState(false);
  const [localFollow, setLocalFollow] = useState(following);

  async function handleFollow() {
    try {
      await axios.post(
        "http://localhost:8080/api/follows",
        {
          followerId: currentUserId,
          followType: "USER",
          followedUserId: profileUserId,
          followedPageId: null,
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

  async function handleUnfollow() {
    try {
      await axios.delete(
        `http://localhost:8080/api/follows/users/${currentUserId}/following/${profileUserId}`,
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

  async function handleCreateChat() {
    if (!currentUserId || !profileUserId) {
      console.error("Cannot create chat, missing user IDs", {
        currentUserId,
        profileUserId,
      });
      return;
    }
    if (!token) {
      console.error("Cannot create chat, missing token");
      return;
    }

    try {
      // 1️⃣ Lấy danh sách chat hiện có
      const res = await axios.get("http://localhost:8081/chat/list", {
        headers: { Authorization: `Bearer ${token}` },
      });

      const chats = res.data as any[];

      // 2️⃣ Tìm chat đã có giữa 2 user
      const existingChat = chats.find(
        (chat) =>
          chat.members.includes(currentUserId) &&
          chat.members.includes(profileUserId)
      );

      if (existingChat) {
        // Nếu đã có → chuyển tới chatId đó
        router.push(`/messages/${existingChat.chatId}`);
        return;
      }

      // 3️⃣ Nếu chưa có → tạo chat mới
      const createRes = await axios.post(
        "http://localhost:8081/chat/createchat",
        { members: [currentUserId, profileUserId] },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );

      const chatId = createRes.data?.id || createRes.data?.chatId;
      router.push(`/messages/${chatId}`);
    } catch (err: any) {
      console.error(
        "Error creating/opening chat:",
        err.response?.data || err.message
      );
    }
  }
  return (
    <>
      <div className={styles.profileHeader}>
        {/* Avatar */}
        <div className={styles.profileAvatar}>
          <Image
            src={avatar}
            alt={username}
            className={styles.avatarImg}
            width={150}
            height={150}
          />
        </div>

        {/* Info */}
        <div className={styles.profileInfo}>
          <div className={styles.profileTop}>
            <h5 className={styles.username}>{username}</h5>
            {verified && (
              <span className={styles.verified}>
                <Check />
              </span>
            )}

            {isMe && (
              <button
                className={`${styles.btn} ${styles.moreBtn}`}
                onClick={() => setIsProfile(true)}
              >
                ⋯
              </button>
            )}
          </div>

          <div className={styles.profileStats}>
            <span>
              <strong>{posts}</strong> posts
            </span>
            <span>
              <strong>{followers}</strong> followers
            </span>
            <span>
              <strong>{followingCount}</strong> following
            </span>
          </div>
          <div className={styles.profileBio}></div>
          {!isMe && (
            <>
              <button
                className={`${styles.btn} ${styles.followBtn}`}
                onClick={localFollow ? handleUnfollow : handleFollow}
              >
                {localFollow ? "Following" : "Follow"}
              </button>
              
                <button
                  className={`${styles.btn} ${styles.messageBtn}`}
                  onClick={handleCreateChat}
                >
                  Message
                </button>
              
            </>
          )}
        </div>
      </div>
      <ProfileModal open={isProfile} onClose={() => setIsProfile(false)} />
    </>
  );
}
