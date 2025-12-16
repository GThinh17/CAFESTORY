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
import { Avatar, AvatarImage } from "@/components/ui/avatar";

import styles from "./UsersTable.module.css";
import { useAuth } from "@/context/AuthContext";

interface Reviewers {
  id: string;
  userName: string;
  userAvatarUrl: string;
  userEmail: string;
  followerCount: string;
  totalScore: string;
  status: string;
}

export default function ReviewersTable({
  reviewers = [],
}: {
  reviewers: Reviewers[];
}) {
<<<<<<< HEAD
  const { token } = useAuth();
  const [query, setQuery] = useState("");

  // --- FILTER SEARCH ---
  const filtered = useMemo(() => {
    return reviewers.filter((r) =>
      `${r.userName ?? ""} ${r.userEmail ?? ""}`
        .toLowerCase()
        .includes(query.toLowerCase())
    );
  }, [reviewers, query]);

  return (
    <Card>
      <CardHeader className={styles.Header}>
        <CardTitle>Reviewers</CardTitle>
      </CardHeader>

      <CardContent>
        <div className={styles.toolbar}>
          <Input
            placeholder="Search reviewers..."
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            className={styles.searchInput}
          />
        </div>

        <Table>
          <TableHeader>
            <TableRow>
              <TableHead className={styles.colProfile}>Avatar</TableHead>
              <TableHead className={styles.colName}>Reviewer Name</TableHead>
              <TableHead className={styles.colEmail}>Contact Email</TableHead>
              <TableHead className={styles.colStatus}>Followers</TableHead>
              <TableHead className={styles.colRole}>Score</TableHead>
              <TableHead className={styles.colStatus}>Status</TableHead>
            </TableRow>
          </TableHeader>

          <TableBody>
            {filtered.map((r) => (
              <TableRow key={r.id} className={styles.tableRow}>
                {/* Avatar */}
                <TableCell className={styles.avatarCell}>
                  <Avatar>
                    <AvatarImage
                      src={
                        r.userAvatarUrl ||
                        "https://cdn-icons-png.flaticon.com/512/9131/9131529.png"
                      }
                    />
                  </Avatar>
                </TableCell>

                {/* Name */}
                <TableCell className={styles.nameCell}>{r.userName}</TableCell>

                {/* Email */}
                <TableCell className={styles.colEmail}>{r.userEmail}</TableCell>

                {/* Followers */}
                <TableCell>{r.followerCount ?? 0}</TableCell>

                {/* Score */}
                <TableCell>{r.totalScore ?? 0}</TableCell>

                {/* Status */}
                <TableCell>{r.status}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </CardContent>
    </Card>
  );
}
=======
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead className={styles.colProfile}>Avatar</TableHead>
                <TableHead className={styles.colName}>Reviewer Name</TableHead>
                <TableHead className={styles.colEmail}>Contact Email</TableHead>
                <TableHead className={styles.colStatus}>Followers</TableHead>
                <TableHead className={styles.colRole}>Score</TableHead>
                <TableHead className={styles.colStatus}>Status</TableHead>
              </TableRow>
            </TableHeader>

            <TableBody>
              {filtered.map((r) => (
                <TableRow key={r.id} className={styles.tableRow}>
                  {/* Avatar */}
                  <TableCell className={styles.avatarCell}>
                    <Avatar>
                      <AvatarImage
                        src={
                          r.userAvatarUrl ||
                          "https://cdn-icons-png.flaticon.com/512/9131/9131529.png"
                        }
                      />
                    </Avatar>
                  </TableCell>

                  {/* Name */}
                  <TableCell className={styles.nameCell}>{r.userName}</TableCell>

                  {/* Email */}
                  <TableCell className={styles.colEmail}>{r.userEmail}</TableCell>

                  {/* Followers */}
                  <TableCell>{r.followerCount ?? 0}</TableCell>

                  {/* Score */}
                  <TableCell>{r.totalScore ?? 0}</TableCell>

                  {/* Status */}
                  <TableCell>{r.status}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>
    );
  }
>>>>>>> feature
