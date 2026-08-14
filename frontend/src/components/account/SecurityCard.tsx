import { useEffect, useState } from "react";
import { FiLock, FiChevronRight } from "react-icons/fi";
import { useNavigate, useSearchParams } from "react-router-dom";
import ChangePasswordDialog from "./ChangePasswordDialog";

export default function SecurityCard() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  const [openDialog, setOpenDialog] = useState(
    searchParams.get("open") === "password"
  );

  useEffect(() => {
    if (searchParams.get("open") === "password") {
      navigate("/account/settings", {
        replace: true,
      });
    }
  }, [searchParams, navigate]);

  return (
    <>
      <section className="app-surface app-border rounded-2xl p-6">
        <div className="flex items-start justify-between">
          <div>
            <div className="mb-3 flex h-12 w-12 items-center justify-center rounded-xl bg-blue-500/10">
              <FiLock className="text-xl text-blue-500" />
            </div>

            <h2 className="text-xl font-bold">
              Security
            </h2>

            <p className="app-text-secondary mt-2">
              Manage your password and secure your account.
            </p>
          </div>

          <button
            onClick={() => setOpenDialog(true)}
            className="rounded-xl border px-4 py-2 transition hover:border-blue-500 hover:text-blue-500"
          >
            <span className="flex items-center gap-2">
              Manage
              <FiChevronRight />
            </span>
          </button>
        </div>

        <div className="mt-6 space-y-4">
          <div className="flex items-center justify-between">
            <span>Password</span>

            <span className="font-medium text-emerald-500">
              Configured
            </span>
          </div>

          <div className="flex items-center justify-between">
            <span>Two Factor Authentication</span>

            <span className="font-medium text-yellow-500">
              Coming Soon
            </span>
          </div>

          <div className="flex items-center justify-between">
            <span>Recent Login Activity</span>

            <span className="font-medium text-yellow-500">
              Coming Soon
            </span>
          </div>
        </div>
      </section>

      <ChangePasswordDialog
        open={openDialog}
        onClose={() => setOpenDialog(false)}
      />
    </>
  );
}