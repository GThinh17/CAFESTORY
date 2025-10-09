import styles from "./page.module.scss";
import { Sidebar } from "@/components/side-bar/side-bar";
import { MsgHeader } from "@/components/msgHeader/msgHeader";
import { MessageList } from "@/components/msgList/msgList";
export default function Home() {
  return (
    <div className={styles.page}>
      <main className={styles.main}>
        <div className={styles.leftContainer}>
          <Sidebar />
        </div>

        <div className={styles.mainContainer}>
          <div className={styles.mainLeft}>
            <MsgHeader />
            <MessageList />
          </div>
          <div className={styles.mainRight}></div>
        </div>
      </main>
      <footer />
    </div>
  );
}
