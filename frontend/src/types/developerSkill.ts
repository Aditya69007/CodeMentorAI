export type DeveloperSkillLevel =
  | "MASTERED"
  | "STRONG"
  | "DEVELOPING"
  | "NEEDS_PRACTICE";

export interface DeveloperSkill {
  topicId: number;
  topicName: string;
  totalSubmissions: number;
  acceptedSubmissions: number;
  totalMistakes: number;
  acceptanceRate: number;
  skillScore: number;
  skillLevel: DeveloperSkillLevel;
  message: string;
}