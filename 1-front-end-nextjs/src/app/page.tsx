import styles from "./page.module.scss";
import { ModeToggle } from "@/components/dark-theme-btn";
import { Sidebar } from "@/components/side-bar/side-bar";

export default function Home() {
  return (
    <div className={styles.page}>
      <main className={styles.main}>
        <div className={styles.leftContainer}>
          <Sidebar />
          <ModeToggle />
        </div>
        <div className={styles.mainContainer}></div>
        <div className={styles.rightContainer}></div>
      </main>

      <footer></footer>
    </div>
  );
}
