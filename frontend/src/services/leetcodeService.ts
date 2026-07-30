import api from "./api";
import type { LeetCodeProfile } from "../types/leetcode";

class LeetCodeService {

  /**
   * Fetch complete LeetCode profile
   */
  async getProfile(
    username: string
  ): Promise<LeetCodeProfile> {

    const response = await api.get<LeetCodeProfile>(
      `/leetcode/profile/${username}`
    );

    return response.data;
  }

  /**
   * Refresh profile
   */
  async refreshProfile(
    username: string
  ): Promise<LeetCodeProfile> {

    const response = await api.get<LeetCodeProfile>(
      `/leetcode/profile/${username}`
    );

    return response.data;
  }

}

export default new LeetCodeService();