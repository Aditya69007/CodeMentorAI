import {
  Route,
  Routes,
} from "react-router-dom";
import SessionsPage from "./pages/settings/SessionsPage";
import { NotificationProvider } from "./context/NotificationContext";
import ForgotPasswordPage from "./pages/auth/ForgotPasswordPage";
import ResetPasswordPage from "./pages/auth/ResetPasswordPage";
import AdminTopicsPage from "./pages/admin/AdminTopicsPage";
import AdminProblemsPage from "./pages/admin/AdminProblemsPage";
import AdminEditProblemPage from "./pages/admin/AdminEditProblemPage";
import LoginPage from "./pages/auth/LoginPage";
import RegisterPage from "./pages/auth/RegisterPage";
import OAuthSuccess from "./pages/auth/OAuthSuccess";
import AdminLayout from "./components/admin/layout/AdminLayout";
import AdminUsersPage from "./pages/admin/AdminUsersPage";
import ProblemsPage from "./pages/user/ProblemsPage";
import ProblemSolvePage from "./pages/user/ProblemSolvePage";
import MistakeMemoryPage from "./pages/user/MistakeMemoryPage";
import TopicsPage from "./pages/user/TopicsPage";
import TopicProblemsPage from "./pages/user/TopicProblemsPage";
import AdminUserDetailsPage from "./pages/admin/AdminUserDetailsPage";
import AdminDashboardPage from "./pages/admin/AdminDashboardPage";
import AdminCreateProblemPage from "./pages/admin/AdminCreateProblemPage";
import AdminSubmissionsPage from "./pages/admin/AdminSubmissionsPage";
import ProfilePage from "./pages/account/ProfilePage";
import SettingsPage from "./pages/account/SettingsPage";
import PortfolioPage from "./pages/portfolio/PortfolioPage";

import UserRoute from "./routes/UserRoute";
import AdminRoute from "./routes/AdminRoute";
import RoleBasedRedirect from "./routes/RoleBasedRedirect";

import AppLayout from "./components/layout/AppLayout";

import GrowthReportPage
  from "./pages/user/GrowthReportPage";

import AdminAiAnalyticsPage
  from "./pages/admin/AdminAiAnalyticsPage";

import AdminPlatformAnalyticsPage
  from "./pages/admin/AdminPlatformAnalyticsPage";

import DeveloperSkillGraphPage
  from "./pages/DeveloperSkillGraphPage";

import PersonalizedLearningPlanPage
  from "./components/ai/PersonalizedLearningPlanPage";

import PersonalizedRevisionPlanPage
  from "./pages/user/PersonalizedRevisionPlanPage";

import PersonalizedInterviewPage
  from "./pages/user/PersonalizedInterviewPage";

import UserDashboardPage
  from "./pages/user/UserDashboardPage";


export default function App() {

  return (
  <NotificationProvider>
      <Routes>


        {/* ==========================================
            ROOT REDIRECT
        ========================================== */}

        <Route
          path="/"
          element={<RoleBasedRedirect />}
        />


        {/* ==========================================
            PUBLIC ROUTES
        ========================================== */}

        <Route
          path="/login"
          element={<LoginPage />}
        />

        <Route
          path="/register"
          element={<RegisterPage />}
        />

        <Route
            path="/forgot-password"
            element={<ForgotPasswordPage />}
        />

        <Route
            path="/reset-password"
            element={<ResetPasswordPage />}
        />

        <Route
            path="/oauth-success"
            element={<OAuthSuccess />}
        />

        {/* ==========================================
            USER PLATFORM
        ========================================== */}

        <Route element={<UserRoute />}>


          {/* ==========================================
              FULL-SCREEN CODING WORKSPACE

              IMPORTANT:
              This route is intentionally outside
              AppLayout.

              Therefore ProblemSolvePage will NOT
              display the platform sidebar.
          ========================================== */}

          <Route
            path="/problems/:id"
            element={<ProblemSolvePage />}
          />


          {/* ==========================================
              USER PLATFORM WITH SIDEBAR
          ========================================== */}

          <Route element={<AppLayout />}>


            {/* DASHBOARD */}

            <Route
              path="/dashboard"
              element={<UserDashboardPage />}
            />


            {/* PROBLEMS */}

            <Route
              path="/problems"
              element={<ProblemsPage />}
            />


            {/* TOPICS */}

            <Route
              path="/topics"
              element={<TopicsPage />}
            />


            <Route
              path="/topics/:slug"
              element={<TopicProblemsPage />}
            />


            {/* MISTAKE MEMORY */}

            <Route
              path="/mistake-memory"
              element={<MistakeMemoryPage />}
            />


            {/* DEVELOPER SKILLS */}

            <Route
              path="/developer-skills"
              element={<DeveloperSkillGraphPage />}
            />


            {/* PERSONALIZED LEARNING PLAN */}

            <Route
              path="/learning-plan"
              element={<PersonalizedLearningPlanPage />}
            />


            {/* PERSONALIZED REVISION PLAN */}

            <Route
              path="/revision-plan"
              element={<PersonalizedRevisionPlanPage />}
            />


            {/* GROWTH REPORT */}

            <Route
              path="/growth-report"
              element={<GrowthReportPage />}
            />


            {/* PERSONALIZED INTERVIEW */}

            <Route
              path="/interview"
              element={<PersonalizedInterviewPage />}
            />

            {/* ACCOUNT */}

            <Route
              path="/account/profile"
              element={<ProfilePage />}
            />

            <Route
              path="/account/settings"
              element={<SettingsPage />}
            />

            <Route
              path="/account/sessions"
              element={<SessionsPage />}
            />

            {/* PORTFOLIO */}
            <Route
                path="/portfolio"
                element={<PortfolioPage />}
            />

          </Route>

        </Route>


        {/* ==========================================
            ADMIN PLATFORM
        ========================================== */}

        <Route element={<AdminRoute />}>


          <Route element={<AdminLayout />}>


            {/* PLATFORM ANALYTICS */}

            <Route
              path="/admin/analytics"
              element={<AdminPlatformAnalyticsPage />}
            />


            {/* AI ANALYTICS */}

            <Route
              path="/admin/ai-analytics"
              element={<AdminAiAnalyticsPage />}
            />


            {/* TOPICS */}

            <Route
              path="/admin/topics"
              element={<AdminTopicsPage />}
            />


            {/* PROBLEMS */}

            <Route
              path="/admin/problems"
              element={<AdminProblemsPage />}
            />


            {/* ADMIN DASHBOARD */}

            <Route
              path="/admin/dashboard"
              element={<AdminDashboardPage />}
            />


            {/* USERS */}

            <Route
              path="/admin/users"
              element={<AdminUsersPage />}
            />


            <Route
              path="/admin/users/:userId"
              element={<AdminUserDetailsPage />}
            />


            {/* CREATE PROBLEM */}

            <Route
              path="/admin/problems/create"
              element={<AdminCreateProblemPage />}
            />


            {/* EDIT PROBLEM */}

            <Route
              path="/admin/problems/:problemId/edit"
              element={<AdminEditProblemPage />}
            />


            {/* SUBMISSIONS */}

            <Route
              path="/admin/submissions"
              element={<AdminSubmissionsPage />}
            />


          </Route>

        </Route>


        {/* ==========================================
            FALLBACK
        ========================================== */}

        <Route
          path="*"
          element={<RoleBasedRedirect />}
        />


      </Routes>
  </NotificationProvider>
  
);

}