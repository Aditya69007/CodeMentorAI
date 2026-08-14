import { useEffect, useState, type ReactNode } from "react";

import {
  getCurrentUser,
  loginUser,
} from "../services/authService";
import type { AuthUser } from "../types/auth";
import { AuthContext } from "./AuthContextDefinition";

interface AuthProviderProps {
  children: ReactNode;
}

const TOKEN_STORAGE_KEY = "token";
const USER_STORAGE_KEY = "authUser";

export function AuthProvider({
  children,
}: AuthProviderProps) {

  const [token, setToken] = useState<string | null>(() =>
    localStorage.getItem(TOKEN_STORAGE_KEY)
  );

  const [user, setUser] = useState<AuthUser | null>(() => {

    const storedUser =
      localStorage.getItem(USER_STORAGE_KEY);

    if (!storedUser) {
      return null;
    }

    try {
      return JSON.parse(storedUser) as AuthUser;
    } catch {
      localStorage.removeItem(USER_STORAGE_KEY);
      return null;
    }
  });


  useEffect(() => {

    const syncAuth = () => {

      const storedToken =
        localStorage.getItem(TOKEN_STORAGE_KEY);

      const storedUser =
        localStorage.getItem(USER_STORAGE_KEY);

      setToken(storedToken);

      if (storedUser) {

        try {

          setUser(
            JSON.parse(storedUser)
          );

        } catch {

          setUser(null);

        }

      } else {

        setUser(null);

      }

    };

    syncAuth();

    window.addEventListener(
      "storage",
      syncAuth
    );

    return () => {

      window.removeEventListener(
        "storage",
        syncAuth
      );

    };

  }, []);

  const login = async (
    email: string,
    password: string
  ): Promise<AuthUser> => {

    const response = await loginUser({
      email,
      password,
    });

    // Store token first so /users/me can authenticate
    localStorage.setItem(
      TOKEN_STORAGE_KEY,
      response.token
    );

    setToken(response.token);

    // Get the complete/current user profile
    const currentUser = await getCurrentUser();

    const authenticatedUser: AuthUser = {
      userId: currentUser.userId,
      firstName: currentUser.firstName,
      lastName: currentUser.lastName,
      username: currentUser.username,
      email: currentUser.email,
      role: currentUser.role,
      provider: currentUser.provider,
      profilePicture: currentUser.profilePicture,

      createdAt: currentUser.createdAt,
      githubUsername: currentUser.githubUsername,
      leetcodeUsername: currentUser.leetcodeUsername,

      sessionId: response.sessionId,
    };

    localStorage.setItem(
      USER_STORAGE_KEY,
      JSON.stringify(authenticatedUser)
    );

    setUser(authenticatedUser);

    return authenticatedUser;
  };

  const refreshUser = async () => {

    const currentUser = await getCurrentUser();

    const authenticatedUser: AuthUser = {
      userId: currentUser.userId,
      firstName: currentUser.firstName,
      lastName: currentUser.lastName,
      username: currentUser.username,
      email: currentUser.email,
      role: currentUser.role,
      profilePicture: currentUser.profilePicture,
      provider: currentUser.provider,

      createdAt: currentUser.createdAt,
      githubUsername: currentUser.githubUsername,
      leetcodeUsername: currentUser.leetcodeUsername,

      sessionId: user?.sessionId ?? 0,
    };

    localStorage.setItem(
      USER_STORAGE_KEY,
      JSON.stringify(authenticatedUser)
    );

    setUser(authenticatedUser);
  };

  const logout = () => {

    localStorage.removeItem(TOKEN_STORAGE_KEY);
    localStorage.removeItem(USER_STORAGE_KEY);

    setToken(null);
    setUser(null);
  };

  const isAuthenticated =
    Boolean(token && user);

  const isAdmin =
    user?.role === "ADMIN" ||
    user?.role === "SUPER_ADMIN";

  const isSuperAdmin =
    user?.role === "SUPER_ADMIN";

  return (
    <AuthContext.Provider
      value={{
        token,
        user,
        isAuthenticated,
        isAdmin,
        isSuperAdmin,
        login,
        logout,
        refreshUser,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}