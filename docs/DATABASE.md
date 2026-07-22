# 🗄️ CodeMentorAI — DATABASE DOCUMENTATION

> Complete database architecture documentation.

---

# DATABASE OVERVIEW

| Item | Value |
|------|-------|
| Database | PostgreSQL |
| ORM | Spring Data JPA / Hibernate |
| Status | 🟡 Release Candidate (RC) |

---

# DATABASE VISION

The database is no longer only responsible for storing coding platform data.

It is the persistent storage layer for the **Developer Identity Platform**.

Every table should contribute toward building one unified Developer Identity.

---

# DATABASE ARCHITECTURE

```
Developer Identity

│

├── User

├── GitHub Profile

├── LeetCode Profile

├── Projects

├── Portfolio

├── Resume

├── AI Analytics

├── Learning Analytics

└── Interview Analytics
```

The database should evolve around Developer Identity rather than isolated modules.

---

# LOGICAL MODULES

The database is organized into the following logical domains.

## Authentication

- User
- Roles
- OAuth Providers

---

## Coding Platform

- Topics
- Problems
- Test Cases
- Submissions

---

## AI Intelligence

- AI Analysis
- AI Mistakes
- AI Chat
- Progressive Hints

---

## Learning Intelligence

- Growth
- Learning Plans
- Revision Plans
- Independent Solve Sessions

---

## Developer Identity

Future

- GitHub Profile
- GitHub Repository
- LeetCode Profile
- Connected Accounts
- Portfolio
- Resume Metadata

---

# CURRENT TABLES

## User

Purpose

Stores platform users.

Main Fields

- id
- firstName
- lastName
- email
- password
- role
- provider
- profilePicture
- enabled
- createdAt

Relationships

```
User

├── Submission

├── AiMistake

├── IndependentSolveSession

└── Future

    ├── GitHubProfile

    ├── LeetCodeProfile

    ├── Portfolio

    └── Resume
```

---

## Topic

Stores learning topics.

Examples

- Arrays
- Graph
- DP
- Tree
- Stack
- Queue

---

## Problem

Stores coding questions.

Relationships

```
Topic

↓

Problem

↓

Examples

↓

Test Cases

↓

Submissions
```

---

## ProblemExample

Stores visible examples.

---

## TestCase

Stores hidden and public judge test cases.

---

## Submission

Stores every code submission.

Contains

- Language
- Status
- Execution Time
- Memory
- Source Code
- Output
- Hidden Test Results

---

# AI TABLES

## AiAnalysis

Stores Gemini responses.

Includes

- Analysis
- Explanation
- Recommendations

---

## AiMistake

Stores recurring AI-detected mistakes.

Includes

- Mistake Type
- Severity
- Concept
- Description

---

## AiChatMessage

Stores AI conversation history.

---

## AiProgressiveHint

Stores progressive hints.

---

# LEARNING TABLES

## IndependentSolveSession

Tracks solving problems without AI assistance.

---

# FUTURE TABLES

## GitHubProfile

Status

🚧 Planned

Purpose

Store synchronized GitHub information.

Example fields

- username
- profileUrl
- avatarUrl
- bio
- followers
- following
- publicRepos
- totalStars
- languages
- lastSyncedAt

Relationship

```
User

↓

GitHubProfile
```

---

## GitHubRepository

Status

🚧 Planned

Purpose

Store synchronized repositories.

Example fields

- repositoryName
- description
- language
- stars
- forks
- topics
- repositoryUrl

Relationship

```
GitHubProfile

↓

GitHubRepository
```

---

## LeetCodeProfile

Status

🚧 Planned

Purpose

Store synchronized LeetCode statistics.

Example fields

- username
- totalSolved
- easySolved
- mediumSolved
- hardSolved
- acceptanceRate
- contestRating
- contestRanking
- profileUrl
- lastSyncedAt

Relationship

```
User

↓

LeetCodeProfile
```

---

## Portfolio

Status

🚧 Planned

Purpose

Store user portfolio preferences.

Examples

- Featured Projects
- Visibility
- Public URL
- Theme

---

## ResumeMetadata

Status

🚧 Planned

Purpose

Store resume generation preferences.

Examples

- Resume Template
- Resume Theme
- Last Generated
- Export Format

Resume content should NOT duplicate existing database data.

---

# ENTITY RELATIONSHIPS

```
User

│

├── Submission

├── AiMistake

├── IndependentSolveSession

├── GitHubProfile

│       └── GitHubRepository

├── LeetCodeProfile

├── Portfolio

└── ResumeMetadata
```

---

# DATABASE FEATURES

Current

✅ Authentication

✅ Problems

✅ Topics

✅ Test Cases

✅ Submissions

✅ AI Mentor

✅ AI Chat

✅ AI Mistake Memory

✅ Progressive Hints

✅ Independent Solve

Future

🚧 GitHub Integration

🚧 LeetCode Integration

🚧 Portfolio

🚧 Resume

🚧 Recruiter Platform

---

# INDEXING STRATEGY

Current

- User.email
- Topic.slug
- Problem.title
- Submission.user_id
- Submission.problem_id
- Submission.createdAt
- AiMistake.user_id
- AiChatMessage.submission_id

Future

- GitHubProfile.username
- GitHubRepository.language
- GitHubRepository.stars
- LeetCodeProfile.username

---

# FUTURE DATABASE ROADMAP

Phase 1

Developer Identity

- GitHub Profile
- GitHub Repositories
- LeetCode Profile

Phase 2

Portfolio

- Featured Projects
- Public Portfolio

Phase 3

Resume

- Resume Metadata
- Export History

Phase 4

Recruiter Platform

- Candidate Insights
- Recruiter Notes

---

# DATABASE RULES

Always

- Maintain normalization.
- Preserve referential integrity.
- Reuse existing entities whenever possible.
- Prefer extending tables over duplication.
- Use Flyway or Liquibase for all schema changes.
- Keep Developer Identity as the central data model.

Never

- Duplicate user information.
- Store derived AI data unnecessarily.
- Delete production data without migrations.
- Break backward compatibility.

---

# FINAL PRINCIPLE

Every new table must answer one question:

**"Does this strengthen the Developer Identity?"**

If YES

Create it.

If NO

Reconsider the schema.

The database is no longer just a coding platform database.

It is the foundation of the CodeMentorAI Developer Identity Platform.