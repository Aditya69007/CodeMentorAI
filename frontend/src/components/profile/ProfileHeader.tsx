import { useEffect, useState } from "react";
import { FiEdit2, FiLock, FiMail } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import { getProfile } from "../../services/userService";
import { useAuth } from "../../hooks/useAuth";
import UserAvatar from "../common/UserAvatar";
import EditProfileModal from "./EditProfileModal";

export default function ProfileHeader() {
  const { user, refreshUser } = useAuth();

  const navigate = useNavigate();

  const [editOpen, setEditOpen] = useState(false);

  const [memberSince, setMemberSince] = useState("—");
  const [problemsSolved, setProblemsSolved] = useState<number | null>(null);

  useEffect(() => {
    const loadProfile = async () => {
      try {
        const profile = await getProfile();

        if (profile.createdAt) {
          setMemberSince(
            new Date(profile.createdAt).toLocaleDateString(
              "en-US",
              {
                month: "short",
                year: "numeric",
              }
            )
          );
        }

        setProblemsSolved(profile.problemsSolved ?? null);

      } catch (error) {
        console.error("Failed to load profile", error);
      }
    };

    void loadProfile();
  }, []);

  if (!user) {
    return null;
  }


  const fullName =
    `${user.firstName} ${user.lastName}`;


  const username =
  user.username
  ? `@${user.username}`
  : "—";


  return (
    <>
      <section
        className="
          app-surface
          app-border
          overflow-hidden
          rounded-3xl
          p-6
          sm:p-8
        "
      >
        <div
          className="
            flex
            flex-col
            gap-8
            lg:flex-row
            lg:items-center
            lg:justify-between
          "
        >

          {/* Profile Identity */}
          <div className="flex items-center gap-6">

            <div className="shrink-0">
              <UserAvatar
                user={user}
                size="xl"
              />
            </div>

            <div className="min-w-0">

              <h1 className="text-3xl font-bold sm:text-4xl">
                {fullName}
              </h1>

              <p className="mt-2 text-lg app-text-secondary">
                AI Developer
              </p>

              <div className="mt-4 flex items-center gap-2">

                <FiMail className="shrink-0 text-blue-500" />

                <span className="truncate">
                  {user.email}
                </span>

              </div>

              <div className="mt-5">

                <span
                  className="
                    inline-flex
                    rounded-full
                    bg-blue-500/10
                    px-4
                    py-2
                    text-sm
                    font-semibold
                    text-blue-400
                  "
                >
                  {user.role}
                </span>

              </div>

            </div>

          </div>

        <div className="mt-6 flex flex-wrap items-center gap-x-8 gap-y-4">

          <div>
            <p className="text-xs font-medium uppercase tracking-wider app-text-secondary">
              Username
            </p>

            <p className="mt-1 font-semibold">
              {username}
            </p>
          </div>

          <div className="hidden h-10 w-px bg-slate-700 sm:block" />

          <div>
            <p className="text-xs font-medium uppercase tracking-wider app-text-secondary">
              Member Since
            </p>

            <p className="mt-1 font-semibold">
              {memberSince}
            </p>
          </div>

          <div className="hidden h-10 w-px bg-slate-700 sm:block" />

          <div>
            <p className="text-xs font-medium uppercase tracking-wider app-text-secondary">
              Problems Solved
            </p>

            <p className="mt-1 font-semibold">
              {problemsSolved ?? "—"}
            </p>
          </div>

        </div>

          {/* Actions */}
          <div
            className="
              flex
              w-full
              flex-col
              gap-3
              sm:flex-row
              lg:w-auto
              lg:flex-col
            "
          >

            <button
              type="button"
              onClick={() => setEditOpen(true)}
              className="
                inline-flex
                w-full
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
                hover:from-blue-500
                hover:to-indigo-500
                lg:min-w-[190px]
              "
            >
              <FiEdit2 />

              Edit Profile
            </button>

            <button
              type="button"
              onClick={() => navigate("/account/settings?open=password")}
              className="
                inline-flex
                w-full
                items-center
                justify-center
                gap-2
                rounded-xl
                border
                border-slate-700
                px-6
                py-3
                font-semibold
                text-slate-300
                transition
                hover:border-slate-600
                hover:bg-slate-800
                hover:text-white
                lg:min-w-[190px]
              "
            >
              <FiLock />

              Change Password
            </button>

          </div>

        </div>
      </section>

      {/* Edit Profile Modal */}
      {editOpen && (
        <EditProfileModal
          user={user}
          onClose={() => setEditOpen(false)}
          onSaved={refreshUser}
        />
      )}
    </>
  );
}