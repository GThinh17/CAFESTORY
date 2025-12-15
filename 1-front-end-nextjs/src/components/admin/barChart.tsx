"use client";

import { useEffect, useState } from "react";
import axios from "axios";
import { useAuth } from "@/context/AuthContext";

import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  ResponsiveContainer,
} from "recharts";
import styles from "./barChart.module.css";
import { ChartTooltip } from "@/components/ui/chart";

interface ChartItem {
  day: string;
  total: number;
}

export function MyBarChart() {
  const { token } = useAuth();
  const [chartData, setChartData] = useState<ChartItem[]>([]);

  useEffect(() => {
    const fetchPayments = async () => {
      try {
        const res = await axios.get("http://localhost:8080/api/payments", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        const payments = res.data.data;

        // 1️⃣ Lấy 10 ngày gần nhất (YYYY-MM-DD)
        const days: string[] = [];
        for (let i = 6; i >= 0; i--) {
          const d = new Date();
          d.setDate(d.getDate() - i);
          days.push(d.toISOString().slice(0, 10));
        }

        // 2️⃣ Init mỗi ngày = 0
        const grouped: Record<string, number> = {};
        days.forEach((d) => (grouped[d] = 0));

        // 3️⃣ Lọc SUCCESS + cộng total theo ngày
        payments
          .filter((p: any) => p.status === "SUCCESS")
          .forEach((p: any) => {
            const day = p.processedAt.slice(0, 10);
            if (grouped[day] !== undefined) {
              grouped[day] += p.total;
            }
          });

        // 4️⃣ Chuẩn hóa data cho chart
        const formatted = days.map((d) => ({
          day: d,
          total: grouped[d],
        }));

        setChartData(formatted);
      } catch (error) {
        console.error("Fetch payments failed", error);
      }
    };

    if (token) fetchPayments();
  }, [token]);

  return (
    <Card className={styles.cardContainer}>
      <CardHeader className={styles.cardHeader}>
        <CardTitle className={styles.cardTitle}>Revenue by Date</CardTitle>
      </CardHeader>

      <CardContent className={styles.cardContent}>
        <ResponsiveContainer width="100%" height={400}>
          <BarChart data={chartData}>
            <CartesianGrid vertical={false} />
            <XAxis
              dataKey="day"
              tickLine={false}
              axisLine={false}
              tickFormatter={(value) => {
                const d = new Date(value);
                return d.toLocaleString("en-US", {
                  month: "short",
                  day: "2-digit",
                }); // DEC 15
              }}
            />

            <YAxis />
            <ChartTooltip />
            <Bar dataKey="total" fill="var(--chart-2)" radius={[6, 6, 0, 0]} />
          </BarChart>
        </ResponsiveContainer>
      </CardContent>
    </Card>
  );
}
