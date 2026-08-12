import { useEffect, useState } from "react";
import {
  FiUser,
  FiLink,
  FiShield,
  FiCheckCircle,
} from "react-icons/fi";

import {
  getProfile,
  type UserProfile,
} from "../../services/userService";

function StatusItem({
  icon,
  title,
  value,
  subtitle,
}: {
  icon: React.ReactNode;
  title: string;
  value: string;
  subtitle: string;
}) {
  return (

    <div
      className="
        group
        flex
        items-center
        gap-4
        rounded-2xl
        border
        app-border
        app-surface-secondary
        px-5
        py-4
        transition-all
        duration-200
        hover:border-blue-500/30
        hover:bg-slate-900/40
      "
    >
      <div
        className="
          flex
          h-11
          w-11
          shrink-0
          items-center
          justify-center
          rounded-xl
          bg-blue-500/10
          text-blue-400
          transition
          group-hover:bg-blue-500/15
        "
      >
        {icon}
      </div>

      <div className="min-w-0">
        <p className="text-[11px] font-semibold uppercase tracking-wider app-text-muted">
          {title}
        </p>

        <p className="mt-0.5 truncate text-base font-semibold app-text-primary">
          {value}
        </p>

        <p className="mt-0.5 text-xs app-text-muted">
          {subtitle}
        </p>
      </div>
    </div>
  );
}

export default function AccountStatusCard() {
  const [profile, setProfile] = useState<UserProfile | null>(null);

  useEffect(() => {
    async function load() {
      try {
        const data = await getProfile();
        setProfile(data);
      } catch (error) {
        console.error("Failed to load account status", error);
      }
    }

    void load();
  }, []);

  if (!profile) return null;

  const connected =
    Number(Boolean(profile.githubUsername)) +
    Number(Boolean(profile.leetcodeUsername)) +
    1;

  return (
    <section className="app-surface app-border rounded-3xl p-6 sm:p-7">

      {/* Header */}
      <div className="mb-5 flex items-end justify-between gap-4">
        <div>
          <h2 className="text-xl font-bold">
            Account Overview
          </h2>

          <p className="mt-1 text-sm app-text-secondary">
            Your CodeMentorAI account status at a glance.
          </p>
        </div>

        <span
          className="
            hidden
            rounded-full
            border
            border-emerald-500/20
            bg-emerald-500/10
            px-3
            py-1
            text-xs
            font-semibold
            text-emerald-400
            sm:inline-flex
            sm:items-center
            sm:gap-1.5
          "
        >
          <span className="h-1.5 w-1.5 rounded-full bg-emerald-400" />
          Account Active
        </span>
      </div>

      {/* Status */}
      <div className="grid gap-3 sm:grid-cols-2 xl:grid-cols-4">

        <StatusItem
          icon={<FiUser size={19} />}
          title="Profile"
          value="Complete"
          subtitle="Personal information"
        />

        <StatusItem
          icon={<FiLink size={19} />}
          title="Connected"
          value={`${connected} Platforms`}
          subtitle="Developer accounts"
        />

        <StatusItem
          icon={<FiShield size={19} />}
          title="Provider"
          value={profile.provider}
          subtitle="Authentication"
        />

        <StatusItem
          icon={<FiCheckCircle size={19} />}
          title="Status"
          value="Active"
          subtitle="Account verified"
        />

      </div>

    </section>
  );
}