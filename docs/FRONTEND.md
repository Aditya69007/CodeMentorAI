# 🎨 CodeMentorAI — FRONTEND DOCUMENTATION

> Complete frontend architecture documentation.

---

# FRONTEND OVERVIEW

| Item | Value |
|------|-------|
| Framework | React |
| Build Tool | Vite |
| Language | TypeScript |
| Styling | Tailwind CSS |
| Routing | React Router DOM |
| HTTP Client | Axios |
| Architecture | Component-Based |
| Current Status | 🟡 Release Candidate (RC) |

---

# FRONTEND VISION

The frontend is no longer just a collection of pages.

It is the presentation layer of the **Developer Identity Platform**.

Every screen should contribute to one unified Developer Identity.

```
Developer Identity

↓

Profile

Portfolio

GitHub

LeetCode

Growth

Interview

Resume

AI Mentor
```

The frontend should always consume reusable services and reusable components.

---

# PROJECT STRUCTURE

```
frontend/

src/

components/

common/
account/
portfolio/
dashboard/
workspace/
admin/

pages/

account/
portfolio/
admin/
auth/

services/

types/

hooks/

context/

layouts/

routes/

utils/

assets/

App.tsx

main.tsx
```

---

# APPLICATION ARCHITECTURE

```
React

↓

Pages

↓

Reusable Components

↓

Service Layer

↓

Axios

↓

Spring Boot REST API

↓

Backend
```

---

# CURRENT ROUTES

## Authentication

- Login
- Register
- Google OAuth Success

---

## User Platform

- Dashboard
- Problems
- Workspace
- Topics
- Topic Dashboard
- Mistake Memory
- Developer Skills
- Learning Plan
- Revision Plan
- Growth Report
- Personalized Interview
- Profile
- Settings
- Portfolio

---

## Admin Platform

- Dashboard
- Users
- Problems
- Topics
- Submissions
- Platform Analytics
- AI Analytics

---

# MAJOR FRONTEND MODULES

## Authentication

✅ Complete

Includes

- JWT Login
- Google OAuth
- Protected Routes

---

## Dashboard

✅ Complete

---

## Problems

✅ Complete

---

## Workspace

✅ Complete

Includes

- Monaco Editor
- AI Mentor
- Progressive Hints
- AI Chat
- Independent Solve Mode
- Solution Evolution

---

## Learning Intelligence

✅ Complete

Includes

- Growth Report
- Learning Plan
- Revision Plan
- Interview
- Developer Skills
- Mistake Memory

---

## Account Center

✅ Complete

Includes

- Profile
- Settings
- UserHero
- UserAvatar

---

## Portfolio

🟡 Production Polish

Includes

- Portfolio Hero
- AI Summary
- AI Skill Analysis
- Portfolio Stats
- Projects
- Coding Profiles

Current backend integration

- User Profile
- Growth Report
- Developer Skills

Remaining

- Dynamic Projects
- GitHub Integration
- LeetCode Integration

---

# COMMON COMPONENTS

Current reusable components

- UserAvatar
- UserHero
- Cards
- Buttons
- Layout Components

Future reusable components

- GitHubCard
- LeetCodeCard
- ResumeSection
- RecruiterSummaryCard

---

# SERVICES

Current

- api.ts
- authService.ts
- userService.ts
- portfolioService.ts
- aiMentorService.ts
- problemService.ts
- submissionService.ts
- topicService.ts
- developerActivityService.ts
- personalizedInterviewService.ts

Every backend request must go through the service layer.

---

# TYPES

Includes

- Auth
- User
- Problems
- Topics
- Submissions
- Growth Report
- Developer Skills
- Learning Plan
- Revision Plan
- Hint Dependency
- Interview
- Portfolio
- Developer Activity

---

# STATE MANAGEMENT

Current

- useState
- useEffect
- Custom Hooks
- Context API

Architecture

Service-driven

---

# DESIGN PRINCIPLES

- AI-first
- Responsive
- Dark Theme
- Reusable Components
- Minimal UI
- Production Ready
- Component Reuse
- Type Safety

---

# DEVELOPER IDENTITY

The frontend should always build a unified Developer Identity.

Developer Identity combines

- User Profile
- GitHub
- LeetCode
- AI Growth
- Skills
- Portfolio
- Resume
- Interview
- Learning Analytics

Every future page should strengthen this identity.

---

# CURRENT FRONTEND STATUS

Authentication

✅

Dashboard

✅

Problems

✅

Workspace

✅

Learning Intelligence

✅

Profile

✅

Settings

✅

Portfolio

🟡 Polish

Admin

✅

Overall Progress

🚀 ~97%

---

# NEXT FRONTEND ROADMAP

Phase 1

Developer Identity

- GitHub Integration
- LeetCode Integration
- Dynamic Projects

Phase 2

Portfolio

- AI Portfolio Polish
- Public Portfolio

Phase 3

Resume

- AI Resume Generator
- ATS Resume
- PDF Export

Phase 4

Recruiter Platform

- Recruiter Dashboard
- AI Candidate Summary

---

# DEVELOPMENT RULES

Always

- Reuse Components
- Reuse Services
- Keep Pages Small
- Maintain Type Safety
- Preserve Architecture
- Extend Existing Components

Never

- Duplicate Components
- Duplicate Services
- Duplicate Logic
- Create Parallel Implementations
- Break Existing Architecture

---

# FINAL PRINCIPLE

Every frontend feature must answer one question:

"Does this improve the Developer Identity?"

If YES

Build it.

If NO

Reconsider the feature.

The frontend is no longer page-driven.

It is Developer Identity driven.