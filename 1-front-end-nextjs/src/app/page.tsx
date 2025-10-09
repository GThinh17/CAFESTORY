import styles from "./page.module.scss";
import { Sidebar } from "@/components/side-bar/side-bar";
import { OnlineAvt } from "@/components/online-avt/onlineAvt";
import { Suggestions } from "@/components/suggestions/suggestions";
import { PostList } from "@/components/PostCf/PostList";
import { MsgModal } from "@/components/msgModal/msgModal";
export default function Home() {
  return (
    <div className={styles.page}>
      <main className={styles.main}>
        <div className={styles.leftContainer}>
          <Sidebar />
        </div>
        <div className={styles.mainContainer}>
          <div className={styles.onlineCon}>
            <OnlineAvt />
          </div>
          <div className={styles.postCon}>
            <PostList/>
          </div>
        </div>
        <div className={styles.rightContainer}>
          <Suggestions/>
        </div>
        <MsgModal/>
      </main>
      <footer></footer>
    </div>
  );
}
