"use client";

import { getMessaging, getToken } from "firebase/messaging";
import axios from "axios";
import { app } from "../api/config/firebase";
import axiosClient from "@/api/config/axiosClient";

const VAPID_KEY = "BBOLiiSbwHTslCoEGOpbj-KVkF6e_RWg4ZoefUDDvbeu3Sm5g2NMvrDo4S89NfYcGgNahoRkdt3ARiSGypEXgnQ";

export const registerFcm = async (userId: string) => {
  if (typeof window === "undefined" || !("Notification" in window)) return;

  try {
    const permission = await Notification.requestPermission();
    if (permission !== "granted") {
      console.log("Notification permission denied");
      return;
    }

    const messaging = getMessaging(app);

    const token = await getToken(messaging, {
      vapidKey: VAPID_KEY
    });

    if (!token) {
      console.log("Cannot get FCM token");
      return;
    }

    console.log("FCM Token:", token);

    await axiosClient.post(
      "/fcm/token",
      {
        fcmToken: token,
        platform: "WEB"
      },
      {
        headers: {
          "X-USER-ID": userId
        }
      }
    );

    localStorage.setItem("fcm_token", token);
  } catch (err) {
    console.error("registerFcm error:", err);
  }
};


export const removeFcmToken = (fcmToken: string) => {
  return axiosClient.delete("/fcm/token", {
    data: {
      fcmToken: fcmToken
    }
  });
};
