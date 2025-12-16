"use client";

import React, { useEffect, useState } from "react";
import styles from "./suggestions.module.css";
import Image from "next/image";
import axios from "axios";
import { useRouter, usePathname, useParams } from "next/navigation";
import { useAuth } from "@/context/AuthContext";
import { Input } from "@/components/ui/input";
import { FileText, User } from "lucide-react";
import Link from "next/link";

interface PageFollower {
  userId: string;
  userName: string;
  userAvatarUrl: string | null;
}

export function CfFollowExplore() {
  const router = useRouter();
  const pathname = usePathname();
  const params = useParams(); // 👈 lấy params
  const { token } = useAuth();

  const pageId = params?.pageId as string; // 👈 bf0e11bb-...

  const [followers, setFollowers] = useState<PageFollower[]>([]);
  const [search, setSearch] = useState("");

  /* ================= FETCH PAGE FOLLOWERS ================= */
  useEffect(() => {
    if (!token || !pageId) return;

    async function fetchPageFollowers() {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/follows/pages/${pageId}/followers`,
          {
            headers: { Authorization: `Bearer ${token}` },
          }
        );

        const mappedFollowers: PageFollower[] = res.data.data.map(
          (item: any) => ({
            userId: item.followerId,
            userName: item.followerFullName,
            userAvatarUrl: item.followerAvatar,
          })
        );

        setFollowers(mappedFollowers);
      } catch (err) {
        console.error("Fetch page followers failed:", err);
        setFollowers([]);
      }
    }

    fetchPageFollowers();
  }, [token, pageId]);

  /* ================= SEARCH FILTER ================= */
  const filteredFollowers = followers.filter((f) =>
    f.userName?.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className={styles.suggestions}>
      {/* 🔍 Search + Explore */}
      <div className={styles.topInput}>
        <Input
          value={search}
          placeholder="Search page followers..."
          onChange={(e) => setSearch(e.target.value)}
        />

        <Link
          href="/explore/post"
          className={`${styles.exploreBtn} ${
            pathname === "/explore/post" ? styles.active : ""
          }`}
        >
          <FileText size={18} />
        </Link>

        <Link
          href="/explore/page"
          className={`${styles.exploreBtn} ${
            pathname === "/explore/page" ? styles.active : ""
          }`}
        >
          <User size={18} />
        </Link>
      </div>

      {/* Followers list */}
      <ul className="user-list">
        {filteredFollowers.length > 0
          ? filteredFollowers.map((user) => (
              <li
                key={user.userId}
                className="user-item"
                onClick={() => router.push(`/profile/${user.userId}`)}
              >
                <div className="user-left">
                  <Image
                    src={
                      user.userAvatarUrl ||
                      "https://cdn-icons-png.flaticon.com/512/9131/9131529.png"
                    }
                    alt={user.userName}
                    width={30}
                    height={30}
                    className="avatar-suggest"
                  />
                  <div className="username-suggest">{user.userName}</div>
                </div>
              </li>
            ))
          : search.trim() && <li className="not-found">No follower found</li>}
      </ul>
    </div>
  );
}
