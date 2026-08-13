import { useEffect, useState } from "react";
import { toast } from "react-hot-toast";
import axios from "axios";
import {
  FiAtSign,
  FiEdit2,
  FiLock,
  FiMail,
  FiShield,
  FiUser,
  FiSave,
  FiKey,
  FiEye,
  FiEyeOff,
  FiCheckCircle,
  FiAlertCircle,
} from "react-icons/fi";

import { useAuth } from "../../hooks/useAuth";
import {
  getProfile,
  updateProfile,
  changePassword,
  type UserProfile,
} from "../../services/userService";

export default function AdminProfilePage() {
  const { refreshUser } = useAuth();

  const [profile, setProfile] = useState<UserProfile | null>(null);

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [username, setUsername] = useState("");

  const [saving, setSaving] = useState(false);

  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const [changingPassword, setChangingPassword] = useState(false);

  const [showCurrentPassword, setShowCurrentPassword] = useState(false);
  const [showNewPassword, setShowNewPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  useEffect(() => {
    async function loadProfile() {
      try {
        const data = await getProfile();

        setProfile(data);
        setFirstName(data.firstName);
        setLastName(data.lastName);
        setUsername(data.username);
      } catch (error) {
        console.error("Failed to load admin profile", error);
        toast.error("Failed to load profile");
      }
    }

    void loadProfile();
  }, []);

  async function handleSave() {
    try {
      setSaving(true);

      const updated = await updateProfile({
        firstName,
        lastName,
        username,
      });

      setProfile(updated);

      await refreshUser();

      toast.success("Profile updated successfully");
    } catch (error) {
      console.error("Failed to update admin profile", error);
      toast.error("Failed to update profile");
    } finally {
      setSaving(false);
    }
  }

  async function handleChangePassword() {
    if (!currentPassword || !newPassword || !confirmPassword) {
      toast.error("Please fill in all password fields.");
      return;
    }

    if (currentPassword === newPassword) {
      toast.error(
        "New password must be different from your current password."
      );
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

    if (newPassword !== confirmPassword) {
      toast.error( "New passwords do not match.");
      return;
    }

    try {
      setChangingPassword(true);

      await changePassword({
        currentPassword,
        newPassword,
        confirmPassword,
      });

      setCurrentPassword("");
      setNewPassword("");
      setConfirmPassword("");

      toast.success("Administrator password changed successfully.");

      } catch (error: unknown) {
        console.error("Password change failed:", error);

        if (axios.isAxiosError(error)) {
          const message =
            error.response?.data?.message ||
            "Failed to change password. Please try again.";

          toast.error(message);
        } else {
          toast.error("Failed to change password. Please try again.");
        }
      } finally {
      setChangingPassword(false);
    }
  }

  if (!profile) {
    return (
      <div className="mx-auto w-full max-w-6xl">
        <section className="app-surface app-border rounded-3xl p-8">
          <p className="app-text-secondary">
            Loading admin profile...
          </p>
        </section>
      </div>
    );
  }

  return (
    <div className="mx-auto w-full max-w-6xl space-y-6">

      {/* HEADER */}

      <section className="app-surface app-border rounded-3xl p-8">

        <div className="flex flex-col gap-6 sm:flex-row sm:items-center sm:justify-between">

          <div className="flex items-center gap-5">

            <div className="flex h-16 w-16 items-center justify-center rounded-2xl bg-blue-500/10">
              <FiShield className="text-3xl text-blue-500" />
            </div>

            <div>
              <p className="text-sm font-semibold uppercase tracking-wider text-blue-500">
                Administrator Account
              </p>

              <h1 className="mt-1 text-3xl font-bold">
                {profile.firstName} {profile.lastName}
              </h1>

              <p className="mt-1 app-text-secondary">
                Manage your administrator profile and account identity.
              </p>
            </div>

          </div>

          <span className="inline-flex w-fit items-center gap-2 rounded-full bg-blue-500/10 px-4 py-2 text-sm font-semibold text-blue-400">
            <FiShield />
            {profile.role}
          </span>

        </div>

      </section>


      {/* PROFILE INFORMATION */}

      <section className="app-surface app-border rounded-3xl p-8">

        <div className="mb-7 flex items-center gap-4">

          <div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-blue-500/10">
            <FiEdit2 className="text-xl text-blue-500" />
          </div>

          <div>
            <h2 className="text-2xl font-bold">
              Profile Information
            </h2>

            <p className="mt-1 text-sm app-text-secondary">
              Update your personal administrator information.
            </p>
          </div>

        </div>


        <div className="grid gap-5 md:grid-cols-2">

          {/* FIRST NAME */}

          <div>
            <label className="mb-2 flex items-center gap-2 text-sm font-semibold">
              <FiUser className="text-blue-500" />
              First Name
            </label>

            <input
              value={firstName}
              onChange={(e) => setFirstName(e.target.value)}
              className="app-input w-full rounded-xl border px-4 py-3.5 outline-none transition focus:border-blue-500 focus:ring-2 focus:ring-blue-500/20"
            />
          </div>


          {/* LAST NAME */}

          <div>
            <label className="mb-2 flex items-center gap-2 text-sm font-semibold">
              <FiUser className="text-blue-500" />
              Last Name
            </label>

            <input
              value={lastName}
              onChange={(e) => setLastName(e.target.value)}
              className="app-input w-full rounded-xl border px-4 py-3.5 outline-none transition focus:border-blue-500 focus:ring-2 focus:ring-blue-500/20"
            />
          </div>


          {/* USERNAME */}

          <div className="md:col-span-2">

            <label className="mb-2 flex items-center gap-2 text-sm font-semibold">
              <FiAtSign className="text-blue-500" />
              Username
            </label>

            <input
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              className="app-input w-full rounded-xl border px-4 py-3.5 outline-none transition focus:border-blue-500 focus:ring-2 focus:ring-blue-500/20"
            />

            <p className="mt-2 text-xs app-text-secondary">
              This username is used as your administrator identity.
            </p>

          </div>


          {/* EMAIL */}

          <div className="md:col-span-2">

            <label className="mb-2 flex items-center gap-2 text-sm font-semibold">
              <FiMail className="text-blue-500" />
              Email
            </label>

            <div className="relative">

              <input
                value={profile.email}
                disabled
                className="w-full cursor-not-allowed rounded-xl border app-border bg-slate-100 px-4 py-3.5 pr-11 app-text-secondary outline-none dark:bg-slate-800/40"
              />

              <FiLock className="absolute right-4 top-1/2 -translate-y-1/2 text-slate-500" />

            </div>

            <p className="mt-2 text-xs app-text-secondary">
              Email is managed by your authentication provider.
            </p>

          </div>

        </div>


        {/* SAVE */}

        <div className="mt-7 flex justify-end border-t border-slate-800 pt-6">

          <button
            onClick={handleSave}
            disabled={saving}
            className="inline-flex items-center gap-2 rounded-xl bg-gradient-to-r from-blue-600 to-indigo-600 px-6 py-3 font-semibold text-white shadow-lg shadow-blue-500/20 transition hover:-translate-y-0.5 disabled:cursor-not-allowed disabled:opacity-60"
          >
            <FiSave />

            {saving ? "Saving..." : "Save Changes"}
          </button>

        </div>

      </section>

      {/* CHANGE PASSWORD */}

      <section className="app-surface app-border overflow-hidden rounded-3xl border">

        {/* HEADER */}

        <div className="border-b app-border p-6 sm:p-8">

          <div className="flex items-start gap-4">

            <div className="flex h-12 w-12 shrink-0 items-center justify-center rounded-2xl bg-blue-500/10">
              <FiKey className="text-xl text-blue-400" />
            </div>

            <div className="min-w-0">

              <div className="flex flex-wrap items-center gap-3">

                <h2 className="text-2xl font-bold">
                  Change Password
                </h2>

                <span className="inline-flex items-center rounded-full border border-blue-500/20 bg-blue-500/10 px-3 py-1 text-xs font-semibold text-blue-400">
                  SECURE
                </span>

              </div>

              <p className="mt-2 max-w-2xl text-sm leading-6 app-text-secondary">
                Update your administrator password to keep your account secure.
                You will need to enter your current password before setting a new one.
              </p>

            </div>

          </div>

        </div>


        {/* BODY */}

        <div className="p-6 sm:p-8">

          <div className="grid gap-5 md:grid-cols-2">

            {/* CURRENT PASSWORD */}

            <PasswordInput
              label="Current Password"
              value={currentPassword}
              onChange={setCurrentPassword}
              placeholder="Enter current password"
              visible={showCurrentPassword}
              onToggle={() =>
                setShowCurrentPassword((value) => !value)
              }
            />


            {/* NEW PASSWORD */}

            <PasswordInput
              label="New Password"
              value={newPassword}
              onChange={setNewPassword}
              placeholder="Create a new password"
              visible={showNewPassword}
              onToggle={() =>
                setShowNewPassword((value) => !value)
              }
            />


            {/* CONFIRM PASSWORD */}

            <div className="md:col-span-2">

              <PasswordInput
                label="Confirm New Password"
                value={confirmPassword}
                onChange={setConfirmPassword}
                placeholder="Re-enter your new password"
                visible={showConfirmPassword}
                onToggle={() =>
                  setShowConfirmPassword((value) => !value)
                }
              />

            </div>

          </div>


          {/* PASSWORD REQUIREMENTS */}

          <div className="mt-6 rounded-2xl border app-border app-surface-secondary p-5">

            <div className="mb-4 flex items-center gap-2">

              <FiShield className="text-blue-400" />

              <p className="text-sm font-semibold">
                Password requirements
              </p>

            </div>

            <div className="grid gap-3 sm:grid-cols-2">

              <PasswordRequirement
                valid={newPassword.length >= 8}
                text="At least 8 characters"
              />

              <PasswordRequirement
                valid={/[A-Z]/.test(newPassword)}
                text="At least one uppercase letter"
              />

              <PasswordRequirement
                valid={/[a-z]/.test(newPassword)}
                text="At least one lowercase letter"
              />

              <PasswordRequirement
                valid={/[0-9]/.test(newPassword)}
                text="At least one number"
              />

              <PasswordRequirement
                valid={newPassword !== "" && newPassword === confirmPassword}
                text="Passwords match"
              />

            </div>

          </div>

        </div>


        {/* FOOTER */}

        <div className="flex flex-col gap-4 border-t app-border p-6 sm:flex-row sm:items-center sm:justify-between sm:p-8">

          <div className="flex items-start gap-3">

            <div className="mt-0.5 flex h-8 w-8 shrink-0 items-center justify-center rounded-lg bg-emerald-500/10">
              <FiLock className="text-sm text-emerald-400" />
            </div>

            <div>

              <p className="text-sm font-semibold">
                Your password is protected
              </p>

              <p className="mt-1 text-xs app-text-secondary">
                Your new password is securely encrypted before being stored.
              </p>

            </div>

          </div>


          <button
            type="button"
            onClick={() => void handleChangePassword()}
            disabled={changingPassword}
            className="
              inline-flex
              min-w-[190px]
              items-center
              justify-center
              gap-2
              rounded-xl
              bg-gradient-to-r
              from-blue-600
              to-indigo-600
              px-6
              py-3.5
              font-semibold
              text-white
              shadow-lg
              shadow-blue-500/20
              transition
              hover:-translate-y-0.5
              hover:shadow-blue-500/30
              disabled:cursor-not-allowed
              disabled:opacity-60
              disabled:hover:translate-y-0
            "
          >

            {changingPassword ? (
              <>
                <span className="h-4 w-4 animate-spin rounded-full border-2 border-white/30 border-t-white" />
                Changing Password...
              </>
            ) : (
              <>
                <FiKey />
                Change Password
              </>
            )}

          </button>

        </div>

      </section>


      {/* ACCOUNT INFORMATION */}

      <section className="app-surface app-border rounded-3xl p-8">

        <div className="mb-6">

          <h2 className="text-xl font-bold">
            Account Information
          </h2>

          <p className="mt-1 text-sm app-text-secondary">
            System information associated with this administrator account.
          </p>

        </div>


        <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">

          <InfoCard
            label="User ID"
            value={String(profile.userId)}
          />

          <InfoCard
            label="Role"
            value={profile.role}
          />

          <InfoCard
            label="Authentication"
            value={profile.provider}
          />

        </div>

      </section>


      {/* ADMIN NOTICE */}

      <section className="rounded-3xl border border-blue-500/20 bg-blue-500/5 p-6">

        <div className="flex items-start gap-4">

          <div className="flex h-11 w-11 shrink-0 items-center justify-center rounded-xl bg-blue-500/10">
            <FiShield className="text-xl text-blue-500" />
          </div>

          <div>

            <h3 className="font-semibold">
              Administrator Access
            </h3>

            <p className="mt-1 text-sm leading-6 app-text-secondary">
              Your administrator permissions are controlled by your
              account role. Platform management actions are available
              from the administrator control center.
            </p>

          </div>

        </div>

      </section>

      {/* ACCOUNT STATUS */}

      <section className="app-surface app-border rounded-3xl p-8">
        <div className="mb-6">
          <h2 className="text-xl font-bold">
            Account Status
          </h2>

          <p className="mt-1 text-sm app-text-secondary">
            Current status and access level of this administrator account.
          </p>
        </div>

        <div className="grid gap-4 sm:grid-cols-2">
          <StatusCard
            label="Account Status"
            value="Active"
            positive
          />

          <StatusCard
            label="Access Level"
            value={
              profile.role === "ADMIN"
                ? "Administrator"
                : profile.role
            }
            positive
          />
        </div>
      </section>


      {/* ADMIN ACCESS */}

      <section className="rounded-3xl border border-blue-500/20 bg-blue-500/5 p-6">
        <div className="flex items-start gap-4">
          <div className="flex h-11 w-11 shrink-0 items-center justify-center rounded-xl bg-blue-500/10">
            <FiShield className="text-xl text-blue-500" />
          </div>

          <div>
            <h3 className="font-semibold">
              Administrator Access
            </h3>

            <p className="mt-1 text-sm leading-6 app-text-secondary">
              This account has administrator access to the CodeMentorAI
              control center. Your available actions are determined by
              your administrator role.
            </p>
          </div>
        </div>
      </section>

    </div>
  );
}


function PasswordInput({
  label,
  value,
  onChange,
  placeholder,
  visible,
  onToggle,
}: {
  label: string;
  value: string;
  onChange: (value: string) => void;
  placeholder: string;
  visible: boolean;
  onToggle: () => void;
}) {
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
          autoComplete="new-password"
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
          aria-label={
            visible
              ? `Hide ${label}`
              : `Show ${label}`
          }
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
          {visible ? <FiEyeOff /> : <FiEye />}
        </button>
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
    <div className="flex items-center gap-2">

      {valid ? (
        <FiCheckCircle className="shrink-0 text-emerald-400" />
      ) : (
        <FiAlertCircle className="shrink-0 text-slate-500" />
      )}

      <span
        className={
          valid
            ? "text-sm text-emerald-400"
            : "text-sm app-text-secondary"
        }
      >
        {text}
      </span>

    </div>
  );
}


function InfoCard({
  label,
  value,
}: {
  label: string;
  value: string;
}) {
  return (
    <div className="rounded-2xl border app-border app-surface-secondary p-5">
      <p className="text-xs font-semibold uppercase tracking-wider app-text-muted">
        {label}
      </p>

      <p className="mt-2 font-semibold app-text-primary">
        {value}
      </p>
    </div>
  );
}

function StatusCard({
  label,
  value,
  positive = false,
}: {
  label: string;
  value: string;
  positive?: boolean;
}) {
  return (
    <div className="rounded-2xl border app-border app-surface-secondary p-5">
      <p className="text-xs font-semibold uppercase tracking-wider app-text-muted">
        {label}
      </p>

      <div className="mt-2 flex items-center gap-2">
        <span
          className={`h-2.5 w-2.5 rounded-full ${
            positive
              ? "bg-emerald-500"
              : "bg-slate-500"
          }`}
        />

        <p className="font-semibold app-text-primary">
          {value}
        </p>
      </div>
    </div>
  );
}