package com.codementor.backend.service.impl;

import com.codementor.backend.dto.GrowthReportResponse;
import com.codementor.backend.dto.LeetCodeProfileResponse;
import com.codementor.backend.dto.github.GitHubDashboardResponse;
import com.codementor.backend.dto.portfolio.AiDeveloperSummaryResponse;
import com.codementor.backend.dto.portfolio.AiSkillsSummaryResponse;
import com.codementor.backend.dto.portfolio.PortfolioScoreResponse;
import com.codementor.backend.dto.DeveloperSkillResponse;
import com.codementor.backend.entity.PortfolioSnapshot;
import com.codementor.backend.entity.User;
import com.codementor.backend.repository.PortfolioSnapshotRepository;
import com.codementor.backend.repository.UserRepository;
import com.codementor.backend.service.AiMentorService;
import com.codementor.backend.service.GitHubDashboardService;
import com.codementor.backend.service.LeetCodeService;
import com.codementor.backend.service.PortfolioAiService;
import com.codementor.backend.service.PortfolioSnapshotService;
import com.codementor.backend.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PortfolioSnapshotServiceImpl
        implements PortfolioSnapshotService {

    private final PortfolioSnapshotRepository portfolioSnapshotRepository;
    private final UserRepository userRepository;

    private final UserService userService;
    private final GitHubDashboardService gitHubDashboardService;
    private final LeetCodeService leetCodeService;
    private final PortfolioAiService portfolioAiService;
    private final AiMentorService aiMentorService;

    private final ObjectMapper objectMapper;

    // ==================================================
    // GITHUB
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public GitHubDashboardResponse getGitHubData(String email) {

        PortfolioSnapshot snapshot =
                getSnapshot(email);

        if (snapshot.getGithubData() == null) {
            return null;
        }

        return convert(
                snapshot.getGithubData(),
                GitHubDashboardResponse.class
        );
    }

    // ==================================================
    // LEETCODE
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public LeetCodeProfileResponse getLeetCodeData(
            String email
    ) {

        PortfolioSnapshot snapshot =
                getSnapshot(email);

        if (snapshot.getLeetcodeData() == null) {
            return null;
        }

        return convert(
                snapshot.getLeetcodeData(),
                LeetCodeProfileResponse.class
        );
    }

    // ==================================================
    // GROWTH REPORT
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public GrowthReportResponse getGrowthReport(
            String email
    ) {

        PortfolioSnapshot snapshot =
                getSnapshot(email);

        if (snapshot.getGrowthReportData() == null) {
            return null;
        }

        return convert(
                snapshot.getGrowthReportData(),
                GrowthReportResponse.class
        );
    }

    // ==================================================
    // AI DEVELOPER SUMMARY
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public AiDeveloperSummaryResponse getDeveloperSummary(
            String email
    ) {

        PortfolioSnapshot snapshot =
                getSnapshot(email);

        if (snapshot.getDeveloperSummaryData() == null) {
            return null;
        }

        return convert(
                snapshot.getDeveloperSummaryData(),
                AiDeveloperSummaryResponse.class
        );
    }

    // ==================================================
    // AI SKILLS SUMMARY
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public AiSkillsSummaryResponse getSkillsSummary(
            String email
    ) {

        PortfolioSnapshot snapshot =
                getSnapshot(email);

        if (snapshot.getSkillsSummaryData() == null) {
            return null;
        }

        return convert(
                snapshot.getSkillsSummaryData(),
                AiSkillsSummaryResponse.class
        );
    }

    // ==================================================
    // PORTFOLIO SCORE
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public PortfolioScoreResponse getPortfolioScore(
            String email
    ) {

        PortfolioSnapshot snapshot =
                getSnapshot(email);

        if (snapshot.getPortfolioScoreData() == null) {
            return null;
        }

        return convert(
                snapshot.getPortfolioScoreData(),
                PortfolioScoreResponse.class
        );
    }

    // ==================================================
    // REFRESH COMPLETE SNAPSHOT
    // ==================================================

    @Override
    @Transactional
    public void refreshSnapshot(String email) {

        System.out.println(
                "========== PORTFOLIO SNAPSHOT REFRESH =========="
        );

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        PortfolioSnapshot snapshot =
                portfolioSnapshotRepository
                        .findByUserId(user.getId())
                        .orElseGet(() ->
                                PortfolioSnapshot.builder()
                                        .user(user)
                                        .build()
                        );

        var profile =
                userService.getCurrentUser(email);

        GitHubDashboardResponse github = null;
        LeetCodeProfileResponse leetcode = null;
        List<DeveloperSkillResponse> developerSkills =
                List.of();

        // ==================================================
        // GITHUB
        // ==================================================

        if (profile.getGithubUsername() != null
                && !profile.getGithubUsername().isBlank()) {

            System.out.println(
                    "Snapshot: Loading GitHub..."
            );

            github =
                    gitHubDashboardService.getDashboard(
                            profile.getGithubUsername()
                    );

            snapshot.setGithubData(
                    objectMapper.valueToTree(github)
            );

            snapshot.setGithubLastSyncedAt(
                    LocalDateTime.now()
            );

            System.out.println(
                    "Snapshot: GitHub saved."
            );
        }

        developerSkills =
            aiMentorService.getMyDeveloperSkillGraph(email);

        // ==================================================
        // LEETCODE
        // ==================================================

        if (profile.getLeetcodeUsername() != null
                && !profile.getLeetcodeUsername().isBlank()) {

            System.out.println(
                    "Snapshot: Loading LeetCode..."
            );

            leetcode =
                    leetCodeService.getProfile(
                            profile.getLeetcodeUsername()
                    );

            snapshot.setLeetcodeData(
                    objectMapper.valueToTree(leetcode)
            );

            snapshot.setLeetcodeLastSyncedAt(
                    LocalDateTime.now()
            );

            System.out.println(
                    "Snapshot: LeetCode saved."
            );
        }

        // ==================================================
        // GROWTH REPORT
        // ==================================================

        System.out.println(
                "Snapshot: Loading Growth Report..."
        );

        GrowthReportResponse growthReport =
                aiMentorService.getMyGrowthReport(email);

        snapshot.setGrowthReportData(
                objectMapper.valueToTree(growthReport)
        );

        snapshot.setGrowthReportUpdatedAt(
                LocalDateTime.now()
        );

        // ==================================================
        // AI DEVELOPER SUMMARY
        // ==================================================

        System.out.println(
                "Snapshot: Loading Developer Summary..."
        );

        try {

            AiDeveloperSummaryResponse developerSummary =
                    portfolioAiService.generateDeveloperSummary(
                            email,
                            github,
                            leetcode,
                            growthReport,
                            developerSkills
                    );

            snapshot.setDeveloperSummaryData(
                    objectMapper.valueToTree(
                            developerSummary
                    )
            );

            snapshot.setDeveloperSummaryUpdatedAt(
                    LocalDateTime.now()
            );

            System.out.println(
                    "Snapshot: Developer Summary saved."
            );

        } catch (Exception ex) {

            System.out.println(
                    "Snapshot: Developer Summary failed."
            );

            ex.printStackTrace();

            // Do not destroy an existing cached summary
            // if Gemini is temporarily unavailable.
        }

        // ==================================================
        // AI SKILLS SUMMARY
        // ==================================================

        System.out.println(
                "Snapshot: Loading Skills Summary..."
        );

        try {

            AiSkillsSummaryResponse skillsSummary =
                    portfolioAiService.generateSkillsSummary(
                            email,
                            github,
                            developerSkills,
                            growthReport
                    );

            snapshot.setSkillsSummaryData(
                    objectMapper.valueToTree(
                            skillsSummary
                    )
            );

            snapshot.setSkillsSummaryUpdatedAt(
                    LocalDateTime.now()
            );

            System.out.println(
                    "Snapshot: Skills Summary saved."
            );

        } catch (Exception ex) {

            System.out.println(
                    "Snapshot: Skills Summary failed."
            );

            ex.printStackTrace();

            // Keep previous cached data.
        }

        // ==================================================
        // PORTFOLIO SCORE
        // ==================================================

        System.out.println(
                "Snapshot: Loading Portfolio Score..."
        );

        try {

            PortfolioScoreResponse portfolioScore =
                    portfolioAiService.generatePortfolioScore(
                            email,
                            github,
                            leetcode,
                            growthReport
                    );

            snapshot.setPortfolioScoreData(
                    objectMapper.valueToTree(
                            portfolioScore
                    )
            );

            snapshot.setPortfolioScoreUpdatedAt(
                    LocalDateTime.now()
            );

            System.out.println(
                    "Snapshot: Portfolio Score saved."
            );

        } catch (Exception ex) {

            System.out.println(
                    "Snapshot: Portfolio Score failed."
            );

            ex.printStackTrace();

            // Keep previous cached data.
        }

        // ==================================================
        // SAVE SNAPSHOT
        // ==================================================

        snapshot.setUpdatedAt(
                LocalDateTime.now()
        );

        portfolioSnapshotRepository.save(snapshot);

        System.out.println(
                "========== PORTFOLIO SNAPSHOT SAVED =========="
        );
    }

    // ==================================================
    // INTERNAL SNAPSHOT LOADER
    // ==================================================

    private PortfolioSnapshot getSnapshot(
            String email
    ) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        return portfolioSnapshotRepository
                .findByUserId(user.getId())
                .orElseGet(() ->
                        PortfolioSnapshot.builder()
                                .user(user)
                                .build()
                );
    }

    // ==================================================
    // JSON → DTO
    // ==================================================

    private <T> T convert(
            JsonNode json,
            Class<T> targetType
    ) {

        try {

            return objectMapper.treeToValue(
                    json,
                    targetType
            );

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Failed to convert portfolio snapshot data",
                    ex
            );
        }
    }
}