import {
  AlertTriangle,
  X,
} from "lucide-react";

interface LogoutAllDialogProps {
  open: boolean;
  loading: boolean;
  onClose: () => void;
  onConfirm: () => void;
}

export default function LogoutAllDialog({
  open,
  loading,
  onClose,
  onConfirm,
}: LogoutAllDialogProps) {

  if (!open) return null;

  return (

    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 backdrop-blur-sm">

      <div className="w-full max-w-lg rounded-2xl border bg-card p-8 shadow-2xl">

        <div className="flex items-start justify-between">

          <div className="flex items-center gap-4">

            <div className="rounded-xl bg-red-500/10 p-3">

              <AlertTriangle
                className="text-red-500"
                size={28}
              />

            </div>

            <div>

              <h2 className="text-2xl font-bold">
                Logout All Other Devices
              </h2>

              <p className="mt-2 text-sm text-muted-foreground">
                All other active sessions will be logged out immediately.
                Your current device will remain signed in.
              </p>

            </div>

          </div>

          <button
            onClick={onClose}
            className="rounded-lg p-2 hover:bg-muted"
          >
            <X size={20} />
          </button>

        </div>

        <div className="mt-8 flex justify-end gap-4">

          <button
            onClick={onClose}
            disabled={loading}
            className="rounded-xl border px-5 py-2 transition hover:bg-muted"
          >
            Cancel
          </button>

          <button
            onClick={onConfirm}
            disabled={loading}
            className="rounded-xl bg-red-600 px-5 py-2 font-semibold text-white transition hover:bg-red-700 disabled:opacity-60"
          >
            {loading ? "Logging Out..." : "Logout All"}
          </button>

        </div>

      </div>

    </div>

  );
}