import type { AuthUser } from "../../types/auth";

interface PersonalInformationCardProps {
  user: AuthUser;
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

export default function PersonalInformationCard({
  user,
}: PersonalInformationCardProps) {

  return (

    <section className="app-surface app-border rounded-2xl p-6">

      <div className="mb-6 flex items-center justify-between">

        <h3 className="text-xl font-bold">

          Personal Information

        </h3>

        <button className="text-sm font-medium text-blue-500 hover:text-blue-400">

          Edit

        </button>

      </div>

      <div className="space-y-5">

        <InfoRow
          label="First Name"
          value={user.firstName}
        />

        <InfoRow
          label="Last Name"
          value={user.lastName}
        />

        <InfoRow
          label="Email"
          value={user.email}
        />

        <InfoRow
          label="Role"
          value={user.role}
        />

        <InfoRow
          label="User ID"
          value={String(user.userId)}
        />

      </div>

    </section>

  );

}