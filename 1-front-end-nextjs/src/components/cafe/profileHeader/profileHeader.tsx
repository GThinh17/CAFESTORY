"use client";

import Image from "next/image";
import styles from "./profileHeader.module.css";
import Link from "next/link";
import { useState } from "react";
import { Pin, MessageCircle, Plus, Search } from "lucide-react";

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
}

export default function ProfileHeader({
  username,
  verified,
  following,
  description,
  address,
  posts,
  followingCount,
  avatar,
  isMe,
  currentUserId,
  profileUserId,
  backgroundImg,
}: ProfileHeaderProps) {
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
              src={avatar}
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
          </div>
        </div>

        {/* RIGHT: ACTION BUTTONS */}
        <div className={styles.rightSide}>
          {!isMe && (
            <>
              <button className={`${styles.btn} ${styles.msgBtn}`}>
                Follow
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
