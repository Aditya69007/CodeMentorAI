import {
  useCallback,
  useEffect,
  useMemo,
  useState,
} from "react";

import axios from "axios";

import {
  FiAlertCircle,
  FiBookOpen,
  FiCheckCircle,
  FiEdit2,
  FiPlus,
  FiPower,
  FiSearch,
  FiTrash2,
  FiX,
  FiXCircle,
} from "react-icons/fi";

import {
  createTopic,
  deleteTopic,
  getAdminTopics,
  toggleTopicStatus,
  updateTopic,
} from "../../services/topicService";

import type {
  AdminTopic,
  TopicRequest,
} from "../../types/topic";


type StatusFilter =
  | "ALL"
  | "ACTIVE"
  | "INACTIVE";


const emptyForm: TopicRequest = {
  name: "",
  slug: "",
  description: "",
};


export default function AdminTopicsPage() {

  const [topics, setTopics] =
    useState<AdminTopic[]>([]);

  const [loading, setLoading] =
    useState(true);

  const [error, setError] =
    useState("");

  const [search, setSearch] =
    useState("");

  const [statusFilter, setStatusFilter] =
    useState<StatusFilter>("ALL");


  const [formOpen, setFormOpen] =
    useState(false);

  const [editingTopic, setEditingTopic] =
    useState<AdminTopic | null>(null);

  const [formData, setFormData] =
    useState<TopicRequest>(emptyForm);

  const [formLoading, setFormLoading] =
    useState(false);

  const [formError, setFormError] =
    useState("");


  const [topicToDelete, setTopicToDelete] =
    useState<AdminTopic | null>(null);

  const [deleting, setDeleting] =
    useState(false);

  const [deleteError, setDeleteError] =
    useState("");

  const [statusLoadingId, setStatusLoadingId] =
    useState<number | null>(null);


const loadTopics = useCallback(
    async (showLoading = true) => {

        try {

        if (showLoading) {
            setLoading(true);
        }

        setError("");

        const response =
            await getAdminTopics();

        setTopics(response);

        } catch (error: unknown) {

        console.error(error);

        setError(
            axios.isAxiosError(error)
            ? error.response?.data?.message ??
                "Unable to load topics."
            : "Unable to load topics."
        );

        } finally {

        if (showLoading) {
            setLoading(false);
        }

        }

    },
    []
    );


    useEffect(() => {

    let cancelled = false;


    const initializeTopics = async () => {

        try {

        const response =
            await getAdminTopics();


        if (!cancelled) {

            setTopics(response);

            setError("");

            setLoading(false);

        }

        } catch (error: unknown) {

        console.error(error);


        if (!cancelled) {

            setError(
            axios.isAxiosError(error)
                ? error.response?.data?.message ??
                    "Unable to load topics."
                : "Unable to load topics."
            );

            setLoading(false);

        }

        }

    };


    void initializeTopics();


    return () => {

        cancelled = true;

    };

    }, []);


  const statistics = useMemo(() => {

    const active =
      topics.filter(
        (topic) => topic.active
      ).length;

    const inactive =
      topics.length - active;

    const problems =
      topics.reduce(
        (total, topic) =>
          total + topic.problemCount,
        0
      );

    return {
      total: topics.length,
      active,
      inactive,
      problems,
    };

  }, [topics]);


  const filteredTopics = useMemo(() => {

    const normalizedSearch =
      search.trim().toLowerCase();

    return topics.filter((topic) => {

      const matchesSearch =
        !normalizedSearch ||
        topic.name
          .toLowerCase()
          .includes(normalizedSearch) ||
        topic.slug
          .toLowerCase()
          .includes(normalizedSearch) ||
        (
          topic.description ?? ""
        )
          .toLowerCase()
          .includes(normalizedSearch);


      const matchesStatus =
        statusFilter === "ALL" ||
        (
          statusFilter === "ACTIVE" &&
          topic.active
        ) ||
        (
          statusFilter === "INACTIVE" &&
          !topic.active
        );


      return (
        matchesSearch &&
        matchesStatus
      );

    });

  }, [
    topics,
    search,
    statusFilter,
  ]);


  const openCreateForm = () => {

    setEditingTopic(null);

    setFormData(emptyForm);

    setFormError("");

    setFormOpen(true);
  };


  const openEditForm = (
    topic: AdminTopic
  ) => {

    setEditingTopic(topic);

    setFormData({
      name: topic.name,
      slug: topic.slug,
      description:
        topic.description ?? "",
    });

    setFormError("");

    setFormOpen(true);
  };


  const closeForm = () => {

    if (formLoading) {
      return;
    }

    setFormOpen(false);

    setEditingTopic(null);

    setFormData(emptyForm);

    setFormError("");
  };


  const handleNameChange = (
    value: string
  ) => {

    setFormData((current) => ({

      ...current,

      name: value,

      slug:
        editingTopic
          ? current.slug
          : value
              .toLowerCase()
              .trim()
              .replace(
                /[^a-z0-9]+/g,
                "-"
              )
              .replace(
                /^-+|-+$/g,
                ""
              ),

    }));
  };


  const handleSubmit = async (
    event: React.FormEvent
  ) => {

    event.preventDefault();


    if (
      !formData.name.trim() ||
      !formData.slug.trim()
    ) {

      setFormError(
        "Topic name and slug are required."
      );

      return;
    }


    try {

      setFormLoading(true);

      setFormError("");


      if (editingTopic) {

        await updateTopic(
          editingTopic.id,
          formData
        );

      } else {

        await createTopic(
          formData
        );

      }


      closeForm();

      await loadTopics(false);

    } catch (error: unknown) {

    console.error(error);

    setFormError(
        axios.isAxiosError(error)
        ? error.response?.data?.message ??
            "Unable to save topic."
        : "Unable to save topic."
    );

    } finally {

      setFormLoading(false);

    }

  };


  const handleToggleStatus = async (
    topic: AdminTopic
  ) => {

    try {

      setStatusLoadingId(topic.id);

      setError("");

      const updatedTopic =
        await toggleTopicStatus(
          topic.id
        );


      setTopics((current) =>
        current.map((item) =>
          item.id === updatedTopic.id
            ? updatedTopic
            : item
        )
      );

    } catch (error: unknown) {

    console.error(error);

    setError(
        axios.isAxiosError(error)
        ? error.response?.data?.message ??
            "Unable to update topic status."
        : "Unable to update topic status."
    );

    } finally {

      setStatusLoadingId(null);

    }

  };


  const handleDelete = async () => {

    if (!topicToDelete) {
      return;
    }


    try {

      setDeleting(true);

      setDeleteError("");


      await deleteTopic(
        topicToDelete.id
      );


      setTopics((current) =>
        current.filter(
          (topic) =>
            topic.id !==
            topicToDelete.id
        )
      );


      setTopicToDelete(null);

    } catch (error: unknown) {

      console.error(error);

    setDeleteError(
    axios.isAxiosError(error)
        ? error.response?.data?.message ??
            "Unable to delete topic."
        : "Unable to delete topic."
    );

    } finally {

      setDeleting(false);

    }

  };


  return (

    <div className="mx-auto w-full max-w-7xl">


      {/* HEADER */}

      <div className="flex flex-col gap-5 sm:flex-row sm:items-end sm:justify-between">

        <div>

          <p className="text-sm font-semibold uppercase tracking-wider text-blue-500">
            Content Management
          </p>

          <h1 className="mt-2 text-3xl font-bold tracking-tight">
            Topics
          </h1>

          <p className="app-text-secondary mt-2">
            Manage problem-solving topics and their availability.
          </p>

        </div>


        <button
          type="button"
          onClick={openCreateForm}
          className="inline-flex items-center justify-center gap-2 rounded-lg bg-blue-600 px-4 py-2.5 text-sm font-semibold text-white transition hover:bg-blue-500"
        >

          <FiPlus />

          Create Topic

        </button>

      </div>


      {/* ERROR */}

      {error && (

        <div className="mt-6 flex items-center gap-3 rounded-lg border border-red-500/30 bg-red-500/10 p-4 text-sm text-red-500">

          <FiAlertCircle />

          {error}

        </div>

      )}


      {/* STATISTICS */}

      <div className="mt-8 grid gap-4 sm:grid-cols-2 xl:grid-cols-4">

        <StatCard
          title="Total Topics"
          value={statistics.total}
          icon={<FiBookOpen />}
        />

        <StatCard
          title="Active Topics"
          value={statistics.active}
          icon={<FiCheckCircle />}
        />

        <StatCard
          title="Inactive Topics"
          value={statistics.inactive}
          icon={<FiXCircle />}
        />

        <StatCard
          title="Assigned Problems"
          value={statistics.problems}
          icon={<FiBookOpen />}
        />

      </div>


      {/* FILTERS */}

      <section className="app-surface app-border mt-6 rounded-2xl border p-4">
        <div className="flex flex-col gap-3 md:flex-row md:items-center">

          {/* Search */}
          <div className="relative flex-1">
            <FiSearch
              size={18}
              className="app-text-muted pointer-events-none absolute left-4 top-1/2 z-10 -translate-y-1/2"
            />

            <input
              type="text"
              value={search}
              onChange={(event) =>
                setSearch(event.target.value)
              }
              placeholder="Search topics..."
              className="
                admin-input
                h-12
                w-full
                rounded-xl
                !pl-11
                !pr-4
                outline-none
                transition
                focus:border-blue-500
                focus:ring-2
                focus:ring-blue-500/20
              "
            />
          </div>

          {/* Status Filter */}
          <div className="relative md:w-52">
            <select
              value={statusFilter}
              onChange={(event) =>
                setStatusFilter(
                  event.target.value as StatusFilter
                )
              }
              className="
                admin-input
                h-12
                w-full
                cursor-pointer
                rounded-xl
                px-4
                outline-none
                transition
                focus:border-blue-500
                focus:ring-2
                focus:ring-blue-500/20
              "
            >
              <option value="ALL">
                All Topics
              </option>

              <option value="ACTIVE">
                Active
              </option>

              <option value="INACTIVE">
                Inactive
              </option>
            </select>
          </div>

        </div>
      </section>


      {/* TOPIC TABLE */}

      <section className="app-surface app-border mt-6 overflow-hidden rounded-xl border">

        <div className="app-border flex items-center justify-between border-b px-5 py-4">

          <div>

            <h2 className="font-semibold">
              Topic Library
            </h2>

            <p className="app-text-secondary mt-1 text-sm">
              {filteredTopics.length} topics shown
            </p>

          </div>

        </div>


        {loading ? (

          <div className="flex min-h-72 items-center justify-center">

            <p className="app-text-secondary text-sm">
              Loading topics...
            </p>

          </div>

        ) : filteredTopics.length === 0 ? (

          <div className="flex min-h-72 flex-col items-center justify-center p-6 text-center">

            <FiBookOpen
              size={28}
              className="app-text-muted"
            />

            <h3 className="mt-4 font-semibold">
              No topics found
            </h3>

            <p className="app-text-secondary mt-2 text-sm">
              Try changing your search or status filter.
            </p>

          </div>

        ) : (

          <div className="overflow-x-auto">

            <table className="w-full min-w-[850px]">

              <thead className="app-surface-secondary">

                <tr className="app-border border-b text-left text-xs uppercase tracking-wide">

                  <th className="px-5 py-3">
                    Topic
                  </th>

                  <th className="px-5 py-3">
                    Slug
                  </th>

                  <th className="px-5 py-3">
                    Problems
                  </th>

                  <th className="px-5 py-3">
                    Status
                  </th>

                  <th className="px-5 py-3 text-right">
                    Actions
                  </th>

                </tr>

              </thead>


              <tbody>

                {filteredTopics.map(
                  (topic) => (

                    <tr
                      key={topic.id}
                      className="app-border border-b last:border-b-0"
                    >

                      <td className="px-5 py-4">

                        <p className="font-semibold">
                          {topic.name}
                        </p>

                        <p className="app-text-secondary mt-1 max-w-md truncate text-sm">
                          {topic.description || "No description"}
                        </p>

                      </td>


                      <td className="app-text-secondary px-5 py-4 text-sm">
                        {topic.slug}
                      </td>


                      <td className="px-5 py-4 text-sm font-semibold">
                        {topic.problemCount}
                      </td>


                      <td className="px-5 py-4">

                        <span
                          className={
                            topic.active
                              ? "inline-flex rounded-full bg-emerald-500/10 px-2.5 py-1 text-xs font-semibold text-emerald-500"
                              : "inline-flex rounded-full bg-slate-500/10 px-2.5 py-1 text-xs font-semibold text-slate-500"
                          }
                        >

                          {topic.active
                            ? "Active"
                            : "Inactive"}

                        </span>

                      </td>


                      <td className="px-5 py-4">

                        <div className="flex justify-end gap-2">

                          <ActionButton
                            title="Edit topic"
                            onClick={() =>
                              openEditForm(topic)
                            }
                          >
                            <FiEdit2 />
                          </ActionButton>


                          <ActionButton
                            title={
                              topic.active
                                ? "Deactivate topic"
                                : "Activate topic"
                            }
                            disabled={
                              statusLoadingId ===
                              topic.id
                            }
                            onClick={() =>
                              void handleToggleStatus(
                                topic
                              )
                            }
                          >
                            <FiPower />
                          </ActionButton>


                          <ActionButton
                            title="Delete topic"
                            danger
                            onClick={() => {

                              setDeleteError("");

                              setTopicToDelete(
                                topic
                              );

                            }}
                          >
                            <FiTrash2 />
                          </ActionButton>

                        </div>

                      </td>

                    </tr>

                  )
                )}

              </tbody>

            </table>

          </div>

        )}

      </section>


      {/* CREATE / EDIT MODAL */}

      {formOpen && (

        <ModalShell
          onClose={closeForm}
        >

          <div className="flex items-start justify-between">

            <div>

              <h2 className="text-xl font-bold">

                {editingTopic
                  ? "Edit Topic"
                  : "Create Topic"}

              </h2>

              <p className="app-text-secondary mt-1 text-sm">

                {editingTopic
                  ? "Update topic information."
                  : "Add a new topic to the problem library."}

              </p>

            </div>


            <button
              type="button"
              onClick={closeForm}
              disabled={formLoading}
              className="app-hover app-text-secondary rounded-lg p-2"
            >
              <FiX />
            </button>

          </div>


          <form
            onSubmit={handleSubmit}
            className="mt-6 space-y-5"
          >

            <div>

              <label className="text-sm font-semibold">
                Topic Name *
              </label>

              <input
                value={formData.name}
                onChange={(event) =>
                  handleNameChange(
                    event.target.value
                  )
                }
                className="admin-input mt-2"
                placeholder="Example: Dynamic Programming"
              />

            </div>


            <div>

              <label className="text-sm font-semibold">
                Slug *
              </label>

              <input
                value={formData.slug}
                onChange={(event) =>
                  setFormData(
                    (current) => ({
                      ...current,
                      slug:
                        event.target.value,
                    })
                  )
                }
                className="admin-input mt-2"
                placeholder="dynamic-programming"
              />

            </div>


            <div>

              <label className="text-sm font-semibold">
                Description
              </label>

              <textarea
                value={formData.description}
                onChange={(event) =>
                  setFormData(
                    (current) => ({
                      ...current,
                      description:
                        event.target.value,
                    })
                  )
                }
                className="admin-input mt-2 min-h-28 resize-y"
                placeholder="Describe this topic..."
              />

            </div>


            {formError && (

              <div className="rounded-lg border border-red-500/30 bg-red-500/10 p-3 text-sm text-red-500">
                {formError}
              </div>

            )}


            <div className="flex justify-end gap-3">

              <button
                type="button"
                onClick={closeForm}
                disabled={formLoading}
                className="app-hover app-border rounded-lg border px-4 py-2 text-sm font-semibold"
              >
                Cancel
              </button>

              <button
                type="submit"
                disabled={formLoading}
                className="rounded-lg bg-blue-600 px-4 py-2 text-sm font-semibold text-white hover:bg-blue-500 disabled:opacity-60"
              >

                {formLoading
                  ? "Saving..."
                  : editingTopic
                    ? "Save Changes"
                    : "Create Topic"}

              </button>

            </div>

          </form>

        </ModalShell>

      )}


      {/* DELETE MODAL */}

      {topicToDelete && (

        <ModalShell
          onClose={() => {

            if (!deleting) {
              setTopicToDelete(null);
              setDeleteError("");
            }

          }}
        >

          <h2 className="text-xl font-bold">
            Delete Topic
          </h2>

          <p className="app-text-secondary mt-3 text-sm leading-6">

            Are you sure you want to permanently delete{" "}

            <strong>
              {topicToDelete.name}
            </strong>
            ?

          </p>


          {topicToDelete.problemCount > 0 && (

            <div className="mt-4 rounded-lg border border-amber-500/30 bg-amber-500/10 p-4 text-sm text-amber-500">

              This topic contains{" "}

              {topicToDelete.problemCount}{" "}

              problems and cannot be permanently deleted. Deactivate it instead.

            </div>

          )}


          {deleteError && (

            <div className="mt-4 rounded-lg border border-red-500/30 bg-red-500/10 p-4 text-sm text-red-500">
              {deleteError}
            </div>

          )}


          <div className="mt-6 flex justify-end gap-3">

            <button
              type="button"
              disabled={deleting}
              onClick={() => {
                setTopicToDelete(null);
                setDeleteError("");
              }}
              className="app-hover app-border rounded-lg border px-4 py-2 text-sm font-semibold"
            >
              Cancel
            </button>


            <button
              type="button"
              disabled={
                deleting ||
                topicToDelete.problemCount > 0
              }
              onClick={() =>
                void handleDelete()
              }
              className="rounded-lg bg-red-600 px-4 py-2 text-sm font-semibold text-white hover:bg-red-500 disabled:cursor-not-allowed disabled:opacity-50"
            >

              {deleting
                ? "Deleting..."
                : "Delete Topic"}

            </button>

          </div>

        </ModalShell>

      )}

    </div>

  );
}


function StatCard({
  title,
  value,
  icon,
}: {
  title: string;
  value: number;
  icon: React.ReactNode;
}) {

  return (

    <div className="app-surface app-border rounded-xl border p-5">

      <div className="flex items-center justify-between">

        <div>

          <p className="app-text-secondary text-sm">
            {title}
          </p>

          <p className="mt-2 text-3xl font-bold">
            {value}
          </p>

        </div>

        <div className="flex h-11 w-11 items-center justify-center rounded-xl bg-blue-500/10 text-xl text-blue-500">
          {icon}
        </div>

      </div>

    </div>

  );
}


function ActionButton({
  title,
  children,
  onClick,
  danger = false,
  disabled = false,
}: {
  title: string;
  children: React.ReactNode;
  onClick: () => void;
  danger?: boolean;
  disabled?: boolean;
}) {

  return (

    <button
      type="button"
      title={title}
      onClick={onClick}
      disabled={disabled}
      className={`
        app-border
        rounded-lg
        border
        p-2
        transition
        disabled:cursor-not-allowed
        disabled:opacity-50
        ${
          danger
            ? "text-red-500 hover:bg-red-500/10"
            : "app-text-secondary app-hover"
        }
      `}
    >
      {children}
    </button>

  );
}


function ModalShell({
  children,
  onClose,
}: {
  children: React.ReactNode;
  onClose: () => void;
}) {

  return (

    <div
      className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 p-4"
      onMouseDown={(event) => {

        if (
          event.target ===
          event.currentTarget
        ) {

          onClose();

        }

      }}
    >

      <div className="app-surface app-border w-full max-w-lg rounded-xl border p-6 shadow-2xl">

        {children}

      </div>

    </div>

  );
}