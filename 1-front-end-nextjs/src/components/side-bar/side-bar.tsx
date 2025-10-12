"use client";
import React from "react";
import { usePathname } from "next/navigation";
import Link from "next/link";
import {
  Home,
  Search,
  Compass,
  MessageCircle,
  Heart,
  PlusSquare,
  User,
  Menu,
  Coffee,
  Crown,
} from "lucide-react";
import { ModeToggle } from "@/components/dark-theme-btn";
import "./sidebar.scss";

export function Sidebar() {
  const pathname = usePathname();

  const menuItems = [
    { href: "/", icon: Home, label: "Home" },
    { href: "/search", icon: Search, label: "Search" },
    { href: "/explore", icon: Compass, label: "Explore" },
    { href: "/messages", icon: MessageCircle, label: "Messages" },
    { href: "/notifications", icon: Heart, label: "Notifications" },
    { href: "/create", icon: PlusSquare, label: "Create" },
    { href: "/profile", icon: User, label: "Profile" },
  ];

  return (
    <div className="sidebar">
      <div className="sidebar__top">
        <h2 className="sidebar__logo">CafeBlog</h2>
        <span className="logoCon">
          <Coffee />
        </span>

        <ul className="sidebar__menu">
          {menuItems.map(({ href, icon: Icon, label }) => (
            <li key={href} className={pathname === href ? "" : ""}>
              <Link href={href} className="sidebar__link">
                <Icon
                  size={22}
                  fill={pathname === href ? "currentColor" : "none"}
                />
                <span className="sidebarComp">{label}</span>
              </Link>
            </li>
          ))}
        </ul>
      </div>

      <div className="sidebar__bottom">
        <li>
          <ModeToggle />
        </li>

        <li>
          <Menu size={22} /> <span className="sidebarComp">More</span>
        </li>
        <li>
          <Crown size={22} /> <span className="sidebarComp">Go pro</span>
        </li>
      </div>
    </div>
  );
}
