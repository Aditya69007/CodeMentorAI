import api from "./api";

export interface UpdateProfileRequest {
  firstName: string;
  lastName: string;
}

export const updateProfile = async (
  data: UpdateProfileRequest
) => {

  const response = await api.put(
    "/users/me",
    data
  );

  return response.data;

};

export const getProfile = async () => {

  const response = await api.get(
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