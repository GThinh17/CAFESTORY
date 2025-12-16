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
import {
  DropdownMenu,
  DropdownMenuTrigger,
  DropdownMenuContent,
  DropdownMenuItem,
} from "@/components/ui/dropdown-menu";
import { Button } from "@/components/ui/button";

import { useAuth } from "@/context/AuthContext";
import styles from "./PaymentsTable.module.css";

/* ================= TYPES ================= */
interface Payment {
  paymentId: string;
  amount: number;
  status: "SUCCESS" | "PENDING" | "FAILED";
  processedAt: string;
  productionId: string;
  userId: string;
  total: number;
}

interface User {
  id: string;
  fullName: string;
}

interface Production {
  productionId: string;
  productionType: string;
  productionName: string;
}

/* ================= COMPONENT ================= */
export default function PaymentsTable() {
  const { token } = useAuth();
  const ITEMS_PER_PAGE = 10;
  const [page, setPage] = useState(1);

  const [payments, setPayments] = useState<Payment[]>([]);
  const [users, setUsers] = useState<User[]>([]);
  const [productions, setProductions] = useState<Production[]>([]);

  const [query, setQuery] = useState("");
  const [statusFilter, setStatusFilter] = useState("All");
  const [loading, setLoading] = useState(false);

  /* ================= FETCH ALL ================= */
  useEffect(() => {
    if (!token) return;

    async function fetchAll() {
      try {
        setLoading(true);

        const [paymentRes, userRes, productionRes] = await Promise.all([
          axios.get("http://localhost:8080/api/payments", {
            headers: { Authorization: `Bearer ${token}` },
          }),
          axios.get("http://localhost:8080/users", {
            headers: { Authorization: `Bearer ${token}` },
          }),
          axios.get("http://localhost:8080/api/production", {
            headers: { Authorization: `Bearer ${token}` },
          }),
        ]);

        setPayments(paymentRes.data?.data ?? []);
        setUsers(userRes.data?.data ?? []);
        setProductions(productionRes.data?.data ?? []);
      } catch (err) {
        console.error("Fetch admin data failed:", err);
      } finally {
        setLoading(false);
      }
    }

    fetchAll();
  }, [token]);

  /* ================= MAP DATA ================= */
  const userMap = useMemo(() => {
    const map = new Map<string, string>();
    users.forEach((u) => map.set(u.id, u.fullName));
    return map;
  }, [users]);

  const productionMap = useMemo(() => {
    const map = new Map<string, string>();
    productions.forEach((p) => map.set(p.productionId, p.productionType));
    return map;
  }, [productions]);

  /* ================= FILTER ================= */
  const statuses = ["All", "SUCCESS", "PENDING", "FAILED"];

  const filteredPayments = useMemo(() => {
    return payments.filter((p) => {
      const userName = userMap.get(p.userId) || "";
      const productionType = productionMap.get(p.productionId) || "";

      const matchText =
        userName.toLowerCase().includes(query.toLowerCase()) ||
        productionType.toLowerCase().includes(query.toLowerCase());

      const matchStatus = statusFilter === "All" || p.status === statusFilter;

      return matchText && matchStatus;
    });
  }, [payments, query, statusFilter, userMap, productionMap]);

  useEffect(() => {
    setPage(1);
  }, [query, statusFilter]);
  const totalPages = Math.ceil(filteredPayments.length / ITEMS_PER_PAGE);

  const paginatedPayments = useMemo(() => {
    const start = (page - 1) * ITEMS_PER_PAGE;
    return filteredPayments.slice(start, start + ITEMS_PER_PAGE);
  }, [filteredPayments, page]);

  /* ================= UI ================= */
  return (
    <Card className={styles.Card}>
      <CardHeader>
        <CardTitle>Payments</CardTitle>
      </CardHeader>

      <CardContent>
        {/* ===== TOOLBAR ===== */}
        <div className={styles.toolbar}>
          <Input
            placeholder="Search by user / production..."
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            className={styles.searchInput}
          />

          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button variant="outline" size="sm">
                Status
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent>
              {statuses.map((s) => (
                <DropdownMenuItem key={s} onClick={() => setStatusFilter(s)}>
                  {s}
                </DropdownMenuItem>
              ))}
            </DropdownMenuContent>
          </DropdownMenu>
        </div>

        {/* ===== TABLE ===== */}
        <div className={styles.tableWrapper}>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>User</TableHead>
                <TableHead>Production Type</TableHead>
                <TableHead>Status</TableHead>
                <TableHead>Total</TableHead>
                <TableHead>Amount</TableHead>
                <TableHead>Processed At</TableHead>
              </TableRow>
            </TableHeader>

            <TableBody>
              {paginatedPayments.map((p) => (
                <TableRow key={p.paymentId}>
                  <TableCell>{userMap.get(p.userId) || "Unknown"}</TableCell>

                  <TableCell>
                    {productionMap.get(p.productionId) || "Unknown"}
                  </TableCell>

                  <TableCell>
                    <Badge
                      className={
                        p.status === "SUCCESS"
                          ? styles.success
                          : p.status === "PENDING"
                          ? styles.pending
                          : styles.failed
                      }
                    >
                      {p.status}
                    </Badge>
                  </TableCell>

                  <TableCell>{p.total} USD</TableCell>
                  <TableCell>{p.amount}</TableCell>

                  <TableCell>
                    {new Date(p.processedAt).toLocaleString()}
                  </TableCell>
                </TableRow>
              ))}

              {!loading && paginatedPayments.length === 0 && (
                <TableRow>
                  <TableCell colSpan={6} className={styles.empty}>
                    Không có dữ liệu
                  </TableCell>
                </TableRow>
              )}

              {loading && (
                <TableRow>
                  <TableCell colSpan={6} className={styles.empty}>
                    Loading...
                  </TableCell>
                </TableRow>
              )}
            </TableBody>
          </Table>
          <div className={styles.pagination}>
            <Button
              variant="outline"
              size="sm"
              disabled={page === 1}
              onClick={() => setPage((p) => Math.max(1, p - 1))}
            >
              Prev
            </Button>

            <span className={styles.pageInfo}>
              Page {page} / {totalPages || 1}
            </span>

            <Button
              variant="outline"
              size="sm"
              disabled={page === totalPages || totalPages === 0}
              onClick={() => setPage((p) => Math.min(totalPages, p + 1))}
            >
              Next
            </Button>
          </div>
        </div>
      </CardContent>
    </Card>
  );
}
