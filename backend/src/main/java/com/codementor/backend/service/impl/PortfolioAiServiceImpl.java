package com.codementor.backend.service.impl;

import com.codementor.backend.ai.GeminiService;
import com.codementor.backend.dto.github.GitHubDashboardResponse;
import com.codementor.backend.dto.portfolio.AiDeveloperSummaryResponse;
import com.codementor.backend.service.AiMentorService;
import com.codementor.backend.service.GitHubAnalyticsService;
import com.codementor.backend.service.GitHubDashboardService;
import com.codementor.backend.service.LeetCodeService;
import com.codementor.backend.service.PortfolioAiService;
import com.codementor.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.codementor.backend.dto.portfolio.AiSkillsSummaryResponse;
import com.codementor.backend.dto.portfolio.PortfolioScoreResponse;
import com.codementor.backend.dto.DeveloperSkillResponse;
import com.codementor.backend.dto.GrowthReportResponse;
import com.codementor.backend.dto.LeetCodeProfileResponse;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortfolioAiServiceImpl
        implements PortfolioAiService {

    private final GitHubDashboardService gitHubDashboardService;
    private final GitHubAnalyticsService gitHubAnalyticsService;
    private final LeetCodeService leetCodeService;
    private final AiMentorService aiMentorService;
    private final UserService userService;
    private final GeminiService geminiService;
    private final ObjectMapper objectMapper;

    @Override
    public AiDeveloperSummaryResponse generateDeveloperSummary(
            String email
    ) {

        var userProfile =
                userService.getCurrentUser(email);

    GitHubDashboardResponse githubDashboard;

    try {
System.out.println("Loading GitHub...");
        githubDashboard =
                gitHubDashboardService.getDashboard(
                        userProfile.getGithubUsername()
                );

System.out.println("GitHub Loaded");

    } catch (Exception ex) {

        ex.printStackTrace();

        throw ex;
    }

        // TODO
        // var githubAnalytics =
        //         gitHubAnalyticsService.calculate(
        //                 githubDashboard.getRepositories()
        //         );
System.out.println("Loading LeetCode...");
        var leetcodeProfile =
                leetCodeService.getProfile(
                        userProfile.getLeetcodeUsername()
                );
System.out.println("LeetCode Loaded");

System.out.println("Loading Growth Report...");
        var growthReport =
                aiMentorService.getMyGrowthReport(email);
System.out.println("Growth Report Loaded");

System.out.println("Loading Skills...");
        var developerSkills =
                aiMentorService.getMyDeveloperSkillGraph(email);
System.out.println("Loading Skills...");


        String prompt = """
        You are a senior engineering recruiter and software architect.

        Generate a professional developer summary.

        Developer Information

        Name:
        %s %s

        GitHub

        Repositories:
        %s

        LeetCode

        %s

        Growth Report

        %s

        Developer Skills

        %s

        Requirements:

        Write exactly 2 professional paragraphs.

        Mention backend, frontend, problem solving and engineering strengths.

        Mention measurable achievements whenever possible.

        Do not exaggerate.

        Return ONLY valid JSON.

        {
        "summary": "Two professional paragraphs",
        "strengths": [
            "Strength 1",
            "Strength 2",
            "Strength 3"
        ],
        "growthAreas": [
            "Growth Area 1",
            "Growth Area 2",
            "Growth Area 3"
        ],
        "recruiterMatch": 84,
        "recommendedRole": "Full Stack Java Developer"
        }

        Rules:

        - strengths must contain exactly 3 items.
        - growthAreas must contain exactly 3 items.
        - recruiterMatch must be an integer between 0 and 100.
        - recommendedRole must be one role only.
        - Output ONLY JSON.
        """.formatted(
                userProfile.getFirstName(),
                userProfile.getLastName(),
                githubDashboard,
                leetcodeProfile,
                growthReport,
                developerSkills
        );
    
        System.out.println("Calling Gemini...");

        String response =
                geminiService.chat(prompt);

        System.out.println("Gemini Finished");

        response = response
                .replace("```json", "")
                .replace("```", "")
                .trim();


        try {

            return objectMapper.readValue(
                    response,
                    AiDeveloperSummaryResponse.class
            );

        } catch (JsonProcessingException e) {

        e.printStackTrace();

        throw new RuntimeException(
                "Failed to parse AI response",
                e
        );

        }
    }

        @Override
        public AiSkillsSummaryResponse generateSkillsSummary(
                String email
        ) {

        var userProfile =
                userService.getCurrentUser(email);

        var githubDashboard =
                gitHubDashboardService.getDashboard(
                        userProfile.getGithubUsername()
                );

        var developerSkills =
                aiMentorService.getMyDeveloperSkillGraph(email);

        var growthReport =
                aiMentorService.getMyGrowthReport(email);

        return AiSkillsSummaryResponse.builder()

                .categories(List.of(

                        AiSkillsSummaryResponse.SkillCategory.builder()
                                .category("Backend")
                                .score(92)
                                .skills(List.of(
                                        "Java",
                                        "Spring Boot",
                                        "REST APIs",
                                        "SQL"
                                ))
                                .build(),

                        AiSkillsSummaryResponse.SkillCategory.builder()
                                .category("Frontend")
                                .score(86)
                                .skills(List.of(
                                        "React",
                                        "TypeScript",
                                        "Tailwind CSS"
                                ))
                                .build(),

                        AiSkillsSummaryResponse.SkillCategory.builder()
                                .category("Problem Solving")
                                .score(90)
                                .skills(List.of(
                                        "Data Structures",
                                        "Algorithms",
                                        "LeetCode"
                                ))
                                .build()

                ))

                .developerLevel("Intermediate Developer")

                .build();

        }

        @Override
        public PortfolioScoreResponse generatePortfolioScore(
                String email
        ) {

        var userProfile =
                userService.getCurrentUser(email);

        var githubDashboard =
                gitHubDashboardService.getDashboard(
                        userProfile.getGithubUsername()
                );

        var leetcodeProfile =
                leetCodeService.getProfile(
                        userProfile.getLeetcodeUsername()
                );

        var growthReport =
                aiMentorService.getMyGrowthReport(email);

        int githubScore =
                (int) Math.round(
                        githubDashboard.getStatistics()
                                .getDeveloperScore()
                );

        int leetcodeScore =
                (int) Math.round(
                        leetcodeProfile.getContest()
                                .getRating() / 20
                );

        leetcodeScore =
                Math.min(100, leetcodeScore);

        int productionReadiness =
                Math.min(
                        100,
                        githubScore + 20
                );

        int openSourceScore =
                githubDashboard.getStatistics()
                        .getRepositories() >= 10
                        ? 80
                        : 60;

        int resumeReadiness =
                Math.min(
                        100,
                        growthReport.getOverallGrowthScore() + 60
                );

        int overallScore =
                (
                        githubScore +
                        leetcodeScore +
                        productionReadiness +
                        openSourceScore +
                        resumeReadiness
                ) / 5;

        return PortfolioScoreResponse.builder()
                .overallScore(overallScore)
                .githubScore(githubScore)
                .leetcodeScore(leetcodeScore)
                .productionReadiness(productionReadiness)
                .openSourceScore(openSourceScore)
                .resumeReadiness(resumeReadiness)
                .build();

        }

        @Override
        public AiDeveloperSummaryResponse generateDeveloperSummary(
                String email,
                GitHubDashboardResponse githubDashboard,
                LeetCodeProfileResponse leetcodeProfile,
                GrowthReportResponse growthReport,
                List<DeveloperSkillResponse> developerSkills
        ) {

        var userProfile =
                userService.getCurrentUser(email);

        String prompt = """
        You are a senior engineering recruiter and software architect.

        Generate a professional developer summary.

        Developer Information

        Name:
        %s %s

        GitHub

        Repositories:
        %s

        LeetCode

        %s

        Growth Report

        %s

        Developer Skills

        %s

        Requirements:

        Write exactly 2 professional paragraphs.

        Mention backend, frontend, problem solving and engineering strengths.

        Mention measurable achievements whenever possible.

        Do not exaggerate.

        Return ONLY valid JSON.

        {
        "summary": "Two professional paragraphs",
        "strengths": [
                "Strength 1",
                "Strength 2",
                "Strength 3"
        ],
        "growthAreas": [
                "Growth Area 1",
                "Growth Area 2",
                "Growth Area 3"
        ],
        "recruiterMatch": 84,
        "recommendedRole": "Full Stack Java Developer"
        }

        Rules:

        - strengths must contain exactly 3 items.
        - growthAreas must contain exactly 3 items.
        - recruiterMatch must be an integer between 0 and 100.
        - recommendedRole must be one role only.
        - Output ONLY JSON.
        """.formatted(
                userProfile.getFirstName(),
                userProfile.getLastName(),
                githubDashboard,
                leetcodeProfile,
                growthReport,
                developerSkills
        );

        System.out.println(
                "Calling Gemini with existing portfolio data..."
        );

        String response =
                geminiService.chat(prompt);

        System.out.println(
                "Gemini Finished"
        );

        response = response
                .replace("```json", "")
                .replace("```", "")
                .trim();

        try {

                return objectMapper.readValue(
                        response,
                        AiDeveloperSummaryResponse.class
                );

        } catch (JsonProcessingException e) {

                e.printStackTrace();

                throw new RuntimeException(
                        "Failed to parse AI response",
                        e
                );
        }
        }

        @Override
        public AiSkillsSummaryResponse generateSkillsSummary(
                String email,
                GitHubDashboardResponse githubDashboard,
                List<DeveloperSkillResponse> developerSkills,
                GrowthReportResponse growthReport
        ) {

        return AiSkillsSummaryResponse.builder()

                .categories(List.of(

                        AiSkillsSummaryResponse.SkillCategory.builder()
                                .category("Backend")
                                .score(92)
                                .skills(List.of(
                                        "Java",
                                        "Spring Boot",
                                        "REST APIs",
                                        "SQL"
                                ))
                                .build(),

                        AiSkillsSummaryResponse.SkillCategory.builder()
                                .category("Frontend")
                                .score(86)
                                .skills(List.of(
                                        "React",
                                        "TypeScript",
                                        "Tailwind CSS"
                                ))
                                .build(),

                        AiSkillsSummaryResponse.SkillCategory.builder()
                                .category("Problem Solving")
                                .score(90)
                                .skills(List.of(
                                        "Data Structures",
                                        "Algorithms",
                                        "LeetCode"
                                ))
                                .build()

                ))

                .developerLevel("Intermediate Developer")
                .build();
        }

        @Override
        public PortfolioScoreResponse generatePortfolioScore(
                String email,
                GitHubDashboardResponse githubDashboard,
                LeetCodeProfileResponse leetcodeProfile,
                GrowthReportResponse growthReport
        ) {

        int githubScore =
                (int) Math.round(
                        githubDashboard.getStatistics()
                                .getDeveloperScore()
                );

        int leetcodeScore =
                (int) Math.round(
                        leetcodeProfile.getContest()
                                .getRating() / 20
                );

        leetcodeScore =
                Math.min(100, leetcodeScore);

        int productionReadiness =
                Math.min(
                        100,
                        githubScore + 20
                );

        int openSourceScore =
                githubDashboard.getStatistics()
                        .getRepositories() >= 10
                        ? 80
                        : 60;

        int resumeReadiness =
                Math.min(
                        100,
                        growthReport.getOverallGrowthScore() + 60
                );

        int overallScore =
                (
                        githubScore +
                        leetcodeScore +
                        productionReadiness +
                        openSourceScore +
                        resumeReadiness
                ) / 5;

        return PortfolioScoreResponse.builder()
                .overallScore(overallScore)
                .githubScore(githubScore)
                .leetcodeScore(leetcodeScore)
                .productionReadiness(productionReadiness)
                .openSourceScore(openSourceScore)
                .resumeReadiness(resumeReadiness)
                .build();
        }

}