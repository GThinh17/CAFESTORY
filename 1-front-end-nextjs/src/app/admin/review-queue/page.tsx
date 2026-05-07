"use client";

import { useEffect, useState } from "react";
import axios from "axios";
import Image from "next/image";
import { useAuth } from "@/context/AuthContext";

export default function ReviewQueuePage() {
  const [blogs, setBlogs] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const { token } = useAuth();
 
  useEffect(() => {
    fetchReviewQueue();
  }, [token]);

  const fetchReviewQueue = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/blogs/admin/review-queue", {
        headers: { Authorization: `Bearer ${token}` }
      });
      console.log(`token: ${token}`);
      setBlogs(res.data.content || res.data.data.content || []);
    } catch (error) {
      console.error("Failed to load review queue", error);
    } finally {
      setLoading(false);
    }
  };

  const handleApprove = async (id: string) => {
    try {
      await axios.post(`http://localhost:8080/api/blogs/${id}/approve`, {}, {
        headers: { Authorization: `Bearer ${token}` }
      });
      setBlogs(blogs.filter(b => b.id !== id));
      alert("Đã duyệt qua bài viết thành công!");
    } catch (error) {
      alert("Lỗi khi duyệt bài");
    }
  };

  const handleDelete = async (id: string) => {
    if (!confirm("Chắc chắn xóa bài viết này?")) return;
    try {
      await axios.delete(`http://localhost:8080/api/blogs/${id}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      setBlogs(blogs.filter(b => b.id !== id));
      alert("Đã xóa ẩn bài viết.");
    } catch (error) {
      alert("Lỗi khi xóa bài");
    }
  };

  if (loading) return <div className="p-10 font-bold text-center">Đang tải cấu hình AI Queue...</div>;

  return (
    <div className="p-8 max-w-5xl mx-auto">
      <h1 className="text-3xl font-bold mb-6">AI Moderation Queue</h1>
      <p className="text-gray-600 mb-8">Danh sách các bài viết có chủ đề không khớp với quán Cafe (Safe Score &lt; 50%). Hệ thống tạm thời đánh dấu là có thể vi phạm.</p>

      {blogs.length === 0 ? (
        <div className="bg-green-50 text-green-700 p-4 rounded-lg text-center">Không có bài viết vi phạm nào đang đợi.</div>
      ) : (
        <div className="grid gap-6">
          {blogs.map(blog => (
            <div key={blog.id} className="border-l-4  bg-white shadow-xl rounded-lg p-6 flex flex-col md:flex-row gap-6">
              <div className="flex-1">
                <div className="flex justify-between items-start">
                  <div>
                    <h3 className="font-semibold text-lg">{blog.caption || "Không có nội dung"}</h3>
                    <p className="text-sm text-gray-500">Đăng bởi: {blog.userFullName || 'Anonymous'} - {new Date(blog.createdAt).toLocaleString()}</p>
                  </div>
                  <div className="bg-red-100 text-red-800 px-3 py-1 rounded font-bold text-lg">
                    Safe Score: {Math.round((blog.safeScore || 0) * 100)}%
                  </div>
                </div>

                <div className="mt-4 flex gap-2 overflow-x-auto pb-2">
                  {blog.mediaUrls?.map((url: string, index: number) => (
                    <Image key={index} src={url} alt="Hinh anh AI" width={150} height={150} className="object-cover rounded border" />
                  ))}
                </div>
              </div>

              <div className="md:w-48 flex flex-col justify-center gap-3 border-t md:border-t-0 md:border-l pt-4 md:pt-0 md:pl-4">
                <button 
                  onClick={() => handleApprove(blog.id)}
                  className="bg-gray-100 hover:bg-green-600 hover:text-white transition-colors text-gray-700 font-medium py-2 px-4 rounded w-full"
                >
                  Xác nhận An Toàn
                </button>
                <button 
                  onClick={() => handleDelete(blog.id)}
                  className="bg-red-500 hover:bg-red-600 text-white font-medium py-2 px-4 rounded w-full"
                >
                  Xóa bỏ (Soft Delete)
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
