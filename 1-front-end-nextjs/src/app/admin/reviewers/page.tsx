"use client";
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
      const response = await axios.get(
        `http://localhost:8080/api/reviewers/top/follower-desc`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
<<<<<<< HEAD
=======
      const response = await axios.get(
        `http://localhost:8080/api/reviewers/top/follower-desc`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
>>>>>>> feature

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

<<<<<<< HEAD
      

=======
>>>>>>> feature
      <ReviewersTable reviewers={reviewersData} />
    </div>
  );
}
