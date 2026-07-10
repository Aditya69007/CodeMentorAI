import {
  useEffect,
  useState,
} from "react";

import {
  FiChevronLeft,
  FiChevronRight,
  FiClock,
  FiCode,
  FiInbox,
} from "react-icons/fi";

import {
  getMyProblemSubmissions,
} from "../../services/submissionService";

import type {
  SubmissionPage,
} from "../../services/submissionService";

import type {
  SubmissionResponse,
} from "../../types/submission";


interface Props {
  problemId: number;
  refreshKey: number;

  onSelectSubmission: (
    submission: SubmissionResponse
  ) => void;
}


export default function SubmissionHistoryPanel({
  problemId,
  refreshKey,
  onSelectSubmission,
}: Props) {

  const [submissions, setSubmissions] =
    useState<SubmissionResponse[]>([]);

  const [page, setPage] =
    useState(0);

  const [totalPages, setTotalPages] =
    useState(0);

  const [loading, setLoading] =
    useState(true);

  const [error, setError] =
    useState("");


  useEffect(() => {

    const loadSubmissions = async () => {

      try {

        setLoading(true);

        setError("");


        const response: SubmissionPage =
          await getMyProblemSubmissions(
            problemId,
            page,
            10
          );


        const currentProblemSubmissions =
          response.content.filter(
            (submission) =>
              submission.problemId === problemId
          );


        setSubmissions(
          currentProblemSubmissions
        );

        setTotalPages(
          response.totalPages
        );


      } catch (error) {

        console.error(error);

        setError(
          "Unable to load submission history."
        );


      } finally {

        setLoading(false);

      }

    };


    loadSubmissions();

  }, [
    page,
    problemId,
    refreshKey,
  ]);


  const getStatusClass = (
    status: string
  ) => {

    switch (status) {

      case "ACCEPTED":

        return "text-emerald-500";


      case "WRONG_ANSWER":

        return "text-red-500";


      case "COMPILATION_ERROR":

        return "text-orange-500";


      case "TIME_LIMIT_EXCEEDED":

        return "text-amber-500";


      case "RUNTIME_ERROR":

        return "text-purple-500";


      default:

        return "app-text-secondary";

    }

  };


  if (loading) {

    return (

      <div className="flex h-full items-center justify-center">

        <div className="text-center">

          <div className="mx-auto h-7 w-7 animate-spin rounded-full border-2 border-blue-500 border-t-transparent" />

          <p className="app-text-secondary mt-3 text-sm">

            Loading submissions...

          </p>

        </div>

      </div>

    );

  }


  if (error) {

    return (

      <div className="flex h-full items-center justify-center p-6 text-center">

        <p className="text-sm text-red-500">

          {error}

        </p>

      </div>

    );

  }


  return (

    <div className="flex h-full min-h-0 flex-col">


      {/* SUBMISSION LIST */}

      <div className="min-h-0 flex-1 overflow-y-auto">


        {
          submissions.length === 0
            ? (

              <div className="flex h-full flex-col items-center justify-center p-6 text-center">

                <FiInbox
                  size={28}
                  className="app-text-muted"
                />


                <p className="mt-3 font-semibold">

                  No submissions

                </p>


                <p className="app-text-secondary mt-1 text-sm">

                  Submit your solution to see it here.

                </p>

              </div>

            )
            : (

              <div>


                {
                  submissions.map(
                    (submission) => (

                      <button

                        type="button"

                        key={submission.id}

                        onClick={() =>
                          onSelectSubmission(
                            submission
                          )
                        }

                        className="
                          app-border
                          app-hover
                          w-full
                          cursor-pointer
                          border-b
                          p-4
                          text-left
                        "

                      >


                        <div className="flex items-start justify-between gap-4">


                          <div className="min-w-0">


                            <p
                              className={`text-sm font-semibold ${getStatusClass(
                                submission.status
                              )}`}
                            >

                              {
                                submission.status.replaceAll(
                                  "_",
                                  " "
                                )
                              }

                            </p>


                            <div className="app-text-muted mt-2 flex flex-wrap items-center gap-4 text-xs">


                              <span className="flex items-center gap-1">

                                <FiCode />

                                {
                                  submission.language
                                }

                              </span>


                              <span className="flex items-center gap-1">

                                <FiClock />

                                {
                                  submission.executionTime ??
                                  0
                                } ms

                              </span>


                              <span>

                                {
                                  submission.passedTestCases ??
                                  0
                                }

                                {" / "}

                                {
                                  submission.totalTestCases ??
                                  0
                                }

                                {" tests"}

                              </span>


                            </div>


                          </div>


                          <span className="app-text-muted shrink-0 text-xs">

                            {
                              new Date(
                                submission.createdAt
                              ).toLocaleString()
                            }

                          </span>


                        </div>


                      </button>

                    )
                  )
                }


              </div>

            )
        }


      </div>


      {/* PAGINATION */}

      {
        totalPages > 1 && (

          <div className="app-border flex shrink-0 items-center justify-between border-t p-3">


            <button

              onClick={() =>
                setPage((current) =>
                  Math.max(
                    current - 1,
                    0
                  )
                )
              }

              disabled={page === 0}

              className="app-surface-secondary flex items-center gap-1 rounded-md px-3 py-2 text-xs disabled:cursor-not-allowed disabled:opacity-40"

            >

              <FiChevronLeft />

              Previous

            </button>


            <span className="app-text-secondary text-xs">

              Page {page + 1} of {totalPages}

            </span>


            <button

              onClick={() =>
                setPage((current) =>
                  Math.min(
                    current + 1,
                    totalPages - 1
                  )
                )
              }

              disabled={
                page >=
                totalPages - 1
              }

              className="app-surface-secondary flex items-center gap-1 rounded-md px-3 py-2 text-xs disabled:cursor-not-allowed disabled:opacity-40"

            >

              Next

              <FiChevronRight />

            </button>


          </div>

        )
      }


    </div>

  );

}