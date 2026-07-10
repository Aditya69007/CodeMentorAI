package com.codementor.backend.repository;

import com.codementor.backend.entity.ProblemExample;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemExampleRepository
        extends JpaRepository<ProblemExample, Long> {

    List<ProblemExample>
    findByProblemIdOrderByOrderIndexAsc(Long problemId);

    boolean existsByProblemId(Long problemId);

    void deleteByProblemId(Long problemId);
}