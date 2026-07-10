package com.codementor.backend.execution;

import com.codementor.backend.dto.ExecutionResult;
import com.codementor.backend.entity.Language;
import com.codementor.backend.entity.TestCase;

import java.util.List;

public interface CodeExecutionService {

    ExecutionResult execute(
            String sourceCode,
            Language language,
            List<TestCase> testCases
    );
}