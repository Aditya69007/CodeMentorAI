package com.codementor.backend.service;

import com.codementor.backend.dto.TestCaseRequest;
import com.codementor.backend.entity.TestCase;

import java.util.List;

public interface TestCaseService {

    TestCase createTestCase(
            Long problemId,
            TestCaseRequest request
    );

    List<TestCase> getTestCasesByProblem(Long problemId);

    void deleteTestCase(Long id);
}