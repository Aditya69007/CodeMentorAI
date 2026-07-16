import {
  FiChevronRight,
} from "react-icons/fi";

import type {
  TopicProblem,
} from "../../types/topic";

import type {
  Difficulty,
} from "../../types/problem";

interface Props {
  problem: TopicProblem;
  onOpen: () => void;
}

const difficultyStyle = (
  value: Difficulty
) => {

  if (value === "EASY") {

    return "text-emerald-400";

  }

  if (value === "MEDIUM") {

    return "text-amber-400";

  }

  return "text-red-400";

};

export default function TopicProblemRow({
  problem,
  onOpen,
}: Props) {

  return (

    <button
      onClick={onOpen}
      className="
        group
        grid
        h-[62px]
        w-full
        grid-cols-[20px_1fr_80px_70px_40px]
        items-center
        gap-4
        rounded-xl
        px-4
        transition
        hover:bg-cyan-500/5
      "
    >

      <div
        className={`
          h-3
          w-3
          rounded-full

          ${
            problem.solved
              ? "bg-emerald-500"

              : problem.attempted
              ? "bg-amber-500"

              : "bg-neutral-600"
          }
        `}
      />

      <div className="truncate text-left">

        <div className="truncate font-medium">

          {problem.title}

        </div>

      </div>

      <div
        className={`
          text-sm
          font-semibold

          ${difficultyStyle(
            problem.difficulty
          )}
        `}
      >

        {problem.difficulty}

      </div>

      <div className="text-sm app-text-secondary">

        {problem.acceptanceRate ?? "--"}%

      </div>

      <FiChevronRight
        className="opacity-50 transition group-hover:translate-x-1"
      />

    </button>

  );

}