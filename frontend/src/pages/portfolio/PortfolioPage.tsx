import PortfolioHero from "../../components/portfolio/PortfolioHero";
import PortfolioStats from "../../components/portfolio/PortfolioStats";
import SkillsCard from "../../components/portfolio/SkillsCard";
import { useEffect, useState } from "react";
import AISummaryCard from "../../components/portfolio/AISummaryCard";
import {
  getGrowthReport,
  type GrowthReportResponse,
} from "../../services/portfolioService";
import ProjectsCard from "../../components/portfolio/ProjectsCard";
import CodingProfilesCard from "../../components/portfolio/CodingProfilesCard";


export default function PortfolioPage() {

  const [growthReport, setGrowthReport] =
    useState<GrowthReportResponse | null>(null);

  useEffect(() => {

    const loadGrowth = async () => {

      try {

        const data = await getGrowthReport();

        setGrowthReport(data);

      } catch (error) {

        console.error(error);

      }

    };

    loadGrowth();

  }, []);

  return (

    <div className="space-y-8">

      <PortfolioHero />

      <PortfolioStats />

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

      <div className="grid gap-6 lg:grid-cols-2">

        <ProjectsCard />

        <CodingProfilesCard />

      </div>

    </div>

  );

}