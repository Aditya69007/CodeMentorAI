import type { Difficulty } from "./problem";

export interface Topic {
  id: number;
  name: string;
  slug: string;
  description: string;
  problemCount: number;
}

export interface TopicProblem {
  id: number;
  title: string;
  difficulty: Difficulty;
  tags: string[];
}