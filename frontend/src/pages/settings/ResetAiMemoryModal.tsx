import { FiAlertTriangle } from "react-icons/fi";

interface ResetAiMemoryModalProps {
  open: boolean;
  loading: boolean;
  onClose: () => void;
  onConfirm: () => Promise<void>;
}

export default function ResetAiMemoryModal({
  open,
  loading,
  onClose,
  onConfirm,
}: ResetAiMemoryModalProps) {

  if (!open) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/70 backdrop-blur-sm">

      <div className="w-full max-w-lg rounded-2xl border border-yellow-500/30 bg-[#111827] p-6 shadow-2xl">

        <div className="flex items-center gap-3">

          <div className="rounded-full bg-yellow-500/10 p-3">

            <FiAlertTriangle className="text-2xl text-yellow-400" />

          </div>

          <div>

            <h2 className="text-2xl font-bold text-white">
              Clear AI Memory
            </h2>

            <p className="text-sm text-gray-400">
              Your AI assistant will start learning from scratch.
            </p>

          </div>

        </div>

        <div className="mt-6 rounded-xl border border-yellow-500/20 bg-yellow-500/5 p-4">

          <p className="font-medium text-yellow-300">
            This will remove:
          </p>

          <ul className="mt-3 space-y-2 text-sm text-gray-300">
            <li>✓ AI mistake memory</li>
            <li>✓ Weak concept history</li>
            <li>✓ Personalized recommendations</li>
          </ul>

        </div>

        <div className="mt-4 rounded-xl border border-green-500/20 bg-green-500/5 p-4">

          <p className="font-medium text-green-300">
            This will NOT remove:
          </p>

          <ul className="mt-3 space-y-2 text-sm text-gray-300">
            <li>✓ Account</li>
            <li>✓ GitHub & LeetCode connections</li>
            <li>✓ Problem submissions</li>
            <li>✓ AI Chats</li>
            <li>✓ AI Analysis Reports</li>
          </ul>

        </div>

        <div className="mt-8 flex justify-end gap-3">

          <button
            onClick={onClose}
            disabled={loading}
            className="rounded-xl border border-gray-700 px-5 py-2 text-white hover:bg-gray-800"
          >
            Cancel
          </button>

          <button
            onClick={onConfirm}
            disabled={loading}
            className="rounded-xl bg-yellow-500 px-5 py-2 font-semibold text-black transition hover:bg-yellow-400 disabled:opacity-50"
          >
            {loading ? "Clearing..." : "Clear AI Memory"}
          </button>

        </div>

      </div>

    </div>
  );
}