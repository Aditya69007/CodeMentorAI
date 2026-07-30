import { useState } from "react";
import {
  FiAlertTriangle,
  FiEye,
  FiEyeOff,
} from "react-icons/fi";

interface DeleteAccountModalProps {
  open: boolean;
  loading: boolean;
  onClose: () => void;
  onConfirm: (password: string) => Promise<void>;
}

export default function DeleteAccountModal({
  open,
  loading,
  onClose,
  onConfirm,
}: DeleteAccountModalProps) {

  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [confirmed, setConfirmed] = useState(false);

  if (!open) return null;

  const handleSubmit = async () => {

    if (!password.trim()) {
      return;
    }

    await onConfirm(password);

    setPassword("");
    setConfirmed(false);

  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/70 backdrop-blur-sm">

      <div className="w-full max-w-lg rounded-2xl border border-red-500/30 bg-[#111827] p-6 shadow-2xl">

        <div className="flex items-center gap-3">

          <div className="rounded-full bg-red-500/10 p-3">

            <FiAlertTriangle className="text-2xl text-red-500" />

          </div>

          <div>

            <h2 className="text-2xl font-bold text-white">
              Delete Account
            </h2>

            <p className="text-sm text-gray-400">
              This action cannot be undone.
            </p>

          </div>

        </div>

        <div className="mt-6 rounded-xl border border-red-500/20 bg-red-500/5 p-4">

          <p className="font-medium text-red-400">
            The following data will be permanently deleted:
          </p>

          <ul className="mt-3 space-y-2 text-sm text-gray-300">
            <li>✓ Your profile</li>
            <li>✓ Login sessions</li>
            <li>✓ Notifications</li>
            <li>✓ Connected account information</li>
          </ul>

        </div>

        <div className="mt-6">

          <label className="mb-2 block text-sm font-medium text-gray-300">
            Enter your password
          </label>

          <div className="relative">

            <input
              type={showPassword ? "text" : "password"}
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full rounded-xl border border-gray-700 bg-gray-900 px-4 py-3 pr-12 text-white outline-none focus:border-red-500"
              placeholder="Password"
            />

            <button
              type="button"
              onClick={() =>
                setShowPassword(!showPassword)
              }
              className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400"
            >
              {showPassword ? <FiEyeOff /> : <FiEye />}
            </button>

          </div>

        </div>

        <label className="mt-5 flex cursor-pointer items-center gap-3">

          <input
            type="checkbox"
            checked={confirmed}
            onChange={(e) =>
              setConfirmed(e.target.checked)
            }
          />

          <span className="text-sm text-gray-300">
            I understand this action cannot be undone.
          </span>

        </label>

        <div className="mt-8 flex justify-end gap-3">

          <button
            onClick={onClose}
            disabled={loading}
            className="rounded-xl border border-gray-700 px-5 py-2 text-white hover:bg-gray-800"
          >
            Cancel
          </button>

          <button
            disabled={
              loading ||
              !confirmed ||
              !password.trim()
            }
            onClick={handleSubmit}
            className="rounded-xl bg-red-600 px-5 py-2 font-semibold text-white transition hover:bg-red-700 disabled:opacity-50"
          >
            {loading
              ? "Deleting..."
              : "Delete Account"}
          </button>

        </div>

      </div>

    </div>
  );

}