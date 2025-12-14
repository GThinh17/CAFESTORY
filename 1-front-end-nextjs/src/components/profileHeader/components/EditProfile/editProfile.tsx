"use client";

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogFooter,
} from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import { useEffect, useState } from "react";
import axios from "axios";
import "./editProfile.css";

// ====== DTO khớp backend ======
export interface UserProfileDTO {
  fullName: string;
  phone: string;

  dateOfBirth: string | null; // yyyy-MM-dd
  address: string | null;
}

interface ProfileEditModalProps {
  open: boolean;
  onClose: () => void;
  userId: string; // c732b027-e85c-4a18-ae72-3c1d37950d0a
  initialData: UserProfileDTO;
}

export default function ProfileEditModal({
  open,
  onClose,
  userId,
  initialData,
}: ProfileEditModalProps) {
  const [loading, setLoading] = useState(false);
  const [form, setForm] = useState<UserProfileDTO>({
    fullName: "",
    phone: "",
    email: "",
    dateOfBirth: null,
    address: null,
  });

  const handleDateChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    let value = e.target.value.replace(/\D/g, ""); // chỉ giữ số

    if (value.length > 8) value = value.slice(0, 8);

    if (value.length > 4) {
      value = `${value.slice(0, 2)} ${value.slice(2, 4)} ${value.slice(4)}`;
    } else if (value.length > 2) {
      value = `${value.slice(0, 2)} ${value.slice(2)}`;
    }

    setForm((prev) => ({
      ...prev,
      dateOfBirth: value,
    }));
  };

  const convertToISODate = (value: string | null) => {
    if (!value) return null;

    const parts = value.trim().split(/\s+/);
    if (parts.length !== 3) return null;

    const [mm, dd, yyyy] = parts;
    return `${yyyy}-${mm.padStart(2, "0")}-${dd.padStart(2, "0")}`;
  };

  // Đổ data khi mở modal
  useEffect(() => {
    if (open && initialData) {
      setForm(initialData);
    }
  }, [open, initialData]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm((prev) => ({
      ...prev,
      [name]: value === "" ? null : value,
    }));
  };

  const handleSubmit = async () => {
    try {
      setLoading(true);

      // ====== PUT partial update ======
      const payload = {
        ...form,
        dateOfBirth: convertToISODate(form.dateOfBirth),
      };

      await axios.put(`http://localhost:8080/users/${userId}`, payload);

      onClose();
    } catch (err) {
      console.error("Update profile failed", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Dialog open={open} onOpenChange={(v) => !v && onClose()}>
      <DialogContent className="Container1">
        <DialogHeader>
          <DialogTitle>Cập nhật thông tin cá nhân</DialogTitle>
        </DialogHeader>

        <div className="grid gap-4 py-4">
          <div className="grid gap-2">
            <Label htmlFor="phone">Số điện thoại</Label>
            <Input
              id="phone"
              name="phone"
              value={form.phone || ""}
              onChange={handleChange}
            />
          </div>

          <div className="grid gap-2">
            <Label htmlFor="dateOfBirth">Ngày sinh</Label>
            <Input
              id="dateOfBirth"
              name="dateOfBirth"
              type="text"
              placeholder="MM dd yyyy"
              inputMode="numeric"
              value={form.dateOfBirth || ""}
              onChange={handleDateChange}
            />
          </div>

          <div className="grid gap-2">
            <Label htmlFor="address">Địa chỉ</Label>
            <Input
              id="address"
              name="address"
              value={form.address || ""}
              onChange={handleChange}
            />
          </div>
        </div>

        <DialogFooter>
          <Button
            variant="outline"
            className="button"
            onClick={onClose}
            disabled={loading}
          >
            Hủy
          </Button>
          <Button className="button" onClick={handleSubmit} disabled={loading}>
            {loading ? "Đang lưu..." : "Lưu"}
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
}
