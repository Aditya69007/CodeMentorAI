# 🚀 CodeMentorAI — BACKEND DOCUMENTATION

> Complete backend architecture documentation.

---

# BACKEND OVERVIEW

| Item | Value |
|------|-------|
| Framework | Spring Boot |
| Language | Java 21 |
| Database | PostgreSQL |
| ORM | Spring Data JPA / Hibernate |
| Authentication | Spring Security + JWT + Google OAuth |
| AI | Google Gemini |
| Architecture | Clean Layered Architecture |
| Current Status | 🟡 Release Candidate (RC) |

---

# BACKEND VISION

The backend is no longer only an API server.

It is the **Developer Intelligence Engine**.

Every service contributes to building one unified **Developer Identity**.

```
Developer Identity

↓

GitHub

LeetCode

CodeMentorAI

↓

AI Intelligence

↓

Portfolio

Resume

Recruiter Dashboard

Growth

Interview

Mentor
```

---

# ARCHITECTURE

```
REST Controller

↓

Service Layer

↓

Repository Layer

↓

PostgreSQL

↓

AI Services

↓

Gemini
```

Business logic always belongs inside Services.

Controllers remain thin.

Repositories contain only persistence logic.

---

# PACKAGE STRUCTURE

backend/

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

integration/

github/

leetcode/

---

# CORE MODULES

## Authentication

✅ Complete

Includes

- JWT Authentication
- Google OAuth
- User Profile
- Role Management

---

## User Module

✅ Complete

Includes

- Profile
- Update Profile
- Current User
- Account Management

Future

- Connected Accounts
- Developer Identity

---

## Problem Module

✅ Complete

Includes

- CRUD
- Search
- Difficulty
- Tags
- Test Cases

---

## Submission Module

✅ Complete

Includes

- Code Execution
- Submission History
- Judge
- Metrics

---

# AI MODULES

## AI Mentor

✅ Complete

Includes

- Analysis
- Explanation
- Chat
- Progressive Hints

---

## AI Learning Intelligence

✅ Complete

Includes

- Growth Report
- Developer Skill Graph
- Learning Plan
- Revision Plan
- Practice Recommendation
- Concept Growth
- Adaptive Mentor
- Interview Profile

---

# DEVELOPER IDENTITY

🚀 New Core Architecture

Developer Identity becomes the heart of CodeMentorAI.

Future data sources

- User Profile
- GitHub
- LeetCode
- Projects
- AI Growth
- Interview
- Skills
- Resume
- Portfolio

Every backend module should extend this identity.

---

# GITHUB INTEGRATION

🟡 Planned

Future package

```
integration/github/
```

Responsibilities

- GitHub Profile
- Repositories
- Languages
- Stars
- Pinned Projects
- Profile URL

Future endpoints

```
GET /api/v1/github/profile

POST /api/v1/github/connect

PUT /api/v1/github/refresh
```

GitHub data improves

- Portfolio
- Resume
- Recruiter Dashboard
- AI Mentor

---

# LEETCODE INTEGRATION

🟡 Planned

Future package

```
integration/leetcode/
```

Responsibilities

- Problems Solved
- Acceptance Rate
- Contest Rating
- Topic Distribution
- Progress

Future endpoints

```
GET /api/v1/leetcode/profile

POST /api/v1/leetcode/connect

PUT /api/v1/leetcode/refresh
```

LeetCode data improves

- AI Mentor
- Growth Report
- Interview
- Portfolio
- Resume

---

# PORTFOLIO MODULE

🟡 In Progress

Current

- User Profile
- Growth Report
- Developer Skills

Future

- Dynamic Projects
- GitHub
- LeetCode
- Public Portfolio

---

# RESUME ENGINE

🟡 Planned

Resume should not store duplicate data.

Resume will be generated from

- Profile
- GitHub
- LeetCode
- Projects
- Skills
- Growth Report
- Achievements

---

# RECRUITER PLATFORM

🟡 Planned

Future modules

- Recruiter Dashboard
- Candidate AI Analysis
- Interview Readiness
- Skill Graph
- GitHub Summary
- LeetCode Summary

---

# CURRENT BACKEND STATUS

Authentication

✅

Problems

✅

Topics

✅

Submissions

✅

AI Mentor

✅

Growth Intelligence

✅

Interview

✅

Portfolio APIs

🟡

Developer Identity

🟡

GitHub Integration

❌

LeetCode Integration

❌

Overall Progress

🚀 ~98%

---

# VERSION 2 ROADMAP

Phase 1

Developer Identity

- GitHub
- LeetCode
- Dynamic Projects

Phase 2

AI Intelligence

- Cross-platform AI Analysis
- Unified Developer Profile

Phase 3

Portfolio

- Public Portfolio
- Dynamic Portfolio

Phase 4

Resume

- AI Resume
- ATS Resume
- PDF Export

Phase 5

Recruiter Platform

- Candidate Insights
- AI Hiring Dashboard

---

# DEVELOPMENT RULES

Always

- Reuse existing Services
- Extend existing Controllers
- Reuse DTOs
- Preserve layered architecture
- Keep business logic inside Services

Never

- Duplicate APIs
- Duplicate Services
- Duplicate Controllers
- Duplicate Repositories
- Create parallel implementations

---

# FINAL PRINCIPLE

Every backend feature must answer one question:

"Does this improve the Developer Identity?"

If YES

Implement it.

If NO

Reconsider the feature.

Developer Identity is now the central backend architecture of CodeMentorAI.