import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../hooks/useAuth";

export default function SuperAdminRoute() {
  const {
    isAuthenticated,
    isSuperAdmin,
  } = useAuth();

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  if (!isSuperAdmin) {
    return <Navigate to="/admin/dashboard" replace />;
  }

  return <Outlet />;
}