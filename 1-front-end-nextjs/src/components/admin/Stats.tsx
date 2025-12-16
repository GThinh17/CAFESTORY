"use client";

import { Card, CardContent } from "@/components/ui/card";
import { motion } from "framer-motion";
import styles from "./Stats.module.css";

type Props = {
  users: number;
  pages: number;
  reviewers: number;
  balance: number;
};

export default function Stats({ users, pages, reviewers, balance }: Props) {
  const items = [
    { label: "Total users", value: users, delay: 0 },
    { label: "Total pages", value: pages, delay: 0.05 },
    { label: "Total reviewers", value: reviewers, delay: 0.1 },
    { label: "Balance", value: balance, delay: 0.15 },
  ];

  return (
    <section className={styles.section}>
      {items.map((s, i) => (
        <motion.div
          key={i}
          initial={{ y: 8, opacity: 0 }}
          animate={{ y: 0, opacity: 1 }}
          transition={{ delay: s.delay }}
        >
          <Card>
            <CardContent className={styles.cardContent}>
              <p className={styles.label}>{s.label}</p>
              <p className={styles.value}>{s.value}</p>
            </CardContent>
          </Card>
        </motion.div>
      ))}
    </section>
  );
}
