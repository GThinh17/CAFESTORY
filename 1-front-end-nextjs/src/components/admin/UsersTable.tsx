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

import styles from "./UsersTable.module.css";
import axios from "axios";
import { useAuth } from "@/context/AuthContext";
interface User {
  id: string;
  fullName: string;
  email: string;
  avatar: string;
  address?: string;
  followerCount: number;
  vertifiedBank?: string;
  role?: string; // nếu backend có
}
export default function UsersTable({ users = [] }: { users: User[] }) {
  const [query, setQuery] = useState("");
  const [roleFilter, setRoleFilter] = useState("All");
  const { token } = useAuth();
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
      const res = await axios.get(
        `http://localhost:8080/users/${id}`,
        {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );
      console.log(">>>>>>>>>>>>>>>>>RES NÈ MẤY BA<<<<<<<<<<<<<<<", res.data.data);
    } catch (error) {
      console.log(">>>>>>>>>>>>>>>>>>>>>>>>> LỖI ĐÂY NÈ <<<<<<<<<<<<<", error);
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
              <TableRow key={u.id} className={styles.tableBodyRow}>
                <TableCell className={styles.avatarCell}>
                  <Avatar>
                    <AvatarImage src={u.avatar} />
                    {/* <AvatarFallback>{u.fullName?.[0] ?? "?"}</AvatarFallback> */}
                  </Avatar>
                </TableCell>

                <TableCell className={styles.nameCell}>{u.fullName}</TableCell>
                <TableCell>{u.email}</TableCell>

                <TableCell>
                  <Badge variant="secondary" className={styles.badgeRole}>
                    {u.role || "User"}
                  </Badge>
                </TableCell>

                <TableCell>
                  <Badge className={styles.badgeStatus}>
                    {u.followerCount}
                  </Badge>
                </TableCell>
                <TableCell> {u.address}</TableCell>
                <TableCell>
                  <Button className={styles.button}>Delete</Button>
                  <Button className={styles.button}
                    onClick={() => handleUserById(u.id)}
                  >View</Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </CardContent>
    </Card >
  );
}
