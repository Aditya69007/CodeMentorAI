package com.codementor.backend.service.impl;

import com.codementor.backend.dto.RecommendedProblemResponse;
import com.codementor.backend.dto.RecommendationScore;
import com.codementor.backend.entity.Problem;
import com.codementor.backend.entity.SubmissionStatus;
import com.codementor.backend.entity.User;
import com.codementor.backend.exception.ResourceNotFoundException;
import com.codementor.backend.repository.ProblemRepository;
import com.codementor.backend.repository.SubmissionRepository;
import com.codementor.backend.repository.UserRepository;
import com.codementor.backend.service.RecommendationEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.codementor.backend.dto.PracticeRecommendationResponse;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class RecommendationEngineServiceImpl
        implements RecommendationEngineService {

    @Override
    public List<PracticeRecommendationResponse> generatePracticeRecommendations(
            String userEmail
    ) {

        throw new UnsupportedOperationException(
                "Implementation will be moved from AiMentorServiceImpl."
        );
    }
}