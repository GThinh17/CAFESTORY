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
import styles from "./page.module.scss";
import { useRouter } from "next/navigation";
import axios from "axios";
import { useState } from "react";


export default function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const handleLogin = async () => {
    try {
      const res = await axios.post("http://localhost:8080/api/login", {
        email,
        password,
      });
      const token = res.data.accessToken;
      if (token) {
        localStorage.setItem("token", token);
        router.push("/");
      }
    } catch (err: any) {
      console.error(err);
      alert(err.response?.data?.message || "Đăng nhập thất bại!");
    }
  };
  const router = useRouter();
  return (
    <div className={styles.container}>
      <Card className={styles.card}>
        <CardHeader>
          <CardTitle className={styles.title}>
            <Link href="/">Admin CafBlog</Link>
          </CardTitle>
        </CardHeader>
        <CardContent className={styles.content}>

          <div className={styles.field}>
            <Label htmlFor="email">Email</Label>
            <Input id="email" type="email" placeholder="Enter your email" value={email}
              onChange={(e) => setEmail(e.target.value)} />
          </div>

          <div className={styles.field}>
            <Label htmlFor="password">Password</Label>
            <Input id="password" type="password" placeholder="Enter your password" value={password}
              onChange={(e) => setPassword(e.target.value)} />
          </div>
        </CardContent>

        <CardFooter className={styles.footer}>
          <Button
            onClick={() => router.push("/admin")}
            className={styles.button}
          >
            Log in
          </Button>
        </CardFooter>
      </Card>
    </div>
  );
}
