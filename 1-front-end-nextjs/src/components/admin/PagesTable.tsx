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
  page_id: string;
  page_name: string;
  avatar_url: string;
  contact_email: string;
  contact_phone: string;
  slug: string;
  description: string;
  post_count: number;
  followerCount: number;
}

export default function PagesTable({ pages = [] }: { pages: Page[] }) {
  const { token } = useAuth();
  const [query, setQuery] = useState("");

  // --- FILTER THEO SEARCH ---
  const filtered = useMemo(() => {
    return pages.filter((p) =>
      (p.page_name + p.contact_email)
        .toLowerCase()
        .includes(query.toLowerCase())
    );
  }, [pages, query]);

  // --- HÀM XEM CHI TIẾT PAGE THEO ID ---
  const handlePageById = async (pageId: string) => {
    try {
      const res = await axios.get(
        `http://localhost:8080/pages/${pageId}`,
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
              <TableHead>Email</TableHead>
              <TableHead>Phone</TableHead>
              <TableHead>Followers</TableHead>
              <TableHead>Action</TableHead>
            </TableRow>
          </TableHeader>

          <TableBody>
            {filtered.map((p) => (
              <TableRow key={p.page_id}>
                <TableCell>
                  <Avatar>
                    <AvatarImage src={p.avatar_url} />
                  </Avatar>
                </TableCell>

                <TableCell>{p.page_name}</TableCell>
                <TableCell>{p.contact_email}</TableCell>
                <TableCell>{p.contact_phone}</TableCell>

                <TableCell>
                  <Badge>{p.followerCount}</Badge>
                </TableCell>

                <TableCell>
                  <Button
                    className={styles.button}
                    onClick={() => handlePageById(p.page_id)}
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
