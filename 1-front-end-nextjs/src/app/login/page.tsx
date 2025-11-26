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
import { useRouter } from "next/navigation";
import axios from "axios";
import { useState } from "react";
import { useAuth, User } from "@/context/AuthContext";


export default function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const { loading, login } = useAuth();
  const handleLogin = async () => {
    try {
      const res = await axios.post("http://localhost:8080/api/login", {
        email,
        password,
      });
      // console.log(res.data);
      const token = res.data.data.accessToken;
      const id = res.data.data.userId;
      const username = res.data.data.fullname;
      const avatar = res.data.data.imagePath
      const user: User = {
        id: id,
        username: username,
        avatar: avatar
      }
      if (token && user) {
        const safeUser = {
          ...user,
          avatar:
            user.avatar && user.avatar !== "null" && user.avatar.trim() !== ""
              ? user.avatar
              : "https://cdn-icons-png.flaticon.com/512/9131/9131529.png"
        };

        localStorage.setItem("token", token);
        localStorage.setItem("user", JSON.stringify(safeUser));

        login(token, safeUser); // Đưa vào AuthContext
        router.push("/");
      }
      console.log("token", token);
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
            onClick={() => handleLogin()}
            className={styles.button}
          >
            Log in
          </Button>
        </CardFooter>
      </Card>
    </div>
  );
}
