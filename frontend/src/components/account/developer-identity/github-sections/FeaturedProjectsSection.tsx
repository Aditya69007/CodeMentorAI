import { useEffect, useState } from "react";
import toast from "react-hot-toast";

import {
  getFeaturedProjects,
  updateFeaturedProjects,
} from "../../../../services/featuredProjectService";

import type {
  FeaturedProject,
} from "../../../../types/featuredProject";

import type {
  GitHubRepository,
} from "../../../../types/github";

type Props = {
  repositories: GitHubRepository[];
};

export default function FeaturedProjectsSection({
  repositories,
}: Props) {

  const [selected, setSelected] =
    useState<string[]>([]);

  const [saving, setSaving] =
    useState(false);

  useEffect(() => {
    async function load() {
    
        try {
    
        const response =
          await getFeaturedProjects();

        const validRepositories = new Set(
          repositories.map((repo) => repo.name)
        );

        const filteredSelection = response
          .map((project: FeaturedProject) => project.repositoryName)
          .filter((name) => validRepositories.has(name));

        setSelected(filteredSelection);
    
        } catch (error) {
    
          console.error(error);
    
        }
    
  }

    load();

  }, []);


  function toggleRepository(
    repositoryName: string
  ) {
    console.log(selected);
    if (selected.includes(repositoryName)) {

      setSelected(
        selected.filter(
          (name) => name !== repositoryName
        )
      );

      return;

    }

    if (selected.length >= 3) {

      toast.error(
        "You can select up to 3 repositories."
      );

      return;

    }

    setSelected([
      ...selected,
      repositoryName,
    ]);

  }

  async function save() {

    try {

      setSaving(true);

      await updateFeaturedProjects({

        repositoryNames: selected,

      });

      toast.success(
        "Featured projects updated."
      );

    } catch {

      toast.error(
        "Unable to save featured projects."
      );

    } finally {

      setSaving(false);

    }

  }

  return (

    <div className="rounded-xl border border-white/10 p-5">

      <div className="flex items-center justify-between">

        <div>

          <h3 className="text-lg font-semibold">

            ⭐ Featured Projects

          </h3>

          <p className="mt-1 text-sm app-text-secondary">

            Choose up to 3 repositories to
            display on your portfolio.

          </p>

        </div>

        <button
          onClick={save}
          disabled={saving}
          className="rounded-xl bg-green-600 px-5 py-2 text-white disabled:opacity-60"
        >

          {saving
            ? "Saving..."
            : "Save"}

        </button>

      </div>

      <div className="mt-5 space-y-3">

        {repositories.map((repository) => (

          <label
            key={repository.name}
            className="flex cursor-pointer items-center justify-between rounded-xl border border-white/10 p-4"
          >

            <div>

              <h4 className="font-semibold">

                {repository.name}

              </h4>

              <p className="text-sm app-text-secondary">

                {repository.description}

              </p>

            </div>

            <input
              type="checkbox"
              checked={
                selected.includes(
                  repository.name
                )
              }
              onChange={() =>
                toggleRepository(
                  repository.name
                )
              }
            />

          </label>

        ))}

      </div>

    </div>

  );

}