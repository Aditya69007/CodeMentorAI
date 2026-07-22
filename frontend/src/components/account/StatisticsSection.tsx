interface StatCardProps {
  title: string;
  value: string;
}

function StatCard({
  title,
  value,
}: StatCardProps) {

  return (

    <div className="app-surface app-border rounded-2xl p-6">

      <p className="app-text-secondary text-sm">

        {title}

      </p>

      <h3 className="mt-3 text-3xl font-bold">

        {value}

      </h3>

    </div>

  );

}

export default function StatisticsSection() {

  return (

    <section>

      <h3 className="mb-6 text-xl font-bold">

        Statistics

      </h3>

      <div className="grid gap-6 md:grid-cols-2 xl:grid-cols-4">

        <StatCard
          title="Problems Solved"
          value="0"
        />

        <StatCard
          title="AI Sessions"
          value="0"
        />

        <StatCard
          title="Growth Score"
          value="Coming Soon"
        />

        <StatCard
          title="Interview Score"
          value="Coming Soon"
        />

      </div>

    </section>

  );

}