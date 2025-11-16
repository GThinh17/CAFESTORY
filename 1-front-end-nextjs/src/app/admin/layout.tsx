import React from "react";
import Sidebar from "@/components/admin/Sidebar";

export default function AdminLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div className="min-h-screen bg-slate-50 text-slate-900 grid grid-cols-12 gap-6 p-6">
      <aside className="col-span-12 md:col-span-3 lg:col-span-2 sticky top-6 h-fit">
        <Sidebar />
      </aside>

      <main className="col-span-12 md:col-span-9 lg:col-span-10">
        {children}
      </main>
    </div>
  );
}
