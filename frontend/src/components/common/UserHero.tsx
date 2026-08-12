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
  actions,
}: UserHeroProps) {


const fullName =
`${user.firstName} ${user.lastName}`;

  return (

    <section className="app-surface app-border rounded-3xl p-8">

      <div className="grid grid-cols-[1fr_430px] gap-12">

        <div className="flex flex-1 items-center gap-8">

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

              <div className="flex items-center space-between gap-2 ">
                <FiShield className="text-blue-500" />

                <span>{user.role}</span>

                {user.username && (
                  <span className="text-sm font-medium text-blue-400/80">
                    @{user.username}
                  </span>
                )}
              </div>

            </div>


          </div>

        </div>

      <div className="flex flex-col gap-6">

        {actions}

      </div>

      </div>

    </section>

  );

}