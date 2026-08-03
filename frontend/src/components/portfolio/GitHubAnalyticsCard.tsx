import { useEffect, useState } from "react";
import {
  getConnectedAccounts,
  getGitHubDashboard,
} from "../../services/connectedAccountsService";
import type { GitHubDashboard } from "../../types/github";

export default function GitHubAnalyticsCard() {

  const [dashboard, setDashboard] =
    useState<GitHubDashboard | null>(null);

  useEffect(() => {

    async function load() {

      try {

        const accounts =
          await getConnectedAccounts();

        if (
          !accounts.githubConnected ||
          !accounts.githubUsername
        ) {
          return;
        }

        const data =
          await getGitHubDashboard(
            accounts.githubUsername
          );

        setDashboard(data);

      } catch (error) {

        console.error(error);

      }

    }

    load();

  }, []);

  if (!dashboard) {
    return null;
  }

  return (

    <section className="app-surface app-border rounded-2xl p-6">

    <div className="mb-8">

        <h2 className="text-2xl font-bold">
        GitHub Analytics
        </h2>

        <p className="mt-2 app-text-secondary">
        AI-powered insights from your GitHub repositories.
        </p>

    </div>

    <div className="grid gap-5 sm:grid-cols-2 lg:grid-cols-3">

        <MetricCard
        title="Developer Score"
        value={dashboard.statistics.developerScore}
        />

        <MetricCard
        title="Repositories"
        value={dashboard.statistics.repositories}
        />

        <MetricCard
        title="Followers"
        value={dashboard.statistics.followers}
        />

        <MetricCard
        title="Repository Score"
        value={dashboard.analytics.repositoryScore}
        />

        <MetricCard
        title="Technology Score"
        value={dashboard.analytics.technologyScore}
        />

        <MetricCard
        title="Account Age"
        value={`${dashboard.statistics.accountAgeYears} Years`}
        />

    </div>

    <div className="mt-10">

    <h3 className="text-xl font-semibold">
        Technology Distribution
    </h3>

    <p className="mt-1 app-text-secondary">
        Languages used across your repositories.
    </p>

    <div className="mt-6 space-y-5">

        {dashboard.languages.map((language) => (

        <div key={language.language}>

            <div className="mb-2 flex items-center justify-between">

            <span className="font-medium">

                {language.language}

            </span>

            <span className="app-text-secondary">

                {language.percentage.toFixed(1)}%

            </span>

            </div>

            <div className="h-3 overflow-hidden rounded-full bg-slate-700">

            <div
                className="h-full rounded-full bg-blue-500 transition-all duration-700"
                style={{
                width: `${language.percentage}%`,
                }}
            />

            </div>

        </div>

        ))}

    </div>

    </div>

    <div className="mt-12 grid gap-6 lg:grid-cols-2">

    {/* Strengths */}

    <div className="rounded-2xl border border-emerald-500/20 bg-emerald-500/5 p-6">

        <h3 className="mb-5 text-xl font-semibold text-emerald-400">
        💪 Strengths
        </h3>

        <div className="space-y-3">

        {dashboard.analytics.strengths.map((strength) => (

            <div
            key={strength}
            className="flex items-start gap-3"
            >

            <span className="mt-1 text-emerald-400">
                ✓
            </span>

            <p>{strength}</p>

            </div>

        ))}

        </div>

    </div>

    {/* Improvements */}

    <div className="rounded-2xl border border-yellow-500/20 bg-yellow-500/5 p-6">

        <h3 className="mb-5 text-xl font-semibold text-yellow-400">
        🚀 Recommended Next
        </h3>

        <div className="space-y-3">

        {dashboard.analytics.improvements.map((item) => (

            <div
            key={item}
            className="flex items-start gap-3"
            >

            <span className="mt-1 text-yellow-400">
                →
            </span>

            <p>{item}</p>

            </div>

        ))}

        </div>

    </div>

    </div>

    <div className="mt-12">

    <h3 className="text-xl font-semibold">
        Strongest Technologies
    </h3>

    <div className="mt-5 flex flex-wrap gap-3">

        {dashboard.analytics.strongestTechnologies.map((tech) => (

        <span
            key={tech}
            className="rounded-full bg-blue-500/10 px-4 py-2 font-medium text-blue-400"
        >

            {tech}

        </span>

        ))}

    </div>

    </div>

    <div className="mt-10">

    <h3 className="text-xl font-semibold">
        Recommended Technologies
    </h3>

    <div className="mt-5 flex flex-wrap gap-3">

        {dashboard.analytics.recommendedTechnologies.map((tech) => (

        <span
            key={tech}
            className="rounded-full bg-orange-500/10 px-4 py-2 font-medium text-orange-400"
        >

            {tech}

        </span>

        ))}

    </div>

    </div>

    </section>

  );

}

type MetricCardProps = {
  title: string;
  value: string | number;
};

function MetricCard({
  title,
  value,
}: MetricCardProps) {

  return (

    <div className="rounded-2xl border border-slate-700/40 bg-slate-900/40 p-5 transition hover:border-blue-500">

      <p className="text-sm app-text-secondary">

        {title}

      </p>

      <h3 className="mt-3 text-3xl font-bold">

        {value}

      </h3>

    </div>

  );

}