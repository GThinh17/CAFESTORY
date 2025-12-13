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
import { useEffect, useState } from "react";
import axios from "axios";

interface ProfileModalProps {
  open: boolean;
  onClose: () => void;
}

export function ProfileModal({ open, onClose }: ProfileModalProps) {
  const router = useRouter();
  const { logout, loading, user, token } = useAuth();
  const [isCfOwner, setIsCfOwner] = useState(false);
  const [isReviewer, setIsReviewer] = useState(false);
  const [cfOwnerId, setCfOwnerId] = useState("");
  const [page, setPage] = useState<any>(null);

  const options: string[] = [
    "Apps and websites",
    "Settings and privacy",
    "Meta Verified",
    "Cafe Page",
    "Dashboard",
    "Log Out",
    "Cancel",
  ];

  useEffect(() => {
    const fetchstatus = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/cafe-owners/user/${user?.id}/exists`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        );

        setIsCfOwner(res.data.data);
      } catch (err) {
        console.log("fetch status fail");
      }
    };

    fetchstatus();
  }, [open, user?.id, token]);

  useEffect(() => {
    const fetchstatus = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/reviewers/${user?.id}/exists`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        );
        setIsReviewer(res.data.data);
      } catch (err) {
        console.log("fetch status fail");
      }
    };

    fetchstatus();
  }, [open, user?.id, token]);

  useEffect(() => {
    const fetchCfOwnerId = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/cafe-owners/user/${user?.id}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        );

        setCfOwnerId(res.data.data.id);
      } catch (err) {
        console.log("fetch status fail");
      }
    };
    fetchCfOwnerId();
  }, [open, user?.id, token]);

  useEffect(() => {
    const fetchPageInf = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/pages/cafe-owner/${cfOwnerId}`
        );

        setPage(res.data.data);
      } catch (err) {
        console.log("fetch status fail");
      }
    };
    fetchPageInf();
  }, [open, cfOwnerId]);

  const handleClick = (item: string) => {
    if (item === "Cafe Page") {
      if (page === null) {
        router.push("/createPage");
        return;
      }
      router.push(`/cafe/${cfOwnerId}`);
      return;
    }
    if (item === "Dashboard") {
      router.push("/reviewer");
      return;
    }

    if (item === "Log Out") {
      logout();
      router.push("/");
      return;
    }

    onClose();
  };

  const filteredOptions = options.filter((item) => {
    if (!isCfOwner && item === "Cafe Page") {
      return false;
    }
    if (!isReviewer && item === "Dashboard") {
      return false;
    }
    return true;
  });

  return (
    <Dialog open={open} onOpenChange={onClose}>
      <DialogContent className="PModalCon">
        <DialogHeader className="HeaderCon">
          <DialogTitle className="TitleText">Options</DialogTitle>
        </DialogHeader>

        <div className="ProfileList">
          {filteredOptions.map((item) => (
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
