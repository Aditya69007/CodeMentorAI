import { useEffect, useState } from "react";
import { FiExternalLink, FiGithub } from "react-icons/fi";
import { FaGoogle } from "react-icons/fa";
import { SiLeetcode } from "react-icons/si";

import {
  getConnectedAccounts,
  getGitHubProfile,
  type ConnectedAccountsResponse,
  type GitHubProfileResponse,
} from "../../services/connectedAccountsService";

function AccountRow({
  icon,
  title,
  username,
  connected,
  url,
}: {
  icon: React.ReactNode;
  title: string;
  username: string;
  connected: boolean;
  url?: string;
}) {
  return (
    <div className="flex items-center justify-between gap-4 rounded-2xl border app-border app-surface-secondary px-4 py-4 transition hover:border-blue-500/40">
      <div className="flex min-w-0 items-center gap-4">
        <div className="flex h-11 w-11 shrink-0 items-center justify-center rounded-xl app-surface-secondary">
          {icon}
        </div>

        <div className="min-w-0">
          <p className="font-semibold app-text-primary">
            {title}
          </p>

          <p className="mt-0.5 truncate text-sm app-text-secondary">
            {username}
          </p>
        </div>
      </div>

      <div className="flex shrink-0 items-center gap-3">
        <span
          className={`rounded-full px-3 py-1 text-xs font-semibold ${
            connected
              ? "bg-emerald-500/10 text-emerald-400"
              : "bg-slate-700/50 text-slate-400"
          }`}
        >
          {connected ? "● Connected" : "Not Connected"}
        </span>

        {url && (
          <a
            href={url}
            target="_blank"
            rel="noreferrer"
            aria-label={`Open ${title} profile`}
            className="flex h-9 w-9 items-center justify-center rounded-lg border border-slate-700 text-slate-400 transition hover:border-blue-500 hover:text-blue-400"
          >
            <FiExternalLink size={16} />
          </a>
        )}
      </div>
    </div>
  );
}

export default function ConnectedAccountsSection() {
  const [accounts, setAccounts] =
    useState<ConnectedAccountsResponse | null>(null);

  const [githubProfile, setGithubProfile] =
    useState<GitHubProfileResponse | null>(null);

  useEffect(() => {
    async function loadAccounts() {
      try {
        const connected = await getConnectedAccounts();

        setAccounts(connected);

        if (
          connected.githubConnected &&
          connected.githubUsername
        ) {
          const profile = await getGitHubProfile(
            connected.githubUsername
          );

          setGithubProfile(profile);
        }
      } catch (error) {
        console.error(
          "Failed to load connected accounts",
          error
        );
      }
    }

    void loadAccounts();
  }, []);

  return (
    <section className="app-surface app-border rounded-3xl p-6">
      <div className="mb-5">
        <h2 className="text-xl font-bold">
          Connected Accounts
        </h2>

        <p className="mt-1 text-sm app-text-secondary">
          Manage the developer platforms connected to your account.
        </p>
      </div>

      <div className="space-y-3">
        <AccountRow
          icon={
            <FiGithub className="text-xl app-text-primary" />
          }
          title="GitHub"
          username={
            accounts?.githubUsername
              ? `@${accounts.githubUsername}`
              : "Not connected"
          }
          connected={Boolean(
            accounts?.githubConnected
          )}
          url={githubProfile?.profileUrl}
        />

        <AccountRow
          icon={
            <SiLeetcode className="text-xl text-orange-500" />
          }
          title="LeetCode"
          username={
            accounts?.leetcodeUsername
              ? `@${accounts.leetcodeUsername}`
              : "Not connected"
          }
          connected={Boolean(
            accounts?.leetcodeConnected
          )}
          url={
            accounts?.leetcodeUsername
              ? `https://leetcode.com/u/${accounts.leetcodeUsername}`
              : undefined
          }
        />

        <AccountRow
          icon={
            <FaGoogle className="text-xl text-red-500" />
          }
          title="Google"
          username="Primary sign-in"
          connected={true}
        />
      </div>
    </section>
  );
}