"use client";

import { useEffect, useMemo, useState } from "react";
import axios from "axios";

import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Badge } from "@/components/ui/badge";
import {
  Table,
  TableHeader,
  TableRow,
  TableHead,
  TableBody,
  TableCell,
} from "@/components/ui/table";
import { Button } from "@/components/ui/button";

import { useAuth } from "@/context/AuthContext";
import styles from "./PaymentsTable.module.css";

/* ================= TYPES ================= */
interface Production {
  productionId: string;
  productionName: string;
  description: string;
  productionType: "REVIEWER" | "CAFEOWNER";
  status: "ACTIVE" | "INACTIVE";
  total: number;
  timeExpired: number;
  createdAt: string;
}

/* ================= COMPONENT ================= */
export default function ProductionTable() {
  const { token } = useAuth();

  const ITEMS_PER_PAGE = 10;
  const [page, setPage] = useState(1);

  const [productions, setProductions] = useState<Production[]>([]);
  const [query, setQuery] = useState("");
  const [loading, setLoading] = useState(false);

  /* ================= FETCH ================= */
  useEffect(() => {
    if (!token) return;

    const fetchProductions = async () => {
      try {
        setLoading(true);
        const res = await axios.get("http://localhost:8080/api/production", {
          headers: { Authorization: `Bearer ${token}` },
        });

        setProductions(res.data?.data ?? []);
      } catch (err) {
        console.error("Fetch production failed:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchProductions();
  }, [token]);

  /* ================= FILTER ================= */
  const filteredProductions = useMemo(() => {
    return productions.filter((p) =>
      p.productionName.toLowerCase().includes(query.toLowerCase())
    );
  }, [productions, query]);

  /* ================= PAGINATION ================= */
  useEffect(() => {
    setPage(1);
  }, [query]);

  const totalPages = Math.ceil(filteredProductions.length / ITEMS_PER_PAGE);

  const paginatedData = useMemo(() => {
    const start = (page - 1) * ITEMS_PER_PAGE;
    return filteredProductions.slice(start, start + ITEMS_PER_PAGE);
  }, [filteredProductions, page]);

  /* ================= UI ================= */
  return (
    <Card className={styles.Card}>
      <CardHeader>
        <CardTitle>Sản phẩm</CardTitle>
      </CardHeader>

      <CardContent>
        {/* ===== SEARCH ===== */}
        <div className={styles.toolbar}>
          <Input
            placeholder="Tìm theo tên sản phẩm..."
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            className={styles.searchInput}
          />
        </div>

        {/* ===== TABLE ===== */}
        <div className={styles.tableWrapper}>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Tên</TableHead>
                <TableHead>Loại</TableHead>
                <TableHead>Trạng thái</TableHead>
                <TableHead>Tổng tiền</TableHead>
                <TableHead>Thời hạn (tháng)</TableHead>
                <TableHead>Ngày tạo</TableHead>
              </TableRow>
            </TableHeader>

            <TableBody>
              {paginatedData.map((p) => (
                <TableRow key={p.productionId}>
                  <TableCell>{p.productionName}</TableCell>

                  <TableCell>{p.productionType}</TableCell>

                  <TableCell>
                    <Badge
                      className={
                        p.status === "ACTIVE" ? styles.success : styles.failed
                      }
                    >
                      {p.status}
                    </Badge>
                  </TableCell>

                  <TableCell>{p.total} USD</TableCell>
                  <TableCell>{p.timeExpired}</TableCell>

                  <TableCell>
                    {new Date(p.createdAt).toLocaleString("vi-VN")}
                  </TableCell>
                </TableRow>
              ))}

              {!loading && paginatedData.length === 0 && (
                <TableRow>
                  <TableCell colSpan={6} className={styles.empty}>
                    Không có dữ liệu
                  </TableCell>
                </TableRow>
              )}

              {loading && (
                <TableRow>
                  <TableCell colSpan={6} className={styles.empty}>
                    Đang tải...
                  </TableCell>
                </TableRow>
              )}
            </TableBody>
          </Table>

          {/* ===== PAGINATION ===== */}
          <div className={styles.pagination}>
            <Button
              variant="outline"
              size="sm"
              disabled={page === 1}
              onClick={() => setPage((p) => p - 1)}
            >
              Prev
            </Button>

            <span className={styles.pageInfo}>
              Trang {page} / {totalPages || 1}
            </span>

            <Button
              variant="outline"
              size="sm"
              disabled={page === totalPages || totalPages === 0}
              onClick={() => setPage((p) => p + 1)}
            >
              Next
            </Button>
          </div>
        </div>
      </CardContent>
    </Card>
  );
}
