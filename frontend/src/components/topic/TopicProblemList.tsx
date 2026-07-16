import TopicProblemRow from "./TopicProblemRow";

import type {
  TopicProblem,
} from "../../types/topic";

interface Props {
  problems: TopicProblem[];
  onOpenProblem: (id: number) => void;
}

export default function TopicProblemList({
  problems,
  onOpenProblem,
}: Props) {

  if (problems.length === 0) {

    return (

      <div className="rounded-2xl border app-border app-surface p-12 text-center">

        <h3 className="text-xl font-bold">

          No Problems

        </h3>

        <p className="mt-3 app-text-secondary">

          There are no problems in this topic.

        </p>

      </div>

    );

  }

  return (

    <div
      className="
        rounded-2xl
        border
        app-border
        app-surface
        overflow-hidden
      "
    >

      {/* Header */}

      <div
        className="
          grid
          grid-cols-[20px_1fr_80px_70px_40px]
          gap-4
          border-b
          app-border
          px-4
          py-3
          text-xs
          uppercase
          tracking-widest
          app-text-secondary
        "
      >

        <div />

        <div>

          Problem

        </div>

        <div>

          Difficulty

        </div>

        <div>

          Accept

        </div>

        <div />

      </div>

      {/* Scroll */}

      <div
        className="
          max-h-[720px]
          overflow-y-auto
        "
      >

        {problems.map(problem => (

          <TopicProblemRow

            key={problem.id}

            problem={problem}

            onOpen={() =>
              onOpenProblem(problem.id)
            }

          />

        ))}

      </div>

    </div>

  );

}