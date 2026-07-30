import { ShieldCheck } from "lucide-react";

export default function EmptySessions() {
  return (

    <div className="rounded-2xl border border-dashed p-16 text-center">

      <div className="mx-auto flex h-20 w-20 items-center justify-center rounded-full bg-emerald-500/10">

        <ShieldCheck
          className="text-emerald-500"
          size={42}
        />

      </div>

      <h2 className="mt-6 text-2xl font-bold">
        No Active Sessions
      </h2>

      <p className="mt-3 text-muted-foreground">
        Your account currently has no active login sessions.
      </p>

    </div>

  );
}