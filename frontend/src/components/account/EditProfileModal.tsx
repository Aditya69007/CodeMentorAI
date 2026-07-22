import type { AuthUser } from "../../types/auth";

interface EditProfileModalProps {
  open: boolean;
  user: AuthUser;
  firstName: string;
  lastName: string;
  onFirstNameChange: (value: string) => void;
  onLastNameChange: (value: string) => void;
  onCancel: () => void;
  onSave: () => void;
}

export default function EditProfileModal({
  open,
  user,
  firstName,
  lastName,
  onFirstNameChange,
  onLastNameChange,
  onCancel,
  onSave,
}: EditProfileModalProps) {

  if (!open) return null;

  return (

    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 backdrop-blur-sm">

      <div className="app-surface app-border w-full max-w-lg rounded-2xl p-6">

        <h2 className="text-2xl font-bold">
          Edit Profile
        </h2>

        <p className="app-text-secondary mt-2">
          Update your personal information.
        </p>

        <div className="mt-6 space-y-4">

          <input
            value={firstName}
            onChange={(e) => onFirstNameChange(e.target.value)}
            className="app-surface-secondary app-border w-full rounded-xl border px-4 py-3 outline-none"
            placeholder="First Name"
          />

          <input
            value={lastName}
            onChange={(e) => onLastNameChange(e.target.value)}
            className="app-surface-secondary app-border w-full rounded-xl border px-4 py-3 outline-none"
            placeholder="Last Name"
          />

          <input
            value={user.email}
            disabled
            className="app-surface-secondary app-border w-full rounded-xl border px-4 py-3 opacity-70"
          />

        </div>

        <div className="mt-8 flex justify-end gap-3">

          <button
            onClick={onCancel}
            className="rounded-xl border px-5 py-2"
          >
            Cancel
          </button>

          <button
            onClick={onSave}
            className="rounded-xl bg-blue-600 px-5 py-2 text-white"
          >
            Save Changes
          </button>

        </div>

      </div>

    </div>

  );

}