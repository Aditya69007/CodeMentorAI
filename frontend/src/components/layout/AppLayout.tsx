import { useState } from "react";

import {
  NavLink,
  Outlet,
  useNavigate,
} from "react-router-dom";

import {
  FiActivity,
  FiCode,
  FiList,
  FiLogOut,
  FiMenu,
  FiMoon,
  FiSun,
  FiX,
} from "react-icons/fi";

import { useAuth } from "../../hooks/useAuth";
import { useTheme } from "../../hooks/useTheme";


export default function AppLayout() {

  const navigate = useNavigate();

  const { logout } = useAuth();

  const {
    theme,
    toggleTheme,
  } = useTheme();


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
  // NAVIGATION STYLE
  // ==================================================

  const navLinkClass = ({
    isActive,
  }: {
    isActive: boolean;
  }) => {

    return `
      flex
      items-center
      gap-2
      rounded-md
      px-3
      py-2
      text-sm
      font-medium
      transition
      ${
        isActive
          ? "bg-blue-600/10 text-blue-600 dark:text-blue-400"
          : "app-text-secondary app-hover"
      }
    `;

  };


  return (

    <div className="app-background min-h-screen">


      {/* ==================================================
          HEADER
      ================================================== */}

      <header className="app-surface app-border sticky top-0 z-50 border-b">


        <div className="mx-auto flex h-16 max-w-[1500px] items-center justify-between px-4 sm:px-6">


          {/* ==================================================
              LOGO
          ================================================== */}

          <button

            onClick={() =>
              navigate("/problems")
            }

            className="flex items-center gap-3"

          >

            <div className="flex h-9 w-9 items-center justify-center rounded-lg bg-blue-600 text-white">

              <FiCode size={19} />

            </div>


            <span className="text-lg font-bold tracking-tight">

              CodeMentor

              <span className="text-blue-600 dark:text-blue-400">

                AI

              </span>

            </span>

          </button>



          {/* ==================================================
              DESKTOP NAVIGATION
          ================================================== */}

          <nav className="hidden items-center gap-1 md:flex">


            {/* PROBLEMS */}

            <NavLink

              to="/problems"

              className={navLinkClass}

            >

              <FiList size={17} />

              Problems

            </NavLink>



            {/* MISTAKE MEMORY */}

            <NavLink

              to="/mistake-memory"

              className={navLinkClass}

            >

              <FiActivity size={17} />

              Mistake Memory

            </NavLink>


          </nav>



          {/* ==================================================
              DESKTOP ACTIONS
          ================================================== */}

          <div className="hidden items-center gap-2 md:flex">


            {/* THEME BUTTON */}

            <button

              onClick={toggleTheme}

              className="app-hover app-border flex h-9 w-9 items-center justify-center rounded-md border"

              title="Change theme"

            >

              {
                theme === "dark"
                  ? (
                    <FiSun size={17} />
                  )
                  : (
                    <FiMoon size={17} />
                  )
              }

            </button>



            {/* LOGOUT BUTTON */}

            <button

              onClick={handleLogout}

              className="app-hover app-border app-text-secondary flex items-center gap-2 rounded-md border px-3 py-2 text-sm font-medium"

            >

              <FiLogOut size={16} />

              Logout

            </button>


          </div>



          {/* ==================================================
              MOBILE MENU BUTTON
          ================================================== */}

          <button

            onClick={() =>
              setMobileMenuOpen(
                (current) => !current
              )
            }

            className="app-hover rounded-md p-2 md:hidden"

            aria-label="Toggle navigation menu"

          >

            {
              mobileMenuOpen
                ? (
                  <FiX size={22} />
                )
                : (
                  <FiMenu size={22} />
                )
            }

          </button>


        </div>



        {/* ==================================================
            MOBILE MENU
        ================================================== */}

        {
          mobileMenuOpen && (

            <div className="app-border border-t px-4 py-3 md:hidden">


              <div className="space-y-1">


                {/* PROBLEMS */}

                <NavLink

                  to="/problems"

                  className={navLinkClass}

                  onClick={() =>
                    setMobileMenuOpen(false)
                  }

                >

                  <FiList />

                  Problems

                </NavLink>



                {/* MISTAKE MEMORY */}

                <NavLink

                  to="/mistake-memory"

                  className={navLinkClass}

                  onClick={() =>
                    setMobileMenuOpen(false)
                  }

                >

                  <FiActivity />

                  Mistake Memory

                </NavLink>



                {/* THEME */}

                <button

                  onClick={toggleTheme}

                  className="app-hover app-text-secondary flex w-full items-center gap-2 rounded-md px-3 py-2 text-sm font-medium"

                >

                  {
                    theme === "dark"
                      ? (
                        <FiSun />
                      )
                      : (
                        <FiMoon />
                      )
                  }


                  {
                    theme === "dark"
                      ? "Light Mode"
                      : "Dark Mode"
                  }

                </button>



                {/* LOGOUT */}

                <button

                  onClick={handleLogout}

                  className="flex w-full items-center gap-2 rounded-md px-3 py-2 text-sm font-medium text-red-500 hover:bg-red-500/10"

                >

                  <FiLogOut />

                  Logout

                </button>


              </div>


            </div>

          )
        }


      </header>



      {/* ==================================================
          PAGE CONTENT
      ================================================== */}

      <Outlet />


    </div>

  );

}