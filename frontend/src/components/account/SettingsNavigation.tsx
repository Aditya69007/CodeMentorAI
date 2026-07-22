import {
  FiLock,
  FiMonitor,
  FiBell,
  FiLink,
  FiAlertTriangle,
} from "react-icons/fi";

const items = [
  {
    icon: FiLock,
    title: "Security",
    description: "Password & Account Protection",
  },
  {
    icon: FiMonitor,
    title: "Appearance",
    description: "Theme & Editor Preferences",
  },
  {
    icon: FiBell,
    title: "Notifications",
    description: "Email & Platform Alerts",
  },
  {
    icon: FiLink,
    title: "Connected Accounts",
    description: "Google, GitHub & LeetCode",
  },
  {
    icon: FiAlertTriangle,
    title: "Danger Zone",
    description: "Delete Account & Sessions",
  },
];

export default function SettingsNavigation() {
  return (
    <section className="app-surface app-border rounded-2xl p-6">

      <div className="mb-6">

        <h2 className="text-xl font-bold">
          Settings Categories
        </h2>

        <p className="app-text-secondary mt-2">
          Quickly access every area of your account.
        </p>

      </div>

      <div className="grid gap-4 md:grid-cols-2 xl:grid-cols-5">

        {items.map((item) => {
          const Icon = item.icon;

          return (
            <div
              key={item.title}
              className="rounded-xl border p-4 transition hover:border-blue-500 hover:bg-blue-500/5"
            >
              <div className="mb-3 flex h-10 w-10 items-center justify-center rounded-lg bg-blue-500/10">
                <Icon className="text-blue-500" />
              </div>

              <h3 className="font-semibold">
                {item.title}
              </h3>

              <p className="app-text-secondary mt-2 text-sm">
                {item.description}
              </p>
            </div>
          );
        })}

      </div>

    </section>
  );
}