package com.codementor.backend.service.impl;

import com.codementor.backend.entity.AiMistake;
import com.codementor.backend.entity.Submission;
import com.codementor.backend.entity.User;
import com.codementor.backend.repository.AiMistakeRepository;
import com.codementor.backend.repository.SubmissionRepository;
import com.codementor.backend.repository.UserRepository;
import com.codementor.backend.service.AdaptiveMentorService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdaptiveMentorServiceImpl
        implements AdaptiveMentorService {

    private final UserRepository userRepository;

    private final SubmissionRepository submissionRepository;

    private final AiMistakeRepository aiMistakeRepository;


    @Override
    @Transactional(readOnly = true)
    public String buildPersonalizedLearningContext(
            String userEmail
    ) {

        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found."
                        )
                );


        Long userId = user.getId();


        List<Submission> submissions =
                submissionRepository
                        .findByUserId(userId);


        List<AiMistake> mistakes =
                aiMistakeRepository
                        .findByUserIdOrderByCreatedAtDesc(
                                userId
                        );


        StringBuilder context =
                new StringBuilder();


        context.append("""
                PERSONALIZED DEVELOPER LEARNING PROFILE:

                """);


        // =====================================================
        // SUBMISSION HISTORY
        // =====================================================

        context.append("SUBMISSION HISTORY:\n");

        context.append("- Total submissions: ")
                .append(submissions.size())
                .append("\n");


        long acceptedSubmissions =
                submissions
                        .stream()
                        .filter(submission ->
                                submission
                                        .getStatus()
                                        .name()
                                        .equals("ACCEPTED")
                        )
                        .count();


        context.append("- Accepted submissions: ")
                .append(acceptedSubmissions)
                .append("\n");


        double acceptanceRate =
                submissions.isEmpty()
                        ? 0
                        : (
                        (double) acceptedSubmissions
                                / submissions.size()
                ) * 100;


        context.append("- Acceptance rate: ")
                .append(
                        Math.round(
                                acceptanceRate * 100.0
                        ) / 100.0
                )
                .append("%\n\n");


        // =====================================================
        // MISTAKE MEMORY
        // =====================================================

        context.append("AI MISTAKE MEMORY:\n");


        if (mistakes.isEmpty()) {

            context.append(
                    "- No previous AI-detected mistakes.\n\n"
            );

        } else {

            int mistakeLimit =
                    Math.min(
                            mistakes.size(),
                            10
                    );


            for (int index = 0;
                 index < mistakeLimit;
                 index++) {

                AiMistake mistake =
                        mistakes.get(index);


                context.append("- Mistake type: ")
                        .append(
                                mistake.getMistakeType()
                        );


                if (mistake.getConcept() != null) {

                    context.append(
                                    ", concept: "
                            )
                            .append(
                                    mistake.getConcept()
                            );
                }


                context.append("\n");
            }


            context.append("\n");
        }


        // =====================================================
        // RECURRING MISTAKES
        // =====================================================

        List<Object[]> recurringMistakes =
                aiMistakeRepository
                        .findRecurringMistakesByUserId(
                                userId
                        );


        context.append(
                "RECURRING MISTAKE PATTERNS:\n"
        );


        if (recurringMistakes.isEmpty()) {

            context.append(
                    "- No recurring mistakes detected.\n\n"
            );

        } else {

            for (Object[] row : recurringMistakes) {

                context.append("- ")
                        .append(row[0])
                        .append(": ")
                        .append(row[1])
                        .append(" occurrences across ")
                        .append(row[2])
                        .append(" problems.\n");
            }


            context.append("\n");
        }


        // =====================================================
        // ADAPTIVE MENTOR RULES
        // =====================================================

        context.append("""
                ADAPTIVE MENTOR INSTRUCTIONS:

                1. Personalize the explanation using the developer's real coding history.

                2. If the developer repeatedly makes a known mistake, mention the pattern
                   naturally without sounding repetitive.

                3. Give more guidance when the developer has a weak history.

                4. Give less unnecessary explanation when the developer demonstrates
                   strong understanding.

                5. Encourage reasoning before revealing complete solutions.

                6. Connect current mistakes with relevant previous mistake patterns.

                7. Do not expose private database information or internal system details.

                8. Do not invent learning history that is not present in the profile.
                """);


        return context.toString();
    }
}