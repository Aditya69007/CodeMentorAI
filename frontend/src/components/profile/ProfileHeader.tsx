import { FiEdit2, FiMail } from "react-icons/fi";
import { useAuth } from "../../hooks/useAuth";
import UserAvatar from "../common/UserAvatar";

export default function ProfileHeader() {

  const { user } = useAuth();

  if (!user) return null;

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

            <p className="mt-2 text-lg app-text-secondary">
              AI Developer
            </p>

            <div className="mt-4 flex items-center gap-2">

              <FiMail className="text-blue-500" />

              <span>{user.email}</span>

            </div>

            <div className="mt-5">

              <span className="rounded-full bg-blue-500/10 px-4 py-2 text-sm font-semibold text-blue-400">

                {user.role}

              </span>

            </div>

          </div>

        </div>

        <button
        onClick={() => {
            document
              .getElementById("personal-information")
              ?.scrollIntoView({
                behavior: "smooth",
                block: "start",
              });

          }}
          className="flex items-center gap-2 rounded-xl bg-blue-600 px-6 py-3 font-semibold text-white transition hover:bg-blue-700"
        >

          <FiEdit2 />

          Edit Profile

        </button>

      </div>

    </section>

  );

}