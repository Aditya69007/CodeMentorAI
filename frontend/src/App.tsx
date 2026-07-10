import { Navigate, Route, Routes } from "react-router-dom";

import LoginPage from "./pages/auth/LoginPage";
import RegisterPage from "./pages/auth/RegisterPage";
import ProblemsPage from "./pages/user/ProblemsPage";
import ProblemSolvePage from "./pages/user/ProblemSolvePage";
import MistakeMemoryPage from "./pages/user/MistakeMemoryPage";
import ProtectedRoute from "./routes/ProtectedRoute";
import AppLayout from "./components/layout/AppLayout";
import TopicsPage from "./pages/user/TopicsPage";
import TopicProblemsPage from "./pages/user/TopicProblemsPage";

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/problems" replace />} />

      <Route path="/login" element={<LoginPage />} />

      <Route path="/register" element={<RegisterPage />} />

      <Route element={<ProtectedRoute />}>
        <Route element={<AppLayout />}>
          <Route path="/problems" element={<ProblemsPage />} />
          <Route
            path="/mistake-memory"
            element={<MistakeMemoryPage />}
          />
          <Route
            path="/problems/:id"
            element={<ProblemSolvePage />}
          />
          <Route
            path="/topics"
            element={<TopicsPage />}
          />

          <Route
            path="/topics/:slug"
            element={<TopicProblemsPage />}
          />
        </Route>
      </Route>

      <Route path="*" element={<Navigate to="/problems" replace />} />
    </Routes>
  );
}