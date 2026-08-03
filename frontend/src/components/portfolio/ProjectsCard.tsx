import {
  FiGithub,
} from "react-icons/fi";
import type { GitHubRepository } from "../../types/github";

interface ProjectsCardProps {
  projects?: GitHubRepository[];
}

export default function ProjectsCard({
  projects = [],
}: ProjectsCardProps) {
  return (
    <section className="app-surface app-border rounded-2xl p-6">

      <div className="mb-6">

        <h2 className="text-2xl font-bold">
          Featured Project
        </h2>

        <p className="app-text-secondary mt-2">
          Your flagship GitHub repository.
        </p>

      </div>

      <div className="space-y-6">

        {projects.map((project) => (

        <div
          key={project.name}
          className="group rounded-3xl border border-slate-700/40 bg-slate-900/40 p-6 transition-all duration-300 hover:-translate-y-1 hover:border-blue-500 hover:shadow-xl hover:shadow-blue-500/10"
        >

          <div className="flex items-start justify-between">

            <div>

              {project.name === "CodeMentorAI" && (
                <span className="mb-3 inline-flex rounded-full bg-amber-500/10 px-3 py-1 text-xs font-semibold text-amber-400">
                  ⭐ FEATURED PROJECT
                </span>
              )}

              <h3 className="text-3xl font-bold tracking-tight">
                {project.name}
              </h3>

              <p className="mt-3 text-sm app-text-secondary">

                {project.language}

                {" • "}

                {project.isPrivate ? "Private Repository" : "Public Repository"}

                {" • "}

                Updated {formatDate(project.updatedAt)}

              </p>

            </div>

            <FiGithub
              className="text-3xl text-slate-500 transition group-hover:text-white"
            />

          </div>

          <p className="mt-5 leading-7 app-text-secondary">

          {
            project.description ??
            `A ${project.language ?? "software"} project hosted on GitHub.`
          }

          </p>

          {project.topics.length > 0 && (

            <div className="mt-5 flex flex-wrap gap-2">

              {project.topics.map((topic) => (

                <span
                  key={topic}
                  className="rounded-full bg-slate-800 px-3 py-1 text-xs text-blue-300"
                >
                  #{topic}
                </span>

              ))}

            </div>

          )}

          <div className="mt-8 flex flex-wrap items-center gap-6 rounded-2xl border border-slate-700/40 bg-slate-800/40 px-5 py-4">

            <span className="font-medium">
              ⭐ {project.stars} Stars
            </span>

            <span className="font-medium">
              🍴 {project.forks} Forks
            </span>

            <span className="font-medium">
              👀 {project.watchers} Watchers
            </span>

            <span className="font-medium">
              🐞 {project.openIssues} Issues
            </span>

          </div>

          <a
            href={project.repositoryUrl}
            target="_blank"
            rel="noreferrer"
            className="mt-8 inline-flex items-center gap-2 rounded-xl bg-gradient-to-r from-blue-600 to-indigo-600 px-6 py-3 font-semibold text-white transition hover:scale-105"
          >

            <FiGithub />

            View on GitHub →

          </a>

        </div>

        ))}

      </div>

    </section>
  );
}

function formatDate(date: string) {

  return new Date(date).toLocaleDateString(
    "en-US",
    {
      month: "short",
      day: "numeric",
      year: "numeric",
    }
  );

}