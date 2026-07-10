import api from "./api";

import type {
  Topic,
  TopicProblem,
} from "../types/topic";


export const getAllTopics =
  async (): Promise<Topic[]> => {

    const response =
      await api.get<Topic[]>(
        "/topics"
      );

    return response.data;
  };


export const getTopicBySlug =
  async (
    slug: string
  ): Promise<Topic> => {

    const response =
      await api.get<Topic>(
        `/topics/${slug}`
      );

    return response.data;
  };


export const getProblemsByTopic =
  async (
    slug: string
  ): Promise<TopicProblem[]> => {

    const response =
      await api.get<TopicProblem[]>(
        `/topics/${slug}/problems`
      );

    return response.data;
  };