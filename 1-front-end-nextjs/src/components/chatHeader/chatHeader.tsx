"use client";
import styles from "./chatHeader.module.css";
import Image from "next/image";
import { Phone, Video, Info } from "lucide-react";
import { useState, useEffect } from "react";
import { useParams } from "next/navigation";
import { useAuth } from "@/context/AuthContext";
import axios from "axios";

interface ChatInfo {
  chatId: string;
  members: string[];
  isGroup: boolean;
  groupName?: string;
}

interface UserInfo {
  id: string;
  username: string;
  avatar: string;
}

export function ChatHeader() {
  const { user, token } = useAuth();
  const params = useParams();
  const chatId = params.chatId as string;
  const [otherUser, setOtherUser] = useState<UserInfo | null>(null);
  
  // ---- FETCH CHAT INFO ----
  useEffect(() => {
    const fetchChatInfo = async () => {
      if (!token || !user) return;

      try {
        // Lấy info chat
        const chatRes = await axios.get<ChatInfo>(
          `http://localhost:8081/chat/list?chatId=${chatId}`,
          {
            headers: { Authorization: `Bearer ${token}` },
          }
        );

        const chat = chatRes.data[0];

        // Lấy user còn lại
        const otherId = chat.members.find((id) => id !== user.id);
        if (!otherId) return;

        // Lấy thông tin user còn lại
        const userRes = await axios.get<UserInfo>(
          `http://localhost:8080/users/${otherId}`,
          {
            headers: { Authorization: `Bearer ${token}` },
          }
        );

        setOtherUser(userRes.data.data);
      } catch (err) {
        console.error(err);
      }
    };

    fetchChatInfo();
  }, [chatId, token, user]);

  return (
    <>
      {chatId && (
        <div className={styles.header}>
          <div className={styles.left}>
            <Image
              src={
                otherUser?.avatar ||
                "https://cdn-icons-png.flaticon.com/512/9131/9131529.png"
              }
              alt="avatar"
              width={42}
              height={42}
              className={styles.avatar}
            />

            <div className={styles.info}>
              <div className={styles.name}>
                {otherUser?.fullName || "Loading..."}
              </div>
            </div>
          </div>

          {/* Icons */}
          <div className={styles.right}>
            <Phone size={22} />
            <Video size={28} />
            <Info size={23} />
          </div>
        </div>
      )}
    </>
  );
}
