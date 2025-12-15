"use client";

import React, { useEffect, useState } from "react";
import "./suggestionsReviewer.css";
import Image from "next/image";
import axios from "axios";
import { useRouter } from "next/navigation";
import { useAuth } from "@/context/AuthContext";

export function SuggestionsReviewers() {
  const router = useRouter();
  const [reviewers, setReviewers] = useState<any[]>([]);
  const { token } = useAuth();

  useEffect(() => {
    async function fetchReviewers() {
      try {
        // 1️⃣ Lấy top reviewers
        const res = await axios.get(
          "http://localhost:8080/api/reviewers/top/follower-desc"
        );

        const reviewersData = res.data.data;

        // 2️⃣ Fetch followerCount theo userId
        const reviewersWithFollowers = await Promise.all(
          reviewersData.map(async (reviewer: any) => {
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
            } catch (err) {
              console.error("Fetch user failed:", err);
              return {
                ...reviewer,
                followerCount: 0,
              };
            }
          })
        );

        setReviewers(reviewersWithFollowers);
      } catch (error) {
        console.error("Fetch reviewers failed:", error);
      }
    }

    if (token) fetchReviewers();
  }, [token]);

  return (
    <div className="suggestions">
      <div className="suggestions-header">
        <span className="title">Top Reviewers</span>
        <a href="#" className="see-all">
          See All
        </a>
      </div>

      <ul className="user-list">
        {reviewers.map((user) => (
          <li
            key={user.userId}
            className="user-item"
            onClick={() => router.push(`/profile/${user.userId}`)}
            style={{ cursor: "pointer" }}
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
                className="avatar111"
              />

              <div>
                <div className="username-suggest">{user.userName}</div>
                <div className="followers">{user.followerCount} followers</div>
              </div>
            </div>

            <button className="follow-btn">Follow</button>
          </li>
        ))}
      </ul>
    </div>
  );
}
