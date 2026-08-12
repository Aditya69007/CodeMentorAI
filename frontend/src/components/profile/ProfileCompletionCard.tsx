import { useEffect, useState } from "react";
import {
  FiCheckCircle,
  FiGithub,
  FiImage,
  FiLink,
  FiUser,
  FiCode,
} from "react-icons/fi";
import { SiLeetcode } from "react-icons/si";

import { getProfile, type UserProfile } from "../../services/userService";
import {
  getConnectedAccounts,
  type ConnectedAccountsResponse,
} from "../../services/connectedAccountsService";

interface CompletionItemProps {
  icon: React.ReactNode;
  label: string;
  completed: boolean;
}

function CompletionItem({
  icon,
  label,
  completed,
}: CompletionItemProps) {
  return (
    <div className="flex items-center gap-3">
      <div
        className={`flex h-9 w-9 shrink-0 items-center justify-center rounded-xl ${
          completed
            ? "bg-emerald-500/10 text-emerald-400"
            : "bg-slate-500/10 app-text-muted"
        }`}
      >
        {completed ? <FiCheckCircle size={17} /> : icon}
      </div>

      <span
        className={`text-sm font-medium ${
          completed ? "app-text-primary" : "app-text-secondary"
        }`}
      >
        {label}
      </span>

      <span className="ml-auto text-xs font-semibold">
        {completed ? (
          <span className="text-emerald-400">Complete</span>
        ) : (
          <span className="app-text-muted">Incomplete</span>
        )}
      </span>
    </div>
  );
}

export default function ProfileCompletionCard() {
  const [profile, setProfile] = useState<UserProfile | null>(null);
  const [accounts, setAccounts] =
    useState<ConnectedAccountsResponse | null>(null);

  useEffect(() => {
    async function load() {
      try {
        const [profileData, accountData] = await Promise.all([
          getProfile(),
          getConnectedAccounts(),
        ]);

        setProfile(profileData);
        setAccounts(accountData);
      } catch (error) {
        console.error(
          "Failed to load profile completion",
          error
        );
      }
    }

    void load();
  }, []);

  if (!profile) return null;

  const checks = [
    {
      label: "Personal information",
      completed: Boolean(
        profile.firstName &&
        profile.lastName &&
        profile.email
      ),
      icon: <FiUser size={17} />,
    },
    {
      label: "Username",
      completed: Boolean(profile.username),
      icon: <FiCode size={17} />,
    },
    {
      label: "Profile picture",
      completed: Boolean(profile.profilePicture),
      icon: <FiImage size={17} />,
    },
    {
      label: "GitHub connected",
      completed: Boolean(
        accounts?.githubConnected &&
        accounts?.githubUsername
      ),
      icon: <FiGithub size={17} />,
    },
    {
      label: "LeetCode connected",
      completed: Boolean(
        accounts?.leetcodeConnected &&
        accounts?.leetcodeUsername
      ),
      icon: <SiLeetcode size={17} />,
    },
    {
      label: "Public portfolio",
      completed: Boolean(profile.username),
      icon: <FiLink size={17} />,
    },
  ];

  const completedCount = checks.filter(
    (item) => item.completed
  ).length;

  const percentage = Math.round(
    (completedCount / checks.length) * 100
  );

  const allComplete = percentage === 100;

  return (
    <section className="app-surface app-border rounded-3xl p-6 sm:p-7">
      {/* Header */}
      <div className="flex flex-col gap-5 sm:flex-row sm:items-center sm:justify-between">
        <div>
          <h2 className="text-xl font-bold">
            Profile Completion
          </h2>

          <p className="mt-1 text-sm app-text-secondary">
            Complete your developer profile to build a stronger
            professional presence.
          </p>
        </div>

        <div className="flex items-center gap-3">
          <div className="relative h-14 w-14">
            <svg
              className="h-14 w-14 -rotate-90"
              viewBox="0 0 36 36"
            >
              <path
                d="M18 2.0845
                   a 15.9155 15.9155 0 0 1 0 31.831
                   a 15.9155 15.9155 0 0 1 0-31.831"
                fill="none"
                stroke="currentColor"
                strokeWidth="3"
                className="text-slate-700"
              />

              <path
                d="M18 2.0845
                   a 15.9155 15.9155 0 0 1 0 31.831
                   a 15.9155 15.9155 0 0 1 0-31.831"
                fill="none"
                stroke="currentColor"
                strokeWidth="3"
                strokeDasharray={`${percentage}, 100`}
                strokeLinecap="round"
                className="text-blue-500 transition-all duration-700"
              />
            </svg>

            <span className="absolute inset-0 flex items-center justify-center text-xs font-bold">
              {percentage}%
            </span>
          </div>
        </div>
      </div>

      {/* Progress bar */}
      <div className="mt-6">
        <div className="mb-2 flex items-center justify-between">
          <span className="text-xs font-semibold app-text-secondary">
            Profile readiness
          </span>

          <span className="text-xs font-semibold text-blue-400">
            {completedCount}/{checks.length}
          </span>
        </div>

        <div className="h-2 overflow-hidden rounded-full bg-slate-700/60">
          <div
            className="h-full rounded-full bg-gradient-to-r from-blue-500 to-indigo-500 transition-all duration-700"
            style={{
              width: `${percentage}%`,
            }}
          />
        </div>
      </div>

      {/* Checklist */}
      <div className="mt-6 grid gap-3 sm:grid-cols-2">
        {checks.map((item) => (
          <CompletionItem
            key={item.label}
            icon={item.icon}
            label={item.label}
            completed={item.completed}
          />
        ))}
      </div>

      {/* Status message */}
      <div
        className={`mt-6 rounded-2xl border p-4 ${
          allComplete
            ? "border-emerald-500/20 bg-emerald-500/5"
            : "border-blue-500/20 bg-blue-500/5"
        }`}
      >
        <div className="flex items-start gap-3">
          <FiCheckCircle
            className={`mt-0.5 shrink-0 ${
              allComplete
                ? "text-emerald-400"
                : "text-blue-400"
            }`}
          />

          <div>
            <p className="text-sm font-semibold">
              {allComplete
                ? "Your developer profile is complete."
                : "Complete your profile to improve your developer presence."}
            </p>

            {!allComplete && (
              <p className="mt-1 text-xs app-text-secondary">
                Connect your developer platforms and complete your
                identity details to finish your profile.
              </p>
            )}
          </div>
        </div>
      </div>
    </section>
  );
}