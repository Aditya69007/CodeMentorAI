import { useEffect, useState, type ReactNode } from "react";
import {
FiUsers,
FiShield,
FiUserPlus,
FiTrash2,
FiActivity,
FiX,
FiSearch,
FiArrowUp,
FiArrowDown,
} from "react-icons/fi";
import { toast } from "sonner";

import {
  getSuperAdmins,
  deleteSuperAdmin,
  createSuperAdmin,
  type AdminSummary,
} from "../../services/superAdminService";

export default function SuperAdminCenterPage() {
    const [admins, setAdmins] = useState<AdminSummary[]>([]);
    const [loading, setLoading] = useState(true);

    const [deleteTarget, setDeleteTarget] =
    useState<AdminSummary | null>(null);

    const [deletePassword, setDeletePassword] =
    useState("");

    const [deleteConfirmation, setDeleteConfirmation] =
    useState("");

    const [deleting, setDeleting] =
    useState(false);

    const [showCreateModal, setShowCreateModal] = useState(false);

    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    const [creating, setCreating] = useState(false);
    const [searchQuery, setSearchQuery] = useState("");
    const [sortOrder, setSortOrder] = useState<"asc" | "desc">("asc");

  
  async function loadAdmins() {
    try {
      setLoading(true);

      const data = await getSuperAdmins();

      setAdmins(data);
    } catch (error) {
      console.error("Failed to load administrators", error);
      toast.error("Failed to load administrators");
    } finally {
      setLoading(false);
    }
  }


    useEffect(() => {
    let mounted = true;

    async function load() {
        try {
        const data = await getSuperAdmins();

        if (mounted) {
            setAdmins(data);
            setLoading(false);
        }
        } catch (error) {
        console.error("Failed to load administrators", error);

        if (mounted) {
            toast.error("Failed to load administrators");
            setLoading(false);
        }
        }
    }

    void load();

    return () => {
        mounted = false;
    };
    }, []);

    async function handleDeleteAdmin() {

    if (!deleteTarget) {
        return;
    }

    if (!deletePassword.trim()) {
        toast.error("Enter your Super Admin password");
        return;
    }

    if (deleteConfirmation !== "DELETE") {
        toast.error('Type "DELETE" to confirm');
        return;
    }

    try {

        setDeleting(true);

        await deleteSuperAdmin(
        deleteTarget.id,
        deletePassword
        );

        setAdmins((current) =>
        current.filter(
            (admin) => admin.id !== deleteTarget.id
        )
        );

        toast.success(
        "Administrator deleted successfully"
        );

        setDeleteTarget(null);
        setDeletePassword("");
        setDeleteConfirmation("");

    } catch (error) {

        console.error(
        "Failed to delete administrator",
        error
        );

        toast.error(
        "Failed to delete administrator"
        );

    } finally {

        setDeleting(false);
    }
    }

    async function handleCreateAdmin() {
    if (!firstName.trim() || !lastName.trim()) {
        toast.error("First name and last name are required");
        return;
    }

    if (!email.trim()) {
        toast.error("Email is required");
        return;
    }

    if (password.length < 8) {
        toast.error("Password must be at least 8 characters");
        return;
    }

    if (password !== confirmPassword) {
        toast.error("Passwords do not match");
        return;
    }

    try {
        setCreating(true);

        await createSuperAdmin({
        firstName: firstName.trim(),
        lastName: lastName.trim(),
        username: username.trim() || undefined,
        email: email.trim(),
        password,
        });

        toast.success("Administrator created successfully");

        setShowCreateModal(false);

        setFirstName("");
        setLastName("");
        setUsername("");
        setEmail("");
        setPassword("");
        setConfirmPassword("");

        await loadAdmins();
    } catch (error) {
        console.error("Failed to create administrator", error);
        toast.error("Failed to create administrator");
    } finally {
        setCreating(false);
    }
    }


    const activeAdmins =
    admins.filter((admin) => admin.enabled).length;

    const filteredAdmins = admins
    .filter((admin) => {
        const query = searchQuery.toLowerCase().trim();

        if (!query) {
        return true;
        }

        return (
        admin.firstName.toLowerCase().includes(query) ||
        admin.lastName.toLowerCase().includes(query) ||
        admin.email.toLowerCase().includes(query) ||
        String(admin.id).includes(query)
        );
    })
    .sort((a, b) =>
        sortOrder === "asc"
        ? a.id - b.id
        : b.id - a.id
    );

  return (
    <div className="mx-auto w-full max-w-7xl space-y-6">

      {/* HEADER */}

      <section className="rounded-3xl border border-red-500/20 bg-red-500/5 p-8">

        <div className="flex flex-col gap-6 lg:flex-row lg:items-center lg:justify-between">

          <div className="flex items-start gap-4">

            <div className="flex h-14 w-14 shrink-0 items-center justify-center rounded-2xl bg-red-500/10">
              <FiShield className="text-2xl text-red-400" />
            </div>

            <div>
              <p className="text-sm font-semibold uppercase tracking-wider text-red-400">
                Restricted Control Center
              </p>

              <h1 className="mt-1 text-3xl font-bold">
                Super Admin Center
              </h1>

              <p className="mt-2 max-w-2xl text-sm leading-6 app-text-secondary">
                Manage administrators and critical platform operations.
                These controls are available only to the Super Admin.
              </p>
            </div>

          </div>

          <div className="rounded-2xl border border-red-500/20 bg-red-500/5 px-5 py-4">
            <p className="text-xs uppercase tracking-wider app-text-muted">
              Access Level
            </p>

            <p className="mt-1 font-bold text-red-400">
              SUPER_ADMIN
            </p>
          </div>

        </div>

      </section>


      {/* OVERVIEW */}

      <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">

        <StatCard
          icon={<FiUsers />}
          label="Total Administrators"
          value={admins.length}
        />

        <StatCard
          icon={<FiActivity />}
          label="Active Administrators"
          value={activeAdmins}
        />

        <StatCard
          icon={<FiShield />}
          label="Access Level"
          value="SUPER_ADMIN"
        />

      </div>


      {/* ADMINISTRATORS */}

      <section className="app-surface app-border rounded-3xl p-6 sm:p-7">

        <div className="mb-6 flex flex-col gap-5">

        <div className="flex flex-col gap-5 lg:flex-row lg:items-center lg:justify-between">

            <div>
            <h2 className="text-xl font-bold">
                Administrators
            </h2>

            <p className="mt-1 text-sm app-text-secondary">
                Create and manage platform administrator accounts.
            </p>
            </div>

            <button
            type="button"
            className="inline-flex items-center justify-center gap-2 rounded-xl bg-gradient-to-r from-blue-600 to-indigo-600 px-5 py-3 font-semibold text-white transition hover:-translate-y-0.5"
            onClick={() => setShowCreateModal(true)}
            >
            <FiUserPlus />
            Create Admin
            </button>

        </div>

        {/* SEARCH + SORT */}

        <div className="flex flex-col gap-3 lg:flex-row lg:items-center">

        {/* SEARCH */}

        <div className="flex-1">

        <div className="flex h-12 items-center rounded-xl border app-border app-surface-secondary px-4">

            <FiSearch className="mr-3 shrink-0 text-lg app-text-muted" />

            <input
            type="text"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            placeholder="Search administrators by name, email or ID..."
            className="h-full min-w-0 flex-1 bg-transparent text-sm outline-none"
            />

        </div>

        </div>


        {/* SORT */}

        <div className="flex h-12 shrink-0 items-center rounded-xl border app-border app-surface-secondary p-1">

            <button
            type="button"
            onClick={() => setSortOrder("asc")}
            className={`inline-flex h-10 items-center gap-2 rounded-lg px-4 text-sm font-semibold transition ${
                sortOrder === "asc"
                ? "bg-blue-600 text-white shadow-sm"
                : "app-text-secondary hover:bg-slate-500/10"
            }`}
            >
            <FiArrowUp className="text-sm" />
            <span>ID</span>
            <span className="hidden sm:inline">
                Low → High
            </span>
            </button>


            <button
            type="button"
            onClick={() => setSortOrder("desc")}
            className={`inline-flex h-10 items-center gap-2 rounded-lg px-4 text-sm font-semibold transition ${
                sortOrder === "desc"
                ? "bg-blue-600 text-white shadow-sm"
                : "app-text-secondary hover:bg-slate-500/10"
            }`}
            >
            <FiArrowDown className="text-sm" />
            <span>ID</span>
            <span className="hidden sm:inline">
                High → Low
            </span>
            </button>

        </div>

        </div>

        </div>


        {loading ? (

          <div className="rounded-2xl border app-border app-surface-secondary p-8 text-center app-text-secondary">
            Loading administrators...
          </div>

        ) : admins.length === 0 ? (

          <div className="rounded-2xl border app-border app-surface-secondary p-8 text-center app-text-secondary">
            No administrators found.
          </div>

        ) : filteredAdmins.length === 0 ? (

        <div className="rounded-2xl border app-border app-surface-secondary p-8 text-center app-text-secondary">
            No administrators match your search.
        </div>

        ) : (

            <div className="max-h-[520px] space-y-3 overflow-y-auto pr-2 scrollbar-thin">


                {filteredAdmins.map((admin) => (

              <div
                key={admin.id}
                className="group flex flex-col gap-4 rounded-2xl border app-border app-surface-secondary p-5 transition-all duration-200 hover:border-blue-500/30 hover:shadow-lg hover:shadow-black/10 lg:flex-row lg:items-center lg:justify-between"
              >

                <div className="flex min-w-0 items-center gap-4">

                  <div className="flex h-11 w-11 shrink-0 items-center justify-center rounded-xl bg-blue-500/10 font-bold text-blue-500">
                    {admin.firstName.charAt(0)}
                  </div>

                  <div className="min-w-0">

                    <p className="font-semibold">
                      {admin.firstName} {admin.lastName}
                    </p>

                    <p className="truncate text-sm app-text-secondary">
                      {admin.email}
                    </p>

                    <p className="mt-1 text-xs app-text-muted">
                      ID: {admin.id}
                    </p>

                  </div>

                </div>


                <div className="flex items-center gap-3">

                  <span
                    className={`rounded-full px-3 py-1 text-xs font-semibold ${
                      admin.enabled
                        ? "bg-emerald-500/10 text-emerald-400"
                        : "bg-slate-500/10 app-text-muted"
                    }`}
                  >
                    {admin.enabled ? "Active" : "Disabled"}
                  </span>

                  {admin.enabled && (
                    <button
                      type="button"
                        onClick={() => {
                        setDeleteTarget(admin);
                        setDeletePassword("");
                        setDeleteConfirmation("");
                        }}
                      className="inline-flex items-center gap-2 rounded-xl border border-red-500/30 px-4 py-2 text-sm font-semibold text-red-400 transition hover:bg-red-500/10"
                    >
                      <FiTrash2 />
                      Delete
                    </button>
                  )}

                </div>

              </div>

            ))}

          </div>

        )}

      </section>


      {/* PLATFORM CONTROL PLACEHOLDER */}

      <section className="rounded-3xl border border-amber-500/20 bg-amber-500/5 p-6">

        <div className="flex items-start gap-4">

          <div className="flex h-11 w-11 shrink-0 items-center justify-center rounded-xl bg-amber-500/10">
            <FiShield className="text-xl text-amber-400" />
          </div>

          <div>
            <h2 className="font-bold">
              Platform Control
            </h2>

            <p className="mt-1 text-sm leading-6 app-text-secondary">
              Additional platform-wide Super Admin controls will be
              added here after administrator management is complete.
            </p>
          </div>

        </div>

      </section>

        {showCreateModal && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 p-4 backdrop-blur-sm">

            <div className="app-surface app-border w-full max-w-2xl rounded-3xl shadow-2xl">

            {/* MODAL HEADER */}

            <div className="flex items-center justify-between border-b app-border p-6">

                <div className="flex items-center gap-4">

                <div className="flex h-11 w-11 items-center justify-center rounded-xl bg-blue-500/10">
                    <FiUserPlus className="text-xl text-blue-500" />
                </div>

                <div>
                    <h2 className="text-xl font-bold">
                    Create Administrator
                    </h2>

                    <p className="mt-1 text-sm app-text-secondary">
                    Create a new administrator account.
                    </p>
                </div>

                </div>

                <button
                type="button"
                onClick={() => setShowCreateModal(false)}
                className="rounded-xl p-2 app-text-secondary transition hover:bg-slate-500/10"
                >
                <FiX className="text-xl" />
                </button>

            </div>


            {/* FORM */}

            <div className="space-y-5 p-6">

                <div className="grid gap-5 sm:grid-cols-2">

                <div>
                    <label className="mb-2 block text-sm font-semibold">
                    First Name
                    </label>

                    <input
                    value={firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                    placeholder="First name"
                    className="app-input w-full rounded-xl border px-4 py-3 outline-none focus:border-blue-500"
                    />
                </div>

                <div>
                    <label className="mb-2 block text-sm font-semibold">
                    Last Name
                    </label>

                    <input
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                    placeholder="Last name"
                    className="app-input w-full rounded-xl border px-4 py-3 outline-none focus:border-blue-500"
                    />
                </div>

                </div>


                <div>
                <label className="mb-2 block text-sm font-semibold">
                    Username
                </label>

                <input
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    placeholder="admin_username"
                    className="app-input w-full rounded-xl border px-4 py-3 outline-none focus:border-blue-500"
                />
                </div>


                <div>
                <label className="mb-2 block text-sm font-semibold">
                    Email
                </label>

                <input
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    placeholder="admin@example.com"
                    className="app-input w-full rounded-xl border px-4 py-3 outline-none focus:border-blue-500"
                />
                </div>


                <div className="grid gap-5 sm:grid-cols-2">

                <div>
                    <label className="mb-2 block text-sm font-semibold">
                    Password
                    </label>

                    <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="Minimum 8 characters"
                    className="app-input w-full rounded-xl border px-4 py-3 outline-none focus:border-blue-500"
                    />
                </div>

                <div>
                    <label className="mb-2 block text-sm font-semibold">
                    Confirm Password
                    </label>

                    <input
                    type="password"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    placeholder="Confirm password"
                    className="app-input w-full rounded-xl border px-4 py-3 outline-none focus:border-blue-500"
                    />
                </div>

                </div>

            </div>


            {/* FOOTER */}

            <div className="flex justify-end gap-3 border-t app-border p-6">

                <button
                type="button"
                onClick={() => setShowCreateModal(false)}
                disabled={creating}
                className="rounded-xl border app-border px-5 py-3 font-semibold transition hover:bg-slate-500/10"
                >
                Cancel
                </button>

                <button
                type="button"
                onClick={handleCreateAdmin}
                disabled={creating}
                className="inline-flex items-center gap-2 rounded-xl bg-gradient-to-r from-blue-600 to-indigo-600 px-5 py-3 font-semibold text-white shadow-lg shadow-blue-500/20 transition hover:-translate-y-0.5 disabled:cursor-not-allowed disabled:opacity-60"
                >
                <FiUserPlus />

                {creating ? "Creating..." : "Create Admin"}
                </button>

            </div>

            </div>

        </div>
        )}


        {deleteTarget && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/70 p-4 backdrop-blur-sm">

            <div className="w-full max-w-md rounded-3xl border app-border app-surface p-6 shadow-2xl">

            <div className="mb-6 flex items-start gap-4">

                <div className="flex h-12 w-12 shrink-0 items-center justify-center rounded-xl bg-red-500/10">
                <FiTrash2 className="text-xl text-red-400" />
                </div>

                <div>
                <h2 className="text-xl font-bold">
                    Delete Administrator
                </h2>

                <p className="mt-1 text-sm app-text-secondary">
                    This action cannot be undone.
                </p>
                </div>

            </div>

            <div className="mb-5 rounded-2xl border border-red-500/20 bg-red-500/5 p-4">

                <p className="font-semibold">
                {deleteTarget.firstName} {deleteTarget.lastName}
                </p>

                <p className="mt-1 text-sm app-text-secondary">
                {deleteTarget.email}
                </p>

                <p className="mt-3 text-sm leading-6 text-red-400">
                You are permanently deleting this administrator
                account.
                </p>

            </div>

            {/* PASSWORD */}

            <div className="mb-5">

                <label className="mb-2 block text-sm font-semibold">
                Super Admin Password
                </label>

                <input
                type="password"
                value={deletePassword}
                onChange={(e) =>
                    setDeletePassword(e.target.value)
                }
                placeholder="Enter your password"
                className="app-input w-full rounded-xl border px-4 py-3 outline-none focus:border-red-500 focus:ring-2 focus:ring-red-500/20"
                />

            </div>

            {/* CONFIRMATION */}

            <div className="mb-6">

                <label className="mb-2 block text-sm font-semibold">
                Type <span className="text-red-400">DELETE</span> to confirm
                </label>

                <input
                type="text"
                value={deleteConfirmation}
                onChange={(e) =>
                    setDeleteConfirmation(e.target.value)
                }
                placeholder="DELETE"
                className="app-input w-full rounded-xl border px-4 py-3 outline-none focus:border-red-500 focus:ring-2 focus:ring-red-500/20"
                />

            </div>

            {/* ACTIONS */}

            <div className="flex justify-end gap-3">

                <button
                type="button"
                disabled={deleting}
                onClick={() => {
                    setDeleteTarget(null);
                    setDeletePassword("");
                    setDeleteConfirmation("");
                }}
                className="rounded-xl border app-border px-5 py-3 font-semibold transition hover:bg-slate-500/10 disabled:opacity-50"
                >
                Cancel
                </button>

                <button
                type="button"
                disabled={
                    deleting ||
                    !deletePassword ||
                    deleteConfirmation !== "DELETE"
                }
                onClick={() => void handleDeleteAdmin()}
                className="inline-flex items-center gap-2 rounded-xl bg-red-600 px-5 py-3 font-semibold text-white transition hover:bg-red-700 disabled:cursor-not-allowed disabled:opacity-50"
                >

                <FiTrash2 />

                {deleting
                    ? "Deleting..."
                    : "Delete Administrator"}

                </button>

            </div>

            </div>

        </div>
        )}

    </div>
  );
}


function StatCard({
  icon,
  label,
  value,
}: {
  icon: ReactNode;
  label: string;
  value: string | number;
}) {
  return (
    <div className="app-surface app-border rounded-2xl p-5">

      <div className="flex items-center gap-3">

        <div className="flex h-10 w-10 items-center justify-center rounded-xl bg-blue-500/10 text-blue-500">
          {icon}
        </div>

        <div>
          <p className="text-xs font-semibold uppercase tracking-wider app-text-muted">
            {label}
          </p>

          <p className="mt-1 text-xl font-bold">
            {value}
          </p>
        </div>

      </div>

    </div>
  );
}