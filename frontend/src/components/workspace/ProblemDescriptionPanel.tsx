import type { Problem } from "../../types/problem";

interface Props {
  problem: Problem;
}

export default function ProblemDescriptionPanel({
  problem,
}: Props) {
  const difficultyClass =
    problem.difficulty === "EASY"
      ? "bg-emerald-500/10 text-emerald-500"
      : problem.difficulty === "MEDIUM"
        ? "bg-amber-500/10 text-amber-500"
        : "bg-red-500/10 text-red-500";

  const sortedExamples = [...(problem.examples ?? [])].sort(
    (a, b) => a.orderIndex - b.orderIndex
  );

  return (
    <div className="h-full overflow-y-auto">
      <div className="p-5 pb-14 sm:p-6 sm:pb-14">

        {/* ==================================================
            PROBLEM TITLE
        ================================================== */}

        <h1 className="text-xl font-bold leading-tight sm:text-2xl">
          {problem.id}. {problem.title}
        </h1>

        {/* ==================================================
            DIFFICULTY + TOPICS
        ================================================== */}

        <div className="mt-4 flex flex-wrap items-center gap-2">
          <span
            className={`
              rounded-full
              px-3
              py-1
              text-xs
              font-semibold
              ${difficultyClass}
            `}
          >
            {problem.difficulty}
          </span>

          {problem.tags?.map((tag) => (
            <span
              key={tag}
              className="
                app-surface-secondary
                app-border
                app-text-secondary
                rounded-full
                border
                px-3
                py-1
                text-xs
                font-medium
              "
            >
              {tag}
            </span>
          ))}
        </div>

        {/* ==================================================
            DESCRIPTION
        ================================================== */}

        <section className="mt-7">
          <div
            className="
              app-text-primary
              whitespace-pre-line
              text-[15px]
              leading-7
            "
          >
            {problem.description}
          </div>
        </section>

        {/* ==================================================
            EXAMPLES
        ================================================== */}

        {sortedExamples.length > 0 ? (
          <section className="mt-8">
            <div className="space-y-7">
              {sortedExamples.map((example, index) => (
                <div key={example.id}>

                  {/* EXAMPLE TITLE */}

                  <h2 className="text-[15px] font-semibold">
                    Example {index + 1}:
                  </h2>

                  {/* EXAMPLE CONTENT */}

                  <div
                    className="
                      app-surface-secondary
                      mt-3
                      rounded-md
                      border-l-2
                      border-slate-500
                      px-4
                      py-3
                    "
                  >

                    {/* INPUT */}

                    <div className="text-sm leading-6">
                      <span className="font-semibold">
                        Input:
                      </span>{" "}

                      <code
                        className="
                          app-text-secondary
                          whitespace-pre-wrap
                          break-words
                          font-mono
                        "
                      >
                        {example.input.trim()}
                      </code>
                    </div>

                    {/* OUTPUT */}

                    <div className="mt-1 text-sm leading-6">
                      <span className="font-semibold">
                        Output:
                      </span>{" "}

                      <code
                        className="
                          app-text-secondary
                          whitespace-pre-wrap
                          break-words
                          font-mono
                        "
                      >
                        {example.output.trim()}
                      </code>
                    </div>

                    {/* EXPLANATION */}

                    {example.explanation?.trim() && (
                      <div className="mt-1 text-sm leading-6">
                        <span className="font-semibold">
                          Explanation:
                        </span>{" "}

                        <span
                          className="
                            app-text-secondary
                            whitespace-pre-line
                          "
                        >
                          {example.explanation.trim()}
                        </span>
                      </div>
                    )}

                  </div>
                </div>
              ))}
            </div>
          </section>
        ) : (

          /* ==================================================
              FALLBACK EXAMPLE
          ================================================== */

          <section className="mt-8">
            <h2 className="text-[15px] font-semibold">
              Example 1:
            </h2>

            <div
              className="
                app-surface-secondary
                mt-3
                rounded-md
                border-l-2
                border-slate-500
                px-4
                py-3
              "
            >

              {/* INPUT */}

              <div className="text-sm leading-6">
                <span className="font-semibold">
                  Input:
                </span>{" "}

                <code
                  className="
                    app-text-secondary
                    whitespace-pre-wrap
                    break-words
                    font-mono
                  "
                >
                  {problem.sampleInput?.trim()}
                </code>
              </div>

              {/* OUTPUT */}

              <div className="mt-1 text-sm leading-6">
                <span className="font-semibold">
                  Output:
                </span>{" "}

                <code
                  className="
                    app-text-secondary
                    whitespace-pre-wrap
                    break-words
                    font-mono
                  "
                >
                  {problem.sampleOutput?.trim()}
                </code>
              </div>

            </div>
          </section>
        )}

        {/* ==================================================
            INPUT FORMAT
        ================================================== */}

        {problem.inputFormat?.trim() && (
          <section className="mt-8">
            <h2 className="text-[15px] font-semibold">
              Input Format
            </h2>

            <p
              className="
                app-text-secondary
                mt-3
                whitespace-pre-line
                text-sm
                leading-6
              "
            >
              {problem.inputFormat.trim()}
            </p>
          </section>
        )}

        {/* ==================================================
            OUTPUT FORMAT
        ================================================== */}

        {problem.outputFormat?.trim() && (
          <section className="mt-8">
            <h2 className="text-[15px] font-semibold">
              Output Format
            </h2>

            <p
              className="
                app-text-secondary
                mt-3
                whitespace-pre-line
                text-sm
                leading-6
              "
            >
              {problem.outputFormat.trim()}
            </p>
          </section>
        )}

        {/* ==================================================
            CONSTRAINTS
        ================================================== */}

        {problem.constraints?.trim() && (
          <section className="mt-8">
            <h2 className="text-[15px] font-semibold">
              Constraints
            </h2>

            <div
              className="
                app-surface-secondary
                mt-3
                rounded-md
                px-4
                py-3
              "
            >
              <pre
                className="
                  app-text-secondary
                  overflow-x-auto
                  whitespace-pre-wrap
                  font-mono
                  text-sm
                  leading-6
                "
              >
                {problem.constraints.trim()}
              </pre>
            </div>
          </section>
        )}

      </div>
    </div>
  );
}