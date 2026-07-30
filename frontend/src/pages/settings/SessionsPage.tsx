import { useEffect, useState } from "react";
import toast from "react-hot-toast";
import { useAuth } from "../../hooks/useAuth";
import sessionService from "../../services/sessionService";

import type { SessionInfo } from "../../components/security/types";

import SecurityHeader from "../../components/security/SecurityHeader";
import SecuritySummary from "../../components/security/SecuritySummary";
import SessionCard from "../../components/security/SessionCard";
import SessionSkeleton from "../../components/security/SessionSkeleton";
import EmptySessions from "../../components/security/EmptySessions";
import LogoutAllDialog from "../../components/security/LogoutAllDialog";

export default function SessionsPage() {

    const { user } = useAuth();

  const [sessions, setSessions] = useState<SessionInfo[]>([]);
  const [loading, setLoading] = useState(true);

  const [dialogOpen, setDialogOpen] = useState(false);
  const [logoutLoading, setLogoutLoading] = useState(false);

  useEffect(() => {

    const loadSessions = async () => {

      try {

        const data = await sessionService.getSessions();

        setSessions(data);

      } catch {

        toast.error("Unable to load active sessions.");

      } finally {

        setLoading(false);

      }

    };

    loadSessions();

  }, []);

  const handleLogoutSession = async (sessionId: number) => {

    try {

      await sessionService.logoutSession(sessionId);

      setSessions((prev) =>
        prev.filter((session) => session.id !== sessionId)
      );

      toast.success("Session logged out successfully.");

    } catch {

      toast.error("Failed to logout session.");

    }

  };

    const handleLogoutAll = async () => {

    try {

        setLogoutLoading(true);

        /*
        Temporary:
        We'll replace "1" with the current session id
        after we integrate AuthContext.
        */

        if (!user) {
        toast.error("User session not found.");
        return;
        }

        await sessionService.logoutAll(user.sessionId);

        const data = await sessionService.getSessions();

        setSessions(data);

        toast.success("Logged out from all other devices.");

        setDialogOpen(false);

    } catch {

        toast.error("Failed to logout all other devices.");

    } finally {

        setLogoutLoading(false);

    }

    };

  if (loading) {
    return (
      <div className="p-8">
        <SessionSkeleton />
      </div>
    );
  }

  return (

    <div className="mx-auto max-w-7xl space-y-8 p-8">

      <SecurityHeader />

      <SecuritySummary
        totalDevices={sessions.length}
        activeDevices={sessions.length}
        currentDevice="Current Device"
      />

      {sessions.length === 0 ? (

        <EmptySessions />

      ) : (

        <div className="space-y-5">

          {sessions.map((session) => (

            <SessionCard
              key={session.id}
              session={session}
              isCurrent={false}
              onLogout={handleLogoutSession}
            />

          ))}

        </div>

      )}

      <div className="flex justify-end">

        <button
          onClick={() => setDialogOpen(true)}
          className="rounded-xl bg-red-600 px-6 py-3 font-semibold text-white hover:bg-red-700"
        >
          Logout All Other Devices
        </button>

      </div>

      <LogoutAllDialog
        open={dialogOpen}
        loading={logoutLoading}
        onClose={() => setDialogOpen(false)}
        onConfirm={handleLogoutAll}
      />

    </div>

  );

}