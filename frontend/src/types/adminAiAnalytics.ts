export interface AdminAiAnalytics {

  totalAnalyses: number;

  totalChatMessages: number;

  totalProgressiveHints: number;

  totalMistakesDetected: number;

  usersWithMistakes: number;

  mostCommonMistakeType: string | null;

  mostCommonConcept: string | null;

  mistakeTypeDistribution: Record<
    string,
    number
  >;

  severityDistribution: Record<
    string,
    number
  >;

  conceptDistribution: Record<
    string,
    number
  >;
}