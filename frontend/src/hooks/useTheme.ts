import { useContext } from "react";

import {
  ThemeContext,
} from "../context/ThemeContextDefinition";

export function useTheme() {
  const context = useContext(ThemeContext);

  if (context === undefined) {
    throw new Error(
      "useTheme must be used inside ThemeProvider"
    );
  }

  return context;
}