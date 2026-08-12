# CodeMentorAI

## Detailed Project Structure

This file contains a much more complete source-tree view of the backend Java files, frontend TypeScript/TSX files, and supporting assets so you can understand the project layout and share it with ChatGPT.

```text
CodeMentorAI/
├── README.md
├── LICENSE
├── docker-compose.yml
├── .gitignore
├── .github/
├── .vscode/
├── ai-service/
├── backend/
│   ├── pom.xml
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── HELP.md
│   ├── .env
│   └── src/
│       ├── main/
│       │   ├── java/com/codementor/backend/
│       │   │   ├── BackendApplication.java
│       │   │   ├── analytics/github/GitHubInsightCalculator.java
│       │   │   ├── analytics/github/GitHubLanguageCalculator.java
│       │   │   ├── analytics/github/GitHubRepositoryScoreCalculator.java
│       │   │   ├── ai/GeminiService.java
│       │   │   ├── ai/GeminiServiceImpl.java
│       │   │   ├── client/GitHubClient.java
│       │   │   ├── client/LeetCodeClient.java
│       │   │   ├── client/LeetCodeGraphQLClient.java
│       │   │   ├── config/AdminDataInitializer.java
│       │   │   ├── config/AsyncConfig.java
│       │   │   ├── config/OpenApiConfig.java
│       │   │   ├── config/PasswordConfig.java
│       │   │   ├── config/ProblemDataSeeder.java
│       │   │   ├── config/TopicDataInitializer.java
│       │   │   ├── config/UsernameMigrationRunner.java
│       │   │   ├── controller/AdminAiAnalyticsController.java
│       │   │   ├── controller/AdminController.java
│       │   │   ├── controller/AiMentorController.java
│       │   │   ├── controller/AuthController.java
│       │   │   ├── controller/DeveloperActivityController.java
│       │   │   ├── controller/ExecutionController.java
│       │   │   ├── controller/FeaturedProjectController.java
│       │   │   ├── controller/GitHubController.java
│       │   │   ├── controller/IndependentSolveSessionController.java
│       │   │   ├── controller/LeetCodeController.java
│       │   │   ├── controller/LearningAnalyticsController.java
│       │   │   ├── controller/PersonalizedInterviewController.java
│       │   │   ├── controller/PortfolioAiController.java
│       │   │   ├── controller/ProblemController.java
│       │   │   ├── controller/PublicPortfolioController.java
│       │   │   ├── controller/SubmissionController.java
│       │   │   ├── controller/TestCaseController.java
│       │   │   ├── controller/TestEmailController.java
│       │   │   ├── controller/TopicController.java
│       │   │   ├── controller/UserController.java
│       │   │   ├── dto/AdminAiAnalyticsResponse.java
│       │   │   ├── dto/AdminDashboardAnalyticsResponse.java
│       │   │   ├── dto/AdminDashboardStatsResponse.java
│       │   │   ├── dto/AdminPlatformAnalyticsResponse.java
│       │   │   ├── dto/AdminRecentSubmissionResponse.java
│       │   │   ├── dto/AdminSubmissionDetailsResponse.java
│       │   │   ├── dto/AdminSubmissionResponse.java
│       │   │   ├── dto/AdminTopicResponse.java
│       │   │   ├── dto/AdminUserActivityResponse.java
│       │   │   ├── dto/AdminUserDetailResponse.java
│       │   │   ├── dto/AdminUserSummaryResponse.java
│       │   │   ├── dto/AdminUserTopicPerformanceResponse.java
│       │   │   ├── dto/AdaptiveMentorProfileResponse.java
│       │   │   ├── dto/AiMentorChatMessageResponse.java
│       │   │   ├── dto/AiMentorChatRequest.java
│       │   │   ├── dto/AiMentorChatResponse.java
│       │   │   ├── dto/AiMentorHintResponse.java
│       │   │   ├── dto/AiMentorResponse.java
│       │   │   ├── dto/AiMistakeDetectionResponse.java
│       │   │   ├── dto/AiMistakeItemResponse.java
│       │   │   ├── dto/AiMistakeResponse.java
│       │   │   ├── dto/AiMistakeSummaryResponse.java
│       │   │   ├── dto/AuthResponse.java
│       │   │   ├── dto/ConnectedAccountsResponse.java
│       │   │   ├── dto/ConceptGrowthResponse.java
│       │   │   ├── dto/DailySubmissionStatsResponse.java
│       │   │   ├── dto/DeleteAccountRequest.java
│       │   │   ├── dto/DeveloperMistakeProfileResponse.java
│       │   │   ├── dto/DeveloperSkillResponse.java
│       │   │   ├── dto/ExecutionResult.java
│       │   │   ├── dto/FeaturedProjectResponse.java
│       │   │   ├── dto/FeaturedProjectsRequest.java
│       │   │   ├── dto/GrowthReportResponse.java
│       │   │   ├── dto/HintDependencyScoreResponse.java
│       │   │   ├── dto/IndependentSolveSessionResponse.java
│       │   │   ├── dto/InterviewAnswerRequest.java
│       │   │   ├── dto/InterviewQuestionResponse.java
│       │   │   ├── dto/InterviewSessionResponse.java
│       │   │   ├── dto/LeetCodeProfileResponse.java
│       │   │   ├── dto/LoginRequest.java
│       │   │   ├── dto/NotificationSettingsResponse.java
│       │   │   ├── dto/PastMistakeRecallResponse.java
│       │   │   ├── dto/PersonalizedInterviewProfileResponse.java
│       │   │   ├── dto/PersonalizedLearningPlanResponse.java
│       │   │   ├── dto/PersonalizedRevisionPlanResponse.java
│       │   │   ├── dto/PracticeRecommendationResponse.java
│       │   │   ├── dto/ProblemExampleRequest.java
│       │   │   ├── dto/ProblemExampleResponse.java
│       │   │   ├── dto/ProblemProgressResponse.java
│       │   │   ├── dto/ProblemRequest.java
│       │   │   ├── dto/ProblemResponse.java
│       │   │   ├── dto/ProblemStatusFilter.java
│       │   │   ├── dto/RecommendedProblemResponse.java
│       │   │   ├── dto/RecommendationScore.java
│       │   │   ├── dto/RecurringMistakeResponse.java
│       │   │   ├── dto/RegisterRequest.java
│       │   │   ├── dto/RunCodeRequest.java
│       │   │   ├── dto/SolutionEvolutionResponse.java
│       │   │   ├── dto/SubmissionRequest.java
│       │   │   ├── dto/SubmissionResponse.java
│       │   │   ├── dto/TestCaseRequest.java
│       │   │   ├── dto/TopicProgressResponse.java
│       │   │   ├── dto/TopicRequest.java
│       │   │   ├── dto/TopicResponse.java
│       │   │   ├── dto/UpdateConnectedAccountsRequest.java
│       │   │   ├── dto/UpdateNotificationSettingsRequest.java
│       │   │   ├── dto/UpdateProfileRequest.java
│       │   │   ├── dto/UserProfileResponse.java
│       │   │   ├── dto/activity/DailyActivityResponse.java
│       │   │   ├── dto/activity/DeveloperActivityResponse.java
│       │   │   ├── dto/auth/ChangePasswordRequest.java
│       │   │   ├── dto/auth/ForgotPasswordRequest.java
│       │   │   ├── dto/auth/ResetPasswordRequest.java
│       │   │   ├── dto/github/GitHubAnalyticsResponse.java
│       │   │   ├── dto/github/GitHubDashboardResponse.java
│       │   │   ├── dto/github/GitHubLanguageResponse.java
│       │   │   ├── dto/github/GitHubRepositoryDto.java
│       │   │   ├── dto/github/GitHubStatisticsResponse.java
│       │   │   ├── dto/github/GitHubTopRepositoryResponse.java
│       │   │   ├── dto/leetcode/GraphQLRequest.java
│       │   │   ├── dto/leetcode/GraphQLResponse.java
│       │   │   ├── dto/leetcode/analytics/LeetCodeAnalyticsService.java
│       │   │   ├── dto/leetcode/analytics/LeetCodeAnalyticsServiceImpl.java
│       │   │   ├── dto/leetcode/calculator/AcceptanceCalculator.java
│       │   │   ├── dto/leetcode/calculator/CalendarCalculator.java
│       │   │   ├── dto/leetcode/calculator/ConsistencyCalculator.java
│       │   │   ├── dto/leetcode/calculator/ContestCalculator.java
│       │   │   ├── dto/leetcode/calculator/DeveloperScoreCalculator.java
│       │   │   ├── dto/leetcode/calculator/DifficultyCalculator.java
│       │   │   ├── dto/leetcode/calculator/SkillCalculator.java
│       │   │   ├── dto/leetcode/graphql/badges/Badge.java
│       │   │   ├── dto/leetcode/graphql/badges/BadgesData.java
│       │   │   ├── dto/leetcode/graphql/badges/BadgesResponse.java
│       │   │   ├── dto/leetcode/graphql/badges/MatchedUser.java
│       │   │   ├── dto/leetcode/graphql/calendar/CalendarData.java
│       │   │   ├── dto/leetcode/graphql/calendar/CalendarMatchedUser.java
│       │   │   ├── dto/leetcode/graphql/calendar/CalendarResponse.java
│       │   │   ├── dto/leetcode/graphql/calendar/UserCalendar.java
│       │   │   ├── dto/leetcode/graphql/contest/Contest.java
│       │   │   ├── dto/leetcode/graphql/contest/ContestBadge.java
│       │   │   ├── dto/leetcode/graphql/contest/ContestData.java
│       │   │   ├── dto/leetcode/graphql/contest/ContestHistory.java
│       │   │   ├── dto/leetcode/graphql/contest/ContestRanking.java
│       │   │   ├── dto/leetcode/graphql/contest/ContestResponse.java
│       │   │   ├── dto/leetcode/graphql/problems/ProblemProgressData.java
│       │   │   ├── dto/leetcode/graphql/problems/ProblemProgressMatchedUser.java
│       │   │   ├── dto/leetcode/graphql/problems/ProblemProgressResponse.java
│       │   │   ├── dto/leetcode/graphql/problems/QuestionCount.java
│       │   │   ├── dto/leetcode/graphql/problems/SubmissionStat.java
│       │   │   ├── dto/leetcode/graphql/problems/SubmitStats.java
│       │   │   ├── dto/leetcode/graphql/recent/RecentSubmissionData.java
│       │   │   ├── dto/leetcode/graphql/recent/RecentSubmissionItem.java
│       │   │   ├── dto/leetcode/graphql/recent/RecentSubmissionResponse.java
│       │   │   ├── dto/leetcode/graphql/skills/SkillCategory.java
│       │   │   ├── dto/leetcode/graphql/skills/SkillStatsData.java
│       │   │   ├── dto/leetcode/graphql/skills/SkillStatsMatchedUser.java
│       │   │   ├── dto/leetcode/graphql/skills/SkillStatsResponse.java
│       │   │   ├── dto/leetcode/graphql/skills/TagProblemCounts.java
│       │   │   ├── dto/leetcode/response/AnalyticsInfo.java
│       │   │   ├── dto/leetcode/response/BadgeInfo.java
│       │   │   ├── dto/leetcode/response/CalendarInfo.java
│       │   │   ├── dto/leetcode/response/ContestInfo.java
│       │   │   ├── dto/leetcode/response/LeetCodeDashboardResponse.java
│       │   │   ├── dto/leetcode/response/ProblemStats.java
│       │   │   ├── dto/leetcode/response/RecentSubmission.java
│       │   │   ├── dto/leetcode/response/SkillInfo.java
│       │   │   ├── dto/leetcode/response/SkillStats.java
│       │   │   ├── dto/portfolio/AiDeveloperSummaryResponse.java
│       │   │   ├── dto/portfolio/AiSkillsSummaryResponse.java
│       │   │   ├── dto/portfolio/PortfolioScoreResponse.java
│       │   │   ├── dto/request/DeleteAccountRequest.java
│       │   │   ├── dto/request/UpdateNotificationPreferencesRequest.java
│       │   │   ├── dto/request/UpdateThemeRequest.java
│       │   │   ├── dto/response/NotificationPreferencesResponse.java
│       │   │   ├── dto/response/NotificationPreferencesResponse.java
│       │   │   ├── dto/response/NotificationPreferencesResponse.java
│       │   │   ├── dto/response/NotificationPreferencesResponse.java
│       │   │   ├── entity/AiAnalysis.java
│       │   │   ├── entity/AiChatMessage.java
│       │   │   ├── entity/AiMistake.java
│       │   │   ├── entity/AiProgressiveHint.java
│       │   │   ├── entity/AuthProvider.java
│       │   │   ├── entity/Difficulty.java
│       │   │   ├── entity/FeaturedProject.java
│       │   │   ├── entity/IndependentSolveSession.java
│       │   │   ├── entity/InterviewQuestion.java
│       │   │   ├── entity/InterviewSession.java
│       │   │   ├── entity/Language.java
│       │   │   ├── entity/MistakeSeverity.java
│       │   │   ├── entity/MistakeType.java
│       │   │   ├── entity/Problem.java
│       │   │   ├── entity/ProblemExample.java
│       │   │   ├── entity/Role.java
│       │   │   ├── entity/Submission.java
│       │   │   ├── entity/SubmissionStatus.java
│       │   │   ├── entity/TestCase.java
│       │   │   ├── entity/ThemePreference.java
│       │   │   ├── entity/Topic.java
│       │   │   ├── entity/User.java
│       │   │   ├── exception/BadRequestException.java
│       │   │   ├── exception/GlobalExceptionHandler.java
│       │   │   ├── exception/ResourceAlreadyExistsException.java
│       │   │   ├── exception/ResourceNotFoundException.java
│       │   │   ├── execution/CodeExecutionService.java
│       │   │   ├── execution/DockerCodeExecutionService.java
│       │   │   ├── export/controller/ExportController.java
│       │   │   ├── export/dto/ConnectedAccountsExport.java
│       │   │   ├── export/dto/ExportDataResponse.java
│       │   │   ├── export/dto/ExportMetadata.java
│       │   │   ├── export/dto/GithubExport.java
│       │   │   ├── export/dto/LeetCodeExport.java
│       │   │   ├── export/dto/NotificationExport.java
│       │   │   ├── export/dto/ProfileExport.java
│       │   │   ├── export/dto/SecurityExport.java
│       │   │   ├── export/pdf/PdfExportService.java
│       │   │   ├── export/pdf/PdfExportServiceImpl.java
│       │   │   ├── export/pdf/PdfPageEvent.java
│       │   │   ├── export/service/DeveloperReportService.java
│       │   │   ├── export/service/DeveloperReportServiceImpl.java
│       │   │   ├── export/service/ExportService.java
│       │   │   ├── export/service/impl/ExportServiceImpl.java
│       │   │   ├── mapper/GitHubDashboardMapper.java
│       │   │   ├── mapper/LeetCodeMapper.java
│       │   │   ├── notification/builder/NotificationBuilder.java
│       │   │   ├── notification/controller/NotificationController.java
│       │   │   ├── notification/dto/CreateNotificationRequest.java
│       │   │   ├── notification/dto/NotificationResponse.java
│       │   │   ├── notification/dto/NotificationSummaryResponse.java
│       │   │   ├── notification/entity/Notification.java
│       │   │   ├── notification/enums/NotificationType.java
│       │   │   ├── notification/mapper/NotificationMapper.java
│       │   │   ├── notification/repository/NotificationRepository.java
│       │   │   ├── notification/service/NotificationService.java
│       │   │   ├── notification/service/impl/NotificationServiceImpl.java
│       │   │   ├── publicportfolio/dto/PublicPortfolioResponse.java
│       │   │   ├── publicportfolio/service/PublicPortfolioService.java
│       │   │   ├── repository/AiAnalysisRepository.java
│       │   │   ├── repository/AiChatMessageRepository.java
│       │   │   ├── repository/AiMistakeRepository.java
│       │   │   ├── repository/AiProgressiveHintRepository.java
│       │   │   ├── repository/FeaturedProjectRepository.java
│       │   │   ├── repository/IndependentSolveSessionRepository.java
│       │   │   ├── repository/InterviewQuestionRepository.java
│       │   │   ├── repository/InterviewSessionRepository.java
│       │   │   ├── repository/ProblemExampleRepository.java
│       │   │   ├── repository/ProblemRepository.java
│       │   │   ├── repository/SubmissionRepository.java
│       │   │   ├── repository/TestCaseRepository.java
│       │   │   ├── repository/TopicRepository.java
│       │   │   ├── repository/UserRepository.java
│       │   │   ├── security/CustomOAuth2UserService.java
│       │   │   ├── security/CustomUserDetailsService.java
│       │   │   ├── security/JwtAuthenticationEntryPoint.java
│       │   │   ├── security/JwtAuthenticationFilter.java
│       │   │   ├── security/JwtService.java
│       │   │   ├── security/OAuth2AuthenticationSuccessHandler.java
│       │   │   ├── security/SecurityConfig.java
│       │   │   ├── security/SecurityUtils.java
│       │   │   ├── seed/ProblemExampleSeedData.java
│       │   │   ├── seed/ProblemSeedData.java
│       │   │   ├── seed/TestCaseSeedData.java
│       │   │   ├── seed/problems/ArrayProblemSeeds.java
│       │   │   ├── seed/problems/BacktrackingProblemSeeds.java
│       │   │   ├── seed/problems/BinarySearchProblemSeeds.java
│       │   │   ├── seed/problems/BinarySearchTreeProblemSeeds.java
│       │   │   ├── seed/problems/BitManipulationProblemSeeds.java
│       │   │   ├── seed/problems/BreadthFirstSearchProblemSeeds.java
│       │   │   ├── seed/problems/DepthFirstSearchProblemSeeds.java
│       │   │   ├── seed/problems/DisjointSetProblemSeeds.java
│       │   │   ├── seed/problems/DynamicProgrammingProblemSeeds.java
│       │   │   ├── seed/problems/GraphProblemSeeds.java
│       │   │   ├── seed/problems/GreedyProblemSeeds.java
│       │   │   ├── seed/problems/HashingProblemSeeds.java
│       │   │   ├── seed/problems/HeapProblemSeeds.java
│       │   │   ├── seed/problems/LinkedListProblemSeeds.java
│       │   │   ├── seed/problems/MathProblemSeeds.java
│       │   │   ├── seed/problems/MatrixProblemSeeds.java
│       │   │   ├── seed/problems/ProblemSeedRegistry.java
│       │   │   ├── seed/problems/QueueProblemSeeds.java
│       │   │   ├── seed/problems/RecursionProblemSeeds.java
│       │   │   ├── seed/problems/SlidingWindowProblemSeeds.java
│       │   │   ├── seed/problems/SortingProblemSeeds.java
│       │   │   ├── seed/problems/StackProblemSeeds.java
│       │   │   ├── seed/problems/StringProblemSeeds.java
│       │   │   ├── seed/problems/TreeProblemSeeds.java
│       │   │   ├── seed/problems/TrieProblemSeeds.java
│       │   │   ├── seed/problems/TwoPointersProblemSeeds.java
│       │   │   ├── service/AdaptiveMentorService.java
│       │   │   ├── service/AdminAiAnalyticsService.java
│       │   │   ├── service/AdminDashboardService.java
│       │   │   ├── service/AdminUserService.java
│       │   │   ├── service/AiMentorService.java
│       │   │   ├── service/AuthenticationService.java
│       │   │   ├── service/DeveloperActivityService.java
│       │   │   ├── service/EmailService.java
│       │   │   ├── service/FeaturedProjectService.java
│       │   │   ├── service/GitHubAnalyticsService.java
│       │   │   ├── service/GitHubDashboardService.java
│       │   │   ├── service/GitHubRepositoryRankingService.java
│       │   │   ├── service/GitHubService.java
│       │   │   ├── service/IndependentSolveSessionService.java
│       │   │   ├── service/LeetCodeService.java
│       │   │   ├── service/LearningAnalyticsService.java
│       │   │   ├── service/PersonalizedInterviewService.java
│       │   │   ├── service/PortfolioAiService.java
│       │   │   ├── service/ProblemService.java
│       │   │   ├── service/RecommendationEngineService.java
│       │   │   ├── service/SubmissionService.java
│       │   │   ├── service/TestCaseService.java
│       │   │   ├── service/TopicService.java
│       │   │   ├── service/UserService.java
│       │   │   ├── service/impl/AdaptiveMentorServiceImpl.java
│       │   │   ├── service/impl/AdminAiAnalyticsServiceImpl.java
│       │   │   ├── service/impl/AdminDashboardServiceImpl.java
│       │   │   ├── service/impl/AdminUserServiceImpl.java
│       │   │   ├── service/impl/AiMentorServiceImpl.java
│       │   │   ├── service/impl/AuthenticationServiceImpl.java
│       │   │   ├── service/impl/DeveloperActivityServiceImpl.java
│       │   │   ├── service/impl/EmailServiceImpl.java
│       │   │   ├── service/impl/FeaturedProjectServiceImpl.java
│       │   │   ├── service/impl/GitHubAnalyticsServiceImpl.java
│       │   │   ├── service/impl/GitHubDashboardServiceImpl.java
│       │   │   ├── service/impl/GitHubRepositoryRankingServiceImpl.java
│       │   │   ├── service/impl/GitHubServiceImpl.java
│       │   │   ├── service/impl/IndependentSolveSessionServiceImpl.java
│       │   │   ├── service/impl/LeetCodeServiceImpl.java
│       │   │   ├── service/impl/LearningAnalyticsServiceImpl.java
│       │   │   ├── service/impl/PortfolioAiServiceImpl.java
│       │   │   ├── service/impl/ProblemServiceImpl.java
│       │   │   ├── service/impl/RecommendationEngineServiceImpl.java
│       │   │   ├── service/impl/SubmissionServiceImpl.java
│       │   │   ├── service/impl/TestCaseServiceImpl.java
│       │   │   ├── service/impl/TopicServiceImpl.java
│       │   │   ├── service/impl/UserServiceImpl.java
│       │   │   ├── session/controller/UserSessionController.java
│       │   │   ├── session/dto/SessionResponse.java
│       │   │   ├── session/entity/UserSession.java
│       │   │   ├── session/mapper/UserSessionMapper.java
│       │   │   ├── session/repository/UserSessionRepository.java
│       │   │   ├── session/service/UserSessionService.java
│       │   │   ├── session/service/impl/UserSessionServiceImpl.java
│       │   │   ├── util/ConceptNormalizer.java
│       │   │   └── publicportfolio/service/PublicPortfolioService.java
│       │   └── resources/
│       │       ├── application.properties
│       │       ├── static/images/brain-logo.png
│       │       └── static/images/codementor-logo.png
│       └── test/java/com/codementor/backend/BackendApplicationTests.java
├── database/
│   ├── database-design.md
│   ├── diagrams/ERD.md
│   ├── migrations/
│   ├── schema/
│   └── seeds/
├── docker/
├── docs/
│   ├── API_DOCUMENTATION.md
│   ├── BACKEND.md
│   ├── CHANGELOG.md
│   ├── DATABASE.md
│   ├── DEPLOYMENT.md
│   ├── FRONTEND.md
│   ├── PROJECT_MASTER_CONTEXT.md
│   └── ROADMAP.md
├── frontend/
│   ├── package.json
│   ├── package-lock.json
│   ├── vite.config.ts
│   ├── index.html
│   ├── eslint.config.js
│   ├── tsconfig.json
│   ├── tsconfig.app.json
│   ├── tsconfig.node.json
│   ├── README.md
│   ├── public/
│   └── src/
│       ├── App.tsx
│       ├── index.css
│       ├── main.tsx
│       ├── assets/hero.png
│       ├── assets/react.svg
│       ├── assets/vite.svg
│       ├── components/account/AppearanceCard.tsx
│       ├── components/account/ChangePasswordDialog.tsx
│       ├── components/account/ConnectedAccountsCard.tsx
│       ├── components/account/DangerZoneCard.tsx
│       ├── components/account/DeveloperInformationCard.tsx
│       ├── components/account/DeveloperToolsCard.tsx
│       ├── components/account/EditProfileModal.tsx
│       ├── components/account/NotificationCard.tsx
│       ├── components/account/PersonalInformationCard.tsx
│       ├── components/account/ProfileHero.tsx
│       ├── components/account/SecurityCard.tsx
│       ├── components/account/SettingsNavigation.tsx
│       ├── components/account/StatisticsSection.tsx
│       ├── components/account/developer-identity/DeveloperIdentityCard.tsx
│       ├── components/account/developer-identity/PlatformCard.tsx
│       ├── components/account/developer-identity/PlatformDialog.tsx
│       ├── components/account/developer-identity/github-sections/FeaturedProjectsSection.tsx
│       ├── components/account/developer-identity/platform-content/GitHubContent.tsx
│       ├── components/account/developer-identity/platform-content/LeetCodeContent.tsx
│       ├── components/activity/DeveloperActivityCalendar.tsx
│       ├── components/admin/common/AdminConfirmModal.tsx
│       ├── components/admin/dashboard/AdminDifficultyChart.tsx
│       ├── components/admin/dashboard/AdminStatusDistribution.tsx
│       ├── components/admin/dashboard/AdminSubmissionActivityChart.tsx
│       ├── components/admin/layout/AdminLayout.tsx
│       ├── components/admin/layout/AdminSidebar.tsx
│       ├── components/admin/layout/AdminTopbar.tsx
│       ├── components/admin/submissions/AdminSubmissionDetailsDrawer.tsx
│       ├── components/ai/PersonalizedLearningPlanPage.tsx
│       ├── components/ai/SolutionEvolutionTimeline.tsx
│       ├── components/common/CardSkeleton.tsx
│       ├── components/common/UserAvatar.tsx
│       ├── components/common/UserHero.tsx
│       ├── components/independent/IndependentSolveHistory.tsx
│       ├── components/layout/AppLayout.tsx
│       ├── components/layout/UserTopbar.tsx
│       ├── components/notifications/NotificationBell.tsx
│       ├── components/notifications/NotificationDrawer.tsx
│       ├── components/notifications/NotificationEmptyState.tsx
│       ├── components/notifications/NotificationItem.tsx
│       ├── components/portfolio/AchievementsCard.tsx
│       ├── components/portfolio/AIDeveloperSummaryCard.tsx
│       ├── components/portfolio/AISkillsSummaryCard.tsx
│       ├── components/portfolio/AISummaryCard.tsx
│       ├── components/portfolio/CodingProfilesCard.tsx
│       ├── components/portfolio/GitHubAnalyticsCard.tsx
│       ├── components/portfolio/LeetCodeAchievementsModal.tsx
│       ├── components/portfolio/LeetCodePerformanceCard.tsx
│       ├── components/portfolio/PortfolioHero.tsx
│       ├── components/portfolio/PortfolioScoreCard.tsx
│       ├── components/portfolio/PortfolioStats.tsx
│       ├── components/portfolio/ProjectsCard.tsx
│       ├── components/portfolio/PublicPortfolioPage.tsx
│       ├── components/portfolio/RecentActivityCard.tsx
│       ├── components/portfolio/SkillsCard.tsx
│       ├── components/portfolio/TopRepositoriesCard.tsx
│       ├── components/portfolio/ViewAllRepositoriesModal.tsx
│       ├── components/problems/AdminProblemForm.tsx
│       ├── components/profile/AccountStatusCard.tsx
│       ├── components/profile/ConnectedAccountsSection.tsx
│       ├── components/profile/PersonalInfoCard.tsx
│       ├── components/profile/ProfileHeader.tsx
│       ├── components/profile/ProfileHero.tsx
│       ├── components/security/EmptySessions.tsx
│       ├── components/security/LogoutAllDialog.tsx
│       ├── components/security/SecurityHeader.tsx
│       ├── components/security/SecuritySummary.tsx
│       ├── components/security/SessionCard.tsx
│       ├── components/security/SessionSkeleton.tsx
│       ├── components/skills/HintDependencyCard.tsx
│       ├── components/topic/TopicHeader.tsx
│       ├── components/topic/TopicProblemList.tsx
│       ├── components/topic/TopicProblemRow.tsx
│       ├── components/topic/TopicSearch.tsx
│       ├── components/topic/TopicSidebar.tsx
│       ├── components/ui/EmptyState.tsx
│       ├── components/ui/ErrorState.tsx
│       ├── components/ui/PageLoader.tsx
│       ├── components/ui/SkeletonCard.tsx
│       ├── components/ui/SkeletonChart.tsx
│       ├── components/ui/SkeletonMetric.tsx
│       ├── components/workspace/CodeEditorPanel.tsx
│       ├── components/workspace/ProblemDescriptionPanel.tsx
│       ├── components/workspace/RunResultPanel.tsx
│       ├── components/workspace/SubmissionHistoryPanel.tsx
│       ├── components/workspace/SubmissionResultPanel.tsx
│       ├── context/AuthContext.tsx
│       ├── context/AuthContextDefinition.ts
│       ├── context/NotificationContext.tsx
│       ├── context/ThemeContext.tsx
│       ├── context/ThemeContextDefinition.ts
│       ├── hooks/useAuth.ts
│       ├── hooks/useNotifications.ts
│       ├── hooks/useTheme.ts
│       ├── pages/DeveloperSkillGraphPage.tsx
│       ├── pages/account/ProfilePage.tsx
│       ├── pages/account/SettingsPage.tsx
│       ├── pages/admin/AdminAiAnalyticsPage.tsx
│       ├── pages/admin/AdminCreateProblemPage.tsx
│       ├── pages/admin/AdminDashboardPage.tsx
│       ├── pages/admin/AdminEditProblemPage.tsx
│       ├── pages/admin/AdminPlatformAnalyticsPage.tsx
│       ├── pages/admin/AdminProblemsPage.tsx
│       ├── pages/admin/AdminSubmissionsPage.tsx
│       ├── pages/admin/AdminTopicsPage.tsx
│       ├── pages/admin/AdminUserDetailsPage.tsx
│       ├── pages/admin/AdminUsersPage.tsx
│       ├── pages/auth/ForgotPasswordPage.tsx
│       ├── pages/auth/LoginPage.tsx
│       ├── pages/auth/OAuthSuccess.tsx
│       ├── pages/auth/RegisterPage.tsx
│       ├── pages/auth/ResetPasswordPage.tsx
│       ├── pages/portfolio/PortfolioPage.tsx
│       ├── pages/settings/DeleteAccountModal.tsx
│       ├── pages/settings/ResetAiMemoryModal.tsx
│       ├── pages/settings/SessionsPage.tsx
│       ├── pages/user/GrowthReportPage.tsx
│       ├── pages/user/MistakeMemoryPage.tsx
│       ├── pages/user/PersonalizedInterviewPage.tsx
│       ├── pages/user/PersonalizedRevisionPlanPage.tsx
│       ├── pages/user/ProblemSolvePage.tsx
│       ├── pages/user/ProblemsPage.tsx
│       ├── pages/user/TopicProblemsPage.tsx
│       ├── pages/user/TopicsPage.tsx
│       ├── pages/user/UserDashboardPage.tsx
│       ├── routes/AdminRoute.tsx
│       ├── routes/ProtectedRoute.tsx
│       ├── routes/RoleBasedRedirect.tsx
│       ├── routes/UserRoute.tsx
│       ├── services/adminAiAnalyticsService.ts
│       ├── services/adminPlatformAnalyticsService.ts
│       ├── services/adminService.ts
│       ├── services/aiMentorService.ts
│       ├── services/api.ts
│       ├── services/authService.ts
│       ├── services/connectedAccountsService.ts
│       ├── services/developerActivityService.ts
│       ├── services/developerSkillService.ts
│       ├── services/exportService.ts
│       ├── services/featuredProjectService.ts
│       ├── services/independentSolveService.ts
│       ├── services/leetcodeService.ts
│       ├── services/mistakeMemoryService.ts
│       ├── services/notificationCenterService.ts
│       ├── services/notificationService.ts
│       ├── services/personalizedInterviewService.ts
│       ├── services/portfolioAiService.ts
│       ├── services/portfolioScoreService.ts
│       ├── services/portfolioService.ts
│       ├── services/problemService.ts
│       ├── services/publicPortfolioService.ts
│       ├── services/sessionService.ts
│       ├── services/submissionService.ts
│       ├── services/themeService.ts
│       ├── services/topicService.ts
│       ├── services/userService.ts
│       ├── styles/
│       ├── types/admin.ts
│       ├── types/adminAiAnalytics.ts
│       ├── types/adminPlatformAnalytics.ts
│       ├── types/aiMentor.ts
│       ├── types/auth.ts
│       ├── types/developerActivity.ts
│       ├── types/developerSkill.ts
│       ├── types/execution.ts
│       ├── types/featuredProject.ts
│       ├── types/github.ts
│       ├── types/hintDependency.ts
│       ├── types/leetcode.ts
│       ├── types/mistakeMemory.ts
│       ├── types/portfolio.ts
│       ├── types/portfolioAi.ts
│       ├── types/portfolioScore.ts
│       ├── types/portfolioSkills.ts
│       ├── types/problem.ts
│       ├── types/publicPortfolio.ts
│       ├── types/settings.ts
│       ├── types/submission.ts
│       ├── types/theme.ts
│       ├── types/topic.ts
│       ├── types/userProfile.ts
│       ├── types/solutionEvolution.ts
│       └── utils/
└── scripts/
```

This is now a much more detailed source map for the backend Java files and frontend TypeScript/TSX files.
