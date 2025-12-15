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
import { useParams } from "next/navigation";

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
}

export default function ProfileEditModal({
  open,
  onClose,
}: ProfileEditModalProps) {
  const [loading, setLoading] = useState(false);
  const [form, setForm] = useState({
    pageName: "",
    description: "",
    contactPhone: "",
    contactEmail: "",
  });

  const [currentData, setCurrentData] = useState({
    pageName: "",
    description: "",
    contactPhone: "",
    contactEmail: "",
  });

  const [realPageId, setRealPageId] = useState();
  const { pageId } = useParams();

  useEffect(() => {
    const fetchPage = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/pages/cafe-owner/${pageId}`
        );
        setRealPageId(res.data.data.pageId);
        setCurrentData({
          pageName: res.data.data.pageName,
          description: res.data.data.description,
          contactPhone: res.data.data.contactPhone,
          contactEmail: res.data.data.contactEmail,
        });
      } catch (err: any) {
        console.error("API error:", err.response?.status, err.message);
      }
    };
    fetchPage();
  }, [pageId]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async () => {
    try {
      setLoading(true);

      const payload = Object.fromEntries(
        Object.entries(form).filter(
          ([_, value]) => value !== "" && value !== null
        )
      );

      await axios.patch(
        `http://localhost:8080/api/pages/${realPageId}`,
        payload
      );

      onClose();
    } catch (err) {
      console.error("Update page failed", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Dialog open={open} onOpenChange={(v) => !v && onClose()}>
      <DialogContent className="Container1">
        <DialogHeader>
          <DialogTitle>Cập nhật thông tin quán cà phê</DialogTitle>
        </DialogHeader>

        <div className="grid gap-4 py-4">
          <Input
            name="pageName"
            placeholder={currentData.pageName}
            value={form.pageName}
            onChange={handleChange}
          />

          <Input
            name="description"
            placeholder={currentData.description}
            value={form.description}
            onChange={handleChange}
          />

          <Input
            name="contactPhone"
            placeholder={currentData.contactPhone}
            value={form.contactPhone}
            onChange={handleChange}
          />

          <Input
            name="contactEmail"
            placeholder={currentData.contactEmail}
            value={form.contactEmail}
            onChange={handleChange}
          />
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
