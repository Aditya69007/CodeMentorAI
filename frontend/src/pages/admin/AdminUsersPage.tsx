import {
  useEffect,
  useState,
  type ReactNode,
} from "react";
import { toast } from "react-hot-toast";
import { useNavigate } from "react-router-dom";
import { getAdminSettings } from "../../services/adminSettingsService";
import {
  FiActivity,
  FiAlertTriangle,
  FiArrowRight,
  FiCheckCircle,
  FiCpu,
  FiSearch,
  FiTrash2,
  FiUser,
  FiUsers,
  FiX,
} from "react-icons/fi";

import {
  deleteAdminUser,
  getAdminUsers,
} from "../../services/adminService";

import type {
  AdminUserSummary,
} from "../../types/admin";


export default function AdminUsersPage() {

  const navigate = useNavigate();

  const [users, setUsers] =
    useState<AdminUserSummary[]>([]);

  const [loading, setLoading] =
    useState(true);

  const [error, setError] =
    useState("");

  const [search, setSearch] =
    useState("");

  const [userToDelete, setUserToDelete] =
    useState<AdminUserSummary | null>(null);

  const [deleting, setDeleting] =
    useState(false);

  const [deleteError, setDeleteError] =
    useState("");

  const [pageSize, setPageSize] = useState(10);
  const [currentPage, setCurrentPage] = useState(1);
  const [confirmBeforeDelete, setConfirmBeforeDelete] = useState(true);

  useEffect(() => {

    const loadUsers = async () => {

      try {

        setLoading(true);

        setError("");

        const data =
          await getAdminUsers();

        setUsers(data);

        const settings = await getAdminSettings();

        setPageSize(settings.defaultPageSize || 10);
        setConfirmBeforeDelete(settings.confirmBeforeDelete ?? true);

      } catch {

        setError(
          "Unable to load platform users."
        );

      } finally {

        setLoading(false);

      }
    };


    void loadUsers();

  }, []);


  const handleDeleteUser = async (userId?: number) => {
    const targetUserId = userId ?? userToDelete?.id;

    if (!targetUserId) {
      return;
    }

    try {
      setDeleting(true);
      setDeleteError("");

      await deleteAdminUser(targetUserId);

      setUsers((currentUsers) =>
        currentUsers.filter((user) => user.id !== targetUserId)
      );

      setUserToDelete(null);

      toast.success("User deleted successfully");
    } catch (error) {
      console.error("Failed to delete user", error);

      setDeleteError("Unable to permanently delete this user.");

      toast.error("Failed to delete user");
    } finally {
      setDeleting(false);
    }
  };


  const searchValue =
    search.toLowerCase().trim();


  const filteredUsers =
    users.filter((user) => {

      const fullName =
        `${user.firstName} ${user.lastName}`
          .toLowerCase();

      const email =
        user.email.toLowerCase();


      return (
        fullName.includes(searchValue) ||
        email.includes(searchValue)
      );

    });


const totalPages = Math.max(
  1,
  Math.ceil(filteredUsers.length / pageSize)
);

const paginatedUsers = filteredUsers.slice(
  (currentPage - 1) * pageSize,
  currentPage * pageSize
);


  const totalSubmissions =
    users.reduce(
      (total, user) =>
        total + user.totalSubmissions,
      0
    );


  const totalSolvedProblems =
    users.reduce(
      (total, user) =>
        total + user.solvedProblems,
      0
    );


  const totalAiAnalyses =
    users.reduce(
      (total, user) =>
        total + user.totalAiAnalyses,
      0
    );


  return (

    <>

      <div className="mx-auto max-w-[1500px]">

        {/* PAGE HEADER */}

        <div>

          <p className="text-sm font-semibold uppercase tracking-wider text-blue-500">
            User Management
          </p>


          <h1 className="mt-2 text-3xl font-bold tracking-tight text-slate-900 dark:text-white">
            Platform Users
          </h1>


          <p className="mt-2 text-slate-500 dark:text-slate-400">
            Monitor user activity, coding performance,
            AI usage, and learning progress.
          </p>

        </div>


        {/* PAGE ERROR */}

        {error && (

          <div className="mt-6 rounded-xl border border-red-500/20 bg-red-500/10 px-5 py-4 text-sm text-red-500">

            {error}

          </div>

        )}


        {/* SUMMARY */}

        <div className="mt-8 grid gap-5 sm:grid-cols-2 xl:grid-cols-4">

          <SummaryCard
            title="Total Users"
            value={loading ? "—" : users.length}
            description="Registered regular users"
            icon={<FiUsers />}
          />


          <SummaryCard
            title="Total Submissions"
            value={loading ? "—" : totalSubmissions}
            description="Submissions from all users"
            icon={<FiActivity />}
          />


          <SummaryCard
            title="Problems Solved"
            value={loading ? "—" : totalSolvedProblems}
            description="Total user problem completions"
            icon={<FiCheckCircle />}
          />


          <SummaryCard
            title="AI Analyses"
            value={loading ? "—" : totalAiAnalyses}
            description="AI mentor analyses generated"
            icon={<FiCpu />}
          />

        </div>


        {/* USERS */}

        <section className="mt-8 overflow-hidden rounded-2xl border border-slate-200 bg-white dark:border-slate-800 dark:bg-slate-900/40">

          {/* HEADER */}

          <div className="flex flex-col gap-5 border-b border-slate-200 px-6 py-5 dark:border-slate-800 lg:flex-row lg:items-center lg:justify-between">

            <div>

              <h2 className="text-lg font-semibold text-slate-900 dark:text-white">
                All Users
              </h2>


              <p className="mt-1 text-sm text-slate-500">
                Inspect user analytics or permanently
                remove platform accounts.
              </p>

            </div>


            <div className="relative w-full lg:w-80">

              <FiSearch className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-500" />


              <input
                type="text"
                value={search}
                onChange={(e) => {
                  setSearch(e.target.value);
                  setCurrentPage(1);
                }}
                placeholder="Search users..."
                className="w-full rounded-xl border border-slate-200 bg-slate-50 py-3 pl-11 pr-4 text-sm text-slate-900 outline-none transition focus:border-blue-500 dark:border-slate-800 dark:bg-slate-950 dark:text-white"
              />

            </div>

          </div>


          {/* LOADING */}

          {loading && (

            <div className="flex h-72 items-center justify-center">

              <div className="text-center">

                <div className="mx-auto h-8 w-8 animate-spin rounded-full border-2 border-slate-700 border-t-blue-500" />


                <p className="mt-4 text-sm text-slate-500">
                  Loading platform users...
                </p>

              </div>

            </div>

          )}


          {/* EMPTY */}

          {!loading &&
            filteredUsers.length === 0 && (

              <div className="flex h-72 items-center justify-center">

                <div className="text-center">

                  <FiUsers className="mx-auto text-4xl text-slate-600" />


                  <p className="mt-4 font-medium text-slate-900 dark:text-white">
                    No users found
                  </p>


                  <p className="mt-1 text-sm text-slate-500">
                    No users match your current search.
                  </p>

                </div>

              </div>

            )}


          {/* TABLE */}

          {!loading &&
            filteredUsers.length > 0 && (

              <div className="overflow-x-auto">

                <table className="w-full">

                  <thead>

                    <tr className="border-b border-slate-200 bg-slate-50/50 text-left dark:border-slate-800 dark:bg-slate-950/30">

                      <TableHeading>
                        User
                      </TableHeading>

                      <TableHeading>
                        Status
                      </TableHeading>

                      <TableHeading>
                        Submissions
                      </TableHeading>

                      <TableHeading>
                        Solved
                      </TableHeading>

                      <TableHeading>
                        Acceptance
                      </TableHeading>

                      <TableHeading>
                        AI Analyses
                      </TableHeading>

                      <TableHeading>
                        Mistakes
                      </TableHeading>

                      <TableHeading>
                        Actions
                      </TableHeading>

                    </tr>

                  </thead>


                  <tbody>

                    {paginatedUsers.map(
                      (user) => {

                        const fullName =
                          `${user.firstName} ${user.lastName}`
                            .trim();


                        const initials =
                          `${user.firstName?.charAt(0) ?? ""}${user.lastName?.charAt(0) ?? ""}`;


                        return (

                          <tr
                            key={user.id}
                            className="border-b border-slate-200 transition last:border-b-0 hover:bg-slate-50 dark:border-slate-800 dark:hover:bg-slate-800/30"
                          >

                            {/* USER */}

                            <td className="px-6 py-5">

                              <div className="flex items-center gap-4">

                                {user.profilePicture ? (

                                  <img
                                    src={user.profilePicture}
                                    alt={fullName}
                                    className="h-11 w-11 rounded-full object-cover"
                                  />

                                ) : (

                                  <div className="flex h-11 w-11 shrink-0 items-center justify-center rounded-full bg-blue-500/10 font-semibold text-blue-500">

                                    {initials || <FiUser />}

                                  </div>

                                )}


                                <div>

                                  <p className="font-medium text-slate-900 dark:text-white">
                                    {fullName}
                                  </p>


                                  <p className="mt-1 text-sm text-slate-500">
                                    {user.email}
                                  </p>

                                </div>

                              </div>

                            </td>


                            {/* STATUS */}

                            <td className="px-6 py-5">

                              {user.enabled ? (

                                <span className="inline-flex items-center gap-2 rounded-full bg-emerald-500/10 px-3 py-1 text-xs font-medium text-emerald-500">

                                  <span className="h-1.5 w-1.5 rounded-full bg-emerald-500" />

                                  Active

                                </span>

                              ) : (

                                <span className="inline-flex items-center gap-2 rounded-full bg-red-500/10 px-3 py-1 text-xs font-medium text-red-500">

                                  <span className="h-1.5 w-1.5 rounded-full bg-red-500" />

                                  Disabled

                                </span>

                              )}

                            </td>


                            <MetricCell
                              value={user.totalSubmissions}
                            />


                            <MetricCell
                              value={user.solvedProblems}
                            />


                            {/* ACCEPTANCE */}

                            <td className="px-6 py-5">

                              <div className="min-w-28">

                                <span className="text-sm font-medium text-slate-900 dark:text-white">

                                  {user.acceptanceRate.toFixed(2)}%

                                </span>


                                <div className="mt-2 h-1.5 overflow-hidden rounded-full bg-slate-200 dark:bg-slate-800">

                                  <div
                                    className="h-full rounded-full bg-blue-500"
                                    style={{
                                      width:
                                        `${Math.min(
                                          user.acceptanceRate,
                                          100
                                        )}%`,
                                    }}
                                  />

                                </div>

                              </div>

                            </td>


                            <MetricCell
                              value={user.totalAiAnalyses}
                            />


                            {/* MISTAKES */}

                            <td className="px-6 py-5">

                              <div className="flex items-center gap-2">

                                <FiAlertTriangle className="text-amber-500" />

                                <span className="font-medium text-slate-900 dark:text-white">

                                  {user.totalMistakes}

                                </span>

                              </div>

                            </td>


                            {/* ACTIONS */}

                            <td className="px-6 py-5">

                              <div className="flex items-center gap-2">

                                <button
                                  type="button"
                                  onClick={() =>
                                    navigate(
                                      `/admin/users/${user.id}`
                                    )
                                  }
                                  className="flex items-center gap-2 rounded-lg border border-slate-200 px-3 py-2 text-sm font-medium text-slate-600 transition hover:border-blue-500/30 hover:bg-blue-500/10 hover:text-blue-500 dark:border-slate-800 dark:text-slate-400"
                                >

                                  Analytics

                                  <FiArrowRight />

                                </button>


                                <button
                                  type="button"
                                  onClick={() => {
                                    setDeleteError("");

                                    if (confirmBeforeDelete) {
                                      setUserToDelete(user);
                                      return;
                                    }

                                    void handleDeleteUser(user.id);
                                  }}
                                  title="Delete user"
                                  className="flex h-10 w-10 items-center justify-center rounded-lg border border-slate-200 text-slate-500 transition hover:border-red-500/30 hover:bg-red-500/10 hover:text-red-500 dark:border-slate-800"
                                >

                                  <FiTrash2 />

                                </button>

                              </div>

                            </td>

                          </tr>

                        );

                      }
                    )}

                  </tbody>

                </table>

                <div className="flex flex-col gap-3 border-t border-slate-200 px-6 py-4 sm:flex-row sm:items-center sm:justify-between dark:border-slate-800">

                  <p className="text-sm text-slate-500">
                    Showing{" "}
                    {filteredUsers.length === 0
                      ? 0
                      : (currentPage - 1) * pageSize + 1}
                    {" "}–{" "}
                    {Math.min(currentPage * pageSize, filteredUsers.length)}
                    {" "}of {filteredUsers.length} users
                  </p>

                  <div className="flex items-center gap-2">

                    <button
                      type="button"
                      disabled={currentPage === 1}
                      onClick={() =>
                        setCurrentPage((page) => Math.max(1, page - 1))
                      }
                      className="rounded-lg border border-slate-200 px-3 py-2 text-sm disabled:opacity-40 dark:border-slate-800"
                    >
                      Previous
                    </button>

                    <span className="px-3 text-sm font-medium">
                      {currentPage} / {totalPages}
                    </span>

                    <button
                      type="button"
                      disabled={currentPage === totalPages}
                      onClick={() =>
                        setCurrentPage((page) =>
                          Math.min(totalPages, page + 1)
                        )
                      }
                      className="rounded-lg border border-slate-200 px-3 py-2 text-sm disabled:opacity-40 dark:border-slate-800"
                    >
                      Next
                    </button>

                  </div>

                </div>

              </div>

            )}

        </section>

      </div>


      {/* DELETE MODAL */}

      {userToDelete && (

        <div className="fixed inset-0 z-[100] flex items-center justify-center bg-black/60 px-4 backdrop-blur-sm">

          <div className="w-full max-w-md rounded-2xl border border-slate-200 bg-white shadow-2xl dark:border-slate-800 dark:bg-slate-950">

            {/* MODAL HEADER */}

            <div className="flex items-center justify-between border-b border-slate-200 px-6 py-5 dark:border-slate-800">

              <div className="flex items-center gap-3">

                <div className="flex h-10 w-10 items-center justify-center rounded-xl bg-red-500/10 text-red-500">

                  <FiTrash2 />

                </div>


                <h2 className="text-lg font-semibold text-slate-900 dark:text-white">

                  Delete User

                </h2>

              </div>


              <button
                type="button"
                disabled={deleting}
                onClick={() =>
                  setUserToDelete(null)
                }
                className="flex h-9 w-9 items-center justify-center rounded-lg text-slate-500 transition hover:bg-slate-100 hover:text-slate-900 disabled:cursor-not-allowed dark:hover:bg-slate-900 dark:hover:text-white"
              >

                <FiX />

              </button>

            </div>


            {/* MODAL BODY */}

            <div className="px-6 py-6">

              <div className="rounded-xl border border-red-500/20 bg-red-500/10 p-4">

                <div className="flex gap-3">

                  <FiAlertTriangle className="mt-0.5 shrink-0 text-xl text-red-500" />


                  <div>

                    <p className="font-medium text-red-500">

                      Permanent deletion

                    </p>


                    <p className="mt-1 text-sm leading-6 text-slate-600 dark:text-slate-400">

                      This action cannot be undone.
                      All submissions, AI analyses,
                      mistake history, and account
                      data belonging to this user
                      will be permanently removed.

                    </p>

                  </div>

                </div>

              </div>


              <p className="mt-5 text-sm text-slate-600 dark:text-slate-400">

                Are you sure you want to delete

                {" "}

                <span className="font-semibold text-slate-900 dark:text-white">

                  {userToDelete.firstName}{" "}
                  {userToDelete.lastName}

                </span>

                ?

              </p>


              <p className="mt-1 text-sm text-slate-500">

                {userToDelete.email}

              </p>


              {deleteError && (

                <div className="mt-5 rounded-lg bg-red-500/10 px-4 py-3 text-sm text-red-500">

                  {deleteError}

                </div>

              )}

            </div>


            {/* MODAL FOOTER */}

            <div className="flex justify-end gap-3 border-t border-slate-200 px-6 py-5 dark:border-slate-800">

              <button
                type="button"
                disabled={deleting}
                onClick={() =>
                  setUserToDelete(null)
                }
                className="rounded-lg border border-slate-200 px-4 py-2.5 text-sm font-medium text-slate-600 transition hover:bg-slate-100 disabled:cursor-not-allowed disabled:opacity-50 dark:border-slate-800 dark:text-slate-400 dark:hover:bg-slate-900"
              >

                Cancel

              </button>


              <button
                type="button"
                disabled={deleting}
                onClick={() =>
                  void handleDeleteUser()
                }
                className="flex min-w-32 items-center justify-center gap-2 rounded-lg bg-red-600 px-4 py-2.5 text-sm font-semibold text-white transition hover:bg-red-500 disabled:cursor-not-allowed disabled:opacity-50"
              >

                <FiTrash2 />


                {deleting
                  ? "Deleting..."
                  : "Delete User"}

              </button>

            </div>

          </div>

        </div>

      )}

    </>

  );
}


interface SummaryCardProps {

  title: string;

  value: number | string;

  description: string;

  icon: ReactNode;

}


function SummaryCard({

  title,

  value,

  description,

  icon,

}: SummaryCardProps) {

  return (

    <div className="rounded-2xl border border-slate-200 bg-white p-5 dark:border-slate-800 dark:bg-slate-900/40">

      <div className="flex items-start justify-between">

        <div>

          <p className="text-sm text-slate-500 dark:text-slate-400">
            {title}
          </p>


          <p className="mt-3 text-3xl font-bold text-slate-900 dark:text-white">
            {value}
          </p>

        </div>


        <div className="flex h-11 w-11 items-center justify-center rounded-xl bg-blue-500/10 text-xl text-blue-500">

          {icon}

        </div>

      </div>


      <p className="mt-4 text-xs text-slate-500">
        {description}
      </p>

    </div>

  );
}


interface TableHeadingProps {

  children: ReactNode;

}


function TableHeading({

  children,

}: TableHeadingProps) {

  return (

    <th className="whitespace-nowrap px-6 py-4 text-xs font-semibold uppercase tracking-wider text-slate-500">

      {children}

    </th>

  );
}


interface MetricCellProps {

  value: number;

}


function MetricCell({

  value,

}: MetricCellProps) {

  return (

    <td className="px-6 py-5 font-medium text-slate-900 dark:text-white">

      {value}

    </td>

  );
}