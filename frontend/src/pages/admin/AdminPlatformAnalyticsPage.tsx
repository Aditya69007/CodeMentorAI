import {
  useEffect,
  useState,
} from "react";

import {
  FiActivity,
  FiBarChart2,
  FiBookOpen,
  FiCheckCircle,
  FiCpu,
  FiLayers,
  FiTarget,
  FiUsers,
} from "react-icons/fi";

import {
  getAdminPlatformAnalytics,
} from "../../services/adminPlatformAnalyticsService";

import type {
  AdminPlatformAnalytics,
} from "../../types/adminPlatformAnalytics";


const formatLabel = (
  value: string
) => {

  return value
    .replaceAll("_", " ")
    .toLowerCase()
    .replace(
      /\b\w/g,
      (character) =>
        character.toUpperCase()
    );
};


interface DistributionSectionProps {

  title: string;

  description: string;

  data: Record<string, number>;

  emptyMessage: string;
}


function DistributionSection({
  title,
  description,
  data,
  emptyMessage,
}: DistributionSectionProps) {

  const entries =
    Object.entries(data);


  const total =
    entries.reduce(
      (sum, [, value]) =>
        sum + value,
      0
    );


  const maximum =
    Math.max(
      ...entries.map(
        ([, value]) => value
      ),
      1
    );


  return (

    <section className="app-surface app-border rounded-2xl border">


      <div className="app-border border-b px-6 py-5">

        <h2 className="text-lg font-semibold">

          {title}

        </h2>


        <p className="app-text-secondary mt-1 text-sm">

          {description}

        </p>

      </div>


      <div className="p-6">

        {entries.length === 0 ? (

          <div className="app-text-secondary py-10 text-center">

            {emptyMessage}

          </div>

        ) : (

          <div className="space-y-5">

            {entries.map(
              ([label, value]) => {

                const width =
                  (value / maximum) * 100;


                const percentage =
                  total > 0
                    ? Math.round(
                        (value / total) * 100
                      )
                    : 0;


                return (

                  <div key={label}>


                    <div className="mb-2 flex items-center justify-between gap-4">

                      <span className="text-sm font-medium">

                        {formatLabel(label)}

                      </span>


                      <span className="app-text-secondary text-sm">

                        {value}

                        {" · "}

                        {percentage}%

                      </span>

                    </div>


                    <div className="app-surface-secondary h-2.5 overflow-hidden rounded-full">

                      <div
                        className="h-full rounded-full bg-blue-500 transition-all duration-300"

                        style={{
                          width: `${width}%`,
                        }}
                      />

                    </div>


                  </div>

                );

              }
            )}

          </div>

        )}

      </div>


    </section>

  );
}


export default function AdminPlatformAnalyticsPage() {

  const [
    analytics,
    setAnalytics,
  ] =
    useState<AdminPlatformAnalytics | null>(
      null
    );


  const [loading, setLoading] =
    useState(true);


  const [error, setError] =
    useState("");


  useEffect(() => {

    let active = true;


    const loadAnalytics = async () => {

      try {

        setLoading(true);

        setError("");


        const data =
          await getAdminPlatformAnalytics();


        if (active) {

          setAnalytics(data);

        }


      } catch (error) {

        console.error(error);


        if (active) {

          setError(
            "Unable to load platform analytics."
          );

        }


      } finally {

        if (active) {

          setLoading(false);

        }

      }

    };


    void loadAnalytics();


    return () => {

      active = false;

    };

  }, []);


  if (loading) {

    return (

      <div className="app-text-secondary flex min-h-[500px] items-center justify-center">

        Loading platform analytics...

      </div>

    );
  }


  if (error || !analytics) {

    return (

      <div className="rounded-xl border border-red-500/30 bg-red-500/10 p-5 text-red-500">

        {error || "Platform analytics are unavailable."}

      </div>

    );
  }


  const statCards = [

    {
      title: "Total Users",
      value: analytics.totalUsers,
      description: "Registered platform users",
      icon: FiUsers,
    },

    {
      title: "Problems",
      value: analytics.totalProblems,
      description: "Coding problems available",
      icon: FiBookOpen,
    },

    {
      title: "Topics",
      value: analytics.totalTopics,
      description: "Learning topics available",
      icon: FiLayers,
    },

    {
      title: "Submissions",
      value: analytics.totalSubmissions,
      description: "Total code submissions",
      icon: FiActivity,
    },

    {
      title: "Accepted",
      value: analytics.acceptedSubmissions,
      description: "Successful submissions",
      icon: FiCheckCircle,
    },

  ];


  const maximumActivity =
    Math.max(
      ...analytics.submissionActivity.map(
        (item) => item.submissions
      ),
      1
    );


  return (

    <div className="mx-auto w-full max-w-7xl">


      {/* PAGE HEADER */}

      <div>

        <p className="text-sm font-semibold uppercase tracking-wider text-blue-500">

          Platform Intelligence

        </p>


        <h1 className="mt-2 text-3xl font-bold tracking-tight">

          Platform Analytics

        </h1>


        <p className="app-text-secondary mt-2 max-w-3xl">

          Monitor platform activity, submission performance,
          programming language usage, problem distribution,
          and AI analysis coverage.

        </p>

      </div>



      {/* STAT CARDS */}

      <div className="mt-8 grid gap-4 sm:grid-cols-2 xl:grid-cols-5">

        {statCards.map(
          (card) => {

            const Icon =
              card.icon;


            return (

              <section
                key={card.title}
                className="app-surface app-border rounded-2xl border p-5"
              >

                <div className="flex items-start justify-between gap-4">

                  <div>

                    <p className="app-text-secondary text-sm font-medium">

                      {card.title}

                    </p>


                    <p className="mt-3 text-3xl font-bold tracking-tight">

                      {card.value}

                    </p>

                  </div>


                  <div className="app-surface-secondary flex h-11 w-11 shrink-0 items-center justify-center rounded-xl">

                    <Icon
                      className="text-blue-500"
                      size={20}
                    />

                  </div>

                </div>


                <p className="app-text-muted mt-4 text-xs">

                  {card.description}

                </p>

              </section>

            );

          }
        )}

      </div>



      {/* PERFORMANCE METRICS */}

      <div className="mt-7 grid gap-6 md:grid-cols-2">


        {/* ACCEPTANCE RATE */}

        <section className="app-surface app-border rounded-2xl border p-6">

          <div className="flex items-start justify-between gap-4">

            <div>

              <p className="app-text-secondary text-sm font-medium">

                Acceptance Rate

              </p>


              <p className="mt-3 text-3xl font-bold">

                {analytics.acceptanceRate.toFixed(2)}%

              </p>

            </div>


            <div className="app-surface-secondary flex h-11 w-11 items-center justify-center rounded-xl">

              <FiTarget
                className="text-blue-500"
                size={20}
              />

            </div>

          </div>


          <div className="app-surface-secondary mt-5 h-3 overflow-hidden rounded-full">

            <div
              className="h-full rounded-full bg-blue-500"

              style={{
                width:
                  `${Math.min(
                    analytics.acceptanceRate,
                    100
                  )}%`,
              }}
            />

          </div>


          <p className="app-text-muted mt-3 text-xs">

            {analytics.acceptedSubmissions} accepted out of{" "}
            {analytics.totalSubmissions} total submissions.

          </p>

        </section>



        {/* AI COVERAGE */}

        <section className="app-surface app-border rounded-2xl border p-6">

          <div className="flex items-start justify-between gap-4">

            <div>

              <p className="app-text-secondary text-sm font-medium">

                AI Analysis Coverage

              </p>


              <p className="mt-3 text-3xl font-bold">

                {analytics.aiAnalysisCoverage.toFixed(2)}%

              </p>

            </div>


            <div className="app-surface-secondary flex h-11 w-11 items-center justify-center rounded-xl">

              <FiCpu
                className="text-blue-500"
                size={20}
              />

            </div>

          </div>


          <div className="app-surface-secondary mt-5 h-3 overflow-hidden rounded-full">

            <div
              className="h-full rounded-full bg-blue-500"

              style={{
                width:
                  `${Math.min(
                    analytics.aiAnalysisCoverage,
                    100
                  )}%`,
              }}
            />

          </div>


          <p className="app-text-muted mt-3 text-xs">

            {analytics.totalAiAnalyses} AI analyses across{" "}
            {analytics.totalSubmissions} submissions.

          </p>

        </section>


      </div>



      {/* SUBMISSION ACTIVITY */}

      <section className="app-surface app-border mt-7 rounded-2xl border">


        <div className="app-border border-b px-6 py-5">

          <div className="flex items-center gap-2">

            <FiBarChart2 className="text-blue-500" />

            <h2 className="text-lg font-semibold">

              Submission Activity

            </h2>

          </div>


          <p className="app-text-secondary mt-1 text-sm">

            Code submissions recorded during the last seven days.

          </p>

        </div>


        <div className="p-6">

          <div className="flex h-64 items-end gap-3">

            {analytics.submissionActivity.map(
              (item) => {

                const height =
                  item.submissions === 0
                    ? 0
                    : Math.max(
                        (
                          item.submissions /
                          maximumActivity
                        ) * 100,
                        6
                      );


                return (

                  <div
                    key={item.date}
                    className="flex h-full min-w-0 flex-1 flex-col justify-end"
                  >

                    <div className="flex flex-1 items-end justify-center">

                      <div
                        title={`${item.submissions} submissions`}
                        className="w-full max-w-16 rounded-t-lg bg-blue-500 transition-all duration-300"

                        style={{
                          height: `${height}%`,
                        }}
                      />

                    </div>


                    <div className="mt-3 text-center">

                      <p className="text-sm font-semibold">

                        {item.submissions}

                      </p>


                      <p className="app-text-muted mt-1 text-xs">

                        {item.date}

                      </p>

                    </div>

                  </div>

                );

              }
            )}

          </div>

        </div>


      </section>



      {/* DISTRIBUTIONS */}

      <div className="mt-7 grid gap-6 xl:grid-cols-2">


        <DistributionSection
          title="Submission Status Distribution"
          description="Breakdown of code submissions by execution result."
          data={
            analytics.submissionStatusDistribution
          }
          emptyMessage="No submission status data available."
        />


        <DistributionSection
          title="Language Distribution"
          description="Programming languages used across platform submissions."
          data={
            analytics.languageDistribution
          }
          emptyMessage="No programming language data available."
        />


      </div>



      {/* DIFFICULTY DISTRIBUTION */}

      <div className="mt-6">


        <DistributionSection
          title="Problem Difficulty Distribution"
          description="Distribution of coding problems by difficulty level."
          data={
            analytics.difficultyDistribution
          }
          emptyMessage="No problem difficulty data available."
        />


      </div>


    </div>

  );
}