import {
  useEffect,
  useState,
} from "react";

import {
  useNavigate,
} from "react-router-dom";

import {
  FiAlertCircle,
  FiBookOpen,
  FiChevronRight,
  FiLayers,
  FiSearch,
} from "react-icons/fi";

import {
  getAllTopics,
} from "../../services/topicService";

import type {
  Topic,
} from "../../types/topic";


export default function TopicsPage() {

  const navigate = useNavigate();


  // ==================================================
  // STATE
  // ==================================================

  const [topics, setTopics] =
    useState<Topic[]>([]);

  const [loading, setLoading] =
    useState(true);

  const [error, setError] =
    useState("");

  const [search, setSearch] =
    useState("");

  const [topicFilter, setTopicFilter] =
    useState<
      "ALL" |
      "AVAILABLE" |
      "EMPTY"
    >("ALL");

  const [sortBy, setSortBy] =
    useState<
      "NAME" |
      "PROBLEMS"
    >("NAME");


  // ==================================================
  // LOAD TOPICS
  // ==================================================

  useEffect(() => {

    const loadTopics = async () => {

      try {

        setLoading(true);

        setError("");

        const response =
          await getAllTopics();

        setTopics(response);

      } catch (error) {

        console.error(error);

        setError(
          "Unable to load topics."
        );

      } finally {

        setLoading(false);

      }
    };


    loadTopics();

  }, []);


  // ==================================================
  // TOTAL PROBLEM COUNT
  // ==================================================

  const totalProblems =
    topics.reduce(
      (total, topic) =>
        total + topic.problemCount,
      0
    );


  // ==================================================
  // SEARCH + FILTER + SORT
  // ==================================================

  const filteredTopics =
    topics

      .filter((topic) => {

        const searchValue =
          search
            .trim()
            .toLowerCase();


        const matchesSearch =

          topic.name
            .toLowerCase()
            .includes(searchValue)

          ||

          topic.description
            .toLowerCase()
            .includes(searchValue);


        const matchesFilter =

          topicFilter === "ALL"

            ? true

            : topicFilter === "AVAILABLE"

              ? topic.problemCount > 0

              : topic.problemCount === 0;


        return (
          matchesSearch &&
          matchesFilter
        );

      })

      .sort((first, second) => {

        if (sortBy === "PROBLEMS") {

          return (
            second.problemCount -
            first.problemCount
          );

        }


        return first.name.localeCompare(
          second.name
        );

      });


  // ==================================================
  // UI
  // ==================================================

  return (

    <main className="mx-auto w-full max-w-[1300px] px-4 py-8 sm:px-6 lg:py-10">


      {/* ==================================================
          HEADER
      ================================================== */}

      <section className="flex flex-col gap-6 lg:flex-row lg:items-end lg:justify-between">


        <div>

          <p className="text-sm font-semibold text-blue-600 dark:text-blue-400">

            TOPIC LIBRARY

          </p>


          <h1 className="mt-2 text-3xl font-bold tracking-tight sm:text-4xl">

            Practice by topic

          </h1>


          <p className="app-text-secondary mt-3 max-w-2xl text-sm leading-6 sm:text-base">

            Strengthen your coding skills by practicing
            problems organized by data structures,
            algorithms, and problem-solving techniques.

          </p>

        </div>



        {/* STATISTICS */}

        <div className="flex gap-3">


          <div className="app-surface app-border rounded-lg border px-5 py-4">

            <p className="app-text-secondary text-xs font-medium uppercase tracking-wide">

              Topics

            </p>

            <p className="mt-1 text-2xl font-bold">

              {topics.length}

            </p>

          </div>


          <div className="app-surface app-border rounded-lg border px-5 py-4">

            <p className="app-text-secondary text-xs font-medium uppercase tracking-wide">

              Problems

            </p>

            <p className="mt-1 text-2xl font-bold">

              {totalProblems}

            </p>

          </div>


        </div>


      </section>



      {/* ==================================================
          SEARCH AND FILTERS
      ================================================== */}

      <section className="app-surface app-border mt-8 rounded-lg border p-3">


        <div className="flex flex-col gap-3 lg:flex-row">


          {/* SEARCH */}

          <div className="relative flex-1">

            <FiSearch
              size={18}
              className="app-text-muted absolute left-3.5 top-1/2 -translate-y-1/2"
            />


            <input

              type="text"

              value={search}

              placeholder="Search topics..."

              onChange={(event) =>
                setSearch(
                  event.target.value
                )
              }

              className="
                app-surface-secondary
                app-border
                w-full
                rounded-md
                border
                py-2.5
                pl-10
                pr-4
                text-sm
                outline-none
                focus:border-blue-500
                focus:ring-2
                focus:ring-blue-500/10
              "

            />

          </div>



          {/* AVAILABILITY FILTER */}

          <select

            value={topicFilter}

            onChange={(event) =>

              setTopicFilter(

                event.target.value as

                  | "ALL"
                  | "AVAILABLE"
                  | "EMPTY"

              )

            }

            className="
              app-surface-secondary
              app-border
              min-w-44
              rounded-md
              border
              px-4
              py-2.5
              text-sm
              outline-none
              focus:border-blue-500
            "

          >

            <option value="ALL">

              All topics

            </option>


            <option value="AVAILABLE">

              Has problems

            </option>


            <option value="EMPTY">

              Coming soon

            </option>

          </select>



          {/* SORT */}

          <select

            value={sortBy}

            onChange={(event) =>

              setSortBy(

                event.target.value as

                  | "NAME"
                  | "PROBLEMS"

              )

            }

            className="
              app-surface-secondary
              app-border
              min-w-44
              rounded-md
              border
              px-4
              py-2.5
              text-sm
              outline-none
              focus:border-blue-500
            "

          >

            <option value="NAME">

              Sort by name

            </option>


            <option value="PROBLEMS">

              Most problems

            </option>

          </select>


        </div>



        {/* RESULT COUNT */}

        <div className="app-text-secondary mt-3 px-1 text-xs">

          Showing{" "}

          <span className="app-text-primary font-semibold">

            {filteredTopics.length}

          </span>

          {" "}of{" "}

          <span className="app-text-primary font-semibold">

            {topics.length}

          </span>

          {" "}topics

        </div>


      </section>



      {/* ==================================================
          ERROR
      ================================================== */}

      {error && (

        <div className="mt-6 flex items-center gap-3 rounded-lg border border-red-500/30 bg-red-500/10 p-4 text-sm text-red-500">

          <FiAlertCircle size={19} />

          {error}

        </div>

      )}



      {/* ==================================================
          LOADING
      ================================================== */}

      {loading && (

        <section className="mt-8 grid gap-4 md:grid-cols-2 xl:grid-cols-3">


          {[
            1,
            2,
            3,
            4,
            5,
            6,
          ].map((item) => (

            <div

              key={item}

              className="app-surface app-border h-52 animate-pulse rounded-lg border"

            />

          ))}


        </section>

      )}



      {/* ==================================================
          TOPIC GRID
      ================================================== */}

      {!loading && !error && (

        <section className="mt-8 grid gap-4 md:grid-cols-2 xl:grid-cols-3">


          {/* EMPTY SEARCH RESULT */}

          {filteredTopics.length === 0 ? (


            <div className="app-surface app-border col-span-full rounded-lg border px-5 py-16 text-center">


              <FiSearch

                size={28}

                className="app-text-muted mx-auto"

              />


              <h2 className="mt-4 font-semibold">

                No topics found

              </h2>


              <p className="app-text-secondary mt-2 text-sm">

                Try changing your search or filter.

              </p>


            </div>


          ) : (


            filteredTopics.map((topic) => (


              <button

                key={topic.id}

                onClick={() =>

                  navigate(

                    `/topics/${topic.slug}`

                  )

                }

                className="
                  app-surface
                  app-hover
                  app-border
                  group
                  flex
                  min-h-52
                  flex-col
                  rounded-lg
                  border
                  p-5
                  text-left
                "

              >


                {/* CARD HEADER */}

                <div className="flex items-start justify-between gap-4">


                  <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-blue-500/10 text-blue-500">

                    <FiLayers size={20} />

                  </div>


                  <FiChevronRight

                    className="
                      app-text-muted
                      transition-transform
                      group-hover:translate-x-1
                      group-hover:text-blue-500
                    "

                  />


                </div>



                {/* TOPIC NAME */}

                <h2 className="mt-5 text-lg font-semibold group-hover:text-blue-600 dark:group-hover:text-blue-400">

                  {topic.name}

                </h2>



                {/* DESCRIPTION */}

                <p className="app-text-secondary mt-2 line-clamp-3 text-sm leading-6">

                  {topic.description}

                </p>



                {/* PROBLEM COUNT */}

                <div className="mt-auto flex items-center gap-2 pt-5">


                  <FiBookOpen

                    size={15}

                    className="app-text-muted"

                  />


                  <span className="app-text-secondary text-sm">

                    {topic.problemCount}{" "}

                    {topic.problemCount === 1

                      ? "problem"

                      : "problems"}

                  </span>


                </div>


              </button>


            ))


          )}


        </section>

      )}


    </main>

  );

}