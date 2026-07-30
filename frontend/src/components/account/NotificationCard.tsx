import { useEffect, useState } from "react";
import { FiBell } from "react-icons/fi";
import toast from "react-hot-toast";

import {
  getNotificationPreferences,
  updateNotificationPreferences,
  type NotificationPreferences,
} from "../../services/notificationService";

interface ToggleProps {
  label: string;
  value: boolean;
  saving: boolean;
  field: keyof NotificationPreferences;
  onToggle: (
    field: keyof NotificationPreferences
  ) => void;
}

function Toggle({
  label,
  value,
  saving,
  field,
  onToggle,
}: ToggleProps) {
  return (
    <div className="flex items-center justify-between">
      <span className="text-[15px] font-medium">{label}</span>

      <button
        type="button"
        disabled={saving}
        onClick={() => onToggle(field)}
        aria-pressed={value}
        className={`
          relative inline-flex h-6 w-11 items-center rounded-full
          transition-all duration-300 ease-in-out
          focus:outline-none
          focus:ring-2 focus:ring-blue-500/50
          disabled:cursor-not-allowed
          disabled:opacity-60
          ${
            value
              ? "bg-blue-500"
              : "bg-gray-600"
          }
        `}
      >
        <span
          className={`
            inline-block h-4.5 w-4.5 transform rounded-full
            bg-white shadow-md transition-transform duration-300
            ${
              value
                ? "translate-x-6"
                : "translate-x-1"
            }
          `}
        />

        {saving && (
          <span className="absolute inset-0 rounded-full border border-blue-400 animate-pulse" />
        )}
      </button>
    </div>
  );
}


export default function NotificationCard() {
  const [preferences, setPreferences] =
    useState<NotificationPreferences>({
      emailNotifications: false,
      aiLearningTips: false,
      contestReminders: false,
      weeklyGrowthReport: false,
      interviewAlerts: false,
    });

  const [loading, setLoading] = useState(true);
  const [savingField, setSavingField] =
    useState<keyof NotificationPreferences | null>(null);

  useEffect(() => {
    void (async () => {
      try {
        const response = await getNotificationPreferences();
        setPreferences(response);
      } catch (error) {
        console.error(error);
        toast.error("Failed to load notification preferences.");
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  const updatePreference = async (
    key: keyof NotificationPreferences
  ) => {

    const updated = {
      ...preferences,
      [key]: !preferences[key],
    };

    setPreferences(updated);
    setSavingField(key);

    try {

      await updateNotificationPreferences(
        updated
      );

      toast.success(
        "Notification preferences updated."
      );

    } catch (error) {

      console.error(error);

      toast.error(
        "Failed to update notification preferences."
      );

      setPreferences(preferences);

    } finally {

      setSavingField(null);

    }

  };

  if (loading) {

    return (

      <section className="app-surface app-border rounded-2xl p-6">

        Loading notification preferences...

      </section>

    );

  }



  return (

    <section className="app-surface app-border rounded-2xl p-6">

      <div className="mb-6">

        <div className="mb-3 flex h-12 w-12 items-center justify-center rounded-xl bg-amber-500/10">

          <FiBell className="text-xl text-amber-500" />

        </div>

        <h2 className="text-xl font-bold">
          Notifications
        </h2>

        <p className="app-text-secondary mt-2">

          Control how you receive updates from CodeMentorAI.

        </p>

      </div>

      <div className="space-y-6">

      <Toggle
          label="Email Notifications"
          value={preferences.emailNotifications}
          field="emailNotifications"
          saving={savingField === "emailNotifications"}
          onToggle={updatePreference}
      />

        <Toggle
          label="AI Learning Tips"
          value={preferences.aiLearningTips}
          field="aiLearningTips"
          saving={savingField === "aiLearningTips"}
          onToggle={updatePreference}
        />

        <Toggle
          label="Contest Reminders"
          value={preferences.contestReminders}
          field="contestReminders"
          saving={savingField === "contestReminders"}
          onToggle={updatePreference}
        />

        <Toggle
          label="Weekly Growth Report"
          value={preferences.weeklyGrowthReport}
          field="weeklyGrowthReport"
          saving={savingField === "weeklyGrowthReport"}
          onToggle={updatePreference}
        />

        <Toggle
          label="Interview Alerts"
          value={preferences.interviewAlerts}
          field="interviewAlerts"
          saving={savingField === "interviewAlerts"}
          onToggle={updatePreference}
        />

      </div>

    </section>
  
  );

}