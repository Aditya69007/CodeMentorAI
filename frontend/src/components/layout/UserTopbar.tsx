import {
  FiChevronDown,
  FiLogOut,
  FiMoon,
  FiSettings,
  FiSun,
  FiUser,
  FiZap,
  FiBriefcase,
  FiShield,
} from "react-icons/fi";

import { useNavigate } from "react-router-dom";
import { useTheme } from "../../hooks/useTheme";
import { useAuth } from "../../hooks/useAuth";
import { useEffect, useRef, useState } from "react";
import UserAvatar from "../common/UserAvatar";
import NotificationBell from "../notifications/NotificationBell";

export default function UserTopbar() {

    const navigate = useNavigate();

const {
  theme,
  toggleTheme,
} = useTheme();

const { user, logout } = useAuth();
const [profileOpen, setProfileOpen] = useState(false);
const profileRef = useRef<HTMLDivElement>(null);

useEffect(() => {
  const handleClickOutside = (event: MouseEvent) => {
    if (profileRef.current && !profileRef.current.contains(event.target as Node)) {
      setProfileOpen(false);
    }
  };

  document.addEventListener("mousedown", handleClickOutside);
  return () => document.removeEventListener("mousedown", handleClickOutside);
}, []);

const fullName = `${user?.firstName ?? ""} ${user?.lastName ?? ""}`.trim();



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

      {/* LEFT */}

      <div>

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

          <FiZap />

          <span>

            DEVELOPER CONTROL CENTER

          </span>

        </div>

        <p
          className="
            app-text-secondary

            mt-1

            hidden

            text-sm

            md:block
          "
        >

          Manage your coding journey with AI assistance.

        </p>

      </div>



    {/* RIGHT */}

    <div className="flex items-center gap-2">

    <button

        type="button"

        onClick={toggleTheme}

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

        hover:text-blue-500
        "

    >

        {
        theme === "DARK"

            ? <FiSun />

            : <FiMoon />

        }

    </button>

    <NotificationBell />

    <div ref={profileRef} className="relative">
        <button
        onClick={() => setProfileOpen(!profileOpen)}
        className="flex items-center gap-3 rounded-xl px-2 py-1.5 hover:bg-slate-500/10 transition"
        >

        {user ? (
          <UserAvatar
            user={user}
            size="sm"
          />
        ) : (
          <div className="flex h-10 w-10 items-center justify-center rounded-full bg-slate-700">
            <FiUser />
          </div>
        )}

        <div className="hidden xl:block text-left">

            <p className="max-w-40 truncate text-sm font-semibold">

                {fullName || "Developer"}

            </p>

            <p className="app-text-secondary max-w-40 truncate text-xs">

                {user?.email}

            </p>

        </div>

        <FiChevronDown
            className={`hidden xl:block transition-transform duration-200 ${
                profileOpen ? "rotate-180" : ""
            }`}
        />
        </button>
        {profileOpen && (
  <div className="absolute right-0 top-14 w-64 rounded-xl border app-border app-surface-secondary shadow-2xl overflow-hidden">

    <div className="border-b app-border p-4">
      <p className="font-semibold">{fullName}</p>
      <p className="text-xs app-text-secondary truncate">{user?.email}</p>

      <span className="mt-3 inline-flex rounded-full bg-blue-500/10 px-2 py-1 text-[11px] font-semibold text-blue-500">
       {user?.role}
      </span>
    </div>

    <div className="p-2">

        <button
        onClick={() => {
            setProfileOpen(false);
            navigate("/account/profile");
        }}
        className="flex w-full items-center gap-3 rounded-lg px-3 py-2 hover:bg-slate-500/10"
        >
        <FiUser />
        Profile
      </button>

      <button
        onClick={() => {
          setProfileOpen(false);
          navigate("/portfolio");
        }}
        className="flex w-full items-center gap-3 rounded-lg px-3 py-2 hover:bg-slate-500/10"
      >
        <FiBriefcase />
        Portfolio
      </button>

        <button
        onClick={() => {
            setProfileOpen(false);
            navigate("/account/settings");
        }}
        className="flex w-full items-center gap-3 rounded-lg px-3 py-2 hover:bg-slate-500/10"
        >
        <FiSettings />
        Settings
      </button>

      <button
        onClick={() => {
          setProfileOpen(false);
          navigate("/account/sessions");
        }}
        className="flex w-full items-center gap-3 rounded-lg px-3 py-2 hover:bg-slate-500/10"
      >
        <FiShield />
        Security Center
      </button>

    </div>
    <div className="border-t app-border p-2">

      <button
        onClick={() => {
          logout();
          navigate("/login");
        }}
        className="flex w-full items-center gap-3 rounded-lg px-3 py-2 text-red-500 hover:bg-red-500/10"
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