"use client";

import { ChevronDown, Pencil, Search } from "lucide-react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import styles from "./msgHeader.module.css";

export function MsgHeader() {
  return (
    <div className={styles.container}>
      {/* Header top */}
      <div className={styles.headerTop}>
        <div className={styles.username}>
          <span className={styles.name}>thnhvu_2</span>
          <ChevronDown className={styles.iconDown} />
        </div>

        <Button variant="ghost" size="icon" className={styles.editBtn}>
          <Pencil className={styles.editIcon} />
        </Button>
      </div>

      {/* Search input */}
      <div className={styles.searchWrapper}>
        <Search className={styles.searchIcon} />
        <Input
          type="text"
          placeholder="Search"
          className={styles.searchInput}
        />
      </div>
    </div>
  );
}
