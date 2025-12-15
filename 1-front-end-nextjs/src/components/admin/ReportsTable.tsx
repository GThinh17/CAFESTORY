"use client";

import { useState, useMemo } from "react";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import {
  Table,
  TableHeader,
  TableRow,
  TableHead,
  TableBody,
  TableCell,
} from "@/components/ui/table";
import { Check } from "lucide-react";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import styles from "./UsersTable.module.css";
interface Reports {
  id: string;
  reportingUserId: string;
  reportType: string;
  problem: string;
  description: string;
  isFlagged: string;
  isBanned: string;
  isDeleted: string;
}

export default function ReportsTable({ reports = [] }: { reports: Reports[] }) {
  const [query, setQuery] = useState("");
  const [selectedReport, setSelectedReport] = useState<Reports | null>(null);
  const [open, setOpen] = useState(false);

  const filtered = useMemo(() => {
    return reports.filter((p) =>
      `${p.problem ?? ""} ${p.description ?? ""}`
        .toLowerCase()
        .includes(query.toLowerCase())
    );
  }, [reports, query]);

  const handleOpenModal = (report: Reports) => {
    setSelectedReport(report);
    setOpen(true);
  };

  return (
    <>
      <Card>
        <CardHeader
          style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            margin: "auto",
          }}
        >
          <CardTitle>Reports</CardTitle>
        </CardHeader>

        <CardContent>
          <Input
            placeholder="Search reports..."
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            className={styles.searchInput}
          />
          <Button> Duyệt bài</Button>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Report Type</TableHead>
                <TableHead>Problem</TableHead>
                <TableHead>Description</TableHead>
                <TableHead>Tools</TableHead>
              </TableRow>
            </TableHeader>

            <TableBody>
              {filtered.map((r) => (
                <TableRow key={r.id}>
                  <TableCell>{r.reportType}</TableCell>
                  <TableCell>{r.problem}</TableCell>
                  <TableCell>{r.description}</TableCell>

                  <TableCell>
                    <Button onClick={() => handleOpenModal(r)}>View</Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>

      {/* 🔥 Modal chi tiết report */}
      <Dialog open={open} onOpenChange={setOpen}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Report Details</DialogTitle>
          </DialogHeader>

          {selectedReport && (
            <div className="space-y-3">
              <p>
                <strong>ID:</strong> {selectedReport.id}
              </p>
              <p>
                <strong>Reporting User:</strong>
                {selectedReport.reportingUserId}
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
                <strong>isFlagged:</strong> {selectedReport.isFlagged}
              </p>
              <p>
                <strong>isBanned:</strong> {selectedReport.isBanned}
              </p>
              <p>
                <strong>isDeleted:</strong> {selectedReport.isDeleted}
              </p>
            </div>
          )}
        </DialogContent>
      </Dialog>
    </>
  );
}
