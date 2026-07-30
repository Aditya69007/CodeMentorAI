export interface GitHubProfile {

    username: string;

    name: string;

    avatarUrl: string;

    bio: string;

    profileUrl: string;

    publicRepositories: number;

    publicGists: number;

    createdAt: string;

    company: string | null;

    location: string | null;

    blog: string;

    followers: number;

    following: number;

}

export interface GitHubStatistics {

    developerScore: number;

    repositories: number;

    followers: number;

    following: number;

    publicGists: number;

    accountAgeYears: number;

}

export interface GitHubAnalytics {

    repositoryScore: number;

    languageDiversityScore: number;

    technologyScore: number;

    consistencyScore: number;

    openSourceScore: number;

    strongestTechnologies: string[];

    recommendedTechnologies: string[];

    strengths: string[];

    improvements: string[];

    insights: string[];

}

export interface GitHubLanguage {

    language: string;

    percentage: number;

}

export interface GitHubTopRepository {

    name: string;

    description: string;

    language: string;

    stars: number;

    forks: number;

    repositoryUrl: string;

}

export interface GitHubDashboard {

    profile: GitHubProfile;

    statistics: GitHubStatistics;

    analytics: GitHubAnalytics;

    languages: GitHubLanguage[];

    topRepositories: GitHubTopRepository[];

}