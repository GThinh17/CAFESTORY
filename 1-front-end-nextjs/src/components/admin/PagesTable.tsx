"use client";

import { useState, useMemo } from "react";
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
import { Avatar, AvatarImage } from "@/components/ui/avatar";

import styles from "./UsersTable.module.css";
import axios from "axios";
import { useAuth } from "@/context/AuthContext";
import PageDetailModal from "./components/PagesDetailedModal";

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
  const [open, setOpen] = useState(false);
  const [selectedPage, setSelectedPage] = useState<Page | null>(null);

  const filtered = useMemo(() => {
    return pages.filter((p) =>
      `${p.pageName ?? ""} ${p.contactEmail ?? ""}`
        .toLowerCase()
        .includes(query.toLowerCase())
    );
  }, [pages, query]);

  const handlePageById = async (pageId: string) => {
    try {
      const res = await axios.get(`http://localhost:8080/api/pages/${pageId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      setSelectedPage(res.data.data);
      setOpen(true);
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
        {/* TOOLBAR */}
        <div className={styles.toolbar}>
          <Input
            placeholder="Search pages..."
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            className={styles.searchInput}
          />
        </div>

        {/* TABLE */}
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead className={styles.colProfile}>Avatar</TableHead>
              <TableHead className={styles.colName}>Name</TableHead>
              <TableHead className={styles.colEmail}>Page email</TableHead>
              <TableHead className={styles.colStatus}>Followers</TableHead>
              <TableHead className={styles.colRole}>Post Count</TableHead>
              <TableHead>Action</TableHead>
            </TableRow>
          </TableHeader>

          <TableBody>
            {filtered.map((p) => (
              <TableRow key={p.pageId} className={styles.tableRow}>
                {/* AVATAR */}
                <TableCell className={styles.avatarCell}>
                  <Avatar className={styles.avatar}>
                    <AvatarImage src={p.avatarUrl} />
                  </Avatar>
                </TableCell>

                {/* NAME */}
                <TableCell className={styles.nameCell}>{p.pageName}</TableCell>

                {/* EMAIL */}
                <TableCell className={styles.colEmail}>
                  {p.contactEmail}
                </TableCell>

                {/* FOLLOWERS */}
                <TableCell className={styles.colEmail}>
                  {p.followingCount}
                </TableCell>

                {/* POSTS */}
                <TableCell className={styles.colEmail}>{p.postCount}</TableCell>

                {/* ACTION */}
                <TableCell className={styles.TableCell}>
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

      {/* MODAL */}
      <PageDetailModal
        open={open}
        page={selectedPage}
        onClose={() => setOpen(false)}
      />
    </Card>
  );
}
