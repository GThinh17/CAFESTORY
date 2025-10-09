import React from "react";
import {MessageItem} from "./msg";
import styles from "./msg.module.scss";

const messages = [
  {
    id: 1,
    name: "Phạm Thị Kiều Mị",
    avatar: "/testPost.jpg",
    lastMessage: "Active 1h ago",
    active: true,
    time: "1h ago",
    you: false,
  },
  {
    id: 2,
    name: "Thien Ha",
    avatar: "/testPost.jpg",
    lastMessage: "You: in đồ lu bu lắm",
    time: "4w",
    you: true,
  },
  {
    id: 3,
    name: "Anh Duy",
    avatar: "/testPost.jpg",
    lastMessage: "You: :)))",
    time: "5w",
    you: true,
  },
  {
    id: 4,
    name: "ĐPP",
    avatar: "/testPost.jpg",
    lastMessage: "Active 5h ago",
    active: true,
  },
  {
    id: 5,
    name: "Nguyễn Duy Khánh",
    avatar: "/testPost.jpg",
    lastMessage: "You sent an attachment.",
    time: "16w",
    you: true,
  },
  {
    id: 6,
    name: "Hai Dang",
    avatar: "/testPost.jpg",
    lastMessage: "Active 1h ago",
    active: true,
  },
];

export function MessageList() {
  return (
    <div className={styles.wrapper}>
      <h3 className={styles.title}>Messages</h3>
      <div className={styles.list}>
        {messages.map((msg, index) => (
          <MessageItem key={index} {...msg} />
        ))}
      </div>
    </div>
  );
}
