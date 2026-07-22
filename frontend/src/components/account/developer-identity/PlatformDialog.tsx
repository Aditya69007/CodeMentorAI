import { FiX } from "react-icons/fi";

type Props = {
  open: boolean;
  title: string;
  onClose: () => void;
  children: React.ReactNode;
};

export default function PlatformDialog({
  open,
  title,
  onClose,
  children,
}: Props) {

  if (!open) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/70 backdrop-blur-sm p-6">

      <div className="relative w-full max-w-5xl rounded-3xl border border-white/10 bg-[#151922] shadow-2xl">

        {/* Header */}

        <div className="flex items-center justify-between border-b border-white/10 px-8 py-6">

          <div>

            <h2 className="text-3xl font-bold">

              {title}

            </h2>

            <p className="mt-2 app-text-secondary">

              Manage your connection and developer profile.

            </p>

          </div>

          <button
            onClick={onClose}
            className="rounded-xl border border-white/10 p-3 hover:border-red-500"
          >

            <FiX />

          </button>

        </div>

        {/* Content */}

        <div className="max-h-[75vh] overflow-y-auto p-8">

          {children}

        </div>

      </div>

    </div>
  );
}