"use client";

import React, { useEffect, useState } from "react";
import "./suggestions.css";
import Image from "next/image";
import axios from "axios";
import { useRouter } from "next/navigation";
import { useAuth } from "@/context/AuthContext";

export function Suggestions() {
  const router = useRouter();
  const [users, setUsers] = useState([]);
  const [userLocation, setUserLocation] = useState();
  const { user, token } = useAuth();

  useEffect(() => {
    async function fetchTopFollowers() {
      try {
        const res = await axios.get(`http://localhost:8080/users/${user?.id}`);
        if (res.data?.data) {
          setUserLocation(res.data.data.location);
        }
      } catch (err) {
        console.error("API Error:", err);
      }
    }

    fetchTopFollowers();
  }, [user?.id]);

  useEffect(() => {
    async function fetchTopFollowers() {
      if (!userLocation) {
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
      } else {
        try {
          const res = await axios.get(
            `http://localhost:8080/api/pages/search?location=${userLocation}`,
            { headers: { Authorization: `Bearer ${token}` } }
          );
          if (res.data?.data) {
            setUsers(res.data.data);
          }
        } catch (err) {
          console.error("API Error:", err);
        }
      }
    }

    fetchTopFollowers();
  }, [token, userLocation]);

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
