"use client";

import React, { useEffect, useState } from "react";
import { MessageItem } from "./msg";
import styles from "./msg.module.scss";
import axios from "axios";
import { useAuth } from "@/context/AuthContext";
import { useRouter } from "next/navigation";
import { useMessageSearch } from "@/context/MessageSearchContext";
// ----- TYPES -----
interface ChatApi {
  chatId: string;
  members: string[];
  lastMessage: string;
  lastMessageSender: string;
  updatedAt: {
    _seconds: number;
    _nanoseconds: number;
  };
  createdAt: {
    _seconds: number;
    _nanoseconds: number;
  };
  isGroup: boolean;
  groupName?: string;
}

interface MessageUI {
  id: string;
  name: string;
  avatar: string;
  lastMessage: string;
  time: string;
  you: boolean;
}

// ----- COMPONENT -----
export function MessageList() {
  const [messages, setMessages] = useState<MessageUI[]>([]);
  const { token, user } = useAuth();
  const router = useRouter();
  const { keyword } = useMessageSearch();
  const currentUserId = user?.id;

  const handleClick = (chatId: string) => {
    router.push(`/messages/${chatId}`);
  };

  useEffect(() => {
    const fetchChats = async () => {
      if (!token || !currentUserId) return;

      try {
        const res = await axios.get<ChatApi[]>(
          "http://localhost:8081/chat/list",
          {
            headers: { Authorization: `Bearer ${token}` },
          }
        );

        const chats = res.data.filter((c) => c.lastMessage?.trim() !== "");

        const formatted = await Promise.all(
          chats.map(async (chat) => {
            // lấy user còn lại
            const otherId = chat.members.find((id) => id !== currentUserId);

            let userInfo = {
              name: "Người dùng",
              avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
            };

            // gọi API user info
            if (otherId) {
              try {
                const userRes = await axios.get(
                  `http://localhost:8080/users/${otherId}`,
                  {
                    headers: {
                      Authorization: `Bearer ${token}`,
                      "Content-Type": "application/json",
                    },
                  }
                );

                userInfo = {
                  name: userRes.data.data.fullName || "Người dùng",
                  avatar:
                    userRes.data.data.avatar ||
                    "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
                };
              } catch (err) {
                console.log("User fetch failed");
              }
            }

            return {
              id: chat.chatId,
              name: chat.groupName || userInfo.name,
              avatar: userInfo.avatar,
              lastMessage: chat.lastMessage,
              time: convertTimestamp(chat.updatedAt._seconds),
              you: chat.lastMessageSender === currentUserId,
            };
          })
        );

        setMessages(formatted);
      } catch (error: any) {
        console.error(
          "Error fetching messages:",
          error.response?.data || error.message
        );
      }
    };

    fetchChats();
  }, [token, currentUserId]);
  const filteredMessages = messages.filter(
    (msg) =>
      msg.name.toLowerCase().includes(keyword.toLowerCase()) ||
      msg.lastMessage.toLowerCase().includes(keyword.toLowerCase())
  );

  return (
    <div className={styles.wrapper}>
      <div className={styles.list}>
        {filteredMessages.map((msg) => (
          <MessageItem
            key={msg.id}
            {...msg}
            onClick={() => handleClick(msg.id)}
          />
        ))}

        {filteredMessages.length === 0 && (
          <div className={styles.empty}>Không tìm thấy</div>
        )}
      </div>
    </div>
  );
}

// ----- TIME CONVERTER -----
function convertTimestamp(seconds: number): string {
  const now = Math.floor(Date.now() / 1000);
  const diff = now - seconds;

  if (diff < 60) return `${diff}giây`;
  if (diff < 3600) return `${Math.floor(diff / 60)}phút`;
  if (diff < 86400) return `${Math.floor(diff / 3600)}giờ`;
  return `${Math.floor(diff / 86400)}ngày`;
}
