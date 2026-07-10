import api from "./api";


import type {
  AiMentorResponse,
  AiMentorChatRequest,
  AiMentorChatResponse,
  AiMentorChatMessage,
  AiMentorHintResponse,
  PastMistakeRecallResponse,
} from "../types/aiMentor";

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
): Promise<AiMentorResponse> => {

  const response =
    await api.get<AiMentorResponse>(
      `/ai-mentor/analysis/${submissionId}`
    );

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

export const getProgressiveHints = async (
  submissionId: number
): Promise<AiMentorHintResponse[]> => {

  const response =
    await api.get<AiMentorHintResponse[]>(
      `/ai-mentor/hints/${submissionId}`
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