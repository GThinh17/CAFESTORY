"use client";

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Badge } from "@/components/ui/badge";

import { User } from "./types";
import styles from "./UserDetailModal.module.css";

interface Props {
  open: boolean;
  user: User | null;
  onClose: () => void;
}

export default function UserDetailModal({ open, user, onClose }: Props) {
  if (!user) return null;

  return (
    <Dialog
      className={styles.ModalContainer}
      open={open}
      onOpenChange={onClose}
    >
      <DialogContent className={styles.modalContent}>
        <DialogHeader>
          <DialogTitle>User Detail</DialogTitle>
        </DialogHeader>

        {/* AVATAR */}
        <div className={styles.avatarWrapper}>
          <img
            src={
              user.avatar ||
              "https://cdn-icons-png.flaticon.com/512/9131/9131529.png"
            }
            className={styles.avatar}
          />

          <div className="text-center">
            <h2 className={styles.userName}>{user.fullName}</h2>
            <p className={styles.userEmail}>{user.email}</p>
          </div>
        </div>

        {/* INFO */}
        <div className={styles.infoGrid}>
          <Info label="Phone:" value={user.phone} />
          <Info label="Date of birth:" value={user.dateOfBirth} />
          <Info label="Address:" value={user.address} />
          <Info label="Followers:" value={user.followerCount} />
          <Info label="Following:" value={user.followingCount} />

          <div className={styles.verifiedRow}>
            <span className={styles.infoLabel}>Verified Bank</span>
            <Badge variant={user.vertifiedBank ? "default" : "secondary"}>
              {user.vertifiedBank ? "Verified" : "Not verified"}
            </Badge>
          </div>

          <Info label="Created At:" value={formatDate(user.createdAt)} />
          <Info label="Updated At:" value={formatDate(user.updatedAt)} />
        </div>
      </DialogContent>
    </Dialog>
  );
}

/* -------- helpers -------- */

function Info({
  label,
  value,
}: {
  label: string;
  value: string | number | null;
}) {
  return (
    <div className={styles.infoRow}>
      <span className={styles.infoLabel}>{label}</span>
      <span className={styles.infoValue}>{value ?? "Chưa cập nhật"}</span>
    </div>
  );
}

function formatDate(date: string | null) {
  if (!date) return "—";
  return new Date(date).toLocaleString("vi-VN");
}
