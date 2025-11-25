"use client";

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import "./profileModal.css";
import { useRouter } from "next/navigation";

interface ProfileModalProps {
  open: boolean;
  onClose: () => void;
}

export function ProfileModal({ open, onClose }: ProfileModalProps) {
  const router = useRouter();

  const options: string[] = [
    "Apps and websites",
    "Settings and privacy",
    "Meta Verified",
    "Supervision",
    "Dashboard",
    "Log Out",
    "Cancel",
  ];

  const handleClick = (item: string) => {
    if (item === "Dashboard") {
      router.push("/reviewer");
      return;
    }

    onClose();
  };

  return (
    <Dialog open={open} onOpenChange={onClose}>
      <DialogContent className="PModalCon">
        <DialogHeader className="HeaderCon">
          <DialogTitle className="TitleText">Options</DialogTitle>
        </DialogHeader>

        <div className="ProfileList">
          {options.map((item) => (
            <button
              key={item}
              className={`ProfileItem ${item === "Cancel" ? "CancelItem" : ""}`}
              onClick={() => handleClick(item)}
            >
              {item}
            </button>
          ))}
        </div>
      </DialogContent>
    </Dialog>
  );
}
