import { FiMonitor } from "react-icons/fi";
import { useTheme } from "../../hooks/useTheme";

export default function AppearanceCard() {
  const { theme, setTheme } = useTheme();

  return (
    <section className="app-surface app-border rounded-2xl p-6">

      <div className="mb-6">

        <div className="mb-3 flex h-12 w-12 items-center justify-center rounded-xl bg-purple-500/10">

          <FiMonitor className="text-xl text-purple-500" />

        </div>

        <h2 className="text-xl font-bold">
          Appearance
        </h2>

        <p className="app-text-secondary mt-2">
          Customize how CodeMentorAI looks and feels.
        </p>

      </div>

      <div className="space-y-4">

        <h3 className="font-semibold">
          Theme
        </h3>

        <div className="flex gap-3">

          <button
            onClick={() => setTheme("LIGHT")}
            className={`rounded-xl border px-5 py-3 transition ${
              theme === "LIGHT"
                ? "border-blue-500 bg-blue-500 text-white"
                : "hover:border-blue-500"
            }`}
          >
            Light
          </button>

          <button
            onClick={() => setTheme("DARK")}
            className={`rounded-xl border px-5 py-3 transition ${
              theme === "DARK"
                ? "border-blue-500 bg-blue-500 text-white"
                : "hover:border-blue-500"
            }`}
          >
            Dark
          </button>

        </div>

      </div>

    </section>
  );
}