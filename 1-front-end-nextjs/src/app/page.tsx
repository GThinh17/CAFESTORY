"use client";
import styles from "./page.module.scss";
import { useState } from "react";
import { Sidebar } from "@/components/side-bar/side-bar";
import { OnlineAvt } from "@/components/online-avt/onlineAvt";
import { Suggestions } from "@/components/suggestions/suggestions";
import { PostList } from "@/components/PostCf/PostList";
import { MsgModal } from "@/components/msgModal/msgModal";
import { SignInButton } from "@/components/SingInBtn/SignInBtn";
export default function Home() {
  const [isSignIn, setIsSignIn] = useState(true);
  return (
    <div className={styles.page}>
      <main className={styles.main}>
        <div className={styles.leftContainer}>
          <Sidebar />
        </div>
        <div className={styles.mainContainer}>
          {!isSignIn && (
            <div className={styles.buttonCon}>
              <SignInButton />
            </div>
          )}
          {isSignIn && (
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
