import api from "./api";

export interface AdminSettingsResponse {
  compactDashboard: boolean;
  adminNotifications: boolean;
  platformAlerts: boolean;
  defaultPageSize: number;
  autoRefreshDashboard: boolean;
  autoRefreshInterval: number;
  confirmBeforeDelete: boolean;
}

export interface UpdateAdminSettingsRequest {
  compactDashboard: boolean;
  adminNotifications: boolean;
  platformAlerts: boolean;
  defaultPageSize: number;
  autoRefreshDashboard: boolean;
  autoRefreshInterval: number;
  confirmBeforeDelete: boolean;
}

export async function getAdminSettings(): Promise<AdminSettingsResponse> {
  const response = await api.get<AdminSettingsResponse>(
    "/admin/settings"
  );

  return response.data;
}

export async function updateAdminSettings(
  data: UpdateAdminSettingsRequest
): Promise<AdminSettingsResponse> {
  const response = await api.put<AdminSettingsResponse>(
    "/admin/settings",
    data
  );

  return response.data;
}