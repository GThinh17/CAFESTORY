"use client";

import React, { useEffect, useState } from "react";
import { MessageItem } from "./msg";
import styles from "./msg.module.scss";
import axios from "axios";
import { useAuth } from "@/context/AuthContext";
import { useRouter } from "next/navigation";

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
  const { token } = useAuth();
  const router = useRouter();

  const handleClick = (chatId: string) => {
    router.push(`/messages/${chatId}`);
  };
  useEffect(() => {
    const fetchChats = async () => {
      if (!token) return;

      try {
        const res = await axios.get<ChatApi[]>(
          "http://localhost:8081/chat/list",
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
            timeout: 5000, // tránh treo request
          }
        );

        console.log("res:", res.data);

        const formatted: MessageUI[] = res.data
          .filter((chat) => chat.lastMessage?.trim() !== "")
          .map((chat) => ({
            id: chat.chatId,
            name: chat.groupName || "Người dùng",
            avatar: "/testPost.jpg",
            lastMessage: chat.lastMessage,
            time: convertTimestamp(chat.updatedAt._seconds),
            you: chat.lastMessageSender === "you",
          }));

        setMessages(formatted);
      } catch (error: any) {
        console.error(
          "Error fetching messages:",
          error.response?.data || error.message
        );
      }
    };

    fetchChats();
  }, [token]);

  return (
    <div className={styles.wrapper}>
      <div className={styles.list}>
        {messages.map((msg) => (
          <MessageItem
            key={msg.id}
            {...msg}
            onClick={() => handleClick(msg.id)}
          />
        ))}
      </div>
    </div>
  );
}

// ----- TIME CONVERTER -----
function convertTimestamp(seconds: number): string {
  const now = Math.floor(Date.now() / 1000);
  const diff = now - seconds;

  if (diff < 60) return `${diff}s ago`;
  if (diff < 3600) return `${Math.floor(diff / 60)}m ago`;
  if (diff < 86400) return `${Math.floor(diff / 3600)}h ago`;
  return `${Math.floor(diff / 86400)}d ago`;
}
