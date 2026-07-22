import { FiMail, FiShield, FiUser } from "react-icons/fi";
import type { AuthUser } from "../../types/auth";
import UserAvatar from "./UserAvatar";

interface UserHeroProps {
  user: AuthUser;
  title: string;
  badges: string[];
  actions?: React.ReactNode;
}

export default function UserHero({
  user,
  title,
  badges,
  actions,
}: UserHeroProps) {


const fullName =
`${user.firstName} ${user.lastName}`;

  return (

    <section className="app-surface app-border rounded-3xl p-8">

      <div className="flex flex-col gap-8 lg:flex-row lg:items-center lg:justify-between">

        <div className="flex items-center gap-6">

            <UserAvatar
                user={user}
                size="xl"
            />

          <div>

            <h1 className="text-4xl font-bold">

              {fullName}

            </h1>

            <p className="app-text-secondary mt-1 text-lg">

              {title}

            </p>

            <div className="mt-4 space-y-2">

              <div className="flex items-center gap-2">

                <FiMail className="text-blue-500" />

                {user.email}

              </div>

              <div className="flex items-center gap-2">

                <FiUser className="text-blue-500" />

                User ID : {user.userId}

              </div>

              <div className="flex items-center gap-2">

                <FiShield className="text-blue-500" />

                {user.role}

              </div>

            </div>

            <div className="mt-5 flex flex-wrap gap-2">

            {badges
            .filter(Boolean)
            .map((badge, index) => (
                <span
                key={`${badge}-${index}`}
                className="rounded-full bg-blue-500/10 px-4 py-2 text-sm font-semibold text-blue-500"
                >
                {badge}
                </span>
            ))}

            </div>

          </div>

        </div>

        {actions && (

          <div className="flex flex-wrap gap-3">

            {actions}

          </div>

        )}

      </div>

    </section>

  );

}