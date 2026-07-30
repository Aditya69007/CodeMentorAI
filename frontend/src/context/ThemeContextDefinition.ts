import { createContext } from "react";

export type Theme = "LIGHT" | "DARK";

export interface ThemeContextType {
  theme: Theme;
  setTheme: (theme: Theme) => Promise<void>;
  toggleTheme: () => Promise<void>;
}

export const ThemeContext =
  createContext<ThemeContextType | undefined>(undefined);