"use client";

import { useState } from "react";
import { MessageCircle, X, Pencil } from "lucide-react";
import Image from "next/image";
import "./msgModal.scss";

export function MsgModal() {
  const [open, setOpen] = useState(false);

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

          <div className="msg-list">
            {messages.map((msg) => (
              <div key={msg.id} className="msg-item">
                <Image
                  src={msg.avatar}
                  alt={msg.name}
                  width={40}
                  height={40}
                  className="msg-avatar"
                />
                <div>
                  <p className="msg-name">{msg.name}</p>
                  <p className="msg-status">{msg.status}</p>
                </div>
              </div>
            ))}
          </div>

          <button className="msg-compose">
            <Pencil className="icon" />
          </button>
        </div>
      )}
    </>
  );
}
