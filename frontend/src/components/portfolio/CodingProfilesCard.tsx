import {
  FiGithub,
  FiExternalLink,
  FiCheckCircle,
} from "react-icons/fi";

import { FaGoogle } from "react-icons/fa";
import { SiLeetcode } from "react-icons/si";

import { useEffect, useState } from "react";

import {
  getConnectedAccounts,
  type ConnectedAccountsResponse,
} from "../../services/connectedAccountsService";


export default function CodingProfilesCard() {

  const [accounts, setAccounts] =
    useState<ConnectedAccountsResponse | null>(null);

  useEffect(() => {

    const loadAccounts = async () => {

      try {

        const data = await getConnectedAccounts();

        setAccounts(data);

      } catch (error) {

        console.error(error);

      }

    };

    loadAccounts();

  }, []);

  const profiles = [

    {
      name: "GitHub",
      username: accounts?.githubConnected
        ? `@${accounts.githubUsername}`
        : "Not Connected",

      connected: accounts?.githubConnected ?? false,

      icon: FiGithub,

      color: "text-slate-200",

      url: accounts?.githubUsername
        ? `https://github.com/${accounts.githubUsername}`
        : null,
    },

    {
      name: "LeetCode",

      username: accounts?.leetcodeConnected
        ? `@${accounts.leetcodeUsername}`
        : "Not Connected",

      connected: accounts?.leetcodeConnected ?? false,

      icon: SiLeetcode,

      color: "text-orange-500",

      url: accounts?.leetcodeUsername
        ? `https://leetcode.com/u/${accounts.leetcodeUsername}/`
        : null,
    },

    {
      name: "Google",

      username: "Connected",

      connected: true,

      icon: FaGoogle,

      color: "text-red-500",

      url: null,
    },

  ];



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

                {profile.connected ? (
                  <span className="flex items-center gap-1 rounded-full bg-emerald-500/10 px-3 py-1 text-sm font-medium text-emerald-500">
                    <FiCheckCircle />
                    Connected
                  </span>
                ) : (
                  <span className="rounded-full bg-yellow-500/10 px-3 py-1 text-sm font-medium text-yellow-500">
                    Not Connected
                  </span>
                )}

            <button
              disabled={!profile.url}
              onClick={() => {

                if (profile.url) {

                  window.open(
                    profile.url,
                    "_blank"
                  );

                }

              }}
              className="rounded-lg border p-2 transition hover:border-blue-500 disabled:cursor-not-allowed disabled:opacity-40"
            >
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