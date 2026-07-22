import { FiChevronRight } from "react-icons/fi";

type Props = {
  icon: React.ReactNode;
  title: string;
  subtitle: string;
  status: "connected" | "disconnected";
  buttonText: string;
  onClick: () => void;
};

export default function PlatformCard({
  icon,
  title,
  subtitle,
  status,
  buttonText,
  onClick,
}: Props) {
  return (
    <div
      className={`group rounded-2xl border p-5 transition-all duration-300 ${
        status === "connected"
          ? "border-green-500/20 bg-green-500/[0.03] hover:border-green-500/40"
          : "border-white/10 bg-white/[0.02] hover:border-white/20"
      }`}
    >
      <div className="flex items-center justify-between gap-6">
        {/* Left */}
        <div className="flex min-w-0 items-center gap-4">
          <div
            className={`flex h-16 w-16 shrink-0 items-center justify-center rounded-2xl text-3xl ${
              status === "connected"
                ? "bg-green-500/10 text-green-400"
                : "bg-white/5 text-zinc-300"
            }`}
          >
            {icon}
          </div>

          <div className="min-w-0">
            <div className="flex items-center gap-3">
              <h3 className="text-xl font-semibold">{title}</h3>

              <span
                className={`rounded-full px-3 py-1 text-xs font-semibold ${
                  status === "connected"
                    ? "bg-green-500/10 text-green-400"
                    : "bg-yellow-500/10 text-yellow-400"
                }`}
              >
                {status === "connected"
                  ? "Connected"
                  : "Not Connected"}
              </span>
            </div>

            <p className="mt-2 truncate text-sm text-zinc-400">
              {subtitle}
            </p>
          </div>
        </div>

        {/* Right */}
        <button
          onClick={onClick}
          className="flex shrink-0 items-center gap-2 rounded-xl border border-white/10 px-5 py-3 text-sm font-medium transition-all duration-300 hover:border-green-500 hover:bg-green-500/10"
        >
          {buttonText}

          <FiChevronRight className="transition-transform duration-300 group-hover:translate-x-1" />
        </button>
      </div>
    </div>
  );
}