import {
  FiAward,
  FiGithub,
  FiBook,
  FiCode,
  FiTrendingUp,
} from "react-icons/fi";
import type { PortfolioScore } from "../../types/portfolioScore";

interface Props {
  score: PortfolioScore;
}

export default function PortfolioScoreCard({
  score,
}: Props) {
  return (
    <section className="app-surface app-border rounded-2xl p-6">

      <div className="mb-6 flex items-center gap-3">

        <div className="rounded-xl bg-blue-500/10 p-3">
          <FiAward className="text-2xl text-blue-500" />
        </div>

        <div>
          <h2 className="text-2xl font-bold">
            AI Portfolio Score
          </h2>

          <p className="app-text-secondary">
            Overall portfolio readiness based on your coding activity.
          </p>
        </div>

      </div>

      <div className="mb-8 text-center">

        <h1 className="text-6xl font-bold text-blue-500">
          {score.overallScore}
        </h1>

        <p className="mt-2 app-text-secondary">
          Overall Score
        </p>

      </div>

      <div className="space-y-5">

        <ScoreRow
          icon={<FiGithub />}
          label="GitHub"
          value={score.githubScore}
        />

        <ScoreRow
          icon={<FiCode />}
          label="LeetCode"
          value={score.leetcodeScore}
        />

        <ScoreRow
          icon={<FiTrendingUp />}
          label="Production Ready"
          value={score.productionReadiness}
        />

        <ScoreRow
          icon={<FiBook />}
          label="Resume Readiness"
          value={score.resumeReadiness}
        />

        <ScoreRow
          icon={<FiAward />}
          label="Open Source"
          value={score.openSourceScore}
        />

      </div>

    </section>
  );
}

interface RowProps {
  icon: React.ReactNode;
  label: string;
  value: number;
}

function ScoreRow({
  icon,
  label,
  value,
}: RowProps) {

  return (
    <div>

      <div className="mb-2 flex items-center justify-between">

        <div className="flex items-center gap-2">
          {icon}
          <span>{label}</span>
        </div>

        <span className="font-semibold">
          {value}
        </span>

      </div>

      <div className="h-2 rounded-full bg-slate-700 overflow-hidden">

        <div
          className="h-full rounded-full bg-blue-500"
          style={{
            width: `${value}%`,
          }}
        />

      </div>

    </div>
  );
}