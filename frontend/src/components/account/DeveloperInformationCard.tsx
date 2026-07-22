interface DeveloperInformationCardProps {
  onConfigure?: () => void;
}

function InfoRow({
  label,
  value,
}: {
  label: string;
  value: string;
}) {
  return (
    <div className="flex items-center justify-between border-b border-slate-700/40 pb-3">
      <span className="app-text-secondary text-sm">
        {label}
      </span>

      <span className="font-medium">
        {value}
      </span>
    </div>
  );
}

export default function DeveloperInformationCard({
  onConfigure,
}: DeveloperInformationCardProps) {

  return (

    <section className="app-surface app-border rounded-2xl p-6">

      <div className="mb-6 flex items-center justify-between">

        <h3 className="text-xl font-bold">

          Developer Information

        </h3>

        <button
          onClick={onConfigure}
          className="text-sm font-medium text-blue-500 hover:text-blue-400"
        >
          Configure
        </button>

      </div>

      <div className="space-y-5">

        <InfoRow
          label="GitHub"
          value="Not Connected"
        />

        <InfoRow
          label="LeetCode"
          value="Not Connected"
        />

        <InfoRow
          label="Preferred Language"
          value="Coming Soon"
        />

        <InfoRow
          label="Experience Level"
          value="Coming Soon"
        />

        <InfoRow
          label="Country"
          value="Coming Soon"
        />

      </div>

    </section>

  );

}