export interface FeaturedProject {
  repositoryName: string;
  displayOrder: number;
}

export interface FeaturedProjectsRequest {
  repositoryNames: string[];
}