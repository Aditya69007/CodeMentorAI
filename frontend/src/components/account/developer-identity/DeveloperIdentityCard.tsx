import axios from "axios";
import { FiAward, FiGithub } from "react-icons/fi";
import { SiLeetcode } from "react-icons/si";
import PlatformCard from "./PlatformCard";
import PlatformDialog from "./PlatformDialog";

import GitHubContent from "./platform-content/GitHubContent";
import LeetCodeContent from "./platform-content/LeetCodeContent";

import { useEffect, useState } from "react";


import {
  getConnectedAccounts,
  getGitHubDashboard,
  updateConnectedAccounts,
  type ConnectedAccountsResponse,
  type GitHubProfileResponse,
} from "../../../services/connectedAccountsService";
import type { GitHubDashboard } from "../../../types/github";



export default function DeveloperIdentityCard() {

  const [, setAccounts] =
    useState<ConnectedAccountsResponse | null>(null);

  const [githubProfile, setGithubProfile] =
    useState<GitHubProfileResponse | null>(null);

  const [githubDashboard, setGithubDashboard] =
      useState<GitHubDashboard | null>(null);

  const [leetcodeConnected, setLeetcodeConnected] =
      useState(false);

  const [leetcodeUsername, setLeetcodeUsername] =
      useState("");

  const [githubUsername, setGithubUsername] =
    useState("");

  const [editingGithub, setEditingGithub] = useState(false);

  const [showDisconnectDialog, setShowDisconnectDialog] = useState(false);

  const [, setLoading] =
    useState(true);

  const [saving, setSaving] =
    useState(false);
  const [githubError, setGithubError] = useState("");

  const [selectedPlatform, setSelectedPlatform] =
    useState<"github" | "leetcode" | null>(null);

  useEffect(() => {
    loadAccounts();
  }, []);

  async function loadAccounts() {
    try {
      setLoading(true);

      const connected =
        await getConnectedAccounts();

      setAccounts(connected);

      setGithubUsername(
        connected.githubUsername ?? ""
      );

      setLeetcodeUsername(
          connected.leetcodeUsername ?? ""
      );

      setLeetcodeConnected(
          connected.leetcodeConnected
      );

      if (
        connected.githubConnected &&
        connected.githubUsername
      ) {

    const dashboard =
        await getGitHubDashboard(
            connected.githubUsername
        );

    setGithubDashboard(dashboard);

    setGithubProfile(
        dashboard.profile
    );

      } else {

        setGithubProfile(null);
        setGithubDashboard(null);

      }

    } catch (error) {

      console.error(error);

    } finally {

      setLoading(false);

    }
  }

  async function connectGitHub() {

    setGithubError("");

    try {

      setSaving(true);

      const accounts = await getConnectedAccounts();

      await updateConnectedAccounts({
          githubUsername,
          leetcodeUsername: accounts.leetcodeUsername ?? ""
      });

      await loadAccounts();

    }catch (error: unknown) {

      if (
          axios.isAxiosError(error) &&
          error.response?.status === 404
      ) {

          setGithubError(
              "GitHub username not found."
          );

      } else {

          setGithubError(
              "Unable to connect to GitHub. Please try again."
          );

      }

  } finally {

      setSaving(false);

    }

  }

  async function refreshGitHub() {

      try {

          setSaving(true);

          await loadAccounts();

      } finally {

          setSaving(false);

      }

  }

  async function saveGithubUsername() {

      try {

          setSaving(true);

          const accounts = await getConnectedAccounts();

          await updateConnectedAccounts({
              githubUsername,
              leetcodeUsername: accounts.leetcodeUsername ?? ""
          });

          await loadAccounts();

          setEditingGithub(false);

      } catch (error: unknown) {

          if (
              axios.isAxiosError(error) &&
              error.response?.status === 404
          ) {

              setGithubError(
                  "GitHub username not found."
              );

          } else {

              setGithubError(
                  "Unable to update GitHub username."
              );

          }

      } finally {

          setSaving(false);

      }

  }

  async function disconnectGitHub() {

      try {

          setSaving(true);

          const accounts =
              await getConnectedAccounts();

          await updateConnectedAccounts({

              githubUsername: "",

              leetcodeUsername:
                  accounts.leetcodeUsername ?? ""

          });

          setGithubProfile(null);

          setGithubDashboard(null);

          setGithubUsername("");

          setEditingGithub(false);

          setGithubError("");

          setShowDisconnectDialog(false);

          await loadAccounts();

      } catch {

          setGithubError(
              "Unable to disconnect GitHub."
          );

      } finally {

          setSaving(false);

      }

  }


  return (

    <section className="app-surface app-border rounded-2xl p-6">

      {/* Header */}

      <div className="flex items-start gap-4">

        <div className="flex h-14 w-14 items-center justify-center rounded-2xl bg-green-500/10">

          <FiAward className="text-3xl text-green-500" />

        </div>

        <div>

          <h2 className="text-2xl font-bold">

            Developer Identity

          </h2>

          <p className="mt-2 max-w-2xl app-text-secondary">

            Connect your developer platforms once.
            CodeMentorAI will automatically build your
            portfolio, resume, AI insights and recruiter profile.

          </p>

        </div>

      </div>

      {/* Progress */}



      {/* Platform Cards */}

      <div className="mt-8 space-y-4">

        <PlatformCard
          icon={<FiGithub />}
          title="GitHub"
          subtitle={
            githubProfile
              ? `@${githubProfile.username} • ${githubProfile.publicRepositories} repositories`
              : "Connect your GitHub account"
          }
          status={
            githubProfile
              ? "connected"
              : "disconnected"
          }
          buttonText={
            githubProfile
              ? "View Details"
              : "Connect"
          }
          onClick={() => setSelectedPlatform("github")}
        />

        <PlatformCard
            icon={<SiLeetcode />}
            title="LeetCode"
            subtitle={
                leetcodeConnected
                    ? `@${leetcodeUsername}`
                    : "Connect your LeetCode profile"
            }
            status={
                leetcodeConnected
                    ? "connected"
                    : "disconnected"
            }
            buttonText={
                leetcodeConnected
                    ? "View Details"
                    : "Connect"
            }
            onClick={() => setSelectedPlatform("leetcode")}
        />

      </div>

        <PlatformDialog
          open={selectedPlatform !== null}
          title={
            selectedPlatform === "github"
              ? "GitHub"
              : "LeetCode"
          }
          onClose={() => setSelectedPlatform(null)}
        >

          {selectedPlatform === "github" && (

            <GitHubContent
                dashboard={githubDashboard}
                githubUsername={githubUsername}
                setGithubUsername={setGithubUsername}
                connectGitHub={connectGitHub}
                refreshGitHub={refreshGitHub}
                saving={saving}
                error={githubError}
                editingGithub={editingGithub}
                setEditingGithub={setEditingGithub}
                saveGithubUsername={saveGithubUsername}
                showDisconnectDialog={showDisconnectDialog}
                setShowDisconnectDialog={setShowDisconnectDialog}
                disconnectGitHub={disconnectGitHub}
            />

          )}

          {selectedPlatform === "leetcode" && (

            <LeetCodeContent
                onConnected={loadAccounts}
            />

          )}

        </PlatformDialog>

    </section>

  );

}