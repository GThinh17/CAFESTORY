import styles from "./page.module.scss";
import { Sidebar } from "@/components/side-bar/side-bar";
import { ProfileInfo } from "@/components/profileHeader/profileInfo";
import { ProfilePostList } from "@/components/profilePost/profilePostList";
export default function Profile() {
  return (
    <div className={styles.page}>
      <main className={styles.main}>
        <div className={styles.leftContainer}>
          <Sidebar />
        </div>

        <div className={styles.mainContainer}>
          <div className={styles.profileHeader}>
            <ProfileInfo />
          </div>

          <div className={styles.profileMain}>
            <ProfilePostList />
          </div>
        </div>
      </main>
      <footer />
    </div>
  );
}
