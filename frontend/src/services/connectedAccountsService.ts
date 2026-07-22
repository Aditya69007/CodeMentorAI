import api from "./api";

export interface ConnectedAccountsResponse {
  githubUsername: string | null;
  leetcodeUsername: string | null;
  githubConnected: boolean;
  leetcodeConnected: boolean;
  githubLastSyncedAt: string | null;
  leetcodeLastSyncedAt: string | null;
}

export interface UpdateConnectedAccountsRequest {
  githubUsername: string;
  leetcodeUsername: string;
}

export interface GitHubProfileResponse {
  username: string;
  name: string;
  avatarUrl: string;
  bio: string;
  profileUrl: string;
  publicRepositories: number;
  followers: number;
  following: number;
}

export const getConnectedAccounts = async () => {
  const response = await api.get<ConnectedAccountsResponse>(
    "/users/connected-accounts"
  );

  return response.data;
};

export const updateConnectedAccounts = async (
  request: UpdateConnectedAccountsRequest
) => {
  const response = await api.put<ConnectedAccountsResponse>(
    "/users/connected-accounts",
    request
  );

  return response.data;
};

export const getGitHubProfile = async (
  username: string
) => {
  const response = await api.get<GitHubProfileResponse>(
    `/github/profile/${username}`
  );

  return response.data;
};