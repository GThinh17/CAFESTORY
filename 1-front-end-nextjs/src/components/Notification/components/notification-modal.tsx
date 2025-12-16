"use client";

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import "./NoticeModal.css";
import Notifications from "../Notification";
import { useEffect, useState } from "react";
import axios from "axios";
import { useAuth } from "@/context/AuthContext";

export interface ApiNotification {
  id: string;
  receiverId: string;
  senderId: string | null;
  senderName: string | null;
  type: string;
  title: string;
  body: string | null;
  read: boolean;
  createdAt: string;
}

export function NotificationModal({
  open,
  onClose,
}: {
  open: boolean;
  onClose: () => void;
}) {
  const { user, token } = useAuth();
  const [notifications, setNotifications] = useState<ApiNotification[]>([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (!open || !user?.id) return;

    async function fetchNotifications() {
      try {
        setLoading(true);
        const res = await axios.get(
          "http://localhost:8080/api/notifications",
          {
            params: { userId: user?.id },
            headers: { Authorization: `Bearer ${token}` },
          }
        );

        setNotifications(res.data.data);
      } catch (err) {
        console.error("Fetch notifications failed", err);
      } finally {
        setLoading(false);
      }
    }

    fetchNotifications();
  }, [open, user?.id, token]);

  return (
    <Dialog open={open} onOpenChange={onClose}>
      <DialogContent className="ModalCon">
        <DialogHeader className="HeaderCon">
          <DialogTitle>Thông báo</DialogTitle>
        </DialogHeader>

        <div className="NoteCon">
          <Notifications
            notifications={notifications}
            loading={loading}
          />
        </div>
      </DialogContent>
    </Dialog>
  );
}
