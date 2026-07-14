package com.codementor.backend.controller;

import com.codementor.backend.dto.AiMentorChatMessageResponse;
import com.codementor.backend.dto.AiMentorChatRequest;
import com.codementor.backend.dto.AiMentorChatResponse;
import com.codementor.backend.dto.AiMentorHintResponse;
import com.codementor.backend.dto.AiMentorResponse;
import com.codementor.backend.dto.AiMistakeResponse;
import com.codementor.backend.dto.AiMistakeSummaryResponse;
import com.codementor.backend.service.AiMentorService;
import com.codementor.backend.dto.DeveloperMistakeProfileResponse;
import com.codementor.backend.dto.RecurringMistakeResponse;
import com.codementor.backend.dto.ConceptGrowthResponse;
import com.codementor.backend.dto.PracticeRecommendationResponse;
import com.codementor.backend.dto.SolutionEvolutionResponse;
import com.codementor.backend.dto.HintDependencyScoreResponse;
import com.codementor.backend.dto.AdaptiveMentorProfileResponse;
import lombok.RequiredArgsConstructor;
import com.codementor.backend.dto.PastMistakeRecallResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.codementor.backend.dto.DeveloperSkillResponse;
import com.codementor.backend.dto.PersonalizedLearningPlanResponse;
import com.codementor.backend.dto.PersonalizedRevisionPlanResponse;
import com.codementor.backend.dto.GrowthReportResponse;
import com.codementor.backend.dto.PersonalizedInterviewProfileResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ai-mentor")
@RequiredArgsConstructor
public class AiMentorController {

    private final AiMentorService aiMentorService;


    // ANALYZE SUBMISSION

    @PostMapping("/analyze/{submissionId}")
    public ResponseEntity<AiMentorResponse> analyzeSubmission(
            @PathVariable Long submissionId,
            Authentication authentication) {

        return ResponseEntity.ok(
                aiMentorService.analyzeSubmission(
                        submissionId,
                        authentication.getName()
                )
        );
    }


    // GET EXISTING ANALYSIS

    @GetMapping("/analysis/{submissionId}")
    public ResponseEntity<AiMentorResponse> getAnalysis(
            @PathVariable Long submissionId,
            Authentication authentication) {

        return ResponseEntity.ok(
                aiMentorService.getAnalysis(
                        submissionId,
                        authentication.getName()
                )
        );
    }


    // SEND CHAT MESSAGE

    @PostMapping("/chat/{submissionId}")
    public ResponseEntity<AiMentorChatResponse> chat(
            @PathVariable Long submissionId,
            @RequestBody AiMentorChatRequest request,
            Authentication authentication) {

        return ResponseEntity.ok(
                aiMentorService.chat(
                        submissionId,
                        request.getMessage(),
                        authentication.getName()
                )
        );
    }


    // GET CHAT HISTORY

    @GetMapping("/chat/{submissionId}")
    public ResponseEntity<List<AiMentorChatMessageResponse>>
    getChatHistory(
            @PathVariable Long submissionId,
            Authentication authentication) {

        return ResponseEntity.ok(
                aiMentorService.getChatHistory(
                        submissionId,
                        authentication.getName()
                )
        );
    }


    // GET PROGRESSIVE HINT

    @PostMapping("/hint/{submissionId}/{level}")
    public ResponseEntity<AiMentorHintResponse> getProgressiveHint(
            @PathVariable Long submissionId,
            @PathVariable Integer level,
            Authentication authentication) {

        return ResponseEntity.ok(
                aiMentorService.getProgressiveHint(
                        submissionId,
                        level,
                        authentication.getName()
                )
        );
    }


    // GET MY MISTAKE SUMMARY
    // IMPORTANT: Keep this BEFORE /mistakes/{submissionId}

    @GetMapping("/mistakes/summary/me")
    public ResponseEntity<List<AiMistakeSummaryResponse>>
    getMyMistakeSummary(
            Authentication authentication) {

        return ResponseEntity.ok(
                aiMentorService.getMyMistakeSummary(
                        authentication.getName()
                )
        );
    }


    // GET SUBMISSION MISTAKES

    @GetMapping("/mistakes/{submissionId}")
    public ResponseEntity<List<AiMistakeResponse>>
    getSubmissionMistakes(
            @PathVariable Long submissionId,
            Authentication authentication) {

        return ResponseEntity.ok(
                aiMentorService.getSubmissionMistakes(
                        submissionId,
                        authentication.getName()
                )
        );
    }
    @GetMapping("/mistakes/profile/me")
        public ResponseEntity<DeveloperMistakeProfileResponse>
        getMyDeveloperMistakeProfile(
                Authentication authentication) {

        return ResponseEntity.ok(
                aiMentorService.getMyDeveloperMistakeProfile(
                        authentication.getName()
                )
        );
        }

                @GetMapping("/mistakes/recurring/me")
                        public ResponseEntity<List<RecurringMistakeResponse>>
                        getMyRecurringMistakes(
                                Authentication authentication) {

                        return ResponseEntity.ok(
                                aiMentorService.getMyRecurringMistakes(
                                        authentication.getName()
                        )
                );
        }

        @GetMapping("/mistakes/recall/{submissionId}")
        public ResponseEntity<PastMistakeRecallResponse>
        getPastMistakeRecall(
                @PathVariable Long submissionId,
                Authentication authentication) {

        return ResponseEntity.ok(
                aiMentorService.getPastMistakeRecall(
                        submissionId,
                        authentication.getName()
                )
        );
        }

        @GetMapping("/growth/me")
        public ResponseEntity<List<ConceptGrowthResponse>>
        getMyConceptGrowth(
                Authentication authentication) {

        return ResponseEntity.ok(
                aiMentorService.getMyConceptGrowth(
                        authentication.getName()
                )
        );
        }

        @GetMapping("/practice-recommendations/me")
        public ResponseEntity<List<PracticeRecommendationResponse>>
        getMyPracticeRecommendations(
                Authentication authentication) {

        return ResponseEntity.ok(
                aiMentorService.getMyPracticeRecommendations(
                        authentication.getName()
                )
        );
        }

        // =========================================================
        // DEVELOPER SKILL GRAPH
        // =========================================================

        @GetMapping("/skills/me")
        public ResponseEntity<List<DeveloperSkillResponse>>
        getMyDeveloperSkillGraph(
                Authentication authentication) {

        return ResponseEntity.ok(
                aiMentorService.getMyDeveloperSkillGraph(
                        authentication.getName()
                )
        );
        }

        // ==================================================
        // GET SOLUTION EVOLUTION TIMELINE
        // ==================================================

        @GetMapping("/solution-evolution/{problemId}")
        public ResponseEntity<List<SolutionEvolutionResponse>>
        getSolutionEvolution(
                @PathVariable Long problemId,
                Authentication authentication) {

        return ResponseEntity.ok(
                aiMentorService.getSolutionEvolution(
                        problemId,
                        authentication.getName()
                )
        );
        }     
        
        // ==================================================
        // GET MY HINT DEPENDENCY SCORE
        // ==================================================

        @GetMapping("/hint-dependency/me")
        public ResponseEntity<HintDependencyScoreResponse>
        getMyHintDependencyScore(
                Authentication authentication) {

        return ResponseEntity.ok(
                aiMentorService.getMyHintDependencyScore(
                        authentication.getName()
                )
        );
        }
    
        // ==================================================
        // GET MY PERSONALIZED LEARNING PLAN
        // ==================================================

        @GetMapping("/learning-plan/me")
        public ResponseEntity<PersonalizedLearningPlanResponse>
        getMyPersonalizedLearningPlan(
                Authentication authentication) {

        return ResponseEntity.ok(
                aiMentorService.getMyPersonalizedLearningPlan(
                        authentication.getName()
                )
        );
        }

        // ==================================================
        // GET MY ADAPTIVE MENTOR PROFILE
        // ==================================================

        @GetMapping("/adaptive-profile/me")
        public ResponseEntity<AdaptiveMentorProfileResponse>
        getMyAdaptiveMentorProfile(
                Authentication authentication) {

        return ResponseEntity.ok(
                aiMentorService.getMyAdaptiveMentorProfile(
                        authentication.getName()
                )
        );
        }
        
        // ==================================================
        // GET MY PERSONALIZED REVISION PLAN
        // ==================================================

        @GetMapping("/revision-plan/me")
        public ResponseEntity<PersonalizedRevisionPlanResponse>
        getMyPersonalizedRevisionPlan(
                Authentication authentication) {

        return ResponseEntity.ok(
                aiMentorService.getMyPersonalizedRevisionPlan(
                        authentication.getName()
                )
        );
        }

        @GetMapping("/growth-report/me")
        public ResponseEntity<GrowthReportResponse> getMyGrowthReport(
                Authentication authentication
        ) {

        GrowthReportResponse response =
                aiMentorService.getMyGrowthReport(
                        authentication.getName()
                );

        return ResponseEntity.ok(response);
        }

        // ==================================================
        // GET MY PERSONALIZED INTERVIEW PROFILE
        // ==================================================

        @GetMapping("/interview-profile/me")
        public ResponseEntity<PersonalizedInterviewProfileResponse>
        getMyPersonalizedInterviewProfile(
                Authentication authentication) {

        return ResponseEntity.ok(
                aiMentorService.getMyPersonalizedInterviewProfile(
                        authentication.getName()
                )
        );
        }
        
}