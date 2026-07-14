import { createContext } from "react";
import type { AuthUser } from "../types/auth";

export interface AuthContextType {
  token: string | null;
  user: AuthUser | null;
  isAuthenticated: boolean;
  isAdmin: boolean;
  login: (email: string, password: string) => Promise<AuthUser>;
  logout: () => void;
}

export const AuthContext =
  createContext<AuthContextType | undefined>(undefined);