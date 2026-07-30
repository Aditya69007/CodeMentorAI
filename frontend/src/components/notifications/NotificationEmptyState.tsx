import { FiBell } from "react-icons/fi";

export default function NotificationEmptyState() {
  return (
    <div
      className="
        flex
        h-full
        flex-col
        items-center
        justify-center

        px-8
        text-center
      "
    >
      <div
        className="
          mb-6

          flex
          h-20
          w-20
          items-center
          justify-center

          rounded-full

          bg-blue-500/10
          text-blue-500
        "
      >
        <FiBell size={36} />
      </div>

      <h3 className="text-lg font-semibold">
        No notifications yet
      </h3>

      <p
        className="
          mt-3

          max-w-xs

          text-sm

          app-text-secondary
        "
      >
        We'll notify you whenever something important happens,
        like AI insights, interview recommendations, contest
        reminders, or security updates.
      </p>
    </div>
  );
}