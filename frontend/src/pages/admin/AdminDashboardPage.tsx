import { useEffect, useState } from "react";

import {
  FiActivity,
  FiBookOpen,
  FiCheckCircle,
  FiCpu,
  FiLayers,
  FiShield,
  FiUsers,
} from "react-icons/fi";

import {
  getAdminDashboardAnalytics,
  getAdminDashboardStats,
} from "../../services/adminService";
import { getAdminSettings } from "../../services/adminSettingsService";
import AdminSubmissionActivityChart from "../../components/admin/dashboard/AdminSubmissionActivityChart";
import AdminDifficultyChart from "../../components/admin/dashboard/AdminDifficultyChart";
import AdminStatusDistribution from "../../components/admin/dashboard/AdminStatusDistribution";

import type {
  AdminDashboardAnalytics,
  AdminDashboardStats,
} from "../../types/admin";

export default function AdminDashboardPage() {
  const [stats, setStats] =
    useState<AdminDashboardStats | null>(null);

  const [analytics, setAnalytics] =
    useState<AdminDashboardAnalytics | null>(null);

  const [loading, setLoading] = useState(true);

  const [error, setError] = useState("");

  useEffect(() => {
    let refreshTimer: ReturnType<typeof setInterval> | undefined;

    const loadDashboardData = async (
      showLoading = true
    ) => {
      try {
        if (showLoading) {
          setLoading(true);
        }

        setError("");

        const [
          dashboardStats,
          dashboardAnalytics,
        ] = await Promise.all([
          getAdminDashboardStats(),
          getAdminDashboardAnalytics(),
        ]);

        setStats(dashboardStats);
        setAnalytics(dashboardAnalytics);

        const settings = await getAdminSettings();

        if (
          settings.autoRefreshDashboard &&
          settings.autoRefreshInterval > 0
        ) {
          refreshTimer = setInterval(() => {
            void loadDashboardData(false);
          }, settings.autoRefreshInterval * 1000);
        }

      } catch {
        setError(
          "Unable to load admin dashboard data."
        );
      } finally {
        setLoading(false);
      }
    };

    void loadDashboardData();

    return () => {
      if (refreshTimer) {
        clearInterval(refreshTimer);
      }
    };
  }, []);

  const dashboardCards = [
    {
      title: "Total Users",
      value: stats?.totalUsers ?? 0,
      description: "Registered regular users",
      icon: FiUsers,
    },
    {
      title: "Total Admins",
      value: stats?.totalAdmins ?? 0,
      description: "Platform administrators",
      icon: FiShield,
    },
    {
      title: "Total Problems",
      value: stats?.totalProblems ?? 0,
      description: "Coding problems available",
      icon: FiBookOpen,
    },
    {
      title: "Total Topics",
      value: stats?.totalTopics ?? 0,
      description: "Problem-solving topics",
      icon: FiLayers,
    },
    {
      title: "Total Submissions",
      value: stats?.totalSubmissions ?? 0,
      description: "Code submissions received",
      icon: FiActivity,
    },
    {
      title: "Accepted Submissions",
      value: stats?.acceptedSubmissions ?? 0,
      description: "Successful code submissions",
      icon: FiCheckCircle,
    },
    {
      title: "AI Analyses",
      value: stats?.totalAiAnalyses ?? 0,
      description: "AI mentor analyses generated",
      icon: FiCpu,
    },
  ];

  return (
    <div className="mx-auto max-w-7xl">
      {/* ==========================================
          PAGE HEADER
      ========================================== */}

      <div>
        <p
          className="
            text-sm
            font-semibold
            uppercase
            tracking-wider
            text-blue-400
          "
        >
          Overview
        </p>

        <h1
          className="
            mt-2
            text-3xl
            font-bold
            tracking-tight
          "
        >
          Dashboard
        </h1>

        <p
          className="
            app-text-secondary
            mt-2
          "
        >
          Monitor platform activity, users,
          coding progress, and AI mentor usage.
        </p>
      </div>

      {/* ==========================================
          ERROR
      ========================================== */}

      {error && (
        <div
          className="
            mt-6
            rounded-xl
            border
            border-red-500/20
            bg-red-500/10
            px-5
            py-4
            text-sm
            text-red-400
          "
        >
          {error}
        </div>
      )}

      {/* ==========================================
          STAT CARDS
      ========================================== */}

      <div
        className="
          mt-8
          grid
          gap-5
          sm:grid-cols-2
          xl:grid-cols-4
        "
      >
        {dashboardCards.map((card) => {
          const Icon = card.icon;

          return (
            <div
              key={card.title}
              className="
                app-surface-secondary
                app-border
                rounded-xl
                border
                p-5
                transition
                duration-200
                hover:-translate-y-0.5
              "
            >
              <div
                className="
                  flex
                  items-start
                  justify-between
                  gap-4
                "
              >
                <div>
                  <p className="app-text-secondary text-sm">
                    {card.title}
                  </p>

                  <p
                    className="
                      mt-3
                      text-3xl
                      font-bold
                    "
                  >
                    {loading ? "—" : card.value}
                  </p>
                </div>

                <div
                  className="
                    flex
                    h-11
                    w-11
                    shrink-0
                    items-center
                    justify-center
                    rounded-xl
                    bg-blue-500/10
                    text-xl
                    text-blue-400
                  "
                >
                  <Icon />
                </div>
              </div>

              <p
                className="
                  app-text-secondary
                  mt-4
                  text-xs
                "
              >
                {card.description}
              </p>
            </div>
          );
        })}
      </div>

      {/* ==========================================
          ANALYTICS
      ========================================== */}

      {analytics && (
        <>
          {/* ==========================================
              SUBMISSION ACTIVITY + PERFORMANCE
          ========================================== */}

          <div
            className="
              mt-8
              grid
              gap-6
              xl:grid-cols-3
            "
          >
            {/* SUBMISSION ACTIVITY */}

            <section
              className="
                app-surface-secondary
                app-border
                rounded-xl
                border
                p-6
                xl:col-span-2
              "
            >
              <div>
                <h2 className="text-lg font-semibold">
                  Submission Activity
                </h2>

                <p
                  className="
                    app-text-secondary
                    mt-1
                    text-sm
                  "
                >
                  Platform submissions during the
                  last 7 days.
                </p>
              </div>

              <div className="mt-6">
                <AdminSubmissionActivityChart
                  data={analytics.submissionActivity}
                />
              </div>
            </section>

            {/* PLATFORM PERFORMANCE */}

            <section
              className="
                app-surface-secondary
                app-border
                rounded-xl
                border
                p-6
              "
            >
              <h2 className="text-lg font-semibold">
                Platform Performance
              </h2>

              <p
                className="
                  app-text-secondary
                  mt-1
                  text-sm
                "
              >
                Submission success and AI mentor
                analysis metrics.
              </p>

              <div className="mt-8 space-y-8">
                <MetricProgress
                  label="Acceptance Rate"
                  value={analytics.acceptanceRate}
                />

                <MetricProgress
                  label="AI Analysis Coverage"
                  value={analytics.aiAnalysisCoverage}
                />
              </div>

              <div
                className="
                  app-border
                  mt-8
                  rounded-xl
                  border
                  p-4
                "
              >
                <p className="app-text-secondary text-xs">
                  Total platform activity
                </p>

                <div className="mt-3 flex items-end justify-between">
                  <div>
                    <p className="text-2xl font-bold">
                      {stats?.totalSubmissions ?? 0}
                    </p>

                    <p className="app-text-secondary mt-1 text-xs">
                      Code submissions
                    </p>
                  </div>

                  <FiActivity className="text-2xl text-blue-400" />
                </div>
              </div>
            </section>
          </div>

          {/* ==========================================
              DIFFICULTY + SUBMISSION STATUS
          ========================================== */}

          <div
            className="
              mt-6
              grid
              gap-6
              xl:grid-cols-2
            "
          >
            {/* DIFFICULTY DISTRIBUTION */}

            <section
              className="
                app-surface-secondary
                app-border
                rounded-xl
                border
                p-6
              "
            >
              <h2 className="text-lg font-semibold">
                Problem Difficulty
              </h2>

              <p
                className="
                  app-text-secondary
                  mt-1
                  text-sm
                "
              >
                Distribution of coding problems
                available on the platform.
              </p>

              <AdminDifficultyChart
                distribution={
                  analytics.difficultyDistribution
                }
              />

              <div
                className="
                  mt-2
                  flex
                  flex-wrap
                  justify-center
                  gap-6
                  text-xs
                "
              >
                <LegendDot
                  label="Easy"
                  dotClassName="bg-emerald-500"
                />

                <LegendDot
                  label="Medium"
                  dotClassName="bg-amber-500"
                />

                <LegendDot
                  label="Hard"
                  dotClassName="bg-red-500"
                />
              </div>
            </section>

            {/* SUBMISSION STATUS */}

            <section
              className="
                app-surface-secondary
                app-border
                rounded-xl
                border
                p-6
              "
            >
              <h2 className="text-lg font-semibold">
                Submission Results
              </h2>

              <p
                className="
                  app-text-secondary
                  mt-1
                  text-sm
                "
              >
                Distribution of all code submission
                outcomes.
              </p>

              <div className="mt-6">
                <AdminStatusDistribution
                  distribution={
                    analytics.submissionStatusDistribution
                  }
                />
              </div>
            </section>
          </div>
        </>
      )}
    </div>
  );
}

/* ==========================================
   METRIC PROGRESS
========================================== */

interface MetricProgressProps {
  label: string;
  value: number;
}

function MetricProgress({
  label,
  value,
}: MetricProgressProps) {
  const safeValue = Math.min(
    Math.max(value, 0),
    100
  );

  return (
    <div>
      <div
        className="
          mb-3
          flex
          items-center
          justify-between
          gap-4
        "
      >
        <span className="app-text-secondary text-sm">
          {label}
        </span>

        <span className="text-lg font-bold">
          {value.toFixed(2)}%
        </span>
      </div>

      <div
        className="
          h-2
          overflow-hidden
          rounded-full
          bg-slate-500/10
        "
      >
        <div
          className="
            h-full
            rounded-full
            bg-blue-500
            transition-all
            duration-700
          "
          style={{
            width: `${safeValue}%`,
          }}
        />
      </div>
    </div>
  );
}

/* ==========================================
   LEGEND DOT
========================================== */

interface LegendDotProps {
  label: string;
  dotClassName: string;
}

function LegendDot({
  label,
  dotClassName,
}: LegendDotProps) {
  return (
    <div
      className="
        app-text-secondary
        flex
        items-center
        gap-2
      "
    >
      <span
        className={`
          h-2.5
          w-2.5
          rounded-full
          ${dotClassName}
        `}
      />

      <span>{label}</span>
    </div>
  );
}