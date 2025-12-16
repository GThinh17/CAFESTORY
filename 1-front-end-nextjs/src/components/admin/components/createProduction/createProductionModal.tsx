"use client";

import { useState, useEffect } from "react";
import axios from "axios";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";
import {
  Select,
  SelectTrigger,
  SelectValue,
  SelectContent,
  SelectItem,
} from "@/components/ui/select";
import { useAuth } from "@/context/AuthContext";
import styles from "./createProductionModal.module.css";
interface Props {
  open: boolean;
  onClose: () => void;
  onSuccess?: () => void;
}

export default function CreateProductionModal({
  open,
  onClose,
  onSuccess,
}: Props) {
  const { token } = useAuth();
  const [loading, setLoading] = useState(false);

  const [form, setForm] = useState({
    productionName: "",
    description: "",
    productionType: "REVIEWER",
    status: "ACTIVE",
    total: "",
  });

  const resetForm = () => {
    setForm({
      productionName: "",
      description: "",
      productionType: "REVIEWER",
      status: "ACTIVE",
      total: "",
    });
  };
  useEffect(() => {
    if (!open) {
      resetForm();
    }
  }, [open]);

  const handleSubmit = async () => {
    if (!token) return;

    try {
      setLoading(true);

      await axios.post(
        "http://localhost:8080/api/production",
        {
          production: {
            ...form,
            total: Number(form.total),
            createdAt: new Date().toISOString(),
          },
          timeExpired: 3, // ⭐ luôn luôn = 3
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );

      onSuccess?.();
      onClose();
    } catch (err) {
      console.error("Create production failed:", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Dialog open={open} onOpenChange={onClose}>
      <DialogContent className={styles.modal}>
        <DialogHeader className={styles.header}>
          <DialogTitle className={styles.title}>Create Production</DialogTitle>
        </DialogHeader>

        <div className={styles.field}>
          <Input
            placeholder="Production name"
            value={form.productionName}
            onChange={(e) =>
              setForm({ ...form, productionName: e.target.value })
            }
          />

          <Textarea
            placeholder="Description"
            value={form.description}
            onChange={(e) => setForm({ ...form, description: e.target.value })}
          />

          <Select
            value={form.productionType}
            onValueChange={(v) => setForm({ ...form, productionType: v })}
          >
            <SelectTrigger>
              <SelectValue placeholder="Production type" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="REVIEWER">Reviewer</SelectItem>
              <SelectItem value="CAFEOWNER">Cafe Owner</SelectItem>
            </SelectContent>
          </Select>

          <Input
            type="number"
            placeholder="Total (USD)"
            value={form.total}
            onChange={(e) => setForm({ ...form, total: e.target.value })}
          />

          <div className={styles.footer}>
            <Button
              variant="outline"
              onClick={onClose}
              className={styles.cancelBtn}
            >
              Hủy bỏ
            </Button>

            <Button
              onClick={handleSubmit}
              disabled={loading}
              className={styles.submitBtn}
            >
              {loading ? "Đang tạo..." : "Tạo sản phẩm"}
            </Button>
          </div>
        </div>
      </DialogContent>
    </Dialog>
  );
}
