"use client";

import { useState, useEffect, useMemo } from "react";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import {
  Table,
  TableHeader,
  TableRow,
  TableHead,
  TableBody,
  TableCell,
} from "@/components/ui/table";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import axios from "axios";
import styles from "./ReportTable.module.css";
import { useAuth } from "@/context/AuthContext";
interface Report {
  id: string;
  reportingUserId: string;
  reportedBlogId?: string;
  reportType: string;
  problem: string;
  description: string;
  createdAt?: string;
  isFlagged: boolean;
  isBanned: boolean;
  isDeleted: boolean;
  feedback: string; // <-- thêm dòng này
}

export default function ReportsTable() {
  const [reports, setReports] = useState<Report[]>([]);
  const [query, setQuery] = useState("");
  const [selectedReport, setSelectedReport] = useState<Report | null>(null);
  const [open, setOpen] = useState(false);
  const [loading, setLoading] = useState(false);
  const [onlyWithFeedback, setOnlyWithFeedback] = useState(false);
  const { user, token } = useAuth();
  const [filterFeedback, setFilterFeedback] = useState<
    "all" | "no-feedback" | "has-feedback"
  >("all");

  useEffect(() => {
    fetchReports();
  }, [token]);

  const fetchReports = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/report", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      setReports(res.data.data);
    } catch (error) {
      console.error("Error fetching reports:", error);
    }
  };

  const filtered = useMemo(() => {
    return reports
      .filter((r) => {
        // filter theo feedback
        if (filterFeedback === "no-feedback") {
          return !r.feedback || r.feedback.trim() === "";
        }
        if (filterFeedback === "has-feedback") {
          return r.feedback && r.feedback.trim() !== "";
        }
        return true; // all
      })
      .filter((r) =>
        `${r.problem ?? ""} ${r.description ?? ""}`
          .toLowerCase()
          .includes(query.toLowerCase())
      );
  }, [reports, query, filterFeedback]);

  const handleOpenModal = (report: Report) => {
    setSelectedReport(report);
    setOpen(true);
  };
  const handleApprove = async () => {
    setLoading(true);
    try {
      // LỌC report chưa feedback
      const reportsToApprove = reports.filter(
        (r) => !r.feedback || r.feedback.trim() === ""
      );

      if (reportsToApprove.length === 0) {
        alert("Không có báo cáo nào cần duyệt!");
        setLoading(false);
        return;
      }

      // 1. Start thread
      const startRes = await axios.get("http://localhost:8082/start");
      const threadId = startRes.data.thread_id;

      // 2. Send ONLY reports chưa feedback
      await axios.post(
        "http://localhost:8082/modelcheckreport",
        {
          thread_id: threadId,
          reports: reportsToApprove,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      alert("Đã gửi duyệt bài thành công!");
    } catch (err) {
      console.error(err);
      alert("Duyệt bài thất bại!");
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <Card className={styles.bigCon}>
        <CardHeader className={styles.Header}>
          <CardTitle>Reports</CardTitle>
        </CardHeader>

        <CardContent>
          <div className={styles.toolbar}>
            <Input
              placeholder="Search reports..."
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              className={styles.searchInput}
            />

            <div className={styles.filterGroup}>
              <Button
                variant={filterFeedback === "all" ? "default" : "outline"}
                onClick={() => setFilterFeedback("all")}
                className={styles.filterBtn}
              >
                Tất cả
              </Button>

              <Button
                variant={
                  filterFeedback === "no-feedback" ? "default" : "outline"
                }
                onClick={() => setFilterFeedback("no-feedback")}
                className={styles.filterBtn}
              >
                Chưa feedback
              </Button>

              <Button
                variant={
                  filterFeedback === "has-feedback" ? "default" : "outline"
                }
                onClick={() => setFilterFeedback("has-feedback")}
                className={styles.filterBtn}
              >
                Đã feedback
              </Button>
            </div>

            <Button
              className={styles.button}
              onClick={handleApprove}
              disabled={loading}
            >
              {loading ? "Đang duyệt..." : "Duyệt tất cả"}
            </Button>
          </div>

          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Report Type</TableHead>
                <TableHead>Problem</TableHead>
                <TableHead>Feedback</TableHead>
                <TableHead>Description</TableHead>
                <TableHead>Flagged</TableHead>
                <TableHead>Banned</TableHead>
                <TableHead>Deleted</TableHead>
                <TableHead>Tools</TableHead>
              </TableRow>
            </TableHeader>

            <TableBody>
              {filtered.map((r) => (
                <TableRow key={r.id} className={styles.tableRow}>
                  <TableCell>{r.reportType}</TableCell>
                  <TableCell className={styles.tableCellBreak}>
                    {r.feedback}
                  </TableCell>

                  <TableCell>{r.problem}</TableCell>
                  <TableCell>{r.description}</TableCell>
                  <TableCell>{r.isFlagged ? "✅" : "❌"}</TableCell>
                  <TableCell>{r.isBanned ? "✅" : "❌"}</TableCell>
                  <TableCell>{r.isDeleted ? "✅" : "❌"}</TableCell>
                  <TableCell>
                    <Button onClick={() => handleOpenModal(r)}>View</Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>

      <Dialog open={open} onOpenChange={setOpen}>
        <DialogContent className="space-y-3">
          <DialogHeader>
            <DialogTitle>Report Details</DialogTitle>
          </DialogHeader>

          {selectedReport && (
            <div className="space-y-2">
              <p>
                <strong>ID:</strong> {selectedReport.id}
              </p>
              <p>
                <strong>Reporting User:</strong>{" "}
                {selectedReport.reportingUserId}
              </p>
              <p>
                <strong>Reported Blog:</strong> {selectedReport.reportedBlogId}
              </p>
              <p>
                <strong>Type:</strong> {selectedReport.reportType}
              </p>
              <p>
                <strong>Problem:</strong> {selectedReport.problem}
              </p>
              <p>
                <strong>Description:</strong> {selectedReport.description}
              </p>
              <p>
                <strong>Flagged:</strong>{" "}
                {selectedReport.isFlagged ? "✅" : "❌"}
              </p>
              <p>
                <strong>Banned:</strong> {selectedReport.isBanned ? "✅" : "❌"}
              </p>
              <p>
                <strong>Deleted:</strong>{" "}
                {selectedReport.isDeleted ? "✅" : "❌"}
              </p>
            </div>
          )}
        </DialogContent>
      </Dialog>
    </>
  );
}
