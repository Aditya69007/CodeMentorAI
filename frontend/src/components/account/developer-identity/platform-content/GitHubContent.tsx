


import type { Dispatch, SetStateAction } from "react";

import type {
  GitHubProfileResponse,
} from "../../../../services/connectedAccountsService";

type Props = {
  profile: GitHubProfileResponse | null;
  githubUsername: string;
  setGithubUsername: Dispatch<SetStateAction<string>>;
  connectGitHub: () => Promise<void>;
  refreshGitHub: () => Promise<void>;
  saving: boolean;
  error: string;

  editingGithub: boolean;
  setEditingGithub: Dispatch<SetStateAction<boolean>>;
  saveGithubUsername: () => Promise<void>;

  showDisconnectDialog: boolean;
  setShowDisconnectDialog: Dispatch<SetStateAction<boolean>>;
  disconnectGitHub: () => Promise<void>;
};
export default function GitHubContent({
  profile,
  githubUsername,
  setGithubUsername,
  connectGitHub,
  refreshGitHub,
  saving,
  error,
  editingGithub,
  setEditingGithub,
  saveGithubUsername,
  showDisconnectDialog,
  setShowDisconnectDialog,
  disconnectGitHub,
}: Props) {

  /*
      Temporary state.

      Later these values will come from
      DeveloperIdentityCard through props.
  */


  if (!profile){

    return (

      <div className="mx-auto max-w-xl">

        <h3 className="text-2xl font-bold">

          Connect GitHub

        </h3>

        <p className="mt-2 app-text-secondary">

          Connect your GitHub account to unlock
          AI analysis, portfolio generation and
          developer insights.

        </p>

        <div className="mt-8">

          <label className="text-sm font-medium">

            GitHub Username

          </label>

            <input
            value={githubUsername}
            onChange={(e) => {

                setGithubUsername(e.target.value);

            }}
            placeholder="Aditya69007"
            autoComplete="off"
            className="mt-3 w-full rounded-xl border border-white/10 bg-transparent px-4 py-3 outline-none transition-all duration-300 focus:border-green-500"
            />

            <p className="mt-2 text-sm text-zinc-400">
            Enter your public GitHub username (not your email).
            </p>

            {error && (

                <p className="mt-3 rounded-lg border border-red-500/30 bg-red-500/10 px-4 py-3 text-sm text-red-400">

                    {error}

                </p>

            )}

        </div>

            <button
            onClick={connectGitHub}
            disabled={saving || githubUsername.trim() === ""}
            className="mt-8 flex h-12 min-w-[190px] items-center justify-center rounded-xl bg-green-600 px-6 font-semibold text-white transition-all duration-300 hover:bg-green-500 disabled:cursor-not-allowed disabled:opacity-60"
            >
            {saving ? (
                <>
                <svg
                    className="mr-2 h-5 w-5 animate-spin"
                    viewBox="0 0 24 24"
                    fill="none"
                >
                    <circle
                    cx="12"
                    cy="12"
                    r="10"
                    stroke="currentColor"
                    strokeWidth="4"
                    className="opacity-25"
                    />
                    <path
                    d="M22 12a10 10 0 00-10-10"
                    stroke="currentColor"
                    strokeWidth="4"
                    className="opacity-75"
                    />
                </svg>

                Connecting...
                </>
            ) : (
                "Connect GitHub"
            )}
            </button>

      </div>

    );

  }

    return (

    <div className="space-y-8">

        {/* Profile */}

        <div className="flex gap-6">

        <img
            src={profile.avatarUrl}
            alt="GitHub"
            className="h-24 w-24 rounded-full border-4 border-green-500/20"
        />

        <div className="flex-1">

            <div className="flex items-center gap-3">

            <h2 className="text-3xl font-bold">

                {profile.name}

            </h2>

            <span className="rounded-full bg-green-500/10 px-3 py-1 text-xs font-semibold text-green-400">

                Connected

            </span>

            </div>

            <p className="mt-2 text-zinc-400">

            @{profile.username}

            </p>

            <p className="mt-5 leading-7 text-zinc-300">

            {profile.bio}

            </p>

        </div>

        </div>

        {/* Stats */}

        <div className="grid grid-cols-3 gap-5">

        <div className="rounded-2xl border border-white/10 p-6 text-center">

            <p className="text-4xl font-bold">

            {profile.publicRepositories}

            </p>

            <p className="mt-2 app-text-secondary">

            Repositories

            </p>

        </div>

        <div className="rounded-2xl border border-white/10 p-6 text-center">

            <p className="text-4xl font-bold">

            {profile.followers}

            </p>

            <p className="mt-2 app-text-secondary">

            Followers

            </p>

        </div>

        <div className="rounded-2xl border border-white/10 p-6 text-center">

            <p className="text-4xl font-bold">

            {profile.following}

            </p>

            <p className="mt-2 app-text-secondary">

            Following

            </p>

        </div>

        </div>

        {/* Username */}

        <div>

        <label className="text-sm font-semibold">

            GitHub Username

        </label>

        <input
            value={githubUsername}
            onChange={(e) => setGithubUsername(e.target.value)}
            readOnly={!editingGithub}
            className={`mt-3 w-full rounded-xl border px-4 py-3 transition-all duration-300
            ${
                editingGithub
                    ? "border-green-500 bg-white/5"
                    : "border-white/10 bg-transparent"
            }`}
        />

        </div>

        {/* Buttons */}

        <div className="flex flex-wrap gap-4">

            {!editingGithub ? (

                <>

                    <button
                        onClick={refreshGitHub}
                        disabled={saving}
                        className="rounded-xl bg-green-600 px-6 py-3 font-semibold text-white disabled:opacity-60"
                    >
                        {saving ? "Refreshing..." : "Refresh"}
                    </button>

                    <button
                        onClick={() => setEditingGithub(true)}
                        className="rounded-xl border border-white/10 px-6 py-3"
                    >
                        Edit Username
                    </button>

                    <button
                        onClick={() => setShowDisconnectDialog(true)}
                        className="rounded-xl border border-red-500/40 px-6 py-3 text-red-400 transition hover:bg-red-500/10"
                    >
                        Disconnect
                    </button>

                    {showDisconnectDialog && (

                        <div className="mt-8 rounded-2xl border border-red-500/30 bg-red-500/5 p-6">

                            <h3 className="text-lg font-semibold text-red-400">

                                Disconnect GitHub?

                            </h3>

                            <p className="mt-2 text-sm text-zinc-400">

                                Your GitHub username will be removed from CodeMentorAI.
                                You can reconnect it anytime.

                            </p>

                            <div className="mt-6 flex gap-4">

                                <button
                                    onClick={() => setShowDisconnectDialog(false)}
                                    className="rounded-xl border border-white/10 px-5 py-3"
                                >
                                    Cancel
                                </button>

                                <button
                                    onClick={disconnectGitHub}
                                    className="rounded-xl bg-red-600 px-5 py-3 font-semibold text-white"
                                >
                                    Disconnect
                                </button>

                            </div>

                        </div>

                    )}
                </>

            ) : (

                <>

                    <button
                        onClick={saveGithubUsername}
                        disabled={saving}
                        className="rounded-xl bg-green-600 px-6 py-3 font-semibold text-white disabled:opacity-60"
                    >
                        {saving ? "Saving..." : "Save"}
                    </button>

                    <button
                        onClick={() => {

                            setGithubUsername(profile.username);

                            setEditingGithub(false);

                        }}
                        className="rounded-xl border border-white/10 px-6 py-3"
                    >
                        Cancel
                    </button>

                </>

            )}

        </div>

    </div>

    );

}