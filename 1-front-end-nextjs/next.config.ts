import { devIndicatorServerState } from "next/dist/server/dev/dev-indicator-server-state";

/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  images: {
    remotePatterns: [
      { protocol: "https", hostname: "res.cloudinary.com" }, // thêm domain này
      { protocol: "https", hostname: "cdn-icons-png.flaticon.com" },
      { protocol: "https", hostname: "avatars.githubusercontent.com" },
      { protocol: "https", hostname: "cdn.pixabay.com" },
      { protocol: "https", hostname: "images.unsplash.com" },
      { protocol: "https", hostname: "cdn.example.com" },
      {
        protocol: "https",
        hostname: "scontent.fsgn2-9.fna.fbcdn.net",
      },
      {
        protocol: "https",
        hostname: "scontent.xx.fbcdn.net",
      },
      {
        protocol: "https",
        hostname: "**.fbcdn.net",
      },
      {
        protocol: "https",
        hostname: "th.bing.com",
      },
      {
        protocol: "https",
        hostname: "hdwpro.com",
      },
      {
        protocol: "https",
        hostname: "scontent.fsgn2-9.fna.fbcdn.net",
      },
      {
        protocol: "https",
        hostname: "scontent.fsgn2-8.fna.fbcdn.net",
      },
      {
        protocol: "https",
        hostname: "www.facebook.com",
      },
    ],
  },
  devIndicators: false,
};

export default nextConfig;
