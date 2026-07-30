import { useState } from "react";
import { FiBell } from "react-icons/fi";
import useNotifications from "../../hooks/useNotifications";
import NotificationDrawer from "./NotificationDrawer";

export default function NotificationBell() {
  const [open, setOpen] = useState(false);

  const { unreadCount } = useNotifications();

  return (
    <>
      <button
        type="button"
        aria-label="Notifications"
        onClick={() => setOpen(true)}
        className="
          app-surface-secondary
          app-border
          app-text-secondary

          relative

          flex
          h-10
          w-10

          items-center
          justify-center

          rounded-lg
          border

          transition

          hover:text-blue-500
        "
      >
        <FiBell size={18} />

        {unreadCount > 0 && (
          <span
            className="
              absolute
              -right-1
              -top-1

              flex
              h-5
              w-5

              items-center
              justify-center

              rounded-full

              bg-red-500

              text-[10px]
              font-bold
              text-white
            "
          >
            {unreadCount > 99 ? "99+" : unreadCount}
          </span>
        )}
      </button>

      <NotificationDrawer
        open={open}
        onClose={() => setOpen(false)}
      />
    </>
  );
}