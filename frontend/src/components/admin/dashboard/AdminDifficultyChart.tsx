import {
  Cell,
  Pie,
  PieChart,
  ResponsiveContainer,
  Tooltip,
} from "recharts";

interface Props {
  distribution: Record<string, number>;
}

const COLORS = [
  "#10b981",
  "#f59e0b",
  "#ef4444",
];

export default function AdminDifficultyChart({
  distribution,
}: Props) {
  const data = Object.entries(distribution).map(
    ([name, value]) => ({
      name,
      value,
    })
  );

  return (
    <div className="h-56 w-full">
      <ResponsiveContainer
        width="100%"
        height="100%"
      >
        <PieChart>
          <Pie
            data={data}
            dataKey="value"
            nameKey="name"
            innerRadius={58}
            outerRadius={82}
            paddingAngle={4}
          >
            {data.map((entry, index) => (
              <Cell
                key={entry.name}
                fill={COLORS[index % COLORS.length]}
              />
            ))}
          </Pie>

          <Tooltip
            contentStyle={{
              backgroundColor: "#0f172a",
              border: "1px solid #334155",
              borderRadius: "10px",
            }}
          />
        </PieChart>
      </ResponsiveContainer>
    </div>
  );
}