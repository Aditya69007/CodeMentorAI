import { FiX } from "react-icons/fi";
import type { BadgeInfo } from "../../types/leetcode";

interface Props {
  open: boolean;
  onClose: () => void;
  badges: BadgeInfo[];
}

export default function LeetCodeAchievementsModal({
  open,
  onClose,
  badges,
}: Props) {
  if (!open) return null;

  const grouped = {
    Submission: badges.filter(
      (badge) => badge.category === "SUBMISSION"
    ),
    Annual: badges.filter(
      (badge) => badge.category === "ANNUAL"
    ),
    "Daily Challenge": badges.filter(
      (badge) => badge.category === "DCC"
    ),
    Other: badges.filter(
      (badge) =>
        badge.category !== "SUBMISSION" &&
        badge.category !== "ANNUAL" &&
        badge.category !== "DCC"
    ),
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/70 backdrop-blur-sm">

      <div className="app-surface flex max-h-[90vh] w-full max-w-6xl flex-col rounded-3xl border border-slate-700">

        {/* Sticky Header */}

        <div className="sticky top-0 z-20 flex items-center justify-between border-b border-slate-700 bg-[rgb(var(--surface))] px-8 py-6">

          <div>

            <h2 className="text-3xl font-bold">
              LeetCode Achievements
            </h2>

            <p className="app-text-secondary mt-2">
              Complete collection of your LeetCode badges.
            </p>

          </div>

          <button
            onClick={onClose}
            className="rounded-xl border border-slate-700 p-3 transition hover:border-orange-500"
          >
            <FiX size={22} />
          </button>

        </div>

        {/* Scrollable Content */}

        <div className="overflow-y-auto px-8 py-8">

          {Object.entries(grouped).map(([title, items]) => {

            if (items.length === 0) return null;

            return (

              <section
                key={title}
                className="mb-10"
              >

                <h3 className="mb-5 text-xl font-semibold">
                  {title}
                </h3>

                <div className="grid gap-5 sm:grid-cols-2 lg:grid-cols-4">

                  {items.map((badge) => (

                    <div
                      key={badge.id}
                      className="rounded-2xl border border-slate-700/40 p-5 transition hover:border-orange-500"
                    >

                      <img
                        src={badge.icon}
                        alt={badge.displayName}
                        className="mb-4 h-16 w-16 object-contain"
                      />

                      <h4 className="font-semibold">
                        {badge.displayName}
                      </h4>

                      <p className="app-text-secondary mt-2 text-sm">
                        {badge.category}
                      </p>

                    </div>

                  ))}

                </div>

              </section>

            );

          })}

        </div>

      </div>

    </div>
  );
}