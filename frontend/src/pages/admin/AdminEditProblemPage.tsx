import {
  useEffect,
  useState,
} from "react";

import {
  useNavigate,
  useParams,
} from "react-router-dom";

import {
  FiArrowLeft,
} from "react-icons/fi";

import AdminProblemForm
  from "../../components/problems/AdminProblemForm";

import {
  getProblemById,
  updateProblem,
} from "../../services/problemService";

import type {
  ProblemRequest,
} from "../../types/problem";


export default function AdminEditProblemPage() {

  const navigate = useNavigate();

  const { problemId } =
    useParams<{ problemId: string }>();


  const [initialData, setInitialData] =
    useState<ProblemRequest | null>(null);

  const [loading, setLoading] =
    useState(true);

  const [saving, setSaving] =
    useState(false);

  const [error, setError] =
    useState("");


  useEffect(() => {

    const loadProblem = async () => {

      if (!problemId) {

        setError("Invalid problem ID.");

        setLoading(false);

        return;
      }


      try {

        setLoading(true);

        setError("");


        const problem =
          await getProblemById(
            Number(problemId)
          );


        const editableProblem:
          ProblemRequest = {

          title:
            problem.title,

          description:
            problem.description,

          difficulty:
            problem.difficulty,

          constraints:
            problem.constraints ?? "",

          inputFormat:
            problem.inputFormat ?? "",

          outputFormat:
            problem.outputFormat ?? "",

          sampleInput:
            problem.sampleInput ?? "",

          sampleOutput:
            problem.sampleOutput ?? "",

          tags:
            problem.tags ?? [],

          examples:
            problem.examples.map(
              (example, index) => ({

                input:
                  example.input,

                output:
                  example.output,

                explanation:
                  example.explanation ?? "",

                orderIndex:
                  example.orderIndex ??
                  index + 1,

              })
            ),

        };


        setInitialData(
          editableProblem
        );

      } catch (error) {

        console.error(error);

        setError(
          "Unable to load problem."
        );

      } finally {

        setLoading(false);

      }

    };


    void loadProblem();

  }, [problemId]);


  const handleUpdateProblem = async (
    data: ProblemRequest
  ) => {

    if (!problemId) {
      return;
    }


    try {

      setSaving(true);

      setError("");


      await updateProblem(
        Number(problemId),
        data
      );


      navigate(
        "/admin/problems"
      );

    } catch (error) {

      console.error(error);

      setError(
        "Unable to update problem. Please check the problem details and try again."
      );

    } finally {

      setSaving(false);

    }

  };


  if (loading) {

    return (

      <div className="flex min-h-96 items-center justify-center">

        <div className="text-center">

          <div className="mx-auto h-8 w-8 animate-spin rounded-full border-2 border-blue-500 border-t-transparent" />

          <p className="app-text-secondary mt-4 text-sm">

            Loading problem...

          </p>

        </div>

      </div>

    );

  }


  if (!initialData) {

    return (

      <div className="mx-auto w-full max-w-6xl">

        <button

          type="button"

          onClick={() =>
            navigate(
              "/admin/problems"
            )
          }

          className="app-hover app-text-secondary inline-flex items-center gap-2 rounded-lg px-3 py-2 text-sm"

        >

          <FiArrowLeft />

          Back to Problems

        </button>


        <div className="mt-8 rounded-xl border border-red-500/30 bg-red-500/10 p-4 text-sm text-red-500">

          {error ||
            "Problem could not be loaded."}

        </div>

      </div>

    );

  }


  return (

    <div className="mx-auto w-full max-w-6xl">


      {/* BACK BUTTON */}

      <button

        type="button"

        onClick={() =>
          navigate(
            "/admin/problems"
          )
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

          Edit Problem

        </h1>


        <p className="app-text-secondary mt-2 max-w-2xl">

          Update the coding problem information,
          examples, tags, and specifications.

        </p>

      </div>



      {/* FORM */}

      <div className="mt-8">

        <AdminProblemForm

          initialData={initialData}

          submitLabel="Update Problem"

          loading={saving}

          error={error}

          onSubmit={handleUpdateProblem}

        />

      </div>


    </div>

  );

}