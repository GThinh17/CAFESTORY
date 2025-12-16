"use client";
import Stats from "@/components/admin/Stats";
import UsersTable from "@/components/admin/UsersTable";
import { useAuth } from "@/context/AuthContext";
import axios from "axios";
import { useEffect, useState } from "react";

interface User {
  id: string;
  fullName: string;
  email: string;
  avatar: string;
  address?: string;
  followerCount: number;
  vertifiedBank?: string;
  role?: string;
}

export default function DashboardPage() {
  const { token } = useAuth();

  const [userData, setUserData] = useState<User[]>([]);

  const handleGetAllUser = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/users/admin`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      console.log("DATA NÈ:", response.data.data);

      setUserData(response.data.data ?? []);
    } catch (error) {
      console.log("Lỗi nằm ở:", error);
    }
  };

  useEffect(() => {
    if (token) {
      handleGetAllUser();
    }
  }, [token]);

  return (
    <div>
      <h1 className="text-2xl font-bold mb-2">Dashboard</h1>
      <p className="text-sm text-muted-foreground mb-6">
        Overview & management
      </p>

      <UsersTable users={userData} />
    </div>
  );
}
