"use client";
import Image from "next/image";
import styles from "./sharedPost.module.scss";

interface SharedPostProps {
  image: string;
  caption: string;
  captionShare: string;
  userShareFullName: string;
  likes: number;
  time: string;
  onClick?: () => void;
}

export function SharedPost({
  onClick,
  image,
  caption,
  captionShare,
  userShareFullName,
  likes,
  time,
}: SharedPostProps) {
  return (
    <div className={styles.post} onClick={onClick}>
      <div className={styles.imageWrapper}>
        <Image
          src={image}
          alt="shared-post"
          width={400}
          height={400}
          className={styles.image}
        />
      </div>

      <div className={styles.info}>
        <p className={styles.caption}>{caption}</p>

        <p className={styles.caption1}>
          <strong>{userShareFullName}</strong>: {captionShare}
        </p>

        <div className={styles.details}>
          <span>{likes} likes</span>
          <span>•</span>
          <span>{time}</span>
        </div>
      </div>
    </div>
  );
}
