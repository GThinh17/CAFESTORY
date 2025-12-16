"use client";

import { useEffect, useState } from "react";
import axios from "axios";
import { Area, AreaChart, CartesianGrid, XAxis } from "recharts";
import styles from "./Chart.module.css";
import { useAuth } from "@/context/AuthContext";

import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  ChartConfig,
  ChartContainer,
  ChartTooltip,
} from "@/components/ui/chart";

interface ChartItem {
  month: string;
  total: number;
}

const chartConfig = {
  total: {
    label: "Revenue",
    color: "var(--chart-1)",
  },
} satisfies ChartConfig;

export function ChartAreaStacked() {
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

        // 1️⃣ Danh sách 12 tháng (short name)
        const months = [
          "Jan",
          "Feb",
          "Mar",
          "Apr",
          "May",
          "Jun",
          "Jul",
          "Aug",
          "Sep",
          "Oct",
          "Nov",
          "Dec",
        ];

        // 2️⃣ Init tất cả tháng = 0
        const grouped: Record<string, number> = {};
        months.forEach((m) => (grouped[m] = 0));

        // 3️⃣ Lọc SUCCESS và cộng total theo tháng
        payments
          .filter((p: any) => p.status === "SUCCESS")
          .forEach((p: any) => {
            const month = new Date(p.processedAt).toLocaleString("en-US", {
              month: "short",
            });

            grouped[month] += p.total;
          });

        // 4️⃣ Convert sang array cho chart
        const formatted = months.map((month) => ({
          month,
          total: grouped[month],
        }));

        setChartData(formatted);
      } catch (error) {
        console.error("Fetch payments failed", error);
      }
    };

    if (token) fetchPayments();
  }, [token]);

  return (
    <Card className={styles.container}>
      <CardHeader className={styles.header}>
        <CardTitle>Revenue by Month</CardTitle>
      </CardHeader>

      <CardContent>
        <ChartContainer config={chartConfig}>
          <AreaChart data={chartData} margin={{ left: 12, right: 12 }}>
            <CartesianGrid vertical={false} />
            <XAxis
              dataKey="month"
              tickLine={false}
              axisLine={false}
              tickMargin={8}
            />
            <ChartTooltip />
            <Area
              dataKey="total"
              type="natural"
              fill="var(--chart-1)"
              fillOpacity={0.4}
              stroke="var(--chart-1)"
            />
          </AreaChart>
        </ChartContainer>
      </CardContent>

      <CardFooter>
        <div className={styles.cardFooterContent}>
          <div className={styles.cardFooterMuted}>
            Only SUCCESS payments are counted
          </div>
        </div>
      </CardFooter>
    </Card>
  );
}
