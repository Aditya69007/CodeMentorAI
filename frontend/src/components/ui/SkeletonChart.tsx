interface SkeletonChartProps {
  className?: string;
}

export default function SkeletonChart({
  className = "",
}: SkeletonChartProps) {
  return (
    <div
      className={`animate-pulse rounded-xl border app-border app-surface p-6 ${className}`}
    >
      {/* Title */}
      <div className="h-6 w-40 rounded-md app-background" />

      {/* Subtitle */}
      <div className="mt-3 h-4 w-60 rounded app-background" />

      {/* Chart Area */}
      <div className="mt-8 flex h-64 items-end justify-between gap-3">
        <div className="h-24 w-full rounded-t-lg app-background" />
        <div className="h-40 w-full rounded-t-lg app-background" />
        <div className="h-32 w-full rounded-t-lg app-background" />
        <div className="h-52 w-full rounded-t-lg app-background" />
        <div className="h-44 w-full rounded-t-lg app-background" />
        <div className="h-60 w-full rounded-t-lg app-background" />
        <div className="h-36 w-full rounded-t-lg app-background" />
      </div>
    </div>
  );
}