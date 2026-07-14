interface Props {
  distribution: Record<string, number>;
}

function formatStatus(status: string) {
  return status
    .toLowerCase()
    .split("_")
    .map(
      (word) =>
        word.charAt(0).toUpperCase() +
        word.slice(1)
    )
    .join(" ");
}

export default function AdminStatusDistribution({
  distribution,
}: Props) {
  const total = Object.values(distribution).reduce(
    (sum, value) => sum + value,
    0
  );

  return (
    <div className="space-y-4">
      {Object.entries(distribution).map(
        ([status, value]) => {
          const percentage =
            total === 0
              ? 0
              : (value / total) * 100;

          return (
            <div key={status}>
              <div className="mb-2 flex items-center justify-between gap-4">
                <span className="app-text-secondary text-sm">
                  {formatStatus(status)}
                </span>

                <span className="text-sm font-semibold">
                  {value}
                </span>
              </div>

              <div className="h-1.5 overflow-hidden rounded-full bg-slate-500/10">
                <div
                  className="h-full rounded-full bg-blue-500 transition-all duration-500"
                  style={{
                    width: `${percentage}%`,
                  }}
                />
              </div>
            </div>
          );
        }
      )}
    </div>
  );
}