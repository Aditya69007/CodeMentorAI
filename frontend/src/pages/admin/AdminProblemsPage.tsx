import {
  useCallback,
  useEffect,
  useState,
} from "react";

import {
  useNavigate,
} from "react-router-dom";

import {
  FiBookOpen,
  FiChevronLeft,
  FiChevronRight,
  FiEdit2,
  FiPlus,
  FiSearch,
  FiTrash2,
  FiX,
} from "react-icons/fi";

import { getAdminProblems } from "../../services/adminService";
import {
  deleteProblem,
} from "../../services/problemService";

import AdminConfirmModal
  from "../../components/admin/common/AdminConfirmModal";


import type {
  Difficulty,
  Problem,
  ProblemPage,
} from "../../types/problem";


type ActiveFilter =
  | "ALL"
  | "ACTIVE"
  | "INACTIVE";


export default function AdminProblemsPage() {

  const [problemToDelete, setProblemToDelete] =
    useState<Problem | null>(null);

  const [deleting, setDeleting] =
    useState(false);

  const [deleteError, setDeleteError] =
    useState("");

    const navigate = useNavigate();

  const [problemPage, setProblemPage] =
    useState<ProblemPage | null>(null);

  const [searchInput, setSearchInput] =
    useState("");

  const [searchTitle, setSearchTitle] =
    useState("");

  const [difficulty, setDifficulty] =
    useState<Difficulty | "">("");

  const [activeFilter, setActiveFilter] =
    useState<ActiveFilter>("ALL");

  const [page, setPage] =
    useState(0);

  const [loading, setLoading] =
    useState(true);

  const [error, setError] =
    useState("");


  const pageSize = 10;


  const loadProblems =
    useCallback(async () => {

      try {

        setLoading(true);

        setError("");


        let active: boolean | null = null;


        if (activeFilter === "ACTIVE") {
          active = true;
        }


        if (activeFilter === "INACTIVE") {
          active = false;
        }


        const response =
          await getAdminProblems(

            searchTitle,

            difficulty,

            null,

            active,

            page,

            pageSize
          );


        setProblemPage(response);

      } catch {

        setError(
          "Unable to load problems."
        );

      } finally {

        setLoading(false);
      }

    }, [
      searchTitle,
      difficulty,
      activeFilter,
      page,
    ]);


    useEffect(() => {
    const timer = setTimeout(() => {
        void loadProblems();
    }, 0);

    return () => clearTimeout(timer);
    }, [loadProblems]);


  const handleSearch = () => {

    setPage(0);

    setSearchTitle(
      searchInput.trim()
    );
  };


  const handleClearFilters = () => {

    setSearchInput("");

    setSearchTitle("");

    setDifficulty("");

    setActiveFilter("ALL");

    setPage(0);
  };

  const handleDeleteProblem = async () => {

    if (!problemToDelete) {
      return;
    }

    try {

      setDeleting(true);
      setDeleteError("");

      await deleteProblem(
        problemToDelete.id
      );

      setProblemToDelete(null);

      await loadProblems();

    } catch (error) {

      console.error(error);

      setDeleteError(
        "Unable to delete problem. Please try again."
      );

    } finally {

      setDeleting(false);

    }
  };

  const hasFilters =

    searchTitle !== "" ||

    difficulty !== "" ||

    activeFilter !== "ALL";


  return (

    <div className="mx-auto max-w-[1600px]">

      {/* HEADER */}

      <div
        className="
          flex
          flex-col
          gap-5
          lg:flex-row
          lg:items-end
          lg:justify-between
        "
      >

        <div>

          <p
            className="
              text-sm
              font-semibold
              uppercase
              tracking-wider
              text-blue-500
            "
          >
            Problem Management
          </p>


          <h1
            className="
              mt-2
              text-3xl
              font-bold
              tracking-tight
            "
          >
            Problems
          </h1>


          <p
            className="
              mt-2
              text-sm
              text-slate-500
            "
          >
            Manage coding problems,
            difficulty levels,
            topics, and availability.
          </p>

        </div>


        <button
        type="button"
        onClick={() =>
            navigate("/admin/problems/create")
        }
        className="
            inline-flex
            items-center
            justify-center
            gap-2
            rounded-xl
            bg-blue-600
            px-5
            py-3
            text-sm
            font-semibold
            text-white
            shadow-sm
            transition
            hover:bg-blue-500
        "
        >
        <FiPlus />

        Create Problem
        </button>

      </div>


      {/* FILTER PANEL */}

      <section
        className="
          mt-8
          rounded-2xl
          border
          border-slate-200
          bg-white
          p-5
          shadow-sm
          dark:border-slate-800
          dark:bg-slate-900/60
        "
      >

        <div
          className="
            flex
            flex-col
            gap-4
            xl:flex-row
            xl:items-center
          "
        >

          {/* SEARCH */}

          <div className="flex flex-1 gap-2">

            <div className="relative flex-1">

              <FiSearch
                className="
                  absolute
                  left-4
                  top-1/2
                  -translate-y-1/2
                  text-slate-500
                "
              />

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
                placeholder="Search problems by title..."
                className="
                  w-full
                  rounded-xl
                  border
                  border-slate-200
                  bg-transparent
                  py-3
                  pl-11
                  pr-4
                  text-sm
                  outline-none
                  transition
                  focus:border-blue-500
                  dark:border-slate-700
                "
              />

            </div>


            <button
              type="button"
              onClick={handleSearch}
              className="
                rounded-xl
                bg-blue-600
                px-5
                text-sm
                font-semibold
                text-white
                transition
                hover:bg-blue-500
              "
            >
              Search
            </button>

          </div>


          {/* DIFFICULTY */}

          <select
            value={difficulty}
            onChange={(event) => {

              setDifficulty(
                event.target.value as
                  Difficulty | ""
              );

              setPage(0);
            }}
            className="
              rounded-xl
              border
              border-slate-200
              bg-transparent
              px-4
              py-3
              text-sm
              outline-none
              dark:border-slate-700
              dark:bg-slate-900
            "
          >

            <option value="">
              All Difficulties
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


          {/* STATUS */}

          <select
            value={activeFilter}
            onChange={(event) => {

              setActiveFilter(
                event.target.value as
                  ActiveFilter
              );

              setPage(0);
            }}
            className="
              rounded-xl
              border
              border-slate-200
              bg-transparent
              px-4
              py-3
              text-sm
              outline-none
              dark:border-slate-700
              dark:bg-slate-900
            "
          >

            <option value="ALL">
              All Status
            </option>

            <option value="ACTIVE">
              Active
            </option>

            <option value="INACTIVE">
              Inactive
            </option>

          </select>


          {hasFilters && (

            <button
              type="button"
              onClick={handleClearFilters}
              className="
                inline-flex
                items-center
                justify-center
                gap-2
                rounded-xl
                border
                border-slate-200
                px-4
                py-3
                text-sm
                text-slate-500
                transition
                hover:text-red-500
                dark:border-slate-700
              "
            >

              <FiX />

              Clear

            </button>

          )}

        </div>

      </section>


      {/* ERROR */}

      {error && (

        <div
          className="
            mt-6
            rounded-xl
            border
            border-red-500/20
            bg-red-500/10
            p-4
            text-sm
            text-red-500
          "
        >
          {error}
        </div>

      )}


      {/* PROBLEM TABLE */}

      <section
        className="
          mt-6
          overflow-hidden
          rounded-2xl
          border
          border-slate-200
          bg-white
          shadow-sm
          dark:border-slate-800
          dark:bg-slate-900/60
        "
      >

        {/* TABLE HEADER */}

        <div
          className="
            flex
            items-center
            justify-between
            border-b
            border-slate-200
            px-6
            py-5
            dark:border-slate-800
          "
        >

          <div>

            <h2 className="font-semibold">
              Problem Library
            </h2>

            <p className="mt-1 text-xs text-slate-500">

              {problemPage?.totalElements ?? 0}

              {" "}problems found

            </p>

          </div>

        </div>


        {loading ? (

          <LoadingState />

        ) : problemPage &&
          problemPage.content.length > 0 ? (

          <>

            <div className="overflow-x-auto">

              <table className="w-full min-w-[1000px]">

                <thead
                  className="
                    bg-slate-50
                    dark:bg-slate-950/60
                  "
                >

                  <tr>

                    <TableHeading>
                      Problem
                    </TableHeading>

                    <TableHeading>
                      Difficulty
                    </TableHeading>

                    <TableHeading>
                      Tags
                    </TableHeading>

                    <TableHeading>
                      Status
                    </TableHeading>

                    <TableHeading>
                      Actions
                    </TableHeading>

                  </tr>

                </thead>


                <tbody>

                  {problemPage.content.map(
                    (problem) => (

                    <ProblemRow
                      key={problem.id}
                      problem={problem}
                      onEdit={(problemId) =>
                        navigate(
                          `/admin/problems/${problemId}/edit`
                        )
                      }
                      onDelete={(problem) => {

                        setDeleteError("");

                        setProblemToDelete(problem);

                      }}
                    />
                    )
                  )}

                </tbody>

              </table>

            </div>


            {/* PAGINATION */}

            <div
              className="
                flex
                flex-col
                gap-4
                border-t
                border-slate-200
                px-6
                py-4
                sm:flex-row
                sm:items-center
                sm:justify-between
                dark:border-slate-800
              "
            >

              <p className="text-sm text-slate-500">

                Page{" "}

                <span className="font-semibold">
                  {problemPage.number + 1}
                </span>

                {" "}of{" "}

                <span className="font-semibold">
                  {problemPage.totalPages}
                </span>

              </p>


              <div className="flex items-center gap-2">

                <PaginationButton
                  disabled={
                    problemPage.first
                  }
                  onClick={() =>
                    setPage(
                      (current) =>
                        current - 1
                    )
                  }
                >

                  <FiChevronLeft />

                  Previous

                </PaginationButton>


                <PaginationButton
                  disabled={
                    problemPage.last
                  }
                  onClick={() =>
                    setPage(
                      (current) =>
                        current + 1
                    )
                  }
                >

                  Next

                  <FiChevronRight />

                </PaginationButton>

              </div>

            </div>

          </>

        ) : (

          <EmptyState />

        )}

  </section>


      <AdminConfirmModal

        open={problemToDelete !== null}

        title="Delete Problem"

        description={
          problemToDelete
            ? `Are you sure you want to permanently delete "${problemToDelete.title}"? This action cannot be undone.`
            : ""
        }

        confirmLabel="Delete Problem"

        loading={deleting}

        danger

        onConfirm={handleDeleteProblem}

        onClose={() => {

          if (!deleting) {

            setProblemToDelete(null);

            setDeleteError("");

          }

        }}

      />

      {deleteError && (

        <div className="mt-4 rounded-xl border border-red-500/20 bg-red-500/10 p-4 text-sm text-red-500">

          {deleteError}

        </div>

      )}


  </div>
  );
}


function ProblemRow({
  problem,
  onEdit,
  onDelete,
}: {
  problem: Problem;
  onEdit: (problemId: number) => void;
  onDelete: (problem: Problem) => void;
}) {

  return (

    <tr
      className="
        border-t
        border-slate-100
        transition
        hover:bg-slate-50
        dark:border-slate-800
        dark:hover:bg-slate-800/30
      "
    >

      <TableCell>

        <div>

          <p className="font-medium">
            {problem.title}
          </p>

          <p className="mt-1 text-xs text-slate-500">
            Problem #{problem.id}
          </p>

        </div>

      </TableCell>


      <TableCell>

        <DifficultyBadge
          difficulty={problem.difficulty}
        />

      </TableCell>


      <TableCell>

        <div className="flex max-w-sm flex-wrap gap-1.5">

          {problem.tags
            .slice(0, 3)
            .map((tag) => (

              <span
                key={tag}
                className="
                  rounded-md
                  bg-slate-500/10
                  px-2
                  py-1
                  text-xs
                  text-slate-500
                "
              >
                {tag}
              </span>

            ))}

          {problem.tags.length > 3 && (

            <span className="text-xs text-slate-500">
              +{problem.tags.length - 3}
            </span>

          )}

        </div>

      </TableCell>


      <TableCell>

        <span
          className={`
            inline-flex
            items-center
            gap-2
            rounded-full
            px-2.5
            py-1
            text-xs
            font-medium

            ${
              problem.active

                ? "bg-emerald-500/10 text-emerald-500"

                : "bg-red-500/10 text-red-500"
            }
          `}
        >

          <span
            className={`
              h-1.5
              w-1.5
              rounded-full

              ${
                problem.active
                  ? "bg-emerald-500"
                  : "bg-red-500"
              }
            `}
          />

          {problem.active
            ? "Active"
            : "Inactive"}

        </span>

      </TableCell>


      <TableCell>

        <div className="flex items-center gap-2">

        <ActionButton
            title="Edit problem"
            onClick={() => onEdit(problem.id)}
        >
        <FiEdit2 />
        </ActionButton>


          <ActionButton
            title="Delete problem"
            danger
            onClick={() =>
              onDelete(problem)
            }
          >
            <FiTrash2 />
          </ActionButton>

        </div>

      </TableCell>

    </tr>
  );
}


function DifficultyBadge({
  difficulty,
}: {
  difficulty: Difficulty;
}) {

  const styles:
    Record<Difficulty, string> = {

    EASY:
      "bg-emerald-500/10 text-emerald-500",

    MEDIUM:
      "bg-amber-500/10 text-amber-500",

    HARD:
      "bg-red-500/10 text-red-500",
  };


  return (

    <span
      className={`
        rounded-full
        px-2.5
        py-1
        text-xs
        font-medium
        ${styles[difficulty]}
      `}
    >
      {difficulty.charAt(0) +
        difficulty
          .slice(1)
          .toLowerCase()}
    </span>
  );
}


function TableHeading({
  children,
}: {
  children: React.ReactNode;
}) {

  return (

    <th
      className="
        px-6
        py-4
        text-left
        text-xs
        font-semibold
        uppercase
        tracking-wider
        text-slate-500
      "
    >
      {children}
    </th>
  );
}


function TableCell({
  children,
}: {
  children: React.ReactNode;
}) {

  return (

    <td className="px-6 py-4 text-sm">
      {children}
    </td>
  );
}


function PaginationButton({
  children,
  disabled,
  onClick,
}: {
  children: React.ReactNode;
  disabled: boolean;
  onClick: () => void;
}) {

  return (

    <button
      type="button"
      disabled={disabled}
      onClick={onClick}
      className="
        inline-flex
        items-center
        gap-2
        rounded-lg
        border
        border-slate-200
        px-3
        py-2
        text-sm
        transition
        hover:bg-slate-100
        disabled:cursor-not-allowed
        disabled:opacity-40
        dark:border-slate-700
        dark:hover:bg-slate-800
      "
    >
      {children}
    </button>
  );
}


function ActionButton({
  children,
  title,
  danger = false,
  onClick,
}: {
  children: React.ReactNode;
  title: string;
  danger?: boolean;
  onClick?: () => void;
}) {

  return (

        <button
        type="button"
        title={title}
        onClick={onClick}
      className={`
        flex
        h-9
        w-9
        items-center
        justify-center
        rounded-lg
        border
        transition

        ${
          danger

            ? `
              border-red-500/20
              text-red-500
              hover:bg-red-500/10
            `

            : `
              border-slate-200
              text-slate-500
              hover:border-blue-500/30
              hover:bg-blue-500/10
              hover:text-blue-500
              dark:border-slate-700
            `
        }
      `}
    >
      {children}
    </button>
  );
}


function LoadingState() {

  return (

    <div
      className="
        flex
        min-h-80
        items-center
        justify-center
      "
    >

      <div className="text-center">

        <div
          className="
            mx-auto
            h-8
            w-8
            animate-spin
            rounded-full
            border-2
            border-blue-500
            border-t-transparent
          "
        />

        <p className="mt-4 text-sm text-slate-500">
          Loading problems...
        </p>

      </div>

    </div>
  );
}


function EmptyState() {

  return (

    <div
      className="
        flex
        min-h-80
        items-center
        justify-center
        p-6
      "
    >

      <div className="text-center">

        <FiBookOpen
          className="
            mx-auto
            text-4xl
            text-slate-500
          "
        />

        <p className="mt-4 font-medium">
          No problems found
        </p>

        <p className="mt-1 text-sm text-slate-500">
          Try changing your search or filters.
        </p>

      </div>

    </div>
  );
}