import {
  FiGithub,
  FiExternalLink,
  FiCheckCircle,
} from "react-icons/fi";

interface Project {
  title: string;
  description: string;
  tech: string[];
  status: string;
}

const projects: Project[] = [
  {
    title: "CodeMentorAI",
    description:
      "AI-powered coding career platform with adaptive learning, interview preparation, AI mentor, growth analytics, and personalized learning.",
    tech: [
      "React",
      "Spring Boot",
      "PostgreSQL",
      "Gemini AI",
    ],
    status: "Production Ready",
  },
  {
    title: "Student Dropout Prediction",
    description:
      "Machine learning system for predicting student dropout risk using explainable AI and ensemble learning.",
    tech: [
      "Python",
      "Flask",
      "LightGBM",
      "TabNet",
    ],
    status: "Research Project",
  },
];

export default function ProjectsCard() {
  return (
    <section className="app-surface app-border rounded-2xl p-6">

      <div className="mb-6">

        <h2 className="text-xl font-bold">
          Featured Projects
        </h2>

        <p className="app-text-secondary mt-2">
          Projects that showcase your engineering skills.
        </p>

      </div>

      <div className="space-y-6">

        {projects.map((project) => (

          <div
            key={project.title}
            className="rounded-2xl border border-slate-700/40 p-5 transition hover:border-blue-500"
          >

            <div className="flex items-center justify-between">

              <h3 className="text-lg font-bold">
                {project.title}
              </h3>

              <span className="flex items-center gap-2 rounded-full bg-emerald-500/10 px-3 py-1 text-sm font-medium text-emerald-500">

                <FiCheckCircle />

                {project.status}

              </span>

            </div>

            <p className="app-text-secondary mt-3">
              {project.description}
            </p>

            <div className="mt-5 flex flex-wrap gap-2">

              {project.tech.map((tech) => (

                <span
                  key={tech}
                  className="rounded-full bg-blue-500/10 px-3 py-1 text-sm font-medium text-blue-500"
                >
                  {tech}
                </span>

              ))}

            </div>

            <div className="mt-6 flex gap-3">

              <button className="flex items-center gap-2 rounded-xl border px-4 py-2 transition hover:border-blue-500">

                <FiGithub />

                GitHub

              </button>

              <button className="flex items-center gap-2 rounded-xl border px-4 py-2 transition hover:border-blue-500">

                <FiExternalLink />

                Live Demo

              </button>

            </div>

          </div>

        ))}

      </div>

    </section>
  );
}