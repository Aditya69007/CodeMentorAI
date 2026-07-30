import {
  Monitor,
  Laptop,
  Smartphone,
  Globe,
  Clock,
  LogOut,
} from "lucide-react";

import type { SessionInfo } from "./types";

interface SessionCardProps {
  session: SessionInfo;
  isCurrent: boolean;
  onLogout: (sessionId: number) => void;
}

export default function SessionCard({
  session,
  isCurrent,
  onLogout,
}: SessionCardProps) {

  const getDeviceIcon = () => {

    const device = session.deviceName.toLowerCase();

    if (device.includes("mobile")) {
      return (
        <Smartphone
          className="text-blue-500"
          size={26}
        />
      );
    }

    if (device.includes("laptop")) {
      return (
        <Laptop
          className="text-emerald-500"
          size={26}
        />
      );
    }

    return (
      <Monitor
        className="text-purple-500"
        size={26}
      />
    );
  };

  return (

    <div className="rounded-2xl border bg-card p-6 shadow-sm transition-all hover:shadow-lg">

      <div className="flex flex-col gap-6 lg:flex-row lg:items-center lg:justify-between">

        <div className="flex items-start gap-4">

          <div className="rounded-xl bg-muted p-3">
            {getDeviceIcon()}
          </div>

          <div>

            <div className="flex items-center gap-3">

              <h2 className="text-lg font-semibold">
                {session.deviceName}
              </h2>

              {isCurrent && (
                <span className="rounded-full bg-emerald-500/20 px-3 py-1 text-xs font-semibold text-emerald-500">
                  Current Device
                </span>
              )}

            </div>

            <p className="mt-1 text-sm text-muted-foreground">
              {session.browser} • {session.operatingSystem}
            </p>

            <div className="mt-4 flex flex-wrap gap-5 text-sm text-muted-foreground">

              <div className="flex items-center gap-2">
                <Globe size={16} />
                {session.ipAddress}
              </div>

              <div className="flex items-center gap-2">
                <Clock size={16} />
                {new Date(session.lastSeen).toLocaleString()}
              </div>

            </div>

          </div>

        </div>

        {!isCurrent && (

          <button
            onClick={() => onLogout(session.id)}
            className="flex items-center gap-2 rounded-xl bg-red-500 px-5 py-3 font-medium text-white transition hover:bg-red-600"
          >
            <LogOut size={18} />
            Logout
          </button>

        )}

      </div>

    </div>

  );
}