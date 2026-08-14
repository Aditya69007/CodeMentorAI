import { useState, type FormEvent } from "react";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import { FiEye, FiEyeOff } from "react-icons/fi";
import { resetPassword } from "../../services/authService";
import { getApiErrorMessage } from "../../services/api";

export default function ResetPasswordPage() {

  const navigate = useNavigate();

  const [searchParams] = useSearchParams();

  const token = searchParams.get("token") ?? "";

  const [newPassword, setNewPassword] = useState("");

  const [confirmPassword, setConfirmPassword] = useState("");

  const [showNewPassword, setShowNewPassword] = useState(false);

  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  const [loading, setLoading] = useState(false);

  const [error, setError] = useState("");

  const [success, setSuccess] = useState("");



  const handleSubmit = async (
    event: FormEvent<HTMLFormElement>
  ) => {

    event.preventDefault();

    if (!token) {
      return;
    }

    setLoading(true);
    setError("");
    setSuccess("");

    try {

      const message = await resetPassword({

        token,

        newPassword,

        confirmPassword,

      });

      setSuccess(message);

      setNewPassword("");

      setConfirmPassword("");

      setTimeout(() => {

        navigate("/login");

      }, 3000);

    } catch (error) {

      setError(getApiErrorMessage(error));

    } finally {

      setLoading(false);

    }

  };

  return (

    <div className="flex min-h-screen items-center justify-center bg-slate-950 px-4">

      <div className="w-full max-w-md rounded-xl border border-slate-800 bg-slate-900 p-8 shadow-xl">
        <div className="mb-5 flex justify-center">
          <img
            src="/brain-logo.png"
            alt="CodeMentorAI"
            className="h-16 w-16 object-contain"
          />
        </div>
        <h1 className="text-center text-3xl font-bold text-white">

          Reset Password

        </h1>

        <p className="mt-2 text-center text-slate-400">

          Enter your new password.

        </p>

        {(!token || error) && (
        <div className="mt-5 rounded-lg bg-red-500/10 p-3 text-sm text-red-400">
            {token ? error : "Invalid or missing password reset token."}
        </div>
        )}

        {success && (

          <div className="mt-5 rounded-lg bg-emerald-500/10 p-3 text-sm text-emerald-400">

            {success}

            <div className="mt-2 text-xs">

              Redirecting to Login...

            </div>

          </div>

        )}

        <form
          onSubmit={handleSubmit}
          className="mt-6 space-y-5"
        >

          <div className="relative">

            <label className="mb-2 block text-sm text-slate-300">

              New Password

            </label>

            <input
              type={showNewPassword ? "text" : "password"}
              value={newPassword}
              onChange={(e) =>
                setNewPassword(e.target.value)
              }
              required
              className="w-full rounded-lg border border-slate-700 bg-slate-800 px-4 py-3 pr-12 text-white outline-none focus:border-blue-500"
            />

            <button
              type="button"
              onClick={() =>
                setShowNewPassword((v) => !v)
              }
              className="absolute right-4 top-1/2 -translate-y-1/2 mt-4 text-slate-400 hover:text-white"
            >
              {showNewPassword
                ? <FiEyeOff />
                : <FiEye />}
            </button>

          </div>

          <div className="relative">

            <label className="mb-2 block text-sm text-slate-300">

              Confirm Password

            </label>

            <input
              type={showConfirmPassword ? "text" : "password"}
              value={confirmPassword}
              onChange={(e) =>
                setConfirmPassword(e.target.value)
              }
              required
              className="w-full rounded-lg border border-slate-700 bg-slate-800 px-4 py-3 pr-12 text-white outline-none focus:border-blue-500"
            />

            <button
              type="button"
              onClick={() =>
                setShowConfirmPassword((v) => !v)
              }
              className="absolute right-4 top-1/2 -translate-y-1/2 mt-4 text-slate-400 hover:text-white"
            >
              {showConfirmPassword
                ? <FiEyeOff />
                : <FiEye />}
            </button>

          </div>

          <button
            type="submit"
            disabled={loading || !token}
            className="w-full rounded-lg bg-blue-600 py-3 font-semibold text-white hover:bg-blue-500 disabled:opacity-50"
          >
            {loading
              ? "Updating..."
              : "Reset Password"}
          </button>

        </form>

        <p className="mt-6 text-center text-sm text-slate-400">

          <Link
            to="/login"
            className="text-blue-400 hover:text-blue-300"
          >
            Back to Login
          </Link>

        </p>

      </div>

    </div>

  );

}