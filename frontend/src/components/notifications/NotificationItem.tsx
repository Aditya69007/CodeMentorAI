import {
  FiTrash2,
  FiLock,
  FiUser,
  FiBook,
  FiAward,
  FiSettings,
  FiCpu,
} from "react-icons/fi";

import { useNavigate } from "react-router-dom";

import type { Notification } from "../../services/notificationCenterService";
import useNotifications from "../../hooks/useNotifications";

interface NotificationItemProps {
  notification: Notification;
}

export default function NotificationItem({
  notification,
}: NotificationItemProps) {

  const navigate = useNavigate();

  const {
    markAsRead,
    deleteNotification,
  } = useNotifications();

  const getIcon = () => {

    switch (notification.type) {

      case "SECURITY":
        return <FiLock size={18} />;

      case "ACCOUNT":
        return <FiUser size={18} />;

      case "LEARNING":
        return <FiBook size={18} />;

      case "GROWTH":
        return <FiAward size={18} />;

      case "AI":
        return <FiCpu size={18} />;

      default:
        return <FiSettings size={18} />;

    }

  };

  const handleClick = async () => {

    if (!notification.isRead) {
      await markAsRead(notification.id);
    }

    if (notification.actionUrl) {
      navigate(notification.actionUrl);
    }

  };

  return (

    <div
      className={`
        border-b
        app-border

        p-4

        transition

        hover:bg-slate-500/10

        cursor-pointer

        ${
          notification.isRead
            ? ""
            : "bg-blue-500/5"
        }
      `}
      onClick={handleClick}
    >

      <div className="flex items-start gap-3">

        <div
          className="
            flex
            h-10
            w-10
            items-center
            justify-center

            rounded-lg

            bg-blue-500/10
            text-blue-500
          "
        >

          {getIcon()}

        </div>

        <div className="flex-1">

          <div className="flex items-center justify-between">

            <h3 className="font-semibold">

              {notification.title}

            </h3>

            {!notification.isRead && (

              <span
                className="
                  h-2
                  w-2

                  rounded-full

                  bg-blue-500
                "
              />

            )}

          </div>

          <p
            className="
              mt-1

              text-sm

              app-text-secondary
            "
          >

            {notification.message}

          </p>

          <p
            className="
              mt-2

              text-xs

              app-text-secondary
            "
          >

            {new Date(
              notification.createdAt
            ).toLocaleString()}

          </p>

        </div>

        <button
          onClick={async (e) => {

            e.stopPropagation();

            await deleteNotification(
              notification.id
            );

          }}
          className="
            rounded-lg

            p-2

            text-red-500

            hover:bg-red-500/10
          "
        >

          <FiTrash2 />

        </button>

      </div>

    </div>

  );

}