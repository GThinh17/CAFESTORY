import styles from "./page.module.scss";
import { Sidebar } from "@/components/side-bar/side-bar";
import { MsgHeader } from "@/components/msgHeader/msgHeader";
import { MessageList } from "@/components/msgList/msgList";
import { ChatHeader } from "@/components/chatHeader/chatHeader";
import { ChatMessageList } from "@/components/chatMessage/chatMsgList";
export default function Home() {
  return (
    <div className={styles.page}>
      <main className={styles.main}>
        <div className={styles.leftContainer}>
          <Sidebar />
        </div>

        <div className={styles.mainContainer}>
          <div className={styles.mainLeft}>
            <div className={styles.chatHeader}>
              <ChatHeader />
            </div>
            <div className={styles.chatMain}>
              <ChatMessageList />
            </div>
          </div>

          <div className={styles.mainRight}>
            <div className={styles.chatHeader}>
              <MsgHeader />
            </div>
            <div className={styles.chatMain}>
              <MessageList />
            </div>
          </div>
        </div>
      </main>
      <footer />
    </div>
  );
}
