"use client";

import { useEffect, useState } from "react";
import axios from "axios";

import Stats from "@/components/admin/Stats";
import { MyBarChart } from "@/components/admin/barChart";
import { ChartAreaStacked } from "@/components/admin/Chart";
import { useAuth } from "@/context/AuthContext";

export default function DashboardPage() {
  const [userCount, setUserCount] = useState(0);
  const [pageCount, setPageCount] = useState(0);
  const [reviewerCount, setReviewerCount] = useState(0);
  const [balance, setBalance] = useState(0);

  const { token } = useAuth();

  useEffect(() => {
    const fetchPayments = async () => {
      try {
        const res = await axios.get("http://localhost:8080/api/payments", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        const payments = res.data.data || [];

        const totalBalance = payments
          .filter((p: any) => p.status === "SUCCESS")
          .reduce((sum: number, p: any) => sum + (p.total || 0), 0);

        setBalance(totalBalance);
      } catch (error) {
        console.error("Failed to fetch payments", error);
      }
    };

    if (token) {
      fetchPayments();
    }
  }, [token]);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const res = await axios.get("http://localhost:8080/users", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        // res.data.data là mảng user

        setUserCount(res.data.data.length);
      } catch (error) {
        console.error("Failed to fetch users", error);
      }
    };

    fetchUsers();
  }, [token]);

  useEffect(() => {
    const fetchPages = async () => {
      try {
        const res = await axios.get(
          "http://localhost:8080/api/pages/top-followers",
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        setPageCount(res.data.data.length);
      } catch (error) {
        console.error("Failed to fetch users", error);
      }
    };

    fetchPages();
  }, [token]);

  useEffect(() => {
    const fetchReviewers = async () => {
      try {
        const res = await axios.get(
          "http://localhost:8080/api/reviewers/top/follower-desc",
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        setReviewerCount(res.data.data.length);
      } catch (error) {
        console.error("Failed to fetch users", error);
      }
    };

    fetchReviewers();
  }, [token]);

  return (
    <div>
      <h1 className="text-2xl font-bold mb-2">Dashboard</h1>
      <p className="text-sm text-muted-foreground mb-6">
        Overview & management
      </p>

      <Stats
        users={userCount}
        pages={pageCount}
        reviewers={reviewerCount}
        balance={`${balance} USD`}
      />

      <ChartAreaStacked />
      <MyBarChart />
    </div>
  );
}
