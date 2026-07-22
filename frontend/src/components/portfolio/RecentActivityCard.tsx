import {
  FiCheckCircle,
  FiTrendingUp,
  FiCpu,
  FiTarget,
  FiClock,
} from "react-icons/fi";

interface Activity {
  title: string;
  description: string;
  time: string;
  icon: React.ElementType;
  color: string;
}

const activities: Activity[] = [
  {
    title: "Solved Graph Problem",
    description: "Completed 'Number of Islands' successfully.",
    time: "2 hours ago",
    icon: FiCheckCircle,
    color: "text-emerald-500",
  },
  {
    title: "AI Mentor Session",
    description: "Received optimization suggestions.",
    time: "Yesterday",
    icon: FiCpu,
    color: "text-blue-500",
  },
  {
    title: "Growth Score Increased",
    description: "+4 AI Growth Score",
    time: "Yesterday",
    icon: FiTrendingUp,
    color: "text-purple-500",
  },
  {
    title: "Interview Readiness Updated",
    description: "Interview profile recalculated.",
    time: "Today",
    icon: FiTarget,
    color: "text-orange-500",
  },
  {
    title: "Coding Streak Maintained",
    description: "196-day streak continues.",
    time: "Today",
    icon: FiClock,
    color: "text-yellow-500",
  },
];

export default function RecentActivityCard() {
  return (
    <section className="app-surface app-border rounded-2xl p-6">

      <div className="mb-6">

        <h2 className="text-xl font-bold">
          Recent Activity
        </h2>

        <p className="app-text-secondary mt-2">
          Your latest progress across CodeMentorAI.
        </p>

      </div>

      <div className="space-y-4">

        {activities.map((activity) => {
          const Icon = activity.icon;

          return (
            <div
              key={activity.title}
              className="flex items-start gap-4 rounded-xl border border-slate-700/40 p-4 transition hover:border-blue-500"
            >
              <div
                className={`mt-1 flex h-11 w-11 items-center justify-center rounded-xl bg-slate-800 ${activity.color}`}
              >
                <Icon className="text-lg" />
              </div>

              <div className="flex-1">

                <div className="flex items-center justify-between">

                  <h3 className="font-semibold">
                    {activity.title}
                  </h3>

                  <span className="app-text-secondary text-sm">
                    {activity.time}
                  </span>

                </div>

                <p className="app-text-secondary mt-1 text-sm">
                  {activity.description}
                </p>

              </div>

            </div>
          );
        })}

      </div>

    </section>
  );
}