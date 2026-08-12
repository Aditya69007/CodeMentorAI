import { useEffect, useState } from "react";
import {
  FiAtSign,
  FiExternalLink,
  FiGlobe,
  FiUser,
} from "react-icons/fi";
import { SiLeetcode } from "react-icons/si";
import { FiGithub } from "react-icons/fi";
import { useAuth } from "../../hooks/useAuth";
import { getProfile, type UserProfile } from "../../services/userService";
import {
  getConnectedAccounts,
  type ConnectedAccountsResponse,
} from "../../services/connectedAccountsService";

export default function DeveloperIdentityCard() {
  const [profile, setProfile] = useState<UserProfile | null>(null);
  const [accounts, setAccounts] =
    useState<ConnectedAccountsResponse | null>(null);

    const { user } = useAuth();

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
        console.error("Failed to load developer identity", error);
      }
    }

    void load();
  }, []);

  if (!profile) return null;

  const currentUsername = user?.username ?? profile.username;

    const portfolioUrl = `/portfolio/${currentUsername}`;

  const githubConnected =
    Boolean(accounts?.githubConnected && accounts?.githubUsername);

  const leetcodeConnected =
    Boolean(accounts?.leetcodeConnected && accounts?.leetcodeUsername);

  return (
    <section className="app-surface app-border rounded-3xl p-6 sm:p-7">
      {/* Header */}
      <div className="mb-6 flex items-start gap-4">
        <div className="flex h-12 w-12 shrink-0 items-center justify-center rounded-2xl bg-blue-500/10">
          <FiUser className="text-xl text-blue-500" />
        </div>

        <div>
          <h2 className="text-xl font-bold">
            Developer Identity
          </h2>

          <p className="mt-1 text-sm app-text-secondary">
            Your public developer identity across CodeMentorAI.
          </p>
        </div>
      </div>

      {/* Identity */}
      <div className="rounded-2xl border app-border app-surface-secondary p-5">
        <div className="flex items-center gap-4">
          <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-blue-500/10">
            <FiAtSign className="text-xl text-blue-500" />
          </div>

          <div className="min-w-0">
            <p className="text-xs font-semibold uppercase tracking-wider app-text-muted">
              Username
            </p>

            <p className="mt-1 text-lg font-bold app-text-primary">
              @{currentUsername}
            </p>

            <p className="mt-1 text-sm app-text-secondary">
              {profile.firstName} {profile.lastName}
            </p>
          </div>
        </div>
      </div>

      {/* Developer Platforms */}
      <div className="mt-4 grid gap-3 sm:grid-cols-2">
        <IdentityItem
          icon={<FiGithub />}
          title="GitHub"
          value={
            githubConnected
              ? `@${accounts?.githubUsername}`
              : "Not connected"
          }
          connected={githubConnected}
          url={
            githubConnected
              ? `https://github.com/${accounts?.githubUsername}`
              : undefined
          }
        />

        <IdentityItem
          icon={<SiLeetcode />}
          title="LeetCode"
          value={
            leetcodeConnected
              ? `@${accounts?.leetcodeUsername}`
              : "Not connected"
          }
          connected={leetcodeConnected}
          url={
            leetcodeConnected
              ? `https://leetcode.com/u/${accounts?.leetcodeUsername}`
              : undefined
          }
        />
      </div>

      {/* Public Portfolio */}
      <div className="mt-4 rounded-2xl border border-blue-500/20 bg-blue-500/5 p-5">
        <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
          <div className="flex min-w-0 items-center gap-4">
            <div className="flex h-11 w-11 shrink-0 items-center justify-center rounded-xl bg-blue-500/10">
              <FiGlobe className="text-xl text-blue-500" />
            </div>

            <div className="min-w-0">
              <p className="text-xs font-semibold uppercase tracking-wider app-text-muted">
                Public Portfolio
              </p>

              <p className="mt-1 truncate font-semibold text-blue-500">
                /portfolio/@{currentUsername}
              </p>
            </div>
          </div>

          <a
            href={portfolioUrl}
            className="inline-flex shrink-0 items-center justify-center gap-2 rounded-xl border border-blue-500/30 px-4 py-2.5 text-sm font-semibold text-blue-500 transition hover:bg-blue-500/10"
          >
            View Portfolio
            <FiExternalLink />
          </a>
        </div>
      </div>
    </section>
  );
}

interface IdentityItemProps {
  icon: React.ReactNode;
  title: string;
  value: string;
  connected: boolean;
  url?: string;
}

function IdentityItem({
  icon,
  title,
  value,
  connected,
  url,
}: IdentityItemProps) {
  return (
    <div className="rounded-2xl border app-border app-surface-secondary p-4">
      <div className="flex items-center justify-between gap-3">
        <div className="flex min-w-0 items-center gap-3">
          <div className="flex h-10 w-10 shrink-0 items-center justify-center rounded-xl bg-slate-500/10 app-text-primary">
            {icon}
          </div>

          <div className="min-w-0">
            <p className="text-sm font-semibold">
              {title}
            </p>

            <p className="mt-0.5 truncate text-xs app-text-secondary">
              {value}
            </p>
          </div>
        </div>

        <span
          className={`shrink-0 rounded-full px-2.5 py-1 text-[11px] font-semibold ${
            connected
              ? "bg-emerald-500/10 text-emerald-400"
              : "bg-slate-500/10 app-text-muted"
          }`}
        >
          {connected ? "Connected" : "Not connected"}
        </span>
      </div>

      {url && (
        <a
          href={url}
          target="_blank"
          rel="noreferrer"
          className="mt-3 inline-flex items-center gap-1.5 text-xs font-medium text-blue-500 hover:text-blue-400"
        >
          Open profile
          <FiExternalLink />
        </a>
      )}
    </div>
  );
}