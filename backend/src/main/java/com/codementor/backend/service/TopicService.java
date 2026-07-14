package com.codementor.backend.service;

import com.codementor.backend.dto.TopicProblemResponse;
import com.codementor.backend.dto.TopicRequest;
import com.codementor.backend.dto.TopicResponse;

import com.codementor.backend.dto.AdminTopicResponse;

import java.util.List;

public interface TopicService {

    List<TopicResponse> getAllActiveTopics();

    TopicResponse getTopicBySlug(
            String slug
    );

    List<TopicProblemResponse> getProblemsByTopicSlug(
            String slug
    );

    List<AdminTopicResponse> getAllTopicsForAdmin();

    AdminTopicResponse createTopic(
                TopicRequest request
        );

        AdminTopicResponse updateTopic(
                Long id,
                TopicRequest request
        );

        AdminTopicResponse toggleTopicStatus(Long id);

        void deleteTopic(Long id);

}