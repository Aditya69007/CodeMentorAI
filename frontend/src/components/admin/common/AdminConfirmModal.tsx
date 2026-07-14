import {
  type ReactNode,
  useEffect,
} from "react";

import {
  FiAlertTriangle,
  FiX,
} from "react-icons/fi";


interface AdminConfirmModalProps {
  open: boolean;

  title: string;

  description: string;

  confirmLabel?: string;

  cancelLabel?: string;

  loading?: boolean;

  danger?: boolean;

  icon?: ReactNode;

  onConfirm: () => void | Promise<void>;

  onClose: () => void;
}


export default function AdminConfirmModal({
  open,
  title,
  description,
  confirmLabel = "Confirm",
  cancelLabel = "Cancel",
  loading = false,
  danger = false,
  icon,
  onConfirm,
  onClose,
}: AdminConfirmModalProps) {


  useEffect(() => {

    if (!open) {
      return;
    }


    const handleKeyDown = (
      event: KeyboardEvent
    ) => {

      if (
        event.key === "Escape" &&
        !loading
      ) {

        onClose();

      }

    };


    document.addEventListener(
      "keydown",
      handleKeyDown
    );


    const previousOverflow =
      document.body.style.overflow;


    document.body.style.overflow =
      "hidden";


    return () => {

      document.removeEventListener(
        "keydown",
        handleKeyDown
      );


      document.body.style.overflow =
        previousOverflow;

    };

  }, [
    open,
    loading,
    onClose,
  ]);


  if (!open) {
    return null;
  }


  const handleBackdropClick = () => {

    if (!loading) {
      onClose();
    }

  };


  return (

    <div
      className="
        fixed
        inset-0
        z-[100]
        flex
        items-center
        justify-center
        bg-black/60
        p-4
        backdrop-blur-sm
      "
      onMouseDown={
        handleBackdropClick
      }
    >


      <div

        role="dialog"

        aria-modal="true"

        aria-labelledby="admin-confirm-modal-title"

        onMouseDown={(event) =>
          event.stopPropagation()
        }

        className="
          app-surface
          app-border
          w-full
          max-w-md
          overflow-hidden
          rounded-2xl
          border
          shadow-2xl
        "
      >


        {/* HEADER */}

        <div className="app-border flex items-start justify-between gap-4 border-b px-6 py-5">


          <div className="flex items-start gap-4">


            <div
              className={`
                flex
                h-11
                w-11
                shrink-0
                items-center
                justify-center
                rounded-xl
                text-xl
                ${
                  danger
                    ? "bg-red-500/10 text-red-500"
                    : "bg-blue-500/10 text-blue-500"
                }
              `}
            >

              {icon ?? (
                <FiAlertTriangle />
              )}

            </div>


            <div>

              <h2
                id="admin-confirm-modal-title"
                className="text-lg font-semibold"
              >

                {title}

              </h2>


              <p className="app-text-secondary mt-2 text-sm leading-6">

                {description}

              </p>

            </div>

          </div>


          <button

            type="button"

            onClick={onClose}

            disabled={loading}

            title="Close"

            aria-label="Close confirmation modal"

            className="
              app-hover
              app-text-muted
              flex
              h-9
              w-9
              shrink-0
              items-center
              justify-center
              rounded-lg
              transition
              hover:text-current
              disabled:cursor-not-allowed
              disabled:opacity-50
            "
          >

            <FiX />

          </button>

        </div>



        {/* ACTIONS */}

        <div className="flex items-center justify-end gap-3 px-6 py-5">


          <button

            type="button"

            onClick={onClose}

            disabled={loading}

            className="
              app-border
              app-hover
              app-text-secondary
              rounded-lg
              border
              px-4
              py-2.5
              text-sm
              font-semibold
              transition
              disabled:cursor-not-allowed
              disabled:opacity-50
            "
          >

            {cancelLabel}

          </button>


          <button

            type="button"

            onClick={() =>
              void onConfirm()
            }

            disabled={loading}

            className={`
              inline-flex
              min-w-32
              items-center
              justify-center
              rounded-lg
              px-4
              py-2.5
              text-sm
              font-semibold
              text-white
              transition
              disabled:cursor-not-allowed
              disabled:opacity-60
              ${
                danger
                  ? "bg-red-600 hover:bg-red-500"
                  : "bg-blue-600 hover:bg-blue-500"
              }
            `}
          >

            {loading
              ? "Deleting..."
              : confirmLabel}

          </button>

        </div>


      </div>

    </div>

  );
}