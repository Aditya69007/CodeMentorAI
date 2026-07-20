import api from "./api";
import type {
  LoginRequest,
  RegisterRequest,
  AuthResponse,
} from "../types/auth";

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