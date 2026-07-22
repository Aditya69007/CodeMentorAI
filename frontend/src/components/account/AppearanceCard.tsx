import { FiMonitor, FiChevronRight } from "react-icons/fi";

export default function AppearanceCard() {
  return (
    <section className="app-surface app-border rounded-2xl p-6">

      <div className="flex items-start justify-between">

        <div>

          <div className="mb-3 flex h-12 w-12 items-center justify-center rounded-xl bg-purple-500/10">

            <FiMonitor className="text-xl text-purple-500" />

          </div>

          <h2 className="text-xl font-bold">
            Appearance
          </h2>

          <p className="app-text-secondary mt-2">
            Customize how CodeMentorAI looks and feels.
          </p>

        </div>

        <button className="rounded-xl border px-4 py-2 transition hover:border-purple-500 hover:text-purple-500">
          <span className="flex items-center gap-2">
            Customize
            <FiChevronRight />
          </span>
        </button>

      </div>

      <div className="mt-6 space-y-4">

        <div className="flex items-center justify-between">
          <span>Theme</span>
          <span className="font-medium text-emerald-500">
            Active
          </span>
        </div>

        <div className="flex items-center justify-between">
          <span>Editor Font Size</span>
          <span className="text-yellow-500">
            Coming Soon
          </span>
        </div>

        <div className="flex items-center justify-between">
          <span>Editor Theme</span>
          <span className="text-yellow-500">
            Coming Soon
          </span>
        </div>

      </div>

    </section>
  );
}