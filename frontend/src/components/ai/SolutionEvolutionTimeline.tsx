import { useEffect, useState } from "react";

import {
  FiAlertTriangle,
  FiCheckCircle,
  FiChevronDown,
  FiChevronUp,
  FiCode,
  FiMinusCircle,
  FiTrendingDown,
  FiTrendingUp,
} from "react-icons/fi";

import {
  getSolutionEvolution,
} from "../../services/aiMentorService";

import type {
  EvolutionStatus,
  SolutionEvolutionAttempt,
} from "../../types/solutionEvolution";


interface SolutionEvolutionTimelineProps {
  problemId: number;
}


export default function SolutionEvolutionTimeline({
  problemId,
}: SolutionEvolutionTimelineProps) {

  const [attempts, setAttempts] =
    useState<SolutionEvolutionAttempt[]>([]);

  const [loading, setLoading] =
    useState(true);

  const [error, setError] =
    useState<string | null>(null);

  const [expandedAttempt, setExpandedAttempt] =
    useState<number | null>(null);


  useEffect(() => {

    const loadTimeline = async () => {

      try {

        setLoading(true);
        setError(null);

        const data =
          await getSolutionEvolution(problemId);

        setAttempts(data);

      } catch (err) {

        console.error(
          "Failed to load solution evolution:",
          err
        );

        setError(
          "Unable to load your solution evolution."
        );

      } finally {

        setLoading(false);

      }

    };


    loadTimeline();

  }, [problemId]);


  const toggleAttempt = (
    submissionId: number
  ) => {

    setExpandedAttempt(
      (current) =>
        current === submissionId
          ? null
          : submissionId
    );

  };


  const getEvolutionConfig = (
    status: EvolutionStatus
  ) => {

    switch (status) {

      case "SOLVED":
        return {
          icon: FiCheckCircle,
          label: "Solved",
          className:
            "border-emerald-500/30 bg-emerald-500/10 text-emerald-500",
        };

      case "IMPROVED":
        return {
          icon: FiTrendingUp,
          label: "Improved",
          className:
            "border-blue-500/30 bg-blue-500/10 text-blue-500",
        };

      case "REGRESSED":
        return {
          icon: FiTrendingDown,
          label: "Regressed",
          className:
            "border-red-500/30 bg-red-500/10 text-red-500",
        };

      case "STATUS_CHANGED":
        return {
          icon: FiAlertTriangle,
          label: "Status Changed",
          className:
            "border-amber-500/30 bg-amber-500/10 text-amber-500",
        };

      case "NO_CHANGE":
        return {
          icon: FiMinusCircle,
          label: "No Change",
          className:
            "app-border app-surface-muted app-text-secondary",
        };

      default:
        return {
          icon: FiCode,
          label: "First Attempt",
          className:
            "border-violet-500/30 bg-violet-500/10 text-violet-500",
        };
    }

  };


  if (loading) {

    return (
      <section className="app-surface app-border rounded-xl border p-6">

        <div className="animate-pulse space-y-4">

          <div className="app-surface-muted h-6 w-56 rounded" />

          <div className="app-surface-muted h-20 rounded-lg" />

          <div className="app-surface-muted h-20 rounded-lg" />

          <div className="app-surface-muted h-20 rounded-lg" />

        </div>

      </section>
    );

  }


  if (error) {

    return (
      <section className="app-surface app-border rounded-xl border p-6">

        <p className="text-sm text-red-500">
          {error}
        </p>

      </section>
    );

  }


  if (attempts.length === 0) {

    return (
      <section className="app-surface app-border rounded-xl border p-6">

        <h2 className="text-lg font-semibold">
          Solution Evolution
        </h2>

        <p className="app-text-secondary mt-2 text-sm">
          Submit solutions to start building your evolution timeline.
        </p>

      </section>
    );

  }


  return (

    <section className="app-surface app-border rounded-xl border p-5 sm:p-6">


      {/* ==========================================
          HEADER
      ========================================== */}

      <div className="mb-6">

        <div className="flex items-center gap-3">

          <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-blue-500/10 text-blue-500">

            <FiTrendingUp size={19} />

          </div>


          <div>

            <h2 className="text-lg font-semibold">
              Solution Evolution
            </h2>

            <p className="app-text-secondary mt-0.5 text-sm">
              See how your solution changed across attempts.
            </p>

          </div>

        </div>


        <div className="app-surface-muted mt-5 flex flex-wrap items-center gap-4 rounded-lg px-4 py-3 text-sm">

          <span className="app-text-secondary">
            Total Attempts
            <strong className="app-text-primary ml-2">
              {attempts.length}
            </strong>
          </span>


          <span className="app-text-secondary">
            Improvements
            <strong className="ml-2 text-blue-500">
              {
                attempts.filter(
                  (attempt) =>
                    attempt.improvedFromPreviousAttempt
                ).length
              }
            </strong>
          </span>


          <span className="app-text-secondary">
            Best Result
            <strong className="ml-2 text-emerald-500">
              {
                attempts.some(
                  (attempt) =>
                    attempt.status === "ACCEPTED"
                )
                  ? "Solved"
                  : "In Progress"
              }
            </strong>
          </span>

        </div>

      </div>


      {/* ==========================================
          TIMELINE
      ========================================== */}

      <div className="relative">


        <div className="app-border absolute bottom-5 left-[17px] top-5 border-l" />


        <div className="space-y-4">

          {
            attempts.map((attempt) => {

              const config =
                getEvolutionConfig(
                  attempt.evolutionStatus
                );

              const Icon =
                config.icon;

              const isExpanded =
                expandedAttempt ===
                attempt.submissionId;


              return (

                <div
                  key={attempt.submissionId}
                  className="relative pl-11"
                >


                  {/* TIMELINE ICON */}

                  <div
                    className={`
                      absolute
                      left-0
                      top-4
                      z-10
                      flex
                      h-9
                      w-9
                      items-center
                      justify-center
                      rounded-full
                      border
                      ${config.className}
                    `}
                  >

                    <Icon size={16} />

                  </div>


                  {/* ATTEMPT CARD */}

                  <div className="app-border overflow-hidden rounded-xl border">


                    <button

                      type="button"

                      onClick={() =>
                        toggleAttempt(
                          attempt.submissionId
                        )
                      }

                      className="app-hover flex w-full items-center justify-between gap-4 p-4 text-left"

                    >


                      <div className="min-w-0">


                        <div className="flex flex-wrap items-center gap-2">


                          <span className="font-semibold">

                            Attempt {attempt.attemptNumber}

                          </span>


                          <span
                            className={`
                              rounded-full
                              border
                              px-2.5
                              py-1
                              text-xs
                              font-medium
                              ${config.className}
                            `}
                          >

                            {config.label}

                          </span>


                          <span className="app-text-secondary text-xs">

                            {attempt.language}

                          </span>


                        </div>


                        <p className="app-text-secondary mt-2 text-sm">

                          {attempt.evolutionMessage}

                        </p>


                        <div className="app-text-secondary mt-3 flex flex-wrap gap-x-5 gap-y-2 text-xs">


                          <span>

                            Tests{" "}

                            <strong className="app-text-primary">

                              {attempt.passedTestCases ?? 0}

                              /

                              {attempt.totalTestCases ?? 0}

                            </strong>

                          </span>


                          <span>

                            Change{" "}

                            <strong
                              className={
                                attempt.passedTestCasesChange > 0

                                  ? "text-emerald-500"

                                  : attempt.passedTestCasesChange < 0

                                    ? "text-red-500"

                                    : "app-text-primary"
                              }
                            >

                              {
                                attempt.passedTestCasesChange > 0
                                  ? "+"
                                  : ""
                              }

                              {attempt.passedTestCasesChange}

                            </strong>

                          </span>


                          <span>

                            Status{" "}

                            <strong className="app-text-primary">

                              {
                                attempt.status
                                  .replaceAll("_", " ")
                              }

                            </strong>

                          </span>


                          <span>

                            {
                              new Date(
                                attempt.createdAt
                              ).toLocaleString()
                            }

                          </span>


                        </div>


                      </div>


                      <div className="app-text-secondary shrink-0">

                        {
                          isExpanded
                            ? (
                              <FiChevronUp size={19} />
                            )
                            : (
                              <FiChevronDown size={19} />
                            )
                        }

                      </div>


                    </button>


                    {/* ==========================================
                        EXPANDED CONTENT
                    ========================================== */}

                    {
                      isExpanded && (

                        <div className="app-border border-t p-4">


                          <div className="grid gap-4 lg:grid-cols-2">


                            {/* SOURCE CODE */}

                            <div>

                              <h3 className="mb-2 text-sm font-semibold">

                                Submitted Code

                              </h3>


                              <pre className="max-h-80 overflow-auto rounded-lg bg-black/90 p-4 text-xs text-white">

                                <code>

                                  {attempt.sourceCode}

                                </code>

                              </pre>

                            </div>


                            {/* AI LEARNING INSIGHT */}

                            <div>

                              <h3 className="mb-2 text-sm font-semibold">

                                AI Learning Insight

                              </h3>


                              <div className="app-surface-muted min-h-32 rounded-lg p-4">


                                {
                                  attempt.conceptToStudy && (

                                    <div className="mb-4">

                                      <p className="app-text-secondary text-xs font-medium uppercase tracking-wide">

                                        Concept to Study

                                      </p>

                                      <p className="mt-1 text-sm font-medium">

                                        {attempt.conceptToStudy}

                                      </p>

                                    </div>

                                  )
                                }


                                {
                                  attempt.aiExplanation
                                    ? (

                                      <p className="app-text-secondary whitespace-pre-wrap text-sm leading-6">

                                        {attempt.aiExplanation}

                                      </p>

                                    )
                                    : (

                                      <p className="app-text-secondary text-sm">

                                        No AI analysis was generated for this attempt.

                                      </p>

                                    )
                                }


                              </div>

                            </div>


                          </div>


                        </div>

                      )
                    }


                  </div>


                </div>

              );

            })
          }

        </div>


      </div>


    </section>

  );

}