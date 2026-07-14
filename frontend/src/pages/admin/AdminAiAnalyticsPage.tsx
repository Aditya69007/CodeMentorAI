import {
  useEffect,
  useState,
} from "react";

import {
  FiActivity,
  FiAlertTriangle,
  FiBarChart2,
  FiCpu,
  FiMessageSquare,
  FiTrendingUp,
  FiUsers,
  FiZap,
} from "react-icons/fi";

import {
  getAdminAiAnalytics,
} from "../../services/adminAiAnalyticsService";

import type {
  AdminAiAnalytics,
} from "../../types/adminAiAnalytics";


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


export default function AdminAiAnalyticsPage() {

  const [analytics, setAnalytics] =
    useState<AdminAiAnalytics | null>(
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
          await getAdminAiAnalytics();


        if (active) {

          setAnalytics(data);

        }


      } catch (error) {

        console.error(error);


        if (active) {

          setError(
            "Unable to load AI analytics."
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

        Loading AI analytics...

      </div>

    );
  }


  if (error || !analytics) {

    return (

      <div className="rounded-xl border border-red-500/30 bg-red-500/10 p-5 text-red-500">

        {error || "AI analytics are unavailable."}

      </div>

    );
  }


  const statCards = [

    {
      title: "AI Analyses",
      value: analytics.totalAnalyses,
      description: "Submission analyses generated",
      icon: FiCpu,
    },

    {
      title: "Chat Messages",
      value: analytics.totalChatMessages,
      description: "AI mentor conversations",
      icon: FiMessageSquare,
    },

    {
      title: "Progressive Hints",
      value: analytics.totalProgressiveHints,
      description: "Hints requested by users",
      icon: FiZap,
    },

    {
      title: "Mistakes Detected",
      value: analytics.totalMistakesDetected,
      description: "Mistakes stored by AI",
      icon: FiAlertTriangle,
    },

    {
      title: "Users With Mistakes",
      value: analytics.usersWithMistakes,
      description: "Users with mistake memory",
      icon: FiUsers,
    },

  ];


  return (

    <div className="mx-auto w-full max-w-7xl">


      {/* PAGE HEADER */}

      <div>

        <p className="text-sm font-semibold uppercase tracking-wider text-blue-500">

          AI Intelligence

        </p>


        <h1 className="mt-2 text-3xl font-bold tracking-tight">

          AI Analytics

        </h1>


        <p className="app-text-secondary mt-2 max-w-3xl">

          Monitor AI mentor usage, progressive hints,
          detected mistakes, and developer learning patterns.

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



      {/* KEY INSIGHTS */}

      <section className="app-surface app-border mt-7 rounded-2xl border">


        <div className="app-border border-b px-6 py-5">

          <div className="flex items-center gap-2">

            <FiTrendingUp className="text-blue-500" />

            <h2 className="text-lg font-semibold">

              Key AI Insights

            </h2>

          </div>


          <p className="app-text-secondary mt-1 text-sm">

            High-level learning patterns detected across the platform.

          </p>

        </div>


        <div className="grid gap-4 p-6 md:grid-cols-2">


          <div className="app-surface-secondary rounded-xl p-5">

            <div className="flex items-center gap-2">

              <FiAlertTriangle className="text-orange-500" />

              <p className="app-text-secondary text-sm font-medium">

                Most Common Mistake

              </p>

            </div>


            <p className="mt-3 text-xl font-bold">

              {analytics.mostCommonMistakeType
                ? formatLabel(
                    analytics.mostCommonMistakeType
                  )
                : "No Data"}

            </p>

          </div>



          <div className="app-surface-secondary rounded-xl p-5">

            <div className="flex items-center gap-2">

              <FiActivity className="text-blue-500" />

              <p className="app-text-secondary text-sm font-medium">

                Most Affected Concept

              </p>

            </div>


            <p className="mt-3 text-xl font-bold">

              {analytics.mostCommonConcept
                ? formatLabel(
                    analytics.mostCommonConcept
                  )
                : "No Data"}

            </p>

          </div>


        </div>


      </section>



      {/* DISTRIBUTIONS */}

      <div className="mt-7 grid gap-6 xl:grid-cols-2">


        <DistributionSection
          title="Mistake Type Distribution"
          description="Frequency of mistake categories detected by the AI mentor."
          data={
            analytics.mistakeTypeDistribution
          }
          emptyMessage="No mistake type data available."
        />


        <DistributionSection
          title="Severity Distribution"
          description="Distribution of detected mistakes by severity level."
          data={
            analytics.severityDistribution
          }
          emptyMessage="No severity data available."
        />


      </div>



      <div className="mt-6">


        <DistributionSection
          title="Concept Distribution"
          description="Programming concepts where the AI mentor detects the most mistakes."
          data={
            analytics.conceptDistribution
          }
          emptyMessage="No concept data available."
        />


      </div>



      {/* PLATFORM NOTE */}

      <section className="app-surface app-border mt-7 rounded-2xl border p-6">

        <div className="flex items-start gap-4">

          <div className="app-surface-secondary flex h-11 w-11 shrink-0 items-center justify-center rounded-xl">

            <FiBarChart2
              className="text-blue-500"
              size={20}
            />

          </div>


          <div>

            <h2 className="font-semibold">

              Platform AI Intelligence

            </h2>


            <p className="app-text-secondary mt-1 max-w-3xl text-sm leading-6">

              These analytics are generated from real AI mentor
              interactions, progressive hints, submission analyses,
              and mistake memory records stored across CodeMentor AI.

            </p>

          </div>

        </div>

      </section>


    </div>

  );
}