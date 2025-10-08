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
  Coffee,
} from "lucide-react";
import { ModeToggle } from "@/components/dark-theme-btn";
import "./sidebar.scss";

export function Sidebar() {
  return (
    <div className="sidebar">
      <div className="sidebar__top">
        <h2 className="sidebar__logo">CafeBlog</h2>
        <span className="logoCon">
          <Coffee />
        </span>
        <ul className="sidebar__menu">
          <li>
            <Home size={22} /> <span className="sidebarComp">Home</span>
          </li>
          <li>
            <Search size={22} /> <span className="sidebarComp">Search</span>
          </li>
          <li>
            <Compass size={22} /> <span className="sidebarComp">Explore</span>
          </li>
          <li>
            <Film size={22} /> <span className="sidebarComp">Reels</span>
          </li>
          <li>
            <MessageCircle size={22} />{" "}
            <span className="sidebarComp">Messages</span>
          </li>
          <li>
            <Heart size={22} />{" "}
            <span className="sidebarComp">Notifications</span>
          </li>
          <li>
            <PlusSquare size={22} /> <span className="sidebarComp">Create</span>
          </li>
          <li>
            <User size={22} /> <span className="sidebarComp">Profile</span>
          </li>
        </ul>
      </div>

      <div className="sidebar__bottom">
        <li>
          <ModeToggle />
        </li>
        <li>
          <Menu size={22} /> <span className="sidebarComp">More</span>
        </li>
      </div>
    </div>
  );
}
