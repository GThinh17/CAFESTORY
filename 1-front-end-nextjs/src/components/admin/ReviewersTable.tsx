"use client";

import { useState, useMemo } from "react";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import {
  Table,
  TableHeader,
  TableRow,
  TableHead,
  TableBody,
  TableCell,
} from "@/components/ui/table";
import { Avatar, AvatarImage } from "@/components/ui/avatar";

import styles from "./UsersTable.module.css";
import axios from "axios";
import { useAuth } from "@/context/AuthContext";

interface Reviewers {
  id: string;
  userName: string;
  userAvatarUrl: string;
  userEmail: string;
  followerCount: string;
  totalScore: string;
  status: string;
  // postCount: number;
  // followingCount: number;
}

export default function ReviewersTable({ reviewers = [] }: { reviewers: Reviewers[] }) {
  const { token } = useAuth();
  const [query, setQuery] = useState("");

  // --- FILTER THEO SEARCH ---
  const filtered = useMemo(() => {
    return reviewers.filter((p) =>
      `${p.userName ?? ""} ${p.userEmail ?? ""}`
        .toLowerCase()
        .includes(query.toLowerCase())
    );
  }, [reviewers, query]);


  // --- HÀM XEM CHI TIẾT PAGE THEO ID ---
  const handlePageById = async (pageId: string) => {
    try {
      const res = await axios.get(
        `http://localhost:8080/api/pages/${pageId}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      console.log("CHI TIẾT PAGE:", res.data.data);
    } catch (error) {
      console.log("LỖI API:", error);
    }
  };

  return (
    <Card>
      <CardHeader className={styles.Header}>
        <CardTitle>Pages</CardTitle>
      </CardHeader>

      <CardContent>
        <div className={styles.toolbar}>
          <Input
            placeholder="Search pages..."
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            className={styles.searchInput}
          />
        </div>

        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>Avatar</TableHead>
              <TableHead> Reviewer Name</TableHead>
              <TableHead>Contact email</TableHead>
              <TableHead>Followers</TableHead>
              <TableHead>Score</TableHead>
              <TableHead>Status</TableHead>
            </TableRow>
          </TableHeader>


          <TableBody>
            {filtered.map((r) => (
              <TableRow key={r.id}>
                <TableCell>
                  <Avatar>
                    <AvatarImage src={r.userAvatarUrl} />
                  </Avatar>
                </TableCell>

                <TableCell>{r.userName}</TableCell>

                <TableCell>{r.userEmail}</TableCell>

                <TableCell>{r.followerCount ?? 0}</TableCell>

                <TableCell>{r.totalScore ?? 0}</TableCell>

                <TableCell>

                  {r.status}

                </TableCell>
              </TableRow>
            ))}
          </TableBody>

        </Table>
      </CardContent>
    </Card>
  );
}
