import {
  FiAlertCircle,
  FiCheckCircle,
  FiClock,
  FiCpu,
  FiXCircle,
} from "react-icons/fi";

import type {
  ExecutionResult,
} from "../../types/execution";


interface Props {

  result: ExecutionResult | null;

  error: string;

  input: string;

  expectedOutput: string;

  caseNumber: number;

}


export default function RunResultPanel({

  result,

  error,

  input,

  expectedOutput,

  caseNumber,

}: Props) {


  // ==========================================
  // REQUEST ERROR
  // ==========================================

  if (error) {

    return (

      <div className="h-full overflow-y-auto p-5">


        <div className="flex items-center gap-3 text-red-500">

          <FiAlertCircle size={22} />

          <p className="font-semibold">

            Run Failed

          </p>

        </div>


        <div className="app-surface-secondary app-border mt-4 rounded-md border p-4">

          <pre className="whitespace-pre-wrap text-sm text-red-400">

            {error}

          </pre>

        </div>


      </div>

    );

  }


  // ==========================================
  // EMPTY RESULT
  // ==========================================

  if (!result) {

    return (

      <div className="flex h-full items-center justify-center p-5">

        <div className="text-center">

          <FiCpu
            size={28}
            className="app-text-muted mx-auto"
          />

          <p className="mt-3 font-semibold">

            No run result

          </p>

          <p className="app-text-secondary mt-1 text-sm">

            Select a testcase and run your code.

          </p>

        </div>

      </div>

    );

  }


  // ==========================================
  // RESULT STATUS
  // ==========================================

  const passed =
    result.status === "ACCEPTED";


  return (

    <div className="h-full overflow-y-auto p-5">


      {/* ==========================================
          STATUS
      ========================================== */}

      <div className="flex items-center gap-3">


        {
          passed
            ? (

              <FiCheckCircle
                size={28}
                className="text-emerald-500"
              />

            )
            : (

              <FiXCircle
                size={28}
                className="text-red-500"
              />

            )
        }


        <div>

          <p
            className={`text-lg font-bold ${
              passed
                ? "text-emerald-500"
                : "text-red-500"
            }`}
          >

            {
              passed
                ? "PASSED"
                : result.status.replaceAll(
                    "_",
                    " "
                  )
            }

          </p>


          <p className="app-text-muted mt-1 text-xs">

            Case {caseNumber}

          </p>

        </div>


      </div>


      {/* ==========================================
          EXECUTION INFORMATION
      ========================================== */}

      <div className="mt-5 grid grid-cols-1 gap-3 sm:grid-cols-2">


        <div className="app-surface-secondary app-border rounded-md border p-4">

          <div className="app-text-muted flex items-center gap-2 text-xs">

            <FiClock />

            Runtime

          </div>

          <p className="mt-2 font-semibold">

            {result.executionTime ?? 0} ms

          </p>

        </div>


        <div className="app-surface-secondary app-border rounded-md border p-4">

          <div className="app-text-muted flex items-center gap-2 text-xs">

            <FiCpu />

            Memory

          </div>

          <p className="mt-2 font-semibold">

            {result.memoryUsed ?? 0}

          </p>

        </div>


      </div>


      {/* ==========================================
          INPUT
      ========================================== */}

      <div className="mt-5">

        <p className="app-text-secondary text-sm font-medium">

          Input

        </p>

        <pre className="app-surface-secondary app-border mt-2 overflow-x-auto whitespace-pre-wrap rounded-md border p-4 font-mono text-sm">

          {input.trim()}

        </pre>

      </div>


      {/* ==========================================
          EXPECTED OUTPUT
      ========================================== */}

      <div className="mt-5">

        <p className="app-text-secondary text-sm font-medium">

          Expected Output

        </p>

        <pre className="app-surface-secondary app-border mt-2 overflow-x-auto whitespace-pre-wrap rounded-md border p-4 font-mono text-sm">

          {expectedOutput.trim()}

        </pre>

      </div>


      {/* ==========================================
          ACTUAL OUTPUT
      ========================================== */}

      <div className="mt-5">

        <p className="app-text-secondary text-sm font-medium">

          Actual Output

        </p>

        <pre className="app-surface-secondary app-border mt-2 min-h-[52px] overflow-x-auto whitespace-pre-wrap rounded-md border p-4 font-mono text-sm">

          {
            result.output?.trim() ||
            "No output"
          }

        </pre>

      </div>


      {/* ==========================================
          ERROR MESSAGE
      ========================================== */}

      {
        result.errorMessage?.trim() && (

          <div className="mt-5">

            <p className="text-sm font-medium text-red-500">

              Error

            </p>

            <pre className="mt-2 overflow-x-auto whitespace-pre-wrap rounded-md border border-red-500/30 bg-red-500/10 p-4 font-mono text-sm text-red-400">

              {result.errorMessage.trim()}

            </pre>

          </div>

        )
      }


    </div>

  );

}