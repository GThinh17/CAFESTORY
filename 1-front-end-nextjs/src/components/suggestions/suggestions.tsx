import React from "react";
import "./suggestions.css";

export function Suggestions() {
  const users = [
    { name: "imkir", followers: "2.5M" },
    { name: "organic__al", followers: "2M" },
    { name: "im_gr", followers: "1.4M" },
    { name: "abh952", followers: "1M" },
    { name: "sakbrl", followers: "209.2k" },
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
              <div className="avatar-suggest"></div>
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
