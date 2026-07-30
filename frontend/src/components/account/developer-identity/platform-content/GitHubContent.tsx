


import type { Dispatch, SetStateAction } from "react";
import type { GitHubDashboard } from "../../../../types/github";

type Props = {
  dashboard: GitHubDashboard | null;
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
  dashboard,
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


  if (!dashboard){

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
            placeholder="Enter your GitHub username"
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

    const profile = dashboard.profile;

    const statistics = dashboard.statistics;

    const analytics = dashboard.analytics;

    const repositories = dashboard.topRepositories;


    return (

    <div className="space-y-8">

        {/* Header */}

        <div className="flex items-start gap-6 rounded-2xl border border-white/10 bg-white/[0.02] p-6">

            <img
                src={profile.avatarUrl}
                alt={profile.username}
                className="h-24 w-24 rounded-full border-4 border-green-500/20 object-cover"
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

                <p className="mt-4 leading-7 text-zinc-300">

                    {profile.bio || "No bio available."}

                </p>

                <div className="mt-6 flex flex-wrap gap-6 text-sm app-text-secondary">

                    <span>

                        📍 {profile.location || "Not specified"}

                    </span>

                    <span>

                        🏢 {profile.company || "Independent"}

                    </span>

                    <span>

                        🌐 {profile.blog || "No website"}

                    </span>

                    <span>

                        📅 GitHub since {statistics.accountAgeYears} years

                    </span>

                </div>

            </div>

        </div>

        {/* Statistics */}

        <div className="grid grid-cols-2 gap-4">

            <div className="rounded-xl border border-white/10 p-4">

                <p className="text-sm app-text-secondary">

                    Developer Score

                </p>

                <h2 className="mt-2 text-3xl font-bold text-green-400">

                    {statistics.developerScore.toFixed(1)}

                </h2>

            </div>

            <div className="rounded-xl border border-white/10 p-4">

                <p className="text-sm app-text-secondary">

                    Repositories

                </p>

                <h2 className="mt-2 text-3xl font-bold text-blue-400">

                    {statistics.repositories}

                </h2>

            </div>

            <div className="rounded-xl border border-white/10 p-4">

                <p className="text-sm app-text-secondary">

                    Followers

                </p>

                <h2 className="mt-2 text-3xl font-bold">

                    {statistics.followers}

                </h2>

            </div>

            <div className="rounded-xl border border-white/10 p-4">

                <p className="text-sm app-text-secondary">

                    Following

                </p>

                <h2 className="mt-2 text-3xl font-bold">

                    {statistics.following}

                </h2>

            </div>

        </div>

        {/* Repository Analytics */}

        <div className="grid grid-cols-2 gap-4">

            <div className="rounded-xl border border-green-500/20 bg-green-500/5 p-5">

                <p className="text-sm app-text-secondary">

                    Repository Score

                </p>

                <h2 className="mt-2 text-4xl font-bold text-green-400">

                    {analytics.repositoryScore.toFixed(0)}

                </h2>

            </div>

            <div className="rounded-xl border border-orange-500/20 bg-orange-500/5 p-5">

                <p className="text-sm app-text-secondary">

                    Language Diversity

                </p>

                <h2 className="mt-2 text-4xl font-bold text-orange-400">

                    {analytics.languageDiversityScore.toFixed(0)}

                </h2>

            </div>

            <div className="rounded-xl border border-cyan-500/20 bg-cyan-500/5 p-5">

                <p className="text-sm app-text-secondary">

                    Public Gists

                </p>

                <h2 className="mt-2 text-4xl font-bold text-cyan-400">

                    {statistics.publicGists}

                </h2>

            </div>

            <div className="rounded-xl border border-purple-500/20 bg-purple-500/5 p-5">

                <p className="text-sm app-text-secondary">

                    Account Age

                </p>

                <h2 className="mt-2 text-4xl font-bold text-purple-400">

                    {statistics.accountAgeYears} Years

                </h2>

            </div>

        </div>

        {/* Top Technologies */}

        <div className="rounded-xl border border-emerald-500/20 bg-emerald-500/5 p-5">

            <h3 className="text-lg font-semibold text-emerald-400">

                💻 Top Technologies

            </h3>

            <div className="mt-4 flex flex-wrap gap-3">

                {analytics.strongestTechnologies.map((technology) => (

                    <span
                        key={technology}
                        className="rounded-full bg-emerald-500/15 px-4 py-2 text-sm font-medium text-emerald-300"
                    >

                        {technology}

                    </span>

                ))}

            </div>

        </div>

        {/* Recommended Technologies */}

        <div className="rounded-xl border border-orange-500/20 bg-orange-500/5 p-5">

            <h3 className="text-lg font-semibold text-orange-400">

                🚀 Recommended Technologies

            </h3>

            <div className="mt-4 flex flex-wrap gap-3">

                {analytics.recommendedTechnologies.map((technology) => (

                    <span
                        key={technology}
                        className="rounded-full bg-orange-500/15 px-4 py-2 text-sm font-medium text-orange-300"
                    >

                        {technology}

                    </span>

                ))}

            </div>

        </div>

        {/* Top Repositories */}

        <div className="rounded-xl border border-white/10 p-5">

            <h3 className="text-lg font-semibold">

                📦 Top Repositories

            </h3>

            <div className="mt-4 space-y-3">

                {repositories.map((repository) => (

                    <div
                        key={repository.name}
                        className="flex items-center justify-between rounded-lg border border-white/5 bg-white/[0.02] p-4"
                    >

                        <div className="flex-1">

                            <h4 className="font-semibold">

                                {repository.name}

                            </h4>

                            <p className="mt-1 text-sm app-text-secondary">

                                {repository.description || "No description available."}

                            </p>

                            <div className="mt-3 flex flex-wrap gap-4 text-xs app-text-secondary">

                                <span>

                                    💻 {repository.language || "Unknown"}

                                </span>

                                <span>

                                    ⭐ {repository.stars}

                                </span>

                                <span>

                                    🍴 {repository.forks}

                                </span>

                            </div>

                        </div>

                        <a
                            href={repository.repositoryUrl}
                            target="_blank"
                            rel="noreferrer"
                            className="rounded-lg border border-green-500/30 px-4 py-2 text-sm font-medium text-green-400 transition hover:bg-green-500/10"
                        >

                            Open →

                        </a>

                    </div>

                ))}

            </div>

        </div>

        {/* AI Strengths */}

        <div className="rounded-xl border border-emerald-500/20 bg-emerald-500/5 p-5">

            <h3 className="text-lg font-semibold text-emerald-400">

                💪 AI Strengths

            </h3>

            <div className="mt-4 space-y-3">

                {analytics.strengths.map((strength) => (

                    <div
                        key={strength}
                        className="flex items-start gap-3 rounded-lg bg-emerald-500/10 p-3"
                    >

                        <span className="text-emerald-400">

                            ✔

                        </span>

                        <p className="text-sm">

                            {strength}

                        </p>

                    </div>

                ))}

            </div>

        </div>

        {/* AI Improvements */}

        <div className="rounded-xl border border-orange-500/20 bg-orange-500/5 p-5">

            <h3 className="text-lg font-semibold text-orange-400">

                🎯 AI Improvements

            </h3>

            <div className="mt-4 space-y-3">

                {analytics.improvements.map((item) => (

                    <div
                        key={item}
                        className="flex items-start gap-3 rounded-lg bg-orange-500/10 p-3"
                    >

                        <span className="text-orange-400">

                            ⚠

                        </span>

                        <p className="text-sm">

                            {item}

                        </p>

                    </div>

                ))}

            </div>

        </div>

        {/* AI Insights */}

        <div className="rounded-xl border border-cyan-500/20 bg-cyan-500/5 p-5">

            <h3 className="text-lg font-semibold text-cyan-400">

                🤖 AI GitHub Insights

            </h3>

            <div className="mt-4 space-y-4">

                {analytics.insights.map((insight) => (

                    <div
                        key={insight}
                        className="rounded-lg bg-cyan-500/10 p-4"
                    >

                        <p className="leading-7">

                            {insight}

                        </p>

                    </div>

                ))}

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