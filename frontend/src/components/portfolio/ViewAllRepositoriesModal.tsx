import { FiGithub, FiX } from "react-icons/fi";
import type { GitHubRepository } from "../../types/github";

interface Props {
  open: boolean;
  onClose: () => void;
  repositories: GitHubRepository[];
  featuredRepositories: GitHubRepository[];
}

export default function ViewAllRepositoriesModal({
  open,
  onClose,
  repositories,
  featuredRepositories,
}: Props) {
  const featuredNames = new Set(
    featuredRepositories.map((repo) => repo.name)
  );

  const remainingRepositories = repositories.filter(
    (repo) => !featuredNames.has(repo.name)
  );

  if (!open) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/70 backdrop-blur-sm">
      <div className="app-surface app-border flex h-[90vh] w-full max-w-6xl flex-col overflow-hidden rounded-3xl">

        {/* Header */}

        <div className="flex items-center justify-between border-b border-slate-700/40 px-8 py-7">

          <div>

            <h2 className="text-3xl font-bold">
              All GitHub Repositories
            </h2>

            <p className="mt-2 app-text-secondary">
              Every public repository connected to your GitHub profile.
            </p>

          </div>

          <button
            onClick={onClose}
            className="rounded-xl border border-slate-700 p-3 transition hover:border-blue-500 hover:bg-slate-800"
          >
            <FiX className="text-2xl" />
          </button>

        </div>

        {/* Repository Grid */}

        <div className="flex-1 overflow-y-auto px-8 py-8">

          <div className="grid gap-6 md:grid-cols-2">

            {remainingRepositories.map((repo) => (

              <div
                key={repo.name}
                className="rounded-2xl border border-slate-700/40 bg-slate-900/40 p-6 transition hover:border-blue-500"
              >

                <div className="flex items-start justify-between">

                  <div>

                    <h3 className="text-xl font-semibold">
                      {repo.name}
                    </h3>

                    <p className="mt-3 text-sm app-text-secondary">

                      {repo.description ??
                        `A ${repo.language ?? "software"} project hosted on GitHub.`}

                    </p>

                  </div>

                  <FiGithub className="text-2xl text-slate-400" />

                </div>

                <div className="mt-5 flex flex-wrap items-center gap-4">

                  {repo.language && (

                    <span className="rounded-full bg-blue-500/10 px-3 py-1 text-xs font-medium text-blue-400">
                      {repo.language}
                    </span>

                  )}

                  <span>⭐ {repo.stars}</span>

                  <span>🍴 {repo.forks}</span>

                  <span>👀 {repo.watchers}</span>

                </div>

                <p className="mt-5 text-sm app-text-secondary">
                  Updated{" "}
                  {new Date(repo.updatedAt).toLocaleDateString()}
                </p>

                <a
                  href={repo.repositoryUrl}
                  target="_blank"
                  rel="noreferrer"
                  className="mt-6 inline-flex items-center gap-2 rounded-xl bg-blue-600 px-5 py-3 font-medium text-white transition hover:bg-blue-700"
                >
                  <FiGithub />
                  Open on GitHub
                </a>

              </div>

            ))}

          </div>

        </div>

      </div>
    </div>
  );
}