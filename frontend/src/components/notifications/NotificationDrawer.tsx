import { FiCheck, FiX } from "react-icons/fi";
import useNotifications from "../../hooks/useNotifications";
import NotificationItem from "./NotificationItem";
import NotificationEmptyState from "./NotificationEmptyState";

interface NotificationDrawerProps {
  open: boolean;
  onClose: () => void;
}

export default function NotificationDrawer({
  open,
  onClose,
}: NotificationDrawerProps) {

  const {
    notifications,
    loading,
    markAllAsRead,
    deleteAllNotifications,
  } = useNotifications();

  if (!open) {
    return null;
  }

  return (
    <>
      {/* Overlay */}

      <div
        className="fixed inset-0 z-40 bg-black/40"
        onClick={onClose}
      />

      {/* Drawer */}

      <div
        className="
          fixed
          right-0
          top-0
          z-50

          flex
          h-screen
          w-[420px]
          flex-col

          border-l

          app-surface
          app-border

          shadow-2xl
        "
      >

        {/* Header */}

        <div
          className="
            flex
            items-center
            justify-between

            border-b

            app-border

            p-5
          "
        >

          <div>

            <h2 className="text-lg font-bold">

              Notifications

            </h2>

            <p className="app-text-secondary text-sm">

              Stay updated with your activity.

            </p>

          </div>

          <button
            onClick={onClose}
            className="rounded-lg p-2 hover:bg-slate-500/10"
          >
            <FiX size={20} />
          </button>

        </div>

        {/* Actions */}

        <div
          className="
            flex
            items-center
            justify-end
            gap-3

            border-b
            app-border

            p-4
          "
        >

          <button
            onClick={markAllAsRead}
            className="
              flex
              items-center
              gap-2

              rounded-lg

              bg-blue-600

              px-3
              py-2

              text-sm
              font-medium
              text-white

              transition

              hover:bg-blue-700
            "
          >
            <FiCheck />
            Mark all as read
          </button>

          <button
            onClick={async () => {

              const confirmed = window.confirm(
                "Delete all notifications?"
              );

              if (!confirmed) return;

              await deleteAllNotifications();

            }}
            className="
              flex
              items-center
              gap-2

              rounded-lg

              bg-red-600

              px-3
              py-2

              text-sm
              font-medium
              text-white

              transition

              hover:bg-red-700
            "
          >
            <FiX />
            Delete all
          </button>

        </div>

        {/* Body */}

        <div className="flex-1 overflow-y-auto">

          {loading ? (

            <div className="p-6 text-center">

              Loading notifications...

            </div>

          ) : notifications.length === 0 ? (

            <NotificationEmptyState />

          ) : (

            notifications.map((notification) => (

              <NotificationItem
                key={notification.id}
                notification={notification}
              />

            ))

          )}

        </div>

      </div>
    </>
  );
}