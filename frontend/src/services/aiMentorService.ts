import api from "./api";
import axios from "axios";

import type {
  AiMentorResponse,
  AiMentorChatRequest,
  AiMentorChatResponse,
  AiMentorChatMessage,
  AiMentorHintResponse,
  PastMistakeRecallResponse,
} from "../types/aiMentor";

import type {
  SolutionEvolutionAttempt,
} from "../types/solutionEvolution";

// ANALYZE SUBMISSION

export const analyzeSubmission = async (
  submissionId: number
): Promise<AiMentorResponse> => {

  const response =
    await api.post<AiMentorResponse>(
      `/ai-mentor/analyze/${submissionId}`
    );

  return response.data;
};


// GET EXISTING ANALYSIS

export const getAnalysis = async (
  submissionId: number
): Promise<AiMentorResponse | null> => {

  const response =
    await api.get<AiMentorResponse>(
      `/ai-mentor/analysis/${submissionId}`
    );


  if (response.status === 204) {

    return null;

  }


  return response.data;
};

// SEND FOLLOW-UP CHAT MESSAGE

export const sendChatMessage = async (
  submissionId: number,
  message: string
): Promise<AiMentorChatResponse> => {

  const request: AiMentorChatRequest = {
    message,
  };

  const response =
    await api.post<AiMentorChatResponse>(
      `/ai-mentor/chat/${submissionId}`,
      request
    );

  return response.data;
};


// GET SAVED CHAT HISTORY

export const getChatHistory = async (
  submissionId: number
): Promise<AiMentorChatMessage[]> => {

  const response =
    await api.get<AiMentorChatMessage[]>(
      `/ai-mentor/chat/${submissionId}`
    );

  return response.data;
};

export const getProgressiveHint = async (
  submissionId: number,
  level: number
): Promise<AiMentorHintResponse> => {

  const response =
    await api.post<AiMentorHintResponse>(
      `/ai-mentor/hint/${submissionId}/${level}`
    );

  return response.data;
};

// GET PAST MISTAKE RECALL

export const getPastMistakeRecall = async (
  submissionId: number
): Promise<PastMistakeRecallResponse> => {

  const response =
    await api.get<PastMistakeRecallResponse>(
      `/ai-mentor/mistakes/recall/${submissionId}`
    );

  return response.data;
};

// ==========================================
// GET SOLUTION EVOLUTION TIMELINE
// ==========================================

export const getSolutionEvolution = async (
  problemId: number
): Promise<SolutionEvolutionAttempt[]> => {

  try {

    const response =
      await api.get<SolutionEvolutionAttempt[]>(
        `/ai-mentor/solution-evolution/${problemId}`
      );

    return response.data;

    } catch (error: unknown) {

      if (
        axios.isAxiosError(error) &&
        error.response?.status === 404
      ) {
        return [];
      }

      throw error;
    }
};

export interface RecommendedLearningProblem {
  id: number;
  title: string;
  difficulty: string;
  solved: boolean;
  reason: string;
}

export interface PersonalizedLearningPlanResponse {
  overallReadinessScore: number;
  learningLevel: string;
  weakConcepts: string[];
  revisionPriorities: string[];
  strengths: string[];
  recommendedProblems: RecommendedLearningProblem[];
  hintDependencyScore: number;
  independentSolveRate: number;
  recommendedAction: string;
  message: string;
}

export const getMyPersonalizedLearningPlan =
  async (): Promise<PersonalizedLearningPlanResponse> => {

    const response =
      await api.get<PersonalizedLearningPlanResponse>(
        "/ai-mentor/learning-plan/me"
      );

    return response.data;
  };

export interface PersonalizedRevisionPlanResponse {
  revisionScore: number;
  revisionLevel: string;
  urgentConcepts: string[];
  improvingConcepts: string[];
  masteredConcepts: string[];
  revisionProblems: {
    id: number;
    title: string;
    difficulty: string;
    solved: boolean;
    reason?: string;
  }[];
  recommendedAction: string;
  message: string;
}

export const getMyPersonalizedRevisionPlan =
  async (): Promise<PersonalizedRevisionPlanResponse> => {

    const response =
      await api.get<PersonalizedRevisionPlanResponse>(
        "/ai-mentor/revision-plan/me"
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

  conceptGrowth: {
    concept: string;
    totalMistakes: number;
    acceptedSubmissions: number;
    growthStatus: string;
    message: string;
  }[];

  recurringMistakes: string[];
  achievements: string[];

  growthSummary: string;
  recommendedNextAction: string;
}

export const getMyGrowthReport =
  async (): Promise<GrowthReportResponse> => {

    const response =
      await api.get<GrowthReportResponse>(
        "/ai-mentor/growth-report/me"
      );

    return response.data;
  };