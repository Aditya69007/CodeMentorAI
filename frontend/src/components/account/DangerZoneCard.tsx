import {
  FiAlertTriangle,
  FiLogOut,
  FiTrash2,
} from "react-icons/fi";

import { useState } from "react";
import toast from "react-hot-toast";
import sessionService from "../../services/sessionService";
import { deleteAccount } from "../../services/userService";
import { useNavigate } from "react-router-dom";
import DeleteAccountModal from "../../pages/settings/DeleteAccountModal";

import { useAuth } from "../../context/useAuth";

export default function DangerZoneCard() {

  const { user, logout } = useAuth();

  const navigate = useNavigate();

  const [deleting, setDeleting] = useState(false);

  const [showDeleteModal, setShowDeleteModal] = useState(false);

  const [loggingOut, setLoggingOut] = useState(false);

  const handleLogoutAllDevices = async () => {

    if (!user?.sessionId) {
      toast.error("Session not found.");
      return;
    }

    try {

      setLoggingOut(true);

      await sessionService.logoutAll(user.sessionId);

      toast.success("Logged out from all other devices.");

    } catch (error) {

      console.error(error);

      toast.error("Failed to logout from all devices.");

    } finally {

      setLoggingOut(false);

    }

  };

  const handleDeleteAccount = async (
    password: string
  ) => {

    try {

      setDeleting(true);

      await deleteAccount({
        password,
      });

      logout();

      toast.success(
        "Account deleted successfully."
      );

      navigate("/login", {
        replace: true,
      });

    } catch (error) {

      console.error(error);

      toast.error(
        "Failed to delete account."
      );

    } finally {

      setDeleting(false);

      setShowDeleteModal(false);

    }

  };


  return (
    <section className="rounded-2xl border border-red-500/30 bg-red-500/5 p-6">

      <div className="mb-6 flex items-center gap-3">

        <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-red-500/10">

          <FiAlertTriangle className="text-xl text-red-500" />

        </div>

        <div>

          <h2 className="text-xl font-bold text-red-500">
            Danger Zone
          </h2>

          <p className="app-text-secondary mt-1">
            Sensitive account actions. Proceed carefully.
          </p>

        </div>

      </div>

      <div className="space-y-3">

        <button
            onClick={handleLogoutAllDevices}
            disabled={loggingOut}
            className="flex w-full items-center justify-between rounded-xl border border-red-500/20 p-4 transition hover:bg-red-500/10 disabled:opacity-60"
        >

          <div className="flex items-center gap-3">

            <FiLogOut />

            <span>Logout From All Devices</span>

          </div>

          <span className="text-sm font-medium text-red-500">
              {loggingOut ? "Logging out..." : "Active"}
          </span>

        </button>

    <button
      onClick={() => setShowDeleteModal(true)}
      className="flex w-full items-center justify-between rounded-xl border border-red-500/20 p-4 transition hover:bg-red-500/10"
    >

      <div className="flex items-center gap-3">

        <FiTrash2 />

        <span>Delete Account</span>

      </div>

      <span className="font-semibold text-red-500">
        Permanent
      </span>

    </button>

      </div>

    <DeleteAccountModal
      open={showDeleteModal}
      loading={deleting}
      onClose={() => setShowDeleteModal(false)}
      onConfirm={handleDeleteAccount}
    />

    </section>
  );
}