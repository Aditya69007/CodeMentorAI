import { FiArrowLeft } from "react-icons/fi";
import type { Topic } from "../../types/topic";
import type { TopicProgress } from "../../services/topicService";

interface TopicHeaderProps {
  topic: Topic;
  progress: TopicProgress;
  problemCount: number;
  onBack: () => void;
}

export default function TopicHeader({
  topic,
  progress,
  problemCount,
  onBack,
}: TopicHeaderProps) {
  return (
    <section className="mb-8">

      <button
        onClick={onBack}
        className="mb-8 flex items-center gap-2 text-sm app-text-secondary transition hover:text-cyan-400"
      >
        <FiArrowLeft size={18} />
        Topic Library
      </button>

      <div className="flex items-start justify-between gap-8">

        <div className="flex-1">

          <span className="rounded-full bg-cyan-500/10 px-4 py-2 text-xs font-semibold uppercase tracking-widest text-cyan-400">
            Topic
          </span>

          <h1 className="mt-5 text-6xl font-black tracking-tight">
            {topic.name}
          </h1>

          <p className="mt-5 max-w-3xl text-lg leading-8 app-text-secondary">
            {topic.description}
          </p>

          <div className="mt-7 flex flex-wrap gap-3">

            <span className="rounded-full bg-blue-500/10 px-4 py-2 text-sm">
              📚 {problemCount} Problems
            </span>

            <span className="rounded-full bg-emerald-500/10 px-4 py-2 text-sm">
              🎯 {Math.round(progress.masteryPercentage)}% Mastery
            </span>

            <span className="rounded-full bg-amber-500/10 px-4 py-2 text-sm">
              🚀 {progress.level}
            </span>

          </div>

        </div>

      </div>

    </section>
  );
}