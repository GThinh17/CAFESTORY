"use client";

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import "./createModal.css";
import { useState, useEffect } from "react";
import { useAuth } from "@/context/AuthContext";
import axios from "axios";
import { useRouter } from "next/navigation";
import { Switch } from "@/components/ui/switch";
import { Label } from "@/components/ui/label";
import { Check } from "lucide-react";

interface Reviewer {
  id: string;
  userId: string;
  userName: string;
  userAvatarUrl: string;
  type: "USER" | "PAGE";
}
import { Check } from "lucide-react";

interface Reviewer {
  id: string;
  userId: string;
  userName: string;
  userAvatarUrl: string;
  type: "USER" | "PAGE";
}

export function CreateModal({
  open,
  onClose,
}: {
  open: boolean;
  onClose: () => void;
}) {
  const [isCfOwner, setIsCfOwner] = useState(false);
  const [isImg, setIsImg] = useState(false);
  const [caption, setcaption] = useState("");
  const [mediaFiles, setMediaFiles] = useState<File[]>([]);
  const [visibility, setvisibility] = useState("PUBLIC");
  const [allowComment, setallowComment] = useState(true);
  const [isPin, setisPin] = useState(false);
  const [locationId, setlocationId] = useState(null);
  const [loadingUp, setLoadingUp] = useState(false);
  const [isPostCf, setIsPostCf] = useState(false);
  const [pageId, setPageId] = useState("");
  const [cfOwnerId, setCfOwnerId] = useState("");
  const { user, token } = useAuth();
  const username = user?.username;
  const avatar = user?.avatar;
  const userId = user?.id;

  const [reviewers, setReviewers] = useState<Reviewer[]>([]);
  const [selectedCollaborators, setSelectedCollaborators] = useState<
    Reviewer[]
  >([]);
  const [showCollaboratorsModal, setShowCollaboratorsModal] = useState(false);

  const router = useRouter();

  // ------------------- FETCH FOLLOWINGS -------------------
  useEffect(() => {
    if (!user?.id || !token || !showCollaboratorsModal) return;

    async function fetchFollowings() {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/follows/users/${userId}/following`,
          { headers: { Authorization: `Bearer ${token}` } }
        );

        const mappedData: Reviewer[] = res.data.data.map((item: any) => {
          if (item.followType === "USER") {
            return {
              id: item.followId,
              userId: item.userFollowedId,
              userName: item.userFollowedFullName,
              userAvatarUrl: item.userFollowedAvatar,
              type: "USER",
            };
          }

          return {
            id: item.followId,
            userId: item.pageFollowedId,
            userName: item.pageFollowedName,
            userAvatarUrl: item.pageFollowedAvatar,
            type: "PAGE",
          };
        });

  const [reviewers, setReviewers] = useState<Reviewer[]>([]);
  const [selectedCollaborators, setSelectedCollaborators] = useState<
    Reviewer[]
  >([]);
  const [showCollaboratorsModal, setShowCollaboratorsModal] = useState(false);

  const router = useRouter();

  // ------------------- FETCH FOLLOWINGS -------------------
  useEffect(() => {
    if (!user?.id || !token || !showCollaboratorsModal) return;

    async function fetchFollowings() {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/follows/users/${userId}/following`,
          { headers: { Authorization: `Bearer ${token}` } }
        );

        const mappedData: Reviewer[] = res.data.data.map((item: any) => {
          if (item.followType === "USER") {
            return {
              id: item.followId,
              userId: item.userFollowedId,
              userName: item.userFollowedFullName,
              userAvatarUrl: item.userFollowedAvatar,
              type: "USER",
            };
          }

          return {
            id: item.followId,
            userId: item.pageFollowedId,
            userName: item.pageFollowedName,
            userAvatarUrl: item.pageFollowedAvatar,
            type: "PAGE",
          };
        });

        setReviewers(mappedData);
      } catch (error) {
        console.error("Fetch followings failed:", error);
        setReviewers(mappedData);
      } catch (error) {
        console.error("Fetch followings failed:", error);
      }
    }

    fetchFollowings();
  }, [user?.id, token, showCollaboratorsModal]);

  // ------------------- FETCH CAFE OWNER STATUS -------------------
    }

    fetchFollowings();
  }, [user?.id, token, showCollaboratorsModal]);

  // ------------------- FETCH CAFE OWNER STATUS -------------------
  useEffect(() => {
    const fetchStatus = async () => {
    const fetchStatus = async () => {
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
    fetchStatus();
    fetchStatus();
  }, [open, user?.id, token]);

  // ------------------- FETCH CAFE OWNER ID -------------------
  // ------------------- FETCH CAFE OWNER ID -------------------
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

  // ------------------- FETCH PAGE ID -------------------
  // ------------------- FETCH PAGE ID -------------------
  useEffect(() => {
    const fetchPage = async () => {
    const fetchPage = async () => {
      try {
        if (!cfOwnerId) return;
        if (!cfOwnerId) return;
        const res = await axios.get(
          `http://localhost:8080/api/pages/cafe-owner/${cfOwnerId}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        );
        setPageId(res.data.data.pageId);
      } catch (err) {
        console.log("fetch status fail");
      }
    };
    fetchPage();
  }, [cfOwnerId, token]);

  // ------------------- UPLOAD IMAGES -------------------
  const uploadImages = async () => {
    const urls: string[] = [];
    for (const file of mediaFiles) {
      const formData = new FormData();
      formData.append("file", file);
      formData.append("upload_preset", "upload");
      const res = await axios.post(
        "https://api.cloudinary.com/v1_1/dwdjlzl9h/image/upload",
        formData
      );
      if (res.data.secure_url) urls.push(res.data.secure_url);
    }
    return urls;
  };

  // ------------------- CREATE BLOG AND TAG COLLABORATORS -------------------
  const handleSharePost = async () => {
    try {
      setLoadingUp(true);

      // 1️⃣ Upload ảnh
      let mediaUrls: string[] = [];
      if (mediaFiles.length > 0) mediaUrls = await uploadImages();

      // 2️⃣ Tạo blog
      const blogRes = await axios.post(
        "http://localhost:8080/api/blogs",
        {
          caption: caption,
          mediaUrls: mediaUrls,
          visibility: "PUBLIC",
          allowComment: true,
          isPin: false,
          locationId: null,
          userId: userId,
        },
        { headers: { Authorization: `Bearer ${token}` } }
      );

      const blogId = blogRes.data.data.id;

      // 3️⃣ Tag collaborators
      await Promise.all(
        selectedCollaborators.map((collab) => {
          const payload: any = {
            userId, // người tạo post
            blogIdTag: blogId,
          };

          if (collab.type === "USER") {
            payload.userIdTag = collab.userId;
          } else if (collab.type === "PAGE") {
            payload.pageTagId = collab.userId;
          }

          return axios.post("http://localhost:8080/api/tags", payload, {
            headers: { Authorization: `Bearer ${token}` },
          });
        })
      );

      // Reset
      setcaption("");
      setMediaFiles([]);
      setSelectedCollaborators([]);
      setIsImg(false);
      onClose();
    } catch (err) {
      console.error(err);
    } finally {
      setLoadingUp(false);
    }
  };

    fetchPage();
  }, [cfOwnerId, token]);

  // ------------------- UPLOAD IMAGES -------------------
  const uploadImages = async () => {
    const urls: string[] = [];
    for (const file of mediaFiles) {
      const formData = new FormData();
      formData.append("file", file);
      formData.append("upload_preset", "upload");
      const res = await axios.post(
        "https://api.cloudinary.com/v1_1/dwdjlzl9h/image/upload",
        formData
      );
      if (res.data.secure_url) urls.push(res.data.secure_url);
    }
    return urls;
  };

  // ------------------- CREATE BLOG AND TAG COLLABORATORS -------------------
  const handleSharePost = async () => {
    try {
      setLoadingUp(true);

      // 1️⃣ Upload ảnh
      let mediaUrls: string[] = [];
      if (mediaFiles.length > 0) mediaUrls = await uploadImages();

      // 2️⃣ Tạo blog
      const blogRes = await axios.post(
        "http://localhost:8080/api/blogs",
        {
          caption: caption,
          mediaUrls: mediaUrls,
          visibility: "PUBLIC",
          allowComment: true,
          isPin: false,
          locationId: null,
          userId: userId,
        },
        { headers: { Authorization: `Bearer ${token}` } }
      );

      const blogId = blogRes.data.data.id;

      // 3️⃣ Tag collaborators
      await Promise.all(
        selectedCollaborators.map((collab) => {
          const payload: any = {
            userId, // người tạo post
            blogIdTag: blogId,
          };

          if (collab.type === "USER") {
            payload.userIdTag = collab.userId;
          } else if (collab.type === "PAGE") {
            payload.pageTagId = collab.userId;
          }

          return axios.post("http://localhost:8080/api/tags", payload, {
            headers: { Authorization: `Bearer ${token}` },
          });
        })
      );

      // Reset
      setcaption("");
      setMediaFiles([]);
      setSelectedCollaborators([]);
      setIsImg(false);
      onClose();
    } catch (err) {
      console.error(err);
    } finally {
      setLoadingUp(false);
    }
  };

  return (
    <Dialog open={open} onOpenChange={onClose}>
      <DialogContent className="modalContainer">
        <DialogHeader className="modalHeader">
          <DialogTitle>Tạo bài viết mới</DialogTitle>
        </DialogHeader>

        <div className="modalBody">
          {/* LEFT */}
          <div className="createLeft">
            {!isImg && (
              <input
                className="button11"
                type="file"
                accept="image/*"
                multiple
                onChange={(e) => {
                  const files = Array.from(e.target.files || []);
                  setMediaFiles(files);
                  setIsImg(true);
                }}
              />
            )}
            {isImg && (
              <div className="imgGrid">
                {mediaFiles.map((file, idx) => (
                  <div className="imgCon" key={idx}>
                    <img
                      src={URL.createObjectURL(file)}
                      className="previewImg"
                    />
                  </div>
                ))}
              </div>
            )}
          </div>

          {/* RIGHT */}
          <div className="createRight">
            {/* Avatar + tên */}
            <div className="userRow">
              <img src={avatar} alt="avatar" className="userAvatar" />
              <span className="username">{username}</span>
            </div>

            {/* Caption */}
            <textarea
              placeholder="Thêm mô tả..."
              className="captionInput"
              maxLength={2200}
              value={caption}
              onChange={(e) => setcaption(e.target.value)}
            />
            />

            {/* Add Location */}

            {/* Add collaborators */}
            <div
              className="optionRow"
              onClick={() => setShowCollaboratorsModal(true)}
            >
              <span>thêm người sáng tạo</span>
              <input
                type="text"
                readOnly
                value={selectedCollaborators.map((c) => c.userName).join(", ")}
                placeholder="Chọn một người..."
              />
            </div>


            {/* Switch: Đăng bài cho cafe */}
            {isCfOwner && (
              <div className="optionRow switchRow">
                <Label htmlFor="post-cf">Bài viết của quán cà phê</Label>
                <Switch
                  id="post-cf"
                  checked={isPostCf}
                  onCheckedChange={(v) => setIsPostCf(v)}
                />
              </div>
            )}

            {/* Share button */}
            {/* Share button */}
            <div className="btnShare">
              <Button
                disabled={loadingUp}
                onClick={handleSharePost}
                onClick={handleSharePost}
                className="btnShare"
              >
                {loadingUp ? "Đăng tải..." : "Chia sẻ"}
              </Button>
            </div>
          </div>
        </div>

        {/* Collaborators Modal */}
        {showCollaboratorsModal && (
          <Dialog
            open={showCollaboratorsModal}
            onOpenChange={() => setShowCollaboratorsModal(false)}
            className="collaboratorsDialog"
          >
            <DialogContent className="collaboratorsDialogContent">
              <DialogHeader className="collaboratorsDialogHeader">
                <DialogTitle className="collaboratorsDialogTitle">
                  Chọn người sáng tạo
                </DialogTitle>
              </DialogHeader>
              <div className="collaboratorsList">
                {reviewers.map((r) => (
                  <div
                    key={r.id}
                    className="collaboratorRow"
                    onClick={() => {
                      if (r.type !== "USER") return; // Chỉ cho phép chọn USER
                      setSelectedCollaborators((prev) =>
                        prev.find((c) => c.id === r.id)
                          ? prev.filter((c) => c.id !== r.id)
                          : [...prev, r]
                      );
                    }}
                  >
                    <img src={r.userAvatarUrl} width={30} height={30} />
                    <span>{r.userName}</span>
                    {selectedCollaborators.find((c) => c.id === r.id) && (
                      <Check />
                    )}
                  </div>
                ))}
              </div>
            </DialogContent>
          </Dialog>
        )}
      </DialogContent>
    </Dialog>
  );
}
