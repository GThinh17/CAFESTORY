"use client";

import Image from "next/image";
import styles from "./profileHeader.module.css";
import Link from "next/link";
import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import { ProfileModal } from "./components/profileModal";
import { Check, MessageCircleWarningIcon } from "lucide-react";
import axios from "axios";
import { useAuth } from "@/context/AuthContext";
import { ProfileTabs } from "./components/profileTab/profileTabs";
import { ReportModal } from "./components/reportModal/reportModal";

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
  pageName: string;
  cfOwnerId: string;
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
  pageName,
  cfOwnerId,
}: ProfileHeaderProps) {
  const router = useRouter();
  const { token, user } = useAuth();
  const [isProfile, setIsProfile] = useState(false);
  const [localFollow, setLocalFollow] = useState(false);
  const [openReport, setOpenReport] = useState(false);

  useEffect(() => {
    if (!token || !currentUserId || !profileUserId) return;
    const fetchIsFollowed = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/follows/users/${currentUserId}/following/${profileUserId}`,
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
  }, [profileUserId]);

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
    <div>
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
              <strong>{posts}</strong> bài viết
            </span>
            <Link href={`/profile/${profileUserId}/followSearching`}>
              <span>
                <strong>{followers}</strong> Người theo dõi
              </span>
            </Link>
            <Link href={`/profile/${profileUserId}/followSearching`}>
              <span>
                <strong>{followingCount}</strong> Đang theo dõi
              </span>
            </Link>
          </div>
          <div className={styles.profileBio}>
            {cfOwnerId && pageName && (
              <Link href={`/cafe/${cfOwnerId}`} className={styles.owner}>
                Cafe page: {pageName}
              </Link>
            )}
          </div>
          {!isMe && (
            <>
              <button
                className={`${styles.btn} ${styles.followBtn}`}
                onClick={localFollow ? handleUnfollow : handleFollow}
              >
                {localFollow ? "Đang theo dõi" : "Theo dõi"}
              </button>

              <button
                className={`${styles.btn} ${styles.messageBtn}`}
                onClick={handleCreateChat}
              >
                Nhắn tin
              </button>
              <button
                onClick={() => setOpenReport(true)}
                className={`${styles.btn} ${styles.messageBtn}`}
              >
                <MessageCircleWarningIcon size={15} className="more-icon" />
              </button>
            </>
          )}
        </div>
      </div>{" "}
      <ProfileModal open={isProfile} onClose={() => setIsProfile(false)} />
      <div className={styles.tabWrapper}>
        <ProfileTabs userId={profileUserId} />
      </div>
      <ReportModal
        open={openReport}
        onClose={() => setOpenReport(false)}
        profileUserId={profileUserId}
      />
    </div>
  );
}
