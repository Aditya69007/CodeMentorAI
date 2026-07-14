package com.codementor.backend.service.impl;

import com.codementor.backend.dto.TopicProblemResponse;
import com.codementor.backend.dto.TopicRequest;
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
import com.codementor.backend.dto.AdminTopicResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl
        implements TopicService {
                
                private final TopicRepository topicRepository;
                
                private final ProblemRepository problemRepository;
                
                
                @Override
                @Transactional(readOnly = true)
                public List<AdminTopicResponse>
                getAllTopicsForAdmin() {
            
                return topicRepository
                        .findAllByOrderByCreatedAtDesc()
                        .stream()
                        .map(this::mapToAdminTopicResponse)
                        .toList();
                }

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

        private AdminTopicResponse mapToAdminTopicResponse(
                Topic topic
        ) {

        long problemCount =
                problemRepository
                        .countByTopicIdAndActiveTrue(
                                topic.getId()
                        );


        return AdminTopicResponse
                .builder()

                .id(topic.getId())

                .name(topic.getName())

                .slug(topic.getSlug())

                .description(topic.getDescription())

                .active(topic.getActive())

                .problemCount(problemCount)

                .createdAt(topic.getCreatedAt())

                .updatedAt(topic.getUpdatedAt())

                .build();
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

        @Override
        @Transactional
        public AdminTopicResponse createTopic(
                TopicRequest request
        ) {

        String name =
                request.getName().trim();

        String slug =
                request.getSlug()
                        .trim()
                        .toLowerCase();


        if (
                topicRepository
                        .existsByNameIgnoreCase(name)
        ) {

                throw new IllegalArgumentException(
                        "A topic with this name already exists."
                );
        }


        if (
                topicRepository
                        .existsBySlug(slug)
        ) {

                throw new IllegalArgumentException(
                        "A topic with this slug already exists."
                );
        }


        Topic topic =
                Topic.builder()

                        .name(name)

                        .slug(slug)

                        .description(
                                request.getDescription()
                        )

                        .active(true)

                        .build();


        Topic savedTopic =
                topicRepository.save(topic);


        return mapToAdminTopicResponse(
                savedTopic
        );
        }

        @Override
        @Transactional
        public AdminTopicResponse updateTopic(
                Long id,
                TopicRequest request
        ) {

        Topic topic =
                topicRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Topic not found."
                                )
                        );


        String name =
                request.getName().trim();

        String slug =
                request.getSlug()
                        .trim()
                        .toLowerCase();


        topicRepository
                .findByNameIgnoreCase(name)
                .filter(existingTopic ->
                        !existingTopic.getId().equals(id)
                )
                .ifPresent(existingTopic -> {

                        throw new IllegalArgumentException(
                                "A topic with this name already exists."
                        );

                });


        topicRepository
                .findBySlug(slug)
                .filter(existingTopic ->
                        !existingTopic.getId().equals(id)
                )
                .ifPresent(existingTopic -> {

                        throw new IllegalArgumentException(
                                "A topic with this slug already exists."
                        );

                });


        topic.setName(name);

        topic.setSlug(slug);

        topic.setDescription(
                request.getDescription()
        );


        Topic updatedTopic =
                topicRepository.save(topic);


        return mapToAdminTopicResponse(
                updatedTopic
        );
        }


        @Override
        @Transactional
        public AdminTopicResponse toggleTopicStatus(
                Long id
        ) {

        Topic topic =
                topicRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Topic not found."
                                )
                        );

        topic.setActive(
                !Boolean.TRUE.equals(
                        topic.getActive()
                )
        );

        Topic updatedTopic =
                topicRepository.save(topic);

        return mapToAdminTopicResponse(
                updatedTopic
        );
        }

        @Override
        @Transactional
        public void deleteTopic(
                Long id
        ) {

        Topic topic =
                topicRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Topic not found."
                                )
                        );


        long problemCount =
                problemRepository
                        .countByTopicId(id);


        if (problemCount > 0) {

                throw new IllegalStateException(
                        "Cannot delete topic because it contains problems. Deactivate the topic instead."
                );
        }


        topicRepository.delete(topic);
        }
}