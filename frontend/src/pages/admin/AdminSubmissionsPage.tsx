import {
  useCallback,
  useEffect,
  useState,
} from "react";

import {
  FiChevronLeft,
  FiChevronRight,
  FiEye,
  FiSearch,
  FiX,
} from "react-icons/fi";

import {
  getAdminSubmissionDetails,
  getAdminSubmissions,
} from "../../services/submissionService";

import AdminSubmissionDetailsDrawer
  from "../../components/admin/submissions/AdminSubmissionDetailsDrawer";

import type {
  AdminSubmission,
  AdminSubmissionDetails,
  AdminSubmissionPage,
  SubmissionLanguage,
  SubmissionStatus,
} from "../../types/submission";


const PAGE_SIZE = 10;


const STATUS_OPTIONS: SubmissionStatus[] = [
  "PENDING",
  "RUNNING",
  "ACCEPTED",
  "WRONG_ANSWER",
  "TIME_LIMIT_EXCEEDED",
  "RUNTIME_ERROR",
  "COMPILATION_ERROR",
];


const LANGUAGE_OPTIONS: SubmissionLanguage[] = [
  "CPP",
  "JAVA",
  "PYTHON",
  "JAVASCRIPT",
];


const formatStatus = (
  status: SubmissionStatus
) => {

  return status
    .replaceAll("_", " ")
    .toLowerCase()
    .replace(
      /\b\w/g,
      (character) =>
        character.toUpperCase()
    );
};


const formatDate = (
  date: string
) => {

  return new Date(date)
    .toLocaleString();
};


const getStatusClass = (
  status: SubmissionStatus
) => {

  switch (status) {

    case "ACCEPTED":
      return "bg-emerald-500/10 text-emerald-500";

    case "WRONG_ANSWER":
      return "bg-red-500/10 text-red-500";

    case "COMPILATION_ERROR":
      return "bg-orange-500/10 text-orange-500";

    case "RUNTIME_ERROR":
      return "bg-rose-500/10 text-rose-500";

    case "TIME_LIMIT_EXCEEDED":
      return "bg-yellow-500/10 text-yellow-500";

    case "RUNNING":
      return "bg-blue-500/10 text-blue-500";

    default:
      return "app-surface-secondary app-text-secondary";
  }
};


export default function AdminSubmissionsPage() {

  const [submissions, setSubmissions] =
    useState<AdminSubmission[]>([]);

  const [pageData, setPageData] =
    useState<AdminSubmissionPage | null>(null);


  const [searchInput, setSearchInput] =
    useState("");

  const [search, setSearch] =
    useState("");

  const [status, setStatus] =
    useState<SubmissionStatus | "">("");

  const [language, setLanguage] =
    useState<SubmissionLanguage | "">("");

  const [page, setPage] =
    useState(0);


  const [loading, setLoading] =
    useState(true);

  const [error, setError] =
    useState("");


  const [
    selectedSubmission,
    setSelectedSubmission,
  ] =
    useState<AdminSubmissionDetails | null>(
      null
    );

  const [detailsOpen, setDetailsOpen] =
    useState(false);

  const [
    detailsLoading,
    setDetailsLoading,
  ] =
    useState(false);

  const [
    detailsError,
    setDetailsError,
  ] =
    useState("");


  // ==========================================
  // LOAD SUBMISSIONS
  // ==========================================

  const loadSubmissions =
    useCallback(async () => {

      try {

        setLoading(true);

        setError("");


        const data =
          await getAdminSubmissions({

            search,

            status,

            language,

            page,

            size: PAGE_SIZE,

          });


        setSubmissions(
          data.content
        );

        setPageData(data);


      } catch (error) {

        console.error(error);

        setError(
          "Unable to load submissions."
        );


      } finally {

        setLoading(false);

      }

    }, [
      search,
      status,
      language,
      page,
    ]);


  useEffect(() => {

    const timeoutId =
      window.setTimeout(() => {

        void loadSubmissions();

      }, 0);


    return () => {

      window.clearTimeout(
        timeoutId
      );

    };

  }, [loadSubmissions]);


  // ==========================================
  // SEARCH
  // ==========================================

  const handleSearch = () => {

    setPage(0);

    setSearch(
      searchInput.trim()
    );
  };


  // ==========================================
  // CLEAR FILTERS
  // ==========================================

  const handleClear = () => {

    setSearchInput("");

    setSearch("");

    setStatus("");

    setLanguage("");

    setPage(0);
  };


  // ==========================================
  // STATUS FILTER
  // ==========================================

  const handleStatusChange = (
    value: SubmissionStatus | ""
  ) => {

    setStatus(value);

    setPage(0);
  };


  // ==========================================
  // LANGUAGE FILTER
  // ==========================================

  const handleLanguageChange = (
    value: SubmissionLanguage | ""
  ) => {

    setLanguage(value);

    setPage(0);
  };


  // ==========================================
  // VIEW SUBMISSION
  // ==========================================

  const handleViewSubmission = async (
    id: number
  ) => {

    try {

      setDetailsOpen(true);

      setDetailsLoading(true);

      setDetailsError("");

      setSelectedSubmission(null);


      const data =
        await getAdminSubmissionDetails(id);


      setSelectedSubmission(data);


    } catch (error) {

      console.error(error);

      setDetailsError(
        "Unable to load submission details."
      );


    } finally {

      setDetailsLoading(false);

    }
  };


  // ==========================================
  // CLOSE DETAILS
  // ==========================================

  const handleCloseDetails = () => {

    setDetailsOpen(false);

    setSelectedSubmission(null);

    setDetailsError("");
  };


  return (

    <div className="mx-auto w-full max-w-7xl">


      {/* PAGE HEADER */}

      <div>

        <p className="text-sm font-semibold uppercase tracking-wider text-blue-500">

          Submission Management

        </p>


        <h1 className="mt-2 text-3xl font-bold tracking-tight">

          Submissions

        </h1>


        <p className="app-text-secondary mt-2">

          Monitor code submissions, execution results,
          languages, and test case performance.

        </p>

      </div>



      {/* FILTERS */}

      <div className="app-surface app-border mt-8 rounded-2xl border p-5">


        <div className="grid grid-cols-1 gap-4 lg:grid-cols-[minmax(300px,1fr)_220px_200px_auto]">


          {/* SEARCH */}

          <div className="flex min-w-0 gap-3">

            <input
              type="text"

              value={searchInput}

              onChange={(event) =>
                setSearchInput(
                  event.target.value
                )
              }

              onKeyDown={(event) => {

                if (event.key === "Enter") {

                  handleSearch();

                }

              }}

              placeholder="Search user, email, or problem..."

              className="admin-input h-12 min-w-0 flex-1"
            />


            <button
              type="button"

              onClick={handleSearch}

              className="inline-flex h-12 shrink-0 items-center justify-center gap-2 rounded-lg bg-blue-600 px-6 font-medium text-white transition hover:bg-blue-700"
            >

              <FiSearch size={18} />

              Search

            </button>

          </div>



          {/* STATUS FILTER */}

          <select
            value={status}

            onChange={(event) =>
              handleStatusChange(
                event.target.value as
                  SubmissionStatus | ""
              )
            }

            className="admin-input h-12"
          >

            <option value="">

              All Statuses

            </option>


            {STATUS_OPTIONS.map(
              (statusOption) => (

                <option
                  key={statusOption}
                  value={statusOption}
                >

                  {formatStatus(
                    statusOption
                  )}

                </option>

              )
            )}

          </select>



          {/* LANGUAGE FILTER */}

          <select
            value={language}

            onChange={(event) =>
              handleLanguageChange(
                event.target.value as
                  SubmissionLanguage | ""
              )
            }

            className="admin-input h-12"
          >

            <option value="">

              All Languages

            </option>


            {LANGUAGE_OPTIONS.map(
              (languageOption) => (

                <option
                  key={languageOption}
                  value={languageOption}
                >

                  {languageOption}

                </option>

              )
            )}

          </select>



          {/* CLEAR */}

          <button
            type="button"

            onClick={handleClear}

            disabled={
              !search &&
              !searchInput &&
              !status &&
              !language
            }

            className="app-border app-hover app-text-secondary inline-flex h-12 items-center justify-center gap-2 rounded-lg border px-5 transition disabled:cursor-not-allowed disabled:opacity-40"
          >

            <FiX />

            Clear

          </button>

        </div>

      </div>



      {/* SUBMISSION TABLE */}

      <div className="app-surface app-border mt-7 overflow-hidden rounded-2xl border">


        {/* TABLE HEADER */}

        <div className="app-border border-b px-6 py-5">

          <h2 className="text-lg font-semibold">

            Submission Activity

          </h2>


          <p className="app-text-secondary mt-1 text-sm">

            {pageData

              ? `${pageData.totalElements} submissions found`

              : "Loading submissions..."}

          </p>

        </div>



        {/* ERROR */}

        {error && (

          <div className="m-6 rounded-lg border border-red-500/30 bg-red-500/10 p-4 text-red-500">

            {error}

          </div>

        )}



        {/* LOADING */}

        {loading ? (

          <div className="app-text-secondary p-12 text-center">

            Loading submissions...

          </div>


        ) : submissions.length === 0 ? (


          /* EMPTY */

          <div className="app-text-secondary p-12 text-center">

            No submissions found.

          </div>


        ) : (


          /* TABLE */

          <div className="overflow-x-auto">


            <table className="w-full min-w-[1250px]">


              <thead className="app-surface-secondary">


                <tr className="app-border border-b text-left text-xs uppercase tracking-wider">


                  <th className="px-6 py-4">

                    User

                  </th>


                  <th className="px-6 py-4">

                    Problem

                  </th>


                  <th className="px-6 py-4">

                    Language

                  </th>


                  <th className="px-6 py-4">

                    Status

                  </th>


                  <th className="px-6 py-4">

                    Test Cases

                  </th>


                  <th className="px-6 py-4">

                    Runtime

                  </th>


                  <th className="px-6 py-4">

                    Submitted

                  </th>


                  <th className="px-6 py-4 text-right">

                    Actions

                  </th>


                </tr>


              </thead>



              <tbody>


                {submissions.map(
                  (submission) => (


                    <tr
                      key={submission.id}

                      className="app-border app-hover border-b last:border-b-0"
                    >


                      {/* USER */}

                      <td className="px-6 py-5">

                        <p className="font-medium">

                          {submission.userName}

                        </p>


                        <p className="app-text-secondary mt-1 text-sm">

                          {submission.userEmail}

                        </p>

                      </td>



                      {/* PROBLEM */}

                      <td className="px-6 py-5">

                        <p className="font-medium">

                          {submission.problemTitle}

                        </p>


                        <p className="app-text-muted mt-1 text-xs">

                          Problem #{submission.problemId}

                        </p>

                      </td>



                      {/* LANGUAGE */}

                      <td className="px-6 py-5">

                        {submission.language}

                      </td>



                      {/* STATUS */}

                      <td className="px-6 py-5">

                        <span
                          className={`
                            inline-flex
                            rounded-full
                            px-3
                            py-1
                            text-xs
                            font-semibold
                            ${getStatusClass(
                              submission.status
                            )}
                          `}
                        >

                          {formatStatus(
                            submission.status
                          )}

                        </span>

                      </td>



                      {/* TEST CASES */}

                      <td className="px-6 py-5">

                        {submission.passedTestCases ?? 0}

                        {" / "}

                        {submission.totalTestCases ?? 0}

                      </td>



                      {/* RUNTIME */}

                      <td className="px-6 py-5">

                        {submission.executionTime !== null

                          ? `${submission.executionTime} ms`

                          : "—"}

                      </td>



                      {/* DATE */}

                      <td className="app-text-secondary px-6 py-5 text-sm">

                        {formatDate(
                          submission.createdAt
                        )}

                      </td>



                      {/* ACTION */}

                      <td className="px-6 py-5 text-right">

                        <button
                          type="button"

                          onClick={() =>
                            handleViewSubmission(
                              submission.id
                            )
                          }

                          className="app-border app-hover app-text-secondary inline-flex h-9 items-center justify-center gap-2 rounded-lg border px-3 text-sm font-medium transition"
                        >

                          <FiEye size={16} />

                          View

                        </button>

                      </td>


                    </tr>

                  )
                )}


              </tbody>


            </table>


          </div>

        )}



        {/* PAGINATION */}

        {pageData &&
          pageData.totalPages > 0 && (

            <div className="app-border flex items-center justify-between border-t px-6 py-4">


              <p className="app-text-secondary text-sm">

                Page {pageData.number + 1} of{" "}

                {pageData.totalPages}

              </p>



              <div className="flex gap-2">


                <button
                  type="button"

                  disabled={pageData.first}

                  onClick={() =>
                    setPage((current) =>
                      Math.max(
                        current - 1,
                        0
                      )
                    )
                  }

                  className="app-border app-hover inline-flex items-center gap-2 rounded-lg border px-4 py-2 disabled:cursor-not-allowed disabled:opacity-40"
                >

                  <FiChevronLeft />

                  Previous

                </button>



                <button
                  type="button"

                  disabled={pageData.last}

                  onClick={() =>
                    setPage(
                      (current) =>
                        current + 1
                    )
                  }

                  className="app-border app-hover inline-flex items-center gap-2 rounded-lg border px-4 py-2 disabled:cursor-not-allowed disabled:opacity-40"
                >

                  Next

                  <FiChevronRight />

                </button>


              </div>


            </div>

          )}


      </div>



      {/* SUBMISSION DETAILS DRAWER */}

      {detailsOpen && (

        <AdminSubmissionDetailsDrawer

          submission={
            selectedSubmission
          }

          loading={
            detailsLoading
          }

          error={
            detailsError
          }

          onClose={
            handleCloseDetails
          }

        />

      )}


    </div>

  );
}