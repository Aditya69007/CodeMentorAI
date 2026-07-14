export interface HintDependencyScore {
  totalSubmissions: number;
  totalProblemsAttempted: number;
  problemsWithHints: number;
  totalHintsUsed: number;

  level1HintsUsed: number;
  level2HintsUsed: number;
  level3HintsUsed: number;
  level4HintsUsed: number;

  hintUsageRate: number;
  dependencyScore: number;

  dependencyLevel:
    | "LOW"
    | "HEALTHY"
    | "MODERATE"
    | "HIGH"
    | "VERY_HIGH";

  message: string;
}