import api from "./api";
import type { AiDeveloperSummary } from "../types/portfolioAi";

export async function getAiDeveloperSummary() {
  const response = await api.get<AiDeveloperSummary>(
    "/portfolio/ai/developer-summary"
  );

  return response.data;
}