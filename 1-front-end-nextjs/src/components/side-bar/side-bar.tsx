import React from "react";
import {
  Home,
  Search,
  Compass,
  Film,
  MessageCircle,
  Heart,
  PlusSquare,
  User,
  Menu,
} from "lucide-react";
import "./sidebar.css";

export function Sidebar() {
  return (
    <div className="sidebar">
      <div className="sidebar__top">
        <h2 className="sidebar__logo">CafeBlog</h2>
        <ul className="sidebar__menu">
          <li>
            <Home size={22} /> <span>Home</span>
          </li>
          <li>
            <Search size={22} /> <span>Search</span>
          </li>
          <li>
            <Compass size={22} /> <span>Explore</span>
          </li>
          <li>
            <Film size={22} /> <span>Reels</span>
          </li>
          <li>
            <MessageCircle size={22} /> <span>Messages</span>
          </li>
          <li>
            <Heart size={22} /> <span>Notifications</span>
          </li>
          <li>
            <PlusSquare size={22} /> <span>Create</span>
          </li>
          <li>
            <User size={22} /> <span>Profile</span>
          </li>
        </ul>
      </div>

      <div className="sidebar__bottom">
        <li>
          <Menu size={22} /> <span>More</span>
        </li>
      </div>
    </div>
  );
}
