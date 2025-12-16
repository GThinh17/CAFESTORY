"use client";

import React, { useEffect, useState } from "react";
import styles from "./suggestions.module.css";
import Image from "next/image";
import axios from "axios";
import { useRouter, usePathname } from "next/navigation";
import { useAuth } from "@/context/AuthContext";
import { Input } from "../../ui/input";
import { FileText, User } from "lucide-react";
import Link from "next/link";

type TabType = "page" | "reviewer";

export function SuggestionsExplore() {
  const router = useRouter();
  const pathname = usePathname();
  const { token } = useAuth();

  const [activeTab, setActiveTab] = useState<TabType>("page");
  const [search, setSearch] = useState("");

  const [pages, setPages] = useState<any[]>([]);
  const [reviewers, setReviewers] = useState<any[]>([]);

  /* ================= PAGE ================= */
  useEffect(() => {
    async function fetchPages() {
      try {
        const res = await axios.get(
          "http://localhost:8080/api/pages/top-followers"
        );
        setPages(res.data?.data ?? []);
      } catch (err) {
        console.error("Fetch pages failed:", err);
      }
    }

    fetchPages();
  }, []);

  /* ================= REVIEWER ================= */
  useEffect(() => {
    async function fetchReviewers() {
      if (!token) return;

      try {
        const res = await axios.get(
          "http://localhost:8080/api/reviewers/top/follower-desc",
          {
            headers: { Authorization: `Bearer ${token}` },
          }
        );

        const reviewersWithFollowers = await Promise.all(
          res.data.data.map(async (reviewer: any) => {
            try {
              const userRes = await axios.get(
                `http://localhost:8080/users/${reviewer.userId}`,
                {
                  headers: { Authorization: `Bearer ${token}` },
                }
              );

              return {
                ...reviewer,
                followerCount: userRes.data.data.followerCount ?? 0,
              };
            } catch {
              return { ...reviewer, followerCount: 0 };
            }
          })
        );

        setReviewers(reviewersWithFollowers);
      } catch (err) {
        console.error("Fetch reviewers failed:", err);
      }
    }

    fetchReviewers();
  }, [token]);

  /* ================= FILTER ================= */
  const filteredPages = pages.filter((p) =>
    p.pageName?.toLowerCase().includes(search.toLowerCase())
  );

  const filteredReviewers = reviewers.filter((r) =>
    r.userName?.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className={styles.suggestions}>
      {/* 🔍 Search + Tabs */}
      <div className={styles.topInput}>
        <Input
          placeholder={
            activeTab === "page" ? "Search page cafe..." : "Search reviewers..."
          }
          onChange={(e) => setSearch(e.target.value)}
        />
        <Link
          href="/explore/post"
          className={`${styles.exploreBtn} ${
            pathname === "/explore/post" ? styles.active : ""
          }`}
          title="Explore posts"
        >
          <FileText size={18} />
        </Link>

        <Link
          href="/explore/page"
          className={`${styles.exploreBtn} ${
            pathname === "/explore/page" ? styles.active : ""
          }`}
          title="Explore pages"
        >
          <User size={18} />
        </Link>
      </div>
      <div className={styles.ButtonTabsCon}>
        <button
          className={`${styles.Btn} ${
            activeTab === "page" ? styles.active1 : ""
          }`}
          onClick={() => {
            setActiveTab("page");
            setSearch("");
          }}
        >
          Page
        </button>{" "}
        <button
          className={`${styles.Btn} ${
            activeTab === "reviewer" ? styles.active1 : ""
          }`}
          onClick={() => {
            setActiveTab("reviewer");
            setSearch("");
          }}
        >
          Reviewer
        </button>
      </div>

      <ul className="user-list">
        {activeTab === "page" &&
          (filteredPages.length > 0
            ? filteredPages.map((page: any) => (
                <li
                  key={page.pageId}
                  className="user-item"
                  onClick={() => router.push(`/cafe/${page.cafeOwnerId}`)}
                >
                  <div className="user-left">
                    <Image
                      src={page.avatarUrl || "/default-avatar.png"}
                      alt="avatar"
                      width={30}
                      height={30}
                      className="avatar-suggest"
                    />
                    <div>
                      <div className="username-suggest">{page.pageName}</div>
                      <div className="followers">
                        {page.followingCount ?? 0} followers
                      </div>
                    </div>
                  </div>
                  <button className="follow-btn">Follow</button>
                </li>
              ))
            : search.trim() && <li className="not-found">No page found</li>)}

        {activeTab === "reviewer" &&
          (filteredReviewers.length > 0
            ? filteredReviewers.map((user: any) => (
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
                      className={styles.avatar}
                    />
                    <div>
                      <div className="username-suggest">{user.userName}</div>
                      <div className="followers">
                        {user.followerCount} followers
                      </div>
                    </div>
                  </div>
                  <button className="follow-btn">Follow</button>
                </li>
              ))
            : search.trim() && (
                <li className="not-found">No reviewer found</li>
              ))}
      </ul>
    </div>
  );
}
