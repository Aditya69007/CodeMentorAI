package com.codementor.backend.service;

import com.codementor.backend.dto.AiMentorChatMessageResponse;
import com.codementor.backend.dto.AiMentorChatResponse;
import com.codementor.backend.dto.AiMentorHintResponse;
import com.codementor.backend.dto.AiMentorResponse;
import com.codementor.backend.dto.AiMistakeResponse;
import com.codementor.backend.dto.AiMistakeSummaryResponse;
import com.codementor.backend.dto.DeveloperMistakeProfileResponse;
import com.codementor.backend.dto.RecurringMistakeResponse;
import com.codementor.backend.dto.PastMistakeRecallResponse;
import com.codementor.backend.dto.PracticeRecommendationResponse;
import java.util.List;
import com.codementor.backend.dto.ConceptGrowthResponse;




public interface AiMentorService {

    AiMentorResponse analyzeSubmission(
            Long submissionId,
            String userEmail
    );


    AiMentorResponse getAnalysis(
            Long submissionId,
            String userEmail
    );


    AiMentorChatResponse chat(
            Long submissionId,
            String message,
            String userEmail
    );


    List<AiMentorChatMessageResponse> getChatHistory(
            Long submissionId,
            String userEmail
    );


    AiMentorHintResponse getProgressiveHint(
            Long submissionId,
            Integer level,
            String userEmail
    );


    List<AiMistakeResponse> getSubmissionMistakes(
            Long submissionId,
            String userEmail
    );


    List<AiMistakeSummaryResponse> getMyMistakeSummary(
            String userEmail
    );
    
    DeveloperMistakeProfileResponse getMyDeveloperMistakeProfile(
        String userEmail
    );

    List<RecurringMistakeResponse> getMyRecurringMistakes(
        String userEmail
    );

    PastMistakeRecallResponse getPastMistakeRecall(
        Long submissionId,
        String userEmail
    );
        List<ConceptGrowthResponse> getMyConceptGrowth(
                String userEmail
    );

    List<PracticeRecommendationResponse> getMyPracticeRecommendations(
        String userEmail
    );

}