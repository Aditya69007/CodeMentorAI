import { ShieldCheck } from "lucide-react";

export default function SecurityHeader() {
  return (
    <div className="rounded-2xl border border-emerald-500/20 bg-gradient-to-r from-emerald-500/10 to-cyan-500/10 p-8">

      <div className="flex items-center gap-5">

        <div className="flex h-16 w-16 items-center justify-center rounded-2xl bg-emerald-500/20">

          <ShieldCheck
            className="text-emerald-400"
            size={34}
          />

        </div>

        <div>

          <h1 className="text-3xl font-bold">
            Security Center
          </h1>

          <p className="mt-2 text-gray-400">
            Manage your active devices, login sessions and account security.
          </p>

        </div>

      </div>

    </div>
  );
}