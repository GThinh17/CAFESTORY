"use client";

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
              <Link href="/messages">
                <button className={`${styles.btn} ${styles.messageBtn}`}>
                  Message
                </button>
              </Link>
            </>
          )}
        </div>
      </div>
      <ProfileModal open={isProfile} onClose={() => setIsProfile(false)} />
    </>
  );
}
