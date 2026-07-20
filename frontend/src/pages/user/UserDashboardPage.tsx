import {
  useEffect,
  useState,
} from "react";

import {
  useNavigate,
} from "react-router-dom";

import {
  FiActivity,
  FiAlertCircle,
  FiArrowRight,
  FiAward,
  FiBookOpen,
  FiCode,
  FiRefreshCw,
  FiTarget,
  FiTrendingUp,
  FiZap,
} from "react-icons/fi";

import DeveloperActivityCalendar
  from "../../components/activity/DeveloperActivityCalendar";

import {
  getMyDeveloperActivity,
} from "../../services/developerActivityService";

import type {
  DeveloperActivity,
} from "../../types/developerActivity";

import {
  getMyGrowthReport,
  getMyPersonalizedLearningPlan,
  getMyPersonalizedRevisionPlan,
} from "../../services/aiMentorService";

import {
  getInterviewHistory,
} from "../../services/personalizedInterviewService";

import type {
  GrowthReportResponse,
  PersonalizedLearningPlanResponse,
  PersonalizedRevisionPlanResponse,
} from "../../services/aiMentorService";

import type {
  InterviewSessionResponse,
} from "../../services/personalizedInterviewService";

import PageLoader from "../../components/ui/PageLoader";

// =====================================================
// COMPONENT
// =====================================================

export default function UserDashboardPage() {

  const navigate =
    useNavigate();

  const [
    developerActivity,
    setDeveloperActivity,
  ] = useState<DeveloperActivity | null>(null);

  const [
    growthReport,
    setGrowthReport,
  ] = useState<GrowthReportResponse | null>(null);


  const [
    learningPlan,
    setLearningPlan,
  ] = useState<PersonalizedLearningPlanResponse | null>(null);


  const [
    revisionPlan,
    setRevisionPlan,
  ] = useState<PersonalizedRevisionPlanResponse | null>(null);


  const [
    interviewHistory,
    setInterviewHistory,
  ] = useState<InterviewSessionResponse[]>([]);


  const [
    loading,
    setLoading,
  ] = useState(true);


  const [
    refreshing,
    setRefreshing,
  ] = useState(false);


  const [
    error,
    setError,
  ] = useState("");


  // =====================================================
  // LOAD DASHBOARD
  // =====================================================

  const loadDashboard = async (
    showRefreshLoader = false
  ) => {

    try {

      if (showRefreshLoader) {

        setRefreshing(true);

      } else {

        setLoading(true);

      }


      setError("");


        const [
          growthResponse,
          learningResponse,
          revisionResponse,
          interviewResponse,
          activityData,
        ] = await Promise.all([

          getMyGrowthReport(),

          getMyPersonalizedLearningPlan(),

          getMyPersonalizedRevisionPlan(),

          getInterviewHistory(),

          getMyDeveloperActivity()

        ]);


      setGrowthReport(
        growthResponse
      );


      setLearningPlan(
        learningResponse
      );


      setRevisionPlan(
        revisionResponse
      );


      setInterviewHistory(
        interviewResponse ?? []
      );

      setDeveloperActivity(activityData);

    } catch (requestError) {

      console.error(
        "Failed to load user dashboard:",
        requestError
      );


      setError(
        "Unable to load your developer dashboard."
      );

    } finally {

      setLoading(false);

      setRefreshing(false);

    }

  };


  // =====================================================
  // INITIAL LOAD
  // =====================================================

  useEffect(() => {

    const initializeDashboard = async () => {

      await loadDashboard();

    };


    initializeDashboard();

  }, []);


  // =====================================================
  // SAFE VALUES
  // =====================================================

  const overallGrowthScore =
    Math.max(
      0,
      Math.min(
        growthReport?.overallGrowthScore ?? 0,
        100
      )
    );


  const learningReadinessScore =
    Math.max(
      0,
      Math.min(
        learningPlan?.overallReadinessScore ?? 0,
        100
      )
    );


  const revisionScore =
    Math.max(
      0,
      Math.min(
        revisionPlan?.revisionScore ?? 0,
        100
      )
    );


  const hintDependencyScore =
    Math.max(
      0,
      Math.min(
        growthReport?.hintDependencyScore ?? 0,
        100
      )
    );


  const independentSolveRate =
    Math.max(
      0,
      Math.min(
        growthReport?.independentSolveRate ?? 0,
        100
      )
    );


  const completedInterviews =
    interviewHistory.filter(
      (interview) =>
        !interview.active
    );


  const latestInterview =
    completedInterviews[0] ?? null;


  // =====================================================
  // LOADING
  // =====================================================

if (loading) {
  return (
    <PageLoader
      title="Loading Dashboard..."
      subtitle="Preparing your developer insights..."
    />
  );
}


  // =====================================================
  // ERROR
  // =====================================================

  if (
    error
    ||
    !growthReport
    ||
    !learningPlan
    ||
    !revisionPlan
  ) {

    return (

      <main className="flex min-h-[calc(100vh-64px)] items-center justify-center px-4">

        <div className="text-center">

          <FiAlertCircle
            className="mx-auto text-red-500"
            size={34}
          />

          <p className="mt-4 text-red-500">

            {
              error
              ||
              "Developer dashboard data is unavailable."
            }

          </p>


          <button

            type="button"

            onClick={() =>
              loadDashboard()
            }

            className="mt-5 rounded-lg bg-blue-600 px-5 py-2.5 text-sm font-semibold text-white hover:bg-blue-700"

          >

            Try Again

          </button>

        </div>

      </main>

    );

  }


  // =====================================================
  // PAGE
  // =====================================================

  return (

    <main className="mx-auto w-full max-w-[1500px] px-4 py-8 sm:px-6">


      {/* =====================================================
          HEADER
      ===================================================== */}

      <section className="flex flex-col gap-4 lg:flex-row lg:items-center lg:justify-between">


        <div>

          <div className="flex items-center gap-3">

            <FiZap
              className="text-blue-500"
              size={28}
            />

            <h1 className="text-3xl font-bold tracking-tight">

              Developer Dashboard

            </h1>

          </div>


          <p className="app-text-secondary mt-2">

            Your personalized coding growth, learning progress,
            revision health, and interview performance in one place.

          </p>

        </div>


        <button

          type="button"

          onClick={() =>
            loadDashboard(true)
          }

          disabled={refreshing}

          className="app-surface app-border app-hover flex items-center justify-center gap-2 rounded-lg border px-4 py-2.5 text-sm font-semibold"

        >

          <FiRefreshCw
            className={
              refreshing
                ? "animate-spin"
                : ""
            }
          />

          Refresh Dashboard

        </button>


      </section>


      {/* =====================================================
          MAIN HERO
      ===================================================== */}

      <section className="app-surface app-border mt-8 grid gap-8 rounded-xl border p-6 lg:grid-cols-[220px_1fr]">


        {/* GROWTH SCORE */}

        <ScoreCircle
          score={overallGrowthScore}
          label="Growth Score"
        />


        {/* INFORMATION */}

        <div className="flex flex-col justify-center">


          <div className="flex flex-wrap items-center gap-3">


            <span className="rounded-full bg-blue-500/10 px-3 py-1 text-xs font-bold text-blue-500">

              {
                growthReport.developerLevel
              }

            </span>


            <span className="app-text-secondary text-sm">

              Current Developer Level

            </span>


          </div>


          <h2 className="mt-4 text-2xl font-bold">

            Your Developer Growth

          </h2>


          <p className="app-text-secondary mt-3 max-w-4xl leading-7">

            {
              growthReport.growthSummary
            }

          </p>


          <div className="mt-5 rounded-lg border border-blue-500/20 bg-blue-500/10 p-4">


            <p className="text-sm font-semibold">

              Recommended Next Action

            </p>


            <p className="app-text-secondary mt-1 text-sm leading-6">

              {
                growthReport.recommendedNextAction
              }

            </p>


          </div>


        </div>


      </section>


      {/* =====================================================
          SCORE CARDS
      ===================================================== */}

      <section className="mt-6 grid gap-4 sm:grid-cols-2 xl:grid-cols-4">


        <MetricCard
          icon={<FiBookOpen />}
          title="Learning Readiness"
          value={`${learningReadinessScore}/100`}
          description={learningPlan.learningLevel}
          onClick={() =>
            navigate("/learning-plan")
          }
        />


        <MetricCard
          icon={<FiRefreshCw />}
          title="Revision Health"
          value={`${revisionScore}/100`}
          description={revisionPlan.revisionLevel}
          onClick={() =>
            navigate("/revision-plan")
          }
        />


        <MetricCard
          icon={<FiActivity />}
          title="Hint Dependency"
          value={`${hintDependencyScore}/100`}
          description="AI assistance usage"
          onClick={() =>
            navigate("/growth-report")
          }
        />


        <MetricCard
          icon={<FiTarget />}
          title="Independent Solve Rate"
          value={`${independentSolveRate.toFixed(1)}%`}
          description="Solved without AI"
          onClick={() =>
            navigate("/growth-report")
          }
        />


      </section>

      {/* =====================================================
          DEVELOPER ACTIVITY CALENDAR
      ===================================================== */}

      {developerActivity && (

        <div className="mt-6">

          <DeveloperActivityCalendar
            data={developerActivity}
          />

        </div>

      )}

      {/* =====================================================
          LOWER GRID
      ===================================================== */}

      <section className="mt-6 grid gap-6 xl:grid-cols-2">


        {/* =====================================================
            CONCEPT GROWTH
        ===================================================== */}

        <div className="app-surface app-border rounded-xl border p-6">


          <div className="flex items-center justify-between gap-4">


            <div className="flex items-center gap-3">


              <FiTrendingUp
                className="text-emerald-500"
                size={22}
              />


              <div>

                <h2 className="text-lg font-bold">

                  Concept Growth

                </h2>


                <p className="app-text-secondary text-sm">

                  Your latest learning progress.

                </p>

              </div>


            </div>


            <button

              onClick={() =>
                navigate("/growth-report")
              }

              className="app-text-secondary app-hover rounded-md p-2"

              title="Open growth report"

            >

              <FiArrowRight />

            </button>


          </div>


          {
            growthReport.conceptGrowth.length === 0
              ? (

                <EmptyState
                  message="Complete more problems to build your concept growth history."
                />

              )
              : (

                <div className="mt-5 space-y-3">


                  {
                    growthReport.conceptGrowth
                      .slice(0, 5)
                      .map((concept) => (

                        <div

                          key={concept.concept}

                          className="app-background app-border flex items-center justify-between gap-4 rounded-lg border p-4"

                        >


                          <div>


                            <p className="font-semibold">

                              {
                                concept.concept
                              }

                            </p>


                            <p className="app-text-secondary mt-1 text-xs">

                              {
                                concept.totalMistakes
                              }

                              {" mistakes • "}

                              {
                                concept.acceptedSubmissions
                              }

                              {" accepted"}

                            </p>


                          </div>


                          <span className="rounded-full bg-emerald-500/10 px-3 py-1 text-xs font-semibold text-emerald-500">

                            {
                              concept.growthStatus
                            }

                          </span>


                        </div>

                      ))
                  }


                </div>

              )
          }


        </div>


        {/* =====================================================
            INTERVIEW PERFORMANCE
        ===================================================== */}

        <div className="app-surface app-border rounded-xl border p-6">


          <div className="flex items-center justify-between gap-4">


            <div className="flex items-center gap-3">


              <FiAward
                className="text-purple-500"
                size={22}
              />


              <div>

                <h2 className="text-lg font-bold">

                  Interview Performance

                </h2>


                <p className="app-text-secondary text-sm">

                  Your latest AI interview result.

                </p>

              </div>


            </div>


            <button

              onClick={() =>
                navigate("/interview")
              }

              className="app-text-secondary app-hover rounded-md p-2"

              title="Open interviews"

            >

              <FiArrowRight />

            </button>


          </div>


          {
            latestInterview
              ? (

                <div className="mt-6 flex flex-col items-center gap-6 sm:flex-row">


                  <ScoreCircle

                    score={
                      latestInterview.finalScore ?? 0
                    }

                    label="Interview Score"

                    small

                  />


                  <div className="flex-1">


                    <span className="rounded-full bg-purple-500/10 px-3 py-1 text-xs font-semibold text-purple-500">

                      {
                        latestInterview.interviewLevel
                      }

                    </span>


                    <h3 className="mt-4 text-xl font-bold">

                      Latest Interview Complete

                    </h3>


                    <p className="app-text-secondary mt-2 text-sm leading-6">

                      {
                        latestInterview.finalFeedback
                        ||
                        "Review your complete AI interview assessment."
                      }

                    </p>


                    <button

                      type="button"

                      onClick={() =>
                        navigate("/interview")
                      }

                      className="mt-4 flex items-center gap-2 text-sm font-semibold text-purple-500"

                    >

                      View Assessment

                      <FiArrowRight />

                    </button>


                  </div>


                </div>

              )
              : (

                <EmptyState
                  message="Complete your first personalized interview to see performance insights."
                />

              )
          }


        </div>


        {/* =====================================================
            RECURRING MISTAKES
        ===================================================== */}

        <div className="app-surface app-border rounded-xl border p-6">


          <div className="flex items-center gap-3">


            <FiAlertCircle
              className="text-amber-500"
              size={22}
            />


            <div>

              <h2 className="text-lg font-bold">

                Recurring Mistakes

              </h2>


              <p className="app-text-secondary text-sm">

                Patterns detected from your coding history.

              </p>

            </div>


          </div>


          {
            growthReport.recurringMistakes.length === 0
              ? (

                <EmptyState
                  message="No recurring mistake patterns detected."
                />

              )
              : (

                <div className="mt-5 flex flex-wrap gap-2">


                  {
                    growthReport.recurringMistakes.map(
                      (mistake) => (

                        <span

                          key={mistake}

                          className="rounded-full bg-amber-500/10 px-3 py-2 text-sm font-medium text-amber-500"

                        >

                          {
                            mistake.replaceAll(
                              "_",
                              " "
                            )
                          }

                        </span>

                      )
                    )
                  }


                </div>

              )
          }


        </div>


        {/* =====================================================
            ACHIEVEMENTS
        ===================================================== */}

        <div className="app-surface app-border rounded-xl border p-6">


          <div className="flex items-center gap-3">


            <FiAward
              className="text-yellow-500"
              size={22}
            />


            <div>

              <h2 className="text-lg font-bold">

                Achievements

              </h2>


              <p className="app-text-secondary text-sm">

                Milestones earned from your progress.

              </p>

            </div>


          </div>


          {
            growthReport.achievements.length === 0
              ? (

                <EmptyState
                  message="Continue practicing to unlock achievements."
                />

              )
              : (

                <div className="mt-5 space-y-3">


                  {
                    growthReport.achievements.map(
                      (achievement) => (

                        <div

                          key={achievement}

                          className="app-background app-border flex items-center gap-3 rounded-lg border p-4"

                        >

                          <FiAward
                            className="shrink-0 text-yellow-500"
                          />


                          <span className="font-medium">

                            {
                              achievement
                            }

                          </span>


                        </div>

                      )
                    )
                  }


                </div>

              )
          }


        </div>


      </section>


      {/* =====================================================
          QUICK ACTIONS
      ===================================================== */}

      <section className="app-surface app-border mt-6 rounded-xl border p-6">


        <div className="flex items-center gap-3">


          <FiCode
            className="text-blue-500"
            size={22}
          />


          <div>

            <h2 className="text-lg font-bold">

              Continue Your Growth

            </h2>


            <p className="app-text-secondary text-sm">

              Choose your next development activity.

            </p>

          </div>


        </div>


        <div className="mt-5 grid gap-3 sm:grid-cols-2 lg:grid-cols-4">


          <QuickAction
            title="Solve Problems"
            description="Practice coding challenges."
            onClick={() =>
              navigate("/problems")
            }
          />


          <QuickAction
            title="Learning Plan"
            description="Follow your AI learning path."
            onClick={() =>
              navigate("/learning-plan")
            }
          />


          <QuickAction
            title="Revision Plan"
            description="Review weak concepts."
            onClick={() =>
              navigate("/revision-plan")
            }
          />


          <QuickAction
            title="AI Interview"
            description="Test your technical readiness."
            onClick={() =>
              navigate("/interview")
            }
          />


        </div>


      </section>


    </main>

  );

}


// =====================================================
// SCORE CIRCLE
// =====================================================

function ScoreCircle({
  score,
  label,
  small = false,
}: {
  score: number;
  label: string;
  small?: boolean;
}) {

  const safeScore =
    Math.max(
      0,
      Math.min(
        score,
        100
      )
    );


  return (

    <div className="flex items-center justify-center">


      <div
        className={`
          relative
          flex
          items-center
          justify-center
          ${
            small
              ? "h-36 w-36"
              : "h-44 w-44"
          }
        `}
      >


        <svg
          className="absolute h-full w-full -rotate-90"
          viewBox="0 0 100 100"
        >


          <circle
            cx="50"
            cy="50"
            r="44"
            fill="none"
            stroke="currentColor"
            strokeWidth="7"
            className="text-blue-500/20"
          />


          <circle
            cx="50"
            cy="50"
            r="44"
            fill="none"
            stroke="currentColor"
            strokeWidth="7"
            strokeLinecap="round"
            pathLength="100"
            strokeDasharray="100"
            strokeDashoffset={
              100 - safeScore
            }
            className="text-blue-500 transition-all duration-700"
          />


        </svg>


        <div className="relative text-center">


          <p
            className={
              small
                ? "text-3xl font-bold text-blue-500"
                : "text-4xl font-bold text-blue-500"
            }
          >

            {
              Math.round(
                safeScore
              )
            }

          </p>


          <p className="app-text-secondary mt-1 text-xs">

            / 100

          </p>


          <p className="app-text-secondary mt-1 text-[11px]">

            {
              label
            }

          </p>


        </div>


      </div>


    </div>

  );

}


// =====================================================
// METRIC CARD
// =====================================================

function MetricCard({
  icon,
  title,
  value,
  description,
  onClick,
}: {
  icon: React.ReactNode;
  title: string;
  value: string;
  description: string;
  onClick: () => void;
}) {

  return (

    <button

      type="button"

      onClick={onClick}

      className="app-surface app-border app-hover rounded-xl border p-5 text-left"

    >


      <div className="flex items-start justify-between gap-4">


        <div className="rounded-lg bg-blue-500/10 p-3 text-blue-500">

          {
            icon
          }

        </div>


        <FiArrowRight className="app-text-secondary" />


      </div>


      <p className="app-text-secondary mt-5 text-sm">

        {
          title
        }

      </p>


      <p className="mt-1 text-2xl font-bold">

        {
          value
        }

      </p>


      <p className="app-text-secondary mt-1 text-xs">

        {
          description
        }

      </p>


    </button>

  );

}


// =====================================================
// QUICK ACTION
// =====================================================

function QuickAction({
  title,
  description,
  onClick,
}: {
  title: string;
  description: string;
  onClick: () => void;
}) {

  return (

    <button

      type="button"

      onClick={onClick}

      className="app-background app-border app-hover flex items-center justify-between gap-4 rounded-lg border p-4 text-left"

    >


      <div>


        <p className="font-semibold">

          {
            title
          }

        </p>


        <p className="app-text-secondary mt-1 text-xs">

          {
            description
          }

        </p>


      </div>


      <FiArrowRight className="app-text-secondary shrink-0" />


    </button>

  );

}


// =====================================================
// EMPTY STATE
// =====================================================

function EmptyState({
  message,
}: {
  message: string;
}) {

  return (

    <div className="app-background mt-5 rounded-lg p-6 text-center">


      <p className="app-text-secondary text-sm">

        {
          message
        }

      </p>


    </div>

  );

}