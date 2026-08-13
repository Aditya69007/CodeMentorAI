import api from "./api";

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

export interface UpdateProfileRequest {
  firstName: string;
  lastName: string;
  username: string;
}

export const updateProfile = async (
  data: UpdateProfileRequest
) => {

  const response = await api.put<UserProfile>(
    "/users/me",
    data
  );

  return response.data;

};

export const getProfile = async (): Promise<UserProfile> => {

  const response = await api.get<UserProfile>(
    "/users/me"
  );

  return response.data;

};

export interface DeleteAccountRequest {
  password: string;
}

export async function deleteAccount(
  request: DeleteAccountRequest
): Promise<void> {

  await api.delete(
    "/users/delete-account",
    {
      data: request,
    }
  );

}

export interface ChangePasswordRequest {
  currentPassword: string;
  newPassword: string;
  confirmPassword: string;
}

export async function changePassword(
  data: ChangePasswordRequest
): Promise<void> {
  await api.post(
    "/users/change-password",
    data
  );
}