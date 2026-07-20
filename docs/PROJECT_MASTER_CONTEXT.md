# 🚀 CodeMentorAI — PROJECT MASTER CONTEXT

> **Single Source of Truth (SSOT)**

This document is the highest-level source of truth for the CodeMentorAI project.

Every developer, contributor, or AI assistant must read this document before making any implementation, architectural, or documentation changes.

If any documentation conflicts exist, this file takes precedence.

---

# 📄 DOCUMENT INFORMATION

| Field | Value |
|--------|-------|
| Project | CodeMentorAI |
| Documentation Version | v1.1 |
| Project Version | v1.0 Release Candidate |
| Last Updated | 2026-07-18 |
| Status | 🟡 Release Candidate (RC) |
| Repository Type | Monorepo |

---

# 🎯 PROJECT VISION

CodeMentorAI is **not** an Online Judge.

It is an **AI-Powered Coding Mentor Platform** designed to help developers continuously improve rather than simply evaluate submissions.

The platform focuses on:

- AI-assisted learning
- Personalized coding guidance
- Developer growth tracking
- Intelligent mistake analysis
- Adaptive learning
- Interview preparation
- Long-term skill improvement

The goal is to create an AI mentor that understands the developer's history and continuously adapts to maximize learning.

---

# 🏗 PROJECT OVERVIEW

## Frontend

- React
- Vite
- TypeScript
- Tailwind CSS
- React Router
- Axios

## Backend

- Spring Boot
- Java
- Spring Security
- JWT Authentication
- Hibernate
- Spring Data JPA

## Database

- PostgreSQL

## AI

- Google Gemini

## Deployment

Frontend

- Vercel

Backend

- Railway
- Render
- AWS

Database

- PostgreSQL

Containerization

- Docker

---

# 🏛 PROJECT ARCHITECTURE

```
React Frontend
        │
        ▼
Reusable Components
        │
        ▼
Service Layer
        │
        ▼
REST API
        │
        ▼
Spring Controllers
        │
        ▼
Service Layer
        │
        ▼
Repositories
        │
        ▼
PostgreSQL
        │
        ▼
Gemini AI
```

---

# 📁 REPOSITORY STRUCTURE

```
CodeMentorAI/

├── frontend/
├── backend/
├── ai-service/
├── database/
├── docker/
├── docs/
├── scripts/

```

---

# 📚 DOCUMENTATION STRUCTURE

```
PROJECT_MASTER_CONTEXT.md
BACKEND.md
FRONTEND.md
DATABASE.md
API_DOCUMENTATION.md
ROADMAP.md
CHANGELOG.md
DEPLOYMENT.md
```

---

# 📌 DOCUMENT PRIORITY

If documentation conflicts occur, use this priority:

1. PROJECT_MASTER_CONTEXT.md
2. CHANGELOG.md
3. ROADMAP.md
4. BACKEND.md
5. FRONTEND.md
6. DATABASE.md
7. API_DOCUMENTATION.md
8. DEPLOYMENT.md

---

# 🤖 AI STARTUP INSTRUCTIONS

Every new ChatGPT conversation should begin by following these steps.

---

## Step 1 — Read Documentation

Read the documentation in this order:

1. PROJECT_MASTER_CONTEXT.md
2. BACKEND.md
3. FRONTEND.md
4. DATABASE.md
5. API_DOCUMENTATION.md
6. ROADMAP.md
7. CHANGELOG.md

Never skip documentation.

---

## Step 2 — Inspect Existing Implementation

Before suggesting or writing any code:

- Inspect the frontend.
- Inspect the backend.
- Inspect reusable components.
- Inspect services.
- Inspect controllers.
- Inspect repositories.
- Inspect existing APIs.

Never assume something is missing.

Always verify.

---

## Step 3 — Development Principles

Always:

- Preserve architecture.
- Reuse existing components.
- Reuse existing services.
- Reuse existing DTOs.
- Extend existing APIs.
- Keep code modular.
- Maintain backward compatibility.
- Follow SOLID principles.
- Follow Clean Architecture.
- Keep business logic inside services.

Never:

- Duplicate controllers.
- Duplicate services.
- Duplicate repositories.
- Duplicate APIs.
- Duplicate components.
- Rewrite completed modules.
- Introduce parallel implementations.

---

## Step 4 — File Creation Rules

Before creating a new file:

1. Search for an existing implementation.
2. Extend an existing file whenever possible.
3. Create a new file only when architecturally justified.

---

## Step 5 — Documentation Rules

Every completed feature must update:

- ROADMAP.md
- CHANGELOG.md

If architecture changes:

- BACKEND.md
- FRONTEND.md
- DATABASE.md
- API_DOCUMENTATION.md

Documentation must always match the implementation.

---

## Step 6 — Git Workflow

Every feature follows:

```
Plan

↓

Inspect Existing Code

↓

Implementation

↓

Testing

↓

Documentation

↓

Commit

↓

Push
```

Use meaningful commit messages.

---

## Step 7 — Code Standards

Every implementation must:

- Be production-ready.
- Be reusable.
- Follow the existing architecture.
- Be responsive.
- Maintain type safety.
- Use existing services.
- Avoid unnecessary refactoring.

---

## Step 8 — Final Rule

If implementation details are uncertain:

Inspect the repository.

Never guess.

Always extend the existing CodeMentorAI architecture.

---

# 🚀 CURRENT PROJECT STATUS
## Current Development Phase

🟡 Release Candidate (RC)

The project has transitioned from feature development to production readiness.

The core platform, AI systems, learning intelligence, and user experience have been implemented.

Current work focuses on:

- Production polish
- Testing
- Documentation synchronization
- Deployment
- Performance optimization

---

# 📊 PROJECT COMPLETION STATUS

| Area | Progress |
|--------|-----------|
| Backend | ✅ 98% |
| Frontend | ✅ 95% |
| AI Intelligence | ✅ 100% |
| Database | ✅ 100% |
| Authentication | ✅ 100% |
| Admin Platform | ✅ 100% |
| Documentation | 🟡 Updating |
| Testing | 🟡 In Progress |
| Deployment | ❌ Pending |

Overall Project Progress

🚀 ~96%

---

# ✅ COMPLETED MODULES

## Core Platform

- Authentication
- Authorization
- JWT Security
- User Management
- Problem Management
- Topic Management
- Code Execution
- Submission System
- Test Case Management

---

## AI Mentor

Completed

- AI Analysis
- AI Explanation
- Progressive Hints
- AI Chat
- Concept Detection
- Adaptive Responses
- Personalized Guidance

---

## AI Learning Intelligence

Completed

- Learning Analytics
- Developer Skill Graph
- Concept Growth
- Personalized Learning Plan
- Personalized Revision Plan
- Adaptive Mentor
- Practice Recommendation
- AI Mistake Memory
- Recurring Mistake Detection
- Past Mistake Recall

---

## User Experience

Completed

- Developer Dashboard
- Topic Dashboard
- Problem Workspace
- Growth Report
- Personalized Interview
- Solution Evolution Timeline
- Developer Activity Calendar
- Independent Solve Mode
- Admin Dashboard
- Platform Analytics
- AI Analytics

---

## Admin Platform

Completed

- User Management
- Problem Management
- Topic Management
- Submission Management
- Dashboard Analytics
- Platform Statistics
- AI Analytics

---

# 🖥 FRONTEND STATUS

Completed

- Authentication
- Dashboard
- Problems
- Problem Workspace
- Topics
- Topic Dashboard
- Mistake Memory
- Developer Skills
- Learning Plan
- Revision Plan
- Growth Report
- Personalized Interview
- Solution Evolution
- Admin Dashboard
- Admin Analytics
- Developer Activity Calendar

Remaining

- Production UI Polish
- Responsive Audit
- Loading Skeleton Improvements
- Final Accessibility Review

---

# ⚙ BACKEND STATUS

Completed

- Authentication
- Authorization
- Problems
- Topics
- Submissions
- Execution
- AI Mentor
- AI Chat
- Progressive Hint
- AI Mistake Memory
- Adaptive Mentor
- Growth Report
- Learning Plan
- Revision Plan
- Interview Engine
- Developer Activity
- Independent Solve Mode
- Admin Platform

Remaining

- Logging Improvements
- Monitoring
- Rate Limiting
- Security Review
- Performance Optimization

---

# 🚀 CURRENT PRIORITIES

Priority 1

Production Polish

Priority 2

Testing

Priority 3

Documentation Synchronization

Priority 4

Deployment

---

# 📅 VERSION 1.0 RELEASE CHECKLIST

## Production Polish

- Responsive Verification
- Loading UX
- Error UX
- Empty States
- Accessibility Review
- Animation Consistency

---

## Testing

Frontend

- Authentication
- Dashboard
- Problems
- Workspace
- Topics
- AI Mentor
- Learning Plan
- Revision Plan
- Growth Report
- Interview
- Admin

Backend

- Authentication
- AI
- Problems
- Topics
- Submissions
- Admin

API

- Endpoint Validation
- Authorization
- Error Handling

---

## Deployment

Frontend

- Vercel

Backend

- Railway / Render

Database

- PostgreSQL

Infrastructure

- Docker

Environment Variables

Production Ready

---

# 🌟 VERSION 1.1 ROADMAP

After Version 1.0 release, planned features include:

## Contest Mode

- Daily Challenge
- Weekly Contest
- Leaderboard
- Rating System

---

## Public Developer Profile

- Public Shareable Profile
- Skill Graph
- Achievements
- Activity Calendar
- Solved Problems

---

## Recruiter Dashboard

- Candidate Overview
- AI Skill Analysis
- Growth Summary
- Interview Readiness
- Recruiter Insights

---

# 📋 DEVELOPMENT PRINCIPLES

Every future implementation must:

- Extend existing architecture.
- Reuse services whenever possible.
- Reuse components whenever possible.
- Avoid duplicate business logic.
- Maintain backward compatibility.
- Preserve modularity.
- Maintain production quality.

---

# 🤖 AI ASSISTANT RULES

When working on CodeMentorAI:

Always:

- Read documentation first.
- Inspect existing code.
- Verify APIs before implementation.
- Reuse existing services.
- Reuse existing components.
- Maintain architecture consistency.
- Update documentation after significant changes.

Never:

- Duplicate APIs.
- Duplicate Services.
- Duplicate Controllers.
- Duplicate Repositories.
- Rebuild completed features.
- Introduce parallel implementations.
- Guess project structure.

---

# 🏁 FINAL STATEMENT

CodeMentorAI is now in the Release Candidate phase.

The primary objective is no longer feature development.

The focus has shifted to delivering a polished, production-ready AI Coding Mentor platform through refinement, testing, deployment, and continuous quality improvements.

Every future development session should begin by reading this document to ensure consistency, architectural integrity, and continuity across the project.