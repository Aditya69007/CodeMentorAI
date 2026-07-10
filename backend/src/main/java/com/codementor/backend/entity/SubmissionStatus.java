package com.codementor.backend.entity;
import com.codementor.backend.entity.SubmissionStatus;
public enum SubmissionStatus {

    PENDING,
    RUNNING,
    ACCEPTED,
    WRONG_ANSWER,
    TIME_LIMIT_EXCEEDED,
    RUNTIME_ERROR,
    COMPILATION_ERROR
}