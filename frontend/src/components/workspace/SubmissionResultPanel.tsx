import {
  useEffect,
  useRef,
  useState,
} from "react";
import ReactMarkdown from "react-markdown";
import {
  FiAlertTriangle,
  FiBookOpen,
  FiCheckCircle,
  FiClock,
  FiCpu,
  FiPlay,
  FiSend,
  FiUser,
  FiXCircle,
  FiZap,
} from "react-icons/fi";

import type {
  SubmissionResponse,
} from "../../types/submission";

import type {
  AiMentorResponse,
  PastMistakeRecallResponse,
} from "../../types/aiMentor";

import {
  analyzeSubmission,
  getAnalysis,
  getChatHistory,
  getPastMistakeRecall,
  getProgressiveHint,
  getProgressiveHints,
  sendChatMessage,
} from "../../services/aiMentorService";


interface Props {
  result: SubmissionResponse | null;
  error: string;

  onUseCode: (
    sourceCode: string,
    language: SubmissionResponse["language"]
  ) => void;
}


interface ChatMessage {
  role: "user" | "assistant";
  content: string;
}


export default function SubmissionResultPanel({
  result,
  error,
  onUseCode,
}: Props) {

  // ==========================================
  // AI STATE
  // ==========================================

  const [
    aiAnalysis,
    setAiAnalysis,
  ] = useState<AiMentorResponse | null>(
    null
  );

  const [
    aiLoading,
    setAiLoading,
  ] = useState(false);

  const [
    aiError,
    setAiError,
  ] = useState("");

  const [
    pastMistakeRecall,
    setPastMistakeRecall,
  ] = useState<PastMistakeRecallResponse | null>(
    null
  );

  // ==========================================
  // CHAT STATE
  // ==========================================

  const [
    chatInput,
    setChatInput,
  ] = useState("");

  const [
    chatLoading,
    setChatLoading,
  ] = useState(false);

  const [
    chatMessages,
    setChatMessages,
  ] = useState<ChatMessage[]>([]);

  const [
    hintResponses,
    setHintResponses,
  ] = useState<Record<number, string>>({});

  const [
    hintLoadingLevel,
    setHintLoadingLevel,
  ] = useState<number | null>(null);

  // ==========================================
  // REFS
  // ==========================================

  const chatEndRef =
    useRef<HTMLDivElement | null>(
      null
    );


  // ==========================================
  // LOAD EXISTING AI ANALYSIS + CHAT
  // ==========================================

  useEffect(() => {

    if (!result?.id) {
      return;
    }


    let cancelled = false;


    const loadExistingAiData = async () => {

      try {

          const [
            analysis,
            savedMessages,
            savedHints,
            recall,
          ] = await Promise.all([
            getAnalysis(result.id),
            getChatHistory(result.id),
            getProgressiveHints(result.id),
            getPastMistakeRecall(result.id),
          ]);


        if (cancelled) {
          return;
        }

          const savedHintResponses =
            savedHints.reduce<Record<number, string>>(
              (accumulator, hint) => {

                accumulator[hint.level] =
                  hint.response;

                return accumulator;

              },
              {}
            );
          
        
            setHintResponses(savedHintResponses);

        setAiAnalysis(
          analysis
        );

        setPastMistakeRecall(recall);

        setChatMessages(

          savedMessages.map(
            (message) => ({

              role:
                message.role === "USER"
                  ? "user"
                  : "assistant",

              content:
                message.content,

            })
          )

        );


      } catch (error) {

        console.log(
          "No existing AI analysis for this submission.",
          error
        );

      }

    };


    loadExistingAiData();


    return () => {

      cancelled = true;

    };

  }, [result?.id]);


  // ==========================================
  // AUTO SCROLL CHAT
  // ==========================================

  useEffect(() => {

    if (
      chatMessages.length === 0 &&
      !chatLoading
    ) {
      return;
    }


    chatEndRef.current?.scrollIntoView({
      behavior: "smooth",
      block: "nearest",
    });

  }, [
    chatMessages,
    chatLoading,
  ]);


  // ==========================================
  // ASK AI MENTOR
  // ==========================================

  const handleAskAi = async () => {

    if (
      !result?.id ||
      aiLoading
    ) {
      return;
    }


    try {

      setAiLoading(true);

      setAiError("");


      const response =
        await analyzeSubmission(
          result.id
        );


        const [
          savedMessages,
          recall,
        ] = await Promise.all([
          getChatHistory(result.id),
          getPastMistakeRecall(result.id),
        ]);


      setAiAnalysis(
        response
      );

      setPastMistakeRecall(
        recall
      );

      setChatMessages(

        savedMessages.map(
          (message) => ({

            role:
              message.role === "USER"
                ? "user"
                : "assistant",

            content:
              message.content,

          })
        )

      );


    } catch (error) {

      console.error(error);

      setAiError(
        "Unable to get AI analysis."
      );


    } finally {

      setAiLoading(false);

    }

  };


  // ==========================================
  // SEND FOLLOW-UP MESSAGE
  // ==========================================

  const handleSendMessage = async () => {

    const message =
      chatInput.trim();


    if (
      !result?.id ||
      !message ||
      chatLoading
    ) {
      return;
    }


    setChatMessages(
      (previousMessages) => [

        ...previousMessages,

        {
          role: "user",
          content: message,
        },

      ]
    );


    setChatInput("");

    setChatLoading(true);

    setAiError("");


    try {

      const response =
        await sendChatMessage(
          result.id,
          message
        );


      setChatMessages(
        (previousMessages) => [

          ...previousMessages,

          {
            role: "assistant",
            content: response.response,
          },

        ]
      );


    } catch (error) {

      console.error(error);

      setAiError(
        "Unable to send message to AI Mentor."
      );


    } finally {

      setChatLoading(false);

    }

  };


  // ==========================================
  // CHAT KEYBOARD CONTROLS
  // ==========================================

  const handleProgressiveHint = async (
    level: number
  ) => {

    if (
      !result?.id ||
      hintLoadingLevel !== null ||
      hintResponses[level]
    ) {
      return;
    }

    try {

      setHintLoadingLevel(level);

      setAiError("");

      const response =
        await getProgressiveHint(
          result.id,
          level
        );

      setHintResponses(
        (previous) => ({
          ...previous,
          [level]: response.response,
        })
      );

    } catch (error) {

      console.error(error);

      setAiError(
        "Unable to get progressive hint."
      );

    } finally {

      setHintLoadingLevel(null);

    }

  };

  const handleChatKeyDown = (
    event: React.KeyboardEvent<HTMLTextAreaElement>
  ) => {

    if (
      event.key === "Enter" &&
      !event.shiftKey
    ) {

      event.preventDefault();

      handleSendMessage();

    }

  };


  // ==========================================
  // REQUEST ERROR
  // ==========================================

  if (error) {

    return (

      <div className="h-full overflow-y-auto p-5">

        <div className="rounded-lg border border-red-500/30 bg-red-500/10 p-4">

          <div className="flex items-center gap-2 font-semibold text-red-500">

            <FiXCircle />

            Submission Failed

          </div>

          <p className="mt-2 text-sm text-red-500">

            {error}

          </p>

        </div>

      </div>

    );

  }


  // ==========================================
  // NO RESULT
  // ==========================================

  if (!result) {

    return (

      <div className="flex h-full min-h-40 flex-col items-center justify-center p-6 text-center">

        <FiPlay
          size={24}
          className="app-text-muted"
        />

        <p className="mt-3 text-sm font-medium">

          Run or submit your code first

        </p>

      </div>

    );

  }


  const accepted =
    result.status === "ACCEPTED";


  return (

    <div className="h-full overflow-y-auto p-5">


      {/* =====================================
          RESULT HEADER
      ===================================== */}

      <div className="flex flex-wrap items-start justify-between gap-4">

        <div className="flex items-center gap-3">

          {
            accepted
              ? (

                <FiCheckCircle
                  size={25}
                  className="text-emerald-500"
                />

              )
              : (

                <FiXCircle
                  size={25}
                  className="text-red-500"
                />

              )
          }


          <div>

            <h2
              className={`text-xl font-bold ${
                accepted
                  ? "text-emerald-500"
                  : "text-red-500"
              }`}
            >

              {
                result.status.replaceAll(
                  "_",
                  " "
                )
              }

            </h2>


            {
              result.id && (

                <p className="app-text-muted mt-1 text-xs">

                  Submission #{result.id}

                </p>

              )
            }

          </div>

        </div>


        {
          !accepted &&
          result.id &&
          !aiAnalysis && (

            <button

              onClick={
                handleAskAi
              }

              disabled={
                aiLoading
              }

              className="
                flex
                items-center
                gap-2
                rounded-md
                bg-violet-600
                px-4
                py-2
                text-sm
                font-semibold
                text-white
                hover:bg-violet-500
                disabled:cursor-not-allowed
                disabled:opacity-50
              "

            >

              <FiZap />

              {
                aiLoading
                  ? "Analyzing..."
                  : "Ask AI Mentor"
              }

            </button>

          )
        }

      </div>


      {/* =====================================
          RESULT METRICS
      ===================================== */}

      <div className="mt-5 grid gap-3 sm:grid-cols-3">


        <div className="app-surface-secondary rounded-lg p-4">

          <div className="app-text-muted flex items-center gap-2 text-xs">

            <FiCheckCircle />

            Test Cases

          </div>

          <p className="mt-2 font-semibold">

            {result.passedTestCases ?? 0}

            {" / "}

            {result.totalTestCases ?? 0}

          </p>

        </div>


        <div className="app-surface-secondary rounded-lg p-4">

          <div className="app-text-muted flex items-center gap-2 text-xs">

            <FiClock />

            Runtime

          </div>

          <p className="mt-2 font-semibold">

            {result.executionTime ?? 0} ms

          </p>

        </div>


        <div className="app-surface-secondary rounded-lg p-4">

          <div className="app-text-muted flex items-center gap-2 text-xs">

            <FiCpu />

            Memory

          </div>

          <p className="mt-2 font-semibold">

            {result.memoryUsed ?? 0}

          </p>

        </div>


      </div>


      {/* =====================================
          SUBMITTED CODE
      ===================================== */}

      {
        result.sourceCode && (

          <div className="mt-5">


            <div className="mb-2 flex items-center justify-between gap-4">

              <p className="app-text-muted text-xs font-semibold uppercase">

                Submitted Code

              </p>


              <div className="flex items-center gap-3">

                <span className="app-text-muted text-xs">

                  {result.language}

                </span>


                <button

                  type="button"

                  onClick={() =>
                    onUseCode(
                      result.sourceCode,
                      result.language
                    )
                  }

                  className="
                    rounded-md
                    bg-blue-600
                    px-3
                    py-1.5
                    text-xs
                    font-semibold
                    text-white
                    hover:bg-blue-500
                  "

                >

                  Use in Editor

                </button>

              </div>

            </div>


            <pre
              className="
                app-surface-secondary
                max-h-80
                overflow-auto
                whitespace-pre
                rounded-lg
                p-4
                font-mono
                text-sm
                leading-6
              "
            >

              {result.sourceCode}

            </pre>


          </div>

        )
      }


      {/* =====================================
          HIDDEN TEST
      ===================================== */}

      {
        result.failedOnHiddenTest && (

          <div className="mt-4 flex items-center gap-2 rounded-lg border border-amber-500/30 bg-amber-500/10 p-3 text-sm text-amber-500">

            <FiAlertTriangle />

            Failed on a hidden test case

          </div>

        )
      }


      {/* =====================================
          ERROR DETAILS
      ===================================== */}

      {
        result.errorMessage && (

          <div className="mt-5">

            <p className="app-text-muted text-xs font-semibold uppercase">

              Details

            </p>

            <pre className="app-surface-secondary mt-2 overflow-x-auto whitespace-pre-wrap rounded-lg p-4 text-sm">

              {result.errorMessage}

            </pre>

          </div>

        )
      }


      {/* =====================================
          OUTPUT
      ===================================== */}

      {
        result.output && (

          <div className="mt-5">

            <p className="app-text-muted text-xs font-semibold uppercase">

              Output

            </p>

            <pre className="app-surface-secondary mt-2 overflow-x-auto whitespace-pre-wrap rounded-lg p-4 text-sm">

              {result.output}

            </pre>

          </div>

        )
      }


      {/* =====================================
          AI ERROR
      ===================================== */}

      {
        aiError && (

          <div className="mt-5 rounded-lg border border-red-500/30 bg-red-500/10 p-4 text-sm text-red-500">

            {aiError}

          </div>

        )
      }


      {/* =====================================
          AI MENTOR
      ===================================== */}

      {
        aiAnalysis && (

          <div className="mt-6 overflow-hidden rounded-xl border border-violet-500/30 bg-violet-500/5">


            {/* HEADER */}

            <div className="flex items-center gap-2 border-b border-violet-500/20 px-5 py-4">

              <FiZap className="text-violet-500" />

              <h3 className="font-semibold">

                AI Mentor

              </h3>

            </div>


            {/* CONTENT */}

            <div className="space-y-5 p-5">
              
            {/* PAST MISTAKE RECALL */}

            {
              pastMistakeRecall?.repeatedMistake && (

                <div className="overflow-hidden rounded-lg border border-amber-500/30 bg-amber-500/5">

                  <div className="flex items-center gap-3 border-b border-amber-500/20 px-4 py-3">

                    <FiAlertTriangle className="shrink-0 text-amber-500" />

                    <div>

                      <p className="text-sm font-semibold text-amber-500">

                        You've made this mistake before

                      </p>

                      <p className="app-text-muted mt-0.5 text-xs">

                        CodeMentor AI found a recurring pattern in your mistake history.

                      </p>

                    </div>

                  </div>


                  <div className="space-y-4 p-4">

                    <div className="grid gap-3 sm:grid-cols-2">

                      <div className="app-surface-secondary rounded-lg p-3">

                        <p className="app-text-muted text-xs">

                          Mistake Pattern

                        </p>

                        <p className="mt-1 text-sm font-semibold">

                          {
                            pastMistakeRecall.mistakeType
                              ?.replaceAll("_", " ")
                          }

                        </p>

                      </div>


                      <div className="app-surface-secondary rounded-lg p-3">

                        <p className="app-text-muted text-xs">

                          Concept

                        </p>

                        <p className="mt-1 text-sm font-semibold">

                          {pastMistakeRecall.concept}

                        </p>

                      </div>

                    </div>


                    <div className="app-text-secondary text-sm leading-6">

                      {pastMistakeRecall.message}

                    </div>
                    {
                      pastMistakeRecall.memoryAdvice && (

                        <div className="rounded-lg border border-emerald-500/20 bg-emerald-500/5 p-4">

                          <div className="flex items-start gap-3">

                            <FiBookOpen className="mt-0.5 shrink-0 text-emerald-500" />

                            <div>

                              <p className="text-sm font-semibold text-emerald-500">

                                What to remember this time

                              </p>

                              <p className="app-text-secondary mt-2 text-sm leading-6">

                                {pastMistakeRecall.memoryAdvice}

                              </p>

                            </div>

                          </div>

                        </div>

                      )
                    }

                    {
                      pastMistakeRecall.previousProblemTitle && (

                        <div className="rounded-lg border border-violet-500/20 bg-violet-500/5 p-3">

                          <p className="app-text-muted text-xs">

                            Previously seen in

                          </p>

                          <p className="mt-1 text-sm font-semibold text-violet-500">

                            {pastMistakeRecall.previousProblemTitle}

                          </p>

                          {
                            pastMistakeRecall.previousSubmissionId && (

                              <p className="app-text-muted mt-1 text-xs">

                                Submission #{pastMistakeRecall.previousSubmissionId}

                              </p>

                            )
                          }

                        </div>

                      )
                    }

                  </div>

                </div>

              )
            }

              {/* EXPLANATION */}

              <div>

                <p className="text-sm font-semibold text-violet-500">

                  What went wrong?

                </p>

                <div className="app-text-secondary mt-2 text-sm leading-6">
                  <ReactMarkdown>
                    {aiAnalysis.explanation}
                  </ReactMarkdown>
                </div>

              </div>


              {/* HINT */}

              <div className="rounded-lg border border-amber-500/20 bg-amber-500/5 p-4">

                <p className="text-sm font-semibold text-amber-500">

                  Hint

                </p>

                <div className="app-text-secondary mt-2 text-sm leading-6">
                  <ReactMarkdown>
                    {aiAnalysis.hint}
                  </ReactMarkdown>
                </div>

              </div>


              {/* CONCEPT */}

              <div className="flex items-start gap-3 rounded-lg border border-blue-500/20 bg-blue-500/5 p-4">

                <FiBookOpen className="mt-0.5 shrink-0 text-blue-500" />

                <div>

                  <p className="text-sm font-semibold text-blue-500">

                    Concept to Study

                  </p>

                  <div className="app-text-secondary mt-1 text-sm leading-6">
                    <ReactMarkdown>
                      {aiAnalysis.conceptToStudy}
                    </ReactMarkdown>
                  </div>

                </div>

              </div>

              {/* PROGRESSIVE GUIDANCE */}

              <div className="border-t border-violet-500/20 pt-5">

                <div className="mb-4">

                  <p className="font-semibold">

                    Progressive Guidance

                  </p>

                  <p className="app-text-muted mt-1 text-xs">

                    Get increasingly detailed help without revealing the complete solution.

                  </p>

                </div>


                <div className="grid gap-2 sm:grid-cols-2">

                  {
                    [
                      {
                        level: 1,
                        label: "Hint 1",
                      },
                      {
                        level: 2,
                        label: "Hint 2",
                      },
                      {
                        level: 3,
                        label: "Explain Approach",
                      },
                      {
                        level: 4,
                        label: "Pseudocode",
                      },
                    ].map((hint) => (

                      <button

                        key={hint.level}

                        type="button"

                        onClick={() =>
                          handleProgressiveHint(
                            hint.level
                          )
                        }

                        disabled={
                          hintLoadingLevel !== null ||
                          Boolean(
                            hintResponses[hint.level]
                          )
                        }

                        className="
                          app-surface-secondary
                          app-border
                          rounded-lg
                          border
                          px-4
                          py-3
                          text-left
                          text-sm
                          font-semibold
                          transition
                          hover:border-violet-500
                          disabled:cursor-not-allowed
                          disabled:opacity-60
                        "

                      >

                        {
                          hintLoadingLevel === hint.level
                            ? "AI Mentor is thinking..."
                            : hintResponses[hint.level]
                              ? `${hint.label} unlocked`
                              : hint.label
                        }

                      </button>

                    ))
                  }

                </div>


                <div className="mt-4 space-y-3">

                  {
                    Object.entries(
                      hintResponses
                    )

                      .sort(
                        ([firstLevel], [secondLevel]) =>
                          Number(firstLevel) -
                          Number(secondLevel)
                      )

                      .map(
                        ([level, response]) => (

                          <div

                            key={level}

                            className="rounded-lg border border-violet-500/20 bg-violet-500/5 p-4"

                          >

                            <p className="text-sm font-semibold text-violet-500">

                              {
                                Number(level) === 1
                                  ? "Hint 1"
                                  : Number(level) === 2
                                    ? "Hint 2"
                                    : Number(level) === 3
                                      ? "Approach Explanation"
                                      : "Pseudocode Guidance"
                              }

                            </p>

                            <div className="app-text-secondary mt-2 text-sm leading-6">
                              <ReactMarkdown>
                                {response}
                              </ReactMarkdown>
                            </div>

                          </div>

                        )
                      )
                  }

                </div>

              </div>

              {/* CHAT */}

              {
                (
                  chatMessages.length > 0 ||
                  chatLoading
                ) && (

                  <div className="space-y-4 border-t border-violet-500/20 pt-5">


                    {
                      chatMessages.map(
                        (message, index) => (

                          <div

                            key={index}

                            className={`flex ${
                              message.role === "user"
                                ? "justify-end"
                                : "justify-start"
                            }`}

                          >

                            <div
                              className={`
                                max-w-[88%]
                                rounded-xl
                                px-4
                                py-3
                                ${
                                  message.role === "user"
                                    ? "bg-violet-600 text-white"
                                    : "app-surface-secondary"
                                }
                              `}
                            >

                              <div className="mb-2 flex items-center gap-2 text-xs font-semibold">

                                {
                                  message.role === "user"
                                    ? <FiUser />
                                    : <FiZap className="text-violet-500" />
                                }

                                {
                                  message.role === "user"
                                    ? "You"
                                    : "AI Mentor"
                                }

                              </div>

                              <div className="wrap-break-word text-sm leading-6">
                                <ReactMarkdown>
                                  {message.content}
                                </ReactMarkdown>
                              </div>

                            </div>

                          </div>

                        )
                      )
                    }


                    {/* AI THINKING */}

                    {
                      chatLoading && (

                        <div className="flex justify-start">

                          <div className="app-surface-secondary rounded-xl px-4 py-3">

                            <div className="flex items-center gap-3">

                              <FiZap className="text-violet-500" />

                              <div className="flex items-center gap-1">

                                <span className="h-2 w-2 animate-bounce rounded-full bg-violet-500 [animation-delay:-0.3s]" />

                                <span className="h-2 w-2 animate-bounce rounded-full bg-violet-500 [animation-delay:-0.15s]" />

                                <span className="h-2 w-2 animate-bounce rounded-full bg-violet-500" />

                              </div>

                            </div>

                          </div>

                        </div>

                      )
                    }


                    <div ref={chatEndRef} />


                  </div>

                )
              }


              {/* CHAT INPUT */}

              <div className="border-t border-violet-500/20 pt-5">


                <p className="app-text-muted mb-3 text-xs">

                  Ask a follow-up question.

                  {" "}

                  Press Enter to send and Shift + Enter for a new line.

                </p>


                <div className="flex items-end gap-2">


                  <textarea

                    value={
                      chatInput
                    }

                    onChange={
                      (event) =>
                        setChatInput(
                          event.target.value
                        )
                    }

                    onKeyDown={
                      handleChatKeyDown
                    }

                    disabled={
                      chatLoading
                    }

                    rows={3}

                    placeholder="Ask AI Mentor..."

                    className="
                      app-surface-secondary
                      min-h-20
                      min-w-0
                      flex-1
                      resize-y
                      rounded-lg
                      border
                      border-violet-500/20
                      px-4
                      py-3
                      text-sm
                      outline-none
                      focus:border-violet-500
                      disabled:cursor-not-allowed
                      disabled:opacity-60
                    "

                  />


                  <button

                    type="button"

                    onClick={
                      handleSendMessage
                    }

                    disabled={
                      chatLoading ||
                      !chatInput.trim()
                    }

                    className="
                      flex
                      h-11
                      w-11
                      shrink-0
                      items-center
                      justify-center
                      rounded-lg
                      bg-violet-600
                      text-white
                      hover:bg-violet-500
                      disabled:cursor-not-allowed
                      disabled:opacity-50
                    "

                    title="Send message"

                  >

                    <FiSend />

                  </button>


                </div>


              </div>


            </div>


          </div>

        )
      }


    </div>

  );
}