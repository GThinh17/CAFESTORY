"use client";

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Badge } from "@/components/ui/badge";
import styles from "./PageDetailModal.module.css";
interface Props {
  open: boolean;
  page: any | null;
  onClose: () => void;
}

export default function PageDetailModal({ open, page, onClose }: Props) {
  if (!page) return null;
  function Info({ label, value }: { label: string; value: any }) {
    return (
      <div className={styles.infoRow}>
        <span>{label}</span>
        <span>{value ?? "Chưa cập nhật"}</span>
      </div>
    );
  }

  function formatDate(date: string | null) {
    if (!date) return "—";
    return new Date(date).toLocaleString("vi-VN");
  }

  return (
    <Dialog open={open} onOpenChange={onClose}>
      <DialogContent className={styles.modalContent}>
        {/* COVER */}
        {page.coverUrl && <img src={page.coverUrl} className={styles.cover} />}

        {/* AVATAR + NAME */}
        <div className={styles.avatarWrapper}>
          <img src={page.avatarUrl} className={styles.avatar} />

          <div>
            <h2 className={styles.pageName}>{page.pageName}</h2>
            <p className={styles.slug}>{page.slug}</p>
          </div>
        </div>

        {/* INFO */}
        <div className={styles.infoGrid}>
          <Info label="Email" value={page.contactEmail} />
          <Info label="Phone" value={page.contactPhone} />
          <Info label="Description" value={page.description} />
          <Info label="Posts" value={page.postCount} />
          <Info label="Following" value={page.followingCount} />

          <div className={styles.infoRow}>
            <span>Verified</span>
            <Badge variant={page.isVerified ? "default" : "secondary"}>
              {page.isVerified ? "Verified" : "Not verified"}
            </Badge>
          </div>

          <Info label="Created At" value={formatDate(page.createdAt)} />
          <Info label="Updated At" value={formatDate(page.updatedAt)} />
        </div>
      </DialogContent>
    </Dialog>
  );
}
