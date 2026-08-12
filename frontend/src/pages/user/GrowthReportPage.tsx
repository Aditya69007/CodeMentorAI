import { useEffect, useState } from "react";
import {
  Award,
  Brain,
  CheckCircle2,
  RefreshCw,
  Sparkles,
  Target,
  TrendingUp,
  TriangleAlert,
} from "lucide-react";

import {getMyGrowthReport} from "../../services/aiMentorService";
import type { GrowthReportResponse } from "../../services/aiMentorService";

const GrowthReportPage = () => {
  const [report, setReport] = useState<GrowthReportResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const [refreshing, setRefreshing] = useState(false);
  const [error, setError] = useState("");

  const fetchReport = async () => {
    try {
      setError("");

      const data = await getMyGrowthReport();

      setReport(data);
    } catch (err) {
      console.error("Failed to load growth report:", err);

      setError("Unable to load your growth report.");
    } finally {
      setLoading(false);
      setRefreshing(false);
    }
  };

  useEffect(() => {
    const loadReport = async () => {
      await fetchReport();
    };

    loadReport();
  }, []);

  const handleRefresh = async () => {
    setRefreshing(true);

    await fetchReport();
  };

  if (loading) {
    return (
      <div className="flex min-h-[60vh] items-center justify-center">
        <RefreshCw className="h-8 w-8 animate-spin text-violet-500" />
      </div>
    );
  }

  if (error || !report) {
    return (
      <div className="flex min-h-[60vh] flex-col items-center justify-center gap-4">
        <p className="app-text-secondary">
          {error || "Growth report is unavailable."}
        </p>

        <button
          onClick={handleRefresh}
          className="rounded-lg border app-border px-4 py-2 text-sm app-text-primary transition app-hover"
        >
          Try Again
        </button>
      </div>
    );
  }

  const growthScore = Math.max(
    0,
    Math.min(report.overallGrowthScore, 100)
  );

  return (
    <div className="mx-auto w-full max-w-7xl space-y-7 px-6 py-10">
      {/* HEADER */}

      <div className="flex flex-col justify-between gap-4 sm:flex-row sm:items-center">
        <div>
          <h1 className="text-3xl font-bold app-text-primary">
            Developer Growth Report
          </h1>

          <p className="mt-2 app-text-secondary">
            A complete overview of your coding progress and learning behavior.
          </p>
        </div>

        <button
          onClick={handleRefresh}
          disabled={refreshing}
          className="flex items-center justify-center gap-2 rounded-lg border app-border px-4 py-3 text-sm font-medium app-text-primary transition app-hover disabled:cursor-not-allowed disabled:opacity-60"
        >
          <RefreshCw
            className={`h-4 w-4 ${
              refreshing ? "animate-spin" : ""
            }`}
          />

          Refresh Report
        </button>
      </div>

      {/* MAIN GROWTH ASSESSMENT */}

      <section className="rounded-2xl border app-border app-surface p-7">
        <div className="grid gap-8 lg:grid-cols-[250px_1fr] lg:items-center">
          <div className="flex justify-center">
            <div
              className="flex h-48 w-48 items-center justify-center rounded-full"
              style={{
                background: `conic-gradient(
                  #8b5cf6 ${growthScore * 3.6}deg,
                  var(--border) 0deg
                )`,
              }}
            >
              <div className="app-surface flex h-40 w-40 flex-col items-center justify-center rounded-full">
                <span className="text-5xl font-bold text-violet-500">
                  {report.overallGrowthScore}
                </span>

                <span className="mt-2 text-sm app-text-secondary">
                  / 100 Growth Score
                </span>
              </div>
            </div>
          </div>

          <div>
            <div className="mb-5 flex flex-wrap items-center gap-3">
              <span className="rounded-full bg-violet-500/10 px-4 py-1.5 text-sm font-semibold text-violet-400">
                {report.developerLevel}
              </span>

              <span className="app-text-muted">
                Current Developer Level
              </span>
            </div>

            <h2 className="text-2xl font-bold app-text-primary">
              Your Growth Assessment
            </h2>

            <p className="mt-4 leading-7 app-text-secondary">
              {report.growthSummary}
            </p>

            <div className="mt-6 rounded-xl border border-violet-500/20 bg-violet-500/10 p-5">
              <p className="font-semibold app-text-primary">
                Recommended Next Action
              </p>

              <p className="mt-2 app-text-secondary">
                {report.recommendedNextAction}
              </p>
            </div>
          </div>
        </div>
      </section>

      {/* METRICS */}

      <section className="grid gap-5 sm:grid-cols-2 lg:grid-cols-4">
        <MetricCard
          icon={<Brain className="h-5 w-5" />}
          title="Hint Dependency"
          value={`${report.hintDependencyScore}%`}
          description="Current AI assistance dependency"
        />

        <MetricCard
          icon={<Target className="h-5 w-5" />}
          title="Independent Solve Rate"
          value={`${report.independentSolveRate}%`}
          description="Problems solved without AI"
        />

        <MetricCard
          icon={<CheckCircle2 className="h-5 w-5" />}
          title="Completed Sessions"
          value={report.totalCompletedIndependentSessions}
          description="Independent sessions completed"
        />

        <MetricCard
          icon={<TrendingUp className="h-5 w-5" />}
          title="Independent Solves"
          value={report.independentlySolvedProblems}
          description="Successfully solved independently"
        />
      </section>

      {/* CONCEPT GROWTH */}

      <section className="rounded-2xl border app-border app-surface p-6">
        <div className="mb-5 flex items-center gap-3">
          <TrendingUp className="h-5 w-5 text-violet-500" />

          <div>
            <h2 className="text-lg font-bold app-text-primary">
              Concept Growth
            </h2>

            <p className="text-sm app-text-muted">
              Progress across concepts from your learning history.
            </p>
          </div>
        </div>

        {report.conceptGrowth.length === 0 ? (
          <p className="app-text-muted">
            No concept growth data detected yet.
          </p>
        ) : (
          <div className="grid gap-4 md:grid-cols-2">
            {report.conceptGrowth.map((concept) => (
              <div
                key={concept.concept}
                className="rounded-xl border app-border app-surface-secondary p-5"
              >
                <div className="flex items-start justify-between gap-4">
                  <h3 className="font-semibold app-text-primary">
                    {concept.concept}
                  </h3>

                  <span className="rounded-full bg-violet-500/10 px-3 py-1 text-xs font-semibold text-violet-400">
                    {concept.growthStatus}
                  </span>
                </div>

                <div className="mt-4 flex gap-5 text-sm app-text-secondary">
                  <span>
                    Mistakes: {concept.totalMistakes}
                  </span>

                  <span>
                    Accepted: {concept.acceptedSubmissions}
                  </span>
                </div>

                <p className="mt-4 text-sm leading-6 app-text-muted">
                  {concept.message}
                </p>
              </div>
            ))}
          </div>
        )}
      </section>

      {/* MISTAKES + ACHIEVEMENTS */}

      <section className="grid gap-5 lg:grid-cols-2">
        <div className="rounded-2xl border app-border app-surface p-6">
          <div className="mb-5 flex items-center gap-3">
            <TriangleAlert className="h-5 w-5 text-violet-500" />

            <h2 className="text-lg font-bold app-text-primary">
              Recurring Mistakes
            </h2>
          </div>

          {report.recurringMistakes.length === 0 ? (
            <p className="app-text-muted">
              No recurring mistakes detected.
            </p>
          ) : (
            <div className="space-y-3">
              {report.recurringMistakes.map((mistake) => (
                <div
                  key={mistake}
                  className="rounded-lg border app-border app-surface-secondary px-4 py-3 text-sm font-medium app-text-secondary"
                >
                  {mistake.replaceAll("_", " ")}
                </div>
              ))}
            </div>
          )}
        </div>

        <div className="rounded-2xl border app-border app-surface p-6">
          <div className="mb-5 flex items-center gap-3">
            <Award className="h-5 w-5 text-violet-500" />

            <h2 className="text-lg font-bold app-text-primary">
              Achievements
            </h2>
          </div>

          {report.achievements.length === 0 ? (
            <p className="app-text-muted">
              Keep progressing to unlock achievements.
            </p>
          ) : (
            <div className="space-y-3">
              {report.achievements.map((achievement) => (
                <div
                  key={achievement}
                  className="flex items-center gap-3 rounded-lg border border-violet-500/20 bg-violet-500/10 px-4 py-3"
                >
                  <Sparkles className="h-4 w-4 text-violet-400" />

                  <span className="text-sm font-medium app-text-primary">
                    {achievement}
                  </span>
                </div>
              ))}
            </div>
          )}
        </div>
      </section>
    </div>
  );
};

interface MetricCardProps {
  icon: React.ReactNode;
  title: string;
  value: string | number;
  description: string;
}

const MetricCard = ({
  icon,
  title,
  value,
  description,
}: MetricCardProps) => {
  return (
    <div className="rounded-2xl border app-border app-surface p-5">
      <div className="flex items-center gap-3 text-violet-500">
        {icon}

        <span className="text-sm font-medium app-text-secondary">
          {title}
        </span>
      </div>

      <p className="mt-5 text-3xl font-bold app-text-primary">
        {value}
      </p>

      <p className="mt-2 text-sm app-text-muted">
        {description}
      </p>
    </div>
  );
};

export default GrowthReportPage;