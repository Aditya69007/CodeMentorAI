import {
  FiCpu,
} from "react-icons/fi";

import type { TopicProgress } from "../../services/topicService";

interface TopicSidebarProps {
  progress: TopicProgress;
  onStart: () => void;
}

export default function TopicSidebar({
  progress,
  onStart,
}: TopicSidebarProps) {
return (

  <aside className="sticky top-5">

    <div className="rounded-3xl border app-border app-surface p-5">

      {/* AI Header */}

      <div className="flex items-center gap-3">

        <div className="rounded-xl bg-cyan-500/10 p-3">

          <FiCpu
            size={22}
            className="text-cyan-400"
          />

        </div>

        <div>

          <p className="text-xs uppercase tracking-widest app-text-secondary">

            AI Mentor

          </p>

          <h3 className="text-2xl font-bold">

            {progress.recommendedProblemTitle}

          </h3>

        </div>

      </div>

    <div className="mt-4 rounded-xl bg-cyan-500/10 border border-cyan-500/20 p-3">

    <div className="text-xs uppercase tracking-wider text-cyan-400">

        Why this problem?

    </div>

    <div className="mt-2 text-sm app-text-secondary">

        {progress.recommendationReason}

    </div>

    </div>

      {/* Chips */}

      <div className="mt-5 flex flex-wrap gap-2">

        <span className="rounded-full bg-blue-500/10 px-3 py-1 text-xs">

          {progress.recommendedDifficulty}

        </span>

        <span className="rounded-full bg-emerald-500/10 px-3 py-1 text-xs">

          +{progress.estimatedLearningGain}% Gain

        </span>

      </div>

      {/* Progress */}

      <div className="mt-5">

        <div className="flex justify-between text-sm">

          <span>Mastery</span>

          <span className="font-bold">

            {Math.round(progress.masteryPercentage)}%

          </span>

        </div>

        <div className="mt-2 h-2 rounded-full bg-neutral-800">

          <div
            className="h-full rounded-full bg-gradient-to-r from-cyan-500 to-indigo-500"
            style={{
              width: `${progress.masteryPercentage}%`,
            }}
          />

        </div>

      </div>

      {/* Stats */}

      <div className="mt-5 grid grid-cols-2 gap-4">

        <div className="rounded-xl bg-white/5 p-3 text-center">

          <div className="text-xs app-text-secondary">

            Solved

          </div>

          <div className="mt-1 text-2xl font-bold">

            {progress.solvedProblems}

          </div>

        </div>

        <div className="rounded-xl bg-white/5 p-3 text-center">

          <div className="text-xs app-text-secondary">

            AI Mistakes

          </div>

          <div className="mt-1 text-2xl font-bold">

            {progress.aiMistakes}

          </div>

        </div>

      </div>

      {/* Weak Concepts */}

      {progress.weakConcepts?.length ? (

        <div className="mt-5">

          <div className="mb-3 text-sm font-semibold">

            Weak Concepts

          </div>

          <div className="flex flex-wrap gap-2">

            {progress.weakConcepts.slice(0,3).map(concept => (

              <span
                key={concept}
                className="rounded-full bg-red-500/10 px-3 py-1 text-xs text-red-400"
              >

                {concept}

              </span>

            ))}

          </div>

        </div>

      ) : null}

      {/* Button */}

      <button
        onClick={onStart}
        className="
          mt-5
          w-full
          rounded-2xl
          bg-gradient-to-r
          from-cyan-500
          to-indigo-600
          py-3
          font-semibold
          text-white
          transition
          hover:scale-[1.02]
        "
      >

        Start Solving

      </button>

    </div>

  </aside>

);
}