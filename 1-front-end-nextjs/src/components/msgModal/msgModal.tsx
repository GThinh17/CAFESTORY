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

  return (
    <>
      {!open && (
        <button onClick={() => setOpen(!open)} className="msg-float-btn">
          <MessageCircle className="icon" />
          <span className="btn-title">Tin nhắn</span>
        </button>
      )}

      {open && (
        <div className="msg-modal">
          <div className="msg-header">
            <h3 className="msg-title">Tin nhắn</h3>
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
