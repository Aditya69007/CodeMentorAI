import { useState } from "react";
import { useAuth } from "../../hooks/useAuth";
import ProfileHero from "../../components/account/ProfileHero";
import PersonalInformationCard from "../../components/account/PersonalInformationCard";
import DeveloperInformationCard from "../../components/account/DeveloperInformationCard";
import StatisticsSection from "../../components/account/StatisticsSection";
import EditProfileModal from "../../components/account/EditProfileModal";

import { updateProfile } from "../../services/userService";


export default function ProfilePage() {
  
  const handleSave = async () => {
    
    try {
      
      await updateProfile({
        firstName,
        lastName,
      });
      
      await refreshUser();

      setIsEditOpen(false);

    } catch (error) {

      console.error(error);
      
    }
    
  };
  
  const { user, refreshUser } = useAuth();
  const [isEditOpen, setIsEditOpen] = useState(false);

  const [firstName, setFirstName] = useState(user?.firstName ?? "");
  const [lastName, setLastName] = useState(user?.lastName ?? "");

  if (!user) {
    return null;
  }

  const fullName = `${user.firstName} ${user.lastName}`;

  const initials =
    `${user.firstName.charAt(0)}${user.lastName.charAt(0)}`;


  return (

    <div className="space-y-8">

      <div>

        <h1 className="text-3xl font-bold">

          My Profile

        </h1>

        <p className="app-text-secondary mt-2">

          Manage your account information and developer profile.

        </p>

      </div>

      <ProfileHero
        user={user}
        fullName={fullName}
        initials={initials}
        onEdit={() => setIsEditOpen(true)}
      />

      <div className="grid gap-6 lg:grid-cols-2">

        <PersonalInformationCard
            user={user}
        />
        
        <DeveloperInformationCard />

      </div>

      <EditProfileModal
        open={isEditOpen}
        user={user}
        firstName={firstName}
        lastName={lastName}
        onFirstNameChange={setFirstName}
        onLastNameChange={setLastName}
        onCancel={() => setIsEditOpen(false)}
        onSave={handleSave}
      />

      <StatisticsSection />



    </div>

  );

}
