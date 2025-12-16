"use client";

import Stats from "@/components/admin/Stats";
import PaymentTable from "@/components/admin/PaymentTable";
import { useAuth } from "@/context/AuthContext";
import axios from "axios";
import { useEffect, useState } from "react";

interface User {
  id: string;
  fullName: string;
  email: string;
  avatar: string;
  followerCount: number;
  role?: string;
}

export default function DashboardPage() {
  const { token } = useAuth();
  const [users, setUsers] = useState<User[]>([]);

  useEffect(() => {
    if (!token) return;

    async function fetchUsers() {
      try {
        const res = await axios.get("http://localhost:8080/users/admin", {
          headers: { Authorization: `Bearer ${token}` },
        });

        setUsers(res.data?.data ?? []);
      } catch (err) {
        console.error("Fetch users failed:", err);
      }
    }

    fetchUsers();
  }, [token]);

  return (
    <div className="space-y-6">
      {/* ===== HEADER ===== */}
      <div>
        <h1 className="text-2xl font-bold">Dashboard</h1>
        <p className="text-sm text-muted-foreground">Overview & management</p>
      </div>

      {/* ===== STATS ===== */}

      {/* ===== PAYMENTS ===== */}
      <PaymentTable />
    </div>
  );
}
