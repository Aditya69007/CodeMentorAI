package com.codementor.backend.service.impl;

import com.codementor.backend.dto.TopicProgressResponse;
import com.codementor.backend.repository.AiMistakeRepository;
import com.codementor.backend.repository.ProblemRepository;
import com.codementor.backend.repository.SubmissionRepository;
import com.codementor.backend.repository.TopicRepository;
import com.codementor.backend.repository.UserRepository;
import com.codementor.backend.service.LearningAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.codementor.backend.entity.Problem;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LearningAnalyticsServiceImpl
        implements LearningAnalyticsService {

    private final UserRepository userRepository;

    private final TopicRepository topicRepository;

    private final ProblemRepository problemRepository;

    private final SubmissionRepository submissionRepository;

    private final AiMistakeRepository aiMistakeRepository;

    @Override
    @Transactional(readOnly = true)
    public TopicProgressResponse getTopicProgress(
            String topicSlug,
            String userEmail
    ) {

        var user =
                userRepository
                        .findByEmail(userEmail)
                        .orElseThrow();

        var topic =
                topicRepository
                        .findBySlug(topicSlug)
                        .orElseThrow();

        long totalProblems =
                problemRepository.countByTopicIdAndActiveTrue(
                        topic.getId()
                );

        long solvedProblems =
                submissionRepository.countSolvedProblemsByTopic(
                        user.getId(),
                        topic.getId()
                );

        long attemptedProblems =
                submissionRepository.countAttemptedProblemsByTopic(
                        user.getId(),
                        topic.getId()
                );

        long totalAttempts =
                submissionRepository.countTotalAttemptsByTopic(
                        user.getId(),
                        topic.getId()
                );

        long acceptedSubmissions =
                submissionRepository.countAcceptedSubmissionsByTopic(
                        user.getId(),
                        topic.getId()
                );

        long aiMistakes =
                aiMistakeRepository.countByUserAndTopic(
                        user.getId(),
                        topic.getId()
                );

        double acceptanceRate =
                totalAttempts == 0
                        ? 0
                        : (acceptedSubmissions * 100.0) / totalAttempts;

        double mastery =
                totalProblems == 0
                        ? 0
                        : (solvedProblems * 100.0) / totalProblems;

        String level;

        if (mastery >= 80) {

            level = "Advanced";

        } else if (mastery >= 50) {

            level = "Intermediate";

        } else {

            level = "Beginner";
        }

        List<Long> solvedProblemIds =
                submissionRepository.findSolvedProblemIdsByUserId(
                        user.getId()
                );

        List<Problem> topicProblems =
                problemRepository.findByTopicIdAndActiveTrueOrderByDifficultyAscTitleAsc(
                        topic.getId()
                );

        Problem recommendedProblem =
                topicProblems
                        .stream()
                        .filter(problem ->
                                !solvedProblemIds.contains(problem.getId())
                        )
                        .findFirst()
                        .orElse(
                                topicProblems.isEmpty()
                                        ? null
                                        : topicProblems.get(0)
                        );

        List<Object[]> conceptStats =
                aiMistakeRepository.findConceptMistakeStatsByTopic(
                        user.getId(),
                        topic.getId()
                );

        List<String> weakConcepts =
                conceptStats.stream()
                        .limit(5)
                        .map(row -> (String) row[0])
                        .toList();

        List<String> strongConcepts =
                topicProblems.stream()
                        .filter(problem ->
                                solvedProblemIds.contains(problem.getId())
                        )
                        .flatMap(problem -> problem.getTags().stream())
                        .distinct()
                        .limit(5)
                        .toList();

                
        String recommendationReason;

        if (recommendedProblem == null) {

        recommendationReason =
                "You have completed every available problem in this topic.";

        } else if (aiMistakes >= 10) {

        recommendationReason =
                "You have repeated AI mistakes in this topic. Focus on strengthening your fundamentals before increasing difficulty.";

        } else if (acceptanceRate < 40) {

        recommendationReason =
                "Your acceptance rate is still low. Solve this recommendation to improve consistency.";

        } else if (mastery < 50) {

        recommendationReason =
                "Build strong fundamentals by solving this recommended problem.";

        } else if (mastery < 80) {

        recommendationReason =
                "This problem matches your current mastery level and will improve long-term retention.";

        } else {

        recommendationReason =
                "You're ready for more advanced challenges. Solve this without AI hints if possible.";

        }

        int learningGain;

        if (recommendedProblem == null) {

            learningGain = 0;

        } else if (mastery < 50) {

            learningGain = 25;

        } else if (mastery < 80) {

            learningGain = 18;

        } else {

            learningGain = 10;

        }

        return TopicProgressResponse.builder()

                .topicId(topic.getId())

                .topicName(topic.getName())

                .topicSlug(topic.getSlug())

                .totalProblems(totalProblems)

                .solvedProblems(solvedProblems)

                .attemptedProblems(attemptedProblems)

                .totalAttempts(totalAttempts)

                .acceptedSubmissions(acceptedSubmissions)

                .aiMistakes(aiMistakes)

                .acceptanceRate(acceptanceRate)

                .masteryPercentage(mastery)

                .level(level)

                .weakConcepts(

                        weakConcepts.isEmpty()

                                ? List.of("No recurring weak concepts")

                                : weakConcepts

                )

                .strongConcepts(

                        strongConcepts.isEmpty()

                                ? List.of("Keep solving to build strengths")

                                : strongConcepts

                )

                .recommendedProblemId(

                        recommendedProblem != null
                                ? recommendedProblem.getId()
                                : null

                )

                .recommendedProblemTitle(

                        recommendedProblem != null
                                ? recommendedProblem.getTitle()
                                : "No Recommendation"

                )

                .recommendedDifficulty(

                        recommendedProblem != null
                                ? recommendedProblem.getDifficulty().name()
                                : null

                )

                .recommendationReason(
                        recommendationReason
                )

                .estimatedLearningGain(
                        learningGain
                )

                .build();
    }

        @Override
        @Transactional
        public void resetAiMemory(String email) {

        var user = userRepository
                .findByEmail(email)
                .orElseThrow();

        aiMistakeRepository.deleteByUserId(user.getId());

        }

}