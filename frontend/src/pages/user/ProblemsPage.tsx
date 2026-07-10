import {
  useEffect,
  useState,
} from "react";

import {
  useNavigate,
} from "react-router-dom";

import {
  FiAlertCircle,
  FiCheck,
  FiChevronLeft,
  FiChevronRight,
  FiCode,
  FiSearch,
  FiTarget,
} from "react-icons/fi";

import {
  filterMyProblems,
  getMyProblemProgress,
  getMySolvedProblemIds,
} from "../../services/problemService";

import type {
  ProblemStatus,
} from "../../services/problemService";

import type {
  Difficulty,
  Problem,
  ProblemProgress,
} from "../../types/problem";


export default function ProblemsPage() {

  const navigate = useNavigate();


  // ==================================================
  // STATE
  // ==================================================

  const [problems, setProblems] =
    useState<Problem[]>([]);

  const [title, setTitle] =
    useState("");

  const [difficulty, setDifficulty] =
    useState<Difficulty | "">("");

  const [status, setStatus] =
    useState<ProblemStatus>("ALL");

  const [page, setPage] =
    useState(0);

  const [totalPages, setTotalPages] =
    useState(0);

  const [totalElements, setTotalElements] =
    useState(0);

  const [loading, setLoading] =
    useState(true);

  const [error, setError] =
    useState("");

  const [progress, setProgress] =
    useState<ProblemProgress | null>(null);

  const [solvedProblemIds, setSolvedProblemIds] =
    useState<Set<number>>(
      new Set()
    );


  const pageSize = 8;


  // ==================================================
  // LOAD USER PROGRESS
  // ==================================================

  useEffect(() => {

    const loadProgress = async () => {

      try {

        const [
          progressResponse,
          solvedIdsResponse,
        ] = await Promise.all([

          getMyProblemProgress(),

          getMySolvedProblemIds(),

        ]);


        setProgress(
          progressResponse
        );


        setSolvedProblemIds(
          new Set(
            solvedIdsResponse
          )
        );

      } catch (error) {

        console.error(
          "Unable to load problem progress:",
          error
        );

      }

    };


    loadProgress();

  }, []);


  // ==================================================
  // LOAD FILTERED PROBLEMS
  // ==================================================

  useEffect(() => {

    const loadProblems = async () => {

      try {

        setLoading(true);

        setError("");


        const response =
          await filterMyProblems(

            title,

            difficulty,

            status,

            page,

            pageSize

          );


        setProblems(
          response.content
        );


        setTotalPages(
          response.totalPages
        );


        setTotalElements(
          response.totalElements
        );


      } catch (error) {

        console.error(error);


        setError(
          "Unable to load problems."
        );


      } finally {

        setLoading(false);

      }

    };


    loadProblems();

  }, [
    title,
    difficulty,
    status,
    page,
  ]);


  // ==================================================
  // DIFFICULTY STYLE
  // ==================================================

  const difficultyStyle = (
    value: Difficulty
  ) => {

    if (value === "EASY") {

      return "bg-emerald-500/10 text-emerald-600 dark:text-emerald-400";

    }


    if (value === "MEDIUM") {

      return "bg-amber-500/10 text-amber-600 dark:text-amber-400";

    }


    return "bg-red-500/10 text-red-600 dark:text-red-400";
  };


  // ==================================================
  // UI
  // ==================================================

  return (

    <main className="mx-auto w-full max-w-[1300px] px-4 py-8 sm:px-6 lg:py-10">


      {/* ==================================================
          HEADER
      ================================================== */}

      <section className="flex flex-col gap-6 lg:flex-row lg:items-end lg:justify-between">


        <div>

          <p className="text-sm font-semibold text-blue-600 dark:text-blue-400">

            PROBLEM LIBRARY

          </p>


          <h1 className="mt-2 text-3xl font-bold tracking-tight sm:text-4xl">

            Practice coding problems

          </h1>


          <p className="app-text-secondary mt-3 max-w-2xl text-sm leading-6 sm:text-base">

            Solve coding challenges, track your progress,
            and receive intelligent AI guidance when you
            need help.

          </p>

        </div>



        {/* PROGRESS SUMMARY */}

        <div className="flex gap-3">


          <div className="app-surface app-border min-w-32 rounded-lg border px-5 py-4">

            <p className="app-text-secondary text-xs font-medium uppercase tracking-wide">

              Problems

            </p>


            <p className="mt-1 text-2xl font-bold">

              {progress?.totalProblems ?? totalElements}

            </p>

          </div>



          <div className="app-surface app-border min-w-32 rounded-lg border px-5 py-4">

            <p className="app-text-secondary text-xs font-medium uppercase tracking-wide">

              Solved

            </p>


            <div className="mt-1 flex items-center gap-2">

              <FiCheck
                size={20}
                className="text-emerald-500"
              />


              <p className="text-2xl font-bold">

                {progress?.solvedProblems ?? 0}

              </p>

            </div>

          </div>


        </div>


      </section>



      {/* ==================================================
          PROGRESS CARD
      ================================================== */}

      {progress && (

        <section className="app-surface app-border mt-8 rounded-lg border p-5">


          <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">


            <div className="flex items-center gap-3">


              <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-emerald-500/10 text-emerald-500">

                <FiTarget size={20} />

              </div>


              <div>

                <h2 className="font-semibold">

                  Your Problem Solving Progress

                </h2>


                <p className="app-text-secondary mt-1 text-sm">

                  Keep solving problems to strengthen your coding skills.

                </p>

              </div>


            </div>



            <div className="sm:text-right">

              <p className="text-xl font-bold">

                {progress.solvedProblems}

                <span className="app-text-muted font-normal">

                  {" "}/ {progress.totalProblems}

                </span>

              </p>


              <p className="app-text-secondary mt-1 text-xs">

                Problems Solved

              </p>

            </div>


          </div>



          {/* PROGRESS BAR */}

          <div className="app-surface-secondary mt-5 h-2 overflow-hidden rounded-full">


            <div

              className="h-full rounded-full bg-emerald-500 transition-all duration-500"

              style={{
                width: `${Math.min(
                  progress.solvedPercentage,
                  100
                )}%`,
              }}

            />


          </div>



          <div className="app-text-secondary mt-2 flex items-center justify-between text-xs">


            <span>

              {progress.solvedPercentage.toFixed(2)}% complete

            </span>


            <span>

              {progress.unsolvedProblems} remaining

            </span>


          </div>


        </section>

      )}



      {/* ==================================================
          FILTER BAR
      ================================================== */}

      <section className="app-surface app-border mt-5 rounded-lg border p-3">


        <div className="flex flex-col gap-3 lg:flex-row">


          {/* SEARCH */}

          <div className="relative flex-1">


            <FiSearch

              size={18}

              className="app-text-muted absolute left-3.5 top-1/2 -translate-y-1/2"

            />


            <input

              type="text"

              value={title}

              placeholder="Search by problem title"

              onChange={(event) => {

                setTitle(
                  event.target.value
                );

                setPage(0);

              }}

              className="
                app-surface-secondary
                app-border
                w-full
                rounded-md
                border
                py-2.5
                pl-10
                pr-4
                text-sm
                outline-none
                focus:border-blue-500
                focus:ring-2
                focus:ring-blue-500/10
              "

            />


          </div>



          {/* STATUS FILTER */}

          <select

            value={status}

            onChange={(event) => {

              const value =
                event.target.value as ProblemStatus;


              setStatus(value);

              setPage(0);

            }}

            className="
              app-surface-secondary
              app-border
              min-w-44
              rounded-md
              border
              px-4
              py-2.5
              text-sm
              outline-none
              focus:border-blue-500
            "

          >

            <option value="ALL">

              All problems

            </option>


            <option value="SOLVED">

              Solved

            </option>


            <option value="UNSOLVED">

              Unsolved

            </option>


          </select>



          {/* DIFFICULTY FILTER */}

          <select

            value={difficulty}

            onChange={(event) => {

              const value =
                event.target.value;


              if (
                value === "" ||
                value === "EASY" ||
                value === "MEDIUM" ||
                value === "HARD"
              ) {

                setDifficulty(value);

                setPage(0);

              }

            }}

            className="
              app-surface-secondary
              app-border
              min-w-48
              rounded-md
              border
              px-4
              py-2.5
              text-sm
              outline-none
              focus:border-blue-500
            "

          >

            <option value="">

              All difficulties

            </option>


            <option value="EASY">

              Easy

            </option>


            <option value="MEDIUM">

              Medium

            </option>


            <option value="HARD">

              Hard

            </option>


          </select>


        </div>


      </section>



      {/* ==================================================
          ERROR
      ================================================== */}

      {error && (

        <div className="mt-5 flex items-center gap-3 rounded-lg border border-red-500/30 bg-red-500/10 p-4 text-sm text-red-500">

          <FiAlertCircle size={19} />

          {error}

        </div>

      )}



      {/* ==================================================
          LOADING
      ================================================== */}

      {loading && (

        <section className="app-surface app-border mt-5 overflow-hidden rounded-lg border">


          {[
            1,
            2,
            3,
            4,
            5,
          ].map((item) => (

            <div

              key={item}

              className="app-border border-b p-5 last:border-b-0"

            >

              <div className="app-surface-secondary h-5 w-52 animate-pulse rounded" />

              <div className="app-surface-secondary mt-3 h-4 w-32 animate-pulse rounded" />

            </div>

          ))}


        </section>

      )}



      {/* ==================================================
          PROBLEM TABLE
      ================================================== */}

      {!loading && !error && (

        <section className="app-surface app-border mt-5 overflow-hidden rounded-lg border">


          {/* TABLE HEADER */}

          <div className="app-surface-secondary app-border hidden grid-cols-[70px_1fr_200px_140px_40px] border-b px-5 py-3 text-xs font-semibold uppercase tracking-wide md:grid">


            <span className="app-text-muted">

              Status

            </span>


            <span className="app-text-muted">

              Problem

            </span>


            <span className="app-text-muted">

              Topics

            </span>


            <span className="app-text-muted">

              Difficulty

            </span>


            <span />


          </div>



          {/* EMPTY */}

          {problems.length === 0 ? (

            <div className="flex flex-col items-center justify-center px-5 py-20 text-center">


              <div className="app-surface-secondary flex h-12 w-12 items-center justify-center rounded-full">

                <FiCode size={21} />

              </div>


              <h2 className="mt-4 font-semibold">

                No problems found

              </h2>


              <p className="app-text-secondary mt-2 text-sm">

                Change your search, status, or difficulty filter.

              </p>


            </div>

          ) : (

            <div>


              {problems.map((problem) => {


                const isSolved =
                  solvedProblemIds.has(
                    problem.id
                  );


                return (

                  <button

                    key={problem.id}

                    onClick={() =>
                      navigate(
                        `/problems/${problem.id}`
                      )
                    }

                    className="
                      app-hover
                      app-border
                      group
                      grid
                      w-full
                      gap-3
                      border-b
                      p-5
                      text-left
                      last:border-b-0
                      md:grid-cols-[70px_1fr_200px_140px_40px]
                      md:items-center
                    "

                  >


                    {/* SOLVED STATUS */}

                    <div className="hidden md:block">


                      {isSolved ? (

                        <div className="flex h-7 w-7 items-center justify-center rounded-full bg-emerald-500/10 text-emerald-500">

                          <FiCheck size={16} />

                        </div>

                      ) : (

                        <div className="app-border h-7 w-7 rounded-full border" />

                      )}


                    </div>



                    {/* TITLE */}

                    <div className="min-w-0">


                      <div className="flex items-center gap-2">


                        {isSolved && (

                          <FiCheck className="shrink-0 text-emerald-500 md:hidden" />

                        )}


                        <h2

                          className={`
                            truncate
                            text-sm
                            font-semibold
                            group-hover:text-blue-600
                            dark:group-hover:text-blue-400

                            ${
                              isSolved
                                ? "text-emerald-600 dark:text-emerald-400"
                                : ""
                            }
                          `}

                        >

                          {problem.title}

                        </h2>


                      </div>


                    </div>



                    {/* TAGS */}

                    <div className="flex flex-wrap gap-1.5">


                      {problem.tags
                        ?.slice(0, 2)
                        .map((tag) => (

                          <span

                            key={tag}

                            className="app-surface-secondary app-text-secondary rounded px-2 py-1 text-xs"

                          >

                            {tag}

                          </span>

                        ))}


                    </div>



                    {/* DIFFICULTY */}

                    <div>


                      <span

                        className={`
                          inline-flex
                          rounded-full
                          px-2.5
                          py-1
                          text-xs
                          font-semibold

                          ${difficultyStyle(
                            problem.difficulty
                          )}
                        `}

                      >

                        {problem.difficulty}

                      </span>


                    </div>



                    {/* ARROW */}

                    <FiChevronRight className="app-text-muted hidden transition-transform group-hover:translate-x-1 group-hover:text-blue-500 md:block" />


                  </button>

                );

              })}


            </div>

          )}


        </section>

      )}



      {/* ==================================================
          PAGINATION
      ================================================== */}

      {!loading &&
        !error &&
        totalPages > 0 && (

          <section className="mt-5 flex flex-col items-center justify-between gap-4 sm:flex-row">


            <p className="app-text-secondary text-sm">

              Page{" "}

              <span className="app-text-primary font-medium">

                {page + 1}

              </span>

              {" "}of{" "}

              <span className="app-text-primary font-medium">

                {totalPages}

              </span>


              <span className="app-text-muted ml-2">

                ({totalElements} problems)

              </span>

            </p>



            <div className="flex gap-2">


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

                className="app-surface app-hover app-border flex items-center gap-2 rounded-md border px-3 py-2 text-sm font-medium disabled:opacity-40"

              >

                <FiChevronLeft />

                Previous

              </button>



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
                  page >= totalPages - 1
                }

                className="app-surface app-hover app-border flex items-center gap-2 rounded-md border px-3 py-2 text-sm font-medium disabled:opacity-40"

              >

                Next

                <FiChevronRight />

              </button>


            </div>


          </section>

        )}


    </main>

  );

}