package com.codementor.backend.service.impl;
import com.codementor.backend.dto.AiMistakeSummaryResponse;
import com.codementor.backend.entity.User;
import com.codementor.backend.repository.UserRepository;
import com.codementor.backend.entity.MistakeType;
import com.codementor.backend.ai.GeminiService;
import com.codementor.backend.dto.AdaptiveMentorProfileResponse;
import com.codementor.backend.dto.AiMentorChatMessageResponse;
import com.codementor.backend.dto.AiMentorChatResponse;
import com.codementor.backend.dto.AiMentorHintResponse;
import com.codementor.backend.dto.AiMentorResponse;
import com.codementor.backend.dto.AiMistakeDetectionResponse;
import com.codementor.backend.dto.AiMistakeItemResponse;
import com.codementor.backend.dto.AiMistakeResponse;
import com.codementor.backend.dto.ConceptGrowthResponse;
import com.codementor.backend.dto.PracticeRecommendationResponse;
import com.codementor.backend.dto.DeveloperSkillResponse;
import com.codementor.backend.dto.GrowthReportResponse;
import com.codementor.backend.dto.HintDependencyScoreResponse;

import com.codementor.backend.dto.PersonalizedInterviewProfileResponse;
import com.codementor.backend.entity.AiAnalysis;
import com.codementor.backend.entity.AiChatMessage;
import com.codementor.backend.entity.AiMistake;
import com.codementor.backend.entity.AiProgressiveHint;
import com.codementor.backend.entity.Submission;
import com.codementor.backend.entity.SubmissionStatus;
import com.codementor.backend.exception.ResourceNotFoundException;
import com.codementor.backend.repository.AiAnalysisRepository;
import com.codementor.backend.repository.AiChatMessageRepository;
import com.codementor.backend.repository.AiMistakeRepository;
import com.codementor.backend.repository.AiProgressiveHintRepository;
import com.codementor.backend.repository.IndependentSolveSessionRepository;
import com.codementor.backend.repository.SubmissionRepository;
import com.codementor.backend.service.AiMentorService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.codementor.backend.dto.RecurringMistakeResponse;
import com.codementor.backend.dto.SolutionEvolutionResponse;
import com.codementor.backend.dto.DeveloperMistakeProfileResponse;
import com.codementor.backend.entity.MistakeSeverity;
import com.codementor.backend.util.ConceptNormalizer;
import com.codementor.backend.dto.PastMistakeRecallResponse;
import com.codementor.backend.dto.PersonalizedLearningPlanResponse;
import com.codementor.backend.dto.PersonalizedRevisionPlanResponse;
import com.codementor.backend.service.AdaptiveMentorService;


import java.util.Comparator;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.codementor.backend.dto.RecommendedProblemResponse;
import com.codementor.backend.entity.Problem;
import com.codementor.backend.repository.ProblemRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AiMentorServiceImpl implements AiMentorService {

    private final AiMistakeRepository aiMistakeRepository;

    private final ProblemRepository problemRepository;

    private final IndependentSolveSessionRepository independentSolveSessionRepository;

    private final AdaptiveMentorService adaptiveMentorService;

    private final AiProgressiveHintRepository aiProgressiveHintRepository;

    private final SubmissionRepository submissionRepository;

    private final AiAnalysisRepository aiAnalysisRepository;

    private final AiChatMessageRepository aiChatMessageRepository;

    private final GeminiService geminiService;

    private final ObjectMapper objectMapper;

    private final UserRepository userRepository;


    // =========================================================
    // ANALYZE SUBMISSION
    // =========================================================

        @Override
        @Transactional
        public AiMentorResponse analyzeSubmission(
                Long submissionId,
                String userEmail) {

        Submission submission =
                getOwnedSubmission(
                        submissionId,
                        userEmail
                );

        ensureAiGuidanceAllowed(submission);

        AiAnalysis existingAnalysis =
                aiAnalysisRepository
                        .findBySubmissionId(submissionId)
                        .orElse(null);


        /*
        * Old submissions may already have AI analysis
        * but may not have Mistake Memory records yet.
        */

        if (existingAnalysis != null) {

                detectAndSaveMistakes(
                        submission,
                        existingAnalysis
                );

                return mapToResponse(existingAnalysis);
        }


        // =====================================================
        // BUILD PERSONALIZED LEARNING CONTEXT
        // =====================================================

        String personalizedLearningContext =
                adaptiveMentorService
                        .buildPersonalizedLearningContext(
                                userEmail
                        );


        // =====================================================
        // BUILD ADAPTIVE AI PROMPT
        // =====================================================

        String prompt =
                buildPrompt(
                        submission,
                        personalizedLearningContext
                );


        String aiResponse =
                geminiService.analyzeCode(prompt);


        try {

                JsonNode json =
                        objectMapper.readTree(aiResponse);


                AiAnalysis aiAnalysis =
                        AiAnalysis.builder()

                                .submission(submission)

                                .explanation(
                                        json
                                                .path("explanation")
                                                .asText()
                                )

                                .hint(
                                        json
                                                .path("hint")
                                                .asText()
                                )

                                .conceptToStudy(
                                        json
                                                .path("conceptToStudy")
                                                .asText()
                                )

                                .build();


                aiAnalysis =
                        aiAnalysisRepository.save(aiAnalysis);


                detectAndSaveMistakes(
                        submission,
                        aiAnalysis
                );


                return mapToResponse(aiAnalysis);


        } catch (Exception exception) {

                throw new RuntimeException(
                        "Failed to process AI response: "
                                + exception.getMessage(),
                        exception
                );
        }
        }
        
// =========================================================
    // GET ANALYSIS
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public AiMentorResponse getAnalysis(
            Long submissionId,
            String userEmail) {

        getOwnedSubmission(
                submissionId,
                userEmail
        );


        AiAnalysis aiAnalysis =
                aiAnalysisRepository
                        .findBySubmissionId(submissionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "AI analysis not found for submission id: "
                                                + submissionId
                                ));


        return mapToResponse(aiAnalysis);
    }


    // =========================================================
    // AI CHAT
    // =========================================================

        @Override
        @Transactional
        public AiMentorChatResponse chat(
                Long submissionId,
                String message,
                String userEmail) {

        Submission submission =
                getOwnedSubmission(
                        submissionId,
                        userEmail
                );

        ensureAiGuidanceAllowed(submission);

        AiAnalysis aiAnalysis =
                aiAnalysisRepository
                        .findBySubmissionId(submissionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "AI analysis not found for submission id: "
                                                + submissionId
                                ));


        List<AiChatMessage> chatHistory =
                aiChatMessageRepository
                        .findBySubmissionIdOrderByCreatedAtAsc(
                                submissionId
                        );


        // =====================================================
        // BUILD PERSONALIZED DEVELOPER LEARNING CONTEXT
        // =====================================================

        String personalizedLearningContext =
                adaptiveMentorService
                        .buildPersonalizedLearningContext(
                                userEmail
                        );


        // =====================================================
        // BUILD ADAPTIVE CHAT PROMPT
        // =====================================================

        String prompt =
                buildChatPrompt(
                        submission,
                        aiAnalysis,
                        chatHistory,
                        message,
                        personalizedLearningContext
                );


        String aiResponse =
                geminiService.chat(prompt);


        // =====================================================
        // SAVE USER MESSAGE
        // =====================================================

        AiChatMessage userChatMessage =
                AiChatMessage.builder()

                        .submission(submission)

                        .role("USER")

                        .content(message)

                        .build();


        aiChatMessageRepository.save(
                userChatMessage
        );


        // =====================================================
        // SAVE AI RESPONSE
        // =====================================================

        AiChatMessage assistantChatMessage =
                AiChatMessage.builder()

                        .submission(submission)

                        .role("ASSISTANT")

                        .content(aiResponse)

                        .build();


        aiChatMessageRepository.save(
                assistantChatMessage
        );


        return AiMentorChatResponse.builder()

                .submissionId(submissionId)

                .response(aiResponse)

                .build();
        }

    // =========================================================
    // GET CHAT HISTORY
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public List<AiMentorChatMessageResponse> getChatHistory(
            Long submissionId,
            String userEmail) {

        getOwnedSubmission(
                submissionId,
                userEmail
        );

        List<AiChatMessage> chatMessages =
                aiChatMessageRepository
                        .findBySubmissionIdOrderByCreatedAtAsc(
                                submissionId
                        );


        return chatMessages

                .stream()

                .map(chatMessage ->

                        AiMentorChatMessageResponse
                                .builder()

                                .id(
                                        chatMessage.getId()
                                )

                                .role(
                                        chatMessage.getRole()
                                )

                                .content(
                                        chatMessage.getContent()
                                )

                                .createdAt(
                                        chatMessage.getCreatedAt()
                                )

                                .build()
                )

                .toList();
    }


    // =========================================================
    // PROGRESSIVE HINT
    // =========================================================

        @Override
        @Transactional
        public AiMentorHintResponse getProgressiveHint(
                Long submissionId,
                Integer level,
                String userEmail) {

        // =====================================================
        // GET OWNED SUBMISSION
        // =====================================================

        Submission submission =
                getOwnedSubmission(
                        submissionId,
                        userEmail
                );


        ensureAiGuidanceAllowed(submission);


        // =====================================================
        // VALIDATE LEVEL
        // =====================================================

        if (level == null || level < 1 || level > 4) {

                throw new IllegalArgumentException(
                        "Hint level must be between 1 and 4."
                );
        }


        // =====================================================
        // RETURN SAVED HINT
        // =====================================================

        Optional<AiProgressiveHint> existingHint =
                aiProgressiveHintRepository
                        .findBySubmissionIdAndLevel(
                                submissionId,
                                level
                        );


        if (existingHint.isPresent()) {

                AiProgressiveHint savedHint =
                        existingHint.get();


                return AiMentorHintResponse
                        .builder()

                        .submissionId(submissionId)

                        .level(savedHint.getLevel())

                        .response(savedHint.getResponse())

                        .build();
        }


        // =====================================================
        // GET EXISTING AI ANALYSIS
        // =====================================================

        AiAnalysis aiAnalysis =
                aiAnalysisRepository
                        .findBySubmissionId(submissionId)
                        .orElse(null);


        // =====================================================
        // CREATE ANALYSIS IF MISSING
        // =====================================================

        if (aiAnalysis == null) {

                analyzeSubmission(
                        submissionId,
                        userEmail
                );


                aiAnalysis =
                        aiAnalysisRepository
                                .findBySubmissionId(submissionId)
                                .orElseThrow(() ->

                                        new IllegalStateException(

                                                "AI analysis could not be created for submission id: "
                                                        + submissionId
                                        )
                                );
        }


        // =====================================================
        // PERSONALIZED LEARNING CONTEXT
        // =====================================================

        String personalizedLearningContext;

        try {

                personalizedLearningContext =
                        adaptiveMentorService
                                .buildPersonalizedLearningContext(
                                        userEmail
                                );

        } catch (Exception exception) {

                personalizedLearningContext =
                        "No personalized learning history is currently available.";

        }


        // =====================================================
        // BUILD PROMPT
        // =====================================================

        String prompt =
                buildProgressiveHintPrompt(
                        submission,
                        aiAnalysis,
                        level,
                        personalizedLearningContext
                );


        // =====================================================
        // GENERATE HINT
        // =====================================================

        String aiResponse =
                geminiService.chat(prompt);


        if (
                aiResponse == null
                || aiResponse.isBlank()
        ) {

                throw new IllegalStateException(
                        "AI mentor returned an empty hint response."
                );
        }


        // =====================================================
        // SAVE HINT
        // =====================================================

        AiProgressiveHint progressiveHint =
                AiProgressiveHint
                        .builder()

                        .submission(submission)

                        .level(level)

                        .response(aiResponse)

                        .build();


        aiProgressiveHintRepository.save(
                progressiveHint
        );


        // =====================================================
        // RETURN RESPONSE
        // =====================================================

        return AiMentorHintResponse
                .builder()

                .submissionId(submissionId)

                .level(level)

                .response(aiResponse)

                .build();
        }
        
        // =========================================================
    // AI MISTAKE MEMORY
    // =========================================================

    private void detectAndSaveMistakes(
            Submission submission,
            AiAnalysis aiAnalysis) {


        /*
         * Accepted submissions should not
         * generate mistake records.
         */

        if (submission.getStatus() == SubmissionStatus.ACCEPTED) {
            return;
        }


        /*
         * Prevent duplicate mistake detection
         * for the same submission.
         */

        if (aiMistakeRepository.existsBySubmissionId(
                submission.getId()
        )) {
            return;
        }


        String prompt =
                buildMistakeDetectionPrompt(
                        submission,
                        aiAnalysis
                );

        try {

                String aiResponse =
                                geminiService.analyzeCode(prompt);


            AiMistakeDetectionResponse detectionResponse =
                    objectMapper.readValue(
                            aiResponse,
                            AiMistakeDetectionResponse.class
                    );


            if (
                    detectionResponse.getMistakes() == null ||
                    detectionResponse.getMistakes().isEmpty()
            ) {
                return;
            }


            for (
                    AiMistakeItemResponse detectedMistake :
                    detectionResponse.getMistakes()
            ) {


                /*
                 * Ignore incomplete AI responses.
                 */

                if (
                        detectedMistake.getMistakeType() == null ||
                        detectedMistake.getConcept() == null ||
                        detectedMistake.getDescription() == null ||
                        detectedMistake.getSeverity() == null
                ) {
                    continue;
                }


                AiMistake mistake =
                        AiMistake.builder()

                                .submission(submission)

                                .user(submission.getUser())

                                .problem(submission.getProblem())

                                .mistakeType(
                                        detectedMistake.getMistakeType()
                                )

                                .concept(
                                        ConceptNormalizer.normalize(
                                                detectedMistake.getConcept()
                                        )
                                )

                                .description(
                                        detectedMistake.getDescription()
                                )

                                .severity(
                                        detectedMistake.getSeverity()
                                )

                                .build();


                aiMistakeRepository.save(mistake);
            }


        } catch (Exception exception) {

                System.err.println(
                        "Mistake detection skipped for submission "
                                + submission.getId()
                                + ": "
                                + exception.getMessage()
                );
        }
    }


    // =========================================================
    // OWNERSHIP CHECK
    // =========================================================
        
    @Override
        @Transactional(readOnly = true)
        public List<AiMistakeResponse> getSubmissionMistakes(
                Long submissionId,
                String userEmail) {

        getOwnedSubmission(
                submissionId,
                userEmail
        );

        return aiMistakeRepository
                .findBySubmissionId(submissionId)
                .stream()
                .map(mistake ->

                        AiMistakeResponse.builder()

                                .id(mistake.getId())

                                .submissionId(
                                        mistake
                                                .getSubmission()
                                                .getId()
                                )

                                .problemId(
                                        mistake
                                                .getProblem()
                                                .getId()
                                )

                                .problemTitle(
                                        mistake
                                                .getProblem()
                                                .getTitle()
                                )

                                .mistakeType(
                                        mistake.getMistakeType()
                                )

                                .concept(
                                        mistake.getConcept()
                                )

                                .description(
                                        mistake.getDescription()
                                )

                                .severity(
                                        mistake.getSeverity()
                                )

                                .createdAt(
                                        mistake.getCreatedAt()
                                )

                                .build()
                )
                .toList();
        }

        @Override
        @Transactional(readOnly = true)
        public List<AiMistakeSummaryResponse> getMyMistakeSummary(
                String userEmail) {

        User user =
                userRepository
                        .findByEmail(userEmail)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found."
                                )
                        );


        return aiMistakeRepository
                .findMistakeSummaryByUserId(
                        user.getId()
                )
                .stream()
                .map(row ->

                        AiMistakeSummaryResponse.builder()

                                .mistakeType(
                                        (MistakeType) row[0]
                                )

                                .count(
                                        (Long) row[1]
                                )

                                .build()
                )
                .toList();
        }

        @Override
        @Transactional(readOnly = true)
        public DeveloperMistakeProfileResponse getMyDeveloperMistakeProfile(
                String userEmail) {

        User user =
                userRepository
                        .findByEmail(userEmail)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found."
                                )
                        );


        List<AiMistake> mistakes =
                aiMistakeRepository
                        .findByUserIdOrderByCreatedAtDesc(
                                user.getId()
                        );


        Map<String, Long> mistakeTypeBreakdown =
                new LinkedHashMap<>();

        Map<String, Long> severityBreakdown =
                new LinkedHashMap<>();

        Map<String, Long> conceptBreakdown =
                new LinkedHashMap<>();


        for (AiMistake mistake : mistakes) {

                mistakeTypeBreakdown.merge(
                        mistake.getMistakeType().name(),
                        1L,
                        Long::sum
                );


                severityBreakdown.merge(
                        mistake.getSeverity().name(),
                        1L,
                        Long::sum
                );


                conceptBreakdown.merge(
                        mistake.getConcept(),
                        1L,
                        Long::sum
                );
        }


        MistakeType mostCommonMistake =
                mistakes.isEmpty()
                        ? null
                        : mistakeTypeBreakdown
                                .entrySet()
                                .stream()
                                .max(Map.Entry.comparingByValue())
                                .map(entry ->
                                        MistakeType.valueOf(
                                                entry.getKey()
                                        )
                                )
                                .orElse(null);


        String weakestConcept =
                conceptBreakdown
                        .entrySet()
                        .stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse(null);


        List<String> insights =
                new java.util.ArrayList<>();


        if (mostCommonMistake != null) {

                insights.add(
                        mostCommonMistake.name()
                                .replace("_", " ")
                                + " is your most frequent mistake."
                );
        }


        if (weakestConcept != null) {

                insights.add(
                        weakestConcept
                                + " is currently your weakest concept."
                );
        }


        long highSeverityCount =
                severityBreakdown.getOrDefault(
                        MistakeSeverity.HIGH.name(),
                        0L
                );


        if (highSeverityCount > 0) {

                insights.add(
                        "You have "
                                + highSeverityCount
                                + " high-severity mistake"
                                + (highSeverityCount == 1 ? "" : "s")
                                + " that need attention."
                );
        }


        return DeveloperMistakeProfileResponse.builder()

                .totalMistakes(
                        (long) mistakes.size()
                )

                .mostCommonMistake(
                        mostCommonMistake
                )

                .weakestConcept(
                        weakestConcept
                )

                .mistakeTypeBreakdown(
                        mistakeTypeBreakdown
                )

                .severityBreakdown(
                        severityBreakdown
                )

                .conceptBreakdown(
                        conceptBreakdown
                )

                .insights(
                        insights
                )

                .build();
        }

                @Override
                @Transactional(readOnly = true)
                public List<RecurringMistakeResponse> getMyRecurringMistakes(
                        String userEmail) {

                User user =
                        userRepository
                                .findByEmail(userEmail)
                                .orElseThrow(() ->
                                        new ResourceNotFoundException(
                                                "User not found."
                                        )
                                );


                return aiMistakeRepository
                        .findRecurringMistakesByUserId(
                                user.getId()
                        )
                        .stream()
                        .map(row -> {

                                MistakeType mistakeType =
                                        (MistakeType) row[0];

                                Long occurrenceCount =
                                        (Long) row[1];

                                Long affectedProblems =
                                        (Long) row[2];


                                String readableMistake =
                                        mistakeType
                                                .name()
                                                .replace("_", " ");


                                String message =
                                        "You have repeated "
                                                + readableMistake
                                                + " "
                                                + occurrenceCount
                                                + " times across "
                                                + affectedProblems
                                                + " problem"
                                                + (affectedProblems == 1 ? "." : "s.");


                                return RecurringMistakeResponse.builder()

                                        .mistakeType(mistakeType)

                                        .occurrenceCount(occurrenceCount)

                                        .affectedProblems(affectedProblems)

                                        .message(message)

                                        .build();
                        })
                        .toList();
                }
                
                @Override
                @Transactional(readOnly = true)
                public PastMistakeRecallResponse getPastMistakeRecall(
                        Long submissionId,
                        String userEmail) {

                // Verify that the submission exists
                // and belongs to the logged-in user.
                Submission submission =
                        getOwnedSubmission(
                                submissionId,
                                userEmail
                        );


                // Get current submission mistakes.
                List<AiMistake> currentMistakes =
                        aiMistakeRepository
                                .findBySubmissionIdOrderByCreatedAtAsc(
                                        submissionId
                                );


                // No mistakes detected for current submission.
                if (currentMistakes.isEmpty()) {

                        return PastMistakeRecallResponse.builder()

                                .repeatedMistake(false)

                                .message(
                                        "No mistake history is available for this submission."
                                )

                                .build();
                }


                User user =
                        userRepository
                                .findByEmail(userEmail)
                                .orElseThrow(() ->
                                        new ResourceNotFoundException(
                                                "User not found."
                                        )
                                );


                // Check every current mistake against past history.
                for (AiMistake currentMistake : currentMistakes) {

                        List<AiMistake> previousMistakes =
                                aiMistakeRepository
                                        .findPreviousSimilarMistakes(

                                                user.getId(),

                                                currentMistake.getMistakeType(),

                                                submissionId
                                        );


                        // We found the same mistake in history.
                        if (!previousMistakes.isEmpty()) {

                        AiMistake previousMistake =
                                previousMistakes.get(0);


                        long occurrenceCount =
                                previousMistakes.size() + 1L;


                        return PastMistakeRecallResponse.builder()

                                .repeatedMistake(true)

                                .mistakeType(
                                        currentMistake.getMistakeType()
                                )

                                .concept(
                                        currentMistake.getConcept()
                                )

                                .occurrenceCount(
                                        occurrenceCount
                                )

                                .previousSubmissionId(
                                        previousMistake
                                                .getSubmission()
                                                .getId()
                                )

                                .previousProblemTitle(
                                        previousMistake
                                                .getProblem()
                                                .getTitle()
                                )

                                .message(
                                        "You have made this mistake before. "
                                                + "This pattern has appeared "
                                                + occurrenceCount
                                                + " times in your coding history."
                                )

                                .memoryAdvice(
                                        buildMemoryAdvice(
                                                currentMistake
                                        )
                                )

                                .build();
                        }
                }


                // Current mistakes were not found in previous history.
                AiMistake firstMistake =
                        currentMistakes.get(0);


                return PastMistakeRecallResponse.builder()

                        .repeatedMistake(false)

                        .mistakeType(
                                firstMistake.getMistakeType()
                        )

                        .concept(
                                firstMistake.getConcept()
                        )

                        .occurrenceCount(1L)

                        .message(
                                "This mistake pattern is new in your coding history."
                        )

                        .build();
                }

                private String buildMemoryAdvice(
                        AiMistake mistake) {

                return switch (mistake.getMistakeType()) {

                        case LOGIC_ERROR ->
                                "Review the logic step by step before coding. "
                                        + "Test your approach manually with normal, edge, "
                                        + "and invalid inputs.";

                        case WRONG_ALGORITHM ->
                                "Before implementation, verify that your algorithm handles "
                                        + "all problem constraints and edge cases. "
                                        + "A solution that works for simple examples may "
                                        + "still fail hidden test cases.";

                        case WRONG_DATA_STRUCTURE ->
                                "Think about the operations required by the problem before "
                                        + "choosing a data structure. Match the problem's "
                                        + "behavior with structures such as stacks, queues, "
                                        + "hash maps, trees, or graphs.";

                        case TIME_COMPLEXITY ->
                                "Check the input constraints before coding and estimate "
                                        + "the time complexity of your approach.";

                        case SPACE_COMPLEXITY ->
                                "Review whether additional memory is necessary and look "
                                        + "for opportunities to reuse existing storage.";

                        case BOUNDARY_CONDITION ->
                                "Test minimum values, maximum values, empty inputs, "
                                        + "and values around important boundaries.";

                        case EDGE_CASE_MISSED ->
                                "Before submitting, explicitly list and test unusual inputs "
                                        + "that may behave differently from normal cases.";

                        case LOOP_ERROR ->
                                "Trace each loop manually and verify initialization, "
                                        + "termination conditions, and variable updates.";

                        case RECURSION_ERROR ->
                                "Trace the recursive calls and confirm that every path "
                                        + "moves toward a valid base case.";

                        case BASE_CASE_ERROR ->
                                "Define and test the smallest valid input before writing "
                                        + "the recursive logic.";

                        case INPUT_OUTPUT_ERROR ->
                                "Verify the required input format and expected output "
                                        + "before focusing on the algorithm.";

                        case SYNTAX_ERROR ->
                                "Check compiler messages carefully and fix syntax issues "
                                        + "before debugging the algorithm.";

                        case INCORRECT_INITIALIZATION ->
                                "Verify that every variable and data structure starts "
                                        + "with the correct initial value.";

                        case NULL_HANDLING ->
                                "Check whether inputs or references can be null or empty "
                                        + "before accessing them.";

                        case OTHER ->
                                "Review the previous mistake carefully and test your "
                                        + "approach with multiple inputs before submitting.";

                };
                }


        @Override
        @Transactional(readOnly = true)
        public List<ConceptGrowthResponse> getMyConceptGrowth(
                String userEmail) {

        User user =
                userRepository
                        .findByEmail(userEmail)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found."
                                )
                        );


        List<AiMistake> mistakes =
                aiMistakeRepository
                        .findByUserIdOrderByCreatedAtDesc(
                                user.getId()
                        );


        Map<String, List<AiMistake>> mistakesByConcept =
                mistakes
                        .stream()
                        .collect(
                                java.util.stream.Collectors.groupingBy(
                                        AiMistake::getConcept
                                )
                        );


        List<ConceptGrowthResponse> growthResponses =
                new java.util.ArrayList<>();


        for (
                Map.Entry<String, List<AiMistake>> entry
                : mistakesByConcept.entrySet()
        ) {

                String concept =
                        entry.getKey();


                List<AiMistake> conceptMistakes =
                        entry.getValue();


                long totalMistakes =
                        conceptMistakes.size();


                /*
                * Find the latest mistake date for this concept.
                *
                * We measure recovery AFTER the user's latest
                * mistake for this concept.
                */

                LocalDateTime latestMistakeDate =
                        conceptMistakes
                                .stream()
                                .map(AiMistake::getCreatedAt)
                                .max(LocalDateTime::compareTo)
                                .orElseThrow();


                /*
                * Find all unique problems associated
                * with this concept.
                */

                java.util.Set<Long> problemIds =
                        conceptMistakes
                                .stream()
                                .map(mistake ->
                                        mistake
                                                .getProblem()
                                                .getId()
                                )
                                .collect(
                                        java.util.stream.Collectors.toSet()
                                );


                /*
                * Count accepted submissions after the
                * latest mistake.
                *
                * Each problem is counted only once.
                */

                long acceptedSubmissions = 0;


                for (Long problemId : problemIds) {

                acceptedSubmissions +=
                        submissionRepository
                                .countByUserAndProblemAndStatusAfterDate(

                                        user.getId(),

                                        problemId,

                                        SubmissionStatus.ACCEPTED,

                                        latestMistakeDate
                                );
                }


                String growthStatus;

                String message;


                if (acceptedSubmissions >= 2) {

                growthStatus =
                        "MASTERED";


                message =
                        "You previously struggled with "
                                + concept
                                + ", but you have now successfully solved "
                                + "multiple related problems.";


                } else if (acceptedSubmissions == 1) {

                growthStatus =
                        "IMPROVING";


                message =
                        "You previously struggled with "
                                + concept
                                + ", but your recent successful submission "
                                + "shows improvement.";


                } else {

                growthStatus =
                        "REPEATING";


                message =
                        "You are still repeating mistakes related to "
                                + concept
                                + ". Review this concept and practice it again.";
                }


                growthResponses.add(

                        ConceptGrowthResponse
                                .builder()

                                .concept(
                                        concept
                                )

                                .totalMistakes(
                                        totalMistakes
                                )

                                .acceptedSubmissions(
                                        acceptedSubmissions
                                )

                                .growthStatus(
                                        growthStatus
                                )

                                .message(
                                        message
                                )

                                .build()
                );
        }


        return growthResponses;
        }

        // =========================================================
        // DEVELOPER SKILL GRAPH
        // =========================================================

        @Override
        @Transactional(readOnly = true)
        public List<DeveloperSkillResponse> getMyDeveloperSkillGraph(
                String userEmail
        ) {

        User user =
                userRepository
                        .findByEmail(userEmail)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found."
                                )
                        );


        // =====================================================
        // GET SUBMISSION PERFORMANCE PER TOPIC
        //
        // row[0] = topicId
        // row[1] = topicName
        // row[2] = totalSubmissions
        // row[3] = acceptedSubmissions
        // =====================================================

        List<Object[]> submissionStats =
                submissionRepository
                        .findDeveloperSkillStatsByUserId(
                                user.getId()
                        );


        // =====================================================
        // GET MISTAKE COUNT PER TOPIC
        //
        // row[0] = topicId
        // row[1] = mistakeCount
        // =====================================================

        List<Object[]> mistakeStats =
                aiMistakeRepository
                        .findMistakeCountByTopicForUser(
                                user.getId()
                        );


        // =====================================================
        // CONVERT MISTAKE STATS INTO MAP
        // =====================================================

        Map<Long, Long> mistakeCountByTopic =
                new LinkedHashMap<>();


        for (Object[] row : mistakeStats) {

                Long topicId =
                        (Long) row[0];


                Long mistakeCount =
                        (Long) row[1];


                mistakeCountByTopic.put(
                        topicId,
                        mistakeCount
                );
        }


        // =====================================================
        // BUILD FINAL SKILL GRAPH
        // =====================================================

        List<DeveloperSkillResponse> skillGraph =
                new ArrayList<>();


        for (Object[] row : submissionStats) {

                Long topicId =
                        (Long) row[0];


                String topicName =
                        (String) row[1];


                Long totalSubmissions =
                        (Long) row[2];


                Long acceptedSubmissions =
                        (Long) row[3];


                Long totalMistakes =
                        mistakeCountByTopic.getOrDefault(
                                topicId,
                                0L
                        );


                // =================================================
                // ACCEPTANCE RATE
                // =================================================

                double acceptanceRate =
                        totalSubmissions == 0
                                ? 0.0
                                : (
                                        (double) acceptedSubmissions
                                                / totalSubmissions
                                ) * 100.0;


                acceptanceRate =
                        Math.round(
                                acceptanceRate * 100.0
                        ) / 100.0;


                // =================================================
                // SKILL SCORE
                //
                // 70% = submission acceptance performance
                // 30% = mistake control
                //
                // More mistakes reduce mistake score.
                // =================================================

                double acceptanceScore =
                        acceptanceRate;


                double mistakeScore =
                        Math.max(
                                0.0,
                                100.0 - (totalMistakes * 15.0)
                        );


                int skillScore =
                        (int) Math.round(

                                (acceptanceScore * 0.70)

                                        +

                                (mistakeScore * 0.30)
                        );


                // Keep score between 0 and 100.

                skillScore =
                        Math.max(
                                0,
                                Math.min(
                                        100,
                                        skillScore
                                )
                        );


                // =================================================
                // DETERMINE SKILL LEVEL
                // =================================================

                String skillLevel;


                if (skillScore >= 80) {

                skillLevel = "MASTERED";

                } else if (skillScore >= 60) {

                skillLevel = "STRONG";

                } else if (skillScore >= 40) {

                skillLevel = "DEVELOPING";

                } else {

                skillLevel = "NEEDS_PRACTICE";
                }


                // =================================================
                // BUILD HUMAN-READABLE MESSAGE
                // =================================================

                String message;


                switch (skillLevel) {

                case "MASTERED" ->

                        message =
                                "You demonstrate strong mastery of "
                                        + topicName
                                        + ".";


                case "STRONG" ->

                        message =
                                "You have strong "
                                        + topicName
                                        + " skills with room for further improvement.";


                case "DEVELOPING" ->

                        message =
                                "Your "
                                        + topicName
                                        + " skills are developing. Continue practicing related problems.";


                default ->

                        message =
                                "You need more practice with "
                                        + topicName
                                        + " problems.";
                }


                // =================================================
                // BUILD RESPONSE
                // =================================================

                skillGraph.add(

                        DeveloperSkillResponse
                                .builder()

                                .topicId(
                                        topicId
                                )

                                .topicName(
                                        topicName
                                )

                                .totalSubmissions(
                                        totalSubmissions
                                )

                                .acceptedSubmissions(
                                        acceptedSubmissions
                                )

                                .totalMistakes(
                                        totalMistakes
                                )

                                .acceptanceRate(
                                        acceptanceRate
                                )

                                .skillScore(
                                        skillScore
                                )

                                .skillLevel(
                                        skillLevel
                                )

                                .message(
                                        message
                                )

                                .build()
                );
        }


        // =====================================================
        // STRONGEST SKILLS FIRST
        // =====================================================

        skillGraph.sort(

                Comparator.comparingInt(
                        DeveloperSkillResponse::getSkillScore
                ).reversed()

        );


        return skillGraph;
        }

        @Override
        @Transactional(readOnly = true)
        public List<PracticeRecommendationResponse> getMyPracticeRecommendations(
                String userEmail) {

        // =========================================================
        // FIND LOGGED-IN USER
        // =========================================================

        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found."
                        )
                );


        // =========================================================
        // GET EXISTING CONCEPT GROWTH
        // =========================================================

        List<ConceptGrowthResponse> conceptGrowth =
                getMyConceptGrowth(userEmail);


        // =========================================================
        // FIND ALL PROBLEMS SOLVED BY USER
        // =========================================================

        Set<Long> solvedProblemIds =
                submissionRepository
                        .findByUserId(user.getId())
                        .stream()

                        .filter(submission ->
                                submission.getStatus()
                                        == SubmissionStatus.ACCEPTED
                        )

                        .map(submission ->
                                submission
                                        .getProblem()
                                        .getId()
                        )

                        .collect(Collectors.toSet());


        // =========================================================
        // FINAL RECOMMENDATION LIST
        // =========================================================

        List<PracticeRecommendationResponse> recommendations =
                new ArrayList<>();


        // =========================================================
        // PROCESS EVERY WEAK / IMPROVING CONCEPT
        // =========================================================

        for (ConceptGrowthResponse growth : conceptGrowth) {

                String priority;

                int recommendedProblemCount;

                String reason;

                String recommendation;


                // =====================================================
                // DETERMINE PRIORITY
                // =====================================================

                if ("REPEATING".equals(growth.getGrowthStatus())) {

                priority = "HIGH";

                recommendedProblemCount = 3;

                reason =
                        "You have made "
                                + growth.getTotalMistakes()
                                + (growth.getTotalMistakes() == 1
                                ? " mistake"
                                : " mistakes")
                                + " related to "
                                + growth.getConcept()
                                + " and have not yet successfully recovered.";

                recommendation =
                        "Practice "
                                + recommendedProblemCount
                                + " "
                                + growth.getConcept()
                                + " problems next.";


                } else if ("IMPROVING".equals(growth.getGrowthStatus())) {

                priority = "MEDIUM";

                recommendedProblemCount = 2;

                reason =
                        "You are improving in "
                                + growth.getConcept()
                                + ", but additional practice will help "
                                + "strengthen your understanding.";

                recommendation =
                        "Practice "
                                + recommendedProblemCount
                                + " more "
                                + growth.getConcept()
                                + " problems.";


                } else {

                priority = "LOW";

                recommendedProblemCount = 1;

                reason =
                        "You have shown strong improvement in "
                                + growth.getConcept()
                                + ".";

                recommendation =
                        "Solve "
                                + recommendedProblemCount
                                + " occasional "
                                + growth.getConcept()
                                + " problem to maintain your skills.";
                }


                // =====================================================
                // FIND REAL PROBLEMS BELONGING TO THIS CONCEPT
                // =====================================================

                List<Problem> conceptProblems =
                        problemRepository
                                .findByTopicNameIgnoreCaseAndActiveTrue(
                                        growth.getConcept()
                                );


                // =====================================================
                // SORT PROBLEMS
                //
                // UNSOLVED FIRST
                // THEN DIFFICULTY
                // THEN TITLE
                // =====================================================

                List<Problem> orderedProblems =
                        conceptProblems
                                .stream()

                                .sorted(
                                        Comparator

                                                .comparing(
                                                        (Problem problem) ->
                                                                solvedProblemIds.contains(
                                                                        problem.getId()
                                                                )
                                                )

                                                .thenComparing(
                                                        Problem::getDifficulty
                                                )

                                                .thenComparing(
                                                        Problem::getTitle
                                                )
                                )

                                .toList();


                // =====================================================
                // CONVERT PROBLEMS INTO RESPONSE DTO
                // =====================================================

                List<RecommendedProblemResponse> recommendedProblems =
                        orderedProblems
                                .stream()

                                .limit(recommendedProblemCount)

                                .map(problem ->

                                        RecommendedProblemResponse
                                                .builder()

                                                .id(
                                                        problem.getId()
                                                )

                                                .title(
                                                        problem.getTitle()
                                                )

                                                .difficulty(
                                                        problem.getDifficulty()
                                                )

                                                .solved(
                                                        solvedProblemIds.contains(
                                                                problem.getId()
                                                        )
                                                )

                                                .reason(
                                                        "Recommended to strengthen your "
                                                                + growth.getConcept()
                                                                + " skills."
                                                )

                                                .build()
                                )

                                .toList();


                // =====================================================
                // BUILD PRACTICE RECOMMENDATION
                // =====================================================

                PracticeRecommendationResponse response =
                        PracticeRecommendationResponse
                                .builder()

                                .concept(
                                        growth.getConcept()
                                )

                                .totalMistakes(
                                        growth.getTotalMistakes()
                                )

                                .successfulRecoveries(
                                        growth.getAcceptedSubmissions()
                                )

                                .growthStatus(
                                        growth.getGrowthStatus()
                                )

                                .priority(
                                        priority
                                )

                                .recommendedProblemCount(
                                        recommendedProblemCount
                                )

                                .reason(
                                        reason
                                )

                                .recommendation(
                                        recommendation
                                )

                                .problems(
                                        recommendedProblems
                                )

                                .build();


                recommendations.add(response);
        }


        // =========================================================
        // SORT RECOMMENDATIONS
        //
        // HIGH
        // MEDIUM
        // LOW
        // =========================================================

        recommendations.sort(

                Comparator.comparingInt(

                        practiceRecommendation ->

                                switch (
                                        practiceRecommendation.getPriority()
                                ) {

                                        case "HIGH" -> 1;

                                        case "MEDIUM" -> 2;

                                        default -> 3;
                                }
                )
        );


        return recommendations;
        }

        @Override
        @Transactional(readOnly = true)
        public List<SolutionEvolutionResponse> getSolutionEvolution(
                Long problemId,
                String userEmail) {

        User user =
                userRepository
                        .findByEmail(userEmail)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found."
                                )
                        );


        List<Submission> submissions =
                submissionRepository
                        .findByUserIdAndProblemIdOrderByCreatedAtAsc(
                                user.getId(),
                                problemId
                        );


        if (submissions.isEmpty()) {

        return List.of();
        }


        List<SolutionEvolutionResponse> timeline =
                new ArrayList<>();


        for (int index = 0;
                index < submissions.size();
                index++) {

                Submission submission =
                        submissions.get(index);


                Submission previousSubmission =
                        index > 0
                                ? submissions.get(index - 1)
                                : null;


                AiAnalysis aiAnalysis =
                        aiAnalysisRepository
                                .findBySubmissionId(
                                        submission.getId()
                                )
                                .orElse(null);


                // =====================================================
                // CALCULATE TEST CASE CHANGE
                // =====================================================

                int currentPassedTestCases =
                        submission.getPassedTestCases() != null
                                ? submission.getPassedTestCases()
                                : 0;


                int previousPassedTestCases =
                        previousSubmission != null
                                && previousSubmission.getPassedTestCases() != null

                                ? previousSubmission.getPassedTestCases()

                                : 0;


                int passedTestCasesChange =
                        previousSubmission == null
                                ? 0
                                : currentPassedTestCases
                                - previousPassedTestCases;


                // =====================================================
                // CALCULATE EVOLUTION STATUS
                // =====================================================

                String evolutionStatus;

                Boolean improvedFromPreviousAttempt;

                String evolutionMessage;


                if (previousSubmission == null) {

                evolutionStatus =
                        "FIRST_ATTEMPT";

                improvedFromPreviousAttempt =
                        false;

                evolutionMessage =
                        "This is the developer's first attempt at this problem.";

                } else if (
                        submission.getStatus()
                                == SubmissionStatus.ACCEPTED
                        &&
                        previousSubmission.getStatus()
                                != SubmissionStatus.ACCEPTED
                ) {

                evolutionStatus =
                        "SOLVED";

                improvedFromPreviousAttempt =
                        true;

                evolutionMessage =
                        "The developer successfully solved the problem after improving the previous solution.";

                } else if (passedTestCasesChange > 0) {

                evolutionStatus =
                        "IMPROVED";

                improvedFromPreviousAttempt =
                        true;

                evolutionMessage =
                        "The solution improved and passed more test cases than the previous attempt.";

                } else if (passedTestCasesChange < 0) {

                evolutionStatus =
                        "REGRESSED";

                improvedFromPreviousAttempt =
                        false;

                evolutionMessage =
                        "The solution regressed and passed fewer test cases than the previous attempt.";

                } else if (
                        submission.getStatus()
                                != previousSubmission.getStatus()
                ) {

                evolutionStatus =
                        "STATUS_CHANGED";

                improvedFromPreviousAttempt =
                        false;

                evolutionMessage =
                        "The submission status changed, but the number of passed test cases did not improve.";

                } else {

                evolutionStatus =
                        "NO_CHANGE";

                improvedFromPreviousAttempt =
                        false;

                evolutionMessage =
                        "No measurable improvement was detected from the previous attempt.";
                }


                // =====================================================
                // BUILD TIMELINE RESPONSE
                // =====================================================

                SolutionEvolutionResponse response =
                        SolutionEvolutionResponse
                                .builder()

                                .submissionId(
                                        submission.getId()
                                )

                                .attemptNumber(
                                        index + 1
                                )

                                .status(
                                        submission.getStatus()
                                )

                                .language(
                                        submission.getLanguage()
                                )

                                .passedTestCases(
                                        submission.getPassedTestCases()
                                )

                                .totalTestCases(
                                        submission.getTotalTestCases()
                                )

                                .executionTime(
                                        submission.getExecutionTime()
                                )

                                .memoryUsed(
                                        submission.getMemoryUsed()
                                )

                                .failedOnHiddenTest(
                                        submission.getFailedOnHiddenTest()
                                )

                                .sourceCode(
                                        submission.getSourceCode()
                                )

                                .aiExplanation(
                                        aiAnalysis != null
                                                ? aiAnalysis.getExplanation()
                                                : null
                                )

                                .aiHint(
                                        aiAnalysis != null
                                                ? aiAnalysis.getHint()
                                                : null
                                )

                                .conceptToStudy(
                                        aiAnalysis != null
                                                ? aiAnalysis.getConceptToStudy()
                                                : null
                                )

                                .evolutionStatus(
                                        evolutionStatus
                                )

                                .passedTestCasesChange(
                                        passedTestCasesChange
                                )

                                .improvedFromPreviousAttempt(
                                        improvedFromPreviousAttempt
                                )

                                .evolutionMessage(
                                        evolutionMessage
                                )

                                .createdAt(
                                        submission.getCreatedAt()
                                )

                                .build();


                timeline.add(response);
        }


        return timeline;
        }
        
        @Override
        @Transactional(readOnly = true)
        public HintDependencyScoreResponse getMyHintDependencyScore(
                String userEmail) {

        User user =
                userRepository
                        .findByEmail(userEmail)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found."
                                )
                        );

        Long userId = user.getId();


        // ==================================================
        // GET REAL DATABASE STATISTICS
        // ==================================================

        long totalSubmissions =
                submissionRepository.countByUserId(userId);

        long totalProblemsAttempted =
                submissionRepository
                        .countDistinctProblemsAttemptedByUserId(
                                userId
                        );

        long problemsWithHints =
                aiProgressiveHintRepository
                        .countDistinctProblemsWithHintsByUserId(
                                userId
                        );

        long totalHintsUsed =
                aiProgressiveHintRepository
                        .countBySubmissionUserId(
                                userId
                        );


        // ==================================================
        // GET HINT USAGE BY LEVEL
        // ==================================================

        List<Object[]> hintLevelCounts =
                aiProgressiveHintRepository
                        .countHintsByLevelForUser(
                                userId
                        );

        long level1HintsUsed = 0;
        long level2HintsUsed = 0;
        long level3HintsUsed = 0;
        long level4HintsUsed = 0;


        for (Object[] row : hintLevelCounts) {

                Integer level =
                        ((Number) row[0]).intValue();

                long count =
                        ((Number) row[1]).longValue();


                switch (level) {

                case 1 ->
                        level1HintsUsed = count;

                case 2 ->
                        level2HintsUsed = count;

                case 3 ->
                        level3HintsUsed = count;

                case 4 ->
                        level4HintsUsed = count;

                default -> {
                        // Ignore invalid hint levels.
                }
                }
        }


        // ==================================================
        // 1. CALCULATE HINT USAGE RATE
        //
        // Measures:
        // On what percentage of attempted problems
        // did the developer use AI hints?
        // ==================================================

        double hintUsageRate =
                totalProblemsAttempted == 0
                        ? 0
                        : (
                                (double) problemsWithHints
                                        / totalProblemsAttempted
                        ) * 100;


        // ==================================================
        // 2. CALCULATE AVERAGE HINT STRENGTH
        //
        // Level 1 = 25%
        // Level 2 = 50%
        // Level 3 = 75%
        // Level 4 = 100%
        //
        // This measures how strong the requested hints were.
        // ==================================================

        long weightedHintPoints =
                (level1HintsUsed * 1)
                        + (level2HintsUsed * 2)
                        + (level3HintsUsed * 3)
                        + (level4HintsUsed * 4);


        double averageHintStrength =
                totalHintsUsed == 0
                        ? 0
                        : (
                                (double) weightedHintPoints
                                        / (totalHintsUsed * 4)
                        ) * 100;


        // ==================================================
        // 3. CALCULATE HINT FREQUENCY
        //
        // Measures how frequently hints are requested
        // compared with total submissions.
        //
        // We cap it at 100.
        // ==================================================

        double hintFrequency =
                totalSubmissions == 0
                        ? 0
                        : (
                                (double) totalHintsUsed
                                        / totalSubmissions
                        ) * 100;


        hintFrequency =
                Math.min(
                        hintFrequency,
                        100
                );


        // ==================================================
        // 4. FINAL DEPENDENCY SCORE
        //
        // Hint Usage Rate      = 50%
        // Average Hint Strength = 30%
        // Hint Frequency        = 20%
        // ==================================================

        double calculatedScore =
                (hintUsageRate * 0.50)
                        + (averageHintStrength * 0.30)
                        + (hintFrequency * 0.20);


        int dependencyScore =
                (int) Math.round(
                        calculatedScore
                );


        dependencyScore =
                Math.max(
                        0,
                        Math.min(
                                dependencyScore,
                                100
                        )
                );


        // ==================================================
        // DETERMINE DEPENDENCY LEVEL
        // ==================================================

        String dependencyLevel;
        String message;


        if (dependencyScore <= 20) {

                dependencyLevel =
                        "LOW";

                message =
                        "You solve most problems independently and use AI hints only when needed.";

        } else if (dependencyScore <= 40) {

                dependencyLevel =
                        "HEALTHY";

                message =
                        "You use AI hints as learning support while maintaining good independent problem-solving habits.";

        } else if (dependencyScore <= 60) {

                dependencyLevel =
                        "MODERATE";

                message =
                        "You sometimes rely on AI hints. Try spending more time solving independently before requesting stronger hints.";

        } else if (dependencyScore <= 80) {

                dependencyLevel =
                        "HIGH";

                message =
                        "You frequently rely on AI hints. Try attempting more problems independently before using progressive guidance.";

        } else {

                dependencyLevel =
                        "VERY_HIGH";

                message =
                        "Your current hint usage suggests strong AI dependency. Focus on independent problem-solving before requesting hints.";
        }


        // ==================================================
        // BUILD RESPONSE
        // ==================================================

        return HintDependencyScoreResponse
                .builder()

                .totalSubmissions(
                        totalSubmissions
                )

                .totalProblemsAttempted(
                        totalProblemsAttempted
                )

                .problemsWithHints(
                        problemsWithHints
                )

                .totalHintsUsed(
                        totalHintsUsed
                )

                .level1HintsUsed(
                        level1HintsUsed
                )

                .level2HintsUsed(
                        level2HintsUsed
                )

                .level3HintsUsed(
                        level3HintsUsed
                )

                .level4HintsUsed(
                        level4HintsUsed
                )

                .hintUsageRate(
                        roundPercentage(
                                hintUsageRate
                        )
                )

                .dependencyScore(
                        dependencyScore
                )

                .dependencyLevel(
                        dependencyLevel
                )

                .message(
                        message
                )

                .build();
        }
        private double roundPercentage(double value) {
                return Math.round(value * 100.0) / 100.0;
        }

        private Submission getOwnedSubmission(
            Long submissionId,
            String userEmail) {
        
                
        Submission submission =
                submissionRepository
                        .findById(submissionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Submission not found with id: "
                                                + submissionId
                                ));


        if (!submission
                .getUser()
                .getEmail()
                .equals(userEmail)) {

            throw new ResourceNotFoundException(
                    "Submission not found with id: "
                            + submissionId
            );
        }


        return submission;
    }


    // =========================================================
    // MAP ANALYSIS RESPONSE
    // =========================================================

    private AiMentorResponse mapToResponse(
            AiAnalysis aiAnalysis) {

        return AiMentorResponse.builder()

                .submissionId(
                        aiAnalysis
                                .getSubmission()
                                .getId()
                )

                .explanation(
                        aiAnalysis.getExplanation()
                )

                .hint(
                        aiAnalysis.getHint()
                )

                .conceptToStudy(
                        aiAnalysis.getConceptToStudy()
                )

                .build();
    }


    // =========================================================
    // INITIAL ANALYSIS PROMPT
    // =========================================================

        private String buildPrompt(
                Submission submission,
                String personalizedLearningContext) {

        return """
                You are an AI coding mentor.

                Analyze the student's coding submission.

                Help the student understand their mistake without
                immediately providing the complete corrected solution.
                
                PERSONALIZED DEVELOPER LEARNING CONTEXT:

                %s

                Use this developer learning context to adapt your explanation.

                If the developer has recurring mistake patterns relevant to
                the current submission, connect the current mistake to that history.

                Give more guidance for weak areas.

                Avoid unnecessary explanations for areas where the developer
                has demonstrated stronger understanding.

                Do not invent learning history.

                PROBLEM TITLE:
                %s


                PROBLEM DESCRIPTION:
                %s


                PROGRAMMING LANGUAGE:
                %s


                STUDENT SOURCE CODE:
                %s


                SUBMISSION STATUS:
                %s


                ERROR MESSAGE:
                %s


                PASSED TEST CASES:
                %s


                TOTAL TEST CASES:
                %s


                FAILED ON HIDDEN TEST:
                %s


                IMPORTANT SECURITY RULES:

                1. Never reveal hidden test case input.

                2. Never reveal hidden expected output.

                3. Never guess or reconstruct hidden test case data.

                4. Do not provide the complete corrected source code.

                5. Explain the student's mistake clearly.

                6. Give one useful hint.

                7. Mention the main programming concept
                   the student should study.


                Return ONLY valid JSON.

                Do not use markdown.

                Do not add text before or after the JSON.


                Use exactly this JSON structure:

                {
                  "explanation": "clear explanation of the mistake",
                  "hint": "a useful hint for the student",
                  "conceptToStudy": "main programming concept"
                }
                """
                .formatted(

                        personalizedLearningContext,
                        submission
                                .getProblem()
                                .getTitle(),

                        submission
                                .getProblem()
                                .getDescription(),

                        submission
                                .getLanguage(),

                        submission
                                .getSourceCode(),

                        submission
                                .getStatus(),

                        submission
                                .getErrorMessage(),

                        submission
                                .getPassedTestCases(),

                        submission
                                .getTotalTestCases(),

                        submission
                                .getFailedOnHiddenTest()
                );
    }


    // =========================================================
    // CHAT PROMPT
    // =========================================================

private String buildChatPrompt(
        Submission submission,
        AiAnalysis aiAnalysis,
        List<AiChatMessage> chatHistory,
        String userMessage,
        String personalizedLearningContext) {


    StringBuilder conversationHistory =
            new StringBuilder();


    for (AiChatMessage chatMessage : chatHistory) {

        conversationHistory
                .append(chatMessage.getRole())
                .append(": ")
                .append(chatMessage.getContent())
                .append("\n\n");
    }


    return """
            You are an adaptive AI coding mentor.

            Continue helping the student understand their coding problem.

            Your response must be personalized using:

            1. The developer's learning history.
            2. Previous coding mistakes.
            3. Weak and strong programming concepts.
            4. Current submission information.
            5. Initial AI analysis.
            6. Previous conversation.


            PERSONALIZED DEVELOPER LEARNING CONTEXT:

            %s


            Use this learning context carefully.

            If the current question relates to a recurring mistake,
            remind the developer that they have struggled with this
            pattern before and explain how to avoid repeating it.

            Give more detailed guidance for weak concepts.

            Avoid unnecessary explanations for concepts where the
            developer has demonstrated stronger understanding.

            Do not invent learning history.

            Do not mention mistake history unless it is relevant
            to the current question or submission.


            PROBLEM TITLE:

            %s


            PROBLEM DESCRIPTION:

            %s


            PROGRAMMING LANGUAGE:

            %s


            STUDENT SOURCE CODE:

            %s


            SUBMISSION STATUS:

            %s


            ERROR MESSAGE:

            %s


            INITIAL AI EXPLANATION:

            %s


            INITIAL AI HINT:

            %s


            CONCEPT TO STUDY:

            %s


            PREVIOUS CONVERSATION:

            %s


            CURRENT STUDENT MESSAGE:

            %s


            IMPORTANT RULES:

            1. Answer the student's current question clearly.

            2. Use relevant developer learning history to personalize
               the response.

            3. Remember and use the previous conversation.

            4. Do not unnecessarily repeat explanations already given.

            5. Use simple and educational explanations.

            6. Never reveal hidden test case input.

            7. Never reveal hidden expected output.

            8. Never guess or reconstruct hidden test case data.

            9. Do not immediately provide the complete corrected solution.

            10. Guide the student toward understanding the solution.

            11. If the student repeatedly struggles with a relevant concept,
                explain the underlying pattern they should improve.

            12. Do not invent developer history or claim that the developer
                made mistakes that are not present in the provided context.


            Return only the mentor's conversational response.
            """
            .formatted(

                    personalizedLearningContext,

                    submission
                            .getProblem()
                            .getTitle(),

                    submission
                            .getProblem()
                            .getDescription(),

                    submission
                            .getLanguage(),

                    submission
                            .getSourceCode(),

                    submission
                            .getStatus(),

                    submission
                            .getErrorMessage(),

                    aiAnalysis
                            .getExplanation(),

                    aiAnalysis
                            .getHint(),

                    aiAnalysis
                            .getConceptToStudy(),

                    conversationHistory.toString(),

                    userMessage
            );
}

    // =========================================================
    // PROGRESSIVE HINT PROMPT
    // =========================================================

private String buildProgressiveHintPrompt(
        Submission submission,
        AiAnalysis aiAnalysis,
        Integer level,
        String personalizedLearningContext) {

    String levelInstruction =
            switch (level) {

                case 1 ->
                        """
                        Give a small conceptual hint.

                        Adapt the hint to the developer's learning history.

                        If the developer is weak in a relevant concept,
                        gently guide them toward recognizing that concept.

                        Do not explain the full approach.

                        Help the student think in the correct direction.
                        """;

                case 2 ->
                        """
                        Give a stronger personalized hint.

                        Explain the key observation needed to solve the problem.

                        If a relevant recurring mistake exists, help the developer
                        recognize the mistake pattern without directly revealing
                        the complete solution.

                        Do not provide complete code.
                        """;

                case 3 ->
                        """
                        Explain the main programming concept and algorithmic approach.

                        Adapt the explanation according to the developer's
                        previous mistake patterns and skill history.

                        Give clear step-by-step guidance.

                        Do not provide complete source code.
                        """;

                case 4 ->
                        """
                        Provide personalized pseudocode-level guidance.

                        Explain the solution steps clearly enough for the student
                        to implement the solution themselves.

                        Use relevant developer learning history to emphasize
                        concepts where the developer needs improvement.

                        Do not provide complete compilable source code.
                        """;

                default ->
                        throw new IllegalArgumentException(
                                "Invalid hint level."
                        );
            };


    return """
            You are an adaptive AI coding mentor.

            Give the student progressive guidance for their submission.

            Your teaching approach must adapt to the developer's
            real coding history and AI mistake memory.


            PERSONALIZED DEVELOPER LEARNING CONTEXT:

            %s


            Use this learning context carefully.

            If the current problem relates to a recurring mistake,
            guide the developer toward recognizing that pattern.

            Give more guidance for weak concepts.

            Avoid unnecessary explanations for concepts where the
            developer has demonstrated stronger understanding.

            Do not invent learning history.

            Do not mention previous mistakes unless they are relevant
            to the current problem or hint.


            PROBLEM TITLE:

            %s


            PROBLEM DESCRIPTION:

            %s


            PROGRAMMING LANGUAGE:

            %s


            STUDENT SOURCE CODE:

            %s


            SUBMISSION STATUS:

            %s


            ERROR MESSAGE:

            %s


            INITIAL EXPLANATION:

            %s


            INITIAL HINT:

            %s


            CONCEPT TO STUDY:

            %s


            CURRENT HINT LEVEL:

            %s


            LEVEL INSTRUCTION:

            %s


            IMPORTANT RULES:

            1. Follow the requested hint level carefully.

            2. Personalize the hint using relevant developer learning history.

            3. If the developer repeatedly struggles with a relevant concept,
               guide them toward understanding the underlying pattern.

            4. Do not unnecessarily repeat the initial hint.

            5. Never reveal hidden test case input.

            6. Never reveal hidden expected output.

            7. Never guess or reconstruct hidden test case data.

            8. Do not provide complete compilable source code.

            9. Help the student learn and solve the problem themselves.

            10. Keep the response focused and educational.

            11. Do not invent previous mistakes or developer history.


            Return only the mentor's response.
            """
            .formatted(

                    personalizedLearningContext,

                    submission
                            .getProblem()
                            .getTitle(),

                    submission
                            .getProblem()
                            .getDescription(),

                    submission
                            .getLanguage(),

                    submission
                            .getSourceCode(),

                    submission
                            .getStatus(),

                submission.getErrorMessage() == null
                        ? "No error message available."
                        : submission.getErrorMessage(),

                aiAnalysis.getExplanation() == null
                        ? "No initial explanation available."
                        : aiAnalysis.getExplanation(),

                aiAnalysis.getHint() == null
                        ? "No initial hint available."
                        : aiAnalysis.getHint(),

                aiAnalysis.getConceptToStudy() == null
                        ? "No specific concept identified."
                        : aiAnalysis.getConceptToStudy(),

                level,

                levelInstruction
            );
}

    // =========================================================
    // MISTAKE DETECTION PROMPT
    // =========================================================

    private String buildMistakeDetectionPrompt(
            Submission submission,
            AiAnalysis aiAnalysis) {

        return """
                You are an AI system that analyzes coding mistakes.

                Analyze the student's incorrect submission and identify
                the most important technical mistakes.

                Do not create duplicate mistakes that describe the same issue.

                Return between 1 and 3 important mistakes.


                PROBLEM TITLE:
                %s


                PROBLEM DESCRIPTION:
                %s


                PROGRAMMING LANGUAGE:
                %s


                STUDENT SOURCE CODE:
                %s


                SUBMISSION STATUS:
                %s


                ERROR MESSAGE:
                %s


                AI EXPLANATION:
                %s


                CONCEPT TO STUDY:
                %s


                ALLOWED MISTAKE TYPES:

                SYNTAX_ERROR
                WRONG_DATA_STRUCTURE
                WRONG_ALGORITHM
                LOGIC_ERROR
                BOUNDARY_CONDITION
                EDGE_CASE_MISSED
                TIME_COMPLEXITY
                SPACE_COMPLEXITY
                INCORRECT_INITIALIZATION
                LOOP_ERROR
                RECURSION_ERROR
                BASE_CASE_ERROR
                NULL_HANDLING
                INPUT_OUTPUT_ERROR
                OTHER


                ALLOWED SEVERITY VALUES:

                LOW
                MEDIUM
                HIGH


                IMPORTANT RULES:

                1. Return only mistakes clearly supported by the submission.

                2. Do not invent mistakes.

                3. Do not reveal hidden test cases.

                4. Keep descriptions concise and specific.

                5. Return ONLY valid JSON.

                6. Do not use markdown.

                7. Do not add text before or after JSON.


                Use exactly this structure:

                {
                  "mistakes": [
                    {
                      "mistakeType": "LOGIC_ERROR",
                      "concept": "Stack",
                      "description": "Concise explanation of the mistake",
                      "severity": "HIGH"
                    }
                  ]
                }
                """
                .formatted(

                        submission
                                .getProblem()
                                .getTitle(),

                        submission
                                .getProblem()
                                .getDescription(),

                        submission
                                .getLanguage(),

                        submission
                                .getSourceCode(),

                        submission
                                .getStatus(),

                        submission
                                .getErrorMessage(),

                        aiAnalysis
                                .getExplanation(),

                        aiAnalysis
                                .getConceptToStudy()
                );
    }

        private void ensureAiGuidanceAllowed(
                Submission submission) {

        boolean independentModeActive =
                independentSolveSessionRepository
                        .existsByUserIdAndProblemIdAndActiveTrue(
                                submission.getUser().getId(),
                                submission.getProblem().getId()
                        );

        if (independentModeActive) {

                throw new IllegalStateException(
                        "AI guidance is locked while Independent Solve Mode is active for this problem."
                );
        }
        }

        @Override
        @Transactional(readOnly = true)
        public PersonalizedLearningPlanResponse getMyPersonalizedLearningPlan(
                String userEmail) {

        // =========================================================
        // FIND LOGGED-IN USER
        // =========================================================

        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found."
                        )
                );


        // =========================================================
        // REUSE EXISTING FEATURES
        // =========================================================

        List<ConceptGrowthResponse> conceptGrowth =
                getMyConceptGrowth(userEmail);

        List<PracticeRecommendationResponse> practiceRecommendations =
                getMyPracticeRecommendations(userEmail);

        List<DeveloperSkillResponse> developerSkills =
                getMyDeveloperSkillGraph(userEmail);

        HintDependencyScoreResponse hintDependency =
                getMyHintDependencyScore(userEmail);


        // =========================================================
        // CALCULATE INDEPENDENT SOLVE RATE
        // =========================================================

        long completedIndependentSessions =
                independentSolveSessionRepository
                        .countByUserIdAndActiveFalse(
                                user.getId()
                        );

        long successfulIndependentSessions =
                independentSolveSessionRepository
                        .countByUserIdAndActiveFalseAndSolvedIndependentlyTrue(
                                user.getId()
                        );

        double independentSolveRate =
                completedIndependentSessions == 0
                        ? 0.0
                        : (
                                successfulIndependentSessions * 100.0
                                / completedIndependentSessions
                        );


        // =========================================================
        // FIND WEAK CONCEPTS
        // =========================================================

        List<String> weakConcepts =
                conceptGrowth
                        .stream()

                        .filter(growth ->
                                "REPEATING".equals(
                                        growth.getGrowthStatus()
                                )
                                ||
                                "IMPROVING".equals(
                                        growth.getGrowthStatus()
                                )
                        )

                        .map(
                                ConceptGrowthResponse::getConcept
                        )

                        .distinct()

                        .toList();


        // =========================================================
        // FIND STRENGTHS
        // =========================================================

        List<String> strengths =
                developerSkills
                        .stream()

                        .filter(skill ->
                                "STRONG".equals(
                                        skill.getSkillLevel()
                                )
                        )

                        .map(
                                DeveloperSkillResponse::getTopicName
                        )

                        .distinct()

                        .toList();


        // =========================================================
        // BUILD REVISION PRIORITIES
        // =========================================================

        List<String> revisionPriorities =
                practiceRecommendations
                        .stream()

                        .filter(recommendation ->
                                "HIGH".equals(
                                        recommendation.getPriority()
                                )
                                ||
                                "MEDIUM".equals(
                                        recommendation.getPriority()
                                )
                        )

                        .map(recommendation ->

                                recommendation.getConcept()
                                        + " - "
                                        + recommendation.getRecommendation()
                        )

                        .limit(5)

                        .toList();

        // =========================================================
        // BUILD PERSONALIZED RECOMMENDED PROBLEMS
        // =========================================================

        List<RecommendedProblemResponse> recommendedProblems =
                practiceRecommendations
                        .stream()

                        // Get problems from every recommendation
                        .flatMap(recommendation ->
                                recommendation
                                        .getProblems()
                                        .stream()
                        )

                        // Remove duplicate problems
                        .collect(
                                Collectors.toMap(

                                        RecommendedProblemResponse::getId,

                                        problem -> problem,

                                        (existingProblem, duplicateProblem) ->
                                                existingProblem,

                                        LinkedHashMap::new
                                )
                        )

                        .values()

                        .stream()

                        // Show maximum 5 problems
                        .limit(5)

                        .toList();

                        
                        // =========================================================
                        // CALCULATE READINESS SCORE
                //
                // SKILL SCORE            = MAX 40
                // INDEPENDENT SCORE      = MAX 30
                // LOW AI DEPENDENCY      = MAX 20
                // CONCEPT HEALTH         = MAX 10
                // =========================================================

                // =========================================================
                // NEW USER ACTIVITY CHECK
                // =========================================================

                long totalUserSubmissions =
                        submissionRepository.countByUserId(
                                user.getId()
                        );

                boolean hasLearningActivity =
                        totalUserSubmissions > 0
                        || completedIndependentSessions > 0
                        || !developerSkills.isEmpty()
                        || !conceptGrowth.isEmpty();    
                                            
                // -------------------------
                // SKILL COMPONENT
                // -------------------------

                double averageSkillScore =
                        developerSkills.isEmpty()
                                ? 0.0
                                : developerSkills
                                        .stream()
                                        .mapToInt(
                                                DeveloperSkillResponse::getSkillScore
                                        )
                                        .average()
                                        .orElse(0.0);

                int skillComponent =
                        (int) Math.round(
                                averageSkillScore * 0.40
                        );


                // -------------------------
                // INDEPENDENT COMPONENT
                // -------------------------

                int independentComponent =
                        (int) Math.round(
                                independentSolveRate * 0.30
                        );


                // -------------------------
                // HINT DEPENDENCY COMPONENT
                // -------------------------

                int hintDependencyScore =
                        hintDependency.getDependencyScore();

                int hintIndependenceComponent =
                        totalUserSubmissions == 0
                                ? 0
                                : (int) Math.round(
                                        (100 - hintDependencyScore) * 0.20
                                );


                // -------------------------
                // CONCEPT HEALTH COMPONENT
                // -------------------------

                int conceptHealthComponent =
                        conceptGrowth.isEmpty()
                                ? 0
                                : Math.max(
                                        0,
                                        10 - (weakConcepts.size() * 2)
                                );


                // -------------------------
                // FINAL READINESS SCORE
                // -------------------------

                int overallReadinessScore;

                if (!hasLearningActivity) {

                        overallReadinessScore = 0;

                } else {

                        overallReadinessScore =
                                skillComponent
                                        + independentComponent
                                        + hintIndependenceComponent
                                        + conceptHealthComponent;

                        overallReadinessScore =
                                Math.max(
                                        0,
                                        Math.min(
                                                overallReadinessScore,
                                                100
                                        )
                                );
                }

                overallReadinessScore =
                        Math.max(
                                0,
                                Math.min(
                                        overallReadinessScore,
                                        100
                                )
                        );

        // =========================================================
        // DETERMINE LEARNING LEVEL
        // =========================================================

        String learningLevel;

        if (!hasLearningActivity) {

                learningLevel = "NOT_STARTED";

        } else if (overallReadinessScore >= 80) {

                learningLevel = "ADVANCED";

        } else if (overallReadinessScore >= 60) {

                learningLevel = "INTERMEDIATE";

        } else if (overallReadinessScore >= 40) {

                learningLevel = "DEVELOPING";

        } else {

                learningLevel = "FOUNDATION";
        }


        // =========================================================
        // DETERMINE RECOMMENDED ACTION
        // =========================================================

        String recommendedAction;

        if (!hasLearningActivity) {

                recommendedAction =
                        "Solve your first coding problem to begin building your personalized learning profile.";

        } else if (!revisionPriorities.isEmpty()) {

                recommendedAction =
                        "Start with your highest-priority revision topic: "
                                + practiceRecommendations
                                        .get(0)
                                        .getConcept()
                                + ".";

        } else if (hintDependencyScore >= 60) {

                recommendedAction =
                        "Practice solving problems independently before requesting AI hints.";

        } else if (independentSolveRate < 50.0) {

                recommendedAction =
                        "Complete more problems using Solve Without AI mode.";

        } else {

                recommendedAction =
                        "Continue solving new problems and maintain your current learning progress.";
        }

        // =========================================================
        // BUILD MESSAGE
        // =========================================================

        String message;

        if (!hasLearningActivity) {

                message =
                        "Your developer journey is ready to begin. Solve coding problems to build your personalized growth profile.";

        } else if (overallReadinessScore >= 80) {

                message =
                        "You are showing strong overall progress. Focus on maintaining your strengths and solving more advanced problems.";

        } else if (overallReadinessScore >= 60) {

                message =
                        "Your learning progress is good. Continue revising weaker concepts while increasing independent problem-solving.";

        } else if (overallReadinessScore >= 40) {

                message =
                        "You are making progress, but targeted revision and more independent practice will improve your readiness.";

        } else {

                message =
                        "Focus on your core weak concepts, reduce AI dependency, and practice consistently using guided revision.";
        }


        // =========================================================
        // BUILD RESPONSE
        // =========================================================

        return PersonalizedLearningPlanResponse
                .builder()

                .overallReadinessScore(
                        overallReadinessScore
                )

                .learningLevel(
                        learningLevel
                )

                .weakConcepts(
                        weakConcepts
                )

                .revisionPriorities(
                        revisionPriorities
                )

                .strengths(
                        strengths
                )
                
                .recommendedProblems(
                        recommendedProblems
                )

                .hintDependencyScore(
                        hintDependencyScore
                )
                
                .independentSolveRate(
                        Math.round(
                                independentSolveRate * 100.0
                        ) / 100.0
                )

                .recommendedAction(
                        recommendedAction
                )

                .message(
                        message
                )

                .build();
        }

        // =========================================================
        // GET MY ADAPTIVE MENTOR PROFILE
        // =========================================================

        @Override
        @Transactional(readOnly = true)
        public AdaptiveMentorProfileResponse getMyAdaptiveMentorProfile(
                String userEmail) {

        // =====================================================
        // GET PERSONALIZED LEARNING DATA
        // =====================================================

        PersonalizedLearningPlanResponse learningPlan =
                getMyPersonalizedLearningPlan(userEmail);

        HintDependencyScoreResponse hintDependency =
                getMyHintDependencyScore(userEmail);

        List<RecurringMistakeResponse> recurringMistakes =
                getMyRecurringMistakes(userEmail);


        // =====================================================
        // BUILD RECURRING MISTAKE LIST
        // =====================================================

        List<String> recurringMistakeNames =
                recurringMistakes
                        .stream()

                        .map(
                                recurringMistake ->
                                        recurringMistake
                                                .getMistakeType()
                                                .name()
                        )

                        .distinct()

                        .limit(5)

                        .toList();


        // =====================================================
        // DETERMINE MENTOR MODE
        // =====================================================

        String mentorMode;

        String teachingStrategy;


        if (
                hintDependency.getDependencyScore() >= 70
                ||
                learningPlan.getIndependentSolveRate() < 25
        ) {

                mentorMode = "STRICT";

                teachingStrategy =
                        "Encourage independent thinking first. "
                                + "Use questions and small progressive hints. "
                                + "Avoid revealing complete solutions too early.";

        } else if (
                hintDependency.getDependencyScore() >= 40
                ||
                learningPlan.getOverallReadinessScore() < 70
        ) {

                mentorMode = "GUIDED";

                teachingStrategy =
                        "Provide structured guidance based on the developer's "
                                + "weak concepts and recurring mistakes. "
                                + "Use progressive explanations before giving solutions.";

        } else {

                mentorMode = "SUPPORTIVE";

                teachingStrategy =
                        "Focus on advanced reasoning, optimization, "
                                + "alternative approaches, and deeper problem-solving skills.";

        }


        // =====================================================
        // BUILD MESSAGE
        // =====================================================

        String message =
                switch (mentorMode) {

                        case "STRICT" ->
                                "Your mentor will prioritize independent problem-solving "
                                        + "and reduce unnecessary AI assistance.";

                        case "GUIDED" ->
                                "Your mentor will provide structured guidance based on "
                                        + "your current learning progress.";

                        default ->
                                "Your mentor will focus on advanced improvement "
                                        + "and deeper problem-solving strategies.";
                };


        // =====================================================
        // BUILD RESPONSE
        // =====================================================

        return AdaptiveMentorProfileResponse
                .builder()

                .mentorMode(
                        mentorMode
                )

                .developerLevel(
                        learningPlan.getLearningLevel()
                )

                .hintDependencyScore(
                        hintDependency.getDependencyScore()
                )

                .independentSolveRate(
                        learningPlan.getIndependentSolveRate()
                )

                .weakConcepts(
                        learningPlan.getWeakConcepts()
                )

                .recurringMistakes(
                        recurringMistakeNames
                )

                .teachingStrategy(
                        teachingStrategy
                )

                .message(
                        message
                )

                .build();
        }    

        // =========================================================
        // GET MY PERSONALIZED REVISION PLAN
        // =========================================================

        @Override
        @Transactional(readOnly = true)
        public PersonalizedRevisionPlanResponse getMyPersonalizedRevisionPlan(
                String userEmail) {

        // =====================================================
        // GET USER
        // =====================================================

        User user =
                userRepository
                        .findByEmail(userEmail)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );


        // =====================================================
        // GET EXISTING LEARNING DATA
        // =====================================================

        List<ConceptGrowthResponse> conceptGrowth =
                getMyConceptGrowth(userEmail);


        List<PracticeRecommendationResponse> practiceRecommendations =
                getMyPracticeRecommendations(userEmail);


        // =====================================================
        // CHECK WHETHER USER HAS REAL ACTIVITY
        // =====================================================

        long totalSubmissions =
                submissionRepository
                        .countByUserId(
                                user.getId()
                        );


        boolean hasRevisionActivity =
                totalSubmissions > 0
                        || !conceptGrowth.isEmpty();


        // =====================================================
        // BUILD URGENT CONCEPTS
        // =====================================================

        List<String> urgentConcepts =
                conceptGrowth
                        .stream()

                        .filter(growth ->
                                "REPEATING".equals(
                                        growth.getGrowthStatus()
                                )
                        )

                        .map(
                                ConceptGrowthResponse::getConcept
                        )

                        .distinct()

                        .limit(5)

                        .toList();


        // =====================================================
        // BUILD IMPROVING CONCEPTS
        // =====================================================

        List<String> improvingConcepts =
                conceptGrowth
                        .stream()

                        .filter(growth ->
                                "IMPROVING".equals(
                                        growth.getGrowthStatus()
                                )
                        )

                        .map(
                                ConceptGrowthResponse::getConcept
                        )

                        .distinct()

                        .limit(5)

                        .toList();


        // =====================================================
        // BUILD MASTERED CONCEPTS
        // =====================================================

        List<String> masteredConcepts =
                conceptGrowth
                        .stream()

                        .filter(growth ->
                                "IMPROVED".equals(
                                        growth.getGrowthStatus()
                                )
                        )

                        .map(
                                ConceptGrowthResponse::getConcept
                        )

                        .distinct()

                        .limit(5)

                        .toList();


        // =====================================================
        // BUILD REVISION PROBLEMS
        // =====================================================

        List<RecommendedProblemResponse> revisionProblems =
                practiceRecommendations
                        .stream()

                        .filter(recommendation ->
                                "HIGH".equals(
                                        recommendation.getPriority()
                                )
                                ||
                                "MEDIUM".equals(
                                        recommendation.getPriority()
                                )
                        )

                        .flatMap(recommendation ->
                                recommendation
                                        .getProblems()
                                        .stream()
                        )

                        .collect(
                                Collectors.toMap(

                                        RecommendedProblemResponse::getId,

                                        problem -> problem,

                                        (
                                                existingProblem,
                                                duplicateProblem
                                        ) -> existingProblem,

                                        LinkedHashMap::new
                                )
                        )

                        .values()

                        .stream()

                        .limit(5)

                        .toList();


        // =====================================================
        // CALCULATE REVISION SCORE
        // =====================================================

        int revisionScore;


        if (!hasRevisionActivity) {

                revisionScore = 0;

        } else {

                revisionScore =
                        100
                                - (urgentConcepts.size() * 20)
                                - (improvingConcepts.size() * 10);


                revisionScore =
                        Math.max(
                                0,
                                Math.min(
                                        revisionScore,
                                        100
                                )
                        );
        }


        // =====================================================
        // DETERMINE REVISION LEVEL
        // =====================================================

        String revisionLevel;

        String recommendedAction;

        String message;


        if (!hasRevisionActivity) {

                revisionLevel =
                        "NOT_STARTED";


                recommendedAction =
                        "Solve coding problems to begin building your personalized revision history.";


                message =
                        "Your revision health will appear after you complete coding activities.";


        } else if (revisionScore < 40) {

                revisionLevel =
                        "URGENT";


                recommendedAction =
                        "Focus immediately on recurring weak concepts before learning new topics.";


                message =
                        "Your mistake history shows that focused revision is currently important.";


        } else if (revisionScore < 70) {

                revisionLevel =
                        "NEEDS_REVISION";


                recommendedAction =
                        "Continue practicing improving concepts and revisit recurring mistakes.";


                message =
                        "You are making progress, but some concepts still require revision.";


        } else {

                revisionLevel =
                        "HEALTHY";


                recommendedAction =
                        "Maintain your progress with occasional targeted revision.";


                message =
                        "Your current revision health is strong. Continue consistent practice.";
        }


        // =====================================================
        // BUILD RESPONSE
        // =====================================================

        return PersonalizedRevisionPlanResponse
                .builder()

                .revisionScore(
                        revisionScore
                )

                .revisionLevel(
                        revisionLevel
                )

                .urgentConcepts(
                        urgentConcepts
                )

                .improvingConcepts(
                        improvingConcepts
                )

                .masteredConcepts(
                        masteredConcepts
                )

                .revisionProblems(
                        revisionProblems
                )

                .recommendedAction(
                        recommendedAction
                )

                .message(
                        message
                )

                .build();
        }


        @Override
        @Transactional(readOnly = true)
        public GrowthReportResponse getMyGrowthReport(
                String userEmail) {

        // =====================================================
        // GET USER
        // =====================================================

        User user =
                userRepository
                        .findByEmail(userEmail)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        Long userId =
                user.getId();


        // =====================================================
        // GET LEARNING DATA
        // =====================================================

        PersonalizedLearningPlanResponse learningPlan =
                getMyPersonalizedLearningPlan(
                        userEmail
                );


        List<ConceptGrowthResponse> conceptGrowth =
                getMyConceptGrowth(
                        userEmail
                );


        DeveloperMistakeProfileResponse mistakeProfile =
                getMyDeveloperMistakeProfile(
                        userEmail
                );


        // =====================================================
        // GET REAL USER ACTIVITY
        // =====================================================

        long totalSubmissions =
                submissionRepository
                        .countByUserId(
                                userId
                        );


        long totalCompletedIndependentSessions =
                independentSolveSessionRepository
                        .countByUserIdAndActiveFalse(
                                userId
                        );


        long independentlySolvedProblems =
                independentSolveSessionRepository
                        .countByUserIdAndActiveFalseAndSolvedIndependentlyTrue(
                                userId
                        );


        // =====================================================
        // CHECK WHETHER USER HAS REAL ACTIVITY
        // =====================================================

        boolean hasGrowthActivity =
                totalSubmissions > 0
                        || totalCompletedIndependentSessions > 0
                        || !conceptGrowth.isEmpty();


        // =====================================================
        // GET LEARNING METRICS
        // =====================================================

        int hintDependencyScore =
                learningPlan
                        .getHintDependencyScore();


        double independentSolveRate =
                learningPlan
                        .getIndependentSolveRate();


        int overallGrowthScore;

        String developerLevel;


        if (!hasGrowthActivity) {

                overallGrowthScore = 0;

                developerLevel =
                        "NOT_STARTED";

        } else {

                overallGrowthScore =
                        learningPlan
                                .getOverallReadinessScore();

                developerLevel =
                        learningPlan
                                .getLearningLevel();
        }


        // =====================================================
        // RECURRING MISTAKES
        // =====================================================

        List<String> recurringMistakes =
                mistakeProfile
                        .getMistakeTypeBreakdown()
                        .entrySet()
                        .stream()

                        .filter(entry ->
                                entry.getValue() > 1
                        )

                        .sorted(
                                Map.Entry
                                        .<String, Long>
                                        comparingByValue()
                                        .reversed()
                        )

                        .map(
                                Map.Entry::getKey
                        )

                        .limit(5)

                        .toList();


        // =====================================================
        // ACHIEVEMENTS
        // =====================================================

        List<String> achievements =
                new ArrayList<>();


        /*
        * IMPORTANT:
        *
        * A completely new user must NOT receive
        * achievements simply because their scores
        * default to zero.
        */

        if (hasGrowthActivity) {


                // =================================================
                // INDEPENDENT PROBLEM SOLVER
                // =================================================

                if (independentlySolvedProblems >= 1) {

                achievements.add(
                        "Independent Problem Solver"
                );
                }


                // =================================================
                // STRONG INDEPENDENT THINKING
                //
                // Require actual completed independent sessions.
                // =================================================

                if (
                        totalCompletedIndependentSessions >= 3
                        &&
                        independentSolveRate >= 50
                ) {

                achievements.add(
                        "Strong Independent Thinking"
                );
                }


                // =================================================
                // LOW AI DEPENDENCY
                //
                // IMPORTANT:
                // Require enough real activity first.
                //
                // Otherwise:
                //
                // 0 submissions
                // 0 hints
                //
                // would incorrectly earn this achievement.
                // =================================================

                if (
                        totalSubmissions >= 5
                        &&
                        hintDependencyScore <= 30
                ) {

                achievements.add(
                        "Low AI Dependency"
                );
                }


                // =================================================
                // CONSISTENT DEVELOPER GROWTH
                // =================================================

                if (overallGrowthScore >= 70) {

                achievements.add(
                        "Consistent Developer Growth"
                );
                }


                // =================================================
                // CONCEPT MASTERY
                // =================================================

                if (
                        conceptGrowth
                                .stream()
                                .anyMatch(concept ->

                                        "MASTERED"
                                                .equalsIgnoreCase(
                                                        concept
                                                                .getGrowthStatus()
                                                )
                                )
                ) {

                achievements.add(
                        "Concept Mastery Achieved"
                );
                }
        }


        // =====================================================
        // GROWTH SUMMARY
        // =====================================================

        String growthSummary;


        if (!hasGrowthActivity) {

                growthSummary =
                        "Your developer journey is ready to begin. Solve coding problems to start building your personalized growth profile.";

        } else if (overallGrowthScore >= 80) {

                growthSummary =
                        "Excellent progress. Your coding skills, independent problem-solving ability, and learning consistency are developing strongly.";

        } else if (overallGrowthScore >= 60) {

                growthSummary =
                        "You are making steady progress. Continue strengthening weak concepts and solving more problems independently.";

        } else if (overallGrowthScore >= 40) {

                growthSummary =
                        "Your development journey is progressing, but consistent practice and reduced AI dependency will accelerate your growth.";

        } else {

                growthSummary =
                        "You are currently building your foundation. Focus on consistent problem-solving, concept revision, and independent practice.";
        }


        // =====================================================
        // RECOMMENDED NEXT ACTION
        // =====================================================

        String recommendedNextAction;


        if (!hasGrowthActivity) {

                recommendedNextAction =
                        "Solve your first coding problem to begin building your personalized developer profile.";

        } else {

                recommendedNextAction =
                        learningPlan
                                .getRecommendedAction();
        }


        // =====================================================
        // BUILD RESPONSE
        // =====================================================

        return GrowthReportResponse
                .builder()

                .overallGrowthScore(
                        overallGrowthScore
                )

                .developerLevel(
                        developerLevel
                )

                .hintDependencyScore(
                        hintDependencyScore
                )

                .independentSolveRate(
                        independentSolveRate
                )

                .totalCompletedIndependentSessions(
                        totalCompletedIndependentSessions
                )

                .independentlySolvedProblems(
                        independentlySolvedProblems
                )

                .conceptGrowth(
                        conceptGrowth
                )

                .recurringMistakes(
                        recurringMistakes
                )

                .achievements(
                        achievements
                )

                .growthSummary(
                        growthSummary
                )

                .recommendedNextAction(
                        recommendedNextAction
                )

                .build();
        }

        @Override
        @Transactional(readOnly = true)
        public PersonalizedInterviewProfileResponse
        getMyPersonalizedInterviewProfile(String userEmail) {

        // =====================================================
        // GET EXISTING PERSONALIZED DATA
        // =====================================================

        PersonalizedLearningPlanResponse learningPlan =
                getMyPersonalizedLearningPlan(userEmail);

        List<ConceptGrowthResponse> conceptGrowth =
                getMyConceptGrowth(userEmail);

        DeveloperMistakeProfileResponse mistakeProfile =
                getMyDeveloperMistakeProfile(userEmail);


        // =====================================================
        // BUILD FOCUS CONCEPTS
        // =====================================================

        List<String> focusConcepts =
                conceptGrowth
                        .stream()

                        .filter(concept ->
                                !"MASTERED".equalsIgnoreCase(
                                        concept.getGrowthStatus()
                                )
                        )

                        .sorted(
                                Comparator.comparingLong(
                                        ConceptGrowthResponse::getTotalMistakes
                                ).reversed()
                        )

                        .map(
                                ConceptGrowthResponse::getConcept
                        )

                        .distinct()

                        .limit(5)

                        .toList();


        // =====================================================
        // BUILD RECURRING MISTAKES
        // =====================================================

        List<String> recurringMistakes =
                mistakeProfile
                        .getMistakeTypeBreakdown()
                        .entrySet()
                        .stream()

                        .filter(entry ->
                                entry.getValue() > 1
                        )

                        .sorted(
                                Map.Entry
                                        .<String, Long>comparingByValue()
                                        .reversed()
                        )

                        .map(Map.Entry::getKey)

                        .limit(5)

                        .toList();


        // =====================================================
        // GET EXISTING METRICS
        // =====================================================

        int overallReadinessScore =
                learningPlan.getOverallReadinessScore();

        int hintDependencyScore =
                learningPlan.getHintDependencyScore();

        double independentSolveRate =
                learningPlan.getIndependentSolveRate();

        String developerLevel =
                learningPlan.getLearningLevel();


        // =====================================================
        // DETERMINE INTERVIEW LEVEL
        // =====================================================

        String interviewLevel;

        if (overallReadinessScore >= 75) {

                interviewLevel = "ADVANCED";

        } else if (overallReadinessScore >= 45) {

                interviewLevel = "INTERMEDIATE";

        } else {

                interviewLevel = "FOUNDATION";
        }


        // =====================================================
        // BUILD INTERVIEW STRATEGY
        // =====================================================

        String interviewStrategy;

        if (hintDependencyScore >= 60) {

                interviewStrategy =
                        "Prioritize independent reasoning questions and avoid providing early hints.";

        } else if (independentSolveRate < 40) {

                interviewStrategy =
                        "Focus on problem-solving confidence, structured thinking, and follow-up questions.";

        } else {

                interviewStrategy =
                        "Use balanced technical questions with increasing difficulty and deeper follow-up discussion.";
        }


        // =====================================================
        // BUILD RECOMMENDED ACTION + MESSAGE
        // =====================================================

        String recommendedAction;

        String message;


        if ("ADVANCED".equals(interviewLevel)) {

                recommendedAction =
                        "Practice advanced coding problems, optimization discussions, and complex technical follow-up questions.";

                message =
                        "Your current progress supports advanced personalized interview practice.";

        } else if ("INTERMEDIATE".equals(interviewLevel)) {

                recommendedAction =
                        "Practice medium-level coding problems and explain your approach, complexity, and design decisions clearly.";

                message =
                        "Your personalized interview will focus on strengthening technical depth and communication.";

        } else {

                recommendedAction =
                        "Strengthen core concepts and practice explaining basic problem-solving approaches without AI assistance.";

                message =
                        "Your personalized interview will focus on building strong technical foundations and independent reasoning.";
        }


        // =====================================================
        // BUILD RESPONSE
        // =====================================================

        return PersonalizedInterviewProfileResponse
                .builder()

                .interviewLevel(
                        interviewLevel
                )

                .developerLevel(
                        developerLevel
                )

                .overallReadinessScore(
                        overallReadinessScore
                )

                .hintDependencyScore(
                        hintDependencyScore
                )

                .independentSolveRate(
                        independentSolveRate
                )

                .focusConcepts(
                        focusConcepts
                )

                .recurringMistakes(
                        recurringMistakes
                )

                .interviewStrategy(
                        interviewStrategy
                )

                .recommendedAction(
                        recommendedAction
                )

                .message(
                        message
                )

                .build();
        }

}