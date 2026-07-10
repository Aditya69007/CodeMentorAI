export type SubmissionStatus =
  | "PENDING"
  | "RUNNING"
  | "ACCEPTED"
  | "WRONG_ANSWER"
  | "COMPILATION_ERROR"
  | "TIME_LIMIT_EXCEEDED"
  | "RUNTIME_ERROR";

export type Language = "CPP" | "JAVA" | "PYTHON";

export interface SubmissionRequest {
  problemId: number;
  sourceCode: string;
  language: Language;
}

export interface SubmissionResponse {
  id: number;
  problemId: number;
  problemTitle: string;
  sourceCode: string;
  output: string | null;
  errorMessage: string | null;
  language: Language;
  status: SubmissionStatus;
  executionTime: number | null;
  memoryUsed: number | null;
  createdAt: string;
  passedTestCases: number | null;
  totalTestCases: number | null;
  failedOnHiddenTest: boolean | null;
}