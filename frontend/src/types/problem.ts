export type Difficulty = "EASY" | "MEDIUM" | "HARD";

export interface ProblemExample {
  id: number;
  input: string;
  output: string;
  explanation: string | null;
  orderIndex: number;
}

export interface Problem {
  id: number;
  title: string;
  description: string;
  difficulty: Difficulty;
  constraints: string;
  inputFormat: string;
  outputFormat: string;
  sampleInput: string;
  sampleOutput: string;
  tags: string[];
  examples: ProblemExample[];
  active: boolean;
}

/* ADD THIS */
export interface ProblemProgress {
  totalProblems: number;
  solvedProblems: number;
  unsolvedProblems: number;
  solvedPercentage: number;
}

export interface ProblemPage {
  content: Problem[];
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
}

export interface ProblemExampleRequest {
  input: string;
  output: string;
  explanation: string;
  orderIndex: number;
}

export interface ProblemRequest {
  title: string;
  description: string;
  difficulty: Difficulty;
  constraints: string;
  inputFormat: string;
  outputFormat: string;
  sampleInput: string;
  sampleOutput: string;
  tags: string[];
  examples: ProblemExampleRequest[];
}