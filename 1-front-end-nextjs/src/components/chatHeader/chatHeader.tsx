"use client";
import styles from "./chatHeader.module.css";
import Image from "next/image";
import { Phone, Video, Info } from "lucide-react";

export function ChatHeader() {
  return (
    <div className={styles.header}>
      <div className={styles.left}>
        <Image
          src="/testPost.jpg"
          alt="avatar"
          width={42}
          height={42}
          className={styles.avatar}
        />

        <div className={styles.info}>
          <div className={styles.name}>ORDER ĐỒ NAM | KOREAN STYLE 👔</div>
          <div className={styles.username}>benice_menswear • Instagram</div>
        </div>
      </div>

      {/* Icons */}
      <div className={styles.right}>
        <Phone size={22}  />
        <Video size={28} />
        <Info size={23} />
      </div>
    </div>
  );
}
