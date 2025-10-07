import styles from "./page.module.scss";
import { ModeToggle } from "@/components/dark-theme-btn";
import { Sidebar } from "@/components/side-bar/side-bar";
import { OnlineAvt } from "@/components/online-avt/onlineAvt";
import { Suggestions } from "@/components/suggestions/suggestions";
export default function Home() {
  return (
    <div className={styles.page}>
      <main className={styles.main}>
        <div className={styles.leftContainer}>
          <Sidebar />
          <ModeToggle />
        </div>
        <div className={styles.mainContainer}>
          <div className={styles.onlineCon}>
            <OnlineAvt />
          </div>
          <div className={styles.postCon}>

          </div>
        </div>
        <div className={styles.rightContainer}>
          <Suggestions/>
        </div>
      </main>

      <footer></footer>
    </div>
  );
}
