import { FiDownload, FiRotateCcw, FiFileText } from "react-icons/fi";
import { useState } from "react";
import toast from "react-hot-toast";
import { downloadDeveloperReport } from "../../services/exportService";
import ResetAiMemoryModal from "../../pages/settings/ResetAiMemoryModal";
import { resetAiMemory } from "../../services/mistakeMemoryService";

export default function DeveloperToolsCard() {
  const [downloading, setDownloading] = useState(false);

  const [resetOpen, setResetOpen] = useState(false);
  const [resetting, setResetting] = useState(false);

  const handleDownload = async () => {
    try {
      setDownloading(true);

      await downloadDeveloperReport();

      toast.success("Developer Report downloaded successfully.");
    } catch (error) {
      console.error(error);
      toast.error("Failed to download report.");
    } finally {
      setDownloading(false);
    }
  };

  const handleResetAiMemory = async () => {
    try {
      setResetting(true);

      await resetAiMemory();

      toast.success("AI memory cleared successfully.");

      setResetOpen(false);

    } catch (error) {

      console.error(error);

      toast.error("Failed to clear AI memory.");

    } finally {

      setResetting(false);

    }
  };

  return (
    <section className="rounded-2xl border border-blue-500/20 bg-blue-500/5 p-6">

      <div className="mb-6 flex items-center gap-3">

        <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-blue-500/10">

          <FiFileText className="text-xl text-blue-500" />

        </div>

        <div>

          <h2 className="text-xl font-bold text-blue-500">
            Developer Tools
          </h2>

          <p className="app-text-secondary mt-1">
            Export reports and manage your AI learning data.
          </p>

        </div>

      </div>

      <div className="space-y-3">

        <button
          onClick={handleDownload}
          disabled={downloading}
          className="flex w-full items-center justify-between rounded-xl border border-blue-500/20 p-4 transition hover:bg-blue-500/10 disabled:cursor-not-allowed disabled:opacity-70"
        >
          <div className="flex items-center gap-3">
            {downloading ? (
              <div className="h-5 w-5 animate-spin rounded-full border-2 border-blue-500 border-t-transparent" />
            ) : (
              <FiDownload />
            )}

            <div className="text-left">
              <p className="font-medium">
                {downloading
                  ? "Generating Developer Report..."
                  : "Download Developer Report"}
              </p>

              <p className="text-xs text-gray-400">
                Personalized AI PDF Report
              </p>
            </div>
          </div>

          <span className="text-sm font-medium text-blue-500">
            {downloading ? "Please Wait..." : "PDF"}
          </span>
        </button>

        <button
          onClick={() => setResetOpen(true)}
          className="flex w-full items-center justify-between rounded-xl border border-blue-500/20 p-4 transition hover:bg-blue-500/10"
        >

          <div className="flex items-center gap-3">

            <FiRotateCcw />

            <div className="text-left">
              <p className="font-medium">
                Reset AI Learning History
              </p>

              <p className="text-xs text-gray-400">
                Remove all AI learning progress
              </p>
            </div>

          </div>

        <span className="text-sm font-medium text-blue-500">
          Reset
        </span>

        </button>

      </div>

      <ResetAiMemoryModal
        open={resetOpen}
        loading={resetting}
        onClose={() => setResetOpen(false)}
        onConfirm={handleResetAiMemory}
      />

    </section>
  );
}