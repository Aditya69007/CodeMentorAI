import api from "./api";

import type {
  Difficulty,
  Problem,
  ProblemPage,
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

export const getMyProblemProgress = async () => {
  const response = await api.get(
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