export interface DailyActivity {
  date: string;
  submissionCount: number;
  acceptedSubmissionCount: number;
}

export interface DeveloperActivity {
  currentStreak: number;
  longestStreak: number;
  totalActiveDays: number;
  totalSubmissions: number;
  activity: DailyActivity[];
}