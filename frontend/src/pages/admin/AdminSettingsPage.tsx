import { useEffect, useState } from "react";
import {
  FiBell,
  FiCheck,
  FiMonitor,
  FiSettings,
  FiShield,
  FiAlertTriangle,
} from "react-icons/fi";
import { toast } from "react-hot-toast";

import {
  getAdminSettings,
  updateAdminSettings,
} from "../../services/adminSettingsService";

export default function AdminSettingsPage() {
  const [compactDashboard, setCompactDashboard] = useState(false);
  const [notifications, setNotifications] = useState(true);
  const [platformAlerts, setPlatformAlerts] = useState(true);

const [defaultPageSize, setDefaultPageSize] = useState(10);
const [autoRefreshDashboard, setAutoRefreshDashboard] = useState(false);
const [autoRefreshInterval, setAutoRefreshInterval] = useState(60);
const [confirmBeforeDelete, setConfirmBeforeDelete] = useState(true);

  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);

  const [saved, setSaved] = useState(false);

  useEffect(() => {
    let mounted = true;

    async function loadSettings() {
      try {
        setLoading(true);

        const settings = await getAdminSettings();

        if (!mounted) {
          return;
        }

        setCompactDashboard(settings.compactDashboard);
        setNotifications(settings.adminNotifications);
        setPlatformAlerts(settings.platformAlerts);
        setDefaultPageSize(settings.defaultPageSize);
        setAutoRefreshDashboard(settings.autoRefreshDashboard);
        setAutoRefreshInterval(settings.autoRefreshInterval);
        setConfirmBeforeDelete(settings.confirmBeforeDelete);
      } catch (error) {
        console.error("Failed to load admin settings", error);

        if (mounted) {
          toast.error("Failed to load admin settings");
        }
      } finally {
        if (mounted) {
          setLoading(false);
        }
      }
    }

    void loadSettings();

    return () => {
      mounted = false;
    };
  }, []);

  async function handleSavePreferences() {
    try {
      setSaving(true);
      setSaved(false);

      const updatedSettings = await updateAdminSettings({
        compactDashboard,
        adminNotifications: notifications,
        platformAlerts,
        defaultPageSize,
        autoRefreshDashboard,
        autoRefreshInterval,
        confirmBeforeDelete,
      });

      setCompactDashboard(updatedSettings.compactDashboard);
      setNotifications(updatedSettings.adminNotifications);
      setPlatformAlerts(updatedSettings.platformAlerts);
      setDefaultPageSize(updatedSettings.defaultPageSize);
      setAutoRefreshDashboard(updatedSettings.autoRefreshDashboard);
      setAutoRefreshInterval(updatedSettings.autoRefreshInterval);
      setConfirmBeforeDelete(updatedSettings.confirmBeforeDelete);

      setSaved(true);

      toast.success("Admin preferences saved successfully");

      window.setTimeout(() => {
        setSaved(false);
      }, 2500);
    } catch (error) {
      console.error("Failed to save admin settings", error);

      toast.error("Failed to save admin preferences");
    } finally {
      setSaving(false);
    }
  }

  return (
    <div className="mx-auto w-full max-w-5xl space-y-6">

      {/* HEADER */}

      <section className="app-surface app-border overflow-hidden rounded-3xl border">
        <div className="p-6 sm:p-8">

          <div className="flex items-start gap-4">

            <div className="flex h-12 w-12 shrink-0 items-center justify-center rounded-2xl bg-blue-500/10">
              <FiSettings className="text-xl text-blue-500" />
            </div>

            <div className="min-w-0">

              <div className="flex flex-wrap items-center gap-2">

                <h1 className="text-2xl font-bold sm:text-3xl">
                  Admin Settings
                </h1>

                {!loading && (
                  <span className="inline-flex items-center gap-1.5 rounded-full bg-emerald-500/10 px-2.5 py-1 text-xs font-semibold text-emerald-400">
                    <span className="h-1.5 w-1.5 rounded-full bg-emerald-400" />
                    Connected
                  </span>
                )}

              </div>

              <p className="mt-2 max-w-2xl text-sm leading-6 app-text-secondary">
                Configure how the CodeMentorAI administrator dashboard
                behaves and how important platform alerts are handled.
              </p>

            </div>

          </div>

        </div>
      </section>


      {/* PREFERENCES */}

      <section className="app-surface app-border overflow-hidden rounded-3xl border">

        {/* HEADER */}

        <div className="border-b app-border p-6 sm:p-7">

          <div className="flex items-start gap-4">

            <div className="flex h-11 w-11 shrink-0 items-center justify-center rounded-xl bg-blue-500/10">
              <FiShield className="text-lg text-blue-500" />
            </div>

            <div>
              <h2 className="text-xl font-bold">
                Administrator Preferences
              </h2>

              <p className="mt-1 text-sm leading-6 app-text-secondary">
                These preferences are saved to your administrator
                settings and restored automatically when you return.
              </p>
            </div>

          </div>

        </div>


        {/* SETTINGS */}

        {loading ? (

          <div className="space-y-4 p-6 sm:p-7">
            <SettingSkeleton />
            <SettingSkeleton />
            <SettingSkeleton />
            <SettingSkeleton />
            <SettingSkeleton />
            <SettingSkeleton />
            <SettingSkeleton />
          </div>

        ) : (

          <div className="space-y-4 p-6 sm:p-7">

            {/* COMPACT DASHBOARD */}

            <SettingRow
              icon={<FiMonitor />}
              title="Compact Dashboard"
              description="Use a more compact layout for administrator tables and dashboard sections."
              enabled={compactDashboard}
              onChange={(value) => {
                setCompactDashboard(value);
                setSaved(false);
              }}
            />


            {/* ADMIN NOTIFICATIONS */}

            <SettingRow
              icon={<FiBell />}
              title="Admin Notifications"
              description="Receive important notifications related to administrator and platform activity."
              enabled={notifications}
              onChange={(value) => {
                setNotifications(value);
                setSaved(false);
              }}
            />


            {/* PLATFORM ALERTS */}

            <SettingRow
              icon={<FiAlertTriangle />}
              title="Platform Alerts"
              description="Show important system and platform alerts inside the administrator interface."
              enabled={platformAlerts}
              onChange={(value) => {
                setPlatformAlerts(value);
                setSaved(false);
              }}
            />


            {/* DEFAULT PAGE SIZE */}

            <div className="rounded-2xl border app-border app-surface-secondary p-5">

              <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">

                <div className="min-w-0">
                  <p className="font-semibold">
                    Default Page Size
                  </p>

                  <p className="mt-1 text-sm leading-6 app-text-secondary">
                    Choose how many records are shown in administrator tables by default.
                  </p>
                </div>

                <select
                  value={defaultPageSize}
                  onChange={(e) => {
                    setDefaultPageSize(Number(e.target.value));
                    setSaved(false);
                  }}
                  className="app-input h-12 w-full rounded-xl border px-4 leading-[3rem] outline-none transition focus:border-blue-500 focus:ring-2 focus:ring-blue-500/10 sm:w-44"
                >
                  <option value={10}>10 records</option>
                  <option value={20}>20 records</option>
                  <option value={50}>50 records</option>
                  <option value={100}>100 records</option>
                </select>

              </div>

            </div>


            {/* AUTO REFRESH */}

            <SettingRow
              icon={<FiMonitor />}
              title="Auto Refresh Dashboard"
              description="Automatically refresh administrator dashboard data."
              enabled={autoRefreshDashboard}
              onChange={(value) => {
                setAutoRefreshDashboard(value);
                setSaved(false);
              }}
            />


            {/* AUTO REFRESH INTERVAL */}

            <div
              className={`rounded-2xl border app-border app-surface-secondary p-5 transition ${
                !autoRefreshDashboard ? "opacity-60" : ""
              }`}
            >

              <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">

                <div className="min-w-0">

                  <p className="font-semibold">
                    Auto Refresh Interval
                  </p>

                  <p className="mt-1 text-sm leading-6 app-text-secondary">
                    Choose how frequently dashboard data should refresh automatically.
                  </p>

                </div>

                <select
                  value={autoRefreshInterval}
                  onChange={(e) => {
                    setAutoRefreshInterval(Number(e.target.value));
                    setSaved(false);
                  }}
                  disabled={!autoRefreshDashboard}
                  className="app-input h-12 w-full rounded-xl border px-4 leading-[3rem] outline-none transition focus:border-blue-500 focus:ring-2 focus:ring-blue-500/10 sm:w-44"
                >
                  <option value={30}>Every 30 seconds</option>
                  <option value={60}>Every 1 minute</option>
                  <option value={120}>Every 2 minutes</option>
                  <option value={300}>Every 5 minutes</option>
                </select>

              </div>

            </div>


            {/* CONFIRM BEFORE DELETE */}

            <SettingRow
              icon={<FiShield />}
              title="Confirm Before Delete"
              description="Require confirmation before permanently deleting administrator or platform data."
              enabled={confirmBeforeDelete}
              onChange={(value) => {
                setConfirmBeforeDelete(value);
                setSaved(false);
              }}
            />

          </div>

        )}


        {/* FOOTER */}

        {!loading && (
          <div className="flex flex-col gap-4 border-t app-border p-6 sm:flex-row sm:items-center sm:justify-between sm:p-7">

            <div className="flex items-center gap-2 text-sm app-text-secondary">

              {saved ? (
                <>
                  <span className="flex h-7 w-7 items-center justify-center rounded-full bg-emerald-500/10">
                    <FiCheck className="text-emerald-400" />
                  </span>

                  <span className="text-emerald-400">
                    Changes saved successfully
                  </span>
                </>
              ) : (
                <>
                  <span className="h-2 w-2 rounded-full bg-blue-500" />

                  <span>
                    Changes are saved when you click Save Preferences.
                  </span>
                </>
              )}

            </div>


            <button
              type="button"
              onClick={() => void handleSavePreferences()}
              disabled={saving}
              className="
                inline-flex
                min-w-[180px]
                items-center
                justify-center
                gap-2
                rounded-xl
                bg-gradient-to-r
                from-blue-600
                to-indigo-600
                px-6
                py-3
                font-semibold
                text-white
                shadow-lg
                shadow-blue-500/20
                transition
                hover:-translate-y-0.5
                hover:shadow-blue-500/30
                disabled:cursor-not-allowed
                disabled:opacity-60
                disabled:hover:translate-y-0
              "
            >
              {saving ? (
                <>
                  <LoadingSpinner />
                  Saving...
                </>
              ) : saved ? (
                <>
                  <FiCheck />
                  Saved
                </>
              ) : (
                <>
                  <FiSettings />
                  Save Preferences
                </>
              )}
            </button>

          </div>
        )}

      </section>


      {/* INFORMATION */}

      <section className="rounded-3xl border border-blue-500/15 bg-blue-500/5 p-5 sm:p-6">

        <div className="flex items-start gap-4">

          <div className="flex h-10 w-10 shrink-0 items-center justify-center rounded-xl bg-blue-500/10">
            <FiSettings className="text-blue-400" />
          </div>

          <div>

            <h3 className="font-semibold">
              Settings are synchronized
            </h3>

            <p className="mt-1 text-sm leading-6 app-text-secondary">
              Your preferences are stored on the server. Changing
              devices or refreshing the page will not reset your
              saved administrator settings.
            </p>

          </div>

        </div>

      </section>

    </div>
  );
}


/* =========================================================
   SETTING ROW
========================================================= */

function SettingRow({
  icon,
  title,
  description,
  enabled,
  onChange,
}: {
  icon: React.ReactNode;
  title: string;
  description: string;
  enabled: boolean;
  onChange: (value: boolean) => void;
}) {
  return (
    <div
      className="
        group
        flex
        flex-col
        gap-5
        rounded-2xl
        border
        app-border
        app-surface-secondary
        p-5
        transition
        hover:border-blue-500/30
        sm:flex-row
        sm:items-center
        sm:justify-between
      "
    >

      <div className="flex min-w-0 items-start gap-4">

        <div
          className={`
            flex
            h-11
            w-11
            shrink-0
            items-center
            justify-center
            rounded-xl
            transition
            ${
              enabled
                ? "bg-blue-500/10 text-blue-400"
                : "bg-slate-500/10 app-text-muted"
            }
          `}
        >
          {icon}
        </div>


        <div className="min-w-0">

          <div className="flex flex-wrap items-center gap-2">

            <p className="font-semibold">
              {title}
            </p>

            <span
              className={`
                rounded-full
                px-2
                py-0.5
                text-[10px]
                font-bold
                uppercase
                tracking-wide
                ${
                  enabled
                    ? "bg-emerald-500/10 text-emerald-400"
                    : "bg-slate-500/10 app-text-muted"
                }
              `}
            >
              {enabled ? "Enabled" : "Disabled"}
            </span>

          </div>

          <p className="mt-1 text-sm leading-6 app-text-secondary">
            {description}
          </p>

        </div>

      </div>


      {/* SWITCH */}

      <button
        type="button"
        role="switch"
        aria-checked={enabled}
        aria-label={`${title}: ${enabled ? "enabled" : "disabled"}`}
        onClick={() => onChange(!enabled)}
        className={`
          relative
          h-7
          w-12
          shrink-0
          rounded-full
          transition-all
          duration-200
          focus:outline-none
          focus:ring-2
          focus:ring-blue-500/30
          ${
            enabled
              ? "bg-blue-600 shadow-lg shadow-blue-500/20"
              : "bg-slate-600"
          }
        `}
      >
        <span
          className={`
            absolute
            top-1
            h-5
            w-5
            rounded-full
            bg-white
            shadow-sm
            transition-all
            duration-200
            ${
              enabled
                ? "left-6"
                : "left-1"
            }
          `}
        />
      </button>

    </div>
  );
}


/* =========================================================
   LOADING SKELETON
========================================================= */

function SettingSkeleton() {
  return (
    <div className="flex items-center justify-between gap-4 rounded-2xl border app-border app-surface-secondary p-5">

      <div className="flex flex-1 items-center gap-4">

        <div className="h-11 w-11 shrink-0 animate-pulse rounded-xl bg-slate-500/10" />

        <div className="flex-1 space-y-2">

          <div className="h-4 w-40 animate-pulse rounded bg-slate-500/10" />

          <div className="h-3 w-3/4 animate-pulse rounded bg-slate-500/10" />

        </div>

      </div>

      <div className="h-7 w-12 animate-pulse rounded-full bg-slate-500/10" />

    </div>
  );
}


/* =========================================================
   LOADING SPINNER
========================================================= */

function LoadingSpinner() {
  return (
    <span
      className="
        h-4
        w-4
        animate-spin
        rounded-full
        border-2
        border-white/30
        border-t-white
      "
    />
  );
}