import {
  FiDownload,
  FiGithub,
  FiLinkedin,
  FiShare2,
} from "react-icons/fi";

import UserHero from "../common/UserHero";
import { useAuth } from "../../hooks/useAuth";

export default function PortfolioHero() {

  const { user } = useAuth();

  if (!user) return null;

  return (

    <UserHero
      user={user}
      title="AI Developer Portfolio"
      badges={[
        user.role,
        user.provider,
      ]}
      actions={
        <>
          <button className="rounded-xl border px-4 py-3 transition hover:border-blue-500">
            <FiGithub />
          </button>

          <button className="rounded-xl border px-4 py-3 transition hover:border-blue-500">
            <FiLinkedin />
          </button>

          <button className="rounded-xl border px-4 py-3 transition hover:border-blue-500">
            <FiShare2 />
          </button>

          <button className="rounded-xl bg-blue-600 px-5 py-3 text-white transition hover:bg-blue-700">

            <span className="flex items-center gap-2">

              <FiDownload />

              Resume

            </span>

          </button>
        </>
      }
    />

  );

}