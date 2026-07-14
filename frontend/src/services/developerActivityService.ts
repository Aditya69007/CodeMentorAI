import api from "./api";

import type {
  DeveloperActivity,
} from "../types/developerActivity";

export const getMyDeveloperActivity =
  async (): Promise<DeveloperActivity> => {

    const response =
      await api.get<DeveloperActivity>(
        "/developer-activity/me"
      );

    return response.data;
  };