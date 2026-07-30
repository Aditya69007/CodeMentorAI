package com.codementor.backend.export.service.impl;

import com.codementor.backend.dto.NotificationSettingsResponse;
import com.codementor.backend.dto.PersonalizedInterviewProfileResponse;
import com.codementor.backend.dto.PersonalizedLearningPlanResponse;
import com.codementor.backend.dto.PersonalizedRevisionPlanResponse;
import com.codementor.backend.dto.UserProfileResponse;
import com.codementor.backend.export.dto.ExportDataResponse;
import com.codementor.backend.export.dto.ExportMetadata;
import com.codementor.backend.export.dto.NotificationExport;
import com.codementor.backend.dto.ConceptGrowthResponse;
import com.codementor.backend.dto.ConnectedAccountsResponse;
import com.codementor.backend.dto.DeveloperMistakeProfileResponse;
import com.codementor.backend.export.dto.ConnectedAccountsExport;
import com.codementor.backend.export.service.DeveloperReportService;
import com.codementor.backend.service.GitHubService;
import com.codementor.backend.dto.GitHubProfileResponse;
import com.codementor.backend.dto.GrowthReportResponse;
import com.codementor.backend.export.dto.GithubExport;
import com.codementor.backend.service.LeetCodeService;
import com.codementor.backend.dto.LeetCodeProfileResponse;
import com.codementor.backend.export.dto.LeetCodeExport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import com.codementor.backend.export.dto.ProfileExport;
import com.codementor.backend.service.UserService;
import com.codementor.backend.service.AiMentorService;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DeveloperReportServiceImpl implements DeveloperReportService {

    private final UserService userService;
    private final GitHubService gitHubService;
    private final LeetCodeService leetCodeService;
    private final AiMentorService aiMentorService;

    @Override
    public ExportDataResponse generateDeveloperReport(String email) {

        // Collect data from all modules here
        ExportMetadata metadata = ExportMetadata.builder()
                .applicationName("CodeMentorAI")
                .version("1.0")
                .exportedAt(LocalDateTime.now())
                .generatedBy(email)
                .build();

        UserProfileResponse user =
            userService.getCurrentUser(email);

        ProfileExport profile = ProfileExport.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .provider(user.getProvider())
                .profilePicture(user.getProfilePicture())
                .build();

        NotificationSettingsResponse notificationSettings =
                userService.getNotificationSettings(email);

        ConnectedAccountsResponse connectedAccountsResponse =
                userService.getConnectedAccounts(email);

        ConnectedAccountsExport connectedAccounts = ConnectedAccountsExport.builder()
                .githubUsername(connectedAccountsResponse.getGithubUsername())
                .leetcodeUsername(connectedAccountsResponse.getLeetcodeUsername())
                .githubConnected(connectedAccountsResponse.getGithubConnected())
                .leetcodeConnected(connectedAccountsResponse.getLeetcodeConnected())
                .githubLastSyncedAt(connectedAccountsResponse.getGithubLastSyncedAt())
                .leetcodeLastSyncedAt(connectedAccountsResponse.getLeetcodeLastSyncedAt())
                .build();

        GitHubProfileResponse githubProfile = null;

        if (connectedAccountsResponse.getGithubConnected() != null
                && connectedAccountsResponse.getGithubConnected()
                && connectedAccountsResponse.getGithubUsername() != null) {

            githubProfile = gitHubService.getProfile(
                    connectedAccountsResponse.getGithubUsername()
            );
        }

        GithubExport github = null;

        if (githubProfile != null) {

            github = GithubExport.builder()
                    .username(githubProfile.getUsername())
                    .name(githubProfile.getName())
                    .avatarUrl(githubProfile.getAvatarUrl())
                    .bio(githubProfile.getBio())
                    .profileUrl(githubProfile.getProfileUrl())
                    .publicRepositories(githubProfile.getPublicRepositories())
                    .publicGists(githubProfile.getPublicGists())
                    .createdAt(githubProfile.getCreatedAt())
                    .company(githubProfile.getCompany())
                    .location(githubProfile.getLocation())
                    .blog(githubProfile.getBlog())
                    .followers(githubProfile.getFollowers())
                    .following(githubProfile.getFollowing())
                    .build();

        }

        LeetCodeProfileResponse leetCodeProfile = null;

        if (connectedAccountsResponse.getLeetcodeConnected() != null
                && connectedAccountsResponse.getLeetcodeConnected()
                && connectedAccountsResponse.getLeetcodeUsername() != null) {

            leetCodeProfile = leetCodeService.getProfile(
                    connectedAccountsResponse.getLeetcodeUsername()
            );

        }

        LeetCodeExport leetcode = null;

        if (leetCodeProfile != null) {

            leetcode = LeetCodeExport.builder()
                    .username(leetCodeProfile.getUsername())
                    .profileUrl(leetCodeProfile.getProfileUrl())
                    .contest(leetCodeProfile.getContest())
                    .problems(leetCodeProfile.getProblems())
                    .calendar(leetCodeProfile.getCalendar())
                    .skills(leetCodeProfile.getSkills())
                    .badges(leetCodeProfile.getBadges())
                    .recentSubmissions(leetCodeProfile.getRecentSubmissions())
                    .analytics(leetCodeProfile.getAnalytics())
                    .build();

        }

        List<ConceptGrowthResponse> aiLearning =
                aiMentorService.getMyConceptGrowth(email);

        GrowthReportResponse growthReport =
                aiMentorService.getMyGrowthReport(email);

        PersonalizedInterviewProfileResponse interview =
                aiMentorService.getMyPersonalizedInterviewProfile(email);

        PersonalizedLearningPlanResponse learningPlan =
                aiMentorService.getMyPersonalizedLearningPlan(email);

        PersonalizedRevisionPlanResponse revisionPlan =
                aiMentorService.getMyPersonalizedRevisionPlan(email);

        DeveloperMistakeProfileResponse mistakeMemory =
                aiMentorService.getMyDeveloperMistakeProfile(email);

        NotificationExport notifications = NotificationExport.builder()
                .emailNotifications(notificationSettings.getEmailNotifications())
                .aiLearningTips(notificationSettings.getAiLearningTips())
                .contestReminders(notificationSettings.getContestReminders())
                .weeklyGrowthReport(notificationSettings.getWeeklyGrowthReport())
                .interviewAlerts(notificationSettings.getInterviewAlerts())
                .build();
                
        return ExportDataResponse.builder()
                .metadata(metadata)
                .profile(profile)
                .notifications(notifications)
                .connectedAccounts(connectedAccounts)
                .github(github)
                .leetcode(leetcode)
                .aiLearning(aiLearning)
                .growthReport(growthReport)
                .interview(interview)
                .learningPlan(learningPlan)
                .revisionPlan(revisionPlan)
                .mistakeMemory(mistakeMemory)
                .build();

    }
}