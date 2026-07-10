import api from "./api";

import type {
  ConceptGrowth,
  DeveloperMistakeProfile,
  MistakeSummary,
  RecurringMistake,
  PracticeRecommendation,
} from "../types/aiMentor";


// ==================================================
// GET USER MISTAKE SUMMARY
// ==================================================

export const getMyMistakeSummary =
  async (): Promise<MistakeSummary[]> => {

    const response =
      await api.get<MistakeSummary[]>(
        "/ai-mentor/mistakes/summary/me"
      );

    return response.data;
  };


// ==================================================
// GET DEVELOPER MISTAKE PROFILE
// ==================================================

export const getMyDeveloperMistakeProfile =
  async (): Promise<DeveloperMistakeProfile> => {

    const response =
      await api.get<DeveloperMistakeProfile>(
        "/ai-mentor/mistakes/profile/me"
      );

    return response.data;
  };


// ==================================================
// GET RECURRING MISTAKES
// ==================================================

export const getMyRecurringMistakes =
  async (): Promise<RecurringMistake[]> => {

    const response =
      await api.get<RecurringMistake[]>(
        "/ai-mentor/mistakes/recurring/me"
      );

    return response.data;
  };


// ==================================================
// GET CONCEPT GROWTH
// ==================================================

export const getMyConceptGrowth =
  async (): Promise<ConceptGrowth[]> => {

    const response =
      await api.get<ConceptGrowth[]>(
        "/ai-mentor/growth/me"
      );

    return response.data;
  };


// ==================================================
// GET PRACTICE RECOMMENDATIONS
// ==================================================

export const getMyPracticeRecommendations =
  async (): Promise<PracticeRecommendation[]> => {

    const response =
      await api.get<PracticeRecommendation[]>(
        "/ai-mentor/practice-recommendations/me"
      );

    return response.data;
  };