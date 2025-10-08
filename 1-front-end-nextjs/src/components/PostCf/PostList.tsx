"use client";
import { Post } from "./post";

export function PostList() {
  const posts = [
    {
      username: "gundam_among_us",
      avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
      image:
        "/testPost.jpg",
      likes: 42,
      caption: "HG Destiny Fighter Spec II & Zeus Silhouette...",
      time: "3d",
    },
    {
      username: "mecha_builds",
      avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
      image:
        "/testPost.jpg",
      likes: 87,
      caption: "Lorem ipsum dolor, sit amet consectetur adipisicing elit. Molestias iure consequuntur blanditiis mollitia inventore praesentium adipisci eligendi saepe laudantium ut dolor ducimus, iusto magnam doloribus. Animi nisi optio quas placeat.",
      time: "1d",
    },
    {
      username: "model_kit_fan",
      avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
      image:
        "/testPost2.jpg",
      likes: 103,
      caption: "Finally completed my RX-78 build 😍 Finally completed my RX-78 build 😍 Finally completed my RX-78 build 😍 Finally completed my RX-78 build 😍 Finally completed my RX-78 build 😍 Finally completed my RX-78 build 😍Finally completed my RX-78 build 😍Finally completed my RX-78 build 😍 Finally completed my RX-78 build 😍Finally completed my RX-78 build 😍",
      time: "5h",
    },
  ];

  return (
    <div className="post-list">
      {posts.map((p, index) => (
        <Post key={index} {...p} />
      ))}
    </div>
  );
}
