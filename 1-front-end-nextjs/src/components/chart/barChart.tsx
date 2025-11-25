"use client";

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

import {
  ChartTooltip,
  ChartTooltipContent,
} from "@/components/ui/chart";

const chartData = [
  { month: "January", desktop: 18622, mobile: 3380 },
  { month: "February", desktop: 30523, mobile: 23200 },
  { month: "March", desktop: 23799, mobile: 54120 },
  { month: "April", desktop: 73222, mobile: 22190 },
  { month: "May", desktop: 20659, mobile: 13044 },
  { month: "June", desktop: 21443, mobile: 52140 },
  { month: "July", desktop: 18611, mobile: 80222 },
  { month: "August", desktop: 30522, mobile: 32200 },
  { month: "December", desktop: 23768, mobile: 12033 },
  { month: "October", desktop: 7343, mobile: 19033 },
  { month: "November", desktop: 20933, mobile: 51330 },
  { month: "September", desktop: 21423, mobile: 34140 },
];

export function MyBarChart() {
  return (
    <Card className={styles.cardContainer}>
      <CardHeader className={styles.cardHeader}>
        <CardTitle className={styles.cardTitle}>Doanh số theo tháng</CardTitle>
      </CardHeader>
      <CardContent className={styles.cardContent}>
        <ResponsiveContainer width="100%" height={500}>
          <BarChart data={chartData} margin={{ top: 20, right: 30, left: 0, bottom: 5 }}>
            <CartesianGrid vertical={false} />
            <XAxis
              dataKey="month"
              tickLine={false}
              axisLine={false}
              tickMargin={10}
              tickFormatter={(value) => value.slice(0, 3)}
            />
            <YAxis />
            <ChartTooltip/>
            <Bar dataKey="desktop" fill="var(--chart-2)" radius={[4, 4, 0, 0]} />
            <Bar dataKey="mobile" fill="var(--chart-3)" radius={[4, 4, 0, 0]} />
          </BarChart>
        </ResponsiveContainer>
      </CardContent>
    </Card>
  );
}
