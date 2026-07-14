package com.codementor.backend.service.impl;

import com.codementor.backend.ai.GeminiService;
import com.codementor.backend.dto.InterviewQuestionResponse;
import com.codementor.backend.dto.InterviewSessionResponse;
import com.codementor.backend.dto.PersonalizedInterviewProfileResponse;
import com.codementor.backend.entity.InterviewQuestion;
import com.codementor.backend.entity.InterviewSession;
import com.codementor.backend.entity.User;
import com.codementor.backend.exception.ResourceNotFoundException;
import com.codementor.backend.repository.InterviewQuestionRepository;
import com.codementor.backend.repository.InterviewSessionRepository;
import com.codementor.backend.repository.UserRepository;
import com.codementor.backend.service.AiMentorService;
import com.codementor.backend.service.PersonalizedInterviewService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonalizedInterviewServiceImpl
        implements PersonalizedInterviewService {

    private final InterviewSessionRepository interviewSessionRepository;

    private final InterviewQuestionRepository interviewQuestionRepository;

    private final UserRepository userRepository;

    private final AiMentorService aiMentorService;

    private final GeminiService geminiService;

    private final ObjectMapper objectMapper;


    // =====================================================
    // START PERSONALIZED INTERVIEW
    // =====================================================

    @Override
    @Transactional
    public InterviewSessionResponse startInterview(
            String userEmail) {

        // =====================================================
        // GET USER
        // =====================================================

        User user =
                userRepository
                        .findByEmail(userEmail)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found."
                                )
                        );


        // =====================================================
        // PREVENT MULTIPLE ACTIVE INTERVIEWS
        // =====================================================

        interviewSessionRepository
                .findByUserIdAndActiveTrue(
                        user.getId()
                )
                .ifPresent(existingSession -> {

                    throw new IllegalStateException(
                            "You already have an active interview session."
                    );
                });


        // =====================================================
        // GET PERSONALIZED INTERVIEW PROFILE
        // =====================================================

        PersonalizedInterviewProfileResponse profile =
                aiMentorService
                        .getMyPersonalizedInterviewProfile(
                                userEmail
                        );


        // =====================================================
        // CREATE SESSION
        // =====================================================

        InterviewSession session =
                InterviewSession
                        .builder()

                        .user(user)

                        .interviewLevel(
                                profile.getInterviewLevel()
                        )

                        .developerLevel(
                                profile.getDeveloperLevel()
                        )

                        .active(true)

                        .currentQuestionNumber(1)

                        .totalQuestions(5)

                        .startedAt(
                                LocalDateTime.now()
                        )

                        .build();


        session =
                interviewSessionRepository.save(
                        session
                );


        // =====================================================
        // GENERATE FIRST PERSONALIZED QUESTION
        // =====================================================

        String prompt =
                buildFirstQuestionPrompt(
                        profile
                );


        String aiResponse =
                geminiService.analyzeCode(
                        prompt
                );


        InterviewQuestion generatedQuestion =
                parseQuestion(
                        aiResponse,
                        session
                );


        InterviewQuestion savedQuestion =
                interviewQuestionRepository.save(
                        generatedQuestion
                );


        // =====================================================
        // BUILD RESPONSE
        // =====================================================

        return InterviewSessionResponse
                .builder()

                .sessionId(
                        session.getId()
                )

                .interviewLevel(
                        session.getInterviewLevel()
                )

                .developerLevel(
                        session.getDeveloperLevel()
                )

                .active(
                        session.getActive()
                )

                .currentQuestionNumber(
                        session.getCurrentQuestionNumber()
                )

                .totalQuestions(
                        session.getTotalQuestions()
                )

                .startedAt(
                        session.getStartedAt()
                )

                .currentQuestion(
                        buildQuestionResponse(
                                savedQuestion
                        )
                )

                .message(
                        "Your personalized interview has started."
                )

                .build();
    }


    // =====================================================
    // BUILD FIRST QUESTION PROMPT
    // =====================================================

    private String buildFirstQuestionPrompt(
            PersonalizedInterviewProfileResponse profile) {

        return """
                You are a professional technical interviewer.

                Generate the FIRST question for a personalized
                software developer interview.

                Candidate profile:

                Interview Level:
                %s

                Developer Level:
                %s

                Overall Readiness Score:
                %d

                Hint Dependency Score:
                %d

                Independent Solve Rate:
                %.2f

                Focus Concepts:
                %s

                Recurring Mistakes:
                %s

                Interview Strategy:
                %s

                Requirements:

                1. Ask exactly ONE technical interview question.
                2. Match the candidate's current interview level.
                3. Focus on reasoning and explanation.
                4. Do not provide the answer.
                5. Do not provide hints.
                6. The question must be clear and suitable
                   for a technical interview.
                7. If focus concepts are empty, ask a strong
                   foundational problem-solving question.

                Return ONLY valid JSON.

                Use exactly this structure:

                {
                  "question": "question text",
                  "questionType": "TECHNICAL",
                  "concept": "main concept",
                  "difficulty": "FOUNDATION"
                }
                """
                .formatted(
                        profile.getInterviewLevel(),
                        profile.getDeveloperLevel(),
                        profile.getOverallReadinessScore(),
                        profile.getHintDependencyScore(),
                        profile.getIndependentSolveRate(),
                        profile.getFocusConcepts(),
                        profile.getRecurringMistakes(),
                        profile.getInterviewStrategy()
                );
    }


    // =====================================================
    // PARSE AI QUESTION
    // =====================================================

    private InterviewQuestion parseQuestion(
            String aiResponse,
            InterviewSession session) {

        try {

            JsonNode root =
                    objectMapper.readTree(
                            aiResponse
                    );


            return InterviewQuestion
                    .builder()

                    .interviewSession(
                            session
                    )

                    .questionNumber(1)

                    .question(
                            root.path("question")
                                    .asText()
                    )

                    .questionType(
                            root.path("questionType")
                                    .asText("TECHNICAL")
                    )

                    .concept(
                            root.path("concept")
                                    .asText("Problem Solving")
                    )

                    .difficulty(
                            root.path("difficulty")
                                    .asText(
                                            session.getInterviewLevel()
                                    )
                    )

                    .answered(false)

                    .createdAt(
                            LocalDateTime.now()
                    )

                    .build();

        } catch (Exception exception) {

            throw new RuntimeException(
                    "Failed to parse interview question: "
                            + exception.getMessage()
            );
        }
    }


    // =====================================================
    // BUILD QUESTION RESPONSE
    // =====================================================

    private InterviewQuestionResponse buildQuestionResponse(
            InterviewQuestion question) {

        return InterviewQuestionResponse
                .builder()

                .questionId(
                        question.getId()
                )

                .questionNumber(
                        question.getQuestionNumber()
                )

                .question(
                        question.getQuestion()
                )

                .questionType(
                        question.getQuestionType()
                )

                .concept(
                        question.getConcept()
                )

                .difficulty(
                        question.getDifficulty()
                )

                .answered(
                        question.getAnswered()
                )

                .answerScore(
                        question.getAnswerScore()
                )

                .aiFeedback(
                        question.getAiFeedback()
                )

                .strengths(
                        question.getStrengths()
                )

                .improvements(
                        question.getImprovements()
                )

                .build();
    }

    // =====================================================
    // SUBMIT ANSWER
    // =====================================================

    @Override
    @Transactional
    public InterviewSessionResponse submitAnswer(
            Long sessionId,
            Long questionId,
            String answer,
            String userEmail) {

        User user =
                userRepository
                        .findByEmail(userEmail)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found."
                                )
                        );


        InterviewSession session =
                interviewSessionRepository
                        .findByIdAndUserId(
                                sessionId,
                                user.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Interview session not found."
                                )
                        );


        if (!Boolean.TRUE.equals(session.getActive())) {

            throw new IllegalStateException(
                    "This interview session is already completed."
            );
        }


        InterviewQuestion question =
                interviewQuestionRepository
                        .findByIdAndInterviewSessionId(
                                questionId,
                                sessionId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Interview question not found."
                                )
                        );


        if (Boolean.TRUE.equals(question.getAnswered())) {

            throw new IllegalStateException(
                    "This interview question has already been answered."
            );
        }


        if (answer == null || answer.isBlank()) {

            throw new IllegalArgumentException(
                    "Interview answer cannot be empty."
            );
        }


        // =====================================================
        // EVALUATE ANSWER WITH GEMINI
        // =====================================================

        String evaluationPrompt =
                buildAnswerEvaluationPrompt(
                        session,
                        question,
                        answer
                );


        String aiResponse =
                geminiService.analyzeCode(
                        evaluationPrompt
                );


        evaluateAndUpdateQuestion(
                question,
                answer,
                aiResponse
        );


        interviewQuestionRepository.save(
                question
        );


        // =====================================================
        // COMPLETE INTERVIEW AFTER FINAL QUESTION
        // =====================================================

        if (question.getQuestionNumber()
                >= session.getTotalQuestions()) {

            session.setActive(false);

            session.setCompletedAt(
                    LocalDateTime.now()
            );

            session.setCurrentQuestionNumber(
                    session.getTotalQuestions()
            );


            completeInterview(
                    session
            );


            InterviewSession savedSession =
                    interviewSessionRepository.save(
                            session
                    );


            return buildSessionResponse(
                    savedSession,
                    question,
                    "Your personalized interview is complete."
            );
        }


        // =====================================================
        // GENERATE NEXT ADAPTIVE QUESTION
        // =====================================================

        int nextQuestionNumber =
                question.getQuestionNumber() + 1;


        String nextQuestionPrompt =
                buildNextQuestionPrompt(
                        session,
                        question,
                        nextQuestionNumber
                );


        String nextQuestionAiResponse =
                geminiService.analyzeCode(
                        nextQuestionPrompt
                );


        InterviewQuestion nextQuestion =
                parseAdaptiveQuestion(
                        nextQuestionAiResponse,
                        session,
                        nextQuestionNumber
                );


        InterviewQuestion savedNextQuestion =
                interviewQuestionRepository.save(
                        nextQuestion
                );


        session.setCurrentQuestionNumber(
                nextQuestionNumber
        );


        InterviewSession savedSession =
                interviewSessionRepository.save(
                        session
                );


        return buildSessionResponse(
                savedSession,
                savedNextQuestion,
                "Answer evaluated. Your next personalized question is ready."
        );
    }


    @Override
    @Transactional(readOnly = true)
    public InterviewSessionResponse getInterviewDetails(
            Long sessionId,
            String userEmail) {

        User user =
                userRepository
                        .findByEmail(userEmail)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found."
                                )
                        );


        InterviewSession session =
                interviewSessionRepository
                        .findByIdAndUserId(
                                sessionId,
                                user.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Interview session not found."
                                )
                        );


        var questions =
                interviewQuestionRepository
                        .findByInterviewSessionIdOrderByQuestionNumberAsc(
                                session.getId()
                        );


        var questionResponses =
                questions
                        .stream()

                        .map(
                                this::buildQuestionResponse
                        )

                        .toList();


        InterviewQuestionResponse currentQuestion = null;


        if (Boolean.TRUE.equals(session.getActive())) {

            currentQuestion =
                    questions
                            .stream()

                            .filter(question ->
                                    !Boolean.TRUE.equals(
                                            question.getAnswered()
                                    )
                            )

                            .findFirst()

                            .map(
                                    this::buildQuestionResponse
                            )

                            .orElse(null);
        }


        String message =
                Boolean.TRUE.equals(session.getActive())

                        ? "Your personalized interview is currently active."

                        : "Your personalized interview is complete.";


        return InterviewSessionResponse
                .builder()

                .sessionId(
                        session.getId()
                )

                .interviewLevel(
                        session.getInterviewLevel()
                )

                .developerLevel(
                        session.getDeveloperLevel()
                )

                .active(
                        session.getActive()
                )

                .currentQuestionNumber(
                        session.getCurrentQuestionNumber()
                )

                .totalQuestions(
                        session.getTotalQuestions()
                )

                .finalScore(
                        session.getFinalScore()
                )

                .finalFeedback(
                        session.getFinalFeedback()
                )

                .startedAt(
                        session.getStartedAt()
                )

                .completedAt(
                        session.getCompletedAt()
                )

                .currentQuestion(
                        currentQuestion
                )

                .questions(
                        questionResponses
                )

                .message(
                        message
                )

                .build();
    }

    // =====================================================
    // BUILD ANSWER EVALUATION PROMPT
    // =====================================================

    private String buildAnswerEvaluationPrompt(
            InterviewSession session,
            InterviewQuestion question,
            String answer) {

        return """
                You are a professional technical interviewer.

                Evaluate the candidate's answer.

                Interview Level:
                %s

                Question:
                %s

                Concept:
                %s

                Candidate Answer:
                %s

                Requirements:

                1. Give a score from 0 to 100.
                2. Evaluate technical correctness.
                3. Evaluate reasoning and clarity.
                4. Identify the candidate's strengths.
                5. Identify what should be improved.
                6. Give concise professional feedback.

                Return ONLY valid JSON.

                Use exactly this structure:

                {
                "score": 75,
                "feedback": "professional feedback",
                "strengths": "candidate strengths",
                "improvements": "areas to improve"
                }
                """
                .formatted(
                        session.getInterviewLevel(),
                        question.getQuestion(),
                        question.getConcept(),
                        answer
                );
    }


    // =====================================================
    // EVALUATE AND UPDATE QUESTION
    // =====================================================

    private void evaluateAndUpdateQuestion(
            InterviewQuestion question,
            String answer,
            String aiResponse) {

        try {

            JsonNode root =
                    objectMapper.readTree(
                            aiResponse
                    );


            int score =
                    Math.max(
                            0,
                            Math.min(
                                    root.path("score").asInt(0),
                                    100
                            )
                    );


            question.setUserAnswer(
                    answer
            );

            question.setAnswerScore(
                    score
            );

            question.setAiFeedback(
                    root.path("feedback")
                            .asText()
            );

            question.setStrengths(
                    root.path("strengths")
                            .asText()
            );

            question.setImprovements(
                    root.path("improvements")
                            .asText()
            );

            question.setAnswered(
                    true
            );

            question.setAnsweredAt(
                    LocalDateTime.now()
            );

        } catch (Exception exception) {

            throw new RuntimeException(
                    "Failed to evaluate interview answer: "
                            + exception.getMessage()
            );
        }
    }


    // =====================================================
    // BUILD NEXT QUESTION PROMPT
    // =====================================================

    private String buildNextQuestionPrompt(
            InterviewSession session,
            InterviewQuestion previousQuestion,
            int nextQuestionNumber) {

        return """
                You are conducting a personalized adaptive
                technical interview.

                Interview Level:
                %s

                Previous Question:
                %s

                Previous Concept:
                %s

                Candidate Score:
                %d

                Candidate Strengths:
                %s

                Candidate Improvements:
                %s

                Generate interview question number %d.

                Adaptive Rules:

                1. If the previous score is below 40,
                ask a clearer foundational question.

                2. If the previous score is between 40 and 74,
                ask a question of similar difficulty that
                tests deeper understanding.

                3. If the previous score is 75 or above,
                slightly increase the difficulty.

                4. Adapt the next question using the candidate's
                strengths and improvement areas.

                5. Ask exactly ONE question.

                6. Do not provide hints or answers.

                Return ONLY valid JSON.

                Use exactly this structure:

                {
                "question": "question text",
                "questionType": "TECHNICAL",
                "concept": "main concept",
                "difficulty": "FOUNDATION"
                }
                """
                .formatted(
                        session.getInterviewLevel(),
                        previousQuestion.getQuestion(),
                        previousQuestion.getConcept(),
                        previousQuestion.getAnswerScore(),
                        previousQuestion.getStrengths(),
                        previousQuestion.getImprovements(),
                        nextQuestionNumber
                );
    }


    // =====================================================
    // PARSE ADAPTIVE QUESTION
    // =====================================================

    private InterviewQuestion parseAdaptiveQuestion(
            String aiResponse,
            InterviewSession session,
            int questionNumber) {

        try {

            JsonNode root =
                    objectMapper.readTree(
                            aiResponse
                    );


            return InterviewQuestion
                    .builder()

                    .interviewSession(
                            session
                    )

                    .questionNumber(
                            questionNumber
                    )

                    .question(
                            root.path("question")
                                    .asText()
                    )

                    .questionType(
                            root.path("questionType")
                                    .asText("TECHNICAL")
                    )

                    .concept(
                            root.path("concept")
                                    .asText("Problem Solving")
                    )

                    .difficulty(
                            root.path("difficulty")
                                    .asText(
                                            session.getInterviewLevel()
                                    )
                    )

                    .answered(false)

                    .createdAt(
                            LocalDateTime.now()
                    )

                    .build();

        } catch (Exception exception) {

            throw new RuntimeException(
                    "Failed to parse adaptive interview question: "
                            + exception.getMessage()
            );
        }
    }


    // =====================================================
    // COMPLETE INTERVIEW
    // =====================================================

    private void completeInterview(
            InterviewSession session) {

        var questions =
                interviewQuestionRepository
                        .findByInterviewSessionIdOrderByQuestionNumberAsc(
                                session.getId()
                        );


        int finalScore =
                (int) Math.round(
                        questions
                                .stream()

                                .filter(question ->
                                        question.getAnswerScore() != null
                                )

                                .mapToInt(
                                        InterviewQuestion::getAnswerScore
                                )

                                .average()

                                .orElse(0)
                );


        String finalFeedback;

        if (finalScore >= 80) {

            finalFeedback =
                    "Excellent interview performance. You demonstrated strong technical reasoning and communication.";

        } else if (finalScore >= 60) {

            finalFeedback =
                    "Good interview performance. Continue strengthening technical depth and explanation clarity.";

        } else if (finalScore >= 40) {

            finalFeedback =
                    "Developing interview performance. Focus on structured reasoning and stronger technical foundations.";

        } else {

            finalFeedback =
                    "Continue building your technical foundations and practice explaining your problem-solving approach clearly.";
        }


        session.setFinalScore(
                finalScore
        );

        session.setFinalFeedback(
                finalFeedback
        );
    }


    // =====================================================
    // BUILD SESSION RESPONSE
    // =====================================================

    private InterviewSessionResponse buildSessionResponse(
            InterviewSession session,
            InterviewQuestion currentQuestion,
            String message) {

        return InterviewSessionResponse
                .builder()

                .sessionId(
                        session.getId()
                )

                .interviewLevel(
                        session.getInterviewLevel()
                )

                .developerLevel(
                        session.getDeveloperLevel()
                )

                .active(
                        session.getActive()
                )

                .currentQuestionNumber(
                        session.getCurrentQuestionNumber()
                )

                .totalQuestions(
                        session.getTotalQuestions()
                )

                .finalScore(
                        session.getFinalScore()
                )

                .finalFeedback(
                        session.getFinalFeedback()
                )

                .startedAt(
                        session.getStartedAt()
                )

                .completedAt(
                        session.getCompletedAt()
                )

                .currentQuestion(
                        buildQuestionResponse(
                                currentQuestion
                        )
                )

                .message(
                        message
                )

                .build();
    }

    // =====================================================
    // GET ACTIVE INTERVIEW
    // =====================================================

    @Override
    @Transactional(readOnly = true)
    public InterviewSessionResponse getActiveInterview(
            String userEmail) {

        User user =
                userRepository
                        .findByEmail(userEmail)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found."
                                )
                        );


        InterviewSession session =
                interviewSessionRepository
                        .findByUserIdAndActiveTrue(
                                user.getId()
                        )
                        .orElse(null);


        // No active interview is a normal state.
        if (session == null) {
            return null;
        }


        List<InterviewQuestion> questions =
                interviewQuestionRepository
                        .findByInterviewSessionIdOrderByQuestionNumberAsc(
                                session.getId()
                        );


        List<InterviewQuestionResponse> questionResponses =
                questions
                        .stream()
                        .map(this::buildQuestionResponse)
                        .toList();


        InterviewQuestionResponse currentQuestion =
                questions
                        .stream()
                        .filter(question ->
                                !Boolean.TRUE.equals(
                                        question.getAnswered()
                                )
                        )
                        .findFirst()
                        .map(this::buildQuestionResponse)
                        .orElse(null);


        return InterviewSessionResponse
                .builder()

                .sessionId(session.getId())

                .interviewLevel(
                        session.getInterviewLevel()
                )

                .developerLevel(
                        session.getDeveloperLevel()
                )

                .active(
                        session.getActive()
                )

                .currentQuestionNumber(
                        session.getCurrentQuestionNumber()
                )

                .totalQuestions(
                        session.getTotalQuestions()
                )

                .finalScore(
                        session.getFinalScore()
                )

                .finalFeedback(
                        session.getFinalFeedback()
                )

                .startedAt(
                        session.getStartedAt()
                )

                .completedAt(
                        session.getCompletedAt()
                )

                .currentQuestion(
                        currentQuestion
                )

                .questions(
                        questionResponses
                )

                .message(
                        "Your personalized interview is currently active."
                )

                .build();
    }


    // =====================================================
    // GET INTERVIEW HISTORY
    // =====================================================

    @Override
    @Transactional(readOnly = true)
    public List<InterviewSessionResponse> getInterviewHistory(
            String userEmail) {

        User user =
                userRepository
                        .findByEmail(userEmail)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found."
                                )
                        );


        List<InterviewSession> sessions =
                interviewSessionRepository
                        .findByUserIdOrderByStartedAtDesc(
                                user.getId()
                        );


        return sessions
                .stream()

                .map(session -> {

                    List<InterviewQuestion> questions =
                            interviewQuestionRepository
                                    .findByInterviewSessionIdOrderByQuestionNumberAsc(
                                            session.getId()
                                    );


                    List<InterviewQuestionResponse> questionResponses =
                            questions
                                    .stream()
                                    .map(this::buildQuestionResponse)
                                    .toList();


                    InterviewQuestionResponse currentQuestion = null;


                    if (Boolean.TRUE.equals(session.getActive())) {

                        currentQuestion =
                                questions
                                        .stream()

                                        .filter(question ->
                                                !Boolean.TRUE.equals(
                                                        question.getAnswered()
                                                )
                                        )

                                        .findFirst()

                                        .map(
                                                this::buildQuestionResponse
                                        )

                                        .orElse(null);
                    }


                    return InterviewSessionResponse
                            .builder()

                            .sessionId(
                                    session.getId()
                            )

                            .interviewLevel(
                                    session.getInterviewLevel()
                            )

                            .developerLevel(
                                    session.getDeveloperLevel()
                            )

                            .active(
                                    session.getActive()
                            )

                            .currentQuestionNumber(
                                    session.getCurrentQuestionNumber()
                            )

                            .totalQuestions(
                                    session.getTotalQuestions()
                            )

                            .finalScore(
                                    session.getFinalScore()
                            )

                            .finalFeedback(
                                    session.getFinalFeedback()
                            )

                            .startedAt(
                                    session.getStartedAt()
                            )

                            .completedAt(
                                    session.getCompletedAt()
                            )

                            .currentQuestion(
                                    currentQuestion
                            )

                            .questions(
                                    questionResponses
                            )

                            .message(
                                    Boolean.TRUE.equals(
                                            session.getActive()
                                    )
                                            ? "Interview in progress."
                                            : "Interview completed."
                            )

                            .build();
                })

                .toList();
    }
}