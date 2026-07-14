import api from "./api";

import type {
  Topic,
  TopicProblem,
  AdminTopic,
  TopicRequest,
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

  export const getAdminTopics =
  async (): Promise<AdminTopic[]> => {

    const response =
      await api.get<AdminTopic[]>(
        "/topics/admin/all"
      );

    return response.data;
  };


export const createTopic =
  async (
    data: TopicRequest
  ): Promise<AdminTopic> => {

    const response =
      await api.post<AdminTopic>(
        "/topics/admin",
        data
      );

    return response.data;
  };


export const updateTopic =
  async (
    id: number,
    data: TopicRequest
  ): Promise<AdminTopic> => {

    const response =
      await api.put<AdminTopic>(
        `/topics/admin/${id}`,
        data
      );

    return response.data;
  };


export const toggleTopicStatus =
  async (
    id: number
  ): Promise<AdminTopic> => {

    const response =
      await api.patch<AdminTopic>(
        `/topics/admin/${id}/status`
      );

    return response.data;
  };


export const deleteTopic =
  async (
    id: number
  ): Promise<void> => {

    await api.delete(
      `/topics/admin/${id}`
    );
  };