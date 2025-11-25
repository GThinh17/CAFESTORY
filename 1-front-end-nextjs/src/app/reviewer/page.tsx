"use client";
import styles from "./page.module.scss";
import { useState } from "react";
import { Sidebar } from "@/components/side-bar/side-bar";
import Stats from "@/components/chart/Stats";
import { MyBarChart } from "@/components/chart/barChart";
import { ChartAreaStacked } from "@/components/chart/Chart";
export default function Home() {
  const [isSignIn, setIsSignIn] = useState(false);
  return (
    <div className={styles.page}>
      <main className={styles.main}>
        <div className={styles.leftContainer}>
          <Sidebar />
        </div>
        <div className={styles.rightContainer}>
          <div className={styles.chartCon}>
            <Stats total={4} active={2} editors={2} pending={1}/>
          </div>
          <div className={styles.chartCon}>
            <MyBarChart />
          </div>
          <div className={styles.chartCon}>
            <ChartAreaStacked />
          </div>
        </div>
      </main>
      <footer></footer>
    </div>
  );
}
