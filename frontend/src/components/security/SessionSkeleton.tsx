export default function SessionSkeleton() {
  return (
    <div className="space-y-5">

      {[1, 2, 3].map((item) => (

        <div
          key={item}
          className="animate-pulse rounded-2xl border bg-card p-6"
        >

          <div className="flex items-center gap-4">

            <div className="h-14 w-14 rounded-xl bg-muted" />

            <div className="flex-1">

              <div className="h-5 w-56 rounded bg-muted" />

              <div className="mt-3 h-4 w-40 rounded bg-muted" />

              <div className="mt-6 flex gap-6">

                <div className="h-4 w-28 rounded bg-muted" />

                <div className="h-4 w-40 rounded bg-muted" />

              </div>

            </div>

            <div className="h-10 w-28 rounded-xl bg-muted" />

          </div>

        </div>

      ))}

    </div>
  );
}