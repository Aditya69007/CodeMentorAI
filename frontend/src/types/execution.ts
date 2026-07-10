import type {
  SubmissionStatus,
} from "./submission";

export interface ExecutionResult {
  status: SubmissionStatus;
  output: string | null;
  errorMessage: string | null;
  executionTime: number | null;
  memoryUsed: number | null;
  passedTestCases: number | null;
  totalTestCases: number | null;
  failedOnHiddenTest: boolean | null;
}