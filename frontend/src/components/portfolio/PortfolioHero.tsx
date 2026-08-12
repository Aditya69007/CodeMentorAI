import {
  FiDownload,
  FiGithub,
  FiShare2,
} from "react-icons/fi";
import toast from "react-hot-toast";
import type { UserProfile } from "../../types/userProfile";

import { SiLeetcode } from "react-icons/si";
import { downloadDeveloperReport } from "../../services/exportService";
import UserHero from "../common/UserHero";
import { useAuth } from "../../hooks/useAuth";

import {
  getConnectedAccounts,
  type ConnectedAccountsResponse,
} from "../../services/connectedAccountsService";

import {
  useState,
  useEffect,
} from "react";

interface PortfolioHeroProps {
  publicProfile?: UserProfile;
  publicView?: boolean;
}

export default function PortfolioHero({
  publicProfile,
  publicView = false,
}: PortfolioHeroProps) {

  const { user } = useAuth();

  const [downloading, setDownloading] =
    useState(false);

  const [accounts, setAccounts] =
    useState<ConnectedAccountsResponse | null>(null);

  const displayUser = publicView
    ? {
        userId: 0,
        firstName: publicProfile?.firstName ?? "",
        lastName: publicProfile?.lastName ?? "",
        username: publicProfile?.username ?? "",
        email: publicProfile?.email ?? "",
        role: publicProfile?.role ?? "USER",
        provider: publicProfile?.provider ?? "LOCAL",
        profilePicture: publicProfile?.profilePicture,
        sessionId: 0,
      }
    : user;

  /*
   * Connected accounts are required only for
   * the authenticated/private portfolio.
   *
   * Public portfolio already receives GitHub
   * and LeetCode usernames from the backend.
   */
  useEffect(() => {

    if (publicView) {
      return;
    }

    async function loadAccounts() {

      try {

        const data =
          await getConnectedAccounts();

        setAccounts(data);

      } catch (error) {

        console.error(error);

      }

    }

    void loadAccounts();

  }, [publicView]);


  const sharePortfolio = async () => {

    try {

      const shareUrl = publicView
        ? window.location.href
        : `${window.location.origin}/portfolio/${displayUser?.username}`;

      await navigator.clipboard.writeText(
        shareUrl
      );

      toast.success(
        "Portfolio link copied!"
      );

    } catch {

      toast.error(
        "Failed to copy portfolio link."
      );

    }

  };


  const openGitHub = () => {

    const githubUsername = publicView
      ? publicProfile?.githubUsername
      : accounts?.githubUsername;

    if (!githubUsername) {
      return;
    }

    window.open(
      `https://github.com/${githubUsername}`,
      "_blank"
    );

  };


  const openLeetCode = () => {

    const leetcodeUsername = publicView
      ? publicProfile?.leetcodeUsername
      : accounts?.leetcodeUsername;

    if (!leetcodeUsername) {
      return;
    }

    window.open(
      `https://leetcode.com/u/${leetcodeUsername}`,
      "_blank"
    );

  };


  const handleDownload = async () => {

    try {

      setDownloading(true);

      await downloadDeveloperReport();

    } catch (error) {

      console.error(error);

    } finally {

      setDownloading(false);

    }

  };


  if (!displayUser) {
    return null;
  }


  return (

    <UserHero
      user={displayUser}
      title="AI Developer Portfolio"

      badges={[
        displayUser.role,
        displayUser.provider,
      ]}

      actions={

        <div className="space-y-5">

          <button
            onClick={handleDownload}
            disabled={downloading}
            className="w-full rounded-2xl bg-blue-600 py-5 text-lg font-semibold text-white transition hover:bg-blue-700 disabled:opacity-50"
          >

            <span className="flex items-center justify-center gap-3">

              <FiDownload />

              {downloading
                ? "Generating..."
                : "Download Report"}

            </span>

          </button>


          <div>

            <div className="grid grid-cols-3 gap-3">

              <button
                onClick={openGitHub}
                className="flex items-center justify-center gap-2 rounded-xl border border-slate-700 py-3 transition hover:border-blue-500"
              >

                <FiGithub />

                GitHub

              </button>


              <button
                onClick={openLeetCode}
                className="flex items-center justify-center gap-2 rounded-xl border border-slate-700 py-3 transition hover:border-orange-500"
              >

                <SiLeetcode className="text-orange-500" />

                LeetCode

              </button>


              <button
                onClick={sharePortfolio}
                className="flex items-center justify-center gap-2 rounded-xl border border-slate-700 py-3 transition hover:border-blue-500"
              >

                <FiShare2 />

                Share

              </button>

            </div>

          </div>


          <div className="rounded-2xl border border-blue-500/20 bg-blue-500/5 p-5">

            <h3 className="font-semibold text-blue-400">
              💡 Portfolio Tip
            </h3>

            <p className="mt-2 text-sm leading-7 text-slate-400">
              Keep solving LeetCode consistently,
              maintain an active GitHub profile, and
              build production-ready projects to improve
              your recruiter score.
            </p>

          </div>

        </div>
      }

    />

  );
}