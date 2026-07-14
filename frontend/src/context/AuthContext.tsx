import { useState, type ReactNode } from "react";

import { loginUser } from "../services/authService";
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

  const login = async (
    email: string,
    password: string
  ): Promise<AuthUser> => {

    const response = await loginUser({
      email,
      password,
    });

    const authenticatedUser: AuthUser = {
      userId: response.userId,
      firstName: response.firstName,
      lastName: response.lastName,
      email: response.email,
      role: response.role,
      profilePicture: response.profilePicture,
    };

    localStorage.setItem(
      TOKEN_STORAGE_KEY,
      response.token
    );

    localStorage.setItem(
      USER_STORAGE_KEY,
      JSON.stringify(authenticatedUser)
    );

    setToken(response.token);
    setUser(authenticatedUser);

    return authenticatedUser;
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
    user?.role === "ADMIN";

  return (
    <AuthContext.Provider
      value={{
        token,
        user,
        isAuthenticated,
        isAdmin,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}