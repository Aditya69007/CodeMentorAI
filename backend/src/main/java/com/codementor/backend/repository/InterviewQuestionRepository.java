package com.codementor.backend.repository;

import com.codementor.backend.entity.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterviewQuestionRepository
        extends JpaRepository<InterviewQuestion, Long> {

    List<InterviewQuestion>
    findByInterviewSessionIdOrderByQuestionNumberAsc(
            Long interviewSessionId
    );

    Optional<InterviewQuestion>
    findByIdAndInterviewSessionId(
            Long questionId,
            Long interviewSessionId
    );

    Optional<InterviewQuestion>
    findByInterviewSessionIdAndAnsweredFalse(
            Long interviewSessionId
    );
}