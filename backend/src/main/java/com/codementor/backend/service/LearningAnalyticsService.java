package com.codementor.backend.service;

import com.codementor.backend.dto.TopicProgressResponse;

public interface LearningAnalyticsService {

    TopicProgressResponse getTopicProgress(
            String topicSlug,
            String userEmail
    );

}