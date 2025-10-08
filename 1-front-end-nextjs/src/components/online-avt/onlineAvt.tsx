"use client";
import Image from "next/image";
import { useState } from "react";
import { Button } from "../ui/button";
import { ChevronLeft, ChevronRight } from "lucide-react";
import "./onlineAvt.css";

const users = [
  {
    name: "itsdougthepu",
    avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
  },
  {
    name: "openaidalle",
    avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
  },
  {
    name: "lewishamilton",
    avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
  },
  {
    name: "wahab.xyz",
    avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
  },
  {
    name: "defavours",
    avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
  },
  {
    name: "mkbhd",
    avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
  }
];

export function OnlineAvt() {
  const itemsPerPage = 6;
  const [page, setPage] = useState(0);

  const totalPages = Math.ceil(users.length / itemsPerPage);
  const startIndex = page * itemsPerPage;
  const endIndex = startIndex + itemsPerPage;
  const visibleUsers = users.slice(startIndex, endIndex);

  const handleNext = () => {
    if (page < totalPages - 1) setPage(page + 1);
  };

  const handlePrev = () => {
    if (page > 0) setPage(page - 1);
  };

  return (
    <div className="onlineAvtWrapper">
      {page > 0 && (
        <Button className="scrollBtn left" onClick={handlePrev}>
          <ChevronLeft size={22} />
        </Button>
      )}

      <div className="container">
        {visibleUsers.map((user) => (
          <div key={user.name} className="story">
            <div className="avatarWrapper">
              <Image
                src={user.avatar}
                alt={user.name}
                width={70}
                height={70}
                className="avatar"
              />
            </div>
            <span className="username">{user.name}</span>
          </div>
        ))}
      </div>

      {page < totalPages - 1 && (
        <Button className="scrollBtn right" onClick={handleNext}>
          <ChevronRight size={22} />
        </Button>
      )}
    </div>
  );
}
