package com.codementor.backend.service.impl;

import com.codementor.backend.dto.TestCaseRequest;
import com.codementor.backend.entity.Problem;
import com.codementor.backend.entity.TestCase;
import com.codementor.backend.exception.ResourceNotFoundException;
import com.codementor.backend.repository.ProblemRepository;
import com.codementor.backend.repository.TestCaseRepository;
import com.codementor.backend.service.TestCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestCaseServiceImpl implements TestCaseService {

    private final TestCaseRepository testCaseRepository;
    private final ProblemRepository problemRepository;

    @Override
    public TestCase createTestCase(
            Long problemId,
            TestCaseRequest request) {

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Problem not found with id: " + problemId
                        ));

        TestCase testCase = TestCase.builder()
                .input(request.getInput())
                .expectedOutput(request.getExpectedOutput())
                .hidden(request.getHidden() != null
                        ? request.getHidden()
                        : true)
                .problem(problem)
                .build();

        return testCaseRepository.save(testCase);
    }

    @Override
    public List<TestCase> getTestCasesByProblem(Long problemId) {

        return testCaseRepository.findByProblemId(problemId);
    }

    @Override
    public void deleteTestCase(Long id) {

        TestCase testCase = testCaseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Test case not found with id: " + id
                        ));

        testCaseRepository.delete(testCase);
    }
}