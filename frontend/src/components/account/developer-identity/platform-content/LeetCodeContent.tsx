import { useEffect, useState } from "react";
import { SiLeetcode } from "react-icons/si";
import { FiLink, FiLoader } from "react-icons/fi";

import {
  getConnectedAccounts,
  updateConnectedAccounts,
  getLeetCodeProfile,
} from "../../../../services/connectedAccountsService";

import type { LeetCodeProfile } from "../../../../types/leetcode";



      
interface LeetCodeContentProps {

    onConnected: () => Promise<void>;

}

export default function LeetCodeContent({

    onConnected,

}: LeetCodeContentProps) {


  const [username, setUsername] = useState("");

  const [loading, setLoading] = useState(false);

  const [error, setError] = useState("");

  const [profile, setProfile] =
    useState<LeetCodeProfile | null>(null);

  const [editing, setEditing] =
      useState(false);



  async function connectLeetCode() {

    if (!username.trim()) {
      return;
    }

    try {

      setLoading(true);

      setError("");

  const accounts = await getConnectedAccounts();

  await updateConnectedAccounts({

      githubUsername: accounts.githubUsername ?? "",

      leetcodeUsername: username,

  });

  await onConnected();

  await loadLeetCode();

    } catch (error) {

      console.error(error);

      setError(
        "Unable to connect LeetCode profile."
      );

    } finally {

      setLoading(false);

    }

  }

  async function loadLeetCode() {

      try {

          const accounts =
              await getConnectedAccounts();

          if (
              !accounts.leetcodeConnected ||
              !accounts.leetcodeUsername
          ) {

              return;

          }

          setUsername(
              accounts.leetcodeUsername
          );

          setLoading(true);

          const response =
              await getLeetCodeProfile(
                  accounts.leetcodeUsername
              );

          setProfile(response);

      } catch (error) {

          console.error(error);

      } finally {

          setLoading(false);

      }

  }

    useEffect(() => {

        const initialize = async () => {

            await loadLeetCode();

        };

        initialize();

    }, []);


  async function refreshProfile() {

    if (!profile) return;

    try {

      setLoading(true);

      const response =
        await getLeetCodeProfile(profile.username);

      setProfile(response);

    } catch {

      setError(
        "Unable to refresh profile."
      );

    } finally {

      setLoading(false);

    }

  }

  async function saveUsername() {

      if (!username.trim()) return;

      try {

          setLoading(true);

          const accounts =
              await getConnectedAccounts();

          await updateConnectedAccounts({

              githubUsername:
                  accounts.githubUsername ?? "",

              leetcodeUsername:
                  username,

          });

          setEditing(false);

          await loadLeetCode();

      } catch {

          setError(
              "Unable to update username."
          );

      } finally {

          setLoading(false);

      }

  }


  async function disconnectLeetCode() {

      try {

          setLoading(true);

          const accounts =
              await getConnectedAccounts();

          await updateConnectedAccounts({

              githubUsername:
                  accounts.githubUsername ?? "",

              leetcodeUsername: ""

          });

          setProfile(null);

          setUsername("");

          await onConnected();

      } catch {

          setError(
              "Unable to disconnect."
          );

      } finally {

          setLoading(false);

      }

  }


  return (

    <div className="space-y-8">

      {!profile ? (
        <>

      {/* Header */}

      <div className="flex items-center gap-4">

        <div className="flex h-16 w-16 items-center justify-center rounded-2xl bg-orange-500/10">

          <SiLeetcode className="text-3xl text-orange-500" />

        </div>

        <div>

          <h2 className="text-3xl font-bold">

            Connect LeetCode

          </h2>

          <p className="mt-2 app-text-secondary">

            Connect your public LeetCode profile to unlock AI analytics,
            personalized learning and developer insights.

          </p>

        </div>

      </div>

      {/* Username */}

      <div>

        <label className="mb-3 block text-sm font-medium">

          LeetCode Username

        </label>

        <input

          value={username}

          onChange={(e) => setUsername(e.target.value)}

          placeholder="Enter your LeetCode username"

          className="w-full rounded-xl border border-white/10 bg-transparent px-4 py-3 outline-none focus:border-orange-500"

        />

      </div>

      {/* Info */}

      <div className="rounded-xl border border-orange-500/20 bg-orange-500/5 p-5">

        <h3 className="font-semibold">

          What happens after connecting?

        </h3>

        <ul className="mt-4 space-y-2 text-sm app-text-secondary">

          <li>• Analyze your contest performance</li>

          <li>• Calculate Developer Score</li>

          <li>• Identify strongest skills</li>

          <li>• Identify weakest skills</li>

          <li>• Track recent submissions</li>

          <li>• Personalize AI learning plan</li>

        </ul>

      </div>

      {/* Connect */}

      <button

        onClick={connectLeetCode}

        disabled={
          loading ||
          !username.trim()
        }

        className="flex w-full items-center justify-center gap-2 rounded-xl bg-orange-500 py-3 font-semibold text-white transition hover:bg-orange-600 disabled:cursor-not-allowed disabled:opacity-50"

      >

          {loading ? (

            <>

              <FiLoader className="animate-spin" />

              Connecting...

            </>

          ) : (

            <>

              <FiLink />

              Connect LeetCode

            </>

        )}

      </button>

      {/* Error */}

      {error && (

        <div className="rounded-xl border border-red-500/20 bg-red-500/5 p-4">

          <p className="text-sm text-red-400">

            {error}

          </p>

        </div>

      )}

      </>

    ) : (

        <div className="space-y-6">

          {/* Stats */}

          <div className="grid grid-cols-2 gap-4">

            <div className="rounded-xl border border-white/10 p-4">

              <p className="text-sm app-text-secondary">
                Developer Score
              </p>

              <h2 className="mt-2 text-3xl font-bold text-green-400">
                {profile.analytics.developerScore.toFixed(2)}
              </h2>

            </div>

            <div className="rounded-xl border border-white/10 p-4">

              <p className="text-sm app-text-secondary">
                Contest Rating
              </p>

              <h2 className="mt-2 text-3xl font-bold text-orange-400">
                {profile.contest.rating.toFixed(0)}
              </h2>

            </div>

            <div className="rounded-xl border border-white/10 p-4">

              <p className="text-sm app-text-secondary">
                Problems Solved
              </p>

              <h2 className="mt-2 text-3xl font-bold">
                {profile.problems.totalSolved}
              </h2>

            </div>

            <div className="rounded-xl border border-white/10 p-4">

              <p className="text-sm app-text-secondary">
                Acceptance
              </p>

              <h2 className="mt-2 text-3xl font-bold">
                {profile.problems.acceptanceRate.toFixed(2)}%
              </h2>

            </div>

          </div>

          {/* Streak */}

          <div className="rounded-xl border border-orange-500/20 bg-orange-500/5 p-5">

            <p className="text-sm app-text-secondary">
              Current Streak
            </p>

            <h2 className="mt-2 text-4xl font-bold text-orange-400">

              🔥 {profile.calendar.currentStreak} Days

            </h2>

          </div>

          {/* Strongest Skills */}

          <div className="rounded-xl border border-emerald-500/20 bg-emerald-500/5 p-5">

            <h3 className="text-lg font-semibold text-emerald-400">

              💪 Strongest Skills

            </h3>

            <div className="mt-4 flex flex-wrap gap-3">

              {profile.analytics.strongestSkills.map((skill) => (

                <span
                  key={skill}
                  className="rounded-full bg-emerald-500/15 px-4 py-2 text-sm font-medium text-emerald-300"
                >
                  {skill}
                </span>

              ))}

            </div>

          </div>

          {/* Needs Improvement */}

          <div className="rounded-xl border border-orange-500/20 bg-orange-500/5 p-5">

            <h3 className="text-lg font-semibold text-orange-400">

              🎯 Needs Improvement

            </h3>

            <div className="mt-4 flex flex-wrap gap-3">

              {profile.analytics.weakestSkills.map((skill) => (

                <span
                  key={skill}
                  className="rounded-full bg-orange-500/15 px-4 py-2 text-sm font-medium text-orange-300"
                >
                  {skill}
                </span>

              ))}

            </div>

          </div>

          {/* Recent Accepted Problems */}

          <div className="rounded-xl border border-white/10 p-5">

            <h3 className="text-lg font-semibold">

              📝 Recent Accepted Problems

            </h3>

            <div className="mt-4 space-y-3">

              {profile.recentSubmissions.slice(0, 5).map((submission) => (

                <div
                  key={submission.id}
                  className="flex items-center justify-between rounded-lg border border-white/5 bg-white/[0.02] p-3"
                >

                  <div>

                    <p className="font-medium">

                      {submission.title}

                    </p>

                    <p className="mt-1 text-xs app-text-secondary">

                      {submission.titleSlug}

                    </p>

                  </div>

                  <a
                    href={`https://leetcode.com/problems/${submission.titleSlug}`}
                    target="_blank"
                    rel="noreferrer"
                    className="text-orange-400 hover:text-orange-300"
                  >
                    Solve →
                  </a>

                </div>

              ))}

            </div>

          </div>

          {/* Badges */}

          <div className="rounded-xl border border-white/10 p-5">

            <h3 className="text-lg font-semibold">

              🏅 Badges

            </h3>

            <div className="mt-4 flex gap-4 overflow-x-auto pb-2">

              {profile.badges.slice(0, 8).map((badge) => (

                <div
                  key={badge.id}
                  className="min-w-[140px] rounded-xl border border-white/10 bg-white/[0.02] p-4 text-center"
                >

                  <img
                    src={badge.icon}
                    alt={badge.displayName}
                    className="mx-auto h-14 w-14 object-contain"
                  />

                  <p className="mt-3 text-sm font-medium">

                    {badge.displayName}

                  </p>

                </div>

              ))}

            </div>

          </div>

          {editing && (

              <div className="rounded-xl border border-orange-500/20 bg-orange-500/5 p-5">

                  <label className="mb-3 block text-sm font-medium">

                      LeetCode Username

                  </label>

                  <input

                      value={username}

                      onChange={(e) =>
                          setUsername(e.target.value)
                      }

                      className="w-full rounded-xl border border-white/10 bg-transparent px-4 py-3"

                  />

                  <div className="mt-4 flex gap-3">

                      <button

                          onClick={saveUsername}

                          className="rounded-xl bg-orange-500 px-5 py-2 text-white"

                      >

                          Save

                      </button>

                      <button

                          onClick={() => setEditing(false)}

                          className="rounded-xl border border-white/10 px-5 py-2"

                      >

                          Cancel

                      </button>

                  </div>

              </div>

          )}

          {/* Actions */}

          <div className="flex flex-wrap gap-4">

            <button

                onClick={refreshProfile}

                disabled={loading}

                className="rounded-xl bg-green-600 px-5 py-3 font-medium text-white hover:bg-green-700 disabled:opacity-50"

            >

                {loading
                    ? "Refreshing..."
                    : "Refresh"}

            </button>

            <button
                onClick={() => setEditing(true)}
                className="rounded-xl border border-white/10 px-5 py-3 hover:border-orange-500"
            >
                Edit Username
            </button>

            <button
              onClick={disconnectLeetCode}
              className="rounded-xl border border-red-500/30 px-5 py-3 text-red-400 hover:bg-red-500/10"
            >
              Disconnect
            </button>

          </div>

        </div>

    )}

  </div>

);

}