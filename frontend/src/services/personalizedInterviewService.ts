import api from "./api";


// =====================================================
// TYPES
// =====================================================

export interface InterviewQuestionResponse {

  questionId: number;

  questionNumber: number;

  question: string;

  questionType: string;

  concept: string;

  difficulty: string;

  answered: boolean;

  answerScore: number | null;

  aiFeedback: string | null;

  strengths: string | null;

  improvements: string | null;
}


export interface InterviewSessionResponse {

  sessionId: number;

  interviewLevel: string;

  developerLevel: string;

  active: boolean;

  currentQuestionNumber: number;

  totalQuestions: number;

  finalScore: number | null;

  finalFeedback: string | null;

  startedAt: string;

  completedAt: string | null;

  currentQuestion: InterviewQuestionResponse | null;

  questions: InterviewQuestionResponse[];

  message: string;
}


// =====================================================
// START INTERVIEW
// =====================================================

export const startInterview =
  async (): Promise<InterviewSessionResponse> => {

    const response =
      await api.post<InterviewSessionResponse>(
        "/interviews/start"
      );

    return response.data;
  };


// =====================================================
// SUBMIT ANSWER
// =====================================================

export const submitInterviewAnswer =
  async (
    sessionId: number,
    questionId: number,
    answer: string
  ): Promise<InterviewSessionResponse> => {

    const response =
      await api.post<InterviewSessionResponse>(
        `/interviews/${sessionId}/questions/${questionId}/answer`,
        {
          answer,
        }
      );

    return response.data;
  };


// =====================================================
// GET ACTIVE INTERVIEW
// =====================================================

export const getActiveInterview =
  async (): Promise<InterviewSessionResponse | null> => {

    const response =
      await api.get<InterviewSessionResponse>(
        "/interviews/active/me"
      );


    if (response.status === 204) {

      return null;
    }


    return response.data;
  };


// =====================================================
// GET INTERVIEW HISTORY
// =====================================================

export const getInterviewHistory =
  async (): Promise<InterviewSessionResponse[]> => {

    const response =
      await api.get<InterviewSessionResponse[]>(
        "/interviews/history/me"
      );

    return response.data;
  };


// =====================================================
// GET INTERVIEW DETAILS
// =====================================================

export const getInterviewDetails =
  async (
    sessionId: number
  ): Promise<InterviewSessionResponse> => {

    const response =
      await api.get<InterviewSessionResponse>(
        `/interviews/${sessionId}`
      );

    return response.data;
  };