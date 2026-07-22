import { useEffect, useState } from "react";
import {
  getDeveloperSkills,
  type DeveloperSkill,
} from "../../services/portfolioService";

export default function SkillsCard() {

  const [skills, setSkills] = useState<DeveloperSkill[]>([]);

  useEffect(() => {

    const load = async () => {

      try {

        const data = await getDeveloperSkills();

        setSkills(data);

      } catch (error) {

        console.error(error);

      }

    };

    load();

  }, []);

  return (

    <section className="app-surface app-border rounded-2xl p-6">

      <div className="mb-6">

        <h2 className="text-xl font-bold">
          AI Skill Analysis
        </h2>

        <p className="app-text-secondary mt-2">
          Your strongest and weakest concepts according to AI.
        </p>

      </div>

      <div className="space-y-5">

        {skills.map((skill) => (

          <div key={skill.topicId}>

            <div className="mb-2 flex items-center justify-between">

              <span className="font-semibold">
                {skill.topicName}
              </span>

              <span className="text-sm font-semibold text-blue-500">
                {skill.skillScore}
              </span>

            </div>

            <div className="h-2 overflow-hidden rounded-full bg-slate-700">

              <div
                className="h-full rounded-full bg-blue-500"
                style={{
                  width: `${skill.skillScore}%`,
                }}
              />

            </div>

            <div className="mt-2 flex justify-between text-sm">

              <span className="app-text-secondary">
                {skill.skillLevel}
              </span>

              <span className="app-text-secondary">
                {skill.acceptanceRate.toFixed(2)}%
              </span>

            </div>

          </div>

        ))}

      </div>

    </section>

  );

}