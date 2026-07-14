export type SubmissionStatus =
  | "PENDING"
  | "RUNNING"
  | "ACCEPTED"
  | "WRONG_ANSWER"
  | "TIME_LIMIT_EXCEEDED"
  | "RUNTIME_ERROR"
  | "COMPILATION_ERROR";


export type SubmissionLanguage =
  | "JAVA"
  | "CPP"
  | "PYTHON"
  | "JAVASCRIPT";


export type Language =
  | "CPP"
  | "JAVA"
  | "PYTHON";


// ==========================================
// USER SUBMISSION TYPES
// ==========================================

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


// ==========================================
// ADMIN SUBMISSION TYPES
// ==========================================

export interface AdminSubmission {
  id: number;

  userId: number;
  userName: string;
  userEmail: string;

  problemId: number;
  problemTitle: string;

  language: SubmissionLanguage;
  status: SubmissionStatus;

  passedTestCases: number | null;
  totalTestCases: number | null;

  executionTime: number | null;
  memoryUsed: number | null;

  failedOnHiddenTest: boolean | null;

  createdAt: string;
}


// ==========================================
// ADMIN SUBMISSION PAGE
// ==========================================

export interface AdminSubmissionPage {
  content: AdminSubmission[];

  totalElements: number;
  totalPages: number;

  size: number;
  number: number;

  first: boolean;
  last: boolean;

  numberOfElements: number;
  empty: boolean;
}


// ==========================================
// ADMIN SUBMISSION FILTERS
// ==========================================

export interface AdminSubmissionFilters {
  search?: string;

  status?: SubmissionStatus | "";

  language?: SubmissionLanguage | "";

  page?: number;
  size?: number;
}

export interface AdminSubmissionDetails
  extends AdminSubmission {

  sourceCode: string;

  output: string | null;

  errorMessage: string | null;
}