import { FiEdit2 } from "react-icons/fi";
import UserHero from "../common/UserHero";
import type { AuthUser } from "../../types/auth";

interface ProfileHeroProps {
  user: AuthUser;
  onEdit: () => void;
}

export default function ProfileHero({
  user,
  onEdit,
}: ProfileHeroProps) {

  return (

    <UserHero
      user={user}
      title="AI Developer"
      badges={[
        user.role,
        "ACTIVE",
      ]}
        actions={
          <div className="flex flex-wrap gap-3">

            <button
              onClick={onEdit}
              className="rounded-xl bg-blue-600 px-6 py-3 font-medium text-white hover:bg-blue-700"
            >
              <span className="flex items-center gap-2">
                <FiEdit2 />
                Edit Profile
              </span>
            </button>

            <button
              onClick={() => window.location.href = "/portfolio"}
              className="rounded-xl border border-blue-500 px-6 py-3 font-medium text-blue-500 hover:bg-blue-500/10"
            >
              View Portfolio
            </button>

          </div>
        }
    />

  );

}