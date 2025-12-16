"use client";

import { useState, useMemo } from "react";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
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
import styles from "./ReportTable.module.css";

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
    
    return reports.filter((r) =>
      `${r.problem ?? ""} ${r.description ?? ""}`
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
            <Button className={styles.button}>Duyệt bài</Button>
          </div>

          <div className={styles.toolbar}>
            <Input
              placeholder="Search reports..."
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              className={styles.searchInput}
            />
            <Button className={styles.button}>Duyệt bài</Button>
          </div>

          <Table>
            <TableHeader>
              <TableRow>
                <TableHead className={styles.colRole}>Report Type</TableHead>
                <TableHead className={styles.colName}>Problem</TableHead>
                <TableHead className={styles.colEmail}>Description</TableHead>
                <TableHead className={styles.colStatus}>Tools</TableHead>
              </TableRow>
            </TableHeader>

            <TableBody>
              {filtered.map((r) => (
                <TableRow key={r.id} className={styles.tableRow}>
                  <TableCell className={styles.TableCell}>
                    {r.reportType}
                  </TableCell>
                  <TableCell className={styles.nameCell}>{r.problem}</TableCell>
                  <TableCell className={styles.colEmail}>
                    {r.description}
                  </TableCell>
                  <TableCell className={styles.TableCell}>
                    <Button
                      className={styles.button}
                      onClick={() => handleOpenModal(r)}
                    >
                      View
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>

      {/* Modal chi tiết report */}
      {/* Modal chi tiết report */}
      <Dialog open={open} onOpenChange={setOpen}>
        <DialogContent className="space-y-3">
        <DialogContent className="space-y-3">
          <DialogHeader>
            <DialogTitle>Report Details</DialogTitle>
          </DialogHeader>

          {selectedReport && (
            <div className="space-y-3">
              <p>
                <strong>ID:</strong> {selectedReport.id}
              </p>
              <p>
                <strong>Reporting User:</strong>{" "}
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
