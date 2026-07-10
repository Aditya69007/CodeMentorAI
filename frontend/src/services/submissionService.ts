import api from "./api";

import type {
  SubmissionRequest,
  SubmissionResponse,
} from "../types/submission";

import type {
  ExecutionResult,
} from "../types/execution";


export interface RunCodeRequest {
  problemId: number;
  exampleId: number;
  sourceCode: string;
  language: "CPP" | "JAVA" | "PYTHON";
}


export interface SubmissionPage {
  content: SubmissionResponse[];
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
  numberOfElements: number;
}


// ==========================================
// CREATE SUBMISSION
// ==========================================

export const createSubmission = async (
  data: SubmissionRequest
): Promise<SubmissionResponse> => {

  const response =
    await api.post<SubmissionResponse>(
      "/submissions",
      data
    );

  return response.data;
};


// ==========================================
// RUN CODE
// ==========================================

export const runCode = async (
  request: RunCodeRequest
): Promise<ExecutionResult> => {

  const response =
    await api.post<ExecutionResult>(
      "/executions/run",
      request
    );

  return response.data;
};

// ==========================================
// GET MY SUBMISSIONS
// ==========================================

export const getMySubmissions = async (
  page: number = 0,
  size: number = 10
): Promise<SubmissionPage> => {

  const response =
    await api.get<SubmissionPage>(
      "/submissions/me",
      {
        params: {
          page,
          size,
        },
      }
    );

  return response.data;
};

export const getMyProblemSubmissions = async (
  problemId: number,
  page: number = 0,
  size: number = 10
): Promise<SubmissionPage> => {
  const response = await api.get<SubmissionPage>(
    `/submissions/problem/${problemId}`,
    {
      params: {
        page,
        size,
      },
    }
  );

  return response.data;
};