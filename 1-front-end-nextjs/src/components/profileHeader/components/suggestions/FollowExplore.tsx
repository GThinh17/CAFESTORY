"use client";

import React, { useEffect, useState } from "react";
import styles from "./suggestions.module.css";
import Image from "next/image";
import axios from "axios";
import { useRouter } from "next/navigation";
import { useAuth } from "@/context/AuthContext";
import { Input } from "@/components/ui/input";
import { User } from "lucide-react";
import Link from "next/link";
import { useParams } from "next/navigation";

/* ================= TYPES ================= */
type TabType = "following" | "followers";
type FollowType = "USER" | "PAGE";

interface FollowItem {
  id: string; // userId hoặc pageId
  name: string;
  avatar: string | null;
  type: FollowType;
  cafeOwnerId?: string; // chỉ PAGE mới có
}

/* ================= COMPONENT ================= */
export function UserFollowExplore() {
  const router = useRouter();
  const { token, user } = useAuth();
  const params = useParams();
  const userId = params.userId as string;
  const [activeTab, setActiveTab] = useState<TabType>("following");
  const [items, setItems] = useState<FollowItem[]>([]);
  const [search, setSearch] = useState("");

  /* ================= FETCH FOLLOW DATA ================= */
  useEffect(() => {
    if (!token || !user?.id) return;

    async function fetchFollowList() {
      try {
        const url =
          activeTab === "following"
            ? `/api/follows/users/${userId}/following`
            : `/api/follows/users/${userId}/followers`;

        const res = await axios.get(`http://localhost:8080${url}`, {
          headers: { Authorization: `Bearer ${token}` },
        });

        /* ===== Map USER + PAGE ===== */
        const baseItems: FollowItem[] = res.data.data.map((item: any) => {
          if (item.followType === "USER") {
            return {
              id:
                activeTab === "following"
                  ? item.userFollowedId
                  : item.followerId,
              name:
                activeTab === "following"
                  ? item.userFollowedFullName
                  : item.followerFullName,
              avatar:
                activeTab === "following"
                  ? item.userFollowedAvatar
                  : item.followerAvatar,
              type: "USER",
            };
          }

          // PAGE
          return {
            id: item.pageFollowedId,
            name: item.pageFollowedName,
            avatar: item.pageFollowedAvatar,
            type: "PAGE",
          };
        });

        /* ===== Fetch cafeOwnerId cho PAGE ===== */
        const finalItems = await Promise.all(
          baseItems.map(async (item) => {
            if (item.type !== "PAGE") return item;

            try {
              const pageRes = await axios.get(
                `http://localhost:8080/api/pages/${item.id}`,
                { headers: { Authorization: `Bearer ${token}` } }
              );

              return {
                ...item,
                cafeOwnerId: pageRes.data.data.cafeOwnerId,
              };
            } catch (err) {
              console.error("Fetch page detail failed:", err);
              return item;
            }
          })
        );

        setItems(finalItems);
      } catch (err) {
        console.error("Fetch follow list failed:", err);
        setItems([]);
      }
    }

    fetchFollowList();
  }, [activeTab, token, user?.id]);

  /* ================= SEARCH FILTER ================= */
  const filteredItems = items.filter((i) =>
    i.name.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className={styles.suggestions}>
      {/* 🔍 Search */}
      <div className={styles.topInput}>
        <Input
          value={search}
          placeholder={
            activeTab === "following"
              ? "Tìm người hoặc quán cafe..."
              : "Tìm người theo dõi bạn..."
          }
          onChange={(e) => setSearch(e.target.value)}
        />

        <Link
          href={`/profile/${user?.id}`}
          className={styles.exploreBtn}
          title="Trang cá nhân"
        >
          <User size={18} />
        </Link>
      </div>

      {/* ================= Tabs ================= */}
      <div className={styles.ButtonTabsCon}>
        <button
          className={`${styles.Btn} ${styles.withDivider} ${
            activeTab === "following" ? styles.active1 : ""
          }`}
          onClick={() => {
            setActiveTab("following");
            setSearch("");
          }}
        >
          Đang theo dõi
        </button>

        <button
          className={`${styles.Btn} ${
            activeTab === "followers" ? styles.active1 : ""
          }`}
          onClick={() => {
            setActiveTab("followers");
            setSearch("");
          }}
        >
          Người theo dõi
        </button>
      </div>

      {/* ================= List ================= */}
      <ul className="user-list">
        {filteredItems.length > 0
          ? filteredItems.map((item) => (
              <li
                key={item.id}
                className="user-item"
                onClick={() => {
                  if (item.type === "USER") {
                    router.push(`/profile/${item.id}`);
                  } else if (item.cafeOwnerId) {
                    router.push(`/cafe/${item.cafeOwnerId}`);
                  }
                }}
              >
                <div className="user-left">
                  <Image
                    src={
                      item.avatar ||
                      "https://cdn-icons-png.flaticon.com/512/9131/9131529.png"
                    }
                    alt={item.name}
                    width={30}
                    height={30}
                    className="avatar-suggest"
                  />

                  <div className="username-suggest">
                    {item.name}
                    {item.type === "PAGE" && (
                      <span style={{ fontSize: 12, color: "#888" }}>
                        {" "}
                        • Quán cafe
                      </span>
                    )}
                  </div>
                </div>
              </li>
            ))
          : search.trim() && (
              <li className="not-found">Không tìm thấy kết quả</li>
            )}
      </ul>
    </div>
  );
}
