import {
  FiFilter,
  FiSearch,
} from "react-icons/fi";

interface TopicSearchProps {
  search: string;
  onSearchChange: (value: string) => void;
}

export default function TopicSearch({
  search,
  onSearchChange,
}: TopicSearchProps) {
  return (
    <div className="rounded-2xl border app-border app-surface p-4">

      <div className="flex gap-3">

        <div className="relative flex-1">

          <FiSearch
            className="absolute left-4 top-1/2 -translate-y-1/2 app-text-secondary"
            size={18}
          />

          <input
            value={search}
            onChange={(e) =>
              onSearchChange(e.target.value)
            }
            placeholder="Search problems..."
            className="
              h-12
              w-full
              rounded-xl
              border
              app-border
              bg-transparent
              pl-12
              pr-4
              outline-none
              focus:border-cyan-500
            "
          />

        </div>

        <button
          className="
            flex
            h-12
            w-12
            items-center
            justify-center
            rounded-xl
            border
            app-border
            transition
            hover:border-cyan-500
          "
        >

          <FiFilter />

        </button>

      </div>

    </div>
  );
}