import { FiX, FiEye, FiEyeOff } from "react-icons/fi";
import { useState } from "react";
import { changePassword } from "../../services/authService";
import { getApiErrorMessage } from "../../services/api";


interface ChangePasswordDialogProps {
  open: boolean;
  onClose: () => void;
}

export default function ChangePasswordDialog({
  open,
  onClose,
}: ChangePasswordDialogProps) {
  const [showCurrent, setShowCurrent] = useState(false);
  const [showNew, setShowNew] = useState(false);
  const [showConfirm, setShowConfirm] = useState(false);

  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const handleSubmit = async () => {

    setError("");
    setSuccess("");

    if (!currentPassword || !newPassword || !confirmPassword) {
        setError("Please fill in all fields.");
        return;
    }

    try {

        setLoading(true);

        const message = await changePassword({
        currentPassword,
        newPassword,
        confirmPassword,
        });

        setSuccess(message);

        setCurrentPassword("");
        setNewPassword("");
        setConfirmPassword("");

        setTimeout(() => {
        onClose();
        setSuccess("");
        }, 1500);

    } catch (error) {

        setError(getApiErrorMessage(error));

    } finally {

        setLoading(false);

    }
    };

  if (!open) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 p-4">

      <div className="w-full max-w-lg rounded-2xl app-surface app-border shadow-2xl">

        <div className="flex items-center justify-between border-b p-6">

          <div>

            <h2 className="text-2xl font-bold">
              Change Password
            </h2>

            <p className="app-text-secondary mt-1">
              Update your account password securely.
            </p>

          </div>

          <button
            onClick={onClose}
            className="rounded-lg p-2 hover:bg-white/10"
          >
            <FiX size={20} />
          </button>

        </div>

        <div className="space-y-5 p-6">

            {error && (
            <div className="rounded-xl border border-red-500 bg-red-500/10 p-3 text-sm text-red-500">
                {error}
            </div>
            )}

            {success && (
            <div className="rounded-xl border border-emerald-500 bg-emerald-500/10 p-3 text-sm text-emerald-500">
                {success}
            </div>
            )}

          {/* Current Password */}

          <div>

            <label className="mb-2 block text-sm font-medium">
              Current Password
            </label>

            <div className="relative">

              <input
                type={showCurrent ? "text" : "password"}
                value={currentPassword}
                onChange={(e) => setCurrentPassword(e.target.value)}
                className="w-full rounded-xl border bg-transparent px-4 py-3 pr-12 outline-none focus:border-blue-500"
              />

              <button
                type="button"
                onClick={() => setShowCurrent(!showCurrent)}
                className="absolute right-4 top-3"
              >
                {showCurrent ? <FiEyeOff /> : <FiEye />}
              </button>

            </div>

          </div>

          {/* New Password */}

          <div>

            <label className="mb-2 block text-sm font-medium">
              New Password
            </label>

            <div className="relative">

              <input
                type={showNew ? "text" : "password"}
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
                className="w-full rounded-xl border bg-transparent px-4 py-3 pr-12 outline-none focus:border-blue-500"
              />

              <button
                type="button"
                onClick={() => setShowNew(!showNew)}
                className="absolute right-4 top-3"
              >
                {showNew ? <FiEyeOff /> : <FiEye />}
              </button>

            </div>

          </div>

          {/* Confirm Password */}

          <div>

            <label className="mb-2 block text-sm font-medium">
              Confirm Password
            </label>

            <div className="relative">

              <input
                type={showConfirm ? "text" : "password"}
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                className="w-full rounded-xl border bg-transparent px-4 py-3 pr-12 outline-none focus:border-blue-500"
              />

              <button
                type="button"
                onClick={() => setShowConfirm(!showConfirm)}
                className="absolute right-4 top-3"
              >
                {showConfirm ? <FiEyeOff /> : <FiEye />}
              </button>

            </div>

          </div>

        </div>

        <div className="flex justify-end gap-3 border-t p-6">

          <button
            onClick={onClose}
            className="rounded-xl border px-5 py-2 hover:border-red-500 hover:text-red-500"
          >
            Cancel
          </button>

            <button
            onClick={handleSubmit}
            disabled={loading}
            className="rounded-xl bg-blue-600 px-5 py-2 text-white transition hover:bg-blue-700 disabled:cursor-not-allowed disabled:opacity-50"
            >
            {loading ? "Updating..." : "Update Password"}
            </button>

        </div>

      </div>

    </div>
  );
}