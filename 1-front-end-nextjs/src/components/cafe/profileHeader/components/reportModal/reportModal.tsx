"use client";

import { useState } from "react";
import axios from "axios";
import { X } from "lucide-react";
import { useAuth } from "@/context/AuthContext";
import "./reportModal.css";

interface ReportModalProps {
  open: boolean;
  onClose: () => void;
  pageId: string;
}

const REPORT_OPTIONS = [
  "Giả mạo người khác",
  "Từ ngữ thô tục",
  "Không đúng chủ đề",
  "Thông tin sai sự thật",
  "Page lừa gạt không có thật",
];

export function ReportModal({ open, onClose, pageId }: ReportModalProps) {
  const { user, token } = useAuth();
  const userId = user?.id;

  const [problem, setProblem] = useState("");
  const [description, setDescription] = useState("");
  const [loading, setLoading] = useState(false);

  if (!open) return null;

  const handleReport = async () => {
    if (!problem) return;

    try {
      setLoading(true);

      await axios.post(
        "http://localhost:8080/api/report",
        {
          reportingUser: userId,
          reportType: "PAGE",
          reported: pageId,
          problem,
          description,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );

      onClose();
      setProblem("");
      setDescription("");
    } catch (err) {
      console.error("Report failed:", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="report-overlay">
      <div className="report-modal">
        <div className="report-header">
          <span>Báo cáo bài viết</span>
          <X className="close-icon" onClick={onClose} />
        </div>

        {/* chọn lý do */}
        <select value={problem} onChange={(e) => setProblem(e.target.value)}>
          <option value="">-- Chọn lý do --</option>
          {REPORT_OPTIONS.map((opt) => (
            <option key={opt} value={opt}>
              {opt}
            </option>
          ))}
        </select>

        {/* mô tả thêm */}
        <textarea
          placeholder="Mô tả thêm (không bắt buộc)"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        />

        <button
          className="report-btn"
          disabled={loading || !problem}
          onClick={handleReport}
        >
          {loading ? "Đang gửi..." : "Gửi báo cáo"}
        </button>
      </div>
    </div>
  );
}
