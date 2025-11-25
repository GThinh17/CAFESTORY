"use client";
import { ProfilePost } from "./profilePost";
import styles from "./profilePost.module.scss";

export function ProfilePostList() {
  const posts = [
    {
      image: "/testPost.jpg",
      caption: "HG Destiny Fighter Spec II & Zeus Silhouette...",
      likes: 42,
      time: "3d",
    },
    {
      image: "/testPost.jpg",
      caption: "Lorem ipsum dolor sit amet consectetur adipisicing elit.",
      likes: 87,
      time: "1d",
    },
    {
      image: "/testPost2.jpg",
      caption: "Finally completed my RX-78 build 😍",
      likes: 103,
      time: "5h",
    },
    {
      image: "/testPost2.jpg",
      caption: "Finally completed my RX-78 build 😍",
      likes: 103,
      time: "5h",
    },
    {
      image: "/testPost2.jpg",
      caption: "Finally completed my RX-78 build 😍",
      likes: 103,
      time: "5h",
    },
    {
      image: "/testPost2.jpg",
      caption: "Finally completed my RX-78 bsssssssssssssssssssssssssssssssssssssssssssssssssssuild 😍",
      likes: 103,
      time: "5h",
    },
    {
      image: "/testPost2.jpg",
      caption: "Finally completed my RX-78 build 😍",
      likes: 103,
      time: "5h",
    },
  ];

  return (
    <div className={styles.list}>
      {posts.map((p, index) => (
        <ProfilePost key={index} {...p} />
      ))}
    </div>
  );
}
