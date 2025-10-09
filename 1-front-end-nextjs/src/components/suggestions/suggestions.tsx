import React from "react";
import "./suggestions.css";
import Image from "next/image";

export function Suggestions() {
  const users = [
    { name: "imkir", followers: "2.5M", image: "/testPost.jpg" },
    { name: "organic__al", followers: "2M", image: "/testPost.jpg" },
    { name: "im_gr", followers: "1.4M", image: "/testPost.jpg" },
    { name: "abh952", followers: "1M", image: "/testPost.jpg" },
    { name: "sakbrl", followers: "209.2k", image: "/testPost.jpg" },
  ];

  return (
    <div className="suggestions">
      <div className="suggestions-header">
        <span className="title">Suggestions for you</span>
        <a href="#" className="see-all">
          See All
        </a>
      </div>

      <ul className="user-list">
        {users.map((user, index) => (
          <li key={index} className="user-item">
            <div className="user-left">
              <Image
                src={user.image}
                alt="avatar"
                width={30}
                height={30}
                className="avatar-suggest"
              />
              <div>
                <div className="username-suggest">{user.name}</div>
                <div className="followers">{user.followers} follows</div>
              </div>
            </div>
            <button className="follow-btn">Follow</button>
          </li>
        ))}
      </ul>
    </div>
  );
}
