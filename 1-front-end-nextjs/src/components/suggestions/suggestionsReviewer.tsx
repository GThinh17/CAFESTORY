"use client";

import React, { useEffect, useState } from "react";
import "./suggestionsReviewer.css";
import Image from "next/image";
import axios from "axios";
import { useRouter } from "next/navigation";

export function SuggestionsReviewers() {
  const router = useRouter();
  const [reviewers, setReviewers] = useState<Reviewer[]>([]);

  useEffect(() => {
    async function fetchReviewers() {
      try {
        const res = await axios.get("http://localhost:8080/api/reviewers");
        setReviewers(res.data.data);
      } catch (error) {
        console.error("Fetch reviewers failed:", error);
      }
    }

    fetchReviewers();
  }, []);

  console.log(reviewers);
  return (
    <div className="suggestions">
      <div className="suggestions-header">
        <span className="title">Top Reviewers</span>
        <a href="#" className="see-all">
          See All
        </a>
      </div>

      <ul className="user-list">
        {reviewers.map((user: any) => (
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
                <div className="followers">
                  {user.followerCount ?? 0} followers
                </div>
              </div>
            </div>

            <button className="follow-btn">Follow</button>
          </li>
        ))}
      </ul>
    </div>
  );
}
