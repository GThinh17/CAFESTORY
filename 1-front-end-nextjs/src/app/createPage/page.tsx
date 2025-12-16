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
import { useState, useEffect } from "react";
import axios from "axios";
import { useAuth } from "@/context/AuthContext";

export default function CreatePage() {
  const router = useRouter();
  const { user, token } = useAuth();
  const [cfOwnerId, setCfOwnerId] = useState();

  const [form, setForm] = useState({
    pageName: "",
    location: "",
    description: "",
    contactEmail: "",
    contactPhone: "",
    avatarFile: null,
    coverFile: null,
  });

  const [message, setMessage] = useState("");

  // ---- FETCH cafeOwnerId ----
  useEffect(() => {
    const fetchCfOwnerId = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/cafe-owners/user/${user?.id}`
        );
        setCfOwnerId(res.data.data.id);
      } catch (err) {
        console.log("fetch cafeOwnerId fail");
      }
    };

    if (user?.id) fetchCfOwnerId();
  }, [user?.id]);

  // ---- HANDLE INPUT CHANGE ----
  const handleChange = (e: any) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  // ---- HANDLE SUBMIT ----
  const handleSubmit = async () => {
    try {
      if (!cfOwnerId) {
        setMessage("Cannot find cafeOwnerId");
        return;
      }

      let avatarUrl = "";
      let coverUrl = "";

      // Upload Avatar
      if (form.avatarFile) {
        const fd = new FormData();
        fd.append("file", form.avatarFile);
        fd.append("upload_preset", "upload");

        const uploadAvatar = await axios.post(
          "https://api.cloudinary.com/v1_1/dwdjlzl9h/image/upload",
          fd
        );
        avatarUrl = uploadAvatar.data.secure_url;
      }

      // Upload Cover
      if (form.coverFile) {
        const fd2 = new FormData();
        fd2.append("file", form.coverFile);
        fd2.append("upload_preset", "upload");

        const uploadCover = await axios.post(
          "https://api.cloudinary.com/v1_1/dwdjlzl9h/image/upload",
          fd2
        );
        coverUrl = uploadCover.data.secure_url;
      }

      // Body gửi API
      const body = {
        cafeOwnerId: cfOwnerId,
        pageName: form.pageName,
        location: form.location, // lấy giá trị user nhập
        description: form.description,
        contactPhone: form.contactPhone,
        contactEmail: form.contactEmail,
        avatarUrl,
        coverUrl,
      };

      const res = await axios.post("http://localhost:8080/api/pages", body, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });

      if (res.data) {
        setMessage("Created successfully!");
        router.push(`/cafe/${cfOwnerId}`); // đổi route nếu muốn
      }
    } catch (err) {
      console.log(err);
      setMessage("Create page failed!");
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
          {/* Page Name */}
          <div className={styles.field}>
            <Label>Page name</Label>
            <Input
              name="pageName"
              type="text"
              placeholder=""
              onChange={handleChange}
            />
          </div>

          {/* Description */}
          <div className={styles.field}>
            <Label>Description</Label>
            <Input
              name="description"
              type="text"
              placeholder=""
              onChange={handleChange}
            />
          </div>
          {/* Slug */}
          <div className={styles.field}>
            <Label>Address</Label>
            <Input
              name="location"
              type="text"
              placeholder=""
              onChange={handleChange}
            />
          </div>

          {/* Email */}
          <div className={styles.field}>
            <Label>Contact Email</Label>
            <Input
              name="contactEmail"
              type="email"
              placeholder=""
              onChange={handleChange}
            />
          </div>

          {/* Phone */}
          <div className={styles.field}>
            <Label>Contact Phone</Label>
            <Input
              name="contactPhone"
              type="text"
              placeholder=""
              onChange={handleChange}
            />
          </div>

          {/* Avatar */}
          <div className={styles.field}>
            <Label>Avatar</Label>
            <Input
              name="avatarFile"
              type="file"
              accept="image/*"
              onChange={(e: any) =>
                setForm({ ...form, avatarFile: e.target.files[0] })
              }
            />
          </div>

          {/* Cover Image */}
          <div className={styles.field}>
            <Label>Cover Image</Label>
            <Input
              name="coverFile"
              type="file"
              accept="image/*"
              onChange={(e: any) =>
                setForm({ ...form, coverFile: e.target.files[0] })
              }
            />
          </div>

          {message && (
            <p style={{ color: "red", marginTop: "10px" }}>{message}</p>
          )}
        </CardContent>

        <CardFooter className={styles.footer}>
          <Button className={styles.button} onClick={handleSubmit}>
            Create Page
          </Button>
        </CardFooter>
      </Card>
    </div>
  );
}
