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

export type UserRole = "USER" | "ADMIN";

export interface AuthUser {
  userId: number;
  firstName: string;
  lastName: string;
  email: string;
  role: UserRole;
  profilePicture: string | null;
}

export interface AuthResponse extends AuthUser {
  token: string;
  message: string;
}