"use client";

import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import styles from "./Sidebar.module.css";
import { useRouter } from "next/navigation";
import { useAuth } from "@/context/AuthContext";
import axios from "axios";
import { useEffect, useState } from "react";
import CreateProductionModal from "./components/createProduction/createProductionModal";

export default function Sidebar() {
  const router = useRouter();
  const [openCreate, setOpenCreate] = useState(false);

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

      const UserData = response.data.data;
      const userAvatar = UserData?.avatar
        ? `http://localhost:8080/${UserData.avatar}`
        : "https://i.pravatar.cc/40?img=12";

      setAvatar(userAvatar);
      setfullName(UserData.fullName);
    } catch (error) {
      console.log("LỖI GET ME:", error);
    }
  };

  useEffect(() => {
    if (token) fetchGetMe();
  }, [token]);

  // ================= EXPORT CSV =================
  const exportProductionCSV = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/production", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      const data = res.data.data;

      if (!data || data.length === 0) return;

      // Header CSV (tiếng Việt OK)
      const headers = [
        "Mã sản phẩm",
        "Tên sản phẩm",
        "Mô tả",
        "Loại",
        "Trạng thái",
        "Tổng tiền",
        "Thời hạn (tháng)",
        "Ngày tạo",
      ];

      const rows = data.map((item: any) => [
        item.productionId,
        item.productionName,
        item.description,
        item.productionType,
        item.status,
        item.total,
        item.timeExpired,
        new Date(item.createdAt).toLocaleString("vi-VN"),
      ]);

      // 👉 UTF-8 BOM
      const csvContent =
        "\uFEFF" +
        [headers, ...rows]
          .map((row) =>
            row.map((cell) => `"${String(cell).replace(/"/g, '""')}"`).join(",")
          )
          .join("\n");

      const blob = new Blob([csvContent], {
        type: "text/csv;charset=utf-8;",
      });

      const url = URL.createObjectURL(blob);
      const link = document.createElement("a");

      link.href = url;
      link.download = "danh_sach_san_pham.csv";
      link.click();

      URL.revokeObjectURL(url);
    } catch (err) {
      console.error("Export CSV failed", err);
    }
  };

  // =================================================

  return (
    <div className={styles.wrapper}>
      <Card className={styles.card}>
        <CardHeader>
          <CardTitle className="text-lg">Admin</CardTitle>
        </CardHeader>

        <CardContent className="space-y-3">
          <div className={styles.userInfo}>
            <Avatar>
              <AvatarImage src={avatar} />
              <AvatarFallback>AD</AvatarFallback>
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
              Người dùng
            </Button>
            <Button
              onClick={() => router.push("/admin/pages")}
              variant="ghost"
              className={styles.navBtn}
            >
              Quán cà phê
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
              Báo cáo
            </Button>
            <Button
              onClick={() => router.push("/admin/payment")}
              variant="ghost"
              className={styles.navBtn}
            >
              Thanh toán
            </Button>
            <Button
              onClick={() => router.push("/admin/production")}
              variant="ghost"
              className={styles.navBtn}
            >
              Sản phẩm
            </Button>
            <Button
              onClick={() => {
                logout();
                router.push("/login/admin");
              }}
              variant="ghost"
              className={styles.navBtn}
            >
              Đăng xuất
            </Button>
          </nav>
        </CardContent>
      </Card>

      <Card className={styles.quickActions}>
        <CardHeader>
          <CardTitle className="text-sm">Quick Actions</CardTitle>
        </CardHeader>

        <CardContent className={styles.quickList}>
          <Button onClick={() => setOpenCreate(true)}>Tạo sản phẩm</Button>

          <Button variant="outline" onClick={exportProductionCSV}>
            Xuất file CSV sản phẩm
          </Button>
        </CardContent>
      </Card>

      <CreateProductionModal
        open={openCreate}
        onClose={() => setOpenCreate(false)}
        onSuccess={() => console.log("Created production successfully")}
      />
    </div>
  );
}
