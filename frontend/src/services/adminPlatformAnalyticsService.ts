import api from "./api";

import type {
  AdminPlatformAnalytics,
} from "../types/adminPlatformAnalytics";


export const getAdminPlatformAnalytics =
  async (): Promise<AdminPlatformAnalytics> => {

    const response =
      await api.get<AdminPlatformAnalytics>(
        "/admin/platform-analytics"
      );

    return response.data;
  };