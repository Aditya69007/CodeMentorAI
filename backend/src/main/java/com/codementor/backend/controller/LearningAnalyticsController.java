package com.codementor.backend.controller;

import com.codementor.backend.dto.TopicProgressResponse;
import com.codementor.backend.service.LearningAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/learning")
@RequiredArgsConstructor
public class LearningAnalyticsController {

    private final LearningAnalyticsService learningAnalyticsService;

    @GetMapping("/topics/{slug}")
    public ResponseEntity<TopicProgressResponse> getTopicProgress(
            @PathVariable String slug,
            Authentication authentication
    ) {

        return ResponseEntity.ok(

                learningAnalyticsService.getTopicProgress(
                        slug,
                        authentication.getName()
                )

        );
    }

        @DeleteMapping("/ai-memory")
        public ResponseEntity<Void> resetAiMemory(
                Authentication authentication
        ) {

        learningAnalyticsService.resetAiMemory(
                authentication.getName()
        );

        return ResponseEntity.noContent().build();

        }

}