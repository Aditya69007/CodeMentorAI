import { useEffect, useState } from "react";
import { toast } from "sonner";
import { useAuth } from "../../hooks/useAuth";
import {
  FiUser,
  FiMail,
  FiAtSign,
  FiSave,
  FiLock,
} from "react-icons/fi";
import {
  getProfile,
  updateProfile,
} from "../../services/userService";

export default function PersonalInfoCard() {
  const { refreshUser } = useAuth();

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");

  const [saving, setSaving] = useState(false);

  useEffect(() => {
    async function loadProfile() {
      try {
        const profile = await getProfile();

        setFirstName(profile.firstName);
        setLastName(profile.lastName);
        setUsername(profile.username);
        setEmail(profile.email);
      } catch (error) {
        console.error(error);
      }
    }

    loadProfile();
  }, []);

  async function handleSave() {
    try {
      setSaving(true);

      await updateProfile({
        firstName,
        lastName,
        username,
      });

      await refreshUser();

      toast.success("Profile updated successfully");
    } catch (error) {
      console.error(error);
      toast.error("Failed to update profile");
    } finally {
      setSaving(false);
    }
  }

  return (
    <section
      id="personal-information"
      className="app-surface app-border rounded-3xl p-6 sm:p-8"
    >
      {/* Header */}
      <div className="mb-7 flex items-center gap-4">
        <div className="flex h-12 w-12 shrink-0 items-center justify-center rounded-2xl bg-blue-500/10">
          <FiUser className="text-xl text-blue-500" />
        </div>

        <div>
          <h2 className="text-2xl font-bold">
            Personal Information
          </h2>

          <p className="mt-1 text-sm app-text-secondary">
            Manage your account details and public identity.
          </p>
        </div>
      </div>

      {/* Form */}
      <div className="space-y-5">

        {/* First + Last Name */}
        <div className="grid gap-5 sm:grid-cols-2">
          
          <div>
            <label className="mb-2 flex items-center gap-2 text-sm font-semibold">
              <FiUser className="text-blue-500" />
              First Name
            </label>

            <input
              value={firstName}
              onChange={(e) => setFirstName(e.target.value)}
              placeholder="First name"
              className="
                w-full
                cursor-not-allowed
                rounded-xl
                border
                app-border
                app-surface-secondary
                px-4 py-3.5
                pr-11
                app-text-muted
                outline-none
              "
            />
          </div>

          <div>
            <label className="mb-2 flex items-center gap-2 text-sm font-semibold">
              <FiUser className="text-blue-500" />
              Last Name
            </label>

            <input
              value={lastName}
              onChange={(e) => setLastName(e.target.value)}
              placeholder="Last name"
              className="
                w-full
                rounded-xl
                border app-input
                px-4 py-3.5
                outline-none
                transition
                placeholder:text-slate-500
                hover:border-slate-600
                focus:border-blue-500
                focus:ring-2
                focus:ring-blue-500/20
              "
            />
          </div>

        </div>

        {/* Username */}
        <div>
          <label className="mb-2 flex items-center gap-2 text-sm font-semibold">
            <FiAtSign className="text-blue-500" />
            Username
          </label>

          <input
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            placeholder="username"
            className="
              w-full
              rounded-xl
              border app-input
              px-4 py-3.5
              outline-none
              transition
              placeholder:text-slate-500
              hover:border-slate-600
              focus:border-blue-500
              focus:ring-2
              focus:ring-blue-500/20
            "
          />

          <div className="mt-2 flex flex-wrap items-center gap-2 text-xs app-text-secondary">
            <span>Your public portfolio:</span>

            <a
              href={`/portfolio/${username}`}
              target="_blank"
              rel="noreferrer"
              className="
                font-semibold
                text-blue-500
                transition
                hover:text-blue-400
                hover:underline
              "
            >
              /portfolio/{username || "username"}
            </a>
          </div>
        </div>

        {/* Email */}
        <div>
          <label className="mb-2 flex items-center gap-2 text-sm font-semibold">
            <FiMail className="text-blue-500" />
            Email
          </label>

          <div className="relative">
          <input
            value={email}
            disabled
            className="
              w-full
              cursor-not-allowed
              rounded-xl
              border
              app-border
              bg-slate-100
              px-4 py-3.5
              pr-11
              app-text-secondary
              outline-none
              dark:bg-slate-800/40
            "
          />

            <FiLock
              className="
                absolute
                right-4
                top-1/2
                -translate-y-1/2
                text-slate-500
              "
            />
          </div>

          <p className="mt-2 text-xs app-text-secondary">
            Email is managed by your authentication provider.
          </p>
        </div>

        {/* Divider */}
        <div className="border-t border-slate-800 pt-5">
          <div className="flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">

            <p className="text-xs app-text-secondary">
              Changes will update your public developer profile.
            </p>

            <button
              onClick={handleSave}
              disabled={saving}
              className="
                inline-flex
                items-center
                justify-center
                gap-2
                rounded-xl
                bg-gradient-to-r
                from-blue-600
                to-indigo-600
                px-6
                py-3
                font-semibold
                text-white
                shadow-lg
                shadow-blue-500/20
                transition
                hover:-translate-y-0.5
                hover:shadow-blue-500/30
                disabled:cursor-not-allowed
                disabled:opacity-60
              "
            >
              <FiSave />

              {saving ? "Saving..." : "Save Changes"}
            </button>

          </div>
        </div>

      </div>
    </section>
  );
}