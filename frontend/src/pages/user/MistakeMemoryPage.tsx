import {
  useEffect,
  useState,
} from "react";

import { useNavigate } from "react-router-dom";

import {
  FiActivity,
  FiAlertCircle,
  FiAlertTriangle,
  FiArrowRight,
  FiBarChart2,
  FiCheck,
  FiCpu,
  FiRefreshCw,
  FiTarget,
  FiTrendingUp,
} from "react-icons/fi";

import {
  getMyConceptGrowth,
  getMyDeveloperMistakeProfile,
  getMyMistakeSummary,
  getMyRecurringMistakes,
  getMyPracticeRecommendations,
} from "../../services/mistakeMemoryService";

import type {
  ConceptGrowth,
  DeveloperMistakeProfile,
  MistakeSummary,
  RecurringMistake,
  PracticeRecommendation,
} from "../../types/mistakeMemory";


export default function MistakeMemoryPage() {

  const navigate = useNavigate();

  const [profile, setProfile] =
    useState<DeveloperMistakeProfile | null>(null);

  const [summary, setSummary] =
    useState<MistakeSummary[]>([]);

  const [recurringMistakes, setRecurringMistakes] =
    useState<RecurringMistake[]>([]);

    const [conceptGrowth, setConceptGrowth] =
  useState<ConceptGrowth[]>([]);

  const [
    practiceRecommendations,
    setPracticeRecommendations,
  ] = useState<PracticeRecommendation[]>([]);

  const [loading, setLoading] =
    useState(true);

  const [error, setError] =
    useState("");


  // ==================================================
  // LOAD MISTAKE MEMORY
  // ==================================================

  const loadMistakeMemory = async () => {

    try {

      setLoading(true);

      setError("");


    const [
      profileResponse,
      summaryResponse,
      recurringResponse,
      growthResponse,
      recommendationsResponse,
    ] = await Promise.all([

      getMyDeveloperMistakeProfile(),

      getMyMistakeSummary(),

      getMyRecurringMistakes(),

      getMyConceptGrowth(),

      getMyPracticeRecommendations(),

    ]);


      setProfile(profileResponse);

      setSummary(summaryResponse);

      setRecurringMistakes(recurringResponse);

      setConceptGrowth(growthResponse);

      setPracticeRecommendations(
        recommendationsResponse
      );

    } catch (error) {

      console.error(error);

      setError(
        "Unable to load your Mistake Memory."
      );


    } finally {

      setLoading(false);

    }

  };


    useEffect(() => {

    const initializeMistakeMemory = async () => {
        await loadMistakeMemory();
    };

    void initializeMistakeMemory();

    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);


  // ==================================================
  // FORMAT LABEL
  // ==================================================

  const formatLabel = (
    value: string | null
  ) => {

    if (!value) {
      return "Not available";
    }

    return value
      .replaceAll("_", " ")
      .toLowerCase()
      .replace(/\b\w/g, (character) =>
        character.toUpperCase()
      );
  };


  // ==================================================
  // LOADING
  // ==================================================

  if (loading) {

    return (

      <main className="mx-auto w-full max-w-[1300px] px-4 py-8 sm:px-6 lg:py-10">

        <div className="space-y-5">

          <div className="app-surface-secondary h-28 animate-pulse rounded-lg" />

          <div className="grid gap-4 md:grid-cols-3">

            {[1, 2, 3].map((item) => (

              <div
                key={item}
                className="app-surface-secondary h-32 animate-pulse rounded-lg"
              />

            ))}

          </div>


          <div className="grid gap-5 lg:grid-cols-2">

            <div className="app-surface-secondary h-80 animate-pulse rounded-lg" />

            <div className="app-surface-secondary h-80 animate-pulse rounded-lg" />

          </div>

        </div>

      </main>

    );

  }


  // ==================================================
  // ERROR
  // ==================================================

  if (error || !profile) {

    return (

      <main className="mx-auto w-full max-w-[1300px] px-4 py-8 sm:px-6 lg:py-10">

        <div className="flex min-h-96 flex-col items-center justify-center text-center">

          <FiAlertCircle
            size={34}
            className="text-red-500"
          />

          <h2 className="mt-4 text-xl font-bold">

            Mistake Memory unavailable

          </h2>

          <p className="app-text-secondary mt-2 text-sm">

            {error}

          </p>


          <button
            onClick={loadMistakeMemory}
            className="mt-5 flex items-center gap-2 rounded-md bg-blue-600 px-4 py-2 text-sm font-semibold text-white hover:bg-blue-700"
          >

            <FiRefreshCw />

            Try Again

          </button>

        </div>

      </main>

    );

  }


  return (

    <main className="mx-auto w-full max-w-[1300px] px-4 py-8 sm:px-6 lg:py-10">


      {/* ==================================================
          HEADER
      ================================================== */}

      <section className="flex flex-col gap-6 lg:flex-row lg:items-end lg:justify-between">


        <div>

          <p className="text-sm font-semibold text-blue-600 dark:text-blue-400">

            AI MISTAKE MEMORY

          </p>


          <h1 className="mt-2 text-3xl font-bold tracking-tight sm:text-4xl">

            Developer Mistake Profile

          </h1>


          <p className="app-text-secondary mt-3 max-w-2xl text-sm leading-6 sm:text-base">

            CodeMentor AI remembers your coding mistakes,
            identifies recurring patterns, and helps you
            understand which concepts need the most attention.

          </p>

        </div>


        <button
          onClick={loadMistakeMemory}
          className="app-surface app-hover app-border flex items-center gap-2 rounded-md border px-4 py-2.5 text-sm font-semibold"
        >

          <FiRefreshCw />

          Refresh Memory

        </button>


      </section>



      {/* ==================================================
          MAIN STAT CARDS
      ================================================== */}

      <section className="mt-8 grid gap-4 md:grid-cols-3">


        {/* TOTAL MISTAKES */}

        <div className="app-surface app-border rounded-lg border p-5">

          <div className="flex items-center justify-between">

            <div className="app-surface-secondary flex h-10 w-10 items-center justify-center rounded-lg">

              <FiActivity
                size={19}
                className="text-blue-500"
              />

            </div>

          </div>


          <p className="app-text-secondary mt-5 text-xs font-semibold uppercase tracking-wide">

            Total Mistakes Remembered

          </p>


          <p className="mt-2 text-3xl font-bold">

            {profile.totalMistakes}

          </p>

        </div>



        {/* MOST COMMON MISTAKE */}

        <div className="app-surface app-border rounded-lg border p-5">

          <div className="app-surface-secondary flex h-10 w-10 items-center justify-center rounded-lg">

            <FiAlertTriangle
              size={19}
              className="text-amber-500"
            />

          </div>


          <p className="app-text-secondary mt-5 text-xs font-semibold uppercase tracking-wide">

            Most Common Mistake

          </p>


          <p className="mt-2 text-lg font-bold">

            {formatLabel(
              profile.mostCommonMistake
            )}

          </p>

        </div>



        {/* WEAKEST CONCEPT */}

        <div className="app-surface app-border rounded-lg border p-5">

          <div className="app-surface-secondary flex h-10 w-10 items-center justify-center rounded-lg">

            <FiTarget
              size={19}
              className="text-red-500"
            />

          </div>


          <p className="app-text-secondary mt-5 text-xs font-semibold uppercase tracking-wide">

            Concept Needing Attention

          </p>


          <p className="mt-2 text-lg font-bold">

            {profile.weakestConcept ?? "Not available"}

          </p>

        </div>


      </section>



      {/* ==================================================
          BREAKDOWNS
      ================================================== */}

      <section className="mt-5 grid gap-5 lg:grid-cols-2">


        {/* MISTAKE TYPE BREAKDOWN */}

        <div className="app-surface app-border rounded-lg border">


          <div className="app-border flex items-center gap-3 border-b p-5">

            <FiBarChart2 className="text-blue-500" />

            <div>

              <h2 className="font-semibold">

                Mistake Breakdown

              </h2>

              <p className="app-text-secondary mt-1 text-xs">

                Mistakes detected across your submissions.

              </p>

            </div>

          </div>


          <div className="p-5">


            {summary.length === 0 ? (

              <p className="app-text-secondary py-10 text-center text-sm">

                No mistake data available yet.

              </p>

            ) : (

              <div className="space-y-4">


                {summary.map((item) => {

                  const percentage =
                    profile.totalMistakes > 0
                      ? (
                          item.count /
                          profile.totalMistakes
                        ) * 100
                      : 0;


                  return (

                    <div key={item.mistakeType}>


                      <div className="mb-2 flex items-center justify-between gap-4">

                        <span className="text-sm font-medium">

                          {formatLabel(
                            item.mistakeType
                          )}

                        </span>


                        <span className="app-text-muted text-xs">

                          {item.count}

                        </span>

                      </div>


                      <div className="app-surface-secondary h-2 overflow-hidden rounded-full">

                        <div
                          className="h-full rounded-full bg-blue-500"
                          style={{
                            width:
                              `${percentage}%`,
                          }}
                        />

                      </div>


                    </div>

                  );

                })}


              </div>

            )}


          </div>


        </div>



        {/* CONCEPT BREAKDOWN */}

        <div className="app-surface app-border rounded-lg border">


          <div className="app-border flex items-center gap-3 border-b p-5">

            <FiCpu className="text-purple-500" />

            <div>

              <h2 className="font-semibold">

                Concept Memory

              </h2>

              <p className="app-text-secondary mt-1 text-xs">

                Concepts where CodeMentor AI detected mistakes.

              </p>

            </div>

          </div>


          <div className="p-5">


            {
              Object.keys(
                profile.conceptBreakdown
              ).length === 0
                ? (

                  <p className="app-text-secondary py-10 text-center text-sm">

                    No concept data available yet.

                  </p>

                )
                : (

                  <div className="space-y-3">


                    {
                      Object.entries(
                        profile.conceptBreakdown
                      ).map(
                        ([
                          concept,
                          count,
                        ]) => (

                          <div
                            key={concept}
                            className="app-surface-secondary flex items-center justify-between rounded-md px-4 py-3"
                          >

                            <span className="text-sm font-medium">

                              {concept}

                            </span>


                            <span className="rounded-full bg-blue-500/10 px-2.5 py-1 text-xs font-semibold text-blue-600 dark:text-blue-400">

                              {count}

                            </span>

                          </div>

                        )
                      )
                    }


                  </div>

                )
            }


          </div>


        </div>


      </section>



      {/* ==================================================
          AI INSIGHTS
      ================================================== */}

      <section className="app-surface app-border mt-5 rounded-lg border">


        <div className="app-border flex items-center gap-3 border-b p-5">

          <FiTrendingUp className="text-emerald-500" />


          <div>

            <h2 className="font-semibold">

              Personalized Insights

            </h2>


            <p className="app-text-secondary mt-1 text-xs">

              Insights generated from your coding mistake history.

            </p>

          </div>


        </div>


        <div className="p-5">


          {profile.insights.length === 0 ? (

            <p className="app-text-secondary py-8 text-center text-sm">

              Solve more problems to generate personalized insights.

            </p>

          ) : (

            <div className="grid gap-3 md:grid-cols-2">


              {profile.insights.map(
                (insight, index) => (

                  <div
                    key={`${insight}-${index}`}
                    className="app-surface-secondary flex gap-3 rounded-lg p-4"
                  >

                    <FiCpu
                      size={18}
                      className="mt-0.5 shrink-0 text-blue-500"
                    />


                    <p className="app-text-secondary text-sm leading-6">

                      {insight}

                    </p>


                  </div>

                )
              )}


            </div>

          )}


        </div>


      </section>

      {/* ==================================================
          CONCEPT GROWTH TRACKING
      ================================================== */}

      <section className="app-surface app-border mt-5 rounded-lg border">

        <div className="app-border flex items-center gap-3 border-b p-5">

          <FiTrendingUp className="text-emerald-500" />

          <div>

            <h2 className="font-semibold">
              Concept Growth
            </h2>

            <p className="app-text-secondary mt-1 text-xs">
              Track how your coding skills improve after previous mistakes.
            </p>

          </div>

        </div>


        <div className="p-5">

          {conceptGrowth.length === 0 ? (

            <div className="py-10 text-center">

              <FiActivity
                size={27}
                className="app-text-muted mx-auto"
              />

              <p className="mt-3 font-semibold">
                No growth data available
              </p>

              <p className="app-text-secondary mt-2 text-sm">
                Solve problems and recover from previous mistakes
                to start tracking your improvement.
              </p>

            </div>

          ) : (

            <div className="grid gap-4 md:grid-cols-2">

              {conceptGrowth.map((growth) => {

                const isRepeating =
                  growth.growthStatus === "REPEATING";

                const isImproving =
                  growth.growthStatus === "IMPROVING";

                const isMastered =
                  growth.growthStatus === "MASTERED";


                return (

                  <div
                    key={growth.concept}
                    className="app-surface-secondary rounded-lg p-4"
                  >

                    <div className="flex items-start justify-between gap-4">

                      <div>

                        <p className="font-semibold">
                          {growth.concept}
                        </p>

                        <p className="app-text-secondary mt-2 text-sm leading-6">
                          {growth.message}
                        </p>

                      </div>


                      <span
                        className={`
                          shrink-0 rounded-full px-2.5 py-1
                          text-xs font-semibold

                          ${
                            isRepeating
                              ? "bg-red-500/10 text-red-500"
                              : ""
                          }

                          ${
                            isImproving
                              ? "bg-amber-500/10 text-amber-500"
                              : ""
                          }

                          ${
                            isMastered
                              ? "bg-emerald-500/10 text-emerald-500"
                              : ""
                          }
                        `}
                      >

                        {growth.growthStatus}

                      </span>

                    </div>


                    <div className="mt-4 grid grid-cols-2 gap-3">

                      <div className="app-background rounded-md p-3">

                        <p className="app-text-muted text-xs">
                          Mistakes
                        </p>

                        <p className="mt-1 text-lg font-bold">
                          {growth.totalMistakes}
                        </p>

                      </div>


                      <div className="app-background rounded-md p-3">

                        <p className="app-text-muted text-xs">
                          Successful Recoveries
                        </p>

                        <p className="mt-1 text-lg font-bold">
                          {growth.acceptedSubmissions}
                        </p>

                      </div>

                    </div>

                  </div>

                );

              })}

            </div>

          )}

        </div>

      </section>

      {/* ==================================================
          PERSONALIZED PRACTICE RECOMMENDATIONS
      ================================================== */}

      <section className="app-surface app-border mt-5 rounded-lg border">

        <div className="app-border flex items-center gap-3 border-b p-5">

          <FiTarget className="text-blue-500" />

          <div>
            <h2 className="font-semibold">
              Recommended Practice
            </h2>

            <p className="app-text-secondary mt-1 text-xs">
              Personalized practice recommendations based on your mistake history.
            </p>
          </div>

        </div>


        <div className="p-5">

          {practiceRecommendations.length === 0 ? (

            <div className="py-10 text-center">

              <FiTarget
                size={27}
                className="app-text-muted mx-auto"
              />

              <p className="mt-3 font-semibold">
                No recommendations available
              </p>

              <p className="app-text-secondary mt-2 text-sm">
                Solve more problems to generate personalized practice recommendations.
              </p>

            </div>

          ) : (

            <div className="grid gap-4 md:grid-cols-2">

              {practiceRecommendations.map((item) => {

                const isHigh =
                  item.priority === "HIGH";

                const isMedium =
                  item.priority === "MEDIUM";

                const isLow =
                  item.priority === "LOW";


                return (

                  <div
                    key={item.concept}
                    className="app-surface-secondary rounded-lg p-5"
                  >

                    <div className="flex items-start justify-between gap-4">

                      <div>

                        <p className="font-semibold">
                          {item.concept}
                        </p>

                        <p className="app-text-secondary mt-2 text-sm leading-6">
                          {item.reason}
                        </p>

                      </div>


                      <span
                        className={`
                          shrink-0 rounded-full px-2.5 py-1
                          text-xs font-semibold

                          ${
                            isHigh
                              ? "bg-red-500/10 text-red-500"
                              : ""
                          }

                          ${
                            isMedium
                              ? "bg-amber-500/10 text-amber-500"
                              : ""
                          }

                          ${
                            isLow
                              ? "bg-emerald-500/10 text-emerald-500"
                              : ""
                          }
                        `}
                      >
                        {item.priority} PRIORITY
                      </span>

                    </div>


                    <div className="mt-4 grid grid-cols-3 gap-3">

                      <div className="app-background rounded-md p-3">

                        <p className="app-text-muted text-xs">
                          Mistakes
                        </p>

                        <p className="mt-1 text-lg font-bold">
                          {item.totalMistakes}
                        </p>

                      </div>


                      <div className="app-background rounded-md p-3">

                        <p className="app-text-muted text-xs">
                          Recoveries
                        </p>

                        <p className="mt-1 text-lg font-bold">
                          {item.successfulRecoveries}
                        </p>

                      </div>


                      <div className="app-background rounded-md p-3">

                        <p className="app-text-muted text-xs">
                          Practice
                        </p>

                        <p className="mt-1 text-lg font-bold">
                          {item.recommendedProblemCount}
                        </p>

                      </div>

                    </div>


                    <div className="app-background mt-4 rounded-md p-4">

                      <p className="app-text-muted text-xs font-medium">
                        RECOMMENDATION
                      </p>

                      <p className="mt-2 text-sm font-medium">
                        {item.recommendation}
                      </p>

                    </div>

                  {/* ==================================================
                      REAL RECOMMENDED PROBLEMS
                  ================================================== */}

                  {item.problems?.length > 0 && (

                    <div className="mt-4">

                      <div className="mb-3 flex items-center justify-between">

                        <p className="text-sm font-semibold">
                          Recommended Problems
                        </p>

                        <span className="app-text-muted text-xs">
                          {item.problems.length} problems
                        </span>

                      </div>


                      <div className="space-y-2">

                        {item.problems.map((problem) => {

                          const isEasy =
                            problem.difficulty === "EASY";

                          const isMedium =
                            problem.difficulty === "MEDIUM";

                          const isHard =
                            problem.difficulty === "HARD";


                          return (

                            <div
                              key={problem.id}
                              className="
                                app-background
                                app-border
                                flex
                                items-center
                                justify-between
                                gap-4
                                rounded-md
                                border
                                p-3
                              "
                            >

                              <div className="min-w-0">

                                <div className="flex items-center gap-2">

                                  {problem.solved && (

                                    <div className="flex h-5 w-5 shrink-0 items-center justify-center rounded-full bg-emerald-500/10">

                                      <FiCheck
                                        size={13}
                                        className="text-emerald-500"
                                      />

                                    </div>

                                  )}


                                  <p className="truncate text-sm font-semibold">

                                    {problem.title}

                                  </p>

                                </div>


                                <div className="mt-2 flex items-center gap-2">

                                  <span
                                    className={`
                                      rounded-full
                                      px-2
                                      py-0.5
                                      text-xs
                                      font-semibold

                                      ${
                                        isEasy
                                          ? "bg-emerald-500/10 text-emerald-500"
                                          : ""
                                      }

                                      ${
                                        isMedium
                                          ? "bg-amber-500/10 text-amber-500"
                                          : ""
                                      }

                                      ${
                                        isHard
                                          ? "bg-red-500/10 text-red-500"
                                          : ""
                                      }
                                    `}
                                  >
                                    {problem.difficulty}
                                  </span>


                                  {problem.solved && (

                                    <span className="text-xs font-medium text-emerald-500">

                                      Solved

                                    </span>

                                  )}

                                </div>

                              </div>


                              <button
                                type="button"

                                onClick={() =>
                                  navigate(
                                    `/problems/${problem.id}`
                                  )
                                }

                                className="
                                  app-surface
                                  app-hover
                                  app-border
                                  flex
                                  shrink-0
                                  items-center
                                  gap-2
                                  rounded-md
                                  border
                                  px-3
                                  py-2
                                  text-xs
                                  font-semibold
                                "
                              >

                                {problem.solved
                                  ? "Solve Again"
                                  : "Solve"}

                                <FiArrowRight size={14} />

                              </button>

                            </div>

                          );

                        })}

                      </div>

                    </div>

                  )}

                  </div>

                );

              })}

            </div>

          )}

        </div>

      </section>

      {/* ==================================================
          RECURRING PATTERNS
      ================================================== */}

      <section className="app-surface app-border mt-5 rounded-lg border">


        <div className="app-border flex items-center gap-3 border-b p-5">

          <FiAlertTriangle className="text-amber-500" />


          <div>

            <h2 className="font-semibold">

              Recurring Mistake Patterns

            </h2>


            <p className="app-text-secondary mt-1 text-xs">

              Mistakes that repeatedly appear in your submissions.

            </p>

          </div>


        </div>


        <div className="p-5">


          {recurringMistakes.length === 0 ? (

            <div className="py-10 text-center">

              <FiTarget
                size={27}
                className="app-text-muted mx-auto"
              />


              <p className="mt-3 font-semibold">

                No recurring patterns detected

              </p>


              <p className="app-text-secondary mt-2 text-sm">

                CodeMentor AI will identify patterns as
                your Mistake Memory grows.

              </p>

            </div>

          ) : (

            <div className="space-y-3">


              {recurringMistakes.map(
                (mistake) => (

                  <div
                    key={mistake.mistakeType}
                    className="rounded-lg border border-amber-500/30 bg-amber-500/10 p-4"
                  >

                    <div className="flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">


                      <div>

                        <p className="font-semibold">

                          {formatLabel(
                            mistake.mistakeType
                          )}

                        </p>


                        <p className="app-text-secondary mt-1 text-sm">

                          {mistake.message}

                        </p>

                      </div>


                      <div className="shrink-0 text-right">

                        <p className="text-xl font-bold text-amber-500">

                          {mistake.occurrenceCount}×

                        </p>


                        <p className="app-text-muted text-xs">

                          {mistake.affectedProblems} problems

                        </p>

                      </div>


                    </div>

                  </div>

                )
              )}


            </div>

          )}


        </div>


      </section>


    </main>

  );

}