"use client";

import { useState, useMemo, useEffect } from "react";
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
import UserDetailModal from "@/components/admin/components/UserDetailModal";

import styles from "./UsersTable.module.css";
import axios from "axios";
import { useAuth } from "@/context/AuthContext";
export interface User {
  id: string;
  fullName: string;
  email: string;
  phone: string;
  avatar: string | null;
  address: string | null;
  dateOfBirth: string | null;

  followerCount: number;
  followingCount: number;

  vertifiedBank: boolean | null;

  createdAt: string;
  updatedAt: string | null;
}

export default function UsersTable({ users = [] }: { users: User[] }) {
  const [query, setQuery] = useState("");
  const [roleFilter, setRoleFilter] = useState("All");
  const { token } = useAuth();
  const [open, setOpen] = useState(false);
  const [selectedUser, setSelectedUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(false);

  const roles = useMemo(
    () => ["All", ...Array.from(new Set(users.map((u) => u.role || "User")))],
    [users]
  );

  const filtered = useMemo(() => {
    return users.filter((u) => {
      const matchText = (u.fullName + u.email)
        .toLowerCase()
        .includes(query.toLowerCase());
      const matchRole = roleFilter === "All" || u.role === roleFilter;
      return matchText && matchRole;
    });
  }, [users, query, roleFilter]);

  const handleUserById = async (id: string) => {
    try {
      setLoading(true);

      const res = await axios.get(`http://localhost:8080/users/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      setSelectedUser(res.data.data); // ⭐ gán user chi tiết
      setOpen(true); // ⭐ mở modal
    } catch (error) {
      console.log("LỖI:", error);
    } finally {
      setLoading(false);
    }
  };

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
              <TableHead className={styles.colStatus}>Followers</TableHead>
              <TableHead className={styles.colStatus}>Adress</TableHead>
              <TableHead className={styles.colStatus}>Action</TableHead>
            </TableRow>
          </TableHeader>

          <TableBody>
            {filtered.map((u) => (
              <TableRow key={u.id} className={styles.tableRow}>
                <TableCell className={`${styles.cell} ${styles.avatarCell}`}>
                  <Avatar>
                    <AvatarImage
                      className={styles.avatar}
                      src={
                        u.avatar ||
                        "https://cdn-icons-png.flaticon.com/512/9131/9131529.png"
                      }
                    />
                  </Avatar>
                </TableCell>

                <TableCell className={`${styles.cell} ${styles.nameCell}`}>
                  {u.fullName}
                </TableCell>

                <TableCell className={styles.cell}>{u.email}</TableCell>

                <TableCell className={styles.cell}>
                  <Badge className={styles.badgeRole}>{u.role || "User"}</Badge>
                </TableCell>

                <TableCell className={styles.cell}>
                  <Badge className={styles.badgeStatus}>
                    {u.followerCount}
                  </Badge>
                </TableCell>

                <TableCell className={styles.cell}>
                  {u.address || "-"}
                </TableCell>

                <TableCell className={`${styles.cell} ${styles.actionCell}`}>
                  <Button
                    className={styles.button}
                    disabled={loading}
                    onClick={() => handleUserById(u.id)}
                  >
                    {loading ? "Loading..." : "View"}
                  </Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </CardContent>
      <UserDetailModal
        open={open}
        user={selectedUser}
        onClose={() => {
          setOpen(false);
          setSelectedUser(null);
        }}
      />
    </Card>
  );
}
