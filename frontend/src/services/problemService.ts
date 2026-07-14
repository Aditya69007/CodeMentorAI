import api from "./api";

import type {
  Difficulty,
  Problem,
  ProblemPage,
  ProblemProgress,
  ProblemRequest,
} from "../types/problem";


export type ProblemStatus =
  | "ALL"
  | "SOLVED"
  | "UNSOLVED";


export const getAllProblems = async (): Promise<Problem[]> => {

  const response =
    await api.get<Problem[]>("/problems");

  return response.data;
};

export const createProblem = async (
  request: ProblemRequest
): Promise<Problem> => {
  const response = await api.post<Problem>(
    "/problems",
    request
  );

  return response.data;
};


export const updateProblem = async (
  id: number,
  request: ProblemRequest
): Promise<Problem> => {
  const response = await api.put<Problem>(
    `/problems/${id}`,
    request
  );

  return response.data;
};


export const deleteProblem = async (
  id: number
): Promise<void> => {
  await api.delete(`/problems/${id}`);
};

export const getProblemById = async (
  id: number
): Promise<Problem> => {

  const response =
    await api.get<Problem>(
      `/problems/${id}`
    );

  return response.data;
};


export const filterProblems = async (

  title: string,

  difficulty: Difficulty | "",

  page: number,

  size: number

): Promise<ProblemPage> => {

  const response =
    await api.get<ProblemPage>(
      "/problems/filter",
      {
        params: {

          title,

          difficulty:
            difficulty || undefined,

          page,

          size,
        },
      }
    );

  return response.data;
};


export const filterMyProblems = async (

  title: string,

  difficulty: Difficulty | "",

  status: ProblemStatus,

  page: number,

  size: number

): Promise<ProblemPage> => {

  const response =
    await api.get<ProblemPage>(
      "/problems/filter/me",
      {
        params: {

          title,

          difficulty:
            difficulty || undefined,

          status,

          page,

          size,
        },
      }
    );

  return response.data;
};

export const getMyProblemProgress =
  async (): Promise<ProblemProgress> => {

    const response =
      await api.get<ProblemProgress>(
        "/problems/progress/me"
      );

    return response.data;
  };


export const getMySolvedProblemIds = async (): Promise<number[]> => {
  const response = await api.get<number[]>(
    "/problems/solved/me"
  );

  return response.data;
};

