import api from "./api";
import type { AiSkillsSummary } from "../types/portfolioSkills";

export async function getSkillsSummary() {
  const response = await api.get<AiSkillsSummary>(
    "/portfolio/ai/skills-summary"
  );

  return response.data;
}