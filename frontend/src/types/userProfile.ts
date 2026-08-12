export interface UserProfile {
  userId: number;
  firstName: string;
  lastName: string;
  username: string;

  email: string;
  role: string;
  provider: string;
  profilePicture?: string | null;

  githubUsername?: string | null;
  leetcodeUsername?: string | null;
}