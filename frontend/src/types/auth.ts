export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

export type UserRole = "USER" | "ADMIN" | "SUPER_ADMIN";

export interface AuthUser {
  userId: number;
  firstName: string;
  lastName: string;
  username: string;
  createdAt?: string | null;
  email: string;
  role: string;
  provider: string;
  profilePicture?: string | null;
  sessionId: number;
  leetcodeUsername?: string | null;
  githubUsername?: string | null;
}

export interface AuthResponse extends AuthUser {
  token: string;
  message: string;
  sessionId: number;
}

export interface ForgotPasswordRequest {
  email: string;
}

export interface ResetPasswordRequest {
  token: string;
  newPassword: string;
  confirmPassword: string;
}