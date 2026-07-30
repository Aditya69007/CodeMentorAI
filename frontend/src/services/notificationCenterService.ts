import api from "./api";

export interface Notification {
  id: number;
  title: string;
  message: string;
  type: string;
  icon: string;
  actionUrl: string;
  isRead: boolean;
  createdAt: string;
}

export interface NotificationSummary {
  totalNotifications: number;
  unreadNotifications: number;
}

export const getNotifications = async (): Promise<Notification[]> => {
  const response = await api.get<Notification[]>("/notifications");
  return response.data;
};

export const getNotificationSummary =
  async (): Promise<NotificationSummary> => {
    const response =
      await api.get<NotificationSummary>("/notifications/summary");

    return response.data;
  };

export const markAsRead = async (
  notificationId: number
): Promise<void> => {
  await api.put(`/notifications/${notificationId}/read`);
};

export const markAllAsRead = async (): Promise<void> => {
  await api.put("/notifications/read-all");
};

export const deleteNotification = async (
  notificationId: number
): Promise<void> => {
  await api.delete(`/notifications/${notificationId}`);
};

export const deleteAllNotifications = async (): Promise<void> => {
  await api.delete("/notifications");
};