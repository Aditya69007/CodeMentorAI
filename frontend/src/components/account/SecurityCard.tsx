import { FiLock, FiChevronRight } from "react-icons/fi";

export default function SecurityCard() {
  return (
    <section className="app-surface app-border rounded-2xl p-6">

      <div className="flex items-start justify-between">

        <div>

          <div className="mb-3 flex h-12 w-12 items-center justify-center rounded-xl bg-blue-500/10">

            <FiLock className="text-xl text-blue-500" />

          </div>

          <h2 className="text-xl font-bold">
            Security
          </h2>

          <p className="app-text-secondary mt-2">
            Manage your password and secure your account.
          </p>

        </div>

        <button
          className="rounded-xl border px-4 py-2 transition hover:border-blue-500 hover:text-blue-500"
        >
          <span className="flex items-center gap-2">
            Manage
            <FiChevronRight />
          </span>
        </button>

      </div>

      <div className="mt-6 space-y-4">

        <div className="flex items-center justify-between">

          <span>Password</span>

          <span className="text-emerald-500 font-medium">
            Configured
          </span>

        </div>

        <div className="flex items-center justify-between">

          <span>Two Factor Authentication</span>

          <span className="text-yellow-500 font-medium">
            Coming Soon
          </span>

        </div>

        <div className="flex items-center justify-between">

          <span>Recent Login Activity</span>

          <span className="text-yellow-500 font-medium">
            Coming Soon
          </span>

        </div>

      </div>

    </section>
  );
}