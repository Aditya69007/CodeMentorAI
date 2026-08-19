import {
  useEffect,
  useRef,
  useState,
} from "react";

import RunResultPanel
  from "../../components/workspace/RunResultPanel";

import type {
  ExecutionResult,
} from "../../types/execution";

import {
  useNavigate,
  useParams,
} from "react-router-dom";

import {
  Group,
  Panel,
  Separator,
  type PanelImperativeHandle,
} from "react-resizable-panels";

import {
  FiArrowLeft,
  FiLock,
  FiMaximize2,
  FiMinimize2,
  FiPlay,
  FiShield,
  FiStopCircle,
} from "react-icons/fi";

import {
  finishIndependentSolveSession,
  getActiveIndependentSolveSession,
  startIndependentSolveSession,
} from "../../services/independentSolveService";

import type {
  IndependentSolveSessionResponse,
} from "../../services/independentSolveService";

import ProblemDescriptionPanel
  from "../../components/workspace/ProblemDescriptionPanel";

import CodeEditorPanel
  from "../../components/workspace/CodeEditorPanel";

import SubmissionResultPanel
  from "../../components/workspace/SubmissionResultPanel";

import SubmissionHistoryPanel
  from "../../components/workspace/SubmissionHistoryPanel";

import {
  getProblemById,
} from "../../services/problemService";

import {
  createSubmission,
  runCode,
} from "../../services/submissionService";

import type {
  Problem,
} from "../../types/problem";

import type {
  SubmissionResponse,
} from "../../types/submission";

import SolutionEvolutionTimeline
  from "../../components/ai/SolutionEvolutionTimeline";

import IndependentSolveHistory
  from "../../components/independent/IndependentSolveHistory";

type Language =
  | "CPP"
  | "JAVA"
  | "PYTHON";

type BottomTab =
  | "testcase"
  | "result";


const starterCode: Record<Language, string> = {

  CPP: `#include <iostream>
using namespace std;

int main() {

    return 0;
}`,

  JAVA: `public class Main {
    public static void main(String[] args) {

    }
}`,

  PYTHON: `def main():
    pass

if __name__ == "__main__":
    main()`,

};


export default function ProblemSolvePage() {

  const { id } = useParams();

  const navigate = useNavigate();


  // ==========================================
  // PROBLEM STATE
  // ==========================================

  const [problem, setProblem] =
    useState<Problem | null>(null);


  // ==========================================
  // EDITOR STATE
  // ==========================================

  const [language, setLanguage] =
    useState<Language>("CPP");

  const [code, setCode] =
    useState(starterCode.CPP);

  const [fontSize, setFontSize] =
    useState(15);


  // ==========================================
  // PAGE STATE
  // ==========================================

  const [loading, setLoading] =
    useState(true);

  const [error, setError] =
    useState("");


  // ==========================================
  // EXECUTION STATE
  // ==========================================
  const [
    runResult,
    setRunResult,
  ] = useState<ExecutionResult | null>(
    null
  );

  const [
    resultMode,
    setResultMode,
  ] = useState<"run" | "submit">(
    "run"
  );
  const [running, setRunning] =
    useState(false);

  const [submitting, setSubmitting] =
    useState(false);

  const [
    submissionResult,
    setSubmissionResult,
  ] = useState<SubmissionResponse | null>(
    null
  );

  const [
    submissionError,
    setSubmissionError,
  ] = useState("");


  // ==========================================
  // SUBMISSION HISTORY STATE
  // ==========================================

  const [
    submissionRefreshKey,
    setSubmissionRefreshKey,
  ] = useState(0);

  // ==========================================
  // INDEPENDENT SOLVE MODE STATE
  // ==========================================

  const [
    independentSession,
    setIndependentSession,
  ] =
    useState<IndependentSolveSessionResponse | null>(
      null
    );

  const [
    independentLoading,
    setIndependentLoading,
  ] = useState(false);

  const [
    ,
    setIndependentMessage,
  ] = useState("");

  const [
    elapsedSeconds,
    setElapsedSeconds,
  ] = useState(0);

  const [
    completedIndependentSession,
    setCompletedIndependentSession,
  ] = useState<IndependentSolveSessionResponse | null>(
    null
  );

  // ==========================================
  // TAB STATE
  // ==========================================

const [leftTab, setLeftTab] =
  useState<
  "description" |
  "submissions" |
  "evolution" |
  "independent"
  >("description");


  const [bottomTab, setBottomTab] =
    useState<BottomTab>("testcase");


  // ==========================================
  // SELECTED TESTCASE STATE
  // ==========================================

  const [
    selectedTestcaseIndex,
    setSelectedTestcaseIndex,
  ] = useState(0);


  // ==========================================
  // RESULT PANEL STATE
  // ==========================================

  const resultPanelRef =
    useRef<PanelImperativeHandle | null>(
      null
    );

  const [
    resultMaximized,
    setResultMaximized,
  ] = useState(false);


  // ==========================================
  // LOAD PROBLEM
  // ==========================================

  useEffect(() => {

    const loadProblem = async () => {

      try {

        setLoading(true);

        setError("");

        const response =
          await getProblemById(
            Number(id)
          );

        setProblem(response);

        // Always open Case 1 when a new
        // problem is loaded.

        setSelectedTestcaseIndex(0);

        setBottomTab("testcase");

        setLeftTab("description");


      } catch (error) {

        console.error(error);

        setError(
          "Unable to load problem."
        );


      } finally {

        setLoading(false);

      }

    };


    loadProblem();

  }, [id]);

    // ==========================================
    // LOAD ACTIVE INDEPENDENT SESSION
    // ==========================================

    useEffect(() => {

      if (!problem) {
        return;
      }

      const loadActiveSession = async () => {

        try {

          const response =
            await getActiveIndependentSolveSession(
              problem.id
            );

          setIndependentSession(response);

        } catch{

          /*
          * 404 simply means there is no
          * active session for this problem.
          */

          setIndependentSession(null);

        }

      };

      loadActiveSession();

    }, [problem]);

  // ==========================================
  // INDEPENDENT MODE TIMER
  // ==========================================

  useEffect(() => {

    if (
      !independentSession?.active ||
      !independentSession.startedAt
    ) {


      return;

    }

    const updateTimer = () => {

    const rawStartedAt =
      independentSession.startedAt;

    const startedAt =
      new Date(
        rawStartedAt.endsWith("Z")
          ? rawStartedAt
          : `${rawStartedAt}Z`
      ).getTime();

      const currentTime =
        new Date().getTime();

      const seconds =
        Math.max(
          0,
          Math.floor(
            (currentTime - startedAt) / 1000
          )
        );

      setElapsedSeconds(seconds);

    };

    updateTimer();

    const interval =
      window.setInterval(
        updateTimer,
        1000
      );

    return () =>
      window.clearInterval(interval);

  }, [
    independentSession?.active,
    independentSession?.startedAt,
  ]);


  // ==========================================
  // SORT VISIBLE EXAMPLE TESTCASES
  // ==========================================

  const sortedExamples =
    problem
      ? [...(problem.examples ?? [])].sort(
          (a, b) =>
            a.orderIndex - b.orderIndex
        )
      : [];


  // ==========================================
  // CURRENT SELECTED TESTCASE
  // ==========================================

  const selectedExample =
    sortedExamples[
      selectedTestcaseIndex
    ];


  // ==========================================
  // TESTCASE INPUT
  // ==========================================

  const selectedTestcaseInput =
    selectedExample?.input ??
    problem?.sampleInput ??
    "";


  // ==========================================
  // TESTCASE EXPECTED OUTPUT
  // ==========================================

  const selectedTestcaseOutput =
    selectedExample?.output ??
    problem?.sampleOutput ??
    "";


  // ==========================================
  // RUN CODE
  // ==========================================

  const handleRun = async () => {

    if (!problem) {
      return;
    }


    if (!code.trim()) {

      setSubmissionError(
        "Write some code before running."
      );

      setBottomTab("result");

      return;

    }


    try {

      setRunning(true);

      setSubmissionError("");

      setRunResult(null);

      setSubmissionError("");

      setResultMode("run");


    if (!selectedExample) {

      setSubmissionError(
        "No testcase is available for this problem."
      );

      setBottomTab("result");

      return;
    }


    const response =
      await runCode({

        problemId: problem.id,

        exampleId: selectedExample.id,

        sourceCode: code,

        language,

      });


      setRunResult(response);
      setResultMode("run");
      setBottomTab("result");



    } catch (error) {

      console.error(error);

      setSubmissionError(
        "Failed to run code."
      );

      setBottomTab("result");


    } finally {

      setRunning(false);

    }

  };


  // ==========================================
  // LANGUAGE CHANGE
  // ==========================================

  const handleLanguageChange = (
    newLanguage: Language
  ) => {

    setLanguage(newLanguage);

    setCode(
      starterCode[newLanguage]
    );

    setSubmissionResult(null);

    setSubmissionError("");

    setBottomTab("testcase");

  };


  // ==========================================
  // SUBMIT CODE
  // ==========================================

  const handleSubmit = async () => {

    if (!problem) {
      return;
    }


    if (!code.trim()) {

      setSubmissionError(
        "Write some code before submitting."
      );

      setBottomTab("result");

      return;

    }


    try {

      setSubmitting(true);

      setResultMode("submit");

      setRunResult(null);

      setSubmissionError("");

      setSubmissionResult(null);


      const response =
        await createSubmission({

          problemId: problem.id,

          sourceCode: code,

          language,

        });

        setSubmissionResult(response);
        setResultMode("submit");

        setSubmissionRefreshKey(
          (current) =>
            current + 1
        );

        setLeftTab("submissions");
        setBottomTab("result");


    // ==========================================
    // AUTO FINISH INDEPENDENT MODE AFTER ACCEPTED
    // ==========================================

    if (
      response.status === "ACCEPTED" &&
      independentSession?.active
    ) {

      try {

        const finishedSession =
          await finishIndependentSolveSession(
            problem.id
          );

        // Store completed session so the
        // result banner can display it.
        setCompletedIndependentSession(
          finishedSession
        );

        // Remove active session from UI.
        setIndependentSession(null);

        // Reset timer.
        setElapsedSeconds(0);

        setIndependentMessage(
          finishedSession.message
        );

      } catch (error) {

        console.error(
          "Failed to auto-finish Independent Solve Mode:",
          error
        );

      }

    }


    } catch (error) {

      console.error(error);

      setSubmissionError(
        "Unable to submit code."
      );

      setBottomTab("result");


    } finally {

      setSubmitting(false);

    }

  };

// ==========================================
// START INDEPENDENT MODE
// ==========================================

const handleStartIndependentMode =
  async () => {

    if (!problem) {
      return;
    }

    try {

      setIndependentLoading(true);
      setIndependentMessage("");
      setCompletedIndependentSession(null);

      const response =
        await startIndependentSolveSession(
          problem.id
        );

      setIndependentSession(response);

      setIndependentMessage(
        response.message
      );

    } catch (error) {

      console.error(error);

      setIndependentMessage(
        "Unable to start Independent Solve Mode."
      );

    } finally {

      setIndependentLoading(false);

    }

  };


// ==========================================
// FINISH INDEPENDENT MODE
// ==========================================

const handleFinishIndependentMode =
  async () => {

    if (!problem) {
      return;
    }

    try {

      setIndependentLoading(true);

      const response =
        await finishIndependentSolveSession(
          problem.id
        );

    setCompletedIndependentSession(response);

    setIndependentSession(null);

    setElapsedSeconds(0);
    
    setIndependentMessage(
      response.message
    );

    } catch (error) {

      console.error(error);

      setIndependentMessage(
        "Unable to finish Independent Solve Mode."
      );

    } finally {

      setIndependentLoading(false);

    }

  };


  // ==========================================
  // FORMAT TIMER
  // ==========================================

  const formatIndependentTime = (
    totalSeconds: number
  ) => {

    const hours =
      Math.floor(
        totalSeconds / 3600
      );

    const minutes =
      Math.floor(
        (totalSeconds % 3600) / 60
      );

    const seconds =
      totalSeconds % 60;

    return [
      hours,
      minutes,
      seconds,
    ]
      .map((value) =>
        String(value).padStart(2, "0")
      )
      .join(":");

  };


  // ==========================================
  // MAXIMIZE / RESTORE RESULT PANEL
  // ==========================================

  const handleToggleResultPanel = () => {

    if (!resultPanelRef.current) {
      return;
    }


    if (resultMaximized) {

      resultPanelRef.current.resize("35%");

      setResultMaximized(false);

    } else {

      resultPanelRef.current.resize("75%");

      setResultMaximized(true);

    }

  };


  // ==========================================
  // USE OLD SUBMISSION CODE
  // ==========================================

  const handleUseCode = (
    sourceCode: string,
    submissionLanguage: Language
  ) => {

    setLanguage(
      submissionLanguage
    );

    setCode(
      sourceCode
    );

  };


  // ==========================================
  // LOADING
  // ==========================================

  if (loading) {

    return (

      <div className="app-background flex h-screen items-center justify-center">

        <div className="text-center">

          <div className="mx-auto h-8 w-8 animate-spin rounded-full border-2 border-blue-500 border-t-transparent" />

          <p className="app-text-secondary mt-4 text-sm">

            Loading workspace...

          </p>

        </div>

      </div>

    );

  }


  // ==========================================
  // ERROR
  // ==========================================

  if (error || !problem) {

    return (

      <div className="app-background flex h-screen items-center justify-center">

        <div className="text-center">

          <p className="text-red-500">

            {
              error ||
              "Problem not found."
            }

          </p>


          <button

            onClick={() =>
              navigate("/problems")
            }

            className="mt-4 rounded-md bg-blue-600 px-4 py-2 text-white"

          >

            Back to Problems

          </button>

        </div>

      </div>

    );

  }


  return (

    <div className="app-background flex h-screen min-h-0 flex-col overflow-hidden">


      {/* ==========================================
          WORKSPACE TOOLBAR
      ========================================== */}

      <header className="app-surface app-border flex h-14 shrink-0 items-center justify-between border-b px-3">


        <div className="flex min-w-0 items-center gap-3">


          <button

            onClick={() =>
              navigate("/problems")
            }

            className="app-hover flex h-9 w-9 shrink-0 items-center justify-center rounded-md"

            title="Back to problems"

          >

            <FiArrowLeft />

          </button>


          <div className="min-w-0">

            <p className="truncate text-sm font-semibold">

              CodeMentor AI

            </p>

            <p className="app-text-muted truncate text-xs">

              {problem.title}

            </p>

          </div>


        </div>


          <div className="flex items-center gap-2">


            {
              independentSession?.active
                ? (

                  <div className="flex items-center gap-2">

                    <div
                      className="
                        flex
                        items-center
                        gap-2
                        rounded-md
                        border
                        border-amber-500/30
                        bg-amber-500/10
                        px-3
                        py-2
                        text-sm
                        font-semibold
                        text-amber-500
                      "
                    >

                      <FiLock />

                      Independent

                      <span className="font-mono">

                        {
                          formatIndependentTime(
                            elapsedSeconds
                          )
                        }

                      </span>

                    </div>


                    <button

                      onClick={
                        handleFinishIndependentMode
                      }

                      disabled={
                        independentLoading
                      }

                      className="
                        flex
                        items-center
                        gap-2
                        rounded-md
                        border
                        border-red-500/30
                        bg-red-500/10
                        px-3
                        py-2
                        text-sm
                        font-semibold
                        text-red-500
                        hover:bg-red-500/20
                        disabled:opacity-50
                      "

                    >

                      <FiStopCircle />

                      {
                        independentLoading
                          ? "Finishing..."
                          : "Finish Mode"
                      }

                    </button>

                  </div>

                )
                : (

                  <button

                    onClick={
                      handleStartIndependentMode
                    }

                    disabled={
                      independentLoading
                    }

                    className="
                      flex
                      items-center
                      gap-2
                      rounded-md
                      border
                      border-violet-500/30
                      bg-violet-500/10
                      px-3
                      py-2
                      text-sm
                      font-semibold
                      text-violet-500
                      hover:bg-violet-500/20
                      disabled:opacity-50
                    "

                  >

                    <FiShield />

                    {
                      independentLoading
                        ? "Starting..."
                        : "Solve Without AI"
                    }

                  </button>

                )
            }


            <button

              onClick={handleRun}

            disabled={
              running ||
              submitting
            }

            className="
              app-surface-secondary
              app-border
              flex
              items-center
              gap-2
              rounded-md
              border
              px-4
              py-2
              text-sm
              font-semibold
              hover:bg-slate-700
              disabled:cursor-not-allowed
              disabled:opacity-50
            "

          >

            <FiPlay />

            {
              running
                ? "Running..."
                : "Run"
            }

          </button>


          <button

            onClick={handleSubmit}

            disabled={
              submitting ||
              running
            }

            className="
              flex
              items-center
              gap-2
              rounded-md
              bg-emerald-600
              px-5
              py-2
              text-sm
              font-semibold
              text-white
              hover:bg-emerald-500
              disabled:cursor-not-allowed
              disabled:opacity-50
            "

          >

            <FiPlay />

            {
              submitting
                ? "Submitting..."
                : "Submit"
            }

          </button>


        </div>


      </header>


    {
      completedIndependentSession && (

        <div className="app-border shrink-0 border-b px-4 py-3">

          <div
            className="
              mx-auto
              flex
              max-w-5xl
              items-center
              justify-between
              gap-4
              rounded-lg
              border
              border-emerald-500/30
              bg-emerald-500/10
              px-5
              py-4
            "
          >

            <div>

              <div className="flex items-center gap-2">

                <FiShield className="text-emerald-500" />

                <h3 className="font-semibold text-emerald-500">

                  Independent Solve Completed

                </h3>

              </div>

              <p className="app-text-secondary mt-1 text-sm">

                {
                  completedIndependentSession.solvedIndependently
                    ? "You solved this problem successfully without AI assistance."
                    : "Session completed. Keep practicing independent problem-solving."
                }

              </p>

            </div>


            <div className="flex items-center gap-6">

              <div className="text-center">

                <p className="app-text-muted text-xs">
                  Result
                </p>

                <p className="mt-1 text-sm font-semibold">

                  {
                    completedIndependentSession.solvedIndependently
                      ? "Solved"
                      : "Not Solved"
                  }

                </p>

              </div>


              <div className="text-center">

                <p className="app-text-muted text-xs">
                  Time
                </p>

                <p className="mt-1 font-mono text-sm font-semibold">

                  {
                    formatIndependentTime(
                      completedIndependentSession.durationSeconds ?? 0
                    )
                  }

                </p>

              </div>


              <div className="text-center">

                <p className="app-text-muted text-xs">
                  Attempts
                </p>

                <p className="mt-1 text-sm font-semibold">

                  {
                    completedIndependentSession.submissionsDuringSession
                  }

                </p>

              </div>


              <div className="text-center">

                <p className="app-text-muted text-xs">
                  AI Assistance
                </p>

                <p className="mt-1 text-sm font-semibold text-emerald-500">

                  None

                </p>

              </div>


              <button

                type="button"

                onClick={() =>
                  setCompletedIndependentSession(null)
                }

                className="
                  app-hover
                  app-text-secondary
                  flex
                  h-8
                  w-8
                  items-center
                  justify-center
                  rounded-md
                  text-lg
                "

                title="Close result"

              >

                ×

              </button>

            </div>

          </div>

        </div>

      )
    }

      {/* ==========================================
          RESIZABLE WORKSPACE
      ========================================== */}

      <div className="min-h-0 flex-1 overflow-hidden p-1">


        <Group orientation="horizontal">


        {/* ==========================================
            LEFT PANEL
        ========================================== */}

          <Panel
            defaultSize={45}
            minSize={30}
          >

          <div className="app-surface app-border flex h-full min-h-0 flex-col overflow-hidden rounded-md border">

            {/* ==========================================
                LEFT PANEL TABS
            ========================================== */}

        <div className="app-border flex h-11 shrink-0 items-center overflow-x-auto border-b">

          <button
            onClick={() => setLeftTab("description")}
            className={`
              h-full
              min-w-0
              flex-1
              whitespace-nowrap
              border-b-2
              px-2
              text-xs
              font-medium
              transition-colors
              sm:text-sm
              ${
                leftTab === "description"
                  ? "border-blue-500 text-blue-500"
                  : "app-text-secondary border-transparent"
              }
            `}
          >
            Description
          </button>


          <button
            onClick={() => setLeftTab("submissions")}
            className={`
              h-full
              min-w-0
              flex-1
              whitespace-nowrap
              border-b-2
              px-2
              text-xs
              font-medium
              transition-colors
              sm:text-sm
              ${
                leftTab === "submissions"
                  ? "border-blue-500 text-blue-500"
                  : "app-text-secondary border-transparent"
              }
            `}
          >
            Submissions
          </button>


          <button
            onClick={() => setLeftTab("evolution")}
            className={`
              h-full
              min-w-0
              flex-1
              whitespace-nowrap
              border-b-2
              px-2
              text-xs
              font-medium
              transition-colors
              sm:text-sm
              ${
                leftTab === "evolution"
                  ? "border-blue-500 text-blue-500"
                  : "app-text-secondary border-transparent"
              }
            `}
          >
            Evolution
          </button>


          <button
            onClick={() => setLeftTab("independent")}
            className={`
              h-full
              min-w-0
              flex-1
              whitespace-nowrap
              border-b-2
              px-2
              text-xs
              font-medium
              transition-colors
              sm:text-sm
              ${
                leftTab === "independent"
                  ? "border-violet-500 text-violet-500"
                  : "app-text-secondary border-transparent"
              }
            `}
          >
            Independent
          </button>

        </div>


            {/* ==========================================
                LEFT PANEL CONTENT
            ========================================== */}

            <div className="min-h-0 flex-1">


              {/* ==========================================
                  DESCRIPTION CONTENT
              ========================================== */}

              {
                leftTab === "description" && (

                  <ProblemDescriptionPanel
                    problem={problem}
                  />

                )
              }


              {/* ==========================================
                  SUBMISSIONS CONTENT
              ========================================== */}

              {
                leftTab === "submissions" && (

                  <SubmissionHistoryPanel

                    problemId={
                      problem.id
                    }

                    refreshKey={
                      submissionRefreshKey
                    }

                    onSelectSubmission={(
                      submission
                    ) => {

                      setSubmissionResult(
                        submission
                      );

                      setSubmissionError("");

                      setResultMode(
                        "submit"
                      );

                      setBottomTab(
                        "result"
                      );

                    }}

                  />

                )
              }


              {/* ==========================================
                  EVOLUTION CONTENT
              ========================================== */}

              {
                leftTab === "evolution" && (

                  <div className="h-full overflow-y-auto p-4">

                    <SolutionEvolutionTimeline
                      problemId={problem.id}
                    />

                  </div>

                )
              }


              {/* ==========================================
                  INDEPENDENT SOLVE HISTORY CONTENT
              ========================================== */}

              {
                leftTab === "independent" && (

                  <IndependentSolveHistory
                    problemId={problem.id}
                    refreshKey={submissionRefreshKey}
                  />

                )
              }


            </div>

          </div>

        </Panel>


          <Separator className="group relative flex w-2 cursor-col-resize items-center justify-center">

            <div className="h-full w-px bg-slate-700 transition-all group-hover:w-1 group-hover:bg-blue-500" />

          </Separator>


          {/* ==========================================
              RIGHT PANEL
          ========================================== */}

          <Panel
            defaultSize={55}
            minSize={30}
          >


            <Group orientation="vertical">


              {/* ==========================================
                  EDITOR
              ========================================== */}

              <Panel
                defaultSize={65}
                minSize={25}
              >


                <div className="app-surface app-border h-full overflow-hidden rounded-md border">


                  <CodeEditorPanel

                    language={language}

                    code={code}

                    fontSize={fontSize}

                    submitting={
                      submitting ||
                      running
                    }

                    onLanguageChange={
                      handleLanguageChange
                    }

                    onCodeChange={
                      setCode
                    }

                    onFontSizeChange={
                      setFontSize
                    }

                  />


                </div>


              </Panel>


              <Separator className="group relative flex h-2 cursor-row-resize items-center justify-center">

                <div className="h-px w-full bg-slate-700 transition-all group-hover:h-1 group-hover:bg-blue-500" />

              </Separator>


              {/* ==========================================
                  RESULT / TESTCASE PANEL
              ========================================== */}

              <Panel
                panelRef={resultPanelRef}
                defaultSize={35}
                minSize={15}
              >


                <div className="app-surface app-border flex h-full min-h-0 flex-col overflow-hidden rounded-md border">


                  {/* ==========================================
                      BOTTOM HEADER
                  ========================================== */}

                  <div className="app-border flex h-11 shrink-0 items-center justify-between border-b px-2">


                    <div className="flex h-full items-center">


                      <button

                        onClick={() =>
                          setBottomTab(
                            "testcase"
                          )
                        }

                        className={`h-full border-b-2 px-4 text-sm font-medium ${
                          bottomTab === "testcase"
                            ? "border-emerald-500 text-emerald-500"
                            : "border-transparent app-text-secondary"
                        }`}

                      >

                        Testcase

                      </button>


                      <button

                        onClick={() =>
                          setBottomTab(
                            "result"
                          )
                        }

                        className={`h-full border-b-2 px-4 text-sm font-medium ${
                          bottomTab === "result"
                            ? "border-emerald-500 text-emerald-500"
                            : "border-transparent app-text-secondary"
                        }`}

                      >

                        Test Result

                      </button>


                    </div>


                    <button

                      type="button"

                      onClick={
                        handleToggleResultPanel
                      }

                      className="app-hover flex h-8 w-8 items-center justify-center rounded-md"

                      title={
                        resultMaximized
                          ? "Restore panel"
                          : "Maximize panel"
                      }

                    >

                      {
                        resultMaximized
                          ? <FiMinimize2 />
                          : <FiMaximize2 />
                      }

                    </button>


                  </div>


                  {/* ==========================================
                      BOTTOM CONTENT
                  ========================================== */}

                  <div className="min-h-0 flex-1 overflow-hidden">


                    {/* ==========================================
                        TESTCASE CONTENT
                    ========================================== */}

                    {
                      bottomTab === "testcase" && (

                        <div className="h-full overflow-y-auto p-5">


                          {/* ==========================================
                              TESTCASE TABS
                          ========================================== */}

                          <div
                            className="
                              flex
                              flex-wrap
                              items-center
                              gap-2
                            "
                          >


                            {
                              sortedExamples.length > 0
                                ? (

                                  sortedExamples.map(
                                    (example, index) => (

                                      <button

                                        key={
                                          example.id
                                        }

                                        type="button"

                                        onClick={() =>
                                          setSelectedTestcaseIndex(
                                            index
                                          )
                                        }

                                        className={`
                                          rounded-md
                                          px-4
                                          py-2
                                          text-sm
                                          font-semibold
                                          transition-colors

                                          ${
                                            selectedTestcaseIndex ===
                                            index

                                              ? `
                                                bg-emerald-500/15
                                                text-emerald-500
                                              `

                                              : `
                                                app-surface-secondary
                                                app-text-secondary
                                                app-hover
                                              `
                                          }
                                        `}

                                      >

                                        Case {index + 1}

                                      </button>

                                    )
                                  )

                                )
                                : (

                                  <button
                                    type="button"
                                    className="
                                      bg-emerald-500/15
                                      rounded-md
                                      px-4
                                      py-2
                                      text-sm
                                      font-semibold
                                      text-emerald-500
                                    "
                                  >

                                    Case 1

                                  </button>

                                )
                            }


                          </div>


                          {/* ==========================================
                              SELECTED CASE INFORMATION
                          ========================================== */}

                          {
                            sortedExamples.length > 0 && (

                              <p className="app-text-muted mt-4 text-xs">

                                Example {
                                  selectedTestcaseIndex + 1
                                } of {
                                  sortedExamples.length
                                }

                              </p>

                            )
                          }


                          {/* ==========================================
                              INPUT
                          ========================================== */}

                          <div className="mt-5">

                            <p className="app-text-secondary text-sm font-medium">

                              Input

                            </p>


                            <pre
                              className="
                                app-surface-secondary
                                app-border
                                mt-2
                                min-h-[52px]
                                overflow-x-auto
                                whitespace-pre-wrap
                                rounded-md
                                border
                                p-4
                                font-mono
                                text-sm
                                leading-6
                              "
                            >

                              {
                                selectedTestcaseInput.trim()
                              }

                            </pre>


                          </div>


                          {/* ==========================================
                              EXPECTED OUTPUT
                          ========================================== */}

                          <div className="mt-5">

                            <p className="app-text-secondary text-sm font-medium">

                              Expected Output

                            </p>


                            <pre
                              className="
                                app-surface-secondary
                                app-border
                                mt-2
                                min-h-[52px]
                                overflow-x-auto
                                whitespace-pre-wrap
                                rounded-md
                                border
                                p-4
                                font-mono
                                text-sm
                                leading-6
                              "
                            >

                              {
                                selectedTestcaseOutput.trim()
                              }

                            </pre>


                          </div>


                          {/* ==========================================
                              EXPLANATION
                          ========================================== */}

                          {
                            selectedExample
                              ?.explanation
                              ?.trim() && (

                              <div className="mt-5">

                                <p className="app-text-secondary text-sm font-medium">

                                  Explanation

                                </p>


                                <div
                                  className="
                                    app-surface-secondary
                                    app-border
                                    mt-2
                                    rounded-md
                                    border
                                    p-4
                                  "
                                >

                                  <p
                                    className="
                                      app-text-secondary
                                      whitespace-pre-line
                                      text-sm
                                      leading-6
                                    "
                                  >

                                    {
                                      selectedExample
                                        .explanation
                                        .trim()
                                    }

                                  </p>


                                </div>


                              </div>

                            )
                          }


                        </div>

                      )
                    }


                    {/* ==========================================
                        RESULT CONTENT
                    ========================================== */}

                    {
                      bottomTab === "result" && (

                        resultMode === "run"

                          ? (

                            <RunResultPanel

                              result={
                                runResult
                              }

                              error={
                                submissionError
                              }

                              input={
                                selectedTestcaseInput
                              }

                              expectedOutput={
                                selectedTestcaseOutput
                              }

                              caseNumber={
                                selectedTestcaseIndex + 1
                              }

                            />

                          )

                          : (

                          <SubmissionResultPanel

                            key={
                              submissionResult?.id ??
                              "no-submission"
                            }

                            result={
                              submissionResult
                            }

                            error={
                              submissionError
                            }

                            independentModeActive={
                              Boolean(
                                independentSession?.active
                              )
                            }

                            onUseCode={
                              handleUseCode
                            }

                          />

                          )

                      )
                    }


                  </div>


                </div>


              </Panel>


            </Group>


          </Panel>


        </Group>


      </div>


    </div>

  );

}