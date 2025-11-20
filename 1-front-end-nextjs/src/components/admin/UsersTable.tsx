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
import {
  DropdownMenu,
  DropdownMenuTrigger,
  DropdownMenuContent,
  DropdownMenuItem,
} from "@/components/ui/dropdown-menu";
import { Avatar, AvatarImage, AvatarFallback } from "@/components/ui/avatar";

import styles from "./UsersTable.module.css";

// sample data
const sampleUsers = [
  {
    id: 1,
    name: "Nguyen Van A",
    email: "a@example.com",
    role: "Admin",
    status: "Active",
    avatar: "https://i.pravatar.cc/40?img=1",
  },
  {
    id: 2,
    name: "Le Thi B",
    email: "b@example.com",
    role: "Editor",
    status: "Pending",
    avatar: "https://i.pravatar.cc/40?img=2",
  },
  {
    id: 3,
    name: "Tran Van C",
    email: "c@example.com",
    role: "Viewer",
    status: "Inactive",
    avatar: "https://i.pravatar.cc/40?img=3",
  },
  {
    id: 4,
    name: "Pham Thi D",
    email: "d@example.com",
    role: "Editor",
    status: "Active",
    avatar: "https://i.pravatar.cc/40?img=4",
  },
];

export default function UsersTable() {
  const [query, setQuery] = useState("");
  const [roleFilter, setRoleFilter] = useState("All");
  const [users] = useState(sampleUsers);

  const roles = useMemo(
    () => ["All", ...Array.from(new Set(users.map((u) => u.role)))],
    [users]
  );

  const filtered = useMemo(() => {
    return users.filter((u) => {
      const matchText = (u.name + u.email)
        .toLowerCase()
        .includes(query.toLowerCase());
      const matchRole = roleFilter === "All" || u.role === roleFilter;
      return matchText && matchRole;
    });
  }, [users, query, roleFilter]);

  return (
    <Card>
      <CardHeader className={styles.Header}>
        <CardTitle>Users</CardTitle>
      </CardHeader>

      <CardContent>
        <div className={styles.toolbar}>
          <Input
            placeholder="Search users..."
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            className={styles.searchInput}
          />

          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button variant="outline" size="sm" className={styles.filterBtn}>
                Filter
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent>
              {roles.map((r) => (
                <DropdownMenuItem key={r} onClick={() => setRoleFilter(r)}>
                  {r}
                </DropdownMenuItem>
              ))}
            </DropdownMenuContent>
          </DropdownMenu>
        </div>

        <Table>
          <TableHeader>
            <TableRow>
              <TableHead className={styles.colProfile}>Profile</TableHead>
              <TableHead className={styles.colName}>Name</TableHead>
              <TableHead className={styles.colEmail}>Email</TableHead>
              <TableHead className={styles.colRole}>Role</TableHead>
              <TableHead className={styles.colStatus}>Status</TableHead>
            </TableRow>
          </TableHeader>

          <TableBody>
            {filtered.map((u) => (
              <TableRow key={u.id} className={styles.tableBodyRow}>
                <TableCell className={styles.avatarCell}>
                  <Avatar>
                    <AvatarImage src={u.avatar} />
                    <AvatarFallback>{u.name[0]}</AvatarFallback>
                  </Avatar>
                </TableCell>

                <TableCell className={styles.nameCell}>{u.name}</TableCell>
                <TableCell>{u.email}</TableCell>

                <TableCell>
                  <Badge variant="secondary" className={styles.badgeRole}>
                    {u.role}
                  </Badge>
                </TableCell>

                <TableCell>
                  <Badge className={styles.badgeStatus}>{u.status}</Badge>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </CardContent>
    </Card>
  );
}
