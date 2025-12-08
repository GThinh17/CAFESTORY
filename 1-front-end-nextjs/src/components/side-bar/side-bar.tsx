"use client";
import React, { useState } from "react";
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
import { NotificationModal } from "../Notification/components/notification-modal";
import { CreateModal } from "../createModal/createModal";
import { PricingPlans } from "../goProModal/goProModal";
import { useAuth } from "@/context/AuthContext";
import { useRouter } from "next/navigation";

import "./sidebar.scss";

export function Sidebar() {
  const pathname = usePathname();
  const { user, loading, token } = useAuth();
  const [isOpenNotice, setIsOpenNotice] = useState(false);
  const [isOpenCreate, setIsOpenCreate] = useState(false);
  const [isOpenPricing, setIsOpenPricing] = useState(false);

  const router = useRouter();

  const menuItems = [
    { href: "/", icon: Home, label: "Home" },
    { href: "/search", icon: Search, label: "Search" },
    { href: "/explore", icon: Compass, label: "Explore" },
    { href: "/messages", icon: MessageCircle, label: "Messages" },

    // ⚠️ Notifications sẽ handle click => modal
    { icon: Heart, label: "Notifications", modal: true },

    { icon: PlusSquare, label: "Create", modal: true },
    { href: `/profile/${user?.id}`, icon: User, label: "Profile" },
  ];

  return (
    <>
      {/* SIDEBAR */}
      <div className="sidebar">
        <div className="sidebar__top">
          <h2 className="sidebar__logo">
            <Link href="/">CafeBlog</Link>
          </h2>

          <span className="logoCon">
            <Coffee />
          </span>

          <ul className="sidebar__menu">
            {menuItems.map(({ href, icon: Icon, label, modal }) => (
              <li key={label}>
                {modal ? (
                  <button
                    className="sidebar__link"
                    onClick={(e) => {
                      e.preventDefault();
                      e.stopPropagation();
                      if (label === "Notifications") setIsOpenNotice(true);
                      if (label === "Create") {
                        if (!token) {

                          router.push("/login");
                          return;
                        }
                        setIsOpenCreate(true);
                      }

                      if (label === "Notifications") setIsOpenNotice(true);
                      if (label === "Create") setIsOpenCreate(true);
                    }}
                  >
                    <Icon size={22} />
                    <span className="sidebarComp">{label}</span>
                  </button>
                ) : (
                  <Link href={href!} className="sidebar__link">
                    <Icon
                      size={22}
                      fill={pathname === href ? "currentColor" : "none"}
                    />
                    <span className="sidebarComp">{label}</span>
                  </Link>
                )}
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
          <li onClick={() => setIsOpenPricing(true)}>
            <Crown size={22} /> <span className="sidebarComp">Go pro</span>
          </li>
        </div>
      </div>

      {/* ✅ MODAL */}
      <NotificationModal
        open={isOpenNotice}
        onClose={() => setIsOpenNotice(false)}
      />

      <CreateModal open={isOpenCreate} onClose={() => setIsOpenCreate(false)} />

      <PricingPlans
        open={isOpenPricing}
        onClose={() => setIsOpenPricing(false)}
      />
    </>
  );
}
