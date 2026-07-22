import SecurityCard from "../../components/account/SecurityCard";
import AppearanceCard from "../../components/account/AppearanceCard";
import SettingsNavigation from "../../components/account/SettingsNavigation";
import NotificationCard from "../../components/account/NotificationCard";
import DangerZoneCard from "../../components/account/DangerZoneCard";
import DeveloperIdentityCard from "../../components/account/developer-identity/DeveloperIdentityCard";


export default function SettingsPage() {
  return (

    <div className="space-y-8">

      <div>

        <h1 className="text-3xl font-bold">

          Settings

        </h1>

        <p className="app-text-secondary mt-2">

          Manage your account preferences and security.

        </p>

      </div>

       <SettingsNavigation />

      <div className="grid gap-6 lg:grid-cols-2">

          <SecurityCard />

          <AppearanceCard />

          <NotificationCard />

          <DeveloperIdentityCard />

      </div>

      <DangerZoneCard />

    </div>

  );

}