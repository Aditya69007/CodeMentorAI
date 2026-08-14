import {
  useEffect,
  useRef,
  useState,
} from "react";
import NotificationBell from "../../notifications/NotificationBell";
import { useNavigate } from "react-router-dom";

import {
  FiChevronDown,
  FiLogOut,
  FiMenu,
  FiMoon,
  FiSettings,
  FiShield,
  FiSun,
  FiUser,
} from "react-icons/fi";

import { useAuth } from "../../../hooks/useAuth";
import { useTheme } from "../../../hooks/useTheme";

interface AdminTopbarProps {
  onOpenMobileSidebar: () => void;
}

export default function AdminTopbar({
  onOpenMobileSidebar,
}: AdminTopbarProps) {
  const navigate = useNavigate();

  const { user, logout } = useAuth();

  const { theme, toggleTheme } = useTheme();

  const [profileOpen, setProfileOpen] =
    useState(false);

  const profileRef =
    useRef<HTMLDivElement>(null);

  const fullName =
    `${user?.firstName ?? ""} ${user?.lastName ?? ""}`.trim();

  const initials =
    `${user?.firstName?.charAt(0) ?? ""}${user?.lastName?.charAt(0) ?? ""}`;

  const handleLogout = () => {
    setProfileOpen(false);

    logout();

    navigate("/login", {
      replace: true,
    });
  };

  useEffect(() => {
    const handleOutsideClick = (
      event: MouseEvent
    ) => {
      if (
        profileRef.current &&
        !profileRef.current.contains(
          event.target as Node
        )
      ) {
        setProfileOpen(false);
      }
    };

    document.addEventListener(
      "mousedown",
      handleOutsideClick
    );

    return () => {
      document.removeEventListener(
        "mousedown",
        handleOutsideClick
      );
    };
  }, []);

  return (
    <header
      className="
        app-surface
        app-border

        sticky
        top-0
        z-30

        flex
        h-20
        items-center
        justify-between

        border-b

        px-4

        sm:px-6
        lg:px-8
      "
    >
      {/* ==========================================
          LEFT
      ========================================== */}

      <div className="flex min-w-0 items-center gap-3">
        <button
          type="button"
          onClick={onOpenMobileSidebar}
          aria-label="Open sidebar"
          className="
            app-surface-secondary
            app-border
            app-text-secondary

            flex
            h-10
            w-10
            shrink-0
            items-center
            justify-center

            rounded-lg
            border

            transition

            hover:!text-blue-500

            lg:hidden
          "
        >
          <FiMenu />
        </button>

        <div className="min-w-0">
          <div
            className="
              flex
              items-center
              gap-2

              text-xs
              font-semibold
              text-blue-500
            "
          >
            <FiShield />

            <span>
              ADMIN CONTROL CENTER
            </span>
          </div>

          <p
            className="
              app-text-secondary

              mt-1

              hidden
              truncate

              text-sm

              md:block
            "
          >
            Manage and monitor the CodeMentor AI platform
          </p>
        </div>
      </div>

      {/* ==========================================
          RIGHT
      ========================================== */}

      <div className="flex shrink-0 items-center gap-2">
        {/* THEME */}

        <button
          type="button"
          onClick={toggleTheme}
          aria-label="Toggle theme"
          title={
            theme === "DARK"
              ? "Switch to light mode"
              : "Switch to dark mode"
          }
          className="
            app-surface-secondary
            app-border
            app-text-secondary

            flex
            h-10
            w-10
            items-center
            justify-center

            rounded-lg
            border

            transition

            hover:!text-blue-500
          "
        >
          {theme === "DARK" ? (
            <FiSun />
          ) : (
            <FiMoon />
          )}
        </button>

      <NotificationBell />

        {/* PROFILE */}

        <div
          ref={profileRef}
          className="relative ml-1"
        >
          <button
            type="button"
            onClick={() =>
              setProfileOpen((current) => !current)
            }
            className="
              flex
              items-center
              gap-3

              rounded-xl

              px-2
              py-1.5

              transition

              hover:bg-slate-500/10
            "
          >
            <div
              className="
                flex
                h-10
                w-10
                shrink-0
                items-center
                justify-center

                rounded-full

                bg-blue-600

                text-sm
                font-bold
                text-white

                shadow-md
                shadow-blue-600/20
              "
            >
              {initials || "A"}
            </div>

            <div className="hidden text-left xl:block">
              <p
                className="
                  max-w-40
                  truncate

                  text-sm
                  font-semibold
                "
              >
                {fullName || "Administrator"}
              </p>

              <p
                className="
                  app-text-secondary

                  max-w-40
                  truncate

                  text-xs
                "
              >
                {user?.email}
              </p>
            </div>

            <FiChevronDown
              className={`
                app-text-secondary

                hidden

                transition-transform
                duration-200

                xl:block

                ${
                  profileOpen
                    ? "rotate-180"
                    : ""
                }
              `}
            />
          </button>

          {/* PROFILE DROPDOWN */}

          {profileOpen && (
            <div
              className="
                app-surface-secondary
                app-border

                absolute
                right-0
                top-[calc(100%+10px)]

                w-64

                overflow-hidden

                rounded-xl
                border

                shadow-2xl
              "
            >
              <div className="app-border border-b px-4 py-4">
                <p className="text-sm font-semibold">
                  {fullName || "Administrator"}
                </p>

                <p
                  className="
                    app-text-secondary

                    mt-1

                    truncate

                    text-xs
                  "
                >
                  {user?.email}
                </p>

                <span
                  className="
                    mt-3
                    inline-flex

                    rounded-full

                    bg-blue-500/10

                    px-2.5
                    py-1

                    text-[11px]
                    font-semibold
                    text-blue-500
                  "
                >
                  ADMINISTRATOR
                </span>
              </div>

              <div className="p-2">
              <button
                type="button"
                onClick={() => {
                  setProfileOpen(false);
                  navigate("/admin/profile");
                }}
                className="
                  app-text-secondary
                  flex
                  w-full
                  items-center
                  gap-3
                  rounded-lg
                  px-3
                  py-2.5
                  text-sm
                  transition
                  hover:bg-slate-500/10
                  hover:!text-blue-500
                "
              >
                  <FiUser />

                  Profile
                </button>

                <button
                  type="button"
                  onClick={() => {
                    setProfileOpen(false);
                    navigate("/admin/settings");
                  }}
                  className="
                    app-text-secondary
                    flex
                    w-full
                    items-center
                    gap-3
                    rounded-lg
                    px-3
                    py-2.5
                    text-sm
                    transition
                    hover:bg-slate-500/10
                    hover:!text-blue-500
                  "
                >
                  <FiSettings />
                  Settings
                </button>
              </div>

              <div className="app-border border-t p-2">
                <button
                  type="button"
                  onClick={handleLogout}
                  className="
                    flex
                    w-full
                    items-center
                    gap-3

                    rounded-lg

                    px-3
                    py-2.5

                    text-sm
                    text-red-500

                    transition

                    hover:bg-red-500/10
                  "
                >
                  <FiLogOut />

                  Logout
                </button>
              </div>
            </div>
          )}
        </div>
      </div>
    </header>
  );
}