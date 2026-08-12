import ProfileHeader from "../../components/profile/ProfileHeader";
import PersonalInfoCard from "../../components/profile/PersonalInfoCard";
import ConnectedAccountsSection from "../../components/profile/ConnectedAccountsSection";
import AccountStatusCard from "../../components/profile/AccountStatusCard";
import DeveloperIdentityCard from "../../components/profile/DeveloperIdentityCard";
import PublicPortfolioCard from "../../components/profile/PublicPortfolioCard";
import ProfileCompletionCard from "../../components/profile/ProfileCompletionCard";

export default function ProfilePage() {
  return (
    <div className="mx-auto w-full max-w-7xl space-y-6">
      <ProfileHeader />

      <div className="grid items-start gap-6 lg:grid-cols-[1.15fr_0.85fr]">
        <PersonalInfoCard />

        <ConnectedAccountsSection />
      </div>


      <DeveloperIdentityCard />

      <ProfileCompletionCard />
      <AccountStatusCard />

      <PublicPortfolioCard />
    </div>
  );
}