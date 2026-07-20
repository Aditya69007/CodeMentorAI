# 🚀 CodeMentorAI - BACKEND DOCUMENTATION

> Complete backend architecture documentation.

This document describes every backend module, service, repository,
controller, entity and AI pipeline.

---

# BACKEND OVERVIEW

Framework

Spring Boot

Language

Java

Database

PostgreSQL

ORM

Spring Data JPA / Hibernate

Authentication

Spring Security + JWT

Architecture

Controller

↓

Service

↓

Repository

↓

Database

---

# PACKAGE STRUCTURE

backend/src/main/java/com/codementor/backend/

controller/

service/

service/impl/

repository/

entity/

dto/

config/

security/

exception/

execution/

ai/

util/

---

# DESIGN PRINCIPLES

• Layered Architecture

• DTO-based API

• Repository Pattern

• Dependency Injection

• Transactional Services

• REST APIs

• Separation of Concerns

---

# CORE MODULES

## Authentication

Status

✅ COMPLETE

Responsibilities

- Register
- Login
- JWT generation
- JWT validation
- Role authorization
- USER role
- ADMIN role

Main Components

AuthController

AuthService

JwtService

JwtFilter

SecurityConfig

---

## User Module

Status

✅ COMPLETE

Responsibilities

- User profile
- Role management
- Statistics
- Account lookup

Repositories

UserRepository

---

## Topic Module

Status

✅ COMPLETE

Responsibilities

- Topic CRUD
- Topic listing
- Topic Progress
- Topic Problems
- Learning Analytics

Controllers

TopicController

LearningAnalyticsController

Services

TopicService

LearningAnalyticsService

Repositories

TopicRepository

ProblemRepository

SubmissionRepository

AiMistakeRepository

---

## Problem Module

Status

✅ COMPLETE

Responsibilities

- CRUD
- Filtering
- Search
- Difficulty
- Topic mapping
- Tags
- Hidden test cases

Entities

Problem

ProblemExample

TestCase

Repositories

ProblemRepository

TestCaseRepository

---

## Submission Module

Status

✅ COMPLETE

Responsibilities

- Run Code
- Submit Code
- Judge Results
- Save History
- Execution Metrics

Submission Status

PENDING

RUNNING

ACCEPTED

WRONG_ANSWER

TIME_LIMIT_EXCEEDED

RUNTIME_ERROR

COMPILATION_ERROR

Repositories

SubmissionRepository

---

# AI MODULES

## AI Mentor

Status

✅ COMPLETE

Responsibilities

Submission Analysis

Hint Generation

Concept Detection

Follow-up Chat

Progressive Hints

AI Explanation

Gemini Integration

Main Service

AiMentorServiceImpl

---

## Progressive Hint System

Status

✅ COMPLETE

Levels

Level 1

Concept Hint

Level 2

Algorithm Hint

Level 3

Approach Hint

Level 4

Pseudo-code Guidance

Persistence

AiProgressiveHint

---

## AI Chat

Status

✅ COMPLETE

Features

Persistent conversation

Submission context

Adaptive context

Learning history

Repositories

AiChatMessageRepository

---

## AI Mistake Memory

Status

✅ COMPLETE

Responsibilities

Detect mistakes

Store mistakes

Prevent duplicates

Recurring mistakes

Past recall

Developer profile

Growth tracking

Repositories

AiMistakeRepository

Entity

AiMistake

---

## Adaptive Mentor

Status

✅ COMPLETE

Responsibilities

Personalized learning context

Adaptive prompts

Adaptive hints

Adaptive chat

Learning history integration

---

## Learning Analytics

Status

✅ COMPLETE

Responsibilities

Topic mastery

Acceptance rate

Solved problems

Attempted problems

AI mistakes

Recommendation engine

Weak concepts

Strong concepts

Estimated learning gain

Recommendation reason

Main Service

LearningAnalyticsServiceImpl

---

## Developer Skill Graph

Status

✅ COMPLETE

Calculates

Acceptance Rate

Skill Score

Topic Score

Mistake Score

Skill Level

Outputs

MASTERED

STRONG

DEVELOPING

NEEDS_PRACTICE

---

## Concept Growth

Status

✅ COMPLETE

Tracks

Repeated mistakes

Recovered concepts

Improving concepts

Mastered concepts

---

## Practice Recommendation

Status

✅ COMPLETE

Generates

Priority

Recommended Problems

Reason

Concept

Growth Status

---

## Personalized Learning Plan

Status

✅ COMPLETE

Calculates

Overall Readiness

Learning Level

Weak Concepts

Strengths

Revision Priorities

Independent Solve Rate

Hint Dependency

Recommended Action

---

## Hint Dependency Score

Status

✅ COMPLETE (Backend)

Tracks

Problems with hints

Hint frequency

Hint strength

Dependency Score

Dependency Level

---

## Solution Evolution Timeline

Status

✅ COMPLETE (Backend)

Tracks

First Attempt

Improved

Regressed

Solved

Status Changed

No Change

Passed Test Changes

AI History

---

## Independent Solve Mode

Status

🟡

Completed

Session Tracking

AI Lock

Persistence

Remaining

Rewards

Frontend Polish

---

# ADMIN MODULES

Status

✅ COMPLETE

Includes

Admin Dashboard

Problem Management

Topic Management

Submission Management

AI Analytics

Platform Analytics

Charts

Distributions

---

# DATABASE ENTITIES

Core

User

Topic

Problem

ProblemExample

TestCase

Submission

AI

AiAnalysis

AiMistake

AiChatMessage

AiProgressiveHint

Learning

IndependentSolveSession

---

# CURRENT BACKEND COMPLETION

Authentication

✅

Problems

✅

Execution

✅

AI Mentor

✅

Adaptive Mentor

✅

Mistake Memory

✅

Learning Analytics

✅

Skill Graph

✅

Practice Recommendation

✅

Personalized Learning Plan

✅

Hint Dependency Backend

✅

Solution Evolution Backend

✅

Admin Platform

✅

Overall Backend Progress

≈95%

---

# REMAINING BACKEND WORK

Production validation

Logging improvements

Rate limiting

Global monitoring

Gemini retry strategy

Performance optimization

Security audit

Deployment configuration

---

# DEVELOPMENT RULES

Never duplicate services.

Never duplicate repositories.

Never duplicate controllers.

Always reuse existing DTOs.

Always extend existing APIs before creating new ones.

Maintain layered architecture.

Use constructor injection.

Keep business logic inside Services.

Repositories should contain only data-access logic.

Controllers should remain thin.

All new AI features must integrate with existing AI Mentor architecture instead of creating parallel systems.


# 🚀 CodeMentorAI - BACKEND DOCUMENTATION

> Complete backend architecture documentation.

---

# BACKEND OVERVIEW

Framework

Spring Boot

Language

Java

Database

PostgreSQL

ORM

Spring Data JPA / Hibernate

Authentication

Spring Security + JWT

Architecture

Controller

↓

Service

↓

Repository

↓

Database

Status

🟡 Release Candidate (RC)

---

# PACKAGE STRUCTURE

backend/src/main/java/com/codementor/backend/

controller/

service/

service/impl/

repository/

entity/

dto/

config/

security/

exception/

execution/

ai/

util/

---

# DESIGN PRINCIPLES

- Layered Architecture
- Clean Architecture
- SOLID Principles
- DTO-based APIs
- Repository Pattern
- Dependency Injection
- Transactional Services
- Separation of Concerns
- Thin Controllers
- Business Logic inside Services

---

# CORE MODULES

## Authentication

✅ COMPLETE

Includes

- Register
- Login
- JWT Authentication
- JWT Validation
- Role-based Security
- USER
- ADMIN

---

## User Module

✅ COMPLETE

Includes

- User Profile
- Statistics
- Role Management

---

## Topic Module

✅ COMPLETE

Includes

- CRUD
- Progress
- Analytics
- Topic Problems
- Recommendations

---

## Problem Module

✅ COMPLETE

Includes

- CRUD
- Search
- Filtering
- Difficulty
- Tags
- Hidden Test Cases
- Topic Mapping

---

## Submission Module

✅ COMPLETE

Includes

- Run Code
- Submit Code
- Judge
- Metrics
- History

Supported Results

- Accepted
- Wrong Answer
- Runtime Error
- Compilation Error
- Time Limit Exceeded

---

# AI MODULES

## AI Mentor

✅ COMPLETE

- AI Analysis
- AI Explanation
- AI Chat
- Progressive Hints
- Adaptive Responses
- Concept Detection

---

## Progressive Hint System

✅ COMPLETE

Levels

- Concept
- Algorithm
- Approach
- Pseudocode

Persistent storage enabled.

---

## AI Chat

✅ COMPLETE

Includes

- Persistent Conversations
- Submission Context
- Learning Context
- Adaptive Prompts

---

## AI Mistake Memory

✅ COMPLETE

Includes

- Mistake Detection
- Persistence
- Recurring Mistakes
- Recall
- Growth Tracking

---

## Learning Intelligence

✅ COMPLETE

Includes

- Growth Report
- Learning Plan
- Revision Plan
- Practice Recommendation
- Developer Skill Graph
- Adaptive Mentor
- Concept Growth

---

## Personalized Interview

✅ COMPLETE

Includes

- Adaptive Questions
- AI Evaluation
- Interview History
- Final Assessment
- Feedback
- Score

---

## Solution Evolution

✅ COMPLETE

Includes

- Timeline
- Attempt Comparison
- Regression Detection
- Improvement Tracking
- AI Insights

---

## Independent Solve Mode

✅ COMPLETE

Includes

- Session Tracking
- AI Lock
- Completion Tracking
- History

---

## Developer Activity

✅ COMPLETE

Includes

- Daily Activity
- Contribution Calendar
- Progress History

---

# ADMIN MODULES

✅ COMPLETE

Includes

- Dashboard
- Users
- Problems
- Topics
- Submissions
- Platform Analytics
- AI Analytics

---

# DATABASE

Status

✅ COMPLETE

Entities

- User
- Topic
- Problem
- TestCase
- Submission
- AiAnalysis
- AiMistake
- AiChatMessage
- AiProgressiveHint
- IndependentSolveSession

---

# CURRENT BACKEND STATUS

Authentication

✅

Problems

✅

Topics

✅

Execution

✅

AI Mentor

✅

Growth Intelligence

✅

Interview

✅

Independent Solve

✅

Developer Activity

✅

Admin Platform

✅

Overall Backend Progress

🚀 ~98%

---

# REMAINING BACKEND WORK

Production only

- Logging
- Monitoring
- Rate Limiting
- Performance Optimization
- Security Audit
- Production Configuration

No major feature development is planned before Version 1.0 release.

---

# DEVELOPMENT RULES

Always

- Reuse Services
- Reuse Controllers
- Reuse DTOs
- Preserve Architecture
- Keep Controllers Thin
- Keep Logic inside Services

Never

- Duplicate Controllers
- Duplicate Services
- Duplicate Repositories
- Duplicate APIs
- Rewrite completed modules

---

# RELEASE STATUS

Current Phase

🟡 Release Candidate (RC)

Focus

- Production Polish
- Testing
- Documentation
- Deployment