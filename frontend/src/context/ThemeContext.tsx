import {
  useEffect,
  useState,
  type ReactNode,
} from "react";
import { useAuth } from "../hooks/useAuth";
import {
  ThemeContext,
  type Theme,
} from "./ThemeContextDefinition";

import {
  getThemePreference,
  updateThemePreference,
} from "../services/themeService";

interface ThemeProviderProps {
  children: ReactNode;
}

export function ThemeProvider({
  children,
}: ThemeProviderProps) {

  const [theme, setThemeState] =
    useState<Theme>("DARK");
  const { isAuthenticated } = useAuth();
    
    useEffect(() => {
      
      document.documentElement.classList.toggle(
        "dark",
        theme === "DARK"
      );
      
    }, [theme]);
  

  useEffect(() => {
    if (!isAuthenticated) {
      return;
    }

    const loadTheme = async () => {
      try {
        const savedTheme = await getThemePreference();
        setThemeState(savedTheme);
      } catch (error) {
        console.error("Failed to load theme", error);
      }
    };

    loadTheme();
  }, [isAuthenticated]);


  const setTheme = async (
    newTheme: Theme
  ) => {

    setThemeState(newTheme);

    try {

      await updateThemePreference({
        themePreference: newTheme,
      });

    } catch (error) {

      console.error(
        "Failed to save theme",
        error
      );

    }

  };

  const toggleTheme = async () => {

    await setTheme(
      theme === "DARK"
        ? "LIGHT"
        : "DARK"
    );

  };

  return (

    <ThemeContext.Provider
      value={{
        theme,
        setTheme,
        toggleTheme,
      }}
    >

      {children}

    </ThemeContext.Provider>

  );

}