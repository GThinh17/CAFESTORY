"use client";

import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import styles from "./Sidebar.module.css";
import { useRouter } from "next/navigation";

export default function Sidebar() {
  const router = useRouter();

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
              <AvatarFallback>AD</AvatarFallback>
            </Avatar>

            <div>
              <p className={styles.userName}>Pham Thanh Vu</p>
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
              onClick={() => router.push("/admin")}
              variant="ghost"
              className={styles.navBtn}
            >
              Settings
            </Button>
            <Button
              onClick={() => router.push("/login/admin")}
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
