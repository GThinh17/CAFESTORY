"use client";

import { ChevronDown, Pencil, Search } from "lucide-react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import styles from "./msgHeader.module.css";
import { useEffect, useState } from "react";
import { useMessageSearch } from "@/context/MessageSearchContext";

export function MsgHeader() {
  const [username, setUsername] = useState("");
  const { keyword, setKeyword } = useMessageSearch();

  useEffect(() => {
    const raw = localStorage.getItem("user");
    console.log("raw raw raw", raw);
    if (raw) {
      try {
        const user = JSON.parse(raw); // parse JSON
        setUsername(user.username || "");
      } catch (error) {
        console.error("Failed to parse user:", error);
      }
    }
  }, []);
  console.log(username);

  return (
    <div className={styles.container}>
      <div className={styles.headerTop}>
        <div className={styles.username}>
          <span className={styles.name}>{username || "Unknown"}</span>
          <ChevronDown className={styles.iconDown} />
        </div>

        <Button variant="ghost" size="icon" className={styles.editBtn}>
          <Pencil className={styles.editIcon} />
        </Button>
      </div>

      <div className={styles.searchWrapper}>
        <Search className={styles.searchIcon} />
        <Input
          type="text"
          placeholder="Search"
          className={styles.searchInput}
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
        />
      </div>
    </div>
  );
}
