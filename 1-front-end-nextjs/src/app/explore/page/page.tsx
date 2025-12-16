"use client";
import styles from "./page.module.scss";
import { useState, useEffect } from "react";
import { Sidebar } from "@/components/side-bar/side-bar";
import { SuggestionsExplore } from "@/components/exploreComp/suggestions/suggestions";

import { useAuth } from "@/context/AuthContext";

export default function Home() {
  const { token, loading } = useAuth(); // lấy user và loading từ context
  console.log("Token:", token);
  if (loading) {
    // Khi đang load dữ liệu từ localStorage
    return <div>Đang kiểm tra đăng nhập...</div>;
  }

  return (
    <div className={styles.page}>
      <main className={styles.main}>
        <div className={styles.leftContainer}>
          <Sidebar />
        </div>
  
          
          <div className={styles.mainContainer}>
            <SuggestionsExplore/>
          </div>
          <div className={styles.rightContainer}>
            
          </div>
 
      </main>
      <footer></footer>
    </div>
  );
}
