# 🚀 CodeMentorAI

> **An AI-Powered Coding Career Platform that helps developers solve problems, learn from mistakes, track progress, and continuously improve.**

CodeMentorAI is a full-stack platform designed to provide an intelligent and personalized coding journey. It combines problem solving, AI-powered mentorship, mistake analysis, adaptive learning, growth analytics, independent solving, personalized interviews, and developer portfolio insights into one platform.

---

## ✨ Key Features

### 💻 Coding & Problem Solving
- Solve coding problems directly in the platform
- Multi-language code editor
- Run and submit code
- Test case execution
- Submission history
- Performance tracking
- Problem and topic management

### 🤖 AI Mentor
- AI-powered code analysis
- Clear explanations for coding mistakes
- Step-by-step guidance
- Progressive hints
- Concept clarification
- Code improvement suggestions
- Alternative approaches
- Best-practice recommendations

### 🧠 AI Mistake Memory
- Detect coding mistakes automatically
- Store mistakes with context
- Track recurring mistakes
- Identify weak concepts
- Analyze improvement patterns
- Use previous mistakes for personalized guidance

### 🎯 Adaptive Learning
- Personalized learning plans
- Practice recommendations
- Concept growth analysis
- Revision planning
- Skill gap identification
- Recommended topics and problems
- Adaptive recommendations based on user progress

### 🔥 Independent Solve Mode
- Solve problems without AI assistance
- Focus on independent problem-solving ability
- Track solving sessions
- Analyze performance after submission
- Identify mistakes and improvement areas

### 📈 Growth Intelligence
- Track coding activity and performance
- Analyze strengths and weaknesses
- Concept mastery tracking
- Learning behavior analysis
- Mistake trends
- Progress over time
- Personalized growth insights

### 🔄 Solution Evolution
- Track multiple versions of a solution
- Compare previous and improved solutions
- Analyze improvements over time
- Compare time and space complexity
- Track code quality improvements
- Maintain a solution evolution timeline

### 🎤 Personalized Interview
- Personalized interview questions
- AI-generated interview guidance
- Interview recommendations
- Weak-area-focused preparation
- Progress tracking

### 🌐 Developer Profile & Portfolio
- GitHub integration
- LeetCode integration
- Developer analytics
- AI-generated developer summary
- AI skills summary
- Portfolio scoring
- Public developer portfolio

### 🛡️ Authentication & Security
- JWT authentication
- Google OAuth
- Role-based access control
- User, Admin, and Super Admin access
- Password management
- Secure account management

### 👨‍💼 Admin Platform
- User management
- Problem management
- Topic management
- Submission monitoring
- Content management
- Platform analytics
- System monitoring

---

# 🏗️ Platform Architecture

The following diagrams represent the major workflows and intelligence systems inside CodeMentorAI.

---

## 1️⃣ CodeMentorAI Platform Flow & Features

![CodeMentorAI Platform Flow](assets/diagrams/CodeMentorAI%20-%20System%20Diagram.png)

This diagram provides a complete overview of the platform, including:

- User, Admin, and Super Admin roles
- Authentication and authorization
- Core coding features
- Growth and learning modules
- AI Mentor and Mistake Memory
- Growth Intelligence Engine
- Personalized outputs and recommendations

---

## 2️⃣ AI Mentor Flow

![CodeMentorAI AI Flow](assets/diagrams/CodeMentorAI%20-%20AI%20Flow%20Diagram.png)

The AI Mentor analyzes the user's problem, code, and context to provide:

- Clear explanations
- Step-by-step guidance
- Progressive hints
- Concept clarification
- Code improvement suggestions
- Personalized responses

The system also detects and stores mistakes to continuously improve future guidance.

---

## 3️⃣ Independent Solve Flow

![CodeMentorAI Independent Solve Flow](assets/diagrams/CodeMentorAI%20-%20Independent%20Diagram.png)

Independent Solve Mode helps users strengthen their problem-solving ability without relying on AI assistance.

The workflow includes:

- Starting an independent solving session
- Writing, testing, and debugging code
- Submitting the final solution
- Evaluating the submission
- Detecting mistakes and improvement areas
- Updating the user's learning profile
- Generating personalized recommendations

---

## 4️⃣ Solution Evolution Flow

![CodeMentorAI Solution Evolution Flow](assets/diagrams/CodeMentorAI%20-%20Solution%20Evolution%20Diagram.png)

Solution Evolution tracks how a developer improves their solution over multiple attempts.

It includes:

- Submission version tracking
- AI code analysis
- Feedback and optimization suggestions
- Improved solution resubmission
- Solution snapshots
- Version comparison
- Performance comparison
- Code quality improvements
- Learning and growth insights

---

## 5️⃣ Growth Intelligence Flow

![CodeMentorAI Growth Intelligence Flow](assets/diagrams/CodeMentorAI%20-%20Growth%20Intelligence%20Flow.png)

The Growth Intelligence system collects data from across the platform and transforms it into actionable insights.

It analyzes:

- Submissions
- Problems solved
- Mistakes and errors
- AI interactions
- Independent solving sessions
- Contests and ratings
- Interview activity
- Learning behavior

The system then generates:

- Growth metrics
- Skill analysis
- Strengths and weaknesses
- Personalized recommendations
- Learning priorities
- Revision plans
- Career and interview insights

---

# 🛠️ Tech Stack

## Frontend

- React
- TypeScript
- Vite
- Tailwind CSS
- React Router
- Axios
- Monaco Editor
- Chart.js

## Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- Maven

## Database & Infrastructure

- PostgreSQL
- Redis
- Docker
- Docker Compose

## AI & Integrations

- Google Gemini
- GitHub API
- LeetCode GraphQL API

---

# 📂 Project Structure

```text
CodeMentorAI/
│
├── .github/                    # GitHub configuration
├── .vscode/                    # VS Code configuration
│
├── assets/
│   └── diagrams/               # Platform architecture diagrams
│       ├── CodeMentorAI - AI Flow Diagram.png
│       ├── CodeMentorAI - Growth Intelligence Flow.png
│       ├── CodeMentorAI - Independent Diagram.png
│       ├── CodeMentorAI - Solution Evolution Diagram.png
│       └── CodeMentorAI - System Diagram.png
│
├── backend/                    # Spring Boot backend
│   ├── src/
│   │   ├── main/
│   │   └── test/
│   └── pom.xml
│
├── frontend/                   # React + TypeScript frontend
│
├── database/
│   ├── diagrams/
│   │   └── ERD.md
│   ├── migrations/
│   ├── schema/
│   ├── seeds/
│   └── database-design.md
│
├── docs/                       # Project documentation
│
├── .gitignore
├── docker-compose.yml
├── LICENSE
└── README.md