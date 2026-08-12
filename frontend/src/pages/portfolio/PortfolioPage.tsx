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
import { getAiDeveloperSummary } from "../../services/portfolioAiService";
import type { AiDeveloperSummary } from "../../types/portfolioAi";
import AIDeveloperSummaryCard from "../../components/portfolio/AIDeveloperSummaryCard";
import { getSkillsSummary } from "../../services/portfolioSkillsService";
import type { AiSkillsSummary } from "../../types/portfolioSkills";
import AISkillsSummaryCard from "../../components/portfolio/AISkillsSummaryCard";
import CardSkeleton from "../../components/common/CardSkeleton";
import PortfolioScoreCard from "../../components/portfolio/PortfolioScoreCard";
import { getPortfolioScore } from "../../services/portfolioScoreService";
import type { PortfolioScore } from "../../types/portfolioScore";


import { getGrowthReport } from "../../services/portfolioService";
import type { GrowthReportResponse } from "../../services/aiMentorService";

export default function PortfolioPage() {
  const [growthReport, setGrowthReport] =
    useState<GrowthReportResponse | null>(null);

  const [featuredRepositories, setFeaturedRepositories] = useState<
    GitHubRepository[]
  >([]);

  const [developerSummary, setDeveloperSummary] =
    useState<AiDeveloperSummary | null>(null);

const [githubDashboard, setGithubDashboard] =
  useState<GitHubDashboard | null>(null);

  const [showRepositoriesModal, setShowRepositoriesModal] =
    useState(false);

  const [skillsSummary, setSkillsSummary] =
  useState<AiSkillsSummary | null>(null);

  const [loadingDeveloperSummary, setLoadingDeveloperSummary] =
    useState(true);

  const [loadingSkillsSummary, setLoadingSkillsSummary] =
    useState(true);

  const [loadingGithub, setLoadingGithub] =
    useState(true);

  const [loadingLeetCode, setLoadingLeetCode] =
    useState(true);

  const [portfolioScore, setPortfolioScore] =
    useState<PortfolioScore | null>(null);

  const [loadingPortfolioScore, setLoadingPortfolioScore] =
    useState(true);



  useEffect(() => {

    async function loadGrowthReport() {

      try {

        const growth =
          await getGrowthReport();

        setGrowthReport(growth);

      } catch (error) {

        console.error(error);

      }

    }

    loadGrowthReport();

  }, []);

  useEffect(() => {

    async function loadDeveloperSummary() {

      try {

        const summary =
          await getAiDeveloperSummary();

        setDeveloperSummary(summary);

      } catch (error) {

        console.error(error);

      } finally {

        setLoadingDeveloperSummary(false);

      }

    }

    loadDeveloperSummary();

  }, []);

  useEffect(() => {

    async function loadSkillsSummary() {

      try {

        const summary =
          await getSkillsSummary();

        setSkillsSummary(summary);

      } catch (error) {

        console.error(error);

      } finally {

        setLoadingSkillsSummary(false);

      }

    }

    loadSkillsSummary();

  }, []);

  useEffect(() => {

    async function loadGitHub() {

      try {

        const accounts =
          await getConnectedAccounts();

        if (
          !accounts.githubConnected ||
          !accounts.githubUsername
        ) {

          setLoadingGithub(false);
          return;

        }

        const dashboard =
          await getGitHubDashboard(
            accounts.githubUsername
          );

        setGithubDashboard(dashboard);

        const featured =
          await getFeaturedProjects();

        const repositories = featured
          .map((item) =>
            dashboard.repositories.find(
              (repo) =>
                repo.name === item.repositoryName
            )
          )
          .filter(
            (repo): repo is GitHubRepository =>
              repo !== undefined
          );

        setFeaturedRepositories(repositories);

      } catch (error) {

        console.error(error);

      } finally {

        setLoadingGithub(false);

      }

    }

    loadGitHub();

  }, []);

  useEffect(() => {

    async function loadLeetCode() {

      try {

        /*
        * Nothing to fetch here.
        *
        * LeetCodePerformanceCard
        * already loads its own data.
        */

      } finally {

        setLoadingLeetCode(false);

      }

    }

    loadLeetCode();

  }, []);

  useEffect(() => {

    async function loadPortfolioScore() {

      try {

        const score =
          await getPortfolioScore();

        setPortfolioScore(score);

      } catch (error) {

        console.error(error);

      } finally {

        setLoadingPortfolioScore(false);

      }

    }

    loadPortfolioScore();

  }, []);


  const handleRegenerateDeveloperSummary = async () => {

    setLoadingDeveloperSummary(true);

    try {

      const summary = await getAiDeveloperSummary();

      setDeveloperSummary(summary);

    } catch (error) {

      console.error(error);

    } finally {

      setLoadingDeveloperSummary(false);

    }

  };

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

      {loadingDeveloperSummary ? (
        <CardSkeleton />
      ) : (
        developerSummary && (
        <AIDeveloperSummaryCard
          data={developerSummary}
          onRegenerate={handleRegenerateDeveloperSummary}
        />
        )
      )}

      {loadingPortfolioScore ? (

        <CardSkeleton />

      ) : (

        portfolioScore && (
          <PortfolioScoreCard
            score={portfolioScore}
          />
        )

      )}

      {/* Coding Profiles */}

      <CodingProfilesCard />

      {loadingSkillsSummary ? (
        <CardSkeleton />
      ) : (
        skillsSummary && (
          <AISkillsSummaryCard
            data={skillsSummary}
          />
        )
      )}

      {loadingLeetCode ? (
        <CardSkeleton />
      ) : (
        <LeetCodePerformanceCard />
      )}

      {/* Featured Projects */}

      {loadingGithub ? (

        <CardSkeleton />

      ) : (

        <>
          <GitHubAnalyticsCard />

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

        </>

      )}

    </div>
  );
}