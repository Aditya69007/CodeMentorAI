import api from "./api";
import type { PortfolioScore } from "../types/portfolioScore";

export async function getPortfolioScore() {
  const { data } = await api.get<PortfolioScore>(
    "/portfolio/ai/portfolio-score"
  );

  return data;
}