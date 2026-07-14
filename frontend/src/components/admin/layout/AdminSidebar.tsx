import { NavLink } from "react-router-dom";

import {
  FiActivity,
  FiBarChart2,
  FiBookOpen,
  FiChevronLeft,
  FiChevronRight,
  FiCpu,
  FiGrid,
  FiLayers,
  FiMessageSquare,
  FiUsers,
  FiX,
} from "react-icons/fi";

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
            <div
              className="
                flex
                h-11
                w-11
                shrink-0
                items-center
                justify-center

                rounded-xl

                bg-blue-600

                text-xl
                text-white

                shadow-lg
                shadow-blue-600/20
              "
            >
              <FiMessageSquare />
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
            SYSTEM STATUS
        ========================================== */}

        <div className="app-border shrink-0 border-t p-3">
          {collapsed ? (
            <div
              title="System Online"
              className="
                hidden
                h-11
                items-center
                justify-center

                lg:flex
              "
            >
              <span
                className="
                  h-2.5
                  w-2.5

                  rounded-full

                  bg-emerald-500

                  shadow-[0_0_10px_rgba(16,185,129,0.5)]
                "
              />
            </div>
          ) : (
            <div
              className="
                app-surface-secondary
                app-border

                rounded-xl
                border

                px-4
                py-3
              "
            >
              <div className="flex items-center gap-2">
                <span
                  className="
                    h-2
                    w-2

                    rounded-full

                    bg-emerald-500
                  "
                />

                <p className="text-sm font-semibold">
                  System Online
                </p>
              </div>

              <p
                className="
                  app-text-secondary

                  mt-2

                  text-xs
                  leading-5
                "
              >
                All services operational
              </p>
            </div>
          )}
        </div>
      </aside>
    </>
  );
}