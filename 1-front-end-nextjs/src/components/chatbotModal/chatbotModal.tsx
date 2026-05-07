"use client";

import { useState } from "react";
import { X, Send, Sparkles } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card } from "@/components/ui/card";
import styles from "./chatbot.module.css";
import axios from "axios";

type Message = {
  role: "user" | "ai";
  text: string;
};

/* ================================
   PARSE MARKDOWN IMAGE
================================ */
const parseMessage = (text: string) => {
  const parts: Array<
    | { type: "text"; content: string }
    | { type: "image"; src: string; alt: string }
  > = [];

  const regex = /!\[(.*?)\]\((.*?)\)/g;
  let lastIndex = 0;
  let match;

  while ((match = regex.exec(text)) !== null) {
    // text trước ảnh
    if (match.index > lastIndex) {
      parts.push({
        type: "text",
        content: text.slice(lastIndex, match.index),
      });
    }

    // ảnh
    parts.push({
      type: "image",
      src: match[2],
      alt: match[1] || "image",
    });

    lastIndex = regex.lastIndex;
  }

  // text còn lại
  if (lastIndex < text.length) {
    parts.push({
      type: "text",
      content: text.slice(lastIndex),
    });
  }

  return parts;
};

export default function ChatbotModalWidget() {
  const [open, setOpen] = useState(false);
  const [messages, setMessages] = useState<Message[]>([
    {
      role: "ai",
      text: "Xin chào! Tôi là trợ lý AI. Tôi có thể giúp bạn tìm quán cà phê, sản phẩm hoặc giá cả.",
    },
  ]);

  const [input, setInput] = useState("");
  const [threadId, setThreadId] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const sendMessage = async () => {
    if (!input.trim() || loading) return;

    const userMessage = input;

    setMessages((prev) => [...prev, { role: "user", text: userMessage }]);
    setInput("");
    setLoading(true);

    try {
      let currentThreadId = threadId;

      // Tạo thread lần đầu
      if (!currentThreadId) {
        const startRes = await axios.get("http://localhost:8082/start");
        currentThreadId = startRes.data.thread_id;
        setThreadId(currentThreadId);
      }

      // Gửi chat
      const chatRes = await axios.post("http://localhost:8082/chat-mongdb", {
        thread_id: currentThreadId,
        message: userMessage,
      });

      setMessages((prev) => [
        ...prev,
        {
          role: "ai",
          text: chatRes.data.response, // ⚠️ backend trả "response"
        },
      ]);
    } catch (err) {
      console.error("Chat error:", err);
      setMessages((prev) => [
        ...prev,
        { role: "ai", text: "❌ Có lỗi xảy ra, vui lòng thử lại." },
      ]);
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      {/* Floating Button */}
      {!open && (
        <button onClick={() => setOpen(true)} className={styles.floatingBtn}>
          <Sparkles className="text-white" />
          <span className={styles.notifyDot} />
        </button>
      )}

      {/* Chat Modal */}
      {open && (
        <Card className={styles.chatModal}>
          {/* Header */}
          <div className={styles.header}>
            <div className={styles.headerLeft}>
              <Sparkles />
              <div>
                <p className="font-semibold">Trợ lý ảo AI</p>
                <p className={styles.status}>● Online</p>
              </div>
            </div>
            <button onClick={() => setOpen(false)}>
              <X />
            </button>
          </div>

          {/* Messages */}
          <div className={styles.messages}>
            {messages.map((msg, i) => (
              <div
                key={i}
                className={`${styles.messageRow} ${
                  msg.role === "user" ? styles.messageRight : styles.messageLeft
                }`}
              >
                <div
                  className={`${styles.messageBubble} ${
                    msg.role === "user" ? styles.rightBubble : styles.leftBubble
                  }`}
                >
                  {parseMessage(msg.text).map((part, idx) =>
                    part.type === "text" ? (
                      <p key={idx} className={styles.messageText}>
                        {part.content}
                      </p>
                    ) : (
                      <img
                        key={idx}
                        src={part.src}
                        alt={part.alt}
                        className={styles.chatImage}
                      />
                    )
                  )}
                </div>
              </div>
            ))}
          </div>

          {/* Input */}
          <div className={styles.inputArea}>
            <Input
              placeholder={
                loading ? "AI đang trả lời..." : "Hỏi về quán cà phê, giá..."
              }
              value={input}
              disabled={loading}
              onChange={(e) => setInput(e.target.value)}
              onKeyDown={(e) => e.key === "Enter" && sendMessage()}
            />
            <Button size="icon" onClick={sendMessage} disabled={loading}>
              <Send className="w-4 h-4" />
            </Button>
          </div>
        </Card>
      )}
    </>
  );
}
