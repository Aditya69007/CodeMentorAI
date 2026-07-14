import {
  useEffect,
  useMemo,
  useState,
} from "react";

import {
  useNavigate,
  useParams,
} from "react-router-dom";

import {
  FiActivity,
  FiAlertTriangle,
  FiArrowLeft,
  FiArrowRight,
  FiCheckCircle,
  FiCpu,
  FiTarget,
  FiUser,
} from "react-icons/fi";

import { getAdminUserDetails } from "../../services/adminService";

import type {
  AdminUserDetails,
} from "../../types/admin";

import AdminSubmissionActivityChart
  from "../../components/admin/dashboard/AdminSubmissionActivityChart";

import AdminStatusDistribution
  from "../../components/admin/dashboard/AdminStatusDistribution";

  
    function DifficultyBadge({
    difficulty,
    }: {
    difficulty: string;
    }) {
    const difficultyStyles:
        Record<string, string> = {

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
            inline-flex
            rounded-full
            px-2.5
            py-1
            text-xs
            font-medium

            ${
            difficultyStyles[difficulty] ??
            "bg-slate-500/10 text-slate-500"
            }
        `}
        >
        {formatEnum(difficulty)}
        </span>
    );
    }

export default function AdminUserDetailsPage() {
  const navigate = useNavigate();

  const { userId } = useParams<{
    userId: string;
  }>();


  const [user, setUser] =
    useState<AdminUserDetails | null>(null);

  const [loading, setLoading] =
    useState(true);

  const [error, setError] =
    useState("");


  useEffect(() => {
    const loadUserDetails = async () => {
      if (!userId) {
        setError("Invalid user.");
        setLoading(false);
        return;
      }

      try {
        setLoading(true);
        setError("");

        const response =
          await getAdminUserDetails(
            Number(userId)
          );

        setUser(response);

      } catch (error) {
        console.error(
          "Unable to load user details:",
          error
        );

        setError(
          "Unable to load user analytics."
        );

      } finally {
        setLoading(false);
      }
    };

    void loadUserDetails();

  }, [userId]);


  const statusDistribution =
    useMemo(() => {
      if (!user) {
        return {};
      }

      return user.submissionStatusDistribution;
    }, [user]);


  if (loading) {
    return (
      <div className="flex min-h-[500px] items-center justify-center">
        <div className="text-center">

          <div
            className="
              mx-auto
              h-10
              w-10
              animate-spin
              rounded-full
              border-4
              border-blue-500/20
              border-t-blue-500
            "
          />

          <p className="mt-4 text-sm text-slate-500">
            Loading user analytics...
          </p>

        </div>
      </div>
    );
  }


  if (error || !user) {
    return (
      <div className="mx-auto max-w-7xl">

        <button
          type="button"
          onClick={() =>
            navigate("/admin/users")
          }
          className="
            flex
            items-center
            gap-2
            text-sm
            text-slate-500
            transition
            hover:text-blue-500
          "
        >
          <FiArrowLeft />

          Back to Users
        </button>


        <div
          className="
            mt-6
            rounded-2xl
            border
            border-red-500/20
            bg-red-500/10
            p-5
            text-sm
            text-red-500
          "
        >
          {error || "User not found."}
        </div>

      </div>
    );
  }


  const fullName =
    `${user.firstName} ${user.lastName}`.trim();

  const initials =
    `${user.firstName?.charAt(0) ?? ""}${
      user.lastName?.charAt(0) ?? ""
    }`;


  const statCards = [
    {
      title: "Total Submissions",
      value: user.totalSubmissions,
      description: "Code attempts submitted",
      icon: FiActivity,
    },

    {
      title: "Problems Solved",
      value: user.solvedProblems,
      description: "Unique problems completed",
      icon: FiTarget,
    },

    {
      title: "Acceptance Rate",
      value: `${user.acceptanceRate.toFixed(2)}%`,
      description: "Successful submission rate",
      icon: FiCheckCircle,
    },

    {
      title: "AI Analyses",
      value: user.totalAiAnalyses,
      description: "AI mentor analyses generated",
      icon: FiCpu,
    },

    {
      title: "Mistakes Detected",
      value: user.totalMistakes,
      description: "Learning mistakes identified",
      icon: FiAlertTriangle,
    },
  ];


  return (
    <div className="mx-auto max-w-[1500px]">

      {/* HEADER */}

      <button
        type="button"
        onClick={() =>
          navigate("/admin/users")
        }
        className="
          flex
          items-center
          gap-2
          text-sm
          font-medium
          text-slate-500
          transition
          hover:text-blue-500
        "
      >
        <FiArrowLeft />

        Back to Users
      </button>


      {/* USER PROFILE */}

      <section
        className="
          mt-6
          rounded-2xl
          border
          border-slate-200
          bg-white
          p-6
          shadow-sm
          dark:border-slate-800
          dark:bg-slate-900/60
        "
      >

        <div
          className="
            flex
            flex-col
            gap-5
            sm:flex-row
            sm:items-center
            sm:justify-between
          "
        >

          <div className="flex items-center gap-4">

            <div
              className="
                flex
                h-16
                w-16
                items-center
                justify-center
                rounded-2xl
                bg-blue-500/10
                text-xl
                font-bold
                text-blue-500
              "
            >
              {initials || <FiUser />}
            </div>


            <div>

              <div className="flex items-center gap-3">

                <h1
                  className="
                    text-2xl
                    font-bold
                    text-slate-950
                    dark:text-white
                  "
                >
                  {fullName}
                </h1>


                <span
                  className={`
                    rounded-full
                    px-3
                    py-1
                    text-xs
                    font-medium

                    ${
                      user.enabled

                        ? `
                          bg-emerald-500/10
                          text-emerald-500
                        `

                        : `
                          bg-red-500/10
                          text-red-500
                        `
                    }
                  `}
                >
                  {user.enabled
                    ? "Active"
                    : "Disabled"}
                </span>

              </div>


              <p className="mt-1 text-sm text-slate-500">
                {user.email}
              </p>


              <p className="mt-1 text-xs text-slate-500">
                Joined{" "}
                {new Date(
                  user.createdAt
                ).toLocaleDateString()}
              </p>

            </div>

          </div>


          <div
            className="
              rounded-xl
              border
              border-slate-200
              bg-slate-50
              px-5
              py-3
              dark:border-slate-800
              dark:bg-slate-950/50
            "
          >

            <p className="text-xs text-slate-500">
              User ID
            </p>

            <p
              className="
                mt-1
                font-semibold
                text-slate-900
                dark:text-white
              "
            >
              #{user.id}
            </p>

          </div>

        </div>

      </section>


      {/* STAT CARDS */}

      <div
        className="
          mt-6
          grid
          gap-4
          sm:grid-cols-2
          xl:grid-cols-5
        "
      >

        {statCards.map((card) => {
          const Icon = card.icon;

          return (
            <div
              key={card.title}
              className="
                rounded-2xl
                border
                border-slate-200
                bg-white
                p-5
                shadow-sm
                transition
                hover:-translate-y-0.5
                hover:shadow-md
                dark:border-slate-800
                dark:bg-slate-900/60
              "
            >

              <div
                className="
                  flex
                  items-start
                  justify-between
                "
              >

                <div>

                  <p className="text-sm text-slate-500">
                    {card.title}
                  </p>

                  <p
                    className="
                      mt-3
                      text-3xl
                      font-bold
                      text-slate-950
                      dark:text-white
                    "
                  >
                    {card.value}
                  </p>

                </div>


                <div
                  className="
                    flex
                    h-11
                    w-11
                    items-center
                    justify-center
                    rounded-xl
                    bg-blue-500/10
                    text-xl
                    text-blue-500
                  "
                >
                  <Icon />
                </div>

              </div>


              <p className="mt-4 text-xs text-slate-500">
                {card.description}
              </p>

            </div>
          );
        })}

      </div>


      {/* ACTIVITY + STATUS */}

      <div
        className="
          mt-6
          grid
          gap-6
          xl:grid-cols-2
        "
      >

        <section
          className="
            rounded-2xl
            border
            border-slate-200
            bg-white
            p-6
            shadow-sm
            dark:border-slate-800
            dark:bg-slate-900/60
          "
        >

          <h2
            className="
              text-lg
              font-semibold
              text-slate-950
              dark:text-white
            "
          >
            Submission Activity
          </h2>

          <p className="mt-1 text-sm text-slate-500">
            User submission activity during the last 7 days.
          </p>


          <div className="mt-6">

            <AdminSubmissionActivityChart
              data={user.submissionActivity}
            />

          </div>

        </section>


        <section
          className="
            rounded-2xl
            border
            border-slate-200
            bg-white
            p-6
            shadow-sm
            dark:border-slate-800
            dark:bg-slate-900/60
          "
        >

          <h2
            className="
              text-lg
              font-semibold
              text-slate-950
              dark:text-white
            "
          >
            Submission Results
          </h2>

          <p className="mt-1 text-sm text-slate-500">
            Distribution of this user's submission outcomes.
          </p>


          <div className="mt-6">

            <AdminStatusDistribution
              distribution={statusDistribution}
            />

          </div>

        </section>

      </div>


      {/* TOPIC PERFORMANCE + MISTAKES */}

      <div
        className="
          mt-6
          grid
          gap-6
          xl:grid-cols-2
        "
      >

        {/* TOPICS */}

        <section
          className="
            rounded-2xl
            border
            border-slate-200
            bg-white
            shadow-sm
            dark:border-slate-800
            dark:bg-slate-900/60
          "
        >

          <div className="p-6">

            <h2
              className="
                text-lg
                font-semibold
                text-slate-950
                dark:text-white
              "
            >
              Topic Performance
            </h2>

            <p className="mt-1 text-sm text-slate-500">
              Coding performance grouped by topic.
            </p>

          </div>


          <div className="overflow-x-auto">

            <table className="w-full">

              <thead
                className="
                  border-y
                  border-slate-200
                  bg-slate-50
                  dark:border-slate-800
                  dark:bg-slate-950/40
                "
              >

                <tr>

                  <TableHeading>
                    Topic
                  </TableHeading>

                  <TableHeading>
                    Attempts
                  </TableHeading>

                  <TableHeading>
                    Accepted
                  </TableHeading>

                  <TableHeading>
                    Mistakes
                  </TableHeading>

                  <TableHeading>
                    Rate
                  </TableHeading>

                </tr>

              </thead>


              <tbody>

                {user.topicPerformance.map(
                  (topic) => (

                    <tr
                      key={topic.topicId}
                      className="
                        border-b
                        border-slate-100
                        last:border-none
                        dark:border-slate-800
                      "
                    >

                      <TableCell>
                        <span
                          className="
                            font-medium
                            text-slate-900
                            dark:text-white
                          "
                        >
                          {topic.topicName}
                        </span>
                      </TableCell>


                      <TableCell>
                        {topic.totalSubmissions}
                      </TableCell>


                      <TableCell>
                        {topic.acceptedSubmissions}
                      </TableCell>


                      <TableCell>
                        {topic.mistakes}
                      </TableCell>


                      <TableCell>
                        {topic.acceptanceRate.toFixed(2)}%
                      </TableCell>

                    </tr>

                  )
                )}

              </tbody>

            </table>

          </div>

        </section>


        {/* MISTAKES */}

        <section
          className="
            rounded-2xl
            border
            border-slate-200
            bg-white
            p-6
            shadow-sm
            dark:border-slate-800
            dark:bg-slate-900/60
          "
        >

          <h2
            className="
              text-lg
              font-semibold
              text-slate-950
              dark:text-white
            "
          >
            AI Mistake Analysis
          </h2>

          <p className="mt-1 text-sm text-slate-500">
            Mistake patterns identified by the AI mentor.
          </p>


          <div className="mt-6 space-y-4">

            {Object.entries(
              user.mistakeTypeDistribution
            ).length === 0 ? (

              <div
                className="
                  rounded-xl
                  border
                  border-dashed
                  border-slate-300
                  p-10
                  text-center
                  text-sm
                  text-slate-500
                  dark:border-slate-700
                "
              >
                No AI mistakes recorded.
              </div>

            ) : (

              Object.entries(
                user.mistakeTypeDistribution
              ).map(([type, count]) => (

                <div
                  key={type}
                  className="
                    flex
                    items-center
                    justify-between
                    rounded-xl
                    border
                    border-slate-200
                    bg-slate-50
                    px-4
                    py-4
                    dark:border-slate-800
                    dark:bg-slate-950/40
                  "
                >

                  <div className="flex items-center gap-3">

                    <div
                      className="
                        flex
                        h-9
                        w-9
                        items-center
                        justify-center
                        rounded-lg
                        bg-amber-500/10
                        text-amber-500
                      "
                    >
                      <FiAlertTriangle />
                    </div>


                    <span
                      className="
                        text-sm
                        font-medium
                        text-slate-800
                        dark:text-slate-200
                      "
                    >
                      {formatEnum(type)}
                    </span>

                  </div>


                  <span
                    className="
                      text-lg
                      font-bold
                      text-slate-950
                      dark:text-white
                    "
                  >
                    {count}
                  </span>

                </div>

              ))

            )}

          </div>

        </section>

      </div>



        {/* RECENT SUBMISSIONS */}

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
        {/* HEADER */}

        <div
            className="
            flex
            flex-col
            gap-4
            border-b
            border-slate-200
            p-6
            sm:flex-row
            sm:items-center
            sm:justify-between
            dark:border-slate-800
            "
        >
            <div>
            <h2
                className="
                text-lg
                font-semibold
                text-slate-950
                dark:text-white
                "
            >
                Recent Submissions
            </h2>

            <p className="mt-1 text-sm text-slate-500">
                Latest code submissions made by this user.
            </p>
            </div>

            <button
            type="button"
            onClick={() =>
                navigate(
                `/admin/submissions?userId=${user.id}`
                )
            }
            className="
                inline-flex
                items-center
                justify-center
                gap-2
                rounded-lg
                border
                border-slate-200
                px-4
                py-2
                text-sm
                font-medium
                text-slate-600
                transition
                hover:border-blue-500/30
                hover:bg-blue-500/10
                hover:text-blue-500
                dark:border-slate-700
                dark:text-slate-300
            "
            >
            View All Submissions

            <FiArrowRight />
            </button>
        </div>


        {/* TABLE */}

        {user.recentSubmissions.length === 0 ? (

            <div
            className="
                flex
                min-h-52
                items-center
                justify-center
                p-6
            "
            >
            <div className="text-center">

                <div
                className="
                    mx-auto
                    flex
                    h-12
                    w-12
                    items-center
                    justify-center
                    rounded-xl
                    bg-slate-500/10
                    text-xl
                    text-slate-500
                "
                >
                <FiActivity />
                </div>

                <p
                className="
                    mt-4
                    text-sm
                    font-medium
                    text-slate-700
                    dark:text-slate-300
                "
                >
                No submissions found
                </p>

                <p className="mt-1 text-xs text-slate-500">
                This user has not submitted any code yet.
                </p>

            </div>
            </div>

        ) : (

            <div
            className="
                max-h-[520px]
                overflow-auto
            "
            >

            <table className="w-full min-w-[1100px]">

                {/* STICKY HEADER */}

                <thead
                className="
                    sticky
                    top-0
                    z-10
                    border-b
                    border-slate-200
                    bg-slate-50
                    dark:border-slate-800
                    dark:bg-slate-950
                "
                >

                <tr>

                    <TableHeading>
                    Problem
                    </TableHeading>

                    <TableHeading>
                    Topic
                    </TableHeading>

                    <TableHeading>
                    Difficulty
                    </TableHeading>

                    <TableHeading>
                    Language
                    </TableHeading>

                    <TableHeading>
                    Status
                    </TableHeading>

                    <TableHeading>
                    Tests
                    </TableHeading>

                    <TableHeading>
                    Execution
                    </TableHeading>

                    <TableHeading>
                    Submitted
                    </TableHeading>

                </tr>

                </thead>


                <tbody>

                {user.recentSubmissions.map(
                    (submission) => (

                    <tr
                        key={submission.id}
                        className="
                        border-b
                        border-slate-100
                        transition
                        last:border-none
                        hover:bg-slate-50
                        dark:border-slate-800
                        dark:hover:bg-slate-800/30
                        "
                    >

                        {/* PROBLEM */}

                        <TableCell>

                        <div>

                            <p
                            className="
                                font-medium
                                text-slate-900
                                dark:text-white
                            "
                            >
                            {submission.problemTitle}
                            </p>

                            <p className="mt-1 text-xs text-slate-500">
                            Submission #{submission.id}
                            </p>

                        </div>

                        </TableCell>


                        {/* TOPIC */}

                        <TableCell>
                        {submission.topicName}
                        </TableCell>


                        {/* DIFFICULTY */}

                        <TableCell>

                        <DifficultyBadge
                            difficulty={
                            submission.difficulty
                            }
                        />

                        </TableCell>


                        {/* LANGUAGE */}

                        <TableCell>

                        <span
                            className="
                            rounded-md
                            bg-blue-500/10
                            px-2
                            py-1
                            text-xs
                            font-medium
                            text-blue-500
                            "
                        >
                            {submission.language}
                        </span>

                        </TableCell>


                        {/* STATUS */}

                        <TableCell>

                        <SubmissionStatusBadge
                            status={submission.status}
                        />

                        </TableCell>


                        {/* TEST CASES */}

                        <TableCell>

                        <span
                            className="
                            font-medium
                            text-slate-700
                            dark:text-slate-300
                            "
                        >
                            {submission.passedTestCases ?? 0}

                            <span className="text-slate-500">
                            /
                            {submission.totalTestCases ?? 0}
                            </span>
                        </span>

                        </TableCell>


                        {/* EXECUTION TIME */}

                        <TableCell>

                        {submission.executionTime !== null
                            ? `${submission.executionTime} ms`
                            : "—"}

                        </TableCell>


                        {/* DATE */}

                        <TableCell>

                        <div>

                            <p
                            className="
                                text-slate-700
                                dark:text-slate-300
                            "
                            >
                            {new Date(
                                submission.createdAt
                            ).toLocaleDateString()}
                            </p>

                            <p className="mt-1 text-xs text-slate-500">
                            {new Date(
                                submission.createdAt
                            ).toLocaleTimeString()}
                            </p>

                        </div>

                        </TableCell>

                    </tr>

                    )
                )}

                </tbody>

            </table>

            </div>

        )}

        </section>

    </div>
  );
}


function formatEnum(value: string) {
  return value
    .toLowerCase()
    .split("_")
    .map(
      (word) =>
        word.charAt(0).toUpperCase() +
        word.slice(1)
    )
    .join(" ");
}


interface TableElementProps {
  children: React.ReactNode;
}


function TableHeading({
  children,
}: TableElementProps) {
  return (
    <th
      className="
        whitespace-nowrap
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
}: TableElementProps) {
  return (
    <td
      className="
        whitespace-nowrap
        px-6
        py-4
        text-sm
        text-slate-500
      "
    >
      {children}
    </td>
  );
}


function SubmissionStatusBadge({
  status,
}: {
  status: string;
}) {
  const statusStyles:
    Record<string, string> = {

    ACCEPTED:
      "bg-emerald-500/10 text-emerald-500",

    WRONG_ANSWER:
      "bg-red-500/10 text-red-500",

    COMPILATION_ERROR:
      "bg-orange-500/10 text-orange-500",

    TIME_LIMIT_EXCEEDED:
      "bg-amber-500/10 text-amber-500",

    RUNTIME_ERROR:
      "bg-purple-500/10 text-purple-500",

    PENDING:
      "bg-slate-500/10 text-slate-500",

    RUNNING:
      "bg-blue-500/10 text-blue-500",
  };


  return (
    <span
      className={`
        inline-flex
        rounded-full
        px-2.5
        py-1
        text-xs
        font-medium

        ${
          statusStyles[status] ??
          "bg-slate-500/10 text-slate-500"
        }
      `}
    >
      {formatEnum(status)}
    </span>
  );
}