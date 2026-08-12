import { FiGithub } from "react-icons/fi";
import type {
  GitHubRepository,
  GitHubTopRepository,
} from "../../types/github";

interface Props {
  repositories: GitHubTopRepository[];
  featuredRepositories: GitHubRepository[];
  onBrowseAll: () => void;
}

export default function TopRepositoriesCard({
  repositories,
  featuredRepositories,
  onBrowseAll,
}: Props) {

  const featuredNames = new Set(
    featuredRepositories.map(
      (repository) => repository.name
    )
  );

  const topRepositories = repositories
    .filter(
      (repository) =>
        !featuredNames.has(repository.name)
    )
    .slice(0, 3);


  return (

    <section className="app-surface app-border rounded-3xl p-6">

      <div className="mb-6">

        <h2 className="text-xl font-bold">
          Top Open Source Projects
        </h2>

        <p className="app-text-secondary mt-2">
          AI-ranked repositories based on quality, activity and popularity.
        </p>

      </div>

    <div className="space-y-4">

    {topRepositories.map((repo, index) => (

    <div
      key={repo.name}
      className="flex items-center justify-between rounded-2xl border app-border app-surface-secondary p-6 transition hover:border-blue-500/50"
    >

      <div className="flex items-start gap-5">

        <div className="flex h-10 w-10 items-center justify-center rounded-full bg-blue-600 font-bold text-white">
          {index + 1}
        </div>

        <div>

          <h3 className="text-xl font-semibold">
            {repo.name}
          </h3>

          <p className="mt-2 max-w-2xl text-sm app-text-secondary">
            {repo.description ??
              `A ${repo.language ?? "software"} project hosted on GitHub.`}
          </p>

          <div className="mt-4 flex items-center gap-5 text-sm">

            {repo.language && (
              <span className="rounded-full bg-blue-500/10 px-3 py-1 text-blue-400">
                {repo.language}
              </span>
            )}

            <span>⭐ {repo.stars}</span>

            <span>🍴 {repo.forks}</span>

          </div>

        </div>

      </div>

      <a
        href={repo.repositoryUrl}
        target="_blank"
        rel="noreferrer"
        className="rounded-xl border app-border px-5 py-3 transition hover:border-blue-500 hover:text-blue-500"
      >
        <FiGithub />
      </a>

    </div>


    ))}

    </div>

    <div className="mt-8 flex justify-end">

      <button
        onClick={onBrowseAll}
        className="rounded-xl border border-slate-700 px-5 py-3 font-medium transition hover:border-blue-500 hover:text-blue-400"
      >
        Browse All Repositories →
      </button>

    </div>

    </section>
  );
}