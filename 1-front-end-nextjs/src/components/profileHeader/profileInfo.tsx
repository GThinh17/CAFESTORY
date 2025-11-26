"use client";
import React from 'react'
import { ProfileHeader } from './profileHeader'
import { useAuth } from '@/context/AuthContext'

export function ProfileInfo() {
  const { user, loading } = useAuth();

  // ⛔ Quan trọng: khi AuthContext CHƯA load xong → KHÔNG render gì cả
  if (loading) return null;

  // Giải pháp fallback avatar
  const username = user?.username
  const avatar = user?.avatar

  return (
    <div>
      <ProfileHeader
        username={username ?? "Unknown"}
        verified
        following
        posts={1861}
        followers="4M"
        followingCount={454}
        name="Marques Brownlee"
        bio="I promise I won't overdo the filters."
        website="https://mkbhd.com"
        avatar={avatar ?? "https://cdn-icons-png.flaticon.com/512/9131/9131529.png"}
      />
    </div>
  );
}
