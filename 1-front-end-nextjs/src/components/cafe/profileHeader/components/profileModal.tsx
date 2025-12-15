"use client";

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import "./profileModal.css";
import { useRouter } from "next/navigation";
import { useAuth } from "@/context/AuthContext";
import { ChangeAvtModal } from "./ChangeAvtModal/changeAvtModal";
import ProfileEditModal from "./EditProfile/editProfile";
import { useState } from "react";

interface ProfileModalProps {
  open: boolean;
  onClose: () => void;
}

export function ProfileModal({ open, onClose }: ProfileModalProps) {
  const router = useRouter();
  const [openChangeAvt, setOpenChangeAvt] = useState(false);
  const [openEdit, setOpenEdit] = useState(false);

  const options: string[] = [
    "Change Background Image",
    "Change Avatar",
    "Edit Profile",
    "Cancel",
  ];

  const handleClick = (item: string) => {
    if (item === "Change Avatar") {
      setOpenChangeAvt(true); // Mở modal Change Avatar
      return;
    }
    if (item === "Edit Profile") {
      setOpenEdit(true); // Mở modal Change Avatar
      return;
    }
    onClose();
  };

  return (
    <>
      <Dialog open={open} onOpenChange={onClose}>
        <DialogContent className="PModalCon">
          <DialogHeader className="HeaderCon">
            <DialogTitle className="TitleText">Options</DialogTitle>
          </DialogHeader>

          <div className="ProfileList">
            {options.map((item) => (
              <button
                key={item}
                className={`ProfileItem ${
                  item === "Cancel" ? "CancelItem" : ""
                }`}
                onClick={() => handleClick(item)}
              >
                {item}
              </button>
            ))}
          </div>
        </DialogContent>
      </Dialog>
      <ChangeAvtModal
        open={openChangeAvt}
        onClose={() => setOpenChangeAvt(false)}
      />
      <ProfileEditModal open={openEdit} onClose={() => setOpenEdit(false)} />
    </>
  );
}
