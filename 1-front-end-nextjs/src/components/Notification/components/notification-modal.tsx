"use client";

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import "./NoticeModal.css"
import Notifications from "../Notification";

export function NotificationModal({
  open,
  onClose,
}: {
  open: boolean;
  onClose: () => void;
}) {
  return (
    <Dialog open={open} onOpenChange={onClose}>
      <DialogContent className="ModalCon">
        <DialogHeader className="HeaderCon">
          <DialogTitle>Notifications</DialogTitle>
        </DialogHeader>

        <div className="NoteCon">
          <Notifications />
        </div>
      </DialogContent>
    </Dialog>
  );
}
