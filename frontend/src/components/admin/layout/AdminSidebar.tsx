import { NavLink, useNavigate } from "react-router-dom";
import {
  FiActivity,
  FiBarChart2,
  FiBookOpen,
  FiChevronLeft,
  FiChevronRight,
  FiCpu,
  FiGrid,
  FiLayers,
  FiUsers,
  FiX,
  FiLogOut,
  FiShield,
} from "react-icons/fi";
import { useAuth } from "../../../hooks/useAuth";
import { useState } from "react";

interface AdminSidebarProps {
  collapsed: boolean;
  mobileOpen: boolean;
  onToggleCollapse: () => void;
  onCloseMobile: () => void;
}

const navigationItems = [
  {
    label: "Dashboard",
    path: "/admin/dashboard",
    icon: FiGrid,
  },
  {
    label: "Problems",
    path: "/admin/problems",
    icon: FiBookOpen,
  },
  {
    label: "Topics",
    path: "/admin/topics",
    icon: FiLayers,
  },
  {
    label: "Users",
    path: "/admin/users",
    icon: FiUsers,
  },
  {
    label: "Submissions",
    path: "/admin/submissions",
    icon: FiActivity,
  },
  {
    label: "AI Analytics",
    path: "/admin/ai-analytics",
    icon: FiCpu,
  },
  {
    label: "Platform Analytics",
    path: "/admin/analytics",
    icon: FiBarChart2,
  },
];

export default function AdminSidebar({
  collapsed,
  mobileOpen,
  onToggleCollapse,
  onCloseMobile,
}: AdminSidebarProps) {

  const navigate = useNavigate();
  const { isSuperAdmin, logout } = useAuth();

  const [showRestrictedModal, setShowRestrictedModal] = useState(false);

  function handleLogout() {
    logout();

    navigate("/login", {
      replace: true,
    });
  }

  return (
    <>
      {mobileOpen && (
        <button
          type="button"
          aria-label="Close sidebar"
          onClick={onCloseMobile}
          className="
            fixed
            inset-0
            z-40
            bg-black/60
            backdrop-blur-sm
            lg:hidden
          "
        />
      )}

      <aside
        className={`
          app-surface
          app-border

          fixed
          inset-y-0
          left-0
          z-50

          flex
          flex-col

          border-r

          transition-all
          duration-300
          ease-in-out

          ${
            mobileOpen
              ? "translate-x-0"
              : "-translate-x-full"
          }

          w-64

          lg:translate-x-0

          ${
            collapsed
              ? "lg:w-[76px]"
              : "lg:w-64"
          }
        `}
      >
        {/* ==========================================
            BRAND
        ========================================== */}

        <div
          className={`
            app-border

            relative
            flex
            h-20
            shrink-0
            items-center

            border-b

            ${
              collapsed
                ? "lg:justify-center lg:px-0"
                : "px-4"
            }
          `}
        >
          <div className="flex min-w-0 items-center gap-3">
            <div className="flex h-10 w-10 shrink-0 items-center justify-center rounded-xl overflow-hidden">
              <img
                src="/brain-logo.png"
                alt="CodeMentorAI"
                className="h-full w-full object-contain"
              />
            </div>

            <div
              className={`
                min-w-0
                overflow-hidden

                ${
                  collapsed
                    ? "lg:hidden"
                    : "block"
                }
              `}
            >
              <p className="truncate font-bold">
                CodeMentorAI
              </p>

              <p className="app-text-secondary mt-0.5 text-xs">
                Admin Platform
              </p>
            </div>
          </div>

          {/* MOBILE CLOSE */}

          <button
            type="button"
            onClick={onCloseMobile}
            aria-label="Close sidebar"
            className="
              app-text-secondary

              ml-auto

              flex
              h-9
              w-9
              items-center
              justify-center

              rounded-lg

              transition

              hover:bg-slate-500/10

              lg:hidden
            "
          >
            <FiX />
          </button>

          {/* DESKTOP EDGE TOGGLE */}

          <button
            type="button"
            onClick={onToggleCollapse}
            aria-label={
              collapsed
                ? "Expand sidebar"
                : "Collapse sidebar"
            }
            title={
              collapsed
                ? "Expand sidebar"
                : "Collapse sidebar"
            }
            className="
              app-surface-secondary
              app-border
              app-text-secondary

              absolute
              -right-3.5
              top-1/2

              z-50

              hidden
              h-7
              w-7

              -translate-y-1/2

              items-center
              justify-center

              rounded-full
              border

              text-xs

              shadow-md

              transition

              hover:text-blue-500

              lg:flex
            "
          >
            {collapsed ? (
              <FiChevronRight />
            ) : (
              <FiChevronLeft />
            )}
          </button>
        </div>

        {/* ==========================================
            NAVIGATION
        ========================================== */}

        <nav className="flex-1 overflow-y-auto overflow-x-hidden px-3 py-5">
          {!collapsed && (
            <p
              className="
                app-text-secondary

                mb-3
                px-3

                text-[11px]
                font-semibold
                uppercase
                tracking-[0.14em]
              "
            >
              Management
            </p>
          )}

          <div className="space-y-1">
            {navigationItems.map((item) => {
              const Icon = item.icon;

              return (
                <NavLink
                  key={item.path}
                  to={item.path}
                  onClick={onCloseMobile}
                  className={({ isActive }) =>
                    `
                      group
                      relative

                      flex
                      h-11
                      items-center

                      rounded-lg

                      text-sm
                      font-medium

                      transition-all
                      duration-200

                      ${
                        collapsed
                          ? "lg:justify-center lg:px-0"
                          : "gap-3 px-3"
                      }

                      ${
                        isActive
                          ? "bg-blue-500/10 text-blue-500"
                          : "app-text-secondary hover:bg-slate-500/10 hover:text-blue-500"
                      }
                    `
                  }
                >
                  <Icon className="shrink-0 text-[19px]" />

                  <span
                    className={`
                      whitespace-nowrap

                      ${
                        collapsed
                          ? "lg:hidden"
                          : "block"
                      }
                    `}
                  >
                    {item.label}
                  </span>

                  {collapsed && (
                    <div
                      className="
                        app-surface-secondary
                        app-border

                        pointer-events-none

                        absolute
                        left-[calc(100%+12px)]
                        top-1/2

                        z-[100]

                        hidden

                        -translate-y-1/2

                        whitespace-nowrap

                        rounded-lg
                        border

                        px-3
                        py-2

                        text-xs
                        font-medium

                        opacity-0

                        shadow-xl

                        transition-opacity

                        group-hover:opacity-100

                        lg:block
                      "
                    >
                      {item.label}
                    </div>
                  )}
                </NavLink>
              );
            })}
          </div>
        </nav>

        {/* ==========================================
            ADMIN ACTIONS
        ========================================== */}

        <div className="app-border shrink-0 border-t p-3">
          <div className="space-y-2">

            {/* SUPER ADMIN */}

            <NavLink
              to="/admin/super-admin"
              onClick={(event) => {
                if (!isSuperAdmin) {
                  event.preventDefault();
                  setShowRestrictedModal(true);
                }

                onCloseMobile();
              }}
              className={({ isActive }) =>
                `
                group
                relative
                flex
                h-11
                items-center
                rounded-xl
                text-sm
                font-medium
                transition-all
                duration-200

                ${
                  collapsed
                    ? "lg:justify-center lg:px-0"
                    : "gap-3 px-3"
                }

                ${
                  isActive
                    ? "bg-red-500/10 text-red-400"
                    : "app-text-secondary hover:!bg-red-500/10 hover:!text-red-400"
                }
                `
              }
            >
              <FiShield className="shrink-0 text-[19px]" />

              <span
                className={`
                  whitespace-nowrap
                  ${
                    collapsed
                      ? "lg:hidden"
                      : "block"
                  }
                `}
              >
                Super Admin
              </span>

              {collapsed && (
                <div
                  className="
                    app-surface-secondary
                    app-border
                    pointer-events-none
                    absolute
                    left-[calc(100%+12px)]
                    top-1/2
                    z-[100]
                    hidden
                    -translate-y-1/2
                    whitespace-nowrap
                    rounded-lg
                    border
                    px-3
                    py-2
                    text-xs
                    font-medium
                    opacity-0
                    shadow-xl
                    transition-opacity
                    group-hover:opacity-100
                    lg:block
                  "
                >
                  Super Admin
                </div>
              )}
            </NavLink>


            {/* LOGOUT */}

            <button
              type="button"
              onClick={handleLogout}
              className={`
                group
                relative
                flex
                h-11
                w-full
                items-center
                rounded-xl
                text-sm
                font-medium
                text-red-400
                transition-all
                duration-200
                hover:!bg-red-500/10
                hover:!text-red-400

                ${
                  collapsed
                    ? "lg:justify-center lg:px-0"
                    : "gap-3 px-3"
                }
              `}
            >
              <FiLogOut className="shrink-0 text-[19px]" />

              <span
                className={`
                  whitespace-nowrap
                  ${
                    collapsed
                      ? "lg:hidden"
                      : "block"
                  }
                `}
              >
                Logout
              </span>

              {collapsed && (
                <div
                  className="
                    app-surface-secondary
                    app-border
                    pointer-events-none
                    absolute
                    left-[calc(100%+12px)]
                    top-1/2
                    z-[100]
                    hidden
                    -translate-y-1/2
                    whitespace-nowrap
                    rounded-lg
                    border
                    px-3
                    py-2
                    text-xs
                    font-medium
                    opacity-0
                    shadow-xl
                    transition-opacity
                    group-hover:opacity-100
                    lg:block
                  "
                >
                  Logout
                </div>
              )}
            </button>

          </div>
        </div>
        
      </aside>

      {showRestrictedModal && (
        <div className="fixed inset-0 z-[9999] flex items-center justify-center bg-black/70 p-4 backdrop-blur-sm">

          <div className="app-surface app-border w-full max-w-md rounded-3xl border p-6 shadow-2xl">

            <div className="flex items-start justify-between gap-4">

              <div className="flex items-center gap-4">

                <div className="flex h-12 w-12 shrink-0 items-center justify-center rounded-xl bg-red-500/10">
                  <FiShield className="text-xl text-red-400" />
                </div>

                <div>
                  <h2 className="text-xl font-bold">
                    Access Restricted
                  </h2>

                  <p className="mt-1 text-sm app-text-secondary">
                    Super Admin only
                  </p>
                </div>

              </div>

              <button
                type="button"
                onClick={() => setShowRestrictedModal(false)}
                className="rounded-xl p-2 app-text-secondary transition hover:bg-slate-500/10 hover:text-white"
                aria-label="Close"
              >
                <FiX className="text-xl" />
              </button>

            </div>

            <div className="mt-6 rounded-2xl border border-red-500/20 bg-red-500/5 p-4">

              <p className="text-sm leading-6 app-text-secondary">
                This section is restricted to the Creator and Super Admin.
                You do not have permission to manage administrators.
              </p>

            </div>

            <div className="mt-6 flex justify-end">

              <button
                type="button"
                onClick={() => setShowRestrictedModal(false)}
                className="rounded-xl bg-blue-600 px-5 py-2.5 font-semibold text-white transition hover:bg-blue-700"
              >
                Close
              </button>

            </div>

          </div>

        </div>
      )}
    
    </>
  );
}