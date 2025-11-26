"use client";
import styles from "./page.module.scss";
import { useState, useEffect } from "react";
import { Sidebar } from "@/components/side-bar/side-bar";
import { OnlineAvt } from "@/components/online-avt/onlineAvt";
import { Suggestions } from "@/components/suggestions/suggestions";
import { PostList } from "@/components/PostCf/PostList";
import { MsgModal } from "@/components/msgModal/msgModal";
import { SignInButton } from "@/components/SingInBtn/SignInBtn";
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
          {!token ? (
            <div className={styles.buttonCon}>
              <SignInButton />
            </div>
          ) : (
            <div className={styles.onlineCon}>
              <OnlineAvt />
            </div>
          )}
          <div className={styles.postCon}>
            <PostList />
          </div>
        </div>
        <div className={styles.rightContainer}>
          <Suggestions />
        </div>
        <MsgModal />
      </main>
      <footer></footer>
    </div>
  );
}