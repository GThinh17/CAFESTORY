"use client";

import { usePathname, useRouter } from "next/navigation";
import styles from "./profileTabs.module.css";
import { LayoutGrid, Bookmark } from "lucide-react";

interface ProfileTabsProps {
  userId: string;
}

export function ProfileTabs({ userId }: ProfileTabsProps) {
  const pathname = usePathname();
  const router = useRouter();

  const isShare = pathname.includes("/share");

  return (
    <div className={styles.tabs}>
      <button
        className={`${styles.tab} ${!isShare ? styles.active : ""}`}
        onClick={() => router.push(`/profile/${userId}`)}
      >
        <LayoutGrid size={20} />
      </button>

      <button
        className={`${styles.tab} ${isShare ? styles.active : ""}`}
        onClick={() => router.push(`/profile/${userId}/share`)}
      >
        <Bookmark size={20} />
      </button>
    </div>
  );
}
