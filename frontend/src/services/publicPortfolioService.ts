import axios from "axios";

import type { PublicPortfolioResponse } from "../types/publicPortfolio";

const API_BASE_URL =
  import.meta.env.VITE_API_BASE_URL ||
  "http://localhost:8080/api/v1";

export async function getPublicPortfolio(
  username: string
): Promise<PublicPortfolioResponse> {
  const response = await axios.get<PublicPortfolioResponse>(
    `${API_BASE_URL}/portfolio/${encodeURIComponent(username)}`
  );

  return response.data;
}