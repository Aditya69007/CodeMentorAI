import SkeletonCard from "./SkeletonCard";
import SkeletonMetric from "./SkeletonMetric";

interface PageLoaderProps {
  title?: string;
  subtitle?: string;
}

export default function PageLoader({
  title = "Loading...",
  subtitle = "Please wait while we prepare your page.",
}: PageLoaderProps) {
  return (
    <main className="mx-auto w-full max-w-[1500px] px-4 py-8 sm:px-6 animate-pulse">
      {/* Header */}
      <section className="mb-8">
        <div className="h-9 w-80 rounded-lg app-background" />

        <div className="mt-3 h-5 w-[520px] rounded-lg app-background" />

        <p className="mt-5 text-sm app-text-secondary">
          {title}
        </p>

        <p className="text-xs app-text-muted">
          {subtitle}
        </p>
      </section>

      {/* Hero */}
      <SkeletonCard className="mb-6 h-[260px]" />

      {/* Metrics */}
      <div className="grid gap-4 sm:grid-cols-2 xl:grid-cols-4">
        <SkeletonMetric />
        <SkeletonMetric />
        <SkeletonMetric />
        <SkeletonMetric />
      </div>

      {/* Lower Content */}
      <div className="mt-6 grid gap-6 xl:grid-cols-2">
        <SkeletonCard className="h-[340px]" />
        <SkeletonCard className="h-[340px]" />
        <SkeletonCard className="h-[340px]" />
        <SkeletonCard className="h-[340px]" />
      </div>
    </main>
  );
}