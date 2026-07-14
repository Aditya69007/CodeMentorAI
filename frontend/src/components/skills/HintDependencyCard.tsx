import {
  FiActivity,
  FiAlertTriangle,
  FiCheckCircle,
  FiHelpCircle,
  FiTrendingUp,
} from "react-icons/fi";

import type {
  HintDependencyScore,
} from "../../types/hintDependency";

interface HintDependencyCardProps {
  data: HintDependencyScore;
}

export default function HintDependencyCard({
  data,
}: HintDependencyCardProps) {

  const getLevelConfig = () => {

    switch (data.dependencyLevel) {

      case "LOW":
        return {
          label: "Low Dependency",
          icon: FiCheckCircle,
          textClass: "text-emerald-500",
          bgClass: "bg-emerald-500/10",
          borderClass: "border-emerald-500/20",
          progressClass: "bg-emerald-500",
        };

      case "HEALTHY":
        return {
          label: "Healthy Usage",
          icon: FiTrendingUp,
          textClass: "text-blue-500",
          bgClass: "bg-blue-500/10",
          borderClass: "border-blue-500/20",
          progressClass: "bg-blue-500",
        };

      case "MODERATE":
        return {
          label: "Moderate Dependency",
          icon: FiActivity,
          textClass: "text-amber-500",
          bgClass: "bg-amber-500/10",
          borderClass: "border-amber-500/20",
          progressClass: "bg-amber-500",
        };

      case "HIGH":
        return {
          label: "High Dependency",
          icon: FiAlertTriangle,
          textClass: "text-orange-500",
          bgClass: "bg-orange-500/10",
          borderClass: "border-orange-500/20",
          progressClass: "bg-orange-500",
        };

      default:
        return {
          label: "Very High Dependency",
          icon: FiAlertTriangle,
          textClass: "text-red-500",
          bgClass: "bg-red-500/10",
          borderClass: "border-red-500/20",
          progressClass: "bg-red-500",
        };
    }

  };

  const config = getLevelConfig();

  const Icon = config.icon;

  const hintLevels = [
    {
      level: "Level 1",
      description: "Conceptual",
      count: data.level1HintsUsed,
    },
    {
      level: "Level 2",
      description: "Key Observation",
      count: data.level2HintsUsed,
    },
    {
      level: "Level 3",
      description: "Algorithm Guidance",
      count: data.level3HintsUsed,
    },
    {
      level: "Level 4",
      description: "Pseudocode",
      count: data.level4HintsUsed,
    },
  ];

  return (

    <section className="app-surface app-border overflow-hidden rounded-xl border">

      {/* HEADER */}

      <div className="app-border flex flex-col gap-4 border-b p-5 sm:flex-row sm:items-center sm:justify-between">

        <div className="flex items-center gap-3">

          <div
            className={`
              flex h-11 w-11 shrink-0
              items-center justify-center
              rounded-xl
              ${config.bgClass}
              ${config.textClass}
            `}
          >
            <FiHelpCircle size={21} />
          </div>

          <div>

            <h2 className="text-lg font-semibold">
              AI Hint Dependency
            </h2>

            <p className="app-text-secondary mt-0.5 text-sm">
              Understand how much you rely on AI guidance while solving problems.
            </p>

          </div>

        </div>

        <div
          className={`
            flex w-fit items-center gap-2
            rounded-full border
            px-3 py-1.5
            text-sm font-semibold
            ${config.bgClass}
            ${config.borderClass}
            ${config.textClass}
          `}
        >
          <Icon size={15} />
          {config.label}
        </div>

      </div>


      {/* SCORE */}

      <div className="p-5">

        <div className="grid gap-5 lg:grid-cols-[220px_1fr]">

          <div
            className={`
              flex flex-col items-center
              justify-center rounded-xl
              border p-6 text-center
              ${config.bgClass}
              ${config.borderClass}
            `}
          >

            <p className="app-text-secondary text-xs font-semibold uppercase tracking-wider">
              Dependency Score
            </p>

            <div className={`mt-3 text-5xl font-bold ${config.textClass}`}>
              {data.dependencyScore}
            </div>

            <p className="app-text-secondary mt-1 text-sm">
              out of 100
            </p>

          </div>


          <div className="flex flex-col justify-center">

            <div className="flex items-center justify-between text-sm">

              <span className="app-text-secondary">
                Independent
              </span>

              <span className="app-text-secondary">
                AI Dependent
              </span>

            </div>

            <div className="app-surface-secondary mt-2 h-3 overflow-hidden rounded-full">

              <div
                className={`
                  h-full rounded-full
                  transition-all duration-500
                  ${config.progressClass}
                `}
                style={{
                  width: `${Math.min(
                    data.dependencyScore,
                    100
                  )}%`,
                }}
              />

            </div>

            <div className="app-surface-muted mt-5 rounded-lg p-4">

              <p className="app-text-secondary text-sm leading-6">
                {data.message}
              </p>

            </div>

          </div>

        </div>


        {/* STATISTICS */}

        <div className="mt-6 grid gap-3 sm:grid-cols-2 lg:grid-cols-4">

          <div className="app-surface-muted rounded-lg p-4">

            <p className="app-text-secondary text-xs">
              Problems Attempted
            </p>

            <p className="mt-1 text-xl font-bold">
              {data.totalProblemsAttempted}
            </p>

          </div>

          <div className="app-surface-muted rounded-lg p-4">

            <p className="app-text-secondary text-xs">
              Problems With Hints
            </p>

            <p className="mt-1 text-xl font-bold">
              {data.problemsWithHints}
            </p>

          </div>

          <div className="app-surface-muted rounded-lg p-4">

            <p className="app-text-secondary text-xs">
              Total Hints Used
            </p>

            <p className="mt-1 text-xl font-bold">
              {data.totalHintsUsed}
            </p>

          </div>

          <div className="app-surface-muted rounded-lg p-4">

            <p className="app-text-secondary text-xs">
              Hint Usage Rate
            </p>

            <p className="mt-1 text-xl font-bold">
              {data.hintUsageRate}%
            </p>

          </div>

        </div>


        {/* HINT LEVEL BREAKDOWN */}

        <div className="mt-6">

          <h3 className="text-sm font-semibold">
            Progressive Hint Usage
          </h3>

          <p className="app-text-secondary mt-1 text-xs">
            Stronger hint levels provide more detailed AI guidance.
          </p>

          <div className="mt-4 grid gap-3 sm:grid-cols-2 lg:grid-cols-4">

            {
              hintLevels.map((hint) => (

                <div
                  key={hint.level}
                  className="app-border rounded-lg border p-4"
                >

                  <div className="flex items-center justify-between">

                    <div>

                      <p className="text-sm font-semibold">
                        {hint.level}
                      </p>

                      <p className="app-text-secondary mt-0.5 text-xs">
                        {hint.description}
                      </p>

                    </div>

                    <span className="app-surface-muted flex h-9 min-w-9 items-center justify-center rounded-lg px-2 text-sm font-bold">
                      {hint.count}
                    </span>

                  </div>

                </div>

              ))
            }

          </div>

        </div>

      </div>

    </section>

  );
}