package com.codementor.backend.service.impl;

import com.codementor.backend.dto.TopicProblemResponse;
import com.codementor.backend.dto.TopicResponse;

import com.codementor.backend.entity.Problem;
import com.codementor.backend.entity.Topic;

import com.codementor.backend.exception.ResourceNotFoundException;

import com.codementor.backend.repository.ProblemRepository;
import com.codementor.backend.repository.TopicRepository;

import com.codementor.backend.service.TopicService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl
        implements TopicService {

    private final TopicRepository topicRepository;

    private final ProblemRepository problemRepository;


    @Override
    @Transactional(readOnly = true)
    public List<TopicResponse> getAllActiveTopics() {

        return topicRepository
                .findByActiveTrueOrderByNameAsc()
                .stream()
                .map(this::mapToTopicResponse)
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public TopicResponse getTopicBySlug(
            String slug) {

        Topic topic =
                getActiveTopicBySlug(slug);

        return mapToTopicResponse(topic);
    }


    @Override
    @Transactional(readOnly = true)
    public List<TopicProblemResponse>
    getProblemsByTopicSlug(
            String slug) {

        Topic topic =
                getActiveTopicBySlug(slug);


        return problemRepository
                .findByTopicIdAndActiveTrueOrderByDifficultyAscTitleAsc(
                        topic.getId()
                )
                .stream()
                .map(this::mapToTopicProblemResponse)
                .toList();
    }


    private Topic getActiveTopicBySlug(
            String slug) {

        Topic topic =
                topicRepository
                        .findBySlug(slug)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Topic not found."
                                )
                        );


        if (!Boolean.TRUE.equals(topic.getActive())) {

            throw new ResourceNotFoundException(
                    "Topic not found."
            );
        }


        return topic;
    }


    private TopicResponse mapToTopicResponse(
            Topic topic) {

        long problemCount =
                problemRepository
                        .countByTopicIdAndActiveTrue(
                                topic.getId()
                        );


        return TopicResponse
                .builder()

                .id(
                        topic.getId()
                )

                .name(
                        topic.getName()
                )

                .slug(
                        topic.getSlug()
                )

                .description(
                        topic.getDescription()
                )

                .problemCount(
                        problemCount
                )

                .build();
    }


    private TopicProblemResponse
    mapToTopicProblemResponse(
            Problem problem) {

        return TopicProblemResponse
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

                .tags(
                        problem.getTags()
                )

                .build();
    }
}