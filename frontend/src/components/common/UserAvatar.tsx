import type { AuthUser } from "../../types/auth";

interface UserAvatarProps {
  user: AuthUser;
  size?: "sm" | "md" | "lg" | "xl";
}

const sizes = {
  sm: "h-10 w-10 text-sm",
  md: "h-14 w-14 text-lg",
  lg: "h-20 w-20 text-2xl",
  xl: "h-28 w-28 text-4xl",
};

export default function UserAvatar({
  user,
  size = "md",
}: UserAvatarProps) {

  const initials = (
    (user.firstName?.charAt(0) ?? "") +
    (user.lastName?.charAt(0) ?? "")
  ).toUpperCase();

  return (
    <div
      className={`
        ${sizes[size]}
        relative
        flex
        shrink-0
        items-center
        justify-center
        overflow-hidden
        rounded-full
        bg-gradient-to-br
        from-blue-600
        via-blue-500
        to-cyan-500
        font-bold
        text-white
        shadow-xl
        ring-2
        ring-blue-500/20
      `}
    >
      {user.profilePicture ? (
        <img
          src={user.profilePicture}
          alt={`${user.firstName} ${user.lastName}`}
          className="h-full w-full object-cover"
        />
      ) : (
        <span className="select-none leading-none">
          {initials}
        </span>
      )}
    </div>
  );
}