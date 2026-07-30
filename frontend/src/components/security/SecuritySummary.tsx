import {
  Monitor,
  Shield,
  ShieldCheck,
} from "lucide-react";

interface SecuritySummaryProps {
  totalDevices: number;
  activeDevices: number;
  currentDevice: string;
}

export default function SecuritySummary({
  totalDevices,
  activeDevices,
  currentDevice,
}: SecuritySummaryProps) {
  return (
    <div className="grid gap-6 lg:grid-cols-3">

      <div className="rounded-2xl border bg-card p-6 shadow-sm">

        <div className="mb-4 flex h-12 w-12 items-center justify-center rounded-xl bg-blue-500/10">

          <Monitor
            className="text-blue-500"
            size={24}
          />

        </div>

        <p className="text-sm text-muted-foreground">
          Total Devices
        </p>

        <h2 className="mt-2 text-3xl font-bold">
          {totalDevices}
        </h2>

      </div>

      <div className="rounded-2xl border bg-card p-6 shadow-sm">

        <div className="mb-4 flex h-12 w-12 items-center justify-center rounded-xl bg-green-500/10">

          <ShieldCheck
            className="text-green-500"
            size={24}
          />

        </div>

        <p className="text-sm text-muted-foreground">
          Active Sessions
        </p>

        <h2 className="mt-2 text-3xl font-bold">
          {activeDevices}
        </h2>

      </div>

      <div className="rounded-2xl border bg-card p-6 shadow-sm">

        <div className="mb-4 flex h-12 w-12 items-center justify-center rounded-xl bg-purple-500/10">

          <Shield
            className="text-purple-500"
            size={24}
          />

        </div>

        <p className="text-sm text-muted-foreground">
          Current Device
        </p>

        <h2 className="mt-2 truncate text-lg font-bold">
          {currentDevice}
        </h2>

      </div>

    </div>
  );
}