export default function CardSkeleton() {
  return (
    <div className="app-surface app-border rounded-2xl p-6 animate-pulse">
      <div className="h-7 w-56 rounded bg-slate-700" />

      <div className="mt-3 h-4 w-72 rounded bg-slate-800" />

      <div className="mt-8 space-y-4">
        <div className="h-4 rounded bg-slate-700" />
        <div className="h-4 rounded bg-slate-700" />
        <div className="h-4 w-5/6 rounded bg-slate-700" />
        <div className="h-4 w-3/4 rounded bg-slate-700" />
      </div>
    </div>
  );
}