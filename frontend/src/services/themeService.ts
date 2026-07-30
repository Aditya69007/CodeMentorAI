import api from "./api";

export type ThemePreference = "LIGHT" | "DARK";

export interface UpdateThemeRequest {
  themePreference: ThemePreference;
}

export const getThemePreference = async (): Promise<ThemePreference> => {
  const response = await api.get<ThemePreference>("/auth/theme");
  return response.data;
};

export const updateThemePreference = async (
  request: UpdateThemeRequest
): Promise<string> => {
  const response = await api.put<string>(
    "/auth/theme",
    request
  );

  return response.data;
};