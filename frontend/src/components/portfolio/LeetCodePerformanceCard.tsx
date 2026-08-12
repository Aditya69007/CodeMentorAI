import { useEffect, useMemo, useState } from "react";
import {
  FiAward,
  FiBarChart2,
  FiExternalLink,
  FiTarget,
  FiTrendingUp,
} from "react-icons/fi";
import { SiLeetcode } from "react-icons/si";

import LeetCodeAchievementsModal from "./LeetCodeAchievementsModal";
import leetcodeService from "../../services/leetcodeService";
import { getConnectedAccounts } from "../../services/connectedAccountsService";
import type { LeetCodeProfile } from "../../types/leetcode";

interface LeetCodePerformanceCardProps {
  profile?: LeetCodeProfile | null;
}

export default function LeetCodePerformanceCard({
  profile: publicProfile,
}: LeetCodePerformanceCardProps) {

  const [profile, setProfile] =
    useState<LeetCodeProfile | null>(
      publicProfile ?? null
    );

  const [loading, setLoading] = useState(true);
  const [showAchievements, setShowAchievements] = useState(false);

  useEffect(() => {
    if (publicProfile !== undefined) {
      return;
    }

    async function loadProfile() {
      try {
        const accounts = await getConnectedAccounts();

        if (
          !accounts.leetcodeConnected ||
          !accounts.leetcodeUsername
        ) {
          setLoading(false);
          return;
        }

        const data = await leetcodeService.getProfile(
          accounts.leetcodeUsername
        );

        setProfile(data);
      } catch (error) {
        console.error(error);
      } finally {
        setLoading(false);
      }
    }

    void loadProfile();
  }, [publicProfile]);

const displayProfile =
  publicProfile !== undefined
    ? publicProfile
    : profile;

  const topSkills = useMemo(() => {
    if (!displayProfile) return [];

    return [
      ...displayProfile.skills.advanced,
      ...displayProfile.skills.intermediate,
      ...displayProfile.skills.fundamental,
    ]
      .sort((a, b) => b.problemsSolved - a.problemsSolved)
      .slice(0, 5);
  }, [displayProfile]);

  if (publicProfile === undefined && loading) {
    return (
      <section className="app-surface app-border rounded-2xl p-6">
        <p className="app-text-secondary">
          Loading LeetCode displayProfile?...
        </p>
      </section>
    );
  }

  if (!displayProfile) {
    return (
      <section className="app-surface app-border rounded-2xl p-6">
        <div className="flex items-center gap-3">
          <SiLeetcode className="text-2xl text-orange-500" />

          <div>
            <h2 className="text-xl font-bold">
              LeetCode Performance
            </h2>

            <p className="app-text-secondary mt-1">
              Connect your LeetCode account to showcase your coding journey.
            </p>
          </div>
        </div>
      </section>
    );
  }

  const metrics = [
    {
      label: "Contest Rating",
      value: Math.round(displayProfile?.contest.rating),
      icon: FiTrendingUp,
    },
    {
      label: "Problems Solved",
      value: displayProfile?.problems.totalSolved,
      icon: FiTarget,
    },
    {
      label: "Acceptance Rate",
      value: `${displayProfile?.problems.acceptanceRate.toFixed(1)}%`,
      icon: FiBarChart2,
    },
    {
    label: "Current Streak",
    value: `${displayProfile?.calendar.currentStreak} Days`,
    icon: FiAward,
    },
    {
    label: "Global Rank",
    value: displayProfile?.contest.globalRanking.toLocaleString(),
    icon: FiTrendingUp,
    },
    {
    label: "Top %",
    value: `${displayProfile?.contest.topPercentage.toFixed(2)}%`,
    icon: FiAward,
    },
    {
    label: "Contests",
    value: displayProfile?.contest.attendedContestsCount,
    icon: FiBarChart2,
    },
  ];

    const difficulty = [
    {
        label: "Easy",
        solved: displayProfile?.problems.easySolved,
        total: displayProfile?.problems.easySubmissions,
        color: "bg-emerald-500",
        text: "text-emerald-400",
    },
    {
        label: "Medium",
        solved: displayProfile?.problems.mediumSolved,
        total: displayProfile?.problems.mediumSubmissions,
        color: "bg-yellow-500",
        text: "text-yellow-400",
    },
    {
        label: "Hard",
        solved: displayProfile?.problems.hardSolved,
        total: displayProfile?.problems.hardSubmissions,
        color: "bg-red-500",
        text: "text-red-400",
    },
    ];

  return (
    <section className="app-surface app-border rounded-2xl p-6">

      <div className="flex items-center justify-between">

        <div className="flex items-center gap-3">

          <SiLeetcode className="text-3xl text-orange-500" />

          <div>

            <h2 className="text-xl font-bold">
              LeetCode Performance
            </h2>

            <p className="app-text-secondary">
              @{displayProfile?.username}
            </p>

          </div>

        </div>

        <button
          onClick={() =>
            window.open(
              `https://leetcode.com/u/${displayProfile?.username}/`,
              "_blank"
            )
          }
          className="rounded-lg border border-slate-700 p-2 hover:border-orange-500 transition"
        >
          <FiExternalLink />
        </button>

      </div>

      <div className="mt-6 grid gap-4 sm:grid-cols-2 lg:grid-cols-4 xl:grid-cols-7">

        {metrics.map((item) => {
          const Icon = item.icon;

          return (
            <div
              key={item.label}
              className="rounded-xl border border-slate-700/40 p-5"
            >
              <Icon className="mb-3 text-xl text-orange-500" />

              <p className="text-3xl font-bold">
                {item.value}
              </p>

              <p className="mt-2 text-sm app-text-secondary">
                {item.label}
              </p>
            </div>
          );
        })}

      </div>

   <div className="mt-8 grid gap-8 xl:grid-cols-3">

    {/* Difficulty Breakdown */}

    <div>

        <h3 className="mb-5 text-lg font-semibold">
        Difficulty Breakdown
        </h3>

        <div className="space-y-5">

        {difficulty.map((item) => {

            const percentage =
            item.total === 0
                ? 0
                : (item.solved / item.total) * 100;

            return (

            <div key={item.label}>

                <div className="mb-2 flex items-center justify-between">

                <span className={`font-semibold ${item.text}`}>
                    {item.label}
                </span>

                <span className="app-text-secondary text-sm">
                    {item.solved} / {item.total}
                </span>

                </div>

                <div className="h-2 overflow-hidden rounded-full bg-slate-700">

                <div
                    className={`h-full rounded-full ${item.color}`}
                    style={{
                    width: `${Math.min(percentage, 100)}%`,
                    }}
                />

                </div>

            </div>

            );

        })}

        </div>

    </div>

    {/* Top Skills */}

    <div>

        <h3 className="mb-5 text-lg font-semibold">
        Top Skills
        </h3>

        <div className="flex flex-wrap gap-3">

        {topSkills.map((skill) => (

            <span
            key={skill.tagSlug}
            className="rounded-full border border-orange-500/30 bg-orange-500/10 px-4 py-2 text-sm font-medium text-orange-400"
            >
            {skill.tagName} • {skill.problemsSolved}
            </span>

        ))}

        </div>

    </div>

    {/* Developer Analytics */}

    <div>

    <h3 className="mb-5 text-lg font-semibold">
        Developer Analytics
    </h3>

    <div className="space-y-4">

        <div className="flex items-center justify-between rounded-xl border border-slate-700/40 p-4">

        <span className="app-text-secondary">
            Developer Score
        </span>

        <span className="text-lg font-bold text-blue-400">
            {displayProfile?.analytics.developerScore}
        </span>

        </div>

        <div className="flex items-center justify-between rounded-xl border border-slate-700/40 p-4">

        <span className="app-text-secondary">
            Contest Score
        </span>

        <span className="text-lg font-bold text-orange-400">
            {displayProfile?.analytics.contestScore}
        </span>

        </div>

        <div className="flex items-center justify-between rounded-xl border border-slate-700/40 p-4">

        <span className="app-text-secondary">
            Skill Score
        </span>

        <span className="text-lg font-bold text-emerald-400">
            {displayProfile?.analytics.skillScore}
        </span>

        </div>

        <div className="flex items-center justify-between rounded-xl border border-slate-700/40 p-4">

        <span className="app-text-secondary">
            Consistency
        </span>

        <span className="text-lg font-bold text-purple-400">
            {displayProfile?.analytics.consistencyScore}
        </span>

        </div>

        <div className="flex items-center justify-between rounded-xl border border-slate-700/40 p-4">

        <span className="app-text-secondary">
            Difficulty Score
        </span>

        <span className="text-lg font-bold text-red-400">
            {displayProfile?.analytics.difficultyScore}
        </span>

        </div>

    </div>

    </div>


    </div>

    {/* Badges */}

    <section className="mt-10">

        <div className="mb-6 flex items-center justify-between">

            <div>

                <h3 className="text-2xl font-bold">
                    Featured Achievements
                </h3>

                <p className="app-text-secondary mt-2">
                    Your most important LeetCode milestones.
                </p>

            </div>

            <button
                onClick={() => setShowAchievements(true)}
                className="rounded-xl border border-orange-500 px-5 py-2 text-orange-500 transition hover:bg-orange-500 hover:text-white"
            >
                View All
            </button>

        </div>

        <div className="grid gap-5 sm:grid-cols-2 lg:grid-cols-4">

            {displayProfile?.badges
                .slice(0, 4)
                .map((badge) => (

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

        {displayProfile?.badges.length > 4 && (

            <div className="mt-6 text-center">

                <button
                    onClick={() => setShowAchievements(true)}
                    className="text-lg font-semibold text-orange-500 hover:underline"
                >
                    +{displayProfile?.badges.length - 4} More Achievements
                </button>

            </div>

        )}

    </section>

    <LeetCodeAchievementsModal
        open={showAchievements}
        onClose={() => setShowAchievements(false)}
        badges={displayProfile?.badges}
    />

    </section>
  );
}