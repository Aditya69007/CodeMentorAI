package com.codementor.backend.service;

import com.codementor.backend.dto.TopicProblemResponse;
import com.codementor.backend.dto.TopicResponse;

import java.util.List;

public interface TopicService {

    List<TopicResponse> getAllActiveTopics();

    TopicResponse getTopicBySlug(
            String slug
    );

    List<TopicProblemResponse> getProblemsByTopicSlug(
            String slug
    );
}