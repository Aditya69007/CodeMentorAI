import api from "./api";

import type {
  FeaturedProject,
  FeaturedProjectsRequest,
} from "../types/featuredProject";

/**
 * Get the user's featured projects.
 */
export async function getFeaturedProjects(): Promise<FeaturedProject[]> {
  const response = await api.get<FeaturedProject[]>(
    "/portfolio/featured-projects"
  );

  return response.data;
}

/**
 * Update the user's featured projects.
 */
export async function updateFeaturedProjects(
  request: FeaturedProjectsRequest
): Promise<void> {
  await api.put(
    "/portfolio/featured-projects",
    request
  );
}