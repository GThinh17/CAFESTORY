"use client";
import Image from "next/image";
import styles from "./profilePost.module.scss";

interface ProfilePostProps {
  image: string;
  caption: string;
  likes: number;
  time: string;
}

export function ProfilePost({ image, caption, likes, time }: ProfilePostProps) {
  return (
    <div className={styles.post}>
      <div className={styles.imageWrapper}>
        <Image
          src={image}
          alt="profile-post"
          width={400}
          height={400}
          className={styles.image}
        />
      </div>
      <div className={styles.info}>
        <p className={styles.caption}>{caption}</p>
        <div className={styles.details}>
          <span>{likes} likes</span>
          <span>•</span>
          <span>{time}</span>
        </div>
      </div>
    </div>
  );
}
