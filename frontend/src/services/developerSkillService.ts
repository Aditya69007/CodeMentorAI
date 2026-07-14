import api from "./api";

import type {
  DeveloperSkill,
} from "../types/developerSkill";

import type {
  HintDependencyScore,
} from "../types/hintDependency";

export const getMyDeveloperSkillGraph =
  async (): Promise<DeveloperSkill[]> => {

    const response =
      await api.get<DeveloperSkill[]>(
        "/ai-mentor/skills/me"
      );

    return response.data;
  };

// ==========================================
// GET HINT DEPENDENCY SCORE
// ==========================================

export const getHintDependencyScore =
  async (): Promise<HintDependencyScore> => {

    const response =
      await api.get<HintDependencyScore>(
        "/ai-mentor/hint-dependency/me"
      );

    return response.data;
  };