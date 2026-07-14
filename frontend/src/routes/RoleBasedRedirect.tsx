import {
  Navigate,
} from "react-router-dom";

import {
  useAuth,
} from "../hooks/useAuth";


export default function RoleBasedRedirect() {

  const {
    isAuthenticated,
    isAdmin,
  } = useAuth();


  if (!isAuthenticated) {

    return (
      <Navigate
        to="/login"
        replace
      />
    );

  }


  if (isAdmin) {

    return (
      <Navigate
        to="/admin/dashboard"
        replace
      />
    );

  }


  return (
    <Navigate
      to="/dashboard"
      replace
    />
  );
}