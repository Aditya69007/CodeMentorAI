import type { PortfolioScore } from "./portfolioScore";
import type { AiDeveloperSummary } from "./portfolioAi";
import type { AiSkillsSummary } from "./portfolioSkills";
import type { GitHubDashboard } from "./github";
import type { LeetCodeProfile } from "./leetcode";
import type { UserProfile } from "./userProfile";
import type { GrowthReportResponse } from "../services/aiMentorService";

export interface PublicPortfolioResponse {
profile: UserProfile;
  portfolioScore: PortfolioScore;
  developerSummary: AiDeveloperSummary;
  skillsSummary: AiSkillsSummary;
  githubDashboard: GitHubDashboard | null;
  leetCodeProfile: LeetCodeProfile | null;
  growthReport: GrowthReportResponse;
}