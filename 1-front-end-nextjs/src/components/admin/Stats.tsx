"use client";

import { Card, CardContent } from "@/components/ui/card";
import { motion } from "framer-motion";
import styles from "./Stats.module.css";

type Props = {
  total: number;
  active: number;
  editors: number;
  pending: number;
};

export default function Stats({ total, active, editors, pending }: Props) {
  const items = [
    { label: "Total users", value: total, delay: 0 },
    { label: "Active", value: active, delay: 0.05 },
    { label: "Editors", value: editors, delay: 0.1 },
    { label: "Pending", value: pending, delay: 0.15 },
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
