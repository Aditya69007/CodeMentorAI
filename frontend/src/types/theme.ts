export type ThemePreference =
  | "LIGHT"
  | "DARK"
  | "SYSTEM";

export interface UpdateThemeRequest {
  themePreference: ThemePreference;
}