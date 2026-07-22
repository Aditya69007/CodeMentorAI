import { FiBell, FiChevronRight } from "react-icons/fi";

export default function NotificationCard() {
  return (
    <section className="app-surface app-border rounded-2xl p-6">

      <div className="flex items-start justify-between">

        <div>

          <div className="mb-3 flex h-12 w-12 items-center justify-center rounded-xl bg-amber-500/10">

            <FiBell className="text-xl text-amber-500" />

          </div>

          <h2 className="text-xl font-bold">
            Notifications
          </h2>

          <p className="app-text-secondary mt-2">
            Control how you receive updates from CodeMentorAI.
          </p>

        </div>

        <button className="rounded-xl border px-4 py-2 transition hover:border-amber-500 hover:text-amber-500">
          <span className="flex items-center gap-2">
            Manage
            <FiChevronRight />
          </span>
        </button>

      </div>

      <div className="mt-6 space-y-4">

        <div className="flex items-center justify-between">
          <span>Email Notifications</span>
          <span className="text-emerald-500 font-medium">
            Enabled
          </span>
        </div>

        <div className="flex items-center justify-between">
          <span>AI Learning Recommendations</span>
          <span className="text-yellow-500">
            Coming Soon
          </span>
        </div>

        <div className="flex items-center justify-between">
          <span>Weekly Progress Reports</span>
          <span className="text-yellow-500">
            Coming Soon
          </span>
        </div>

        <div className="flex items-center justify-between">
          <span>Interview Reminders</span>
          <span className="text-yellow-500">
            Coming Soon
          </span>
        </div>

      </div>

    </section>
  );
}