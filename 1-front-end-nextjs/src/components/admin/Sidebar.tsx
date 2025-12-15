"use client";

import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import styles from "./Sidebar.module.css";
import { useRouter } from "next/navigation";
import { useAuth } from "@/context/AuthContext";
import axios from "axios";
import { useEffect, useState } from "react";
import { User } from "lucide-react";

export default function Sidebar() {
  const router = useRouter();
  const { logout, token } = useAuth();
  const [fullName, setfullName] = useState("");
  const [avatar, setAvatar] = useState("");
  const fetchGetMe = async () => {
    try {
      const response = await axios.get("http://localhost:8080/users/getme", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      console.log(">>>>>>>>>>>>>>>>>DATA NÈ<<<<<<<<<<<<<", response.data.data);
      const UserData = response.data.data;
      const userAvatar = UserData?.data?.data?.avatar
        ? `http://localhost:8080/${UserData.data.data.avatar}`
        : "https://i.pravatar.cc/40?img=12";

      setAvatar(userAvatar);
      setfullName(UserData.fullName);
    } catch (error) {
      console.log(
        ">>>>>>>>>>>>>>>>>>>>>>>>>LỖI NÈ<<<<<<<<<<<<<<<<<<<<<<",
        error
      );
    }
  };
  useEffect(() => {
    if (token) {
      fetchGetMe();
    }
  }, [token]);

  return (
    <div className={styles.wrapper}>
      <Card className={styles.card}>
        <CardHeader>
          <CardTitle className="text-lg">Admin</CardTitle>
        </CardHeader>

        <CardContent className="space-y-3">
          <div className={styles.userInfo}>
            <Avatar>
              <AvatarImage src="https://i.pravatar.cc/40?img=12" />
              <AvatarFallback>ADm,i</AvatarFallback>
              <AvatarFallback>ADm,i</AvatarFallback>
            </Avatar>

            <div>
              <p className={styles.userName}>{fullName}</p>

              <p className={styles.userRole}>Super Admin</p>
            </div>
          </div>

          <nav className={styles.nav}>
            <Button
              onClick={() => router.push("/admin")}
              variant="ghost"
              className={styles.navBtn}
            >
              Dashboard
            </Button>
            <Button
              onClick={() => router.push("/admin/users")}
              variant="ghost"
              className={styles.navBtn}
            >
              Users
            </Button>
            <Button
              onClick={() => router.push("/admin/pages")}
              variant="ghost"
              className={styles.navBtn}
            >
              Pages
            </Button>
            <Button
              onClick={() => router.push("/admin/reviewers")}
              variant="ghost"
              className={styles.navBtn}
            >
              Reviewers
            </Button>
            <Button
              onClick={() => router.push("/admin/reports")}
              variant="ghost"
              className={styles.navBtn}
            >
              Reports
            </Button>
            <Button
              onClick={() => (logout(), router.push("/login/admin"))}
              variant="ghost"
              className={styles.navBtn}
            >
              Log out
            </Button>
          </nav>
        </CardContent>
      </Card>

      <Card className={styles.quickActions}>
        <CardHeader>
          <CardTitle className="text-sm">Quick Actions</CardTitle>
        </CardHeader>

        <CardContent className={styles.quickList}>
          <Button>Create user</Button>
          <Button variant="outline">Export CSV</Button>
        </CardContent>
      </Card>
    </div>
  );
}
