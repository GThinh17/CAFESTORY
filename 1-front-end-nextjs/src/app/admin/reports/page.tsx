"use client";
import Stats from "@/components/admin/Stats";
import UsersTable from "@/components/admin/PagesTable";
import { useAuth } from "@/context/AuthContext";
import axios from "axios";
import { useEffect, useState } from "react";
import ReportsTable from "@/components/admin/ReportsTable";

interface Reports {
  id: string;
  reportingUserId: string;
  reportType: string;
  problem: string;
  description: string;
  isFlagged: string;
  isBanned: string;
  isDeleted: string;
  feedback: string;
  // postCount: number;
  // followingCount: number;
}

export default function DashboardPage() {
  const { token } = useAuth();

  const [reportsData, setReportsData] = useState<Reports[]>([]);

  const handleGetAllReviewers = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/report`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      console.log("DATA NÈ:", response.data.data);

      setReportsData(response.data.data ?? []);
    } catch (error) {
      console.log("Lỗi nằm ở:", error);
    }
  };

  useEffect(() => {
    if (token) {
      handleGetAllReviewers();
    }
  }, [token]);

  return (
    <div>
      <h1 className="text-2xl font-bold mb-2">Dashboard</h1>
      <p className="text-sm text-muted-foreground mb-6">
        Overview & management
      </p>

      <ReportsTable reports={reportsData} />
    </div>
  );
}
