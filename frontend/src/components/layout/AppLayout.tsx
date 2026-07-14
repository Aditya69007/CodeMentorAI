import {
  useState,
} from "react";

import {
  NavLink,
  Outlet,
  useNavigate,
} from "react-router-dom";

import {
  FiActivity,
  FiBarChart2,
  FiBookOpen,
  FiChevronLeft,
  FiChevronRight,
  FiCode,
  FiHome,
  FiList,
  FiLogOut,
  FiMenu,
  FiMessageSquare,
  FiMoon,
  FiRefreshCw,
  FiSun,
  FiTrendingUp,
  FiX,
} from "react-icons/fi";

import {
  useAuth,
} from "../../hooks/useAuth";

import {
  useTheme,
} from "../../hooks/useTheme";


// ==================================================
// NAVIGATION
// ==================================================

const navigationItems = [

  {
    to: "/dashboard",
    label: "Dashboard",
    icon: FiHome,
  },

  {
    to: "/problems",
    label: "Problems",
    icon: FiList,
  },

  {
    to: "/mistake-memory",
    label: "Mistake Memory",
    icon: FiActivity,
  },

  {
    to: "/developer-skills",
    label: "Developer Skills",
    icon: FiTrendingUp,
  },

  {
    to: "/learning-plan",
    label: "Learning Plan",
    icon: FiBookOpen,
  },

  {
    to: "/revision-plan",
    label: "Revision Plan",
    icon: FiRefreshCw,
  },

  {
    to: "/growth-report",
    label: "Growth Report",
    icon: FiBarChart2,
  },

  {
    to: "/interview",
    label: "Interview",
    icon: FiMessageSquare,
  },

];


// ==================================================
// COMPONENT
// ==================================================

export default function AppLayout() {

  const navigate =
    useNavigate();


  const {
    logout,
  } = useAuth();


  const {
    theme,
    toggleTheme,
  } = useTheme();


  const [
    sidebarCollapsed,
    setSidebarCollapsed,
  ] = useState(false);


  const [
    mobileMenuOpen,
    setMobileMenuOpen,
  ] = useState(false);


  // ==================================================
  // LOGOUT
  // ==================================================

  const handleLogout = () => {

    logout();

    navigate("/login");

  };


  // ==================================================
  // CLOSE MOBILE MENU
  // ==================================================

  const closeMobileMenu = () => {

    setMobileMenuOpen(false);

  };


  // ==================================================
  // NAVIGATION STYLE
  // ==================================================

  const navLinkClass = ({
    isActive,
  }: {
    isActive: boolean;
  }) => {

    return `
      group
      flex
      h-11
      items-center
      rounded-lg
      transition-all
      duration-200
      ${
        sidebarCollapsed
          ? "justify-center px-2"
          : "gap-3 px-3"
      }
      ${
        isActive
          ? "bg-blue-600 text-white shadow-sm shadow-blue-600/20"
          : "app-text-secondary hover:bg-blue-500/10 hover:text-blue-500"
      }
    `;

  };


  // ==================================================
  // PAGE
  // ==================================================

  return (

    <div className="app-background min-h-screen">


      {/* ==================================================
          DESKTOP SIDEBAR
      ================================================== */}

      <aside
        className={`
          app-surface
          app-border
          fixed
          inset-y-0
          left-0
          z-50
          hidden
          flex-col
          border-r
          transition-all
          duration-300
          lg:flex
          ${
            sidebarCollapsed
              ? "w-[80px]"
              : "w-[260px]"
          }
        `}
      >


        {/* ==================================================
            LOGO
        ================================================== */}

        <div
          className={`
            app-border
            flex
            h-20
            shrink-0
            items-center
            border-b
            ${
              sidebarCollapsed
                ? "justify-center px-3"
                : "justify-between px-5"
            }
          `}
        >


          <button

            type="button"

            onClick={() => {

              if (sidebarCollapsed) {

                setSidebarCollapsed(false);

                return;

              }

              navigate("/dashboard");

            }}
            className="flex min-w-0 items-center gap-3"

            title={
              sidebarCollapsed
                ? "CodeMentorAI"
                : undefined
            }

          >


            <div className="flex h-10 w-10 shrink-0 items-center justify-center rounded-xl bg-blue-600 text-white shadow-sm shadow-blue-600/30">

              <FiCode size={20} />

            </div>


            {
              !sidebarCollapsed && (

                <span className="truncate text-lg font-bold tracking-tight">

                  CodeMentor

                  <span className="text-blue-500">

                    AI

                  </span>

                </span>

              )
            }


          </button>


          {
            !sidebarCollapsed && (

              <button

                type="button"

                onClick={() =>
                  setSidebarCollapsed(true)
                }

                className="app-hover app-text-secondary flex h-8 w-8 shrink-0 items-center justify-center rounded-lg"

                title="Collapse sidebar"

              >

                <FiChevronLeft size={18} />

              </button>

            )
          }


        </div>


        {/* ==================================================
            NAVIGATION
        ================================================== */}

        <nav className="flex-1 overflow-y-auto px-3 py-5">


          {
            !sidebarCollapsed && (

              <p className="app-text-secondary mb-3 px-3 text-[11px] font-bold uppercase tracking-[0.14em]">

                Platform

              </p>

            )
          }


          <div className="space-y-1">


            {
              navigationItems.map(
                ({
                  to,
                  label,
                  icon: Icon,
                }) => (

                  <NavLink

                    key={to}

                    to={to}

                    className={navLinkClass}

                    title={
                      sidebarCollapsed
                        ? label
                        : undefined
                    }

                  >


                    <Icon
                      className="shrink-0"
                      size={19}
                    />


                    {
                      !sidebarCollapsed && (

                        <span className="truncate text-sm font-medium">

                          {
                            label
                          }

                        </span>

                      )
                    }


                  </NavLink>

                )
              )
            }


          </div>


        </nav>


        {/* ==================================================
            SIDEBAR BOTTOM
        ================================================== */}

        <div className="app-border shrink-0 border-t p-3">


          {
            sidebarCollapsed && (

              <button

                type="button"

                onClick={() =>
                  setSidebarCollapsed(false)
                }

                className="app-hover app-text-secondary mb-2 flex h-11 w-full items-center justify-center rounded-lg"

                title="Expand sidebar"

              >

                <FiChevronRight size={19} />

              </button>

            )
          }


          <button

            type="button"

            onClick={toggleTheme}

            className={`
              app-hover
              app-text-secondary
              flex
              h-11
              w-full
              items-center
              rounded-lg
              ${
                sidebarCollapsed
                  ? "justify-center"
                  : "gap-3 px-3"
              }
            `}

            title={
              theme === "dark"
                ? "Switch to light mode"
                : "Switch to dark mode"
            }

          >


            {
              theme === "dark"
                ? (
                  <FiSun
                    className="shrink-0"
                    size={19}
                  />
                )
                : (
                  <FiMoon
                    className="shrink-0"
                    size={19}
                  />
                )
            }


            {
              !sidebarCollapsed && (

                <span className="text-sm font-medium">

                  {
                    theme === "dark"
                      ? "Light Mode"
                      : "Dark Mode"
                  }

                </span>

              )
            }


          </button>


          <button

            type="button"

            onClick={handleLogout}

            className={`
              mt-1
              flex
              h-11
              w-full
              items-center
              rounded-lg
              text-red-500
              transition
              hover:bg-red-500/10
              ${
                sidebarCollapsed
                  ? "justify-center"
                  : "gap-3 px-3"
              }
            `}

            title="Logout"

          >


            <FiLogOut
              className="shrink-0"
              size={19}
            />


            {
              !sidebarCollapsed && (

                <span className="text-sm font-medium">

                  Logout

                </span>

              )
            }


          </button>


        </div>


      </aside>


      {/* ==================================================
          MOBILE HEADER
      ================================================== */}

      <header className="app-surface app-border sticky top-0 z-40 flex h-16 items-center justify-between border-b px-4 lg:hidden">


        <button

          type="button"

          onClick={() =>
            navigate("/dashboard")
          }

          className="flex items-center gap-3"

        >


          <div className="flex h-9 w-9 items-center justify-center rounded-lg bg-blue-600 text-white">

            <FiCode size={19} />

          </div>


          <span className="text-lg font-bold tracking-tight">

            CodeMentor

            <span className="text-blue-500">

              AI

            </span>

          </span>


        </button>


        <button

          type="button"

          onClick={() =>
            setMobileMenuOpen(true)
          }

          className="app-hover app-text-secondary flex h-10 w-10 items-center justify-center rounded-lg"

          aria-label="Open navigation"

        >

          <FiMenu size={22} />

        </button>


      </header>


      {/* ==================================================
          MOBILE OVERLAY
      ================================================== */}

      {
        mobileMenuOpen && (

          <button

            type="button"

            aria-label="Close navigation"

            onClick={closeMobileMenu}

            className="fixed inset-0 z-50 bg-black/60 lg:hidden"

          />

        )
      }


      {/* ==================================================
          MOBILE SIDEBAR
      ================================================== */}

      <aside
        className={`
          app-surface
          fixed
          inset-y-0
          left-0
          z-[60]
          flex
          w-[280px]
          flex-col
          shadow-2xl
          transition-transform
          duration-300
          lg:hidden
          ${
            mobileMenuOpen
              ? "translate-x-0"
              : "-translate-x-full"
          }
        `}
      >


        {/* MOBILE LOGO */}

        <div className="app-border flex h-16 items-center justify-between border-b px-4">


          <button

            type="button"

            onClick={() => {

              navigate("/dashboard");

              closeMobileMenu();

            }}

            className="flex items-center gap-3"

          >


            <div className="flex h-9 w-9 items-center justify-center rounded-lg bg-blue-600 text-white">

              <FiCode size={19} />

            </div>


            <span className="text-lg font-bold">

              CodeMentor

              <span className="text-blue-500">

                AI

              </span>

            </span>


          </button>


          <button

            type="button"

            onClick={closeMobileMenu}

            className="app-hover app-text-secondary flex h-9 w-9 items-center justify-center rounded-lg"

            aria-label="Close navigation"

          >

            <FiX size={21} />

          </button>


        </div>


        {/* MOBILE NAVIGATION */}

        <nav className="flex-1 overflow-y-auto p-3">


          <p className="app-text-secondary mb-3 px-3 text-[11px] font-bold uppercase tracking-[0.14em]">

            Platform

          </p>


          <div className="space-y-1">


            {
              navigationItems.map(
                ({
                  to,
                  label,
                  icon: Icon,
                }) => (

                  <NavLink

                    key={to}

                    to={to}

                    onClick={closeMobileMenu}

                    className={({
                      isActive,
                    }) => `
                      flex
                      h-11
                      items-center
                      gap-3
                      rounded-lg
                      px-3
                      text-sm
                      font-medium
                      transition
                      ${
                        isActive
                          ? "bg-blue-600 text-white"
                          : "app-text-secondary app-hover"
                      }
                    `}

                  >


                    <Icon
                      className="shrink-0"
                      size={19}
                    />


                    {
                      label
                    }


                  </NavLink>

                )
              )
            }


          </div>


        </nav>


        {/* MOBILE BOTTOM ACTIONS */}

        <div className="app-border border-t p-3">


          <button

            type="button"

            onClick={toggleTheme}

            className="app-hover app-text-secondary flex h-11 w-full items-center gap-3 rounded-lg px-3 text-sm font-medium"

          >


            {
              theme === "dark"
                ? (
                  <FiSun size={19} />
                )
                : (
                  <FiMoon size={19} />
                )
            }


            {
              theme === "dark"
                ? "Light Mode"
                : "Dark Mode"
            }


          </button>


          <button

            type="button"

            onClick={handleLogout}

            className="mt-1 flex h-11 w-full items-center gap-3 rounded-lg px-3 text-sm font-medium text-red-500 transition hover:bg-red-500/10"

          >

            <FiLogOut size={19} />

            Logout

          </button>


        </div>


      </aside>


      {/* ==================================================
          PAGE CONTENT
      ================================================== */}

      <div
        className={`
          min-h-screen
          transition-[padding]
          duration-300
          ${
            sidebarCollapsed
              ? "lg:pl-[80px]"
              : "lg:pl-[260px]"
          }
        `}
      >

        <Outlet />

      </div>


    </div>

  );

}