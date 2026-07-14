import {
  useEffect,
  useState,
} from "react";

import {
  useNavigate,
} from "react-router-dom";

import {
  FiAlertTriangle,
  FiBookOpen,
  FiCheckCircle,
  FiRefreshCw,
  FiTrendingUp,
} from "react-icons/fi";

import {
  getMyPersonalizedRevisionPlan,
} from "../../services/aiMentorService";

import type {
  PersonalizedRevisionPlanResponse,
} from "../../services/aiMentorService";


export default function PersonalizedRevisionPlanPage() {

  const navigate = useNavigate();

  const [
    plan,
    setPlan,
  ] = useState<PersonalizedRevisionPlanResponse | null>(
    null
  );

  const [
    loading,
    setLoading,
  ] = useState(true);

  const [
    error,
    setError,
  ] = useState("");


  const loadPlan = async () => {

    try {

      setLoading(true);

      setError("");

      const response =
        await getMyPersonalizedRevisionPlan();

      setPlan(response);

    } catch (error) {

      console.error(error);

      setError(
        "Unable to load your revision plan."
      );

    } finally {

      setLoading(false);

    }

  };


    useEffect(() => {

    const fetchPlan = async () => {

        try {

        setError("");

        const response =
            await getMyPersonalizedRevisionPlan();

        setPlan(response);

        } catch (error) {

        console.error(error);

        setError(
            "Unable to load your revision plan."
        );

        } finally {

        setLoading(false);

        }

    };

    fetchPlan();

    }, []);


  if (loading) {

    return (

      <div className="flex min-h-[calc(100vh-64px)] items-center justify-center">

        <div className="text-center">

          <div className="mx-auto h-8 w-8 animate-spin rounded-full border-2 border-violet-500 border-t-transparent" />

          <p className="app-text-secondary mt-4 text-sm">

            Building your revision plan...

          </p>

        </div>

      </div>

    );

  }


  if (error || !plan) {

    return (

      <div className="flex min-h-[calc(100vh-64px)] items-center justify-center">

        <div className="text-center">

          <p className="text-red-500">

            {error || "Revision plan unavailable."}

          </p>

          <button
            type="button"
            onClick={loadPlan}
            className="mt-4 rounded-md bg-violet-600 px-4 py-2 text-sm font-semibold text-white"
          >

            Try Again

          </button>

        </div>

      </div>

    );

  }


  const revisionScore =
    Math.max(
      0,
      Math.min(
        plan.revisionScore,
        100
      )
    );


  return (

    <main className="mx-auto w-full max-w-7xl px-4 py-8 sm:px-6">


      {/* HEADER */}

      <div className="flex flex-wrap items-center justify-between gap-4">

        <div>

          <h1 className="text-2xl font-bold">

            Personalized Revision Plan

          </h1>

          <p className="app-text-secondary mt-1 text-sm">

            Targeted revision based on your coding mistakes and learning progress.

          </p>

        </div>


        <button
          type="button"
          onClick={loadPlan}
          className="app-surface app-border app-hover flex items-center gap-2 rounded-md border px-4 py-2 text-sm font-semibold"
        >

          <FiRefreshCw />

          Refresh Plan

        </button>

      </div>


      {/* HERO */}

      <div className="app-surface app-border mt-8 grid gap-8 rounded-xl border p-6 lg:grid-cols-[220px_1fr]">


        {/* SCORE */}

        <div className="flex items-center justify-center">

          <div className="relative flex h-44 w-44 items-center justify-center">

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
                  100 - revisionScore
                }
                className="text-violet-500 transition-all duration-700"
              />

            </svg>


            <div className="relative text-center">

              <p className="text-4xl font-bold text-violet-500">

                {revisionScore}

              </p>

              <p className="app-text-muted mt-1 text-xs">

                / 100 Revision Health

              </p>

            </div>

          </div>

        </div>


        {/* INFORMATION */}

        <div className="flex flex-col justify-center">

          <div className="flex flex-wrap items-center gap-3">

            <span className="rounded-full bg-violet-500/10 px-3 py-1 text-xs font-bold text-violet-500">

              {plan.revisionLevel}

            </span>

            <span className="app-text-muted text-sm">

              Current Revision Level

            </span>

          </div>


          <h2 className="mt-4 text-xl font-bold">

            Your Revision Assessment

          </h2>

          <p className="app-text-secondary mt-3 text-sm leading-7">

            {plan.message}

          </p>


          <div className="mt-5 rounded-lg border border-violet-500/20 bg-violet-500/10 p-4">

            <p className="text-sm font-semibold">

              Recommended Next Action

            </p>

            <p className="app-text-secondary mt-1 text-sm">

              {plan.recommendedAction}

            </p>

          </div>

        </div>

      </div>


      {/* CONCEPT SECTIONS */}

      <div className="mt-6 grid gap-4 lg:grid-cols-3">


        <ConceptCard
          title="Urgent Revision"
          icon={<FiAlertTriangle />}
          concepts={plan.urgentConcepts}
          emptyMessage="No urgent concepts detected."
        />


        <ConceptCard
          title="Improving Concepts"
          icon={<FiTrendingUp />}
          concepts={plan.improvingConcepts}
          emptyMessage="No improving concepts detected yet."
        />


        <ConceptCard
          title="Mastered Concepts"
          icon={<FiCheckCircle />}
          concepts={plan.masteredConcepts}
          emptyMessage="Keep practicing to identify mastered concepts."
        />

      </div>


      {/* REVISION PROBLEMS */}

      <section className="app-surface app-border mt-6 rounded-xl border p-6">

        <div className="flex items-center gap-3">

          <FiBookOpen className="text-violet-500" />

          <div>

            <h2 className="font-bold">

              Recommended Revision Problems

            </h2>

            <p className="app-text-muted mt-1 text-xs">

              Problems selected from your learning history.

            </p>

          </div>

        </div>


        {
          plan.revisionProblems.length === 0
            ? (

              <p className="app-text-secondary mt-6 text-sm">

                No targeted revision problems available yet.

              </p>

            )
            : (

              <div className="mt-6 space-y-3">

                {
                  plan.revisionProblems.map(
                    (problem) => (

                      <div
                        key={problem.id}
                        className="app-surface-secondary app-border flex flex-wrap items-center justify-between gap-4 rounded-lg border p-4"
                      >

                        <div>

                          <p className="font-semibold">

                            {problem.title}

                          </p>

                          <p className="app-text-muted mt-1 text-xs">

                            {problem.difficulty}

                          </p>

                        </div>


                        <button
                          type="button"
                          onClick={() =>
                            navigate(
                              `/problems/${problem.id}`
                            )
                          }
                          className="rounded-md bg-violet-600 px-4 py-2 text-sm font-semibold text-white hover:bg-violet-500"
                        >

                          Revise Problem →

                        </button>

                      </div>

                    )
                  )
                }

              </div>

            )
        }

      </section>

    </main>

  );

}


function ConceptCard({
  title,
  icon,
  concepts,
  emptyMessage,
}: {
  title: string;
  icon: React.ReactNode;
  concepts: string[];
  emptyMessage: string;
}) {

  return (

    <div className="app-surface app-border rounded-xl border p-5">

      <div className="flex items-center gap-2 font-semibold">

        <span className="text-violet-500">

          {icon}

        </span>

        {title}

      </div>


      {
        concepts.length === 0
          ? (

            <p className="app-text-muted mt-4 text-sm">

              {emptyMessage}

            </p>

          )
          : (

            <div className="mt-4 space-y-2">

              {
                concepts.map(
                  (concept) => (

                    <div
                      key={concept}
                      className="app-surface-secondary rounded-md px-3 py-2 text-sm"
                    >

                      {concept}

                    </div>

                  )
                )
              }

            </div>

          )
      }

    </div>

  );

}