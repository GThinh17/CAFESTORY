import React from "react";
import Sidebar from "@/components/admin/Sidebar";
import styles from "./layout.module.scss";

export default function AdminLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div className={styles.wrapper}>
      <aside className={styles.sidebar}>
        <Sidebar />
      </aside>

      <main className={styles.main}>{children}</main>
    </div>
  );
}
