package com.codementor.backend.controller;

import com.codementor.backend.dto.TopicProblemResponse;
import com.codementor.backend.dto.TopicProgressResponse;
import com.codementor.backend.dto.TopicResponse;

import com.codementor.backend.service.TopicService;
import com.codementor.backend.dto.TopicRequest;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import com.codementor.backend.dto.AdminTopicResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

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
    
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/admin/all")
    public ResponseEntity<List<AdminTopicResponse>>
    getAllTopicsForAdmin() {

    return ResponseEntity.ok(
            topicService.getAllTopicsForAdmin()
    );
    }

        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
        @PostMapping("/admin")
        public ResponseEntity<AdminTopicResponse>
        createTopic(
                @Valid
                @RequestBody TopicRequest request
        ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        topicService.createTopic(request)
                );
        }

        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
        @PutMapping("/admin/{id}")
        public ResponseEntity<AdminTopicResponse>
        updateTopic(
                @PathVariable Long id,
                @Valid @RequestBody TopicRequest request
        ) {

        return ResponseEntity.ok(
                topicService.updateTopic(
                        id,
                        request
                )
        );
        }
        
        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
        @PatchMapping("/admin/{id}/status")
        public ResponseEntity<AdminTopicResponse>
        toggleTopicStatus(
                @PathVariable Long id
        ) {
    
        return ResponseEntity.ok(
                topicService.toggleTopicStatus(id)
        );
        }

        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
        @DeleteMapping("/admin/{id}")
        public ResponseEntity<Void> deleteTopic(
                @PathVariable Long id
        ) {

        topicService.deleteTopic(id);

        return ResponseEntity
                .noContent()
                .build();
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

        @GetMapping("/{slug}/progress")
        public ResponseEntity<TopicProgressResponse> getTopicProgress(
                @PathVariable String slug,
                Authentication authentication
        ) {

        return ResponseEntity.ok(
                topicService.getTopicProgress(
                        slug,
                        authentication.getName()
                )
        );
        }
}