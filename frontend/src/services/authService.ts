import api from "./api";
import type { ChangePasswordRequest } from "../types/settings";
import type {
  LoginRequest,
  RegisterRequest,
  AuthResponse,
  ForgotPasswordRequest,
  ResetPasswordRequest,
} from "../types/auth";
import type {
  ThemePreference,
  UpdateThemeRequest,
} from "../types/theme";

export const loginUser = async (
  data: LoginRequest
): Promise<AuthResponse> => {

  const response = await api.post<AuthResponse>(
    "/auth/login",
    data
  );

  return response.data;
};

export const registerUser = async (
  data: RegisterRequest
): Promise<string> => {

  const response = await api.post<string>(
    "/auth/register",
    data
  );

  return response.data;
};

export const getCurrentUser = async () => {

  const response = await api.get("/users/me");

  return response.data;

};

export const changePassword = async (
  data: ChangePasswordRequest
): Promise<string> => {

  const response = await api.post<string>(
    "/auth/change-password",
    data
  );

  return response.data;
};

export const forgotPassword = async (
  data: ForgotPasswordRequest
): Promise<string> => {

  const response = await api.post<string>(
    "/auth/forgot-password",
    data
  );

  return response.data;
};

export const resetPassword = async (
  data: ResetPasswordRequest
): Promise<string> => {

  const response = await api.post<string>(
    "/auth/reset-password",
    data
  );

  return response.data;
};

export const getThemePreference = async (): Promise<ThemePreference> => {

  const response = await api.get<ThemePreference>(
    "/auth/theme"
  );

  return response.data;
};

export const updateThemePreference = async (
  data: UpdateThemeRequest
): Promise<string> => {

  const response = await api.put<string>(
    "/auth/theme",
    data
  );

  return response.data;
};