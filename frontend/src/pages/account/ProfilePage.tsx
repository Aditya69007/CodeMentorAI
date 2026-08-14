import ProfileHeader from "../../components/profile/ProfileHeader";
import AccountStatusCard from "../../components/profile/AccountStatusCard";
import DeveloperIdentityCard from "../../components/profile/DeveloperIdentityCard";
import PublicPortfolioCard from "../../components/profile/PublicPortfolioCard";
import ProfileCompletionCard from "../../components/profile/ProfileCompletionCard";

export default function ProfilePage() {
  return (
    <div className="mx-auto w-full max-w-7xl space-y-6">
      <ProfileHeader />


      <DeveloperIdentityCard />

      <ProfileCompletionCard />
      <AccountStatusCard />

      <PublicPortfolioCard />
    </div>
  );
}