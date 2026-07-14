import {
  useEffect,
  useState,
} from "react";

import {
  FiAlertCircle,
  FiCheck,
  FiCheckCircle,
  FiClock,
  FiCode,
  FiCopy,
  FiCpu,
  FiFileText,
  FiUser,
  FiX,
} from "react-icons/fi";

import type {
  AdminSubmissionDetails,
  SubmissionStatus,
} from "../../../types/submission";


interface AdminSubmissionDetailsDrawerProps {

  submission: AdminSubmissionDetails | null;

  loading: boolean;

  error: string;

  onClose: () => void;
}


const formatStatus = (
  status: SubmissionStatus
) => {

  return status
    .replaceAll("_", " ")
    .toLowerCase()
    .replace(/\b\w/g, (character) =>
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


export default function AdminSubmissionDetailsDrawer({
  submission,
  loading,
  error,
  onClose,
}: AdminSubmissionDetailsDrawerProps) {

    const [copied, setCopied] = useState(false);


    useEffect(() => {

    const handleKeyDown = (
        event: KeyboardEvent
    ) => {

        if (event.key === "Escape") {

        onClose();

        }
    };


    const previousOverflow =
        document.body.style.overflow;


    document.body.style.overflow =
        "hidden";


    window.addEventListener(
        "keydown",
        handleKeyDown
    );


    return () => {

        document.body.style.overflow =
        previousOverflow;


        window.removeEventListener(
        "keydown",
        handleKeyDown
        );

    };

    }, [onClose]);


    const handleCopyCode = async () => {

    if (!submission?.sourceCode) {
        return;
    }


    try {

        await navigator.clipboard.writeText(
        submission.sourceCode
        );


        setCopied(true);


        window.setTimeout(() => {

        setCopied(false);

        }, 2000);


    } catch (error) {

        console.error(
        "Unable to copy source code.",
        error
        );

    }
    };

  return (

    <div className="fixed inset-0 z-50">

      {/* BACKDROP */}

      <button
        type="button"
        aria-label="Close submission details"
        onClick={onClose}
        className="absolute inset-0 h-full w-full cursor-default bg-black/50"
      />


      {/* DRAWER */}

      <aside className="app-surface absolute right-0 top-0 flex h-full w-full max-w-3xl flex-col shadow-2xl">


        {/* HEADER */}

        <div className="app-border flex items-center justify-between border-b px-6 py-5">

          <div>

            <p className="text-sm font-semibold uppercase tracking-wider text-blue-500">
              Submission Details
            </p>

            <h2 className="mt-1 text-xl font-bold">

              {submission
                ? `Submission #${submission.id}`
                : "Loading Submission"}

            </h2>

          </div>


          <button
            type="button"
            onClick={onClose}
            aria-label="Close submission details"
            className="app-hover app-text-secondary flex h-10 w-10 items-center justify-center rounded-lg"
          >
            <FiX size={21} />
          </button>

        </div>


        {/* CONTENT */}

        <div className="flex-1 overflow-y-auto p-6">

          {loading && (

            <div className="app-text-secondary flex min-h-80 items-center justify-center">
              Loading submission details...
            </div>

          )}


          {!loading && error && (

            <div className="flex items-center gap-3 rounded-lg border border-red-500/30 bg-red-500/10 p-4 text-red-500">

              <FiAlertCircle />

              {error}

            </div>

          )}


          {!loading &&
            !error &&
            submission && (

              <div className="space-y-6">


                {/* STATUS */}

                <section className="app-border rounded-xl border p-5">

                  <div className="flex flex-wrap items-center justify-between gap-4">

                    <div>

                      <p className="app-text-muted text-xs font-semibold uppercase tracking-wider">
                        Result
                      </p>

                      <span
                        className={`mt-2 inline-flex rounded-full px-3 py-1.5 text-sm font-semibold ${getStatusClass(
                          submission.status
                        )}`}
                      >
                        {formatStatus(
                          submission.status
                        )}
                      </span>

                    </div>


                    <div className="text-right">

                      <p className="app-text-muted text-xs font-semibold uppercase tracking-wider">
                        Submitted
                      </p>

                      <p className="app-text-secondary mt-2 text-sm">
                        {formatDate(
                          submission.createdAt
                        )}
                      </p>

                    </div>

                  </div>

                </section>


                {/* USER + PROBLEM */}

                <div className="grid gap-4 sm:grid-cols-2">

                  <section className="app-border rounded-xl border p-5">

                    <div className="flex items-center gap-2">

                      <FiUser className="text-blue-500" />

                      <h3 className="font-semibold">
                        User
                      </h3>

                    </div>

                    <p className="mt-4 font-medium">
                      {submission.userName}
                    </p>

                    <p className="app-text-secondary mt-1 text-sm">
                      {submission.userEmail}
                    </p>

                    <p className="app-text-muted mt-2 text-xs">
                      User #{submission.userId}
                    </p>

                  </section>


                  <section className="app-border rounded-xl border p-5">

                    <div className="flex items-center gap-2">

                      <FiFileText className="text-blue-500" />

                      <h3 className="font-semibold">
                        Problem
                      </h3>

                    </div>

                    <p className="mt-4 font-medium">
                      {submission.problemTitle}
                    </p>

                    <p className="app-text-muted mt-2 text-xs">
                      Problem #{submission.problemId}
                    </p>

                  </section>

                </div>


                {/* EXECUTION METRICS */}

                <div className="grid gap-4 sm:grid-cols-3">

                  <section className="app-border rounded-xl border p-4">

                    <FiCheckCircle className="text-emerald-500" />

                    <p className="app-text-muted mt-3 text-xs uppercase tracking-wider">
                      Test Cases
                    </p>

                    <p className="mt-1 font-semibold">

                      {submission.passedTestCases ?? 0}

                      {" / "}

                      {submission.totalTestCases ?? 0}

                    </p>

                  </section>


                  <section className="app-border rounded-xl border p-4">

                    <FiClock className="text-blue-500" />

                    <p className="app-text-muted mt-3 text-xs uppercase tracking-wider">
                      Runtime
                    </p>

                    <p className="mt-1 font-semibold">

                      {submission.executionTime !== null
                        ? `${submission.executionTime} ms`
                        : "—"}

                    </p>

                  </section>


                  <section className="app-border rounded-xl border p-4">

                    <FiCpu className="text-purple-500" />

                    <p className="app-text-muted mt-3 text-xs uppercase tracking-wider">
                      Memory
                    </p>

                    <p className="mt-1 font-semibold">

                      {submission.memoryUsed !== null
                        ? `${submission.memoryUsed} KB`
                        : "—"}

                    </p>

                  </section>

                </div>


                {/* LANGUAGE */}

                <section className="app-border rounded-xl border p-5">

                <div className="flex flex-wrap items-center gap-2">

                <FiCode className="text-blue-500" />

                <h3 className="font-semibold">
                    Source Code
                </h3>


                <div className="ml-auto flex items-center gap-2">

                    <span className="app-surface-secondary app-text-secondary rounded-md px-2.5 py-1 text-xs font-semibold">

                    {submission.language}

                    </span>


                    <button
                    type="button"
                    onClick={handleCopyCode}
                    className="app-border app-hover app-text-secondary inline-flex h-9 items-center gap-2 rounded-lg border px-3 text-sm font-medium transition"
                    >

                    {copied ? (

                        <>
                        <FiCheck
                            size={16}
                            className="text-emerald-500"
                        />

                        Copied
                        </>

                    ) : (

                        <>
                        <FiCopy size={16} />

                        Copy Code
                        </>

                    )}

                    </button>

                </div>

                </div>


                  <pre className="app-surface-secondary mt-4 max-h-[500px] overflow-auto rounded-lg p-5 text-sm leading-6">

                    <code>
                      {submission.sourceCode}
                    </code>

                  </pre>

                </section>


                {/* OUTPUT */}

                {submission.output && (

                  <section className="app-border rounded-xl border p-5">

                    <h3 className="font-semibold">
                      Output
                    </h3>

                    <pre className="app-surface-secondary mt-4 overflow-auto rounded-lg p-4 text-sm">
                      {submission.output}
                    </pre>

                  </section>

                )}


                {/* ERROR MESSAGE */}

                {submission.errorMessage && (

                  <section className="rounded-xl border border-red-500/30 bg-red-500/5 p-5">

                    <h3 className="font-semibold text-red-500">
                      Error Message
                    </h3>

                    <pre className="mt-4 overflow-auto whitespace-pre-wrap text-sm text-red-500">
                      {submission.errorMessage}
                    </pre>

                  </section>

                )}


                {/* HIDDEN TEST */}

                {submission.failedOnHiddenTest && (

                  <section className="flex items-start gap-3 rounded-xl border border-yellow-500/30 bg-yellow-500/5 p-5">

                    <FiAlertCircle
                      className="mt-0.5 shrink-0 text-yellow-500"
                      size={20}
                    />

                    <div>

                      <h3 className="font-semibold text-yellow-500">
                        Hidden Test Failure
                      </h3>

                      <p className="app-text-secondary mt-1 text-sm">
                        This submission failed on at least one hidden test case.
                      </p>

                    </div>

                  </section>

                )}

              </div>

            )}

        </div>

      </aside>

    </div>
  );
}