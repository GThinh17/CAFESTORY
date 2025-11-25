"use client";
import React from "react";
import {
  MessageList,
  Message,
  MessageInput,
  Avatar,
  MessageSeparator,
} from "@chatscope/chat-ui-kit-react";
import "./chatMsg.css";

export const ChatMessageList: React.FC = () => {
  const messages = [
    {
      id: 1,
      text: "C5555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr555555",
      time: "Mar 5, 2025, 3:40 PM",
      isOwn: false,
    },
    {
      id: 2,
      text: "5555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr555555",
      time: "Mar 5, 2025, 3:40 PM",
      isOwn: false,
    },
    {
      id: 3,
      text: "5555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr555555",
      time: "Mar 5, 2025, 3:40 PM",
      isOwn: true,
    },
  ];

  const groupedTime = "Mar 5, 2025, 3:40 PM"; // tạm thời gom nhóm theo giờ

  return (
    <div className="Con">
      <div className="messageCon">
        <MessageList>
          {/* ✅ Separator hiển thị thời gian ở giữa */}
          <div className="sep">
            <MessageSeparator content={groupedTime} />
          </div>
          {messages.map((msg) => (
            <Message
              key={msg.id}
              model={{
                message: msg.text,
                sentTime: msg.time,
                direction: msg.isOwn ? "outgoing" : "incoming",
                position: "single",
              }}
            >
            </Message>
          ))}
        </MessageList>
      </div>
      <div className="inputCon">
        <MessageInput placeholder="Type a message..." attachButton={false} />
      </div>
    </div>
  );
};
