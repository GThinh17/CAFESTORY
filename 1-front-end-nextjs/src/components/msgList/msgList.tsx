"use client";

import React, { useEffect, useState, useMemo } from "react";
import { MessageItem } from "./msg";
import styles from "./msg.module.scss";
import axios from "axios";
import { useAuth } from "@/context/AuthContext";
import { useRouter } from "next/navigation";
import { useMessageSearch } from "@/context/MessageSearchContext";

// ---------- TYPES ----------
interface ChatApi {
  chatId: string;
  members: string[];
  lastMessage: string;
  lastMessageSender: string;
  updatedAt: {
    _seconds: number;
    _nanoseconds: number;
  };
  isGroup: boolean;
  groupName?: string;
}

interface MessageUI {
  id: string; // chatId OR userId (follow)
  otherUserId?: string;
  name: string;
  avatar: string;
  lastMessage: string;
  time: string;
  you: boolean;
  isFollow?: boolean;
}

interface FollowUser {
  userId: string;
  fullName: string;
  avatar: string;
}

// ---------- COMPONENT ----------
export function MessageList() {
  const { token, user } = useAuth();
  const router = useRouter();
  const { keyword } = useMessageSearch();
  const currentUserId = user?.id;

  const [messages, setMessages] = useState<MessageUI[]>([]);
  const [followings, setFollowings] = useState<FollowUser[]>([]);

  // ---------- OPEN CHAT ----------
  const openChat = (chatId: string) => {
    router.push(`/messages/${chatId}`);
  };

  // ---------- CREATE / OPEN CHAT ----------
  const handleCreateChat = async (profileUserId: string) => {
    if (!token || !currentUserId) return;

    try {
      const res = await axios.get("http://localhost:8081/chat/list", {
        headers: { Authorization: `Bearer ${token}` },
      });

      const existingChat = res.data.find(
        (chat: any) =>
          chat.members.includes(currentUserId) &&
          chat.members.includes(profileUserId)
      );

      if (existingChat) {
        router.push(`/messages/${existingChat.chatId}`);
        return;
      }

      const createRes = await axios.post(
        "http://localhost:8081/chat/createchat",
        { members: [currentUserId, profileUserId] },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );

      const chatId = createRes.data.chatId || createRes.data.id;
      router.push(`/messages/${chatId}`);
    } catch (err: any) {
      console.error("Create chat failed:", err.response?.data || err.message);
    }
  };

  // ---------- FETCH CHATS ----------
  useEffect(() => {
    const fetchChats = async () => {
      if (!token || !currentUserId) return;

      try {
        const res = await axios.get<ChatApi[]>(
          "http://localhost:8081/chat/list",
          { headers: { Authorization: `Bearer ${token}` } }
        );

        const chats = res.data.filter((c) => c.lastMessage?.trim());

        const formatted: MessageUI[] = await Promise.all(
          chats.map(async (chat) => {
            const otherId = chat.members.find((id) => id !== currentUserId);

            let userInfo = {
              name: "Người dùng",
              avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
            };

            if (otherId) {
              try {
                const userRes = await axios.get(
                  `http://localhost:8080/users/${otherId}`,
                  {
                    headers: { Authorization: `Bearer ${token}` },
                  }
                );

                userInfo = {
                  name: userRes.data.data.fullName,
                  avatar: userRes.data.data.avatar || userInfo.avatar,
                };
              } catch {}
            }

            return {
              id: chat.chatId,
              otherUserId: otherId,
              name: chat.groupName || userInfo.name,
              avatar: userInfo.avatar,
              lastMessage: chat.lastMessage,
              time: convertTimestamp(chat.updatedAt._seconds),
              you: chat.lastMessageSender === currentUserId,
            };
          })
        );

        setMessages(formatted);
      } catch (err) {
        console.error("Fetch chats failed");
      }
    };

    fetchChats();
  }, [token, currentUserId]);

  // ---------- FETCH FOLLOWINGS ----------
  useEffect(() => {
    const fetchFollowings = async () => {
      if (!token || !currentUserId) return;

      try {
        const res = await axios.get(
          `http://localhost:8080/api/follows/users/${currentUserId}/following`,
          { headers: { Authorization: `Bearer ${token}` } }
        );

        const mapped = res.data.data
          .filter((f: any) => f.followType === "USER")
          .map((f: any) => ({
            userId: f.userFollowedId,
            fullName: f.userFollowedFullName,
            avatar:
              f.userFollowedAvatar ||
              "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
          }));

        setFollowings(mapped);
      } catch (err) {
        console.error("Fetch followings failed");
      }
    };

    fetchFollowings();
  }, [token, currentUserId]);

  // ---------- MERGE LIST ----------
  const mergedList = useMemo(() => {
    const chattedUserIds = messages.map((m) => m.otherUserId);

    const followWithoutChat: MessageUI[] = followings
      .filter((f) => !chattedUserIds.includes(f.userId))
      .map((f) => ({
        id: f.userId,
        otherUserId: f.userId,
        name: f.fullName,
        avatar: f.avatar,
        lastMessage: "Bắt đầu trò chuyện",
        time: "",
        you: false,
        isFollow: true,
      }));

    return [...messages, ...followWithoutChat];
  }, [messages, followings]);

  // ---------- SEARCH ----------
  const filtered = mergedList.filter(
    (m) =>
      m.name.toLowerCase().includes(keyword.toLowerCase()) ||
      m.lastMessage.toLowerCase().includes(keyword.toLowerCase())
  );

  // ---------- RENDER ----------
  return (
    <div className={styles.wrapper}>
      <div className={styles.list}>
        {filtered.map((msg) => (
          <MessageItem
            key={msg.id}
            {...msg}
            onClick={() =>
              msg.isFollow
                ? handleCreateChat(msg.otherUserId!)
                : openChat(msg.id)
            }
          />
        ))}

        {filtered.length === 0 && (
          <div className={styles.empty}>Không tìm thấy</div>
        )}
      </div>
    </div>
  );
}

// ---------- TIME ----------
function convertTimestamp(seconds: number): string {
  const now = Math.floor(Date.now() / 1000);
  const diff = now - seconds;

  if (diff < 60) return `${diff}giây`;
  if (diff < 3600) return `${Math.floor(diff / 60)}phút`;
  if (diff < 86400) return `${Math.floor(diff / 3600)}giờ`;
  return `${Math.floor(diff / 86400)}ngày`;
}
