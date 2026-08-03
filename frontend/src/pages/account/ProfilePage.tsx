import { useEffect, useState } from "react";

import PortfolioHero from "../../components/portfolio/PortfolioHero";
import PortfolioStats from "../../components/portfolio/PortfolioStats";
import SkillsCard from "../../components/portfolio/SkillsCard";
import AISummaryCard from "../../components/portfolio/AISummaryCard";
import CodingProfilesCard from "../../components/portfolio/CodingProfilesCard";
import LeetCodePerformanceCard from "../../components/portfolio/LeetCodePerformanceCard";
import ProjectsCard from "../../components/portfolio/ProjectsCard";
import { getConnectedAccounts, getGitHubDashboard } from "../../services/connectedAccountsService";
import { getFeaturedProjects } from "../../services/featuredProjectService";

import type { GitHubRepository } from "../../types/github";


import {
  getGrowthReport,
  type GrowthReportResponse,
} from "../../services/portfolioService";

export default function PortfolioPage() {
  const [growthReport, setGrowthReport] =
    useState<GrowthReportResponse | null>(null);

  const [featuredRepositories, setFeaturedRepositories] = useState<
    GitHubRepository[]
  >([]);

  useEffect(() => {
    async function loadPortfolio() {
      try {
        const growth = await getGrowthReport();
        setGrowthReport(growth);
    
        const accounts = await getConnectedAccounts();
    
        if (
          !accounts.githubConnected ||
          !accounts.githubUsername
        ) {
          setFeaturedRepositories([]);
          return;
        }
    
        const [dashboard, featured] = await Promise.all([
          getGitHubDashboard(accounts.githubUsername),
          getFeaturedProjects(),
        ]);
    
        const repositories = featured
          .map((item) =>
            dashboard.repositories.find(
              (repo) => repo.name === item.repositoryName
            )
          )
          .filter(
            (repo): repo is GitHubRepository => repo !== undefined
          );
    
        setFeaturedRepositories(repositories);
    
      } catch (error) {
        console.error(error);
      }
    }
    loadPortfolio();
  }, []);


  return (
    <div className="space-y-8">

      <PortfolioHero />

      <PortfolioStats />

      {/* Developer Intelligence */}

      <div className="grid gap-6 lg:grid-cols-2">

        <SkillsCard />

        {growthReport && (
          <AISummaryCard
            growthSummary={growthReport.growthSummary}
            recommendedNextAction={growthReport.recommendedNextAction}
            achievements={growthReport.achievements}
          />
        )}

      </div>

      {/* Coding Profiles */}

      <CodingProfilesCard />

      {/* LeetCode */}

      <LeetCodePerformanceCard />

      {/* Featured Projects */}

      <ProjectsCard
        projects={featuredRepositories}
      />

    </div>
  );
}