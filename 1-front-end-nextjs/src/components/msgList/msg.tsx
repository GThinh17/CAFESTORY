import React from "react";
import styles from "./msg.module.scss";
interface message {
  name: string;
  avatar: string;
  lastMessage?: string;
  time?: string;
  onClick?: () => void;
}

export function MessageItem({
  name,
  avatar,
  time,
  onClick,
  lastMessage,
}: message) {
  return (
    <div className={styles.item} onClick={onClick}>
      <img src={avatar} alt={name} className={styles.avatar} />
      <div className={styles.info}>
        <div className={styles.row}>
          <span className={styles.name}>{name}</span>
          {time && <span className={styles.time}>{time}</span>}
        </div>
        <p className={styles.preview}>{lastMessage}</p>
      </div>
    </div>
  );
}
