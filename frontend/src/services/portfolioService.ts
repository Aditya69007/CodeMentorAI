import api from "./api";

// ==================================================
// TYPES
// ==================================================

export interface PortfolioUser {
  userId: number;
  firstName: string;
  lastName: string;
  email: string;
  role: string;
  profilePicture?: string;
}

export interface DeveloperSkill {
  topicId: number;
  topicName: string;
  totalSubmissions: number;
  acceptedSubmissions: number;
  totalMistakes: number;
  acceptanceRate: number;
  skillScore: number;
  skillLevel: string;
  message: string;
}

export interface InterviewProfile {
  interviewReady: boolean;
  readinessScore: number;
}

// ==================================================
// USER PROFILE
// ==================================================

export const getPortfolioProfile = async () => {

  const response = await api.get(
    "/users/me"
  );

  return response.data;

};

// ==================================================
// GROWTH REPORT
// ==================================================

export const getGrowthReport = async (): Promise<GrowthReportResponse> => {
  const response = await api.get("/ai-mentor/growth-report/me");
  return response.data;
};

// ==================================================
// DEVELOPER SKILLS
// ==================================================

export const getDeveloperSkills = async (): Promise<DeveloperSkill[]> => {

  const response = await api.get(
    "/ai-mentor/skills/me"
  );

  return response.data;

};

// ==================================================
// INTERVIEW PROFILE
// ==================================================

export const getInterviewProfile = async () => {

  const response = await api.get(
    "/ai-mentor/interview-profile/me"
  );

  return response.data;

};

// ==================================================
// RECENT SUBMISSIONS
// ==================================================

export const getRecentSubmissions = async (
  page = 0,
  size = 5
) => {

  const response = await api.get(
    "/submissions/me",
    {
      params: {
        page,
        size,
      },
    }
  );

  return response.data;

};

export interface GrowthReportResponse {
  overallGrowthScore: number;
  developerLevel: string;
  hintDependencyScore: number;
  independentSolveRate: number;
  totalCompletedIndependentSessions: number;
  independentlySolvedProblems: number;
  achievements: string[];
  growthSummary: string;
  recommendedNextAction: string;
}