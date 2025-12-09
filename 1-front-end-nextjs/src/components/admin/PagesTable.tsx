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

interface Page {
  pageId: string;
  pageName: string;
  avatarUrl: string;
  contactEmail: string;
  contactPhone: string;
  slug: string;
  description: string;
  postCount: number;
  followingCount: number;
}

export default function PagesTable({ pages = [] }: { pages: Page[] }) {
  const { token } = useAuth();
  const [query, setQuery] = useState("");

  // --- FILTER THEO SEARCH ---
  const filtered = useMemo(() => {
    return pages.filter((p) =>
      `${p.pageName ?? ""} ${p.contactEmail ?? ""}`
        .toLowerCase()
        .includes(query.toLowerCase())
    );
  }, [pages, query]);


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
              <TableHead>Name</TableHead>
              <TableHead>Page email</TableHead>
              <TableHead>Followers</TableHead>
              <TableHead>Post Count</TableHead>
              <TableHead>Action</TableHead>
            </TableRow>
          </TableHeader>

          <TableBody>
            {filtered.map((p) => (
              <TableRow key={p.pageId}>
                <TableCell>
                  <Avatar>
                    <AvatarImage src={p.avatarUrl} />
                  </Avatar>
                </TableCell>

                <TableCell>{p.pageName}</TableCell>
                <TableCell>{p.contactEmail}</TableCell>

                <TableCell>
                  {p.followingCount}
                </TableCell>
                <TableCell>
                  {p.postCount}
                </TableCell>

                <TableCell>
                  <Button
                    className={styles.button}
                    onClick={() => handlePageById(p.pageId)}
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
  );
}
