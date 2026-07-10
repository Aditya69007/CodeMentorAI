package com.codementor.backend.controller;

import com.codementor.backend.dto.TestCaseRequest;
import com.codementor.backend.entity.TestCase;
import com.codementor.backend.service.TestCaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/problems/{problemId}/test-cases")
@RequiredArgsConstructor
public class TestCaseController {

    private final TestCaseService testCaseService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TestCase> createTestCase(
            @PathVariable Long problemId,
            @Valid @RequestBody TestCaseRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(testCaseService.createTestCase(problemId, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<TestCase>> getTestCases(
            @PathVariable Long problemId) {

        return ResponseEntity.ok(
                testCaseService.getTestCasesByProblem(problemId)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{testCaseId}")
    public ResponseEntity<Void> deleteTestCase(
            @PathVariable Long testCaseId) {

        testCaseService.deleteTestCase(testCaseId);

        return ResponseEntity.noContent().build();
    }
}