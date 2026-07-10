package com.codementor.backend.service.impl;
import com.codementor.backend.dto.AiMistakeSummaryResponse;
import com.codementor.backend.entity.User;
import com.codementor.backend.repository.UserRepository;
import com.codementor.backend.entity.MistakeType;
import com.codementor.backend.ai.GeminiService;
import com.codementor.backend.dto.AiMentorChatMessageResponse;
import com.codementor.backend.dto.AiMentorChatResponse;
import com.codementor.backend.dto.AiMentorHintResponse;
import com.codementor.backend.dto.AiMentorResponse;
import com.codementor.backend.dto.AiMistakeDetectionResponse;
import com.codementor.backend.dto.AiMistakeItemResponse;
import com.codementor.backend.dto.AiMistakeResponse;
import com.codementor.backend.dto.ConceptGrowthResponse;
import com.codementor.backend.dto.PracticeRecommendationResponse;
import com.codementor.backend.entity.AiAnalysis;
import com.codementor.backend.entity.AiChatMessage;
import com.codementor.backend.entity.AiMistake;
import com.codementor.backend.entity.AiProgressiveHint;
import com.codementor.backend.entity.MistakeType;
import com.codementor.backend.entity.Submission;
import com.codementor.backend.entity.SubmissionStatus;
import com.codementor.backend.exception.ResourceNotFoundException;
import com.codementor.backend.repository.AiAnalysisRepository;
import com.codementor.backend.repository.AiChatMessageRepository;
import com.codementor.backend.repository.AiMistakeRepository;
import com.codementor.backend.repository.AiProgressiveHintRepository;
import com.codementor.backend.repository.SubmissionRepository;
import com.codementor.backend.service.AiMentorService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.codementor.backend.dto.RecurringMistakeResponse;
import com.codementor.backend.dto.DeveloperMistakeProfileResponse;
import com.codementor.backend.entity.MistakeSeverity;
import com.codementor.backend.util.ConceptNormalizer;
import com.codementor.backend.dto.PastMistakeRecallResponse;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Comparator;

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


        String prompt =
                buildPrompt(submission);


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


            /*
             * Automatically detect and save
             * structured mistakes.
             */

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


        String prompt =
                buildChatPrompt(
                        submission,
                        aiAnalysis,
                        chatHistory,
                        message
                );


        String aiResponse =
                geminiService.chat(prompt);


        AiChatMessage userChatMessage =
                AiChatMessage.builder()

                        .submission(submission)

                        .role("USER")

                        .content(message)

                        .build();


        aiChatMessageRepository.save(
                userChatMessage
        );


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

        Submission submission =
                getOwnedSubmission(
                        submissionId,
                        userEmail
                );


        if (level < 1 || level > 4) {

            throw new IllegalArgumentException(
                    "Hint level must be between 1 and 4."
            );
        }


        /*
         * Return saved hint instead of
         * calling Gemini again.
         */

        Optional<AiProgressiveHint> existingHint =
                aiProgressiveHintRepository
                        .findBySubmissionIdAndLevel(
                                submissionId,
                                level
                        );


        if (existingHint.isPresent()) {

            AiProgressiveHint savedHint =
                    existingHint.get();


            return AiMentorHintResponse.builder()

                    .submissionId(submissionId)

                    .level(savedHint.getLevel())

                    .response(savedHint.getResponse())

                    .build();
        }


        AiAnalysis aiAnalysis =
                aiAnalysisRepository
                        .findBySubmissionId(submissionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "AI analysis not found for submission id: "
                                                + submissionId
                                ));


        String prompt =
                buildProgressiveHintPrompt(
                        submission,
                        aiAnalysis,
                        level
                );


        String aiResponse =
                geminiService.chat(prompt);


        AiProgressiveHint progressiveHint =
                AiProgressiveHint.builder()

                        .submission(submission)

                        .level(level)

                        .response(aiResponse)

                        .build();


        aiProgressiveHintRepository.save(
                progressiveHint
        );


        return AiMentorHintResponse.builder()

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


        String aiResponse =
                geminiService.analyzeCode(prompt);


        try {

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

            throw new RuntimeException(
                    "Failed to detect submission mistakes: "
                            + exception.getMessage(),
                    exception
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
            Submission submission) {

        return """
                You are an AI coding mentor.

                Analyze the student's coding submission.

                Help the student understand their mistake without
                immediately providing the complete corrected solution.


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
            String userMessage) {


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
                You are an AI coding mentor.

                Continue helping the student understand their coding problem.

                Use the problem information, submitted code,
                initial AI analysis, and previous conversation.


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

                2. Remember and use the previous conversation.

                3. Do not unnecessarily repeat explanations already given.

                4. Use simple and educational explanations.

                5. Never reveal hidden test case input.

                6. Never reveal hidden expected output.

                7. Never guess or reconstruct hidden test case data.

                8. Do not immediately provide the complete corrected solution.

                9. Guide the student toward understanding the solution.


                Return only the mentor's conversational response.
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
            Integer level) {

        String levelInstruction =
                switch (level) {

                    case 1 ->
                            """
                            Give a small conceptual hint.
                            Do not explain the full approach.
                            Help the student think in the correct direction.
                            """;

                    case 2 ->
                            """
                            Give a stronger hint.
                            Explain the key observation needed to solve the problem.
                            Do not provide complete code.
                            """;

                    case 3 ->
                            """
                            Explain the main programming concept and algorithmic approach.
                            Give clear step-by-step guidance.
                            Do not provide complete source code.
                            """;

                    case 4 ->
                            """
                            Provide pseudocode-level guidance.
                            Explain the solution steps clearly enough for the student
                            to implement the solution themselves.
                            Do not provide complete compilable source code.
                            """;

                    default ->
                            throw new IllegalArgumentException(
                                    "Invalid hint level."
                            );
                };


        return """
                You are an AI coding mentor.

                Give the student progressive guidance for their submission.


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

                2. Do not unnecessarily repeat the initial hint.

                3. Never reveal hidden test case input.

                4. Never reveal hidden expected output.

                5. Never guess or reconstruct hidden test case data.

                6. Do not provide complete compilable source code.

                7. Help the student learn and solve the problem themselves.

                8. Keep the response focused and educational.


                Return only the mentor's response.
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
                                .getHint(),

                        aiAnalysis
                                .getConceptToStudy(),

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
}