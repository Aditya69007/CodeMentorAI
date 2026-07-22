import { useEffect, useState } from "react";
import {
  FiGithub,
  FiLink,
  FiRefreshCw,
  FiExternalLink,
} from "react-icons/fi";
import { FaGoogle, FaLinkedin } from "react-icons/fa";
import { SiLeetcode } from "react-icons/si";

import {
  getConnectedAccounts,
  getGitHubProfile,
  updateConnectedAccounts,
  type ConnectedAccountsResponse,
  type GitHubProfileResponse,
} from "../../services/connectedAccountsService";


export default function ConnectedAccountsCard() {
  const [accounts, setAccounts] =
    useState<ConnectedAccountsResponse | null>(null);
  const [githubUsername, setGithubUsername] = useState("");
  const [leetcodeUsername, setLeetcodeUsername] = useState("");
  const [linkedinUrl, setLinkedinUrl] = useState("");
  const [saving, setSaving] = useState(false);

  const [githubProfile, setGithubProfile] =
    useState<GitHubProfileResponse | null>(null);

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadAccounts();
  }, []);

  async function loadAccounts() {
    try {
      setLoading(true);

      const connected =
        await getConnectedAccounts();

      setAccounts(connected);
      setGithubUsername(connected.githubUsername ?? "");
      setLeetcodeUsername(connected.leetcodeUsername ?? "");

      if (
        connected.githubConnected &&
        connected.githubUsername
      ) {
        const profile =
          await getGitHubProfile(
            connected.githubUsername
          );

        setGithubProfile(profile);
      }
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  }

    async function saveAccounts() {
    try {
      setSaving(true);

      await updateConnectedAccounts({
        githubUsername,
        leetcodeUsername,
      });

      await loadAccounts();

    } catch (error) {
      console.error(error);
    } finally {
      setSaving(false);
    }
  }

  return (
    <section className="app-surface app-border rounded-2xl p-6">

      <div className="flex items-center justify-between">

        <div>

          <div className="mb-3 flex h-12 w-12 items-center justify-center rounded-xl bg-green-500/10">

            <FiLink className="text-xl text-green-500" />

          </div>

          <h2 className="text-xl font-bold">

            Connected Accounts

          </h2>

          <p className="app-text-secondary mt-2">

            Connect external platforms to build your Developer Identity.

          </p>

        </div>

        <button
          onClick={loadAccounts}
          className="rounded-xl border px-4 py-2 hover:border-green-500"
        >
          <span className="flex items-center gap-2">

            <FiRefreshCw />

            Refresh

          </span>
        </button>

      </div>

      <div className="mt-8 space-y-6">

        {/* Google */}

        <div className="flex items-center justify-between">

          <div className="flex items-center gap-3">

            <FaGoogle className="text-xl text-red-500" />

            <span>Google</span>

          </div>

          <span className="font-medium text-emerald-500">

            Connected

          </span>

        </div>

        {/* GitHub */}

        <div className="rounded-xl border p-4">

          <div className="flex items-center justify-between">

            <div className="flex items-center gap-3">

              <FiGithub className="text-2xl" />

              <div>

                <h3 className="font-semibold mb-3">
                  GitHub
                </h3>

                <label className="text-xs app-text-secondary">
                  GitHub Username
                </label>

                <input
                  type="text"
                  value={githubUsername}
                  onChange={(e) => setGithubUsername(e.target.value)}
                  placeholder="Enter GitHub username"
                  className="mt-2 w-full rounded-lg border bg-transparent px-3 py-2"
                />

                <button
                  onClick={saveAccounts}
                  disabled={saving}
                  className="mt-3 rounded-lg bg-green-600 px-4 py-2 text-white hover:bg-green-700 disabled:opacity-50"
                >
                  {saving ? "Saving..." : "Save"}
                </button>

                {githubProfile && (
                  <>
                    <p className="mt-4 font-medium">
                      {githubProfile.name}
                    </p>

                    <p className="text-sm app-text-secondary">
                      @{githubProfile.username}
                    </p>

                    <p className="mt-2 text-xs">
                      Repositories: {githubProfile.publicRepositories}
                    </p>

                    <p className="text-xs">
                      Followers: {githubProfile.followers}
                    </p>
                  </>
                )}

                {loading ? (

                  <p className="text-sm app-text-secondary">

                    Loading...

                  </p>

                ) : accounts?.githubConnected ? (

                  <>

                    <p className="text-sm app-text-secondary">

                      @{githubProfile?.username}

                    </p>

                    <p className="mt-2 text-xs">

                      Repositories :
                      {" "}
                      {githubProfile?.publicRepositories}

                    </p>

                    <p className="text-xs">

                      Followers :
                      {" "}
                      {githubProfile?.followers}

                    </p>

                  </>

                ) : (

                  <p className="text-sm text-yellow-500">

                    Not Connected

                  </p>

                )}

              </div>

            </div>

            {githubProfile && (

              <a
                href={githubProfile.profileUrl}
                target="_blank"
                rel="noreferrer"
              >

                <FiExternalLink />

              </a>

            )}

          </div>

        </div>

        {/* LeetCode */}

        <div className="flex items-center justify-between">

          <div className="flex items-center gap-3">

            <SiLeetcode className="text-xl text-orange-500" />

            <span>LeetCode</span>

          </div>

          <span>

            {accounts?.leetcodeConnected
              ? "Connected"
              : "Not Connected"}

          </span>

        </div>

        {/* LinkedIn */}

        <div className="flex items-center justify-between">

          <div className="flex items-center gap-3">

            <FaLinkedin className="text-xl text-blue-500" />

            <span>LinkedIn</span>

          </div>

          <span className="text-yellow-500">

            Coming Soon

          </span>

        </div>

      </div>

    </section>
  );
}