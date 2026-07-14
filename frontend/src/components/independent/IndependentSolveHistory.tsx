import {
  useEffect,
  useState,
} from "react";

import {
  FiCheckCircle,
  FiClock,
  FiRefreshCw,
  FiShield,
  FiXCircle,
} from "react-icons/fi";

import {
  getIndependentSolveHistory,
} from "../../services/independentSolveService";

import type {
  IndependentSolveSessionResponse,
} from "../../services/independentSolveService";


interface Props {
  problemId: number;
  refreshKey?: number;
}


export default function IndependentSolveHistory({
  problemId,
  refreshKey = 0,
}: Props) {

  const [
    sessions,
    setSessions,
  ] = useState<IndependentSolveSessionResponse[]>([]);

  const [
    loading,
    setLoading,
  ] = useState(true);

  const [
    error,
    setError,
  ] = useState("");


  useEffect(() => {

    const loadHistory = async () => {

      try {

        setLoading(true);
        setError("");

        const response =
          await getIndependentSolveHistory(
            problemId
          );

        setSessions(response);

      } catch (error) {

        console.error(error);

        setError(
          "Unable to load independent solve history."
        );

      } finally {

        setLoading(false);

      }

    };


    loadHistory();

  }, [
    problemId,
    refreshKey,
  ]);


  const formatDuration = (
    totalSeconds: number | null
  ) => {

    if (totalSeconds === null) {
      return "--";
    }

    const hours =
      Math.floor(totalSeconds / 3600);

    const minutes =
      Math.floor(
        (totalSeconds % 3600) / 60
      );

    const seconds =
      totalSeconds % 60;


    if (hours > 0) {

      return `${hours}h ${minutes}m ${seconds}s`;

    }


    if (minutes > 0) {

      return `${minutes}m ${seconds}s`;

    }


    return `${seconds}s`;

  };


  const formatDate = (
    value: string
  ) => {

    return new Date(value)
      .toLocaleString(
        undefined,
        {
          dateStyle: "medium",
          timeStyle: "short",
        }
      );

  };


  if (loading) {

    return (

      <div className="flex h-full items-center justify-center p-8">

        <div className="text-center">

          <div
            className="
              mx-auto
              h-7
              w-7
              animate-spin
              rounded-full
              border-2
              border-violet-500
              border-t-transparent
            "
          />

          <p className="app-text-muted mt-3 text-sm">

            Loading independent solve history...

          </p>

        </div>

      </div>

    );

  }


  if (error) {

    return (

      <div className="p-5">

        <div
          className="
            rounded-lg
            border
            border-red-500/30
            bg-red-500/10
            p-4
          "
        >

          <p className="text-sm text-red-500">

            {error}

          </p>

        </div>

      </div>

    );

  }


  if (sessions.length === 0) {

    return (

      <div className="flex h-full items-center justify-center p-8">

        <div className="max-w-sm text-center">

          <div
            className="
              mx-auto
              flex
              h-12
              w-12
              items-center
              justify-center
              rounded-full
              bg-violet-500/10
              text-violet-500
            "
          >

            <FiShield size={22} />

          </div>

          <h3 className="mt-4 font-semibold">

            No Independent Sessions Yet

          </h3>

          <p className="app-text-muted mt-2 text-sm leading-6">

            Start Solve Without AI mode to test your
            independent problem-solving ability.

          </p>

        </div>

      </div>

    );

  }


  const solvedCount =
    sessions.filter(
      (session) =>
        session.solvedIndependently
    ).length;


  const successRate =
    Math.round(
      (solvedCount / sessions.length) * 100
    );


  return (

    <div className="h-full overflow-y-auto p-4">


      {/* =====================================
          SUMMARY
      ===================================== */}

      <div
        className="
          app-surface-secondary
          app-border
          rounded-lg
          border
          p-4
        "
      >

        <div className="flex items-center gap-2">

          <FiShield className="text-violet-500" />

          <h3 className="font-semibold">

            Independent Solve Progress

          </h3>

        </div>


        <div className="mt-4 grid grid-cols-3 gap-3">

          <div
            className="
              app-surface
              app-border
              rounded-md
              border
              p-3
              text-center
            "
          >

            <p className="app-text-muted text-xs">

              Sessions

            </p>

            <p className="mt-1 text-lg font-bold">

              {sessions.length}

            </p>

          </div>


          <div
            className="
              app-surface
              app-border
              rounded-md
              border
              p-3
              text-center
            "
          >

            <p className="app-text-muted text-xs">

              Solved

            </p>

            <p className="mt-1 text-lg font-bold text-emerald-500">

              {solvedCount}

            </p>

          </div>


          <div
            className="
              app-surface
              app-border
              rounded-md
              border
              p-3
              text-center
            "
          >

            <p className="app-text-muted text-xs">

              Success Rate

            </p>

            <p className="mt-1 text-lg font-bold text-violet-500">

              {successRate}%

            </p>

          </div>

        </div>

      </div>


      {/* =====================================
          HISTORY
      ===================================== */}

      <div className="mt-4 space-y-3">

        {
          sessions.map(
            (session, index) => (

              <div

                key={
                  `${session.startedAt}-${index}`
                }

                className="
                  app-surface-secondary
                  app-border
                  rounded-lg
                  border
                  p-4
                "

              >

                <div
                  className="
                    flex
                    items-start
                    justify-between
                    gap-4
                  "
                >

                  <div className="flex items-start gap-3">

                    <div
                      className={`
                        mt-0.5
                        flex
                        h-9
                        w-9
                        shrink-0
                        items-center
                        justify-center
                        rounded-full

                        ${
                          session.solvedIndependently

                            ? `
                              bg-emerald-500/10
                              text-emerald-500
                            `

                            : `
                              bg-slate-500/10
                              app-text-muted
                            `
                        }
                      `}
                    >

                      {
                        session.solvedIndependently
                          ? <FiCheckCircle />
                          : <FiXCircle />
                      }

                    </div>


                    <div>

                      <p
                        className={`
                          text-sm
                          font-semibold

                          ${
                            session.solvedIndependently
                              ? "text-emerald-500"
                              : ""
                          }
                        `}
                      >

                        {
                          session.solvedIndependently
                            ? "Solved Independently"
                            : "Session Completed"
                        }

                      </p>


                      <p className="app-text-muted mt-1 text-xs">

                        {
                          formatDate(
                            session.startedAt
                          )
                        }

                      </p>

                    </div>

                  </div>


                  <div
                    className="
                      flex
                      items-center
                      gap-1.5
                      rounded-md
                      bg-slate-500/10
                      px-2.5
                      py-1.5
                    "
                  >

                    <FiClock
                      size={13}
                      className="app-text-muted"
                    />

                    <span className="text-xs font-medium">

                      {
                        formatDuration(
                          session.durationSeconds
                        )
                      }

                    </span>

                  </div>

                </div>


                <div
                  className="
                    app-border
                    mt-4
                    flex
                    items-center
                    justify-between
                    border-t
                    pt-3
                  "
                >

                  <p className="app-text-muted text-xs">

                    Submissions

                  </p>


                  <div className="flex items-center gap-2">

                    <FiRefreshCw
                      size={13}
                      className="app-text-muted"
                    />

                    <span className="text-sm font-semibold">

                      {
                        session.submissionsDuringSession
                      }

                    </span>

                  </div>

                </div>

              </div>

            )
          )
        }

      </div>

    </div>

  );

}