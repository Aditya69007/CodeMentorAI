import axios from "axios";
import { FiAward, FiGithub } from "react-icons/fi";
import { SiLeetcode } from "react-icons/si";
import { FaLinkedin } from "react-icons/fa";
import PlatformCard from "./PlatformCard";
import PlatformDialog from "./PlatformDialog";

import GitHubContent from "./platform-content/GitHubContent";
import LeetCodeContent from "./platform-content/LeetCodeContent";
import LinkedInContent from "./platform-content/LinkedInContent";

import { useEffect, useState } from "react";

import {
  getConnectedAccounts,
  getGitHubProfile,
  updateConnectedAccounts,
  type ConnectedAccountsResponse,
  type GitHubProfileResponse,
} from "../../../services/connectedAccountsService";




export default function DeveloperIdentityCard() {

  const [, setAccounts] =
    useState<ConnectedAccountsResponse | null>(null);

  const [githubProfile, setGithubProfile] =
    useState<GitHubProfileResponse | null>(null);

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
    useState<"github" | "leetcode" | "linkedin" | null>(null);

  useEffect(() => {
    loadAccounts();
  }, []);

  async function loadAccounts() {
    try {
      setLoading(true);

      const connected =
        await getConnectedAccounts();
        console.log("Connected Accounts:", connected);

      setAccounts(connected);

      setGithubUsername(
        connected.githubUsername ?? ""
      );

      if (
        connected.githubConnected &&
        connected.githubUsername
      ) {

        const profile =
          await getGitHubProfile(
            connected.githubUsername
          );
          console.log("GitHub Profile:", profile);

        setGithubProfile(profile);

      } else {

        setGithubProfile(null);

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

      await updateConnectedAccounts({

        githubUsername,

        leetcodeUsername: ""

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

          await updateConnectedAccounts({

              githubUsername,
              leetcodeUsername: ""

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

          await updateConnectedAccounts({

              githubUsername: "",
              leetcodeUsername: ""

          });

          setGithubProfile(null);

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

      <div className="mt-8 rounded-2xl border border-white/10 bg-white/[0.02] p-5">

        <div className="flex items-center justify-between">

          <div>

            <p className="app-text-secondary text-sm">

              Profile Completion

            </p>

            <h3 className="mt-2 text-3xl font-bold">

              67%

            </h3>

          </div>

          <div className="text-right">

            <p className="app-text-secondary text-sm">

              Connected Platforms

            </p>

            <h3 className="mt-2 text-2xl font-bold">

              2 / 3

            </h3>

          </div>

        </div>

        <div className="mt-5 h-3 rounded-full bg-white/10 overflow-hidden">

          <div className="h-full w-2/3 bg-green-500 rounded-full" />

        </div>

      </div>

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
          subtitle="Connect your LeetCode profile"
          status="disconnected"
          buttonText="Connect"
          onClick={() => setSelectedPlatform("leetcode")}
        />

        <PlatformCard
          icon={<FaLinkedin />}
          title="LinkedIn"
          subtitle="Connect your LinkedIn profile"
          status="disconnected"
          buttonText="Connect"
          onClick={() => setSelectedPlatform("linkedin")}
        />

      </div>

        <PlatformDialog
          open={selectedPlatform !== null}
          title={
            selectedPlatform === "github"
              ? "GitHub"
              : selectedPlatform === "leetcode"
              ? "LeetCode"
              : "LinkedIn"
          }
          onClose={() => setSelectedPlatform(null)}
        >

          {selectedPlatform === "github" && (

            <GitHubContent
                profile={githubProfile}
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

            <LeetCodeContent />

          )}

          {selectedPlatform === "linkedin" && (

            <LinkedInContent />

          )}

        </PlatformDialog>

    </section>

  );

}