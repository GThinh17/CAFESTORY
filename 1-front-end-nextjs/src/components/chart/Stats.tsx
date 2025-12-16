"use client";

import { Card, CardContent } from "@/components/ui/card";
import { motion } from "framer-motion";
import styles from "./Stats.module.css";
import { useEffect, useState } from "react";
import axios from "axios";
import { useAuth } from "@/context/AuthContext";

type Props = {
  total: number;
  active: number;
  editors: number;
  walletBalance: string;
};

export default function Stats({
  total,
  active,
  editors,
  walletBalance,
}: Props) {
  const { user, token } = useAuth();
  const [verifiedBank, setVerifiedBank] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const [userEmail, setUserEmail] = useState("");
  const [isWithdrawModalOpen, setIsWithdrawModalOpen] = useState(false);
  const [withdrawEmail, setWithdrawEmail] = useState(userEmail);
  const [withdrawAmount, setWithdrawAmount] = useState<number | "">("");

  // Lấy dữ liệu user khi component mount
  useEffect(() => {
    if (user?.id) {
      axios
        .get(`http://localhost:8080/users/${user.id}`, {
          headers: { Authorization: `Bearer ${token}` },
        })
        .then((res) => {
          setVerifiedBank(res.data.data.vertifiedBank); // lưu trường verifiedBank
          setUserEmail(res.data.data.email);
        })
        .catch((err) => console.error(err));
    }
  }, [user?.id, token]);

  const handleVerifyBank = async () => {
    if (!userEmail) return;
    setLoading(true);
    try {
      const res = await axios.post(
        `http://localhost:8080/api/payout/connect/create-account?email=${userEmail}`,
        {}, // body rỗng nếu API không cần
        {
          headers: { Authorization: `Bearer ${token}` }, // config đúng vị trí
        }
      );
      console.log("Verify bank response:", res.data);
      // giả sử API trả về trạng thái verifiedBank mới
      setVerifiedBank("verified"); // cập nhật trạng thái
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleAddBank = async () => {
    setLoading(true);
    try {
      const email = userEmail; // hoặc dùng email user
      const res = await axios.get(
        `http://localhost:8080/api/payout/connect/onboarding-link?email=${email}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      console.log("Onboarding link:", res.data);
      // Giả sử API trả về { link: "https://..." }
      const link = res.data;
      if (link) {
        window.location.href = link; // chuyển hướng ngay sang Stripe
        // Hoặc: window.open(link, "_blank") để mở tab mới
      }
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };
  const handleWithdrawClick = () => {
    setWithdrawEmail(userEmail); // mặc định là email user
    setWithdrawAmount(""); // reset số tiền
    setIsWithdrawModalOpen(true);
  };
  const handleConfirmWithdraw = async () => {
    if (!withdrawEmail || !withdrawAmount)
      return alert("Vui lòng nhập email và số tiền");
    setLoading(true);
    try {
      const res = await axios.post(
        "http://localhost:8080/api/payout/transfer",
        {
          email: withdrawEmail,
          amount: withdrawAmount,
        },
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      console.log("Transfer response:", res.data);
      alert("Rút tiền thành công!");
      window.location.reload();
      setIsWithdrawModalOpen(false);
    } catch (err) {
      console.error(err);
      alert("Rút tiền thất bại");
    } finally {
      setLoading(false);
    }
  };

  const items = [
    {
      label: "",
      value: "Rút tiền",
      delay: 0.1,
      clickable: true,
      onClick: handleWithdrawClick,
      loading,
    },
    {
      label: verifiedBank ? "" : "Xác minh ngân hàng",
      value: verifiedBank ? "Đã xác minh" : "Xác minh",
      delay: 0.05,
      clickable: !verifiedBank,
      onClick: handleVerifyBank,
      verified: verifiedBank !== null,
      loading,
    },
    {
      label: "",
      value: "Thông tin thanh toán",
      delay: 0.1,
      clickable: true,
      onClick: handleAddBank,
      loading,
    },
    { label: "Tiền trong ví", value: walletBalance, delay: 0.15 },
  ];

  return (
    <section className={styles.section}>
      {items.map((s, i) => (
        <motion.div
          key={i}
          initial={{ y: 8, opacity: 0 }}
          animate={{ y: 0, opacity: 1 }}
          transition={{ delay: s.delay }}
        >
          <Card
            onClick={s.clickable ? s.onClick : undefined}
            className={`${styles.card} ${s.clickable ? styles.clickable : ""} ${
              s.verified ? styles.verified : ""
            }`}
          >
            <CardContent className={styles.cardContent}>
              <p className={styles.label}>{s.label}</p>
              <p className={styles.value}>
                {s.loading ? "Đang xử lý..." : s.value}
              </p>
            </CardContent>
          </Card>
        </motion.div>
      ))}
      {isWithdrawModalOpen && (
        <div className={styles.modalBackdrop}>
          <div className={styles.modalCard}>
            <h3 className={styles.modalTitle}>Rút tiền</h3>
            <div className={styles.modalBody}>
              <label className={styles.inputLabel}>
                Email:
                <input
                  type="email"
                  value={withdrawEmail}
                  onChange={(e) => setWithdrawEmail(e.target.value)}
                  className={styles.inputField}
                />
              </label>
              <label className={styles.inputLabel}>
                Số tiền:
                <input
                  type="number"
                  value={withdrawAmount}
                  onChange={(e) => setWithdrawAmount(Number(e.target.value))}
                  className={styles.inputField}
                />
              </label>
            </div>
            <div className={styles.modalFooter}>
              <button
                className={styles.confirmButton}
                onClick={handleConfirmWithdraw}
                disabled={loading}
              >
                {loading ? "Đang gửi..." : "Xác nhận"}
              </button>
              <button
                className={styles.cancelButton}
                onClick={() => setIsWithdrawModalOpen(false)}
              >
                Huỷ
              </button>
            </div>
          </div>
        </div>
      )}
    </section>
  );
}
