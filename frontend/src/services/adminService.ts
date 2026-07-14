import api from "./api";

import type {
  AdminDashboardStats,
  AdminDashboardAnalytics,
  AdminUserSummary,
  AdminUserDetails,
} from "../types/admin";

import type {
  Difficulty,
  ProblemPage,
} from "../types/problem";

export const getAdminDashboardStats =
  async (): Promise<AdminDashboardStats> => {

    const response =
      await api.get<AdminDashboardStats>(
        "/admin/dashboard/stats"
      );

    return response.data;
  };

export const getAdminProblems = async (

  title: string,

  difficulty: Difficulty | "",

  topicId: number | null,

  active: boolean | null,

  page: number,

  size: number

): Promise<ProblemPage> => {

  const response =
    await api.get<ProblemPage>(
      "/admin/problems",
      {
        params: {

          title,

          difficulty:
            difficulty || undefined,

          topicId:
            topicId ?? undefined,

          active:
            active ?? undefined,

          page,

          size,
        },
      }
    );

  return response.data;
};

export const getAdminDashboardAnalytics =
  async (): Promise<AdminDashboardAnalytics> => {

    const response =
      await api.get<AdminDashboardAnalytics>(
        "/admin/dashboard/analytics"
      );

    return response.data;
  };


export const getAdminUsers =
  async (): Promise<AdminUserSummary[]> => {

    const response =
      await api.get<AdminUserSummary[]>(
        "/admin/users"
      );

    return response.data;
  };

  export const deleteAdminUser =
    async (userId: number): Promise<void> => {

      await api.delete(
        `/admin/users/${userId}`
      );
    };

  export const getAdminUserDetails =
    async (
      userId: number
    ): Promise<AdminUserDetails> => {

      const response =
        await api.get<AdminUserDetails>(
          `/admin/users/${userId}`
        );

      return response.data;
    };