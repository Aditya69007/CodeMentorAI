# 🗄️ CodeMentorAI - DATABASE DOCUMENTATION

> Complete database schema documentation.

Database

PostgreSQL

ORM

Spring Data JPA / Hibernate

Status

Production Ready

---

# DATABASE OVERVIEW

The database is organized into five logical areas:

1. Authentication
2. Coding Platform
3. AI Mentor
4. Learning Intelligence
5. Independent Solve Mode

---

# TABLES

## User

Purpose

Stores registered developers.

Primary Key

id

Main Fields

- firstName
- lastName
- email
- password
- role
- createdAt

Relationships

User
│
├── Submission (1:N)
├── AiMistake (1:N)
├── AiAnalysis (indirect through Submission)
├── AiChatMessage (indirect)
├── AiProgressiveHint (indirect)
└── IndependentSolveSession (1:N)

---

## Topic

Purpose

Represents a learning topic.

Examples

Array

Stack

Queue

Tree

Graph

Dynamic Programming

Relationships

Topic
│
└── Problem (1:N)

---

## Problem

Purpose

Stores coding questions.

Main Fields

- title
- description
- difficulty
- topic
- tags
- constraints
- inputFormat
- outputFormat
- sampleInput
- sampleOutput
- active

Relationships

Problem
│
├── Topic (N:1)
├── ProblemExample (1:N)
├── TestCase (1:N)
├── Submission (1:N)
└── AiMistake (1:N)

---

## ProblemExample

Purpose

Visible examples shown to users.

Relationships

Problem
│
└── ProblemExample

---

## TestCase

Purpose

Stores hidden and public judge test cases.

Main Fields

- input
- expectedOutput
- hidden

Relationships

Problem
│
└── TestCase

---

## Submission

Purpose

Stores every code submission.

Main Fields

- sourceCode
- language
- status
- executionTime
- memoryUsed
- output
- errorMessage
- passedTestCases
- totalTestCases
- failedOnHiddenTest
- createdAt

Relationships

Submission
│
├── User
├── Problem
├── AiAnalysis
├── AiMistake
├── AiProgressiveHint
└── AiChatMessage

---

# AI TABLES

## AiAnalysis

Purpose

Stores Gemini analysis.

Contains

Explanation

Hint

Concept To Study

Relationship

Submission (1:1)

---

## AiMistake

Purpose

Stores AI detected mistakes.

Fields

Mistake Type

Concept

Description

Severity

Relationships

Submission

Problem

User

---

## AiChatMessage

Purpose

Stores AI chat history.

Fields

Role

Message

CreatedAt

Relationship

Submission

---

## AiProgressiveHint

Purpose

Stores generated progressive hints.

Fields

Hint Level

Response

Relationship

Submission

---

# LEARNING TABLES

## IndependentSolveSession

Purpose

Tracks solving without AI.

Fields

Active

SolvedIndependently

StartedAt

CompletedAt

Relationship

User

Problem

---

# ENTITY RELATIONSHIP SUMMARY

User

↓

Submission

↓

Problem

↓

Topic

Submission

↓

AiAnalysis

↓

AiMistake

↓

AiChatMessage

↓

AiProgressiveHint

---

# DATABASE FEATURES

Implemented

✅ User Authentication

✅ Problems

✅ Topics

✅ Test Cases

✅ Hidden Test Cases

✅ Submissions

✅ AI Analysis

✅ AI Mistakes

✅ AI Chat

✅ Progressive Hints

✅ Learning Analytics

✅ Topic Analytics

✅ Independent Solve Sessions

---

# INDEXING STRATEGY

Indexed Fields

User.email

Topic.slug

Problem.title

Submission.user_id

Submission.problem_id

Submission.createdAt

AiMistake.user_id

AiMistake.problem_id

AiChatMessage.submission_id

---

# FUTURE DATABASE WORK

Production indexes

Performance tuning

Query optimization

Partitioning (if needed)

Database backup strategy

Migration versioning

---

# RULES

Never delete production data.

Always use Flyway/Liquibase for future migrations (recommended).

Keep relationships normalized.

Avoid duplicate data.

Maintain referential integrity.