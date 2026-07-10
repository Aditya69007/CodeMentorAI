export interface AiMentorResponse {
  submissionId: number;
  explanation: string;
  hint: string;
  conceptToStudy: string;
}

export interface AiMentorChatRequest {
  message: string;
}

export interface AiMentorChatResponse {
  submissionId: number;
  response: string;
}

export interface AiMentorChatMessage {
  id: number;
  role: "USER" | "ASSISTANT";
  content: string;
  createdAt: string;
}

export interface AiMentorHintResponse {
  submissionId: number;
  level: number;
  response: string;
}

export interface PastMistakeRecallResponse {
  repeatedMistake: boolean;
  mistakeType: string | null;
  concept: string | null;
  occurrenceCount: number | null;
  previousSubmissionId: number | null;
  previousProblemTitle: string | null;
  message: string;
  memoryAdvice: string | null;
}


// ==================================================
// MISTAKE TYPES
// ==================================================

export type MistakeType =
  | "SYNTAX_ERROR"
  | "WRONG_DATA_STRUCTURE"
  | "WRONG_ALGORITHM"
  | "LOGIC_ERROR"
  | "BOUNDARY_CONDITION"
  | "EDGE_CASE_MISSED"
  | "TIME_COMPLEXITY"
  | "SPACE_COMPLEXITY"
  | "INCORRECT_INITIALIZATION"
  | "LOOP_ERROR"
  | "RECURSION_ERROR"
  | "BASE_CASE_ERROR"
  | "NULL_HANDLING"
  | "INPUT_OUTPUT_ERROR"
  | "OTHER";


// ==================================================
// MISTAKE SUMMARY
// ==================================================

export interface MistakeSummary {
  mistakeType: MistakeType;
  count: number;
}


// ==================================================
// DEVELOPER MISTAKE PROFILE
// ==================================================

export interface DeveloperMistakeProfile {
  totalMistakes: number;

  mostCommonMistake: MistakeType | null;

  weakestConcept: string | null;

  mistakeTypeBreakdown: Record<string, number>;

  severityBreakdown: Record<string, number>;

  conceptBreakdown: Record<string, number>;

  insights: string[];
}


// ==================================================
// RECURRING MISTAKES
// ==================================================

export interface RecurringMistake {
  mistakeType: MistakeType;

  occurrenceCount: number;

  affectedProblems: number;

  message: string;
}


// ==================================================
// CONCEPT GROWTH
// ==================================================

export interface ConceptGrowth {
  concept: string;

  totalMistakes: number;

  acceptedSubmissions: number;

  growthStatus:
    | "REPEATING"
    | "IMPROVING"
    | "MASTERED";

  message: string;
}


// ==================================================
// RECOMMENDED PROBLEM
// ==================================================

export interface RecommendedProblem {
  id: number;

  title: string;

  difficulty:
    | "EASY"
    | "MEDIUM"
    | "HARD";

  solved: boolean;
}


// ==================================================
// PRACTICE RECOMMENDATION
// ==================================================

export interface PracticeRecommendation {
  concept: string;

  totalMistakes: number;

  successfulRecoveries: number;

  growthStatus:
    | "REPEATING"
    | "IMPROVING"
    | "MASTERED";

  priority:
    | "HIGH"
    | "MEDIUM"
    | "LOW";

  recommendedProblemCount: number;

  reason: string;

  recommendation: string;

  problems: RecommendedProblem[];
}