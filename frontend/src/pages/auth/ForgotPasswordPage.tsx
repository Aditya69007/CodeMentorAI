import { useState, type FormEvent } from "react";
import { Link } from "react-router-dom";
import { forgotPassword } from "../../services/authService";
import { getApiErrorMessage } from "../../services/api";

export default function ForgotPasswordPage() {

  const [email, setEmail] = useState("");

  const [loading, setLoading] = useState(false);

  const [error, setError] = useState("");

  const [success, setSuccess] = useState("");

  const handleSubmit = async (
    event: FormEvent<HTMLFormElement>
  ) => {

    event.preventDefault();

    setError("");
    setSuccess("");
    setLoading(true);

    try {

      const message = await forgotPassword({
        email,
      });

      setSuccess(message);

      setEmail("");

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
          Forgot Password
        </h1>

        <p className="mt-2 text-center text-slate-400">
          Enter your registered email to receive a password reset link.
        </p>

        {error && (

          <div className="mt-5 rounded-lg bg-red-500/10 p-3 text-sm text-red-400">

            {error}

          </div>

        )}

        {success && (

          <div className="mt-5 rounded-lg bg-emerald-500/10 p-3 text-sm text-emerald-400">

            {success}

          </div>

        )}

        <form
          onSubmit={handleSubmit}
          className="mt-6 space-y-5"
        >

          <div>

            <label className="mb-2 block text-sm text-slate-300">

              Email Address

            </label>

            <input
              type="email"
              value={email}
              onChange={(event) =>
                setEmail(event.target.value)
              }
              required
              className="w-full rounded-lg border border-slate-700 bg-slate-800 px-4 py-3 text-white outline-none focus:border-blue-500"
            />

          </div>

          <button
            type="submit"
            disabled={loading}
            className="w-full rounded-lg bg-blue-600 py-3 font-semibold text-white transition hover:bg-blue-500 disabled:cursor-not-allowed disabled:opacity-50"
          >

            {loading
              ? "Sending..."
              : "Send Reset Link"}

          </button>

        </form>

        <p className="mt-6 text-center text-sm text-slate-400">

          Remember your password?{" "}

          <Link
            to="/login"
            className="font-medium text-blue-400 hover:text-blue-300"
          >
            Login
          </Link>

        </p>

      </div>

    </div>

  );

}