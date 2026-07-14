import api from "./api";

import type {
  AdminAiAnalytics,
} from "../types/adminAiAnalytics";


export const getAdminAiAnalytics =
  async (): Promise<AdminAiAnalytics> => {

    const response =
      await api.get<AdminAiAnalytics>(
        "/ai-analytics/admin"
      );

    return response.data;
  };