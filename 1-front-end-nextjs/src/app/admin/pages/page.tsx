"use client";
import Stats from "@/components/admin/Stats";
import UsersTable from "@/components/admin/PagesTable";
import { useAuth } from "@/context/AuthContext";
import axios from "axios";
import { useEffect, useState } from "react";
import PagesTable from "@/components/admin/PagesTable";

interface Page {
  pageId: string;
  pageName: string;
  avatarUrl: string;
  contactEmail: string;
  contactPhone: string;
  slug: string;
  description: string;
  postCount: number;
  followingCount: number;
}

export default function DashboardPage() {
  const { token } = useAuth();

  const [pageData, setPageData] = useState<Page[]>([]);

  const handleGetAllPage = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/pages/top-followers`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );

      console.log("DATA NÈ:", response.data.data);

      setPageData(response.data.data ?? []);
    } catch (error) {
      console.log("Lỗi nằm ở:", error);
    }
  };

  useEffect(() => {
    if (token) {
      handleGetAllPage();
    }
  }, [token]);

  return (
    <div>
      <h1 className="text-2xl font-bold mb-2">Dashboard</h1>
      <p className="text-sm text-muted-foreground mb-6">
        Overview & management
      </p>

      <PagesTable pages={pageData} />
    </div>
  );
}
