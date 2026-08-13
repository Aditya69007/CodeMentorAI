import api from "./api";

export interface AdminSummary {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  profilePicture?: string | null;
  enabled: boolean;
  createdAt: string;
}

export interface CreateAdminRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  username?: string;
}

export async function getSuperAdmins(): Promise<AdminSummary[]> {
  const response = await api.get<AdminSummary[]>(
    "/super-admin/admins"
  );

  return response.data;
}

export async function createSuperAdmin(
  data: CreateAdminRequest
): Promise<AdminSummary> {
  const response = await api.post<AdminSummary>(
    "/super-admin/admins",
    data
  );

  return response.data;
}

export async function deleteSuperAdmin(
  adminId: number,
  password: string
): Promise<void> {
  await api.delete(
    `/super-admin/admins/${adminId}`,
    {
      data: {
        password,
      },
    }
  );
}