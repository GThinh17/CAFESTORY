"use client";

import { useEffect, useState } from "react";
import axios from "axios";

import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  ResponsiveContainer,
} from "recharts";

import { ChartTooltip } from "@/components/ui/chart";
import { useAuth } from "@/context/AuthContext";
import styles from "./barChart.module.css";

type ChartItem = {
  month: string;
  desktop: number;
  mobile: number;
};

export function MyBarChart() {
  const { user, token } = useAuth();
  const [chartData, setChartData] = useState<ChartItem[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!user?.id) return;

    const fetchData = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/earning-summary/user/${user.id}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        const apiData = res.data.data;

        const currentYear = new Date().getFullYear();

        // 1️⃣ Tạo sẵn 12 tháng với giá trị 0
        const fullYearData: ChartItem[] = Array.from(
          { length: 12 },
          (_, i) => ({
            month: `${i + 1}/${currentYear}`,
            desktop: 0,
            mobile: 0,
          })
        );

        // 2️⃣ Map dữ liệu API vào đúng tháng
        apiData.forEach((item: any) => {
          const monthIndex = item.month - 1;

          fullYearData[monthIndex] = {
            month: `${item.month}/${item.year}`,
            desktop: item.totalEarningAmount ?? 0,
            mobile: item.bonusAmount ?? 0,
          };
        });

        setChartData(fullYearData);
      } catch (error) {
        console.error("Fetch earning summary error:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [user?.id, token]);

  return (
    <Card className={styles.cardContainer}>
      <CardHeader className={styles.cardHeader}>
        <CardTitle className={styles.cardTitle}>Doanh số theo tháng</CardTitle>
      </CardHeader>

      <CardContent className={styles.cardContent}>
        {loading ? (
          <p>Đang tải dữ liệu...</p>
        ) : (
          <ResponsiveContainer width="100%" height={500}>
            <BarChart data={chartData}>
              <CartesianGrid vertical={false} />

              <XAxis
                dataKey="month"
                tickLine={false}
                axisLine={false}
                tickMargin={10}
              />

              <YAxis />

              <ChartTooltip />

              <Bar
                dataKey="desktop"
                name="Tổng thu nhập"
                fill="var(--chart-2)"
                radius={[4, 4, 0, 0]}
              />

              <Bar
                dataKey="mobile"
                name="Thưởng"
                fill="var(--chart-3)"
                radius={[4, 4, 0, 0]}
              />
            </BarChart>
          </ResponsiveContainer>
        )}
      </CardContent>
    </Card>
  );
}
