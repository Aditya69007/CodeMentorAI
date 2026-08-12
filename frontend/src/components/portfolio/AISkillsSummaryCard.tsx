import { FiCpu } from "react-icons/fi";
import type { AiSkillsSummary } from "../../types/portfolioSkills";

interface Props {
  data: AiSkillsSummary;
}

export default function AISkillsSummaryCard({
  data,
}: Props) {

  return (

    <section className="app-surface app-border rounded-3xl p-8">

      {/* Header */}

      <div className="mb-8 flex items-center gap-4">

        <div className="rounded-2xl bg-violet-500/10 p-4">

          <FiCpu className="text-3xl text-violet-400" />

        </div>

        <div>

          <h2 className="text-2xl font-bold">
            AI Skills Summary
          </h2>

          <p className="mt-1 app-text-secondary">
            Generated using GitHub, LeetCode and CodeMentorAI.
          </p>

        </div>

      </div>

      <div className="space-y-8">

        {data.categories.map((category) => (

          <div
            key={category.category}
            className="rounded-2xl border app-border app-surface-secondary p-6"
          >

            <div className="mb-4 flex items-center justify-between">

              <h3 className="text-xl font-semibold">
                {category.category}
              </h3>

              <span className="text-xl font-bold text-violet-400">
                {category.score}%
              </span>

            </div>

            <div className="mb-5 h-3 overflow-hidden rounded-full bg-slate-200 dark:bg-slate-700">

              <div
                className="h-full rounded-full bg-gradient-to-r from-violet-500 to-blue-500 transition-all duration-700"
                style={{
                  width: `${category.score}%`,
                }}
              />

            </div>

            <div className="flex flex-wrap gap-3">

              {category.skills.map((skill) => (

                <span
                  key={skill}
                  className="rounded-full bg-violet-500/10 px-4 py-2 text-sm font-medium text-violet-600 dark:text-violet-300"
                >
                  {skill}
                </span>

              ))}

            </div>

          </div>

        ))}

      </div>

      <div className="mt-8 rounded-2xl border border-emerald-500/20 bg-emerald-500/5 p-6">

        <p className="text-sm app-text-secondary">
          Developer Level
        </p>

        <h3 className="mt-2 text-2xl font-bold text-emerald-400">
          {data.developerLevel}
        </h3>

      </div>

    </section>

  );

}
