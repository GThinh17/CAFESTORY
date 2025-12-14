"use client";
import Image from "next/image";
import { useEffect, useState } from "react";
import { Button } from "../ui/button";
import { ChevronLeft, ChevronRight } from "lucide-react";
import axios from "axios";
import { useRouter } from "next/navigation";
import "./onlineAvt.css";
import { useAuth } from "@/context/AuthContext";

interface Reviewer {
  id: string;
  userId: string;
  userName: string;
  userAvatarUrl: string;
  type: "USER" | "PAGE";
}

export function OnlineAvt() {
  const router = useRouter();
  const itemsPerPage = 6;
  const [page, setPage] = useState(0);
  const [reviewers, setReviewers] = useState<Reviewer[]>([]);
  const { user, token } = useAuth();

  // ---- CALL API ----
  useEffect(() => {
    if (!user?.id || !token) return;

    async function fetchFollowings() {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/follows/users/${user.id}/following`,
          {
            headers: { Authorization: `Bearer ${token}` },
          }
        );

        const mappedData: Reviewer[] = res.data.data.map((item: any) => {
          if (item.followType === "USER") {
            return {
              id: item.followId,
              userId: item.userFollowedId,
              userName: item.userFollowedFullName,
              userAvatarUrl: item.userFollowedAvatar,
              type: "USER",
            };
          }

          return {
            id: item.followId,
            userId: item.pageFollowedId, // pageId
            userName: item.pageFollowedName,
            userAvatarUrl: item.pageFollowedAvatar,
            type: "PAGE",
          };
        });

        setReviewers(mappedData);
      } catch (error) {
        console.error("Fetch followings failed:", error);
      }
    }

    fetchFollowings();
  }, [user?.id, token]);

  // ---- HANDLE CLICK ----
  const handleClick = async (item: Reviewer) => {
    // USER → profile
    if (item.type === "USER") {
      router.push(`/profile/${item.userId}`);
      return;
    }

    // PAGE → fetch cafeOwnerId → /cafe/{id}
    try {
      const res = await axios.get(
        `http://localhost:8080/api/pages/cafe-owner`,
        {
          params: { pageId: item.userId },
          headers: { Authorization: `Bearer ${token}` },
        }
      );

      const cafeOwnerId = res.data;
      router.push(`/cafe/${cafeOwnerId}`);
    } catch (error) {
      console.error("Fetch cafe owner failed:", error);
    }
  };

  // ---- PAGINATION ----
  const totalPages = Math.ceil(reviewers.length / itemsPerPage);
  const startIndex = page * itemsPerPage;
  const endIndex = startIndex + itemsPerPage;
  const visibleUsers = reviewers.slice(startIndex, endIndex);

  return (
    <div className="onlineAvtWrapper">
      {page > 0 && (
        <Button className="scrollBtn left" onClick={() => setPage(page - 1)}>
          <ChevronLeft size={22} />
        </Button>
      )}

      <div className="container">
        {visibleUsers.map((item) => (
          <div
            key={item.id}
            className="story"
            onClick={() => handleClick(item)}
          >
            <div className="avatarWrapper">
              <Image
                src={
                  item.userAvatarUrl ||
                  "https://cdn-icons-png.flaticon.com/512/9131/9131529.png"
                }
                alt={item.userName}
                width={70}
                height={70}
                className="avatar"
              />
            </div>
            <span className="username">{item.userName}</span>
          </div>
        ))}
      </div>

      {page < totalPages - 1 && (
        <Button className="scrollBtn right" onClick={() => setPage(page + 1)}>
          <ChevronRight size={22} />
        </Button>
      )}
    </div>
  );
}
