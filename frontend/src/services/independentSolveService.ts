import api from "./api";
import axios from "axios";

export interface IndependentSolveSessionResponse {

  problemId: number;

  active: boolean;

  startedAt: string;

  endedAt: string | null;

  durationSeconds: number | null;

  submissionsDuringSession: number;

  solvedIndependently: boolean;

  message: string;

}


// ==========================================
// START INDEPENDENT SOLVE SESSION
// ==========================================

export const startIndependentSolveSession =
  async (
    problemId: number
  ): Promise<IndependentSolveSessionResponse> => {

    const response =
      await api.post<IndependentSolveSessionResponse>(
        `/independent-solve/start/${problemId}`
      );

    return response.data;

  };


// ==========================================
// GET ACTIVE SESSION
// ==========================================

export const getActiveIndependentSolveSession =
  async (
    problemId: number
  ): Promise<IndependentSolveSessionResponse | null> => {

    try {

      const response =
        await api.get<IndependentSolveSessionResponse>(
          `/independent-solve/active/${problemId}`
        );

      return response.data;

    } catch (error: unknown) {

      if (
        axios.isAxiosError(error) &&
        error.response?.status === 404
      ) {
        return null;
      }

      throw error;
    }
  };


// ==========================================
// FINISH INDEPENDENT SOLVE SESSION
// ==========================================

export const finishIndependentSolveSession =
  async (
    problemId: number
  ): Promise<IndependentSolveSessionResponse> => {

    const response =
      await api.post<IndependentSolveSessionResponse>(
        `/independent-solve/finish/${problemId}`
      );

    return response.data;

  };

export const getIndependentSolveHistory =
  async (
    problemId: number
  ): Promise<IndependentSolveSessionResponse[]> => {

    const response =
      await api.get<
        IndependentSolveSessionResponse[]
      >(
        `/independent-solve/history/${problemId}`
      );

    return response.data;
  };