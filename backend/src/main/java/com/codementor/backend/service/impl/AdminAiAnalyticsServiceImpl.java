package com.codementor.backend.service.impl;

import com.codementor.backend.dto.AdminAiAnalyticsResponse;

import com.codementor.backend.repository.AiAnalysisRepository;
import com.codementor.backend.repository.AiChatMessageRepository;
import com.codementor.backend.repository.AiMistakeRepository;
import com.codementor.backend.repository.AiProgressiveHintRepository;

import com.codementor.backend.service.AdminAiAnalyticsService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminAiAnalyticsServiceImpl
        implements AdminAiAnalyticsService {

    private final AiAnalysisRepository aiAnalysisRepository;

    private final AiChatMessageRepository aiChatMessageRepository;

    private final AiProgressiveHintRepository aiProgressiveHintRepository;

    private final AiMistakeRepository aiMistakeRepository;


    @Override
    @Transactional(readOnly = true)
    public AdminAiAnalyticsResponse getAnalytics() {

        long totalAnalyses =
                aiAnalysisRepository.count();

        long totalChatMessages =
                aiChatMessageRepository.count();

        long totalProgressiveHints =
                aiProgressiveHintRepository.count();

        long totalMistakesDetected =
                aiMistakeRepository.count();

        long usersWithMistakes =
                aiMistakeRepository
                        .countDistinctUsersWithMistakes();


        Map<String, Long> mistakeTypeDistribution =
                convertDistribution(
                        aiMistakeRepository
                                .findGlobalMistakeTypeDistribution()
                );


        Map<String, Long> severityDistribution =
                convertDistribution(
                        aiMistakeRepository
                                .findGlobalSeverityDistribution()
                );


        Map<String, Long> conceptDistribution =
                convertDistribution(
                        aiMistakeRepository
                                .findGlobalConceptDistribution()
                );


        String mostCommonMistakeType =
                getFirstKey(
                        mistakeTypeDistribution
                );


        String mostCommonConcept =
                getFirstKey(
                        conceptDistribution
                );


        return AdminAiAnalyticsResponse
                .builder()

                .totalAnalyses(
                        totalAnalyses
                )

                .totalChatMessages(
                        totalChatMessages
                )

                .totalProgressiveHints(
                        totalProgressiveHints
                )

                .totalMistakesDetected(
                        totalMistakesDetected
                )

                .usersWithMistakes(
                        usersWithMistakes
                )

                .mostCommonMistakeType(
                        mostCommonMistakeType
                )

                .mostCommonConcept(
                        mostCommonConcept
                )

                .mistakeTypeDistribution(
                        mistakeTypeDistribution
                )

                .severityDistribution(
                        severityDistribution
                )

                .conceptDistribution(
                        conceptDistribution
                )

                .build();
    }


    private Map<String, Long> convertDistribution(
            List<Object[]> rows
    ) {

        Map<String, Long> distribution =
                new LinkedHashMap<>();


        for (Object[] row : rows) {

            if (
                    row[0] == null ||
                    row[1] == null
            ) {
                continue;
            }


            distribution.put(
                    row[0].toString(),
                    (Long) row[1]
            );
        }


        return distribution;
    }


    private String getFirstKey(
            Map<String, Long> distribution
    ) {

        return distribution
                .keySet()
                .stream()
                .findFirst()
                .orElse(null);
    }
}