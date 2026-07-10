package com.codementor.backend.repository;

import com.codementor.backend.entity.TestCase;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestCaseRepository
        extends JpaRepository<TestCase, Long> {

    List<TestCase> findByProblemId(
            Long problemId
    );

    boolean existsByProblemId(
            Long problemId
    );

    void deleteByProblemId(
            Long problemId
    );
}