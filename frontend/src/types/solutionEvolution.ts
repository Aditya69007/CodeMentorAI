import type {
  SubmissionStatus,
} from "./submission";


export type EvolutionStatus =
  | "FIRST_ATTEMPT"
  | "IMPROVED"
  | "REGRESSED"
  | "NO_CHANGE"
  | "STATUS_CHANGED"
  | "SOLVED";


export type SolutionEvolutionLanguage =
  | "JAVA"
  | "CPP"
  | "PYTHON"
  | "JAVASCRIPT";


export interface SolutionEvolutionAttempt {

  submissionId: number;

  attemptNumber: number;

  status: SubmissionStatus;

  language: SolutionEvolutionLanguage;

  passedTestCases: number | null;

  totalTestCases: number | null;

  executionTime: number | null;

  memoryUsed: number | null;

  failedOnHiddenTest: boolean | null;

  sourceCode: string;

  aiExplanation: string | null;

  aiHint: string | null;

  conceptToStudy: string | null;

  evolutionStatus: EvolutionStatus;

  passedTestCasesChange: number;

  improvedFromPreviousAttempt: boolean;

  evolutionMessage: string;

  createdAt: string;
}