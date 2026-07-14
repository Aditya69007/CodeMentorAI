export interface DailySubmissionActivity {
  date: string;
  submissions: number;
}

export interface AdminPlatformAnalytics {
  totalUsers: number;
  totalProblems: number;
  totalTopics: number;
  totalSubmissions: number;
  acceptedSubmissions: number;
  acceptanceRate: number;
  totalAiAnalyses: number;
  aiAnalysisCoverage: number;

  submissionActivity: DailySubmissionActivity[];

  submissionStatusDistribution: Record<
    string,
    number
  >;

  languageDistribution: Record<
    string,
    number
  >;

  difficultyDistribution: Record<
    string,
    number
  >;
}