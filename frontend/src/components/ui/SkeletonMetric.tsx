interface SkeletonMetricProps {
  className?: string;
}

export default function SkeletonMetric({
  className = "",
}: SkeletonMetricProps) {
  return (
    <div
      className={`animate-pulse rounded-xl border app-border app-surface p-5 ${className}`}
    >
      {/* Icon */}
      <div className="h-10 w-10 rounded-lg app-background" />

      {/* Title */}
      <div className="mt-5 h-4 w-28 rounded app-background" />

      {/* Value */}
      <div className="mt-4 h-8 w-20 rounded app-background" />

      {/* Description */}
      <div className="mt-3 h-3 w-36 rounded app-background" />
    </div>
  );
}