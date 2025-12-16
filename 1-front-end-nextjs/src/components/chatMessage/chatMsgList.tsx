"use client";
import React, { useEffect, useState } from "react";
import {
  MessageList,
  Message,
  MessageInput,
  MessageSeparator,
} from "@chatscope/chat-ui-kit-react";

import axios from "axios";
import { useAuth } from "@/context/AuthContext";
import { useParams } from "next/navigation";
import "./chatMsg.css";

interface MessageApi {
  messageId?: string;
  senderId: string;
  content: string;
  type: string;
  mediaUrl: string | null;
  timestamp: any;
  seenBy: string[];
}

export const ChatMessageList: React.FC = () => {
  const [messages, setMessages] = useState<MessageApi[]>([]);
  const { user, token } = useAuth();

  const params = useParams();
  const chatId = params.chatId as string;

  // ---- FETCH MESSAGES ----
  useEffect(() => {
    const fetchMessages = async () => {
      try {
        const res = await axios.get(`http://localhost:8081/message/${chatId}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        setMessages(res.data);
      } catch (err: any) {
        console.error("Error sending message:", err);
        if (err.response) console.error("Server response:", err.response.data);
      }
    };

    // gọi ngay khi mở

    const interval = setInterval(() => {
      fetchMessages();
    }, 1000);
    return () => clearInterval(interval);
    // cleanup khi component unmount
  }, [chatId, token]);

  // ---- FORMAT TIME ----
  const formatTime = (t: any) => {
    // Nếu backend trả về kiểu Firestore: {_seconds: ...}
    if (t?._seconds) {
      return new Date(t._seconds * 1000).toLocaleString("en-US");
    }

    // Nếu backend trả về ISO string "2025-12-07T07:51:38.882Z"
    return new Date(t).toLocaleString("en-US");
  };

  // ---------------------------------------------------------
  // ✅ HÀM SEND MESSAGE
  // ---------------------------------------------------------
  const handleSend = async (text: string) => {
    if (!text.trim()) return;

    try {
      const res = await axios.post(
        "http://localhost:8081/message/send",
        {
          senderId: user?.id,
          chatId: chatId,
          content: text,
        },
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );

      const newMsg = res.data.data;

      // push message vào UI
      setMessages((prev) => [...prev, newMsg]);
    } catch (err: any) {
      console.error("Error sending message:", err);
      if (err.response) console.error("Server response:", err.response.data);
    }
  };

  return (
    <>
      {chatId && (
        <div className="Con">
          <div className="messageCon">
            <MessageList>
              {messages.length > 0 && (
                <div className="sep">
                  <MessageSeparator
                    content={formatTime(messages[0].timestamp)}
                  />
                </div>
              )}

              {messages.map((msg, i) => (
                <Message
                  key={i}
                  model={{
                    message: msg.content,
                    sentTime: formatTime(msg.timestamp),
                    direction:
                      msg.senderId === user?.id ? "outgoing" : "incoming",
                    position: "single",
                  }}
                />
              ))}
            </MessageList>
          </div>

          {/* INPUT SEND */}
          <div className="inputCon">
            <MessageInput
              placeholder="Type a message..."
              attachButton={false}
              onSend={handleSend}
            />
          </div>
        </div>
      )}
    </>
  );
};
