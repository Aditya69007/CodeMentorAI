import {
  FiGithub,
  FiExternalLink,
  FiCheckCircle,
} from "react-icons/fi";
import { FaLinkedin, FaGoogle } from "react-icons/fa";
import { SiLeetcode } from "react-icons/si";

interface CodingProfile {
  name: string;
  username: string;
  status: string;
  icon: React.ElementType;
  color: string;
}

const profiles: CodingProfile[] = [
  {
    name: "GitHub",
    username: "@aditya69007",
    status: "Coming Soon",
    icon: FiGithub,
    color: "text-slate-200",
  },
  {
    name: "LeetCode",
    username: "@your_username",
    status: "Coming Soon",
    icon: SiLeetcode,
    color: "text-orange-500",
  },
  {
    name: "LinkedIn",
    username: "@your_profile",
    status: "Coming Soon",
    icon: FaLinkedin,
    color: "text-blue-500",
  },
  {
    name: "Google",
    username: "Connected",
    status: "Connected",
    icon: FaGoogle,
    color: "text-red-500",
  },
];

export default function CodingProfilesCard() {
  return (
    <section className="app-surface app-border rounded-2xl p-6">

      <div className="mb-6">

        <h2 className="text-xl font-bold">
          Coding Profiles
        </h2>

        <p className="app-text-secondary mt-2">
          Connect and showcase your developer profiles.
        </p>

      </div>

      <div className="space-y-4">

        {profiles.map((profile) => {
          const Icon = profile.icon;

          return (
            <div
              key={profile.name}
              className="flex items-center justify-between rounded-xl border border-slate-700/40 p-4 transition hover:border-blue-500"
            >
              <div className="flex items-center gap-4">

                <div className={`text-2xl ${profile.color}`}>
                  <Icon />
                </div>

                <div>

                  <h3 className="font-semibold">
                    {profile.name}
                  </h3>

                  <p className="app-text-secondary text-sm">
                    {profile.username}
                  </p>

                </div>

              </div>

              <div className="flex items-center gap-3">

                {profile.status === "Connected" ? (
                  <span className="flex items-center gap-1 rounded-full bg-emerald-500/10 px-3 py-1 text-sm font-medium text-emerald-500">
                    <FiCheckCircle />
                    Connected
                  </span>
                ) : (
                  <span className="rounded-full bg-yellow-500/10 px-3 py-1 text-sm font-medium text-yellow-500">
                    Coming Soon
                  </span>
                )}

                <button className="rounded-lg border p-2 transition hover:border-blue-500">
                  <FiExternalLink />
                </button>

              </div>

            </div>
          );
        })}

      </div>

    </section>
  );
}