import {
  useCallback,
  useEffect,
  useState,
} from "react";

import {
  FiActivity,
  FiAlertCircle,
  FiArrowRight,
  FiAward,
  FiCheckCircle,
  FiClock,
  FiLoader,
  FiPlay,
  FiRefreshCw,
  FiTarget,
  FiTrendingUp,
} from "react-icons/fi";

import {
  getActiveInterview,
  getInterviewDetails,
  getInterviewHistory,
  startInterview,
  submitInterviewAnswer,
} from "../../services/personalizedInterviewService";

import type {
  InterviewSessionResponse,
} from "../../services/personalizedInterviewService";


// =====================================================
// COMPONENT
// =====================================================

export default function PersonalizedInterviewPage() {

  const [
    activeInterview,
    setActiveInterview,
  ] = useState<InterviewSessionResponse | null>(null);


  const [
    interviewHistory,
    setInterviewHistory,
  ] = useState<InterviewSessionResponse[]>([]);


  const [
    selectedInterview,
    setSelectedInterview,
  ] = useState<InterviewSessionResponse | null>(null);


  const [
    answer,
    setAnswer,
  ] = useState("");


  const [
    loading,
    setLoading,
  ] = useState(true);


  const [
    starting,
    setStarting,
  ] = useState(false);


  const [
    submitting,
    setSubmitting,
  ] = useState(false);


  const [
    historyLoading,
    setHistoryLoading,
  ] = useState(false);


  const [
    error,
    setError,
  ] = useState<string | null>(null);


  // =====================================================
  // LOAD PAGE DATA
  // =====================================================

  const loadPageData = useCallback(async () => {

    try {

      setLoading(true);

      setError(null);


      const [
        activeResponse,
        historyResponse,
      ] = await Promise.all([

        getActiveInterview(),

        getInterviewHistory(),

      ]);


      setActiveInterview(
        activeResponse
      );


      setInterviewHistory(
        historyResponse ?? []
      );

    } catch (requestError) {

      console.error(
        "Failed to load interview data:",
        requestError
      );


      setError(
        "Unable to load your personalized interview data."
      );

    } finally {

      setLoading(false);

    }

  }, []);


    useEffect(() => {

    const initializePage = async () => {

        await loadPageData();

    };

    initializePage();

    }, [loadPageData]);


  // =====================================================
  // START INTERVIEW
  // =====================================================

  const handleStartInterview = async () => {

    try {

      setStarting(true);

      setError(null);

      setSelectedInterview(null);


      const response =
        await startInterview();


      setActiveInterview(
        response
      );


      setAnswer("");

    } catch (requestError) {

      console.error(
        "Failed to start interview:",
        requestError
      );


      setError(
        "Unable to start your personalized interview."
      );

    } finally {

      setStarting(false);

    }

  };


  // =====================================================
  // SUBMIT ANSWER
  // =====================================================

  const handleSubmitAnswer = async () => {

    if (
      !activeInterview
      ||
      !activeInterview.currentQuestion
      ||
      !answer.trim()
    ) {

      return;

    }


    try {

      setSubmitting(true);

      setError(null);


      const response =
        await submitInterviewAnswer(

          activeInterview.sessionId,

          activeInterview.currentQuestion.questionId,

          answer.trim()

        );


      setAnswer("");


      if (response.active) {

        setActiveInterview(
          response
        );

      } else {

        setActiveInterview(
          null
        );


        const completedInterview =
          await getInterviewDetails(
            response.sessionId
          );


        setSelectedInterview(
          completedInterview
        );


        await loadHistory();

      }

    } catch (requestError) {

      console.error(
        "Failed to submit interview answer:",
        requestError
      );


      setError(
        "Unable to evaluate your answer."
      );

    } finally {

      setSubmitting(false);

    }

  };


  // =====================================================
  // LOAD HISTORY
  // =====================================================

  const loadHistory = async () => {

    try {

      setHistoryLoading(true);


      const response =
        await getInterviewHistory();


      setInterviewHistory(
        response ?? []
      );

    } catch (requestError) {

      console.error(
        "Failed to refresh interview history:",
        requestError
      );

    } finally {

      setHistoryLoading(false);

    }

  };


  // =====================================================
  // VIEW INTERVIEW DETAILS
  // =====================================================

  const handleViewInterview =
    async (
      sessionId: number
    ) => {

      try {

        setError(null);


        const response =
          await getInterviewDetails(
            sessionId
          );


        setSelectedInterview(
          response
        );

      } catch (requestError) {

        console.error(
          "Failed to load interview details:",
          requestError
        );


        setError(
          "Unable to load interview details."
        );

      }

    };


  // =====================================================
  // FORMAT DATE
  // =====================================================

  const formatDate = (
    value: string | null
  ) => {

    if (!value) {

      return "—";

    }


    return new Date(
      value
    ).toLocaleString();

  };


  // =====================================================
  // SCORE STYLE
  // =====================================================

  const getScoreStyle = (
    score: number
  ) => {

    if (score >= 80) {

      return "text-emerald-500";

    }


    if (score >= 60) {

      return "text-blue-500";

    }


    if (score >= 40) {

      return "text-amber-500";

    }


    return "text-red-500";

  };


  // =====================================================
  // LOADING
  // =====================================================

  if (loading) {

    return (

      <main className="mx-auto flex min-h-[70vh] max-w-[1500px] items-center justify-center px-4">

        <div className="text-center">

          <FiLoader
            className="mx-auto animate-spin text-blue-500"
            size={34}
          />

          <p className="app-text-secondary mt-4">

            Loading your personalized interview...

          </p>

        </div>

      </main>

    );

  }


  // =====================================================
  // PAGE
  // =====================================================

  return (

    <main className="mx-auto max-w-[1500px] px-4 py-8 sm:px-6">


      {/* =====================================================
          PAGE HEADER
      ===================================================== */}

      <section className="mb-8 flex flex-col gap-4 lg:flex-row lg:items-center lg:justify-between">


        <div>

          <div className="mb-2 flex items-center gap-3">

            <FiActivity
              className="text-blue-500"
              size={28}
            />

            <h1 className="text-3xl font-bold tracking-tight">

              Personalized Interview

            </h1>

          </div>


          <p className="app-text-secondary">

            AI-powered technical interviews adapted to your coding progress,
            mistakes, and independent problem-solving performance.

          </p>

        </div>


        {
          !activeInterview && (

            <button

              onClick={handleStartInterview}

              disabled={starting}

              className="
                flex
                items-center
                justify-center
                gap-2
                rounded-lg
                bg-blue-600
                px-5
                py-3
                font-medium
                text-white
                transition
                hover:bg-blue-700
                disabled:cursor-not-allowed
                disabled:opacity-60
              "

            >

              {
                starting
                  ? (
                    <FiLoader
                      className="animate-spin"
                    />
                  )
                  : (
                    <FiPlay />
                  )
              }

              {
                starting
                  ? "Preparing Interview..."
                  : "Start New Interview"
              }

            </button>

          )
        }


      </section>


      {/* =====================================================
          ERROR
      ===================================================== */}

      {
        error && (

          <div className="mb-6 flex items-center gap-3 rounded-lg border border-red-500/30 bg-red-500/10 p-4 text-red-500">

            <FiAlertCircle size={20} />

            <span>

              {error}

            </span>

          </div>

        )
      }


      {/* =====================================================
          ACTIVE INTERVIEW
      ===================================================== */}

      {
        activeInterview
        &&
        activeInterview.currentQuestion
        &&
        (

          <section className="app-surface app-border mb-8 overflow-hidden rounded-xl border">


            {/* INTERVIEW HEADER */}

            <div className="app-border border-b p-6">


              <div className="flex flex-col gap-4 lg:flex-row lg:items-center lg:justify-between">


                <div>


                  <div className="mb-3 flex flex-wrap items-center gap-2">


                    <span className="rounded-full bg-blue-500/10 px-3 py-1 text-xs font-semibold text-blue-500">

                      {activeInterview.interviewLevel}

                    </span>


                    <span className="rounded-full bg-purple-500/10 px-3 py-1 text-xs font-semibold text-purple-500">

                      {activeInterview.currentQuestion.questionType}

                    </span>


                    <span className="app-text-secondary text-sm">

                      {
                        activeInterview.currentQuestion.concept
                      }

                    </span>


                  </div>


                  <h2 className="text-xl font-bold">

                    Interview in Progress

                  </h2>


                </div>


                <div className="text-right">


                  <p className="app-text-secondary text-sm">

                    Question

                  </p>


                  <p className="text-2xl font-bold">

                    {
                      activeInterview.currentQuestion.questionNumber
                    }

                    {" / "}

                    {
                      activeInterview.totalQuestions
                    }

                  </p>


                </div>


              </div>


              {/* PROGRESS BAR */}

              <div className="mt-5 h-2 overflow-hidden rounded-full bg-slate-500/15">

                <div

                  className="h-full rounded-full bg-blue-600 transition-all duration-500"

                  style={{
                    width:
                      `${
                        (
                          activeInterview.currentQuestion.questionNumber
                          /
                          activeInterview.totalQuestions
                        )
                        *
                        100
                      }%`,
                  }}

                />

              </div>


            </div>


            {/* QUESTION */}

            <div className="p-6">


              <div className="mb-6">


                <div className="mb-3 flex items-center gap-2 text-sm font-semibold text-blue-500">

                  <FiTarget />

                  Current Question

                </div>


                <p className="text-lg leading-8">

                  {
                    activeInterview.currentQuestion.question
                  }

                </p>


              </div>


              {/* ANSWER */}

              <div>


                <label className="mb-2 block text-sm font-semibold">

                  Your Answer

                </label>


                <textarea

                  value={answer}

                  onChange={(event) =>
                    setAnswer(
                      event.target.value
                    )
                  }

                  disabled={submitting}

                  placeholder="
Explain your approach clearly.

You can include:
• Algorithm or solution strategy
• Data structures used
• Time complexity
• Space complexity
• Edge cases
                  "

                  className="
                    app-background
                    app-border
                    min-h-[220px]
                    w-full
                    resize-y
                    rounded-lg
                    border
                    p-4
                    leading-7
                    outline-none
                    transition
                    focus:border-blue-500
                    disabled:opacity-60
                  "

                />


                <div className="mt-4 flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">


                  <p className="app-text-secondary text-sm">

                    {
                      answer.trim().length
                    }

                    {" characters"}

                  </p>


                  <button

                    onClick={handleSubmitAnswer}

                    disabled={
                      submitting
                      ||
                      !answer.trim()
                    }

                    className="
                      flex
                      items-center
                      justify-center
                      gap-2
                      rounded-lg
                      bg-blue-600
                      px-5
                      py-3
                      font-medium
                      text-white
                      transition
                      hover:bg-blue-700
                      disabled:cursor-not-allowed
                      disabled:opacity-60
                    "

                  >

                    {
                      submitting
                        ? (
                          <FiLoader
                            className="animate-spin"
                          />
                        )
                        : (
                          <FiArrowRight />
                        )
                    }


                    {
                      submitting
                        ? "AI Evaluating Answer..."
                        : "Submit Answer"
                    }


                  </button>


                </div>


              </div>


            </div>


          </section>

        )
      }


      {/* =====================================================
          NO ACTIVE INTERVIEW
      ===================================================== */}

      {
        !activeInterview
        &&
        !selectedInterview
        &&
        (

          <section className="app-surface app-border mb-8 rounded-xl border p-10 text-center">


            <div className="mx-auto flex h-16 w-16 items-center justify-center rounded-full bg-blue-500/10 text-blue-500">

              <FiTarget size={30} />

            </div>


            <h2 className="mt-5 text-2xl font-bold">

              Ready for Your Next Interview?

            </h2>


            <p className="app-text-secondary mx-auto mt-3 max-w-2xl leading-7">

              Your interview difficulty and questions are personalized using
              your developer level, recurring mistakes, concept growth,
              hint dependency, and independent solve performance.

            </p>


          </section>

        )
      }


      {/* =====================================================
          SELECTED / COMPLETED INTERVIEW
      ===================================================== */}

      {
        selectedInterview
        &&
        !selectedInterview.active
        &&
        (

          <section className="mb-8 space-y-6">


            {/* RESULT HEADER */}

            <div className="app-surface app-border rounded-xl border p-6">


              <div className="flex flex-col gap-6 lg:flex-row lg:items-center">


            <div className="flex shrink-0 items-center justify-center">

                <div className="relative flex h-36 w-36 items-center justify-center">

                  <svg
                    className="absolute h-full w-full -rotate-90"
                    viewBox="0 0 100 100"
                  >

                    {/* BACKGROUND CIRCLE */}

                    <circle
                      cx="50"
                      cy="50"
                      r="44"
                      fill="none"
                      stroke="currentColor"
                      strokeWidth="7"
                      className="text-purple-500/20"
                    />


                    {/* SCORE PROGRESS CIRCLE */}

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
                        100 -
                        Math.max(
                          0,
                          Math.min(
                            selectedInterview.finalScore ?? 0,
                            100
                          )
                        )
                      }
                      className="
                        text-purple-500
                        transition-all
                        duration-700
                      "
                    />

                  </svg>


                  {/* SCORE TEXT */}

                  <div className="relative text-center">

                    <p
                      className={`
                        text-4xl
                        font-bold
                        ${
                          getScoreStyle(
                            selectedInterview.finalScore ?? 0
                          )
                        }
                      `}
                    >

                      {
                        selectedInterview.finalScore ?? 0
                      }

                    </p>


                    <p className="app-text-secondary mt-1 text-xs">

                      / 100

                    </p>

                  </div>

                </div>

            </div>


                <div className="flex-1">


                  <div className="mb-3 flex flex-wrap items-center gap-2">


                    <span className="rounded-full bg-purple-500/10 px-3 py-1 text-xs font-semibold text-purple-500">

                      {
                        selectedInterview.interviewLevel
                      }

                    </span>


                    <span className="rounded-full bg-emerald-500/10 px-3 py-1 text-xs font-semibold text-emerald-500">

                      COMPLETED

                    </span>


                  </div>


                  <h2 className="text-2xl font-bold">

                    Interview Assessment

                  </h2>


                  <p className="app-text-secondary mt-3 leading-7">

                    {
                      selectedInterview.finalFeedback
                    }

                  </p>


                </div>


              </div>


            </div>


            {/* QUESTION RESULTS */}

            <div className="app-surface app-border rounded-xl border p-6">


              <div className="mb-6 flex items-center gap-3">


                <FiTrendingUp
                  className="text-purple-500"
                  size={22}
                />


                <div>


                  <h2 className="text-xl font-bold">

                    Question Performance

                  </h2>


                  <p className="app-text-secondary text-sm">

                    Complete AI evaluation of your interview answers.

                  </p>


                </div>


              </div>


              <div className="space-y-5">


                {
                  (
                    selectedInterview.questions
                    ??
                    []
                  )
                    .map((question) => (

                      <article

                        key={question.questionId}

                        className="app-background app-border rounded-lg border p-5"

                      >


                        <div className="mb-4 flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">


                          <div className="flex flex-wrap items-center gap-2">


                            <span className="font-bold">

                              Question {
                                question.questionNumber
                              }

                            </span>


                            <span className="rounded-full bg-blue-500/10 px-2.5 py-1 text-xs font-medium text-blue-500">

                              {
                                question.concept
                              }

                            </span>


                          </div>


                          <div
                            className={`
                              text-xl
                              font-bold
                              ${
                                getScoreStyle(
                                  question.answerScore ?? 0
                                )
                              }
                            `}
                          >

                            {
                              question.answerScore ?? 0
                            }

                            /100

                          </div>


                        </div>


                        <p className="leading-7">

                          {
                            question.question
                          }

                        </p>


                        {
                          question.aiFeedback && (

                            <div className="mt-5 rounded-lg bg-blue-500/5 p-4">


                              <div className="mb-2 flex items-center gap-2 font-semibold text-blue-500">

                                <FiActivity />

                                AI Feedback

                              </div>


                              <p className="app-text-secondary leading-7">

                                {
                                  question.aiFeedback
                                }

                              </p>


                            </div>

                          )
                        }


                        <div className="mt-4 grid gap-4 lg:grid-cols-2">


                          {
                            question.strengths && (

                              <div className="rounded-lg bg-emerald-500/5 p-4">


                                <div className="mb-2 flex items-center gap-2 font-semibold text-emerald-500">

                                  <FiCheckCircle />

                                  Strengths

                                </div>


                                <p className="app-text-secondary leading-7">

                                  {
                                    question.strengths
                                  }

                                </p>


                              </div>

                            )
                          }


                          {
                            question.improvements && (

                              <div className="rounded-lg bg-amber-500/5 p-4">


                                <div className="mb-2 flex items-center gap-2 font-semibold text-amber-500">

                                  <FiTrendingUp />

                                  Improvements

                                </div>


                                <p className="app-text-secondary leading-7">

                                  {
                                    question.improvements
                                  }

                                </p>


                              </div>

                            )
                          }


                        </div>


                      </article>

                    ))
                }


              </div>


            </div>


          </section>

        )
      }


      {/* =====================================================
          INTERVIEW HISTORY
      ===================================================== */}

      <section className="app-surface app-border rounded-xl border p-6">


        <div className="mb-6 flex items-center justify-between gap-4">


          <div className="flex items-center gap-3">


            <FiClock
              className="text-purple-500"
              size={22}
            />


            <div>


              <h2 className="text-xl font-bold">

                Interview History

              </h2>


              <p className="app-text-secondary text-sm">

                Review your previous personalized interviews.

              </p>


            </div>


          </div>


          <button

            onClick={loadHistory}

            disabled={historyLoading}

            className="app-hover app-border rounded-md border p-2"

            title="Refresh interview history"

          >

            <FiRefreshCw
              className={
                historyLoading
                  ? "animate-spin"
                  : ""
              }
            />

          </button>


        </div>


        {
          interviewHistory.length === 0
            ? (

              <div className="app-background rounded-lg p-8 text-center">


                <FiAward
                  className="app-text-secondary mx-auto"
                  size={30}
                />


                <p className="app-text-secondary mt-3">

                  Complete your first personalized interview to build your history.

                </p>


              </div>

            )
            : (

              <div className="space-y-3">


                {
                  interviewHistory.map(
                    (interview) => (

                      <button

                        key={interview.sessionId}

                        onClick={() =>
                          handleViewInterview(
                            interview.sessionId
                          )
                        }

                        className="
                          app-background
                          app-border
                          app-hover
                          flex
                          w-full
                          flex-col
                          gap-4
                          rounded-lg
                          border
                          p-4
                          text-left
                          sm:flex-row
                          sm:items-center
                          sm:justify-between
                        "

                      >


                        <div>


                          <div className="mb-1 flex flex-wrap items-center gap-2">


                            <span className="font-semibold">

                              Interview #{interview.sessionId}

                            </span>


                            <span className="rounded-full bg-purple-500/10 px-2.5 py-1 text-xs font-medium text-purple-500">

                              {
                                interview.interviewLevel
                              }

                            </span>


                          </div>


                          <p className="app-text-secondary text-sm">

                            {
                              formatDate(
                                interview.startedAt
                              )
                            }

                          </p>


                        </div>


                        <div className="flex items-center gap-5">


                          <div className="text-right">


                            <p className="app-text-secondary text-xs">

                              Final Score

                            </p>


                            <p
                              className={`
                                text-xl
                                font-bold
                                ${
                                  getScoreStyle(
                                    interview.finalScore ?? 0
                                  )
                                }
                              `}
                            >

                              {
                                interview.finalScore ?? "—"
                              }

                            </p>


                          </div>


                          <FiArrowRight
                            className="app-text-secondary"
                          />


                        </div>


                      </button>

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