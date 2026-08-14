import {
  createContext,
  useCallback,
  useContext,
  useEffect,
  useState,
} from "react";

import type { ReactNode } from "react";

import {
  getNotifications,
  getNotificationSummary,
  markAsRead as markNotificationRead,
  markAllAsRead as markAllNotificationsRead,
  deleteNotification as deleteNotificationApi,
  deleteAllNotifications as deleteAllNotificationsApi,
} from "../services/notificationCenterService";

import type {
  Notification,
} from "../services/notificationCenterService";

interface NotificationContextType {
  notifications: Notification[];
  unreadCount: number;
  loading: boolean;

  refreshNotifications: () => Promise<void>;
  markAsRead: (id: number) => Promise<void>;
  markAllAsRead: () => Promise<void>;
  deleteNotification: (id: number) => Promise<void>;
  deleteAllNotifications: () => Promise<void>;
}

export const NotificationContext =
  createContext<NotificationContextType | undefined>(undefined);

export function NotificationProvider({
  children,
}: {
  children: ReactNode;
}) {
  const isPublicPortfolio =
    window.location.pathname.startsWith("/portfolio/");

  const [notifications, setNotifications] =
    useState<Notification[]>([]);

  const [unreadCount, setUnreadCount] =
    useState(0);

  const [loading, setLoading] =
    useState(!isPublicPortfolio);

  const refreshNotifications = useCallback(async () => {
    // Public portfolio does not need notifications
    if (isPublicPortfolio) {
      return;
    }

    try {
      const [notificationData, summary] =
        await Promise.all([
          getNotifications(),
          getNotificationSummary(),
        ]);

      setNotifications(notificationData);
      setUnreadCount(
        summary.unreadNotifications
      );
    } finally {
      setLoading(false);
    }
  }, [isPublicPortfolio]);

  useEffect(() => {
    void refreshNotifications();

    const interval = window.setInterval(() => {
      void refreshNotifications();
    }, 30000);

    return () => {
      window.clearInterval(interval);
    };
  }, [refreshNotifications]);

  const markAsRead = async (id: number) => {
    await markNotificationRead(id);
    await refreshNotifications();
  };

  const markAllAsRead = async () => {
    await markAllNotificationsRead();
    await refreshNotifications();
  };

  const deleteNotification = async (id: number) => {
    await deleteNotificationApi(id);
    await refreshNotifications();
  };

  const deleteAllNotifications = async () => {
    await deleteAllNotificationsApi();
    await refreshNotifications();
  };

  return (
    <NotificationContext.Provider
      value={{
        notifications,
        unreadCount,
        loading,
        refreshNotifications,
        markAsRead,
        markAllAsRead,
        deleteNotification,
        deleteAllNotifications,
      }}
    >
      {children}
    </NotificationContext.Provider>
  );
}

export function useNotifications() {
  const context = useContext(NotificationContext);

  if (!context) {
    throw new Error(
      "useNotifications must be used inside NotificationProvider"
    );
  }

  return context;
}