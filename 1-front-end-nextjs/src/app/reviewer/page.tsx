"use client";
import styles from "./page.module.scss";
import { useState } from "react";
import { Sidebar } from "@/components/side-bar/side-bar";
import Stats from "@/components/chart/Stats";
import { MyBarChart } from "@/components/chart/barChart";
import { ChartAreaStacked } from "@/components/chart/Chart";
import { useAuth } from "@/context/AuthContext";
import axios from "axios";
import { useEffect } from "react";
export default function Home() {
  const { user, token } = useAuth();

  const [wallet, setWallet] = useState<any>(null);
  const [pending, setPending] = useState(true);

  useEffect(() => {
    if (!user?.id) return;

    const fetchWallet = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/wallets/by-user/${user.id}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        setWallet(res.data.data);
      } catch (err) {
        console.error("Fetch wallet failed", err);
      } finally {
        setPending(false);
      }
    };
    fetchWallet();
  }, [user?.id, token]);

  return (
    <div className={styles.page}>
      <main className={styles.main}>
        <div className={styles.leftContainer}>
          <Sidebar />
        </div>
        <div className={styles.rightContainer}>
          <div className={styles.chartCon}>
            <Stats
              total={4}
              active={2}
              editors={2}
              walletBalance={`${wallet?.balance ?? 0} ${
                wallet?.currency ?? ""
              }`}
            />
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
