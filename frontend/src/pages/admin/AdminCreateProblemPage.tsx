import {
  useState,
} from "react";

import {
  useNavigate,
} from "react-router-dom";

import {
  FiArrowLeft,
} from "react-icons/fi";

import AdminProblemForm
  from "../../components/problems/AdminProblemForm";

import {
  createProblem,
} from "../../services/problemService";

import type {
  ProblemRequest,
} from "../../types/problem";


export default function AdminCreateProblemPage() {

  const navigate = useNavigate();

  const [loading, setLoading] =
    useState(false);

  const [error, setError] =
    useState("");


  const handleCreateProblem = async (
    data: ProblemRequest
  ) => {

    try {

      setLoading(true);
      setError("");

      await createProblem(data);

      navigate("/admin/problems");

    } catch (error) {

      console.error(error);

      setError(
        "Unable to create problem. Please check the problem details and try again."
      );

    } finally {

      setLoading(false);

    }
  };


  return (

    <div className="mx-auto w-full max-w-6xl">


      {/* BACK BUTTON */}

      <button

        type="button"

        onClick={() =>
          navigate("/admin/problems")
        }

        className="app-hover app-text-secondary inline-flex items-center gap-2 rounded-lg px-3 py-2 text-sm"

      >

        <FiArrowLeft />

        Back to Problems

      </button>



      {/* PAGE HEADER */}

      <div className="mt-6">

        <p className="text-sm font-semibold uppercase tracking-wider text-blue-500">

          Problem Management

        </p>


        <h1 className="mt-2 text-3xl font-bold tracking-tight">

          Create Problem

        </h1>


        <p className="app-text-secondary mt-2 max-w-2xl">

          Add a new coding problem to the CodeMentor AI problem library.

        </p>

      </div>



      {/* FORM */}

      <div className="mt-8">

        <AdminProblemForm

          submitLabel="Create Problem"

          loading={loading}

          error={error}

          onSubmit={handleCreateProblem}

        />

      </div>


    </div>

  );
}