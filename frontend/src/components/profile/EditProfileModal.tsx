import { useRef, useState } from "react";
import {
  FiLock,
  FiMail,
  FiSave,
  FiTrash2,
  FiCamera,
  FiUser,
  FiX,
  FiAtSign,
} from "react-icons/fi";
import { toast } from "sonner";

import type { AuthUser } from "../../types/auth";
import UserAvatar from "../common/UserAvatar";
import {
  updateProfile,
  updateProfilePicture,
  removeProfilePicture,
} from "../../services/userService";

interface EditProfileModalProps {
  user: AuthUser;
  onClose: () => void;
  onSaved: () => Promise<void>;
}

export default function EditProfileModal({
  user,
  onClose,
  onSaved,
}: EditProfileModalProps) {

  const fileInputRef = useRef<HTMLInputElement>(null);

  const [firstName, setFirstName] = useState(user.firstName);
  const [lastName, setLastName] = useState(user.lastName);
  const [username, setUsername] = useState(user.username);

  const [uploading, setUploading] = useState(false);
  const [saving, setSaving] = useState(false);

  const [preview, setPreview] = useState<string | null>(
    user.profilePicture ?? null
  );

  const handlePhotoChange = async (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {

    const file = event.target.files?.[0];

    if (!file) {
      return;
    }

    if (!file.type.startsWith("image/")) {
      toast.error("Please select a valid image.");
      event.target.value = "";
      return;
    }

    if (file.size > 5 * 1024 * 1024) {
      toast.error("Image must be smaller than 5 MB.");
      event.target.value = "";
      return;
    }

    try {

      setUploading(true);

      const image = await updateProfilePicture(file);

      setPreview(image);

      await onSaved();

      toast.success("Profile picture updated.");

    } catch (error) {

      console.error(error);

      toast.error("Failed to update profile picture.");

    } finally {

      setUploading(false);
      event.target.value = "";

    }
  };

  const handleSave = async () => {

    if (!firstName.trim()) {
      toast.error("First name is required.");
      return;
    }

    if (!lastName.trim()) {
      toast.error("Last name is required.");
      return;
    }

    if (!username.trim()) {
      toast.error("Username is required.");
      return;
    }

    try {

      setSaving(true);

      await updateProfile({
        firstName: firstName.trim(),
        lastName: lastName.trim(),
        username: username.trim(),
      });

      await onSaved();

      toast.success("Profile updated successfully.");

      onClose();

    } catch (error) {

      console.error(error);

      toast.error("Failed to update profile.");

    } finally {

      setSaving(false);

    }
  };

    const handleRemovePhoto = async () => {

    if (!preview) {
        return;
    }

    try {

        setUploading(true);

        await removeProfilePicture();

        setPreview(null);

        await onSaved();

        toast.success("Profile photo removed successfully.");

    } catch (error) {

        console.error(error);

        toast.error("Failed to remove profile photo.");

    } finally {

        setUploading(false);

    }
    };

  return (
    <div
      className="
        fixed
        inset-0
        z-50
        flex
        items-center
        justify-center
        bg-black/70
        px-4
        py-6
        backdrop-blur-sm
      "
      onMouseDown={(event) => {
        if (event.target === event.currentTarget) {
          onClose();
        }
      }}
    >

      <div
        className="
          flex
          max-h-[90vh]
          w-full
          max-w-2xl
          flex-col
          overflow-hidden
          rounded-3xl
          border
          border-slate-700/70
          bg-slate-900
          shadow-2xl
        "
      >

        {/* Header */}
        <div
          className="
            flex
            items-center
            justify-between
            border-b
            border-slate-800
            px-6
            py-5
          "
        >

          <div>

            <h2 className="text-2xl font-bold text-white">
              Edit Profile
            </h2>

            <p className="mt-1 text-sm text-slate-400">
              Update your profile information and photo.
            </p>

          </div>

          <button
            type="button"
            onClick={onClose}
            className="
              flex
              h-10
              w-10
              items-center
              justify-center
              rounded-xl
              text-slate-400
              transition
              hover:bg-slate-800
              hover:text-white
            "
          >
            <FiX size={20} />
          </button>

        </div>

        {/* Content */}
        <div className="overflow-y-auto px-6 py-6">

          {/* Profile Photo */}
          <div
            className="
              rounded-2xl
              border
              border-slate-800
              bg-slate-950/40
              p-5
            "
          >

    <div className="flex flex-col items-center text-center">

    <div className="relative">

        {preview ? (
        <img
            src={preview}
            alt="Profile"
            className="
            h-32
            w-32
            rounded-full
            border-2
            border-slate-700
            object-cover
            shadow-xl
            "
        />
        ) : (
        <div className="h-32 w-32">
            <UserAvatar
            user={user}
            size="xl"
            />
        </div>
        )}

        {uploading && (
        <div
            className="
            absolute
            inset-0
            flex
            items-center
            justify-center
            rounded-full
            bg-black/70
            text-xs
            font-semibold
            text-white
            "
        >
            Uploading...
        </div>
        )}

    </div>

    <h3 className="mt-4 text-lg font-semibold text-white">
        Profile Photo
    </h3>

    <p className="mt-1 text-sm text-slate-400">
        JPG, PNG or WebP • Max 5 MB
    </p>

    <div className="mt-4 flex flex-wrap justify-center gap-3">

        <button
        type="button"
        disabled={uploading}
        onClick={() => fileInputRef.current?.click()}
        className="
            inline-flex
            items-center
            gap-2
            rounded-xl
            bg-blue-600
            px-5
            py-2.5
            text-sm
            font-semibold
            text-white
            transition
            hover:bg-blue-500
            disabled:cursor-not-allowed
            disabled:opacity-50
        "
        >
        <FiCamera />

        {uploading ? "Uploading..." : "Change Photo"}
        </button>

        <button
        type="button"
        onClick={handleRemovePhoto}
        disabled={!preview || uploading}
        className="
            inline-flex
            items-center
            gap-2
            rounded-xl
            border
            border-red-500/30
            px-5
            py-2.5
            text-sm
            font-semibold
            text-red-400
            transition
            hover:bg-red-500/10
            disabled:cursor-not-allowed
            disabled:opacity-40
        "
        >
        <FiTrash2 />

        Remove Photo
        </button>

    </div>

    <input
        ref={fileInputRef}
        type="file"
        accept="image/png,image/jpeg,image/webp"
        className="hidden"
        onChange={handlePhotoChange}
    />

    </div>

          </div>

          {/* Personal Information */}
          <div className="mt-6">

            <div className="mb-4">

              <h3 className="text-lg font-semibold text-white">
                Personal Information
              </h3>

              <p className="mt-1 text-sm text-slate-400">
                Manage your public developer identity.
              </p>

            </div>

            <div className="space-y-5">

              {/* First + Last */}
              <div className="grid gap-5 sm:grid-cols-2">

                <div>

                  <label className="mb-2 flex items-center gap-2 text-sm font-semibold text-slate-300">
                    <FiUser className="text-blue-500" />
                    First Name
                  </label>

                  <input
                    value={firstName}
                    onChange={(event) =>
                      setFirstName(event.target.value)
                    }
                    className="
                      w-full
                      rounded-xl
                      border
                      border-slate-700
                      bg-slate-950
                      px-4
                      py-3
                      text-white
                      outline-none
                      transition
                      focus:border-blue-500
                      focus:ring-2
                      focus:ring-blue-500/20
                    "
                  />

                </div>

                <div>

                  <label className="mb-2 flex items-center gap-2 text-sm font-semibold text-slate-300">
                    <FiUser className="text-blue-500" />
                    Last Name
                  </label>

                  <input
                    value={lastName}
                    onChange={(event) =>
                      setLastName(event.target.value)
                    }
                    className="
                      w-full
                      rounded-xl
                      border
                      border-slate-700
                      bg-slate-950
                      px-4
                      py-3
                      text-white
                      outline-none
                      transition
                      focus:border-blue-500
                      focus:ring-2
                      focus:ring-blue-500/20
                    "
                  />

                </div>

              </div>

              {/* Username */}
              <div>

                <label className="mb-2 flex items-center gap-2 text-sm font-semibold text-slate-300">
                  <FiAtSign className="text-blue-500" />
                  Username
                </label>

                <input
                  value={username}
                  onChange={(event) =>
                    setUsername(event.target.value)
                  }
                  className="
                    w-full
                    rounded-xl
                    border
                    border-slate-700
                    bg-slate-950
                    px-4
                    py-3
                    text-white
                    outline-none
                    transition
                    focus:border-blue-500
                    focus:ring-2
                    focus:ring-blue-500/20
                  "
                />

              </div>

              {/* Email */}
              <div>

                <label className="mb-2 flex items-center gap-2 text-sm font-semibold text-slate-300">
                  <FiMail className="text-blue-500" />
                  Email
                </label>

                <div className="relative">

                  <input
                    value={user.email}
                    disabled
                    className="
                      w-full
                      cursor-not-allowed
                      rounded-xl
                      border
                      border-slate-700
                      bg-slate-800/50
                      px-4
                      py-3
                      pr-11
                      text-slate-400
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

                <p className="mt-2 text-xs text-slate-500">
                  Email is managed by your authentication provider.
                </p>

              </div>

            </div>

          </div>

        </div>

        {/* Footer */}
        <div
          className="
            flex
            items-center
            justify-end
            gap-3
            border-t
            border-slate-800
            px-6
            py-4
          "
        >

          <button
            type="button"
            onClick={onClose}
            className="
              rounded-xl
              border
              border-slate-700
              px-5
              py-2.5
              font-semibold
              text-slate-300
              transition
              hover:bg-slate-800
              hover:text-white
            "
          >
            Cancel
          </button>

          <button
            type="button"
            onClick={handleSave}
            disabled={saving || uploading}
            className="
              inline-flex
              items-center
              gap-2
              rounded-xl
              bg-gradient-to-r
              from-blue-600
              to-indigo-600
              px-5
              py-2.5
              font-semibold
              text-white
              shadow-lg
              shadow-blue-500/20
              transition
              hover:-translate-y-0.5
              disabled:cursor-not-allowed
              disabled:opacity-50
            "
          >

            <FiSave />

            {saving ? "Saving..." : "Save Changes"}

          </button>

        </div>

      </div>

    </div>
  );
}