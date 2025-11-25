"use client";
import Link from "next/link";
import {
  Card,
  CardHeader,
  CardTitle,
  CardContent,
  CardFooter,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import styles from "./page.module.css";
import React from "react";

export default function LoginPage() {
  return (
    <div className={styles.container}>
      <Card className={styles.card}>
        <CardHeader>
          <CardTitle className={styles.title}>
            <Link href="/">CafeBlog</Link>
          </CardTitle>
        </CardHeader>
        <CardContent className={styles.content}>
          <div className={styles.field}>
            <Label htmlFor="email">Email</Label>
            <Input id="email" type="email" placeholder="Enter your email" />
          </div>
          <div className={styles.field}>
            <Label htmlFor="password">Password</Label>
            <Input
              id="password"
              type="password"
              placeholder="Enter your password"
            />
          </div>
        </CardContent>
        <CardFooter className={styles.footer}>
          <Button className={styles.button}>Log in</Button>
        </CardFooter>
        <Link href="/register">
          <h5>Do not have account? Register</h5>
        </Link>
      </Card>
    </div>
  );
}
