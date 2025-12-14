"use client"
import Stats from "@/components/admin/Stats";
import UsersTable from "@/components/admin/PagesTable";
import { useAuth } from "@/context/AuthContext";
import axios from "axios";
import { useEffect, useState } from "react";
import PagesTable from "@/components/admin/PagesTable";

interface Page {
  page_id: string;
  page_name: string;
  avatar_url: string;
  contact_email: string;
  contact_phone: string;
  slug: string;
  description: string;
  post_count: number;
  followerCount: number;

}

export default function DashboardPage() {
  const { token } = useAuth();

  const [pageData, setPageData] = useState<Page[]>([]);

  const handleGetAllPage = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/users/admin`, {
        headers: { Authorization: `Bearer ${token}` },
      });

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

      <Stats total={4} active={2} editors={2} pending={1} />

      <PagesTable pages={pageData} />
    </div>
  );
}

"use client"
import Stats from "@/components/admin/Stats";
import UsersTable from "@/components/admin/PagesTable";
import { useAuth } from "@/context/AuthContext";
import axios from "axios";
import { useEffect, useState } from "react";
import ReviewersTable from "@/components/admin/ReviewersTable";

interface Reviewers {
  id: string;
  userName: string;
  userAvatarUrl: string;
  userEmail: string;
  followerCount: string;
  totalScore: string;
  status: string;
  // postCount: number;
  // followingCount: number;
}

export default function DashboardPage() {
  const { token } = useAuth();

  const [reviewersData, setReviewersData] = useState<Reviewers[]>([]);

  const handleGetAllReviewers = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/reviewers`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      console.log("DATA NÈ:", response.data.data);

      setReviewersData(response.data.data ?? []);
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

      <Stats total={4} active={2} editors={2} pending={1} />

      <ReviewersTable reviewers={reviewersData} />
    </div>
  );
}
