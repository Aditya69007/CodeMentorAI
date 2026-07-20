import clsx from "clsx";

interface SkeletonCardProps {
  className?: string;
}

export default function SkeletonCard({
  className = "",
}: SkeletonCardProps) {
  return (
    <div
      className={clsx(
        "animate-pulse rounded-xl border app-border app-surface p-6",
        className
      )}
    >
      <div className="h-6 w-48 rounded-md app-background" />

      <div className="mt-5 space-y-4">
        <div className="h-4 w-full rounded app-background" />

        <div className="h-4 w-11/12 rounded app-background" />

        <div className="h-4 w-9/12 rounded app-background" />
      </div>

      <div className="mt-8 grid grid-cols-2 gap-4">
        <div className="h-20 rounded-lg app-background" />

        <div className="h-20 rounded-lg app-background" />
      </div>
    </div>
  );
}