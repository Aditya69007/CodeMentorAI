package com.codementor.backend.service;

import com.codementor.backend.dto.PracticeRecommendationResponse;

import java.util.List;

public interface RecommendationEngineService {

    List<PracticeRecommendationResponse> generatePracticeRecommendations(
            String userEmail
    );
}