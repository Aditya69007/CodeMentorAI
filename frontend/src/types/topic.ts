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
  solved?: boolean;
  attempted?: boolean;
  acceptanceRate?: number;
  attempts?: number;
}

export interface AdminTopic {
  id: number;
  name: string;
  slug: string;
  description: string | null;
  active: boolean;
  problemCount: number;
  createdAt: string;
  updatedAt: string;
}

export interface TopicRequest {
  name: string;
  slug: string;
  description: string;
}