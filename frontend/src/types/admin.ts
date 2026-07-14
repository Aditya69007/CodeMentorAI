export interface AdminDashboardStats {
  totalUsers: number;
  totalAdmins: number;
  totalProblems: number;
  totalTopics: number;
  totalSubmissions: number;
  acceptedSubmissions: number;
  totalAiAnalyses: number;
}

export interface DailySubmissionStats {
  date: string;
  submissions: number;
}

export interface AdminDashboardAnalytics {
  submissionActivity: DailySubmissionStats[];
  submissionStatusDistribution: Record<string, number>;
  difficultyDistribution: Record<string, number>;
  acceptanceRate: number;
  aiAnalysisCoverage: number;
}

export interface AdminUserSummary {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  profilePicture: string | null;
  enabled: boolean;
  createdAt: string;
  totalSubmissions: number;
  solvedProblems: number;
  acceptedSubmissions: number;
  acceptanceRate: number;
  totalAiAnalyses: number;
  totalMistakes: number;
}

export interface AdminUserSubmissionActivity {
  date: string;
  submissions: number;
}

export interface AdminUserTopicPerformance {
  topicId: number;
  topicName: string;
  totalSubmissions: number;
  acceptedSubmissions: number;
  mistakes: number;
  acceptanceRate: number;
}

export interface AdminUserRecentSubmission {
  id: number;
  problemId: number;
  problemTitle: string;
  topicName: string;
  difficulty: string;
  language: string;
  status: string;
  passedTestCases: number | null;
  totalTestCases: number | null;
  executionTime: number | null;
  createdAt: string;
}

export interface AdminUserDetails {
  id: number;

  firstName: string;
  lastName: string;
  email: string;

  profilePicture: string | null;

  enabled: boolean;

  createdAt: string;

  totalSubmissions: number;
  solvedProblems: number;
  acceptedSubmissions: number;
  acceptanceRate: number;
  totalAiAnalyses: number;
  totalMistakes: number;

  submissionStatusDistribution:
    Record<string, number>;

  mistakeTypeDistribution:
    Record<string, number>;

  submissionActivity:
    AdminUserSubmissionActivity[];

  topicPerformance:
    AdminUserTopicPerformance[];

  recentSubmissions:
    AdminUserRecentSubmission[];
}