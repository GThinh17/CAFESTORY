"use client";

import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
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
      <DialogContent className="max-w-lg p-0">
        <DialogHeader className="p-4">
          <DialogTitle>Notifications</DialogTitle>
        </DialogHeader>

        {/* Your notification list */}
        <div className="max-h-[70vh] overflow-y-auto p-4">
          <Notifications />
        </div>
      </DialogContent>
    </Dialog>
  );
}
