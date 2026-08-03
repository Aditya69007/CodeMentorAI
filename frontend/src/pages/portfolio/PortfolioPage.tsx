import { useEffect, useState } from "react";
import TopRepositoriesCard from "../../components/portfolio/TopRepositoriesCard";
import PortfolioHero from "../../components/portfolio/PortfolioHero";
import PortfolioStats from "../../components/portfolio/PortfolioStats";
import SkillsCard from "../../components/portfolio/SkillsCard";
import AISummaryCard from "../../components/portfolio/AISummaryCard";
import CodingProfilesCard from "../../components/portfolio/CodingProfilesCard";
import LeetCodePerformanceCard from "../../components/portfolio/LeetCodePerformanceCard";
import ProjectsCard from "../../components/portfolio/ProjectsCard";
import { getConnectedAccounts, getGitHubDashboard } from "../../services/connectedAccountsService";
import { getFeaturedProjects } from "../../services/featuredProjectService";
import GitHubAnalyticsCard from "../../components/portfolio/GitHubAnalyticsCard";
import ViewAllRepositoriesModal from "../../components/portfolio/ViewAllRepositoriesModal";
import type { GitHubDashboard } from "../../types/github";
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

const [githubDashboard, setGithubDashboard] =
  useState<GitHubDashboard | null>(null);

  const [showRepositoriesModal, setShowRepositoriesModal] =
    useState(false);


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

        setGithubDashboard(dashboard);
    
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

      <GitHubAnalyticsCard />

      <LeetCodePerformanceCard />

      {/* Featured Projects */}

      <ProjectsCard
        projects={featuredRepositories}
      />

      {githubDashboard && (
        <>
        <TopRepositoriesCard
          repositories={githubDashboard.topRepositories}
          featuredRepositories={featuredRepositories}
          onBrowseAll={() => setShowRepositoriesModal(true)}
        />

      <ViewAllRepositoriesModal
        open={showRepositoriesModal}
        onClose={() => setShowRepositoriesModal(false)}
        repositories={githubDashboard.repositories}
        featuredRepositories={featuredRepositories}
        />
      </>
      )}

    </div>
  );
}