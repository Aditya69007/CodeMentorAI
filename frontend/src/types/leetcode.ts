export interface ContestInfo {
  rating: number;
  globalRanking: number;
  attendedContestsCount: number;
  topPercentage: number;
  badge: string | null;
}

export interface ProblemStats {
  totalSolved: number;
  easySolved: number;
  mediumSolved: number;
  hardSolved: number;
  totalSubmissions: number;
  easySubmissions: number;
  mediumSubmissions: number;
  hardSubmissions: number;
  acceptanceRate: number;
}

export interface CalendarInfo {
    currentStreak: number;
    maxStreak: number;
    totalActiveDays: number;
  submissionCalendar: string;
}

export interface SkillInfo {
  tagName: string;
  tagSlug: string;
  problemsSolved: number;
}

export interface SkillStats {
  fundamental: SkillInfo[];
  intermediate: SkillInfo[];
  advanced: SkillInfo[];
}

export interface BadgeInfo {
  id: string;
  displayName: string;
  icon: string;
  creationDate: string;
  category: string;
}

export interface RecentSubmission {
  id: string;
  title: string;
  titleSlug: string;
  timestamp: string;
}

export interface AnalyticsInfo {
  developerScore: number;
  acceptanceRate: number;
  contestScore: number;
  consistencyScore: number;
  difficultyScore: number;
  skillScore: number;
  strongestSkills: string[];
  weakestSkills: string[];
}

export interface LeetCodeProfile {
  username: string;
  contest: ContestInfo;
  problems: ProblemStats;
  calendar: CalendarInfo;
  skills: SkillStats;
  badges: BadgeInfo[];
  recentSubmissions: RecentSubmission[];
  analytics: AnalyticsInfo;
}