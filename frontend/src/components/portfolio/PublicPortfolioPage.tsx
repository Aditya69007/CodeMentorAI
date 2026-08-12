import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";

import { getPublicPortfolio } from "../../services/publicPortfolioService";

import type { PublicPortfolioResponse } from "../../types/publicPortfolio";

import PortfolioHero from "../../components/portfolio/PortfolioHero";
import PortfolioStats from "../../components/portfolio/PortfolioStats";
import PortfolioScoreCard from "../../components/portfolio/PortfolioScoreCard";
import AIDeveloperSummaryCard from "../../components/portfolio/AIDeveloperSummaryCard";
import AISkillsSummaryCard from "../../components/portfolio/AISkillsSummaryCard";
import AISummaryCard from "../../components/portfolio/AISummaryCard";
import GitHubAnalyticsCard from "../../components/portfolio/GitHubAnalyticsCard";
import LeetCodePerformanceCard from "../../components/portfolio/LeetCodePerformanceCard";
import ProjectsCard from "../../components/portfolio/ProjectsCard";

export default function PublicPortfolioPage() {
  const { username } = useParams();

  const [portfolio, setPortfolio] =
    useState<PublicPortfolioResponse | null>(null);

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function loadPortfolio() {
      if (!username) {
        setLoading(false);
        return;
      }

      try {
        const data = await getPublicPortfolio(username);

        setPortfolio(data);
      } catch (error) {
        console.error("Failed to load public portfolio:", error);

        if (axios.isAxiosError(error)) {
          console.log("Status:", error.response?.status);
          console.log("Response:", error.response?.data);
          console.log("URL:", error.config?.url);
        }
      } finally {
        setLoading(false);
      }
    }

    loadPortfolio();
  }, [username]);

  if (loading) {
    return (
      <div className="flex min-h-[60vh] items-center justify-center">
        <div className="app-text-secondary text-lg">
          Loading Portfolio...
        </div>
      </div>
    );
  }

  if (!portfolio) {
    return (
      <div className="flex min-h-[60vh] items-center justify-center">
        <div className="app-text-secondary text-lg">
          Portfolio not found.
        </div>
      </div>
    );
  }

  return (
    <div className="space-y-8">

      {/* Developer Hero */}
      <PortfolioHero
        publicView={true}
        publicProfile={portfolio.profile}
      />

      {/* Growth KPIs */}
      <PortfolioStats
        report={portfolio.growthReport}
      />

      {/* Portfolio Score */}
      <PortfolioScoreCard
        score={portfolio.portfolioScore}
      />

      {/* AI Developer Intelligence */}
      <AIDeveloperSummaryCard
        data={portfolio.developerSummary}
      />

      {/* Growth + AI Skills */}
      <div className="grid gap-8 xl:grid-cols-2">

        <AISummaryCard
          growthSummary={
            portfolio.growthReport.growthSummary
          }
          recommendedNextAction={
            portfolio.growthReport.recommendedNextAction
          }
          achievements={
            portfolio.growthReport.achievements
          }
        />

        <AISkillsSummaryCard
          data={portfolio.skillsSummary}
        />

      </div>

      {/* GitHub */}
      <GitHubAnalyticsCard
        dashboard={portfolio.githubDashboard}
      />

      {/* LeetCode */}
      <LeetCodePerformanceCard
        profile={portfolio.leetCodeProfile ?? null}
      />

      {/* Projects */}
      <ProjectsCard
        projects={
          portfolio.githubDashboard?.repositories ?? []
        }
      />

    </div>
  );
}