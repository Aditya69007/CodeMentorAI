export interface SkillCategory {
  category: string;
  score: number;
  skills: string[];
}

export interface AiSkillsSummary {
  categories: SkillCategory[];
  developerLevel: string;
}