"use client";

import { useState } from "react";
import { MessageCircle, X, Pencil } from "lucide-react";
import Image from "next/image";
import { MessageList } from "../msgList/msgList";
import { ChatHeader } from "../chatHeader/chatHeader";
import { ChatMessageList } from "../chatMessage/chatMsgList";
import "./msgModal.scss";

export function MsgModal() {
  const [open, setOpen] = useState(false);
  const [isList, setIsList] = useState(true);
  const [isChat, setIsChat] = useState(false);
  const messages = [
    {
      id: 1,
      name: "Phạm Thị Kiều Mị",
      avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
      status: "Active 6m ago",
    },
    {
      id: 2,
      name: "Thiên Hà",
      avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
      status: "You: in đồ lu bu lắm · 4w",
    },
    {
      id: 3,
      name: "Anh Duy",
      avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
      status: "You: :))) · 5w",
    },
    {
      id: 4,
      name: "ĐPP",
      avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
      status: "Active 3h ago",
    },
    {
      id: 5,
      name: "Nguyễn Duy Khánh",
      avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
      status: "You sent an attachment · 16w",
    },
    {
      id: 5,
      name: "Nguyễn Duy Khánh",
      avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
      status: "You sent an attachment · 16w",
    },
  ];

  return (
    <>
      {!open && (
        <button onClick={() => setOpen(!open)} className="msg-float-btn">
          <MessageCircle className="icon" />
          <span className="btn-title">Messages</span>
        </button>
      )}

      {open && (
        <div className="msg-modal">
          <div className="msg-header">
            <h3 className="msg-title">Messages</h3>
            <button onClick={() => setOpen(false)} className="msg-close">
              <X className="icon" />
            </button>
          </div>

          {isList && (
            <div className="msg-list">
              <MessageList />
            </div>
          )}
          {isChat && (
            <div className="msg-list">
              <ChatHeader />
              <ChatMessageList />
            </div>
          )}

          <button className="msg-compose">
            <Pencil className="icon" />
          </button>
        </div>
      )}
    </>
  );
}
