package com.codementor.backend.export.dto;

import com.codementor.backend.dto.ConceptGrowthResponse;
import com.codementor.backend.dto.DeveloperMistakeProfileResponse;
import com.codementor.backend.dto.GrowthReportResponse;
import com.codementor.backend.dto.PersonalizedInterviewProfileResponse;
import com.codementor.backend.dto.PersonalizedLearningPlanResponse;
import com.codementor.backend.dto.PersonalizedRevisionPlanResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExportDataResponse {

    private ExportMetadata metadata;

    private ProfileExport profile;

    private ConnectedAccountsExport connectedAccounts;

    private NotificationExport notifications;

    private GithubExport github;

    private LeetCodeExport leetcode;

    private java.util.List<ConceptGrowthResponse> aiLearning;

    private GrowthReportResponse growthReport;

    private PersonalizedInterviewProfileResponse interview;

    private PersonalizedLearningPlanResponse learningPlan;

    private PersonalizedRevisionPlanResponse revisionPlan;

    private DeveloperMistakeProfileResponse mistakeMemory;

}