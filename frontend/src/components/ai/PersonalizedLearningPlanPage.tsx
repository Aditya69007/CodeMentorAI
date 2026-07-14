import {
  useEffect,
  useState,
} from "react";

import {
  useNavigate,
} from "react-router-dom";

import {
  FiActivity,
  FiAlertTriangle,
  FiArrowRight,
  FiBookOpen,
  FiCheckCircle,
  FiCpu,
  FiRefreshCw,
  FiShield,
  FiTarget,
  FiTrendingUp,
  FiZap,
} from "react-icons/fi";

import {
  getMyPersonalizedLearningPlan,
} from "../../services/aiMentorService";

import type {
  PersonalizedLearningPlanResponse,
} from "../../services/aiMentorService";


export default function PersonalizedLearningPlanPage() {

    const navigate = useNavigate();

  const [
    plan,
    setPlan,
  ] = useState<PersonalizedLearningPlanResponse | null>(
    null
  );

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


  // ==========================================
  // LOAD PERSONALIZED LEARNING PLAN
  // ==========================================

  const loadPlan = async (
    manualRefresh = false
  ) => {

    try {

      if (manualRefresh) {
        setRefreshing(true);
      } else {
        setLoading(true);
      }

      setError("");

      const response =
        await getMyPersonalizedLearningPlan();

      setPlan(response);

    } catch (error) {

      console.error(
        "Failed to load personalized learning plan:",
        error
      );

      setError(
        "Unable to load your personalized learning plan."
      );

    } finally {

      setLoading(false);
      setRefreshing(false);

    }

  };


    useEffect(() => {

    const fetchPlan = async () => {

        try {

        const response =
            await getMyPersonalizedLearningPlan();

        setPlan(response);

        } catch (error) {

        console.error(
            "Failed to load personalized learning plan:",
            error
        );

        setError(
            "Unable to load your personalized learning plan."
        );

        } finally {

        setLoading(false);

        }

    };

    void fetchPlan();

    }, []);


  // ==========================================
  // LOADING
  // ==========================================

  if (loading) {

    return (

      <div className="app-background flex min-h-[calc(100vh-64px)] items-center justify-center">

        <div className="text-center">

          <div
            className="
              mx-auto
              h-9
              w-9
              animate-spin
              rounded-full
              border-2
              border-violet-500
              border-t-transparent
            "
          />

          <p className="app-text-secondary mt-4 text-sm">

            Building your personalized learning plan...

          </p>

        </div>

      </div>

    );

  }


  // ==========================================
  // ERROR
  // ==========================================

  if (error || !plan) {

    return (

      <div className="app-background flex min-h-[calc(100vh-64px)] items-center justify-center p-6">

        <div
          className="
            app-surface
            app-border
            max-w-md
            rounded-xl
            border
            p-6
            text-center
          "
        >

          <FiAlertTriangle
            className="mx-auto text-red-500"
            size={32}
          />

          <h2 className="mt-4 text-lg font-semibold">

            Learning Plan Unavailable

          </h2>

          <p className="app-text-secondary mt-2 text-sm">

            {error}

          </p>

          <button

            type="button"

            onClick={() =>
              loadPlan()
            }

            className="
              mt-5
              rounded-md
              bg-violet-600
              px-4
              py-2
              text-sm
              font-semibold
              text-white
              hover:bg-violet-500
            "

          >

            Try Again

          </button>

        </div>

      </div>

    );

  }



  return (

    <div className="app-background min-h-[calc(100vh-64px)] overflow-y-auto">

      <div className="mx-auto max-w-7xl p-6">


        {/* ==========================================
            PAGE HEADER
        ========================================== */}

        <div
          className="
            flex
            flex-col
            gap-4
            md:flex-row
            md:items-center
            md:justify-between
          "
        >

          <div>

            <div className="flex items-center gap-3">

              <div
                className="
                  flex
                  h-11
                  w-11
                  items-center
                  justify-center
                  rounded-xl
                  bg-violet-500/10
                  text-violet-500
                "
              >

                <FiBookOpen size={22} />

              </div>

              <div>

                <h1 className="text-2xl font-bold">

                  Personalized Learning Plan

                </h1>

                <p className="app-text-muted mt-1 text-sm">

                  Your adaptive roadmap based on real coding performance.

                </p>

              </div>

            </div>

          </div>


          <button

            type="button"

            onClick={() =>
              loadPlan(true)
            }

            disabled={refreshing}

            className="
              app-surface
              app-border
              app-hover
              flex
              items-center
              justify-center
              gap-2
              rounded-md
              border
              px-4
              py-2
              text-sm
              font-semibold
              disabled:opacity-50
            "

          >

            <FiRefreshCw
              className={
                refreshing
                  ? "animate-spin"
                  : ""
              }
            />

            {
              refreshing
                ? "Refreshing..."
                : "Refresh Plan"
            }

          </button>

        </div>


        {/* ==========================================
            HERO SECTION
        ========================================== */}

        <div
          className="
            app-surface
            app-border
            mt-6
            grid
            gap-6
            rounded-xl
            border
            p-6
            lg:grid-cols-[220px_1fr]
          "
        >


          {/* READINESS SCORE */}

          <div className="flex items-center justify-center">

            <div
            className="
                relative
                flex
                h-44
                w-44
                items-center
                justify-center
            "
            >

            {/* BACKGROUND CIRCLE */}

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
                className="text-violet-500/20"
                />


                {/* PROGRESS CIRCLE */}

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
                    100 - plan.overallReadinessScore
                }
                className="
                    text-violet-500
                    transition-all
                    duration-700
                "
                />

            </svg>


            {/* SCORE */}

            <div className="relative flex flex-col items-center justify-center">

                <p className="text-4xl font-bold text-violet-500">

                {plan.overallReadinessScore}

                </p>

                <p className="app-text-muted mt-1 text-xs">

                / 100 Readiness

                </p>

            </div>

            </div>

          </div>


          {/* HERO INFORMATION */}

          <div className="flex flex-col justify-center">

            <div className="flex flex-wrap items-center gap-3">

              <span
                className="
                  rounded-full
                  bg-violet-500/10
                  px-3
                  py-1
                  text-xs
                  font-bold
                  text-violet-500
                "
              >

                {plan.learningLevel}

              </span>

              <span className="app-text-muted text-sm">

                Current Learning Level

              </span>

            </div>


            <h2 className="mt-4 text-xl font-bold">

              Your Current Learning Assessment

            </h2>

            <p className="app-text-secondary mt-3 max-w-3xl text-sm leading-7">

              {plan.message}

            </p>


            <div
              className="
                mt-5
                flex
                items-start
                gap-3
                rounded-lg
                border
                border-violet-500/20
                bg-violet-500/10
                p-4
              "
            >

              <FiZap
                className="mt-0.5 shrink-0 text-violet-500"
                size={18}
              />

              <div>

                <p className="text-sm font-semibold">

                  Recommended Next Action

                </p>

                <p className="app-text-secondary mt-1 text-sm leading-6">

                  {plan.recommendedAction}

                </p>

              </div>

            </div>

          </div>

        </div>


        {/* ==========================================
            PERFORMANCE METRICS
        ========================================== */}

        <div className="mt-6 grid gap-4 md:grid-cols-2 lg:grid-cols-3">


          {/* READINESS */}

          <div
            className="
              app-surface
              app-border
              rounded-xl
              border
              p-5
            "
          >

            <div className="flex items-center justify-between">

              <div>

                <p className="app-text-muted text-xs font-medium">

                  Overall Readiness

                </p>

                <p className="mt-2 text-2xl font-bold">

                  {plan.overallReadinessScore}%

                </p>

              </div>

              <div
                className="
                  flex
                  h-10
                  w-10
                  items-center
                  justify-center
                  rounded-lg
                  bg-blue-500/10
                  text-blue-500
                "
              >

                <FiActivity size={19} />

              </div>

            </div>


            <div className="mt-4 h-2 overflow-hidden rounded-full bg-slate-500/10">

              <div
                className="h-full rounded-full bg-blue-500"
                style={{
                  width: `${plan.overallReadinessScore}%`,
                }}
              />

            </div>

          </div>


          {/* HINT DEPENDENCY */}

          <div
            className="
              app-surface
              app-border
              rounded-xl
              border
              p-5
            "
          >

            <div className="flex items-center justify-between">

              <div>

                <p className="app-text-muted text-xs font-medium">

                  Hint Dependency

                </p>

                <p className="mt-2 text-2xl font-bold">

                  {plan.hintDependencyScore}%

                </p>

              </div>

              <div
                className="
                  flex
                  h-10
                  w-10
                  items-center
                  justify-center
                  rounded-lg
                  bg-amber-500/10
                  text-amber-500
                "
              >

                <FiCpu size={19} />

              </div>

            </div>


            <div className="mt-4 h-2 overflow-hidden rounded-full bg-slate-500/10">

              <div
                className="h-full rounded-full bg-amber-500"
                style={{
                  width: `${plan.hintDependencyScore}%`,
                }}
              />

            </div>

          </div>


          {/* INDEPENDENT SOLVE RATE */}

          <div
            className="
              app-surface
              app-border
              rounded-xl
              border
              p-5
            "
          >

            <div className="flex items-center justify-between">

              <div>

                <p className="app-text-muted text-xs font-medium">

                  Independent Solve Rate

                </p>

                <p className="mt-2 text-2xl font-bold">

                  {plan.independentSolveRate.toFixed(2)}%

                </p>

              </div>

              <div
                className="
                  flex
                  h-10
                  w-10
                  items-center
                  justify-center
                  rounded-lg
                  bg-emerald-500/10
                  text-emerald-500
                "
              >

                <FiShield size={19} />

              </div>

            </div>


            <div className="mt-4 h-2 overflow-hidden rounded-full bg-slate-500/10">

              <div
                className="h-full rounded-full bg-emerald-500"
                style={{
                  width: `${Math.min(
                    plan.independentSolveRate,
                    100
                  )}%`,
                }}
              />

            </div>

          </div>

        </div>


        {/* ==========================================
            LEARNING INSIGHTS
        ========================================== */}

        <div className="mt-6 grid gap-6 lg:grid-cols-2">


          {/* WEAK CONCEPTS */}

          <div
            className="
              app-surface
              app-border
              rounded-xl
              border
              p-5
            "
          >

            <div className="flex items-center gap-2">

              <FiTarget className="text-red-500" />

              <h2 className="font-semibold">

                Concepts to Improve

              </h2>

            </div>


            {
              plan.weakConcepts.length > 0
                ? (

                  <div className="mt-4 space-y-2">

                    {
                      plan.weakConcepts.map(
                        (concept) => (

                          <div
                            key={concept}
                            className="
                              app-surface-secondary
                              app-border
                              flex
                              items-center
                              gap-3
                              rounded-lg
                              border
                              p-3
                            "
                          >

                            <FiAlertTriangle className="shrink-0 text-red-500" />

                            <span className="text-sm font-medium">

                              {concept}

                            </span>

                          </div>

                        )
                      )
                    }

                  </div>

                )
                : (

                  <div className="mt-5 text-center">

                    <FiCheckCircle
                      className="mx-auto text-emerald-500"
                      size={28}
                    />

                    <p className="app-text-secondary mt-3 text-sm">

                      No major weak concepts detected yet.

                    </p>

                  </div>

                )
            }

          </div>


          {/* STRENGTHS */}

          <div
            className="
              app-surface
              app-border
              rounded-xl
              border
              p-5
            "
          >

            <div className="flex items-center gap-2">

              <FiTrendingUp className="text-emerald-500" />

              <h2 className="font-semibold">

                Current Strengths

              </h2>

            </div>


            {
              plan.strengths.length > 0
                ? (

                  <div className="mt-4 space-y-2">

                    {
                      plan.strengths.map(
                        (strength) => (

                          <div
                            key={strength}
                            className="
                              app-surface-secondary
                              app-border
                              flex
                              items-center
                              gap-3
                              rounded-lg
                              border
                              p-3
                            "
                          >

                            <FiCheckCircle className="shrink-0 text-emerald-500" />

                            <span className="text-sm font-medium">

                              {strength}

                            </span>

                          </div>

                        )
                      )
                    }

                  </div>

                )
                : (

                  <div className="mt-5 text-center">

                    <FiTrendingUp
                      className="mx-auto app-text-muted"
                      size={28}
                    />

                    <p className="app-text-secondary mt-3 text-sm">

                      Keep solving problems to identify your strongest concepts.

                    </p>

                  </div>

                )
            }

          </div>

        </div>


        {/* ==========================================
            REVISION PRIORITIES
        ========================================== */}

        <div
          className="
            app-surface
            app-border
            mt-6
            rounded-xl
            border
            p-5
          "
        >

          <div className="flex items-center gap-2">

            <FiBookOpen className="text-violet-500" />

            <h2 className="font-semibold">

              Revision Priorities

            </h2>

          </div>


          {
            plan.revisionPriorities.length > 0
              ? (

                <div className="mt-4 space-y-3">

                  {
                    plan.revisionPriorities.map(
                      (priority, index) => (

                        <div
                          key={`${priority}-${index}`}
                          className="
                            app-surface-secondary
                            app-border
                            flex
                            items-center
                            gap-4
                            rounded-lg
                            border
                            p-4
                          "
                        >

                          <div
                            className="
                              flex
                              h-8
                              w-8
                              shrink-0
                              items-center
                              justify-center
                              rounded-full
                              bg-violet-500/10
                              text-sm
                              font-bold
                              text-violet-500
                            "
                          >

                            {index + 1}

                          </div>

                          <p className="app-text-secondary flex-1 text-sm">

                            {priority}

                          </p>

                          <FiArrowRight className="app-text-muted shrink-0" />

                        </div>

                      )
                    )
                  }

                </div>

              )
              : (

                <div className="mt-5 text-center">

                  <FiCheckCircle
                    className="mx-auto text-emerald-500"
                    size={28}
                  />

                  <p className="app-text-secondary mt-3 text-sm">

                    No urgent revision priorities detected.

                  </p>

                </div>

              )
          }

        </div>

        {/* ==========================================
            RECOMMENDED PROBLEMS
        ========================================== */}

        <div className="app-surface app-border mt-6 rounded-xl border p-6">

        <div className="mb-5">

            <h2 className="text-lg font-semibold">

            Recommended Problems

            </h2>

            <p className="app-text-secondary mt-1 text-sm">

            Problems selected from your current learning progress.

            </p>

        </div>


        {
            plan.recommendedProblems.length > 0
            ? (

                <div className="space-y-3">

                {
                    plan.recommendedProblems.map(
                    (problem) => (

                        <button

                        key={problem.id}

                        type="button"

                        onClick={() =>
                            navigate(
                            `/problems/${problem.id}`
                            )
                        }

                        className="
                            app-surface-secondary
                            app-border
                            app-hover
                            flex
                            w-full
                            items-center
                            justify-between
                            gap-4
                            rounded-lg
                            border
                            p-4
                            text-left
                            transition
                        "

                        >

                        <div className="min-w-0">

                            <p className="truncate font-semibold">

                            {problem.title}

                            </p>

                            <p className="app-text-muted mt-1 text-xs">

                            {problem.reason}

                            </p>

                        </div>


                        <div className="flex shrink-0 items-center gap-3">

                            <span
                            className="
                                rounded-full
                                bg-amber-500/10
                                px-3
                                py-1
                                text-xs
                                font-semibold
                                text-amber-500
                            "
                            >

                            {problem.difficulty}

                            </span>


                            <span className="text-sm font-semibold text-blue-500">

                            Solve →

                            </span>

                        </div>

                        </button>

                    )
                    )
                }

                </div>

            )
            : (

                <p className="app-text-secondary text-sm">

                Keep solving problems to unlock personalized recommendations.

                </p>

            )
        }

        </div>

      </div>

    </div>

  );


}