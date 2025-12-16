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
import styles from "../login/page.module.css";
import { useRouter } from "next/navigation";
import { useState } from "react";

export default function RegisterPage() {
  const router = useRouter();

  const [form, setForm] = useState({
    fullname: "",
    email: "",
    phone: "",
    password: "",
    confirmPassword: "",
    location: "",
  });

  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    if (form.password !== form.confirmPassword) {
      setMessage("Passwords do not match!");
      return;
    }

    try {
      const res = await fetch("http://localhost:8080/api/signup", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          fullname: form.fullname,
          email: form.email,
          phone: form.phone,
          password: form.password,
          location: form.location,
        }),
      });

      const data = await res.json();

      if (res.ok) {
        setMessage("Signup successful! You can now log in.");
        router.push("/login");
      } else {
        setMessage(data.message || "Signup failed!");
      }
    } catch (err) {
      console.error(err);
      setMessage("Server error. Please try again later.");
    }
  };

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
            <Label>Fullname</Label>
            <Input
              name="fullname"
              type="text"
              placeholder="Enter your fullname"
              onChange={handleChange}
            />
          </div>

          <div className={styles.field}>
            <Label>Email</Label>
            <Input
              name="email"
              type="email"
              placeholder="Enter your email"
              onChange={handleChange}
            />
          </div>

          <div className={styles.field}>
            <Label>Password</Label>
            <Input
              name="password"
              type="password"
              placeholder="Enter your password"
              onChange={handleChange}
            />
          </div>

          <div className={styles.field}>
            <Label>Confirm Password</Label>
            <Input
              name="confirmPassword"
              type="password"
              placeholder="Confirm your password"
              onChange={handleChange}
            />
          </div>

          <div className={styles.field}>
            <Label>Phone number</Label>
            <Input
              name="phone"
              type="text"
              placeholder="Enter your phone number"
              onChange={handleChange}
            />
          </div>

          <div className={styles.field}>
            <Label>City Location</Label>
            <Input
              name="location"
              type="text"
              placeholder="Enter your username"
              onChange={handleChange}
            />
          </div>

          {message && (
            <p style={{ color: "red", marginTop: "10px" }}>{message}</p>
          )}
        </CardContent>

        <CardFooter className={styles.footer}>
          <Button className={styles.button} onClick={handleSubmit}>
            Sign up
          </Button>
        </CardFooter>

        <Link href="/login">
          <h5>Already have account? Log in</h5>
        </Link>
      </Card>
    </div>
  );
}
