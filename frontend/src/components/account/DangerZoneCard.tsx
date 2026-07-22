import {
  FiAlertTriangle,
  FiLogOut,
  FiTrash2,
  FiDownload,
  FiRotateCcw,
} from "react-icons/fi";

export default function DangerZoneCard() {
  return (
    <section className="rounded-2xl border border-red-500/30 bg-red-500/5 p-6">

      <div className="mb-6 flex items-center gap-3">

        <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-red-500/10">

          <FiAlertTriangle className="text-xl text-red-500" />

        </div>

        <div>

          <h2 className="text-xl font-bold text-red-500">
            Danger Zone
          </h2>

          <p className="app-text-secondary mt-1">
            Sensitive account actions. Proceed carefully.
          </p>

        </div>

      </div>

      <div className="space-y-3">

        <button className="flex w-full items-center justify-between rounded-xl border border-red-500/20 p-4 transition hover:bg-red-500/10">

          <div className="flex items-center gap-3">

            <FiLogOut />

            <span>Logout From All Devices</span>

          </div>

          <span className="text-sm text-yellow-500">
            Coming Soon
          </span>

        </button>

        <button className="flex w-full items-center justify-between rounded-xl border border-red-500/20 p-4 transition hover:bg-red-500/10">

          <div className="flex items-center gap-3">

            <FiDownload />

            <span>Export My Data</span>

          </div>

          <span className="text-sm text-yellow-500">
            Coming Soon
          </span>

        </button>

        <button className="flex w-full items-center justify-between rounded-xl border border-red-500/20 p-4 transition hover:bg-red-500/10">

          <div className="flex items-center gap-3">

            <FiRotateCcw />

            <span>Reset AI Learning History</span>

          </div>

          <span className="text-sm text-yellow-500">
            Coming Soon
          </span>

        </button>

        <button className="flex w-full items-center justify-between rounded-xl border border-red-500/20 p-4 transition hover:bg-red-500/10">

          <div className="flex items-center gap-3">

            <FiTrash2 />

            <span>Delete Account</span>

          </div>

          <span className="font-semibold text-red-500">
            Permanent
          </span>

        </button>

      </div>

    </section>
  );
}