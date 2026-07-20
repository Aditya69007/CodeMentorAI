# 🌐 CodeMentorAI - API DOCUMENTATION

> Complete REST API Reference

---

# BASE URL

```
/api/v1
```

Architecture

```
React

↓

Axios

↓

Spring Boot REST API

↓

Service Layer

↓

Repository

↓

PostgreSQL
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
```

Authentication

JWT Bearer Token

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

# ⚡ CODE EXECUTION

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
```

---

# 📈 SOLUTION EVOLUTION

Status

✅ COMPLETE

Endpoints

```
GET /ai-mentor/solution-evolution/{problemId}
```

Returns

- Timeline
- Attempt History
- Improvement
- Regression
- AI Insights

---

# 💡 HINT DEPENDENCY

Status

✅ COMPLETE

Endpoints

```
GET /ai-mentor/hint-dependency/me
```

Returns

- Dependency Score
- Dependency Level
- Usage Statistics
- Recommendation

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

# 📈 ADMIN AI ANALYTICS

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

# 📦 RESPONSE FORMAT

Every endpoint returns a consistent JSON response using appropriate HTTP status codes.

Supported Status Codes

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

---

# 📋 DEVELOPMENT RULES

- Never duplicate APIs.
- Extend existing endpoints whenever possible.
- Keep controllers thin.
- Keep business logic inside services.
- Preserve backward compatibility.
- Update this document whenever new endpoints are added.