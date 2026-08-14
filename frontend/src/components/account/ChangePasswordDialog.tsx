import { useState } from "react";
import {
  FiAlertCircle,
  FiCheckCircle,
  FiEye,
  FiEyeOff,
  FiLock,
  FiX,
} from "react-icons/fi";
import toast from "react-hot-toast";
import { forgotPassword, changePassword } from "../../services/authService";
import { useAuth } from "../../hooks/useAuth";

import { getApiErrorMessage } from "../../services/api";

interface ChangePasswordDialogProps {
  open: boolean;
  onClose: () => void;
}

interface PasswordInputProps {
  label: string;
  value: string;
  onChange: (value: string) => void;
  placeholder: string;
  visible: boolean;
  onToggle: () => void;
  autoComplete?: string;
}

function PasswordInput({
  label,
  value,
  onChange,
  placeholder,
  visible,
  onToggle,
  autoComplete = "current-password",
}: PasswordInputProps) {
  return (
    <div>
      <label className="mb-2 flex items-center gap-2 text-sm font-semibold">
        <FiLock className="text-blue-400" />
        {label}
      </label>

      <div className="relative">
        <input
          type={visible ? "text" : "password"}
          value={value}
          onChange={(e) => onChange(e.target.value)}
          placeholder={placeholder}
          autoComplete={autoComplete}
          className="
            app-input
            h-12
            w-full
            rounded-xl
            border
            px-4
            pr-12
            outline-none
            transition
            focus:border-blue-500
            focus:ring-2
            focus:ring-blue-500/20
          "
        />

        <button
          type="button"
          onClick={onToggle}
          aria-label={visible ? `Hide ${label}` : `Show ${label}`}
          className="
            absolute
            right-3
            top-1/2
            flex
            h-8
            w-8
            -translate-y-1/2
            items-center
            justify-center
            rounded-lg
            text-slate-400
            transition
            hover:bg-slate-700/50
            hover:text-blue-400
          "
        >
          {visible ? <FiEyeOff size={18} /> : <FiEye size={18} />}
        </button>
      </div>
    </div>
  );
}

export default function ChangePasswordDialog({
  open,
  onClose,
}: ChangePasswordDialogProps) {

  const { user } = useAuth();

const [resetLoading, setResetLoading] = useState(false);

  const [showCurrent, setShowCurrent] = useState(false);
  const [showNew, setShowNew] = useState(false);
  const [showConfirm, setShowConfirm] = useState(false);

  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const [loading, setLoading] = useState(false);

  if (!open) {
    return null;
  }

  const resetForm = () => {
    setCurrentPassword("");
    setNewPassword("");
    setConfirmPassword("");

    setShowCurrent(false);
    setShowNew(false);
    setShowConfirm(false);
  };

  const handleClose = () => {
    if (loading) return;

    resetForm();
    onClose();
  };

  const handleSubmit = async () => {
    if (loading) return;

    if (!currentPassword || !newPassword || !confirmPassword) {
      toast.error("Please fill in all password fields.");
      return;
    }

    if (newPassword.length < 8) {
      toast.error("Password must be at least 8 characters.");
      return;
    }

    if (!/[A-Z]/.test(newPassword)) {
      toast.error("Password must contain an uppercase letter.");
      return;
    }

    if (!/[a-z]/.test(newPassword)) {
      toast.error("Password must contain a lowercase letter.");
      return;
    }

    if (!/[0-9]/.test(newPassword)) {
      toast.error("Password must contain a number.");
      return;
    }

    if (currentPassword === newPassword) {
      toast.error(
        "New password must be different from your current password."
      );
      return;
    }

    if (newPassword !== confirmPassword) {
      toast.error("New passwords do not match.");
      return;
    }

    try {
      setLoading(true);

      await changePassword({
        currentPassword,
        newPassword,
        confirmPassword,
      });

      toast.success("Password changed successfully.");

      resetForm();
      onClose();
    } catch (error: unknown) {
      console.error("Password change failed:", error);

      toast.error(
        getApiErrorMessage(
          error,
          "Failed to change password. Please try again."
        )
      );
    } finally {
      setLoading(false);
    }
  };

  const handleForgotPassword = async () => {

    if (!user?.email || resetLoading) {
      return;
    }

    try {

      setResetLoading(true);

      await forgotPassword({
        email: user.email,
      });

      toast.success(
        "Password reset link has been sent to your email."
      );

    } catch (error: unknown) {

      toast.error(
        getApiErrorMessage(
          error,
          "Failed to send password reset link."
        )
      );

    } finally {

      setResetLoading(false);

    }
  };


  return (
    <div
      className="
        fixed
        inset-0
        z-[9999]
        flex
        items-center
        justify-center
        bg-black/70
        p-4
        backdrop-blur-sm
      "
      onMouseDown={(event) => {
        if (event.target === event.currentTarget) {
          handleClose();
        }
      }}
    >
    <div
      className="
        flex
        w-full
        max-w-xl
        max-h-[calc(100vh-2rem)]
        flex-col
        overflow-hidden
        rounded-2xl
        border
        app-border
        app-surface
        shadow-2xl
      "
        onMouseDown={(event) => event.stopPropagation()}
      >
        {/* HEADER */}
        <div
          className="
            flex
            items-center
            justify-between
            border-b
            app-border
            px-7
            py-6
          "
        >
          <div>
            <div className="flex items-center gap-3">
              <div
                className="
                  flex
                  h-11
                  w-11
                  items-center
                  justify-center
                  rounded-xl
                  bg-blue-500/10
                  text-blue-400
                "
              >
                <FiLock size={21} />
              </div>

              <div>
                <h2 className="text-xl font-bold">
                  Change Password
                </h2>

                <p className="mt-1 text-sm app-text-secondary">
                  Update your account password securely.
                </p>
              </div>
            </div>
          </div>

          <button
            type="button"
            onClick={handleClose}
            disabled={loading}
            aria-label="Close"
            className="
              flex
              h-9
              w-9
              items-center
              justify-center
              rounded-lg
              app-text-secondary
              transition
              hover:bg-slate-500/10
              hover:text-white
              disabled:cursor-not-allowed
              disabled:opacity-50
            "
          >
            <FiX size={20} />
          </button>
        </div>

        {/* BODY */}
        <div className="min-h-0 flex-1 space-y-5 overflow-y-auto px-7 py-6">
          <PasswordInput
            label="Current Password"
            value={currentPassword}
            onChange={setCurrentPassword}
            placeholder="Enter your current password"
            visible={showCurrent}
            onToggle={() => setShowCurrent((value) => !value)}
            autoComplete="current-password"
          />

          <div className="rounded-xl border app-border bg-blue-500/5 p-4">

            <p className="text-sm font-medium">
              Don't remember your current password?
            </p>

            <p className="mt-1 text-xs app-text-secondary">
              We'll send a password reset link to your registered email.
            </p>

            <button
              type="button"
              onClick={handleForgotPassword}
              disabled={resetLoading}
              className="
                mt-3
                text-sm
                font-semibold
                text-blue-500
                transition
                hover:text-blue-400
                disabled:cursor-not-allowed
                disabled:opacity-50
              "
            >
              {resetLoading
                ? "Sending..."
                : "Send Reset Link"}
            </button>

          </div>

          <PasswordInput
            label="New Password"
            value={newPassword}
            onChange={setNewPassword}
            placeholder="Enter your new password"
            visible={showNew}
            onToggle={() => setShowNew((value) => !value)}
            autoComplete="new-password"
          />

          <PasswordInput
            label="Confirm Password"
            value={confirmPassword}
            onChange={setConfirmPassword}
            placeholder="Confirm your new password"
            visible={showConfirm}
            onToggle={() => setShowConfirm((value) => !value)}
            autoComplete="new-password"
          />

          {/* PASSWORD REQUIREMENTS */}
          <div
            className="
              rounded-xl
              border
              border-blue-500/10
              bg-blue-500/5
              p-4
            "
          >
            <p className="mb-3 text-sm font-semibold">
              Password requirements
            </p>

            <div className="grid gap-2 text-xs sm:grid-cols-2">
              <PasswordRequirement
                valid={newPassword.length >= 8}
                text="At least 8 characters"
              />

              <PasswordRequirement
                valid={/[A-Z]/.test(newPassword)}
                text="One uppercase letter"
              />

              <PasswordRequirement
                valid={/[a-z]/.test(newPassword)}
                text="One lowercase letter"
              />

              <PasswordRequirement
                valid={/[0-9]/.test(newPassword)}
                text="One number"
              />
            </div>
          </div>
        </div>

        {/* FOOTER */}
        <div
          className="
            flex
            items-center
            justify-end
            gap-3
            border-t
            app-border
            px-7
            py-5
          "
        >
          <button
            type="button"
            onClick={handleClose}
            disabled={loading}
            className="
              rounded-xl
              border
              app-border
              px-5
              py-2.5
              text-sm
              font-semibold
              transition
              hover:border-red-500/50
              hover:bg-red-500/5
              hover:text-red-400
              disabled:cursor-not-allowed
              disabled:opacity-50
            "
          >
            Cancel
          </button>

          <button
            type="button"
            onClick={handleSubmit}
            disabled={loading}
            className="
              inline-flex
              min-w-[155px]
              items-center
              justify-center
              gap-2
              rounded-xl
              bg-blue-600
              px-5
              py-2.5
              text-sm
              font-semibold
              text-white
              shadow-lg
              shadow-blue-600/20
              transition
              hover:bg-blue-700
              disabled:cursor-not-allowed
              disabled:opacity-60
            "
          >
            {loading ? (
              <>
                <span
                  className="
                    h-4
                    w-4
                    animate-spin
                    rounded-full
                    border-2
                    border-white/30
                    border-t-white
                  "
                />
                Updating...
              </>
            ) : (
              <>
                <FiCheckCircle size={16} />
                Update Password
              </>
            )}
          </button>
        </div>
      </div>
    </div>
  );
}

function PasswordRequirement({
  valid,
  text,
}: {
  valid: boolean;
  text: string;
}) {
  return (
    <div
      className={`flex items-center gap-2 ${
        valid ? "text-emerald-400" : "app-text-secondary"
      }`}
    >
      {valid ? (
        <FiCheckCircle size={14} />
      ) : (
        <FiAlertCircle size={14} />
      )}

      <span>{text}</span>
    </div>
  );
}