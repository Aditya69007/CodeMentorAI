import {
  FiExternalLink,
  FiGlobe,
  FiShare2,
} from "react-icons/fi";
import toast from "react-hot-toast";
import { useAuth } from "../../hooks/useAuth";

export default function PublicPortfolioCard() {
  const { user } = useAuth();

  if (!user?.username) return null;

  const portfolioUrl =
    `${window.location.origin}/portfolio/${user.username}`;

  const copyPortfolioLink = async () => {
    try {
      await navigator.clipboard.writeText(portfolioUrl);
      toast.success("Portfolio link copied!");
    } catch (error) {
      console.error(error);
      toast.error("Failed to copy portfolio link.");
    }
  };

  return (
    <section className="app-surface app-border rounded-3xl p-6 sm:p-7">
      <div className="flex items-start gap-4">
        <div className="flex h-12 w-12 shrink-0 items-center justify-center rounded-2xl bg-emerald-500/10">
          <FiGlobe className="text-xl text-emerald-500" />
        </div>

        <div>
          <h2 className="text-xl font-bold">
            Public Developer Profile
          </h2>

          <p className="mt-1 text-sm app-text-secondary">
            Share your CodeMentorAI developer portfolio with recruiters and
            other developers.
          </p>
        </div>
      </div>

      <div className="mt-6 rounded-2xl border border-emerald-500/20 bg-emerald-500/5 p-5">
        <p className="text-xs font-semibold uppercase tracking-wider app-text-muted">
          Your public portfolio
        </p>

        <p className="mt-2 break-all text-lg font-semibold text-emerald-500">
          /portfolio/{user.username}
        </p>

        <div className="mt-5 flex flex-wrap gap-3">
          <a
            href={portfolioUrl}
            target="_blank"
            rel="noreferrer"
            className="inline-flex items-center gap-2 rounded-xl bg-emerald-600 px-5 py-2.5 text-sm font-semibold text-white transition hover:bg-emerald-700"
          >
            <FiExternalLink />
            View Portfolio
          </a>

          <button
            type="button"
            onClick={copyPortfolioLink}
            className="inline-flex items-center gap-2 rounded-xl border app-border px-5 py-2.5 text-sm font-semibold transition hover:border-emerald-500 hover:text-emerald-500"
          >
            <FiShare2 />
            Copy Link
          </button>
        </div>
      </div>
    </section>
  );
}