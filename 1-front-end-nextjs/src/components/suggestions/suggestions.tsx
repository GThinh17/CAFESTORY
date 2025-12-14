"use client";

import React, { useEffect, useState } from "react";
import "./suggestions.css";
import Image from "next/image";
import axios from "axios";
import { useRouter } from "next/navigation";

export function Suggestions() {
  const router = useRouter();
  const [users, setUsers] = useState([]);

  useEffect(() => {
    async function fetchTopFollowers() {
      try {
        const res = await axios.get(
          "http://localhost:8080/api/pages/top-followers"
        );
        if (res.data?.data) {
          setUsers(res.data.data);
        }
      } catch (err) {
        console.error("API Error:", err);
      }
    }

    fetchTopFollowers();
  }, []);

  console.log(users);
  function goToCafe(cafeOwnerId: string) {
    router.push(`/cafe/${cafeOwnerId}`);
  }

  return (
    <div className="suggestions">
      <div className="suggestions-header">
        <span className="title">Suggestions for you</span>
        <a href="#" className="see-all">
          See All
        </a>
      </div>

      <ul className="user-list">
        {users.map((user: any) => (
          <li
            key={user.pageId}
            className="user-item"
            onClick={() => goToCafe(user.cafeOwnerId)}
            style={{ cursor: "pointer" }}
          >
            <div className="user-left">
              <Image
                src={user.avatarUrl || "/default-avatar.png"}
                alt="avatar"
                width={30}
                height={30}
                className="avatar-suggest"
              />

              <div>
                <div className="username-suggest">{user.pageName}</div>
                <div className="followers">
                  {user.followingCount ?? 0} followers
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
