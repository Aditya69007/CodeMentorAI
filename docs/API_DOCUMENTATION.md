# 🌐 CodeMentorAI — API DOCUMENTATION

> Complete REST API Reference

---

# API OVERVIEW

| Item | Value |
|------|-------|
| Base URL | `/api/v1` |
| Authentication | JWT Bearer Token |
| Response Format | JSON |
| Current Status | 🟡 Release Candidate (RC) |

---

# API VISION

The REST API is the communication layer of the **Developer Identity Platform**.

Every API should either:

- Manage Developer Identity
- Improve AI Intelligence
- Enhance Learning Analytics
- Support Portfolio & Resume
- Provide Recruiter Insights

Future integrations (GitHub & LeetCode) will extend the existing architecture instead of creating separate systems.

---

# API ARCHITECTURE

```
React

↓

Axios

↓

REST API

↓

Controllers

↓

Services

↓

Repositories

↓

PostgreSQL

↓

AI Services

↓

Gemini
```

---

# 🔐 AUTHENTICATION

Status

✅ COMPLETE

Base

```
/auth
```

Endpoints

```
POST /auth/register

POST /auth/login

GET /auth/me
```

OAuth

```
GET /oauth2/authorization/google
```

Authentication

- JWT
- Google OAuth

---

# 👤 USER

Status

✅ COMPLETE

Base

```
/users
```

Endpoints

```
GET /users/me

PUT /users/me
```

Future

```
GET /users/profile
```

---

# 📚 TOPICS

Status

✅ COMPLETE

Base

```
/topics
```

Endpoints

```
GET /topics

GET /topics/{slug}

GET /topics/{slug}/progress

GET /topics/{slug}/problems

GET /topics/admin/all

POST /topics/admin

PUT /topics/admin/{id}

PATCH /topics/admin/{id}/status

DELETE /topics/admin/{id}
```

---

# 💻 PROBLEMS

Status

✅ COMPLETE

Base

```
/problems
```

Endpoints

```
GET /problems

GET /problems/{id}

POST /problems

PUT /problems/{id}

DELETE /problems/{id}

GET /problems/search

GET /problems/filter

GET /problems/filter/me

GET /problems/difficulty/{difficulty}

GET /problems/progress/me

GET /problems/solved/me
```

---

# 🧪 TEST CASES

Status

✅ COMPLETE

Base

```
/problems/{problemId}/test-cases
```

Endpoints

```
GET /

POST /

DELETE /{testCaseId}
```

---

# ⚡ EXECUTION

Status

✅ COMPLETE

Base

```
/executions
```

Endpoints

```
POST /run
```

---

# 📝 SUBMISSIONS

Status

✅ COMPLETE

Base

```
/submissions
```

Endpoints

```
POST /

GET /{id}

GET /problem/{problemId}

GET /me

GET /admin

GET /admin/{id}
```

---

# 🤖 AI MENTOR

Status

✅ COMPLETE

Base

```
/ai-mentor
```

Endpoints

```
POST /analyze/{submissionId}

GET /analysis/{submissionId}

POST /chat/{submissionId}

GET /chat/{submissionId}

POST /hint/{submissionId}/{level}
```

---

# 🧠 AI LEARNING INTELLIGENCE

Status

✅ COMPLETE

Endpoints

```
GET /ai-mentor/growth/me

GET /ai-mentor/growth-report/me

GET /ai-mentor/learning-plan/me

GET /ai-mentor/revision-plan/me

GET /ai-mentor/practice-recommendations/me

GET /ai-mentor/skills/me

GET /ai-mentor/adaptive-profile/me

GET /ai-mentor/interview-profile/me

GET /ai-mentor/solution-evolution/{problemId}

GET /ai-mentor/hint-dependency/me
```

---

# 🧠 AI MISTAKE MEMORY

Status

✅ COMPLETE

Endpoints

```
GET /ai-mentor/mistakes/{submissionId}

GET /ai-mentor/mistakes/summary/me

GET /ai-mentor/mistakes/profile/me

GET /ai-mentor/mistakes/recurring/me

GET /ai-mentor/mistakes/recall/{submissionId}
```

---

# 🎓 PERSONALIZED INTERVIEW

Status

✅ COMPLETE

Base

```
/interviews
```

Endpoints

```
POST /start

GET /active/me

GET /history/me

GET /{sessionId}

POST /{sessionId}/questions/{questionId}/answer
```

---

# 🚀 INDEPENDENT SOLVE MODE

Status

✅ COMPLETE

Base

```
/independent-solve
```

Endpoints

```
POST /start/{problemId}

POST /finish/{problemId}

GET /history/{problemId}

GET /active/{problemId}
```

---

# 📅 DEVELOPER ACTIVITY

Status

✅ COMPLETE

Base

```
/developer-activity
```

Endpoints

```
GET /me
```

---

# 📊 LEARNING ANALYTICS

Status

✅ COMPLETE

Base

```
/learning
```

Endpoints

```
GET /topics/{slug}
```

---

# 👨‍💼 ADMIN

Status

✅ COMPLETE

Base

```
/admin
```

Endpoints

```
GET /dashboard/stats

GET /dashboard/analytics

GET /users

GET /users/{userId}

DELETE /users/{userId}

GET /problems

GET /platform-analytics
```

---

# 📈 AI ANALYTICS

Status

✅ COMPLETE

Base

```
/ai-analytics
```

Endpoints

```
GET /admin
```

---

# 🚧 VERSION 2 APIs (Planned)

These APIs are part of the Developer Identity Platform roadmap and are **not yet implemented**.

## GitHub

```
POST /github/connect

GET /github/profile

PUT /github/refresh
```

Purpose

- Connect GitHub account
- Synchronize repositories
- Fetch languages
- Fetch stars
- Fetch pinned projects

---

## LeetCode

```
POST /leetcode/connect

GET /leetcode/profile

PUT /leetcode/refresh
```

Purpose

- Connect LeetCode username
- Synchronize solved problems
- Synchronize contest rating
- Synchronize topic statistics

---

## Portfolio

```
GET /portfolio/me

PUT /portfolio/me
```

Purpose

- Developer Portfolio
- Featured Projects
- Public Profile

---

## Resume

```
POST /resume/generate

GET /resume/me

GET /resume/download
```

Purpose

- AI Resume
- ATS Resume
- PDF Export

---

## Recruiter

```
GET /recruiter/candidate/{id}

GET /recruiter/summary/{id}
```

Purpose

- AI Candidate Summary
- Skill Analysis
- Growth Timeline

---

# 📦 RESPONSE FORMAT

Every endpoint returns JSON with standard HTTP status codes.

Supported

```
200 OK

201 Created

400 Bad Request

401 Unauthorized

403 Forbidden

404 Not Found

500 Internal Server Error
```

---

# 🔐 AUTHORIZATION

Protected endpoints require

```
Authorization

Bearer <JWT>
```

Google OAuth endpoints are handled through Spring Security OAuth.

---

# API DEVELOPMENT RULES

Always

- Extend existing APIs before creating new ones.
- Keep Controllers thin.
- Keep business logic inside Services.
- Preserve backward compatibility.
- Reuse DTOs whenever possible.
- Keep REST naming consistent.

Never

- Duplicate endpoints.
- Create parallel APIs.
- Expose database entities directly.
- Break existing API contracts.

---

# FINAL PRINCIPLE

Every new endpoint must answer one question:

**"Does this strengthen the Developer Identity?"**

If YES

Implement it.

If NO

Reconsider the API.

The REST API is no longer only a coding platform API.

It is the communication layer of the CodeMentorAI Developer Identity Platform.