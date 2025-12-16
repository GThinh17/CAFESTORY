"use client";

import { usePathname, useRouter } from "next/navigation";
import styles from "./profileTabs.module.css";
import { LayoutGrid, Bookmark, ContactRound } from "lucide-react";

interface ProfileTabsProps {
  userId: string;
}

export function ProfileTabs({ userId }: ProfileTabsProps) {
  const pathname = usePathname();
  const router = useRouter();

  // Xác định tab nào đang active dựa trên pathname
  const isPosts = pathname === `/profile/${userId}`;
  const isShare = pathname === `/profile/${userId}/share`;
  const isTagged = pathname === `/profile/${userId}/tagged`;

  return (
    <div className={styles.tabs}>
      <button
        className={`${styles.tab} ${isPosts ? styles.active : ""}`}
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

      <button
        className={`${styles.tab} ${isTagged ? styles.active : ""}`}
        onClick={() => router.push(`/profile/${userId}/tagged`)}
      >
        <ContactRound size={20} />
      </button>
    </div>
  );
}
