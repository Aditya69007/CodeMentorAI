import { useEffect, useState } from "react";
import {
  FiTrendingUp,
  FiAward,
  FiHelpCircle,
  FiTarget,
} from "react-icons/fi";
import { getGrowthReport } from "../../services/portfolioService";
import type { GrowthReportResponse } from "../../services/aiMentorService";

interface KpiCardProps {
  icon: React.ReactNode;
  title: string;
  value: string;
  description: string;
  progress: number;
  color: string;
}

function KpiCard({
  icon,
  title,
  value,
  description,
  progress,
  color,
}: KpiCardProps) {
  return (
    <div className="app-surface app-border rounded-2xl p-4.5">

  <div className="mb-4 flex items-start justify-between gap-4">

    <div className={`rounded-xl p-3 ${color} shrink-0`}>
      {icon}
    </div>

    <div className="flex-1 text-right">

      <div className="text-2xl font-bold leading-tight break-words whitespace-normal">

        {value}

      </div>

    </div>

  </div>

      <h3 className="font-semibold">
        {title}
      </h3>

      <p className="app-text-secondary mt-2 text-sm">
        {description}
      </p>

      <div className="mt-5 h-2 overflow-hidden rounded-full bg-slate-700/40">

        <div
          className="h-full rounded-full bg-blue-500"
          style={{ width: `${Math.min(progress, 100)}%` }}
        />

      </div>

    </div>
  );
}

interface PortfolioStatsProps {
  report?: GrowthReportResponse;
}

export default function PortfolioStats({
  report: publicReport,
}: PortfolioStatsProps) {

  const [report, setReport] =
    useState<GrowthReportResponse | null>(
      publicReport ?? null
    );

  useEffect(() => {

    if (publicReport) {
      return;
    }

    const load = async () => {

      try {

        const data = await getGrowthReport();

        setReport(data);

      } catch (error) {

        console.error(error);

      }

    };

    load();

  }, [publicReport]);

  if (!report) {
    return (
      <div className="app-text-secondary">
        Loading AI report...
      </div>
    );
  }

  return (

    <section className="grid gap-6 lg:grid-cols-2 xl:grid-cols-4">

      <KpiCard
        icon={<FiTrendingUp />}
        title="Growth Score"
        value={String(report.overallGrowthScore)}
        description="Overall AI growth score."
        progress={report.overallGrowthScore}
        color="bg-blue-500/10 text-blue-500"
      />

      <KpiCard
        icon={<FiAward />}
        title="Developer Level"
        value={report.developerLevel}
        description="Current AI developer level."
        progress={40}
        color="bg-emerald-500/10 text-emerald-500"
      />

      <KpiCard
        icon={<FiHelpCircle />}
        title="Hint Dependency"
        value={`${report.hintDependencyScore}%`}
        description="Lower is better."
        progress={100 - report.hintDependencyScore}
        color="bg-orange-500/10 text-orange-500"
      />

      <KpiCard
        icon={<FiTarget />}
        title="Independent Solve Rate"
        value={`${report.independentSolveRate}%`}
        description="Problems solved without AI."
        progress={report.independentSolveRate}
        color="bg-purple-500/10 text-purple-500"
      />

    </section>

  );

}