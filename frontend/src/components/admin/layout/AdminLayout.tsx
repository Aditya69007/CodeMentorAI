import { useState } from "react";
import { Outlet } from "react-router-dom";

import AdminSidebar from "./AdminSidebar";
import AdminTopbar from "./AdminTopbar";

export default function AdminLayout() {
  const [sidebarCollapsed, setSidebarCollapsed] = useState<boolean>(() => {
    return localStorage.getItem("adminSidebarCollapsed") === "true";
  });

  const [mobileSidebarOpen, setMobileSidebarOpen] = useState(false);

  const toggleSidebar = () => {
    setSidebarCollapsed((current) => {
      const next = !current;

      localStorage.setItem(
        "adminSidebarCollapsed",
        String(next)
      );

      return next;
    });
  };

  return (
    <div className="app-bg min-h-screen app-text-primary">
      <AdminSidebar
        collapsed={sidebarCollapsed}
        mobileOpen={mobileSidebarOpen}
        onToggleCollapse={toggleSidebar}
        onCloseMobile={() => setMobileSidebarOpen(false)}
      />

      <div
        className={`
          min-h-screen
          transition-[padding-left]
          duration-300
          ease-in-out
          ${
            sidebarCollapsed
              ? "lg:pl-[76px]"
              : "lg:pl-64"
          }
        `}
      >
        <AdminTopbar
          onOpenMobileSidebar={() =>
            setMobileSidebarOpen(true)
          }
        />

        <main className="px-5 py-7 sm:px-7 lg:px-9">
          <Outlet />
        </main>
      </div>
    </div>
  );
}