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


export interface MistakeSummary {
  mistakeType: MistakeType;
  count: number;
}


export interface DeveloperMistakeProfile {
  totalMistakes: number;

  mostCommonMistake: MistakeType | null;

  weakestConcept: string | null;

  mistakeTypeBreakdown: Record<string, number>;

  severityBreakdown: Record<string, number>;

  conceptBreakdown: Record<string, number>;

  insights: string[];
}


export interface RecurringMistake {
  mistakeType: MistakeType;

  occurrenceCount: number;

  affectedProblems: number;

  message: string;
}

export interface ConceptGrowth {
  concept: string;
  totalMistakes: number;
  acceptedSubmissions: number;
  growthStatus: "REPEATING" | "IMPROVING" | "MASTERED";
  message: string;
}

export interface RecommendedProblem {
  id: number;
  title: string;
  difficulty: "EASY" | "MEDIUM" | "HARD";
  solved: boolean;
}

export interface PracticeRecommendation {
  concept: string;
  totalMistakes: number;
  successfulRecoveries: number;
  growthStatus: "REPEATING" | "IMPROVING" | "MASTERED";
  priority: "HIGH" | "MEDIUM" | "LOW";
  recommendedProblemCount: number;
  reason: string;
  recommendation: string;
  problems: RecommendedProblem[];
}
