import api from "./api";

export interface NotificationPreferences {
  emailNotifications: boolean;
  aiLearningTips: boolean;
  contestReminders: boolean;
  weeklyGrowthReport: boolean;
  interviewAlerts: boolean;
}

export const getNotificationPreferences =
  async (): Promise<NotificationPreferences> => {

    const response = await api.get<NotificationPreferences>(
      "/auth/notifications"
    );

    return response.data;
  };

export const updateNotificationPreferences =
  async (
    request: NotificationPreferences
  ): Promise<string> => {

    const response = await api.put<string>(
      "/auth/notifications",
      request
    );

    return response.data;
  };