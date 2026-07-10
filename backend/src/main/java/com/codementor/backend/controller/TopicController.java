package com.codementor.backend.controller;

import com.codementor.backend.dto.TopicProblemResponse;
import com.codementor.backend.dto.TopicResponse;

import com.codementor.backend.service.TopicService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;


    @GetMapping
    public ResponseEntity<List<TopicResponse>>
    getAllActiveTopics() {

        return ResponseEntity.ok(
                topicService.getAllActiveTopics()
        );
    }


    @GetMapping("/{slug}")
    public ResponseEntity<TopicResponse>
    getTopicBySlug(
            @PathVariable String slug) {

        return ResponseEntity.ok(
                topicService.getTopicBySlug(
                        slug
                )
        );
    }


    @GetMapping("/{slug}/problems")
    public ResponseEntity<List<TopicProblemResponse>>
    getProblemsByTopicSlug(
            @PathVariable String slug) {

        return ResponseEntity.ok(
                topicService.getProblemsByTopicSlug(
                        slug
                )
        );
    }
}