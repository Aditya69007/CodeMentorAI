import {
  useEffect,
  useState,
} from "react";

import {
  useNavigate,
  useParams,
} from "react-router-dom";

import {
  FiAlertCircle,
  FiArrowLeft,
  FiBookOpen,
  FiChevronRight,
  FiCode,
} from "react-icons/fi";

import {
  getProblemsByTopic,
  getTopicBySlug,
} from "../../services/topicService";

import type {
  Topic,
  TopicProblem,
} from "../../types/topic";

import type {
  Difficulty,
} from "../../types/problem";


export default function TopicProblemsPage() {

  const navigate = useNavigate();

  const { slug } =
    useParams<{ slug: string }>();


  const [topic, setTopic] =
    useState<Topic | null>(null);

  const [problems, setProblems] =
    useState<TopicProblem[]>([]);

  const [loading, setLoading] =
    useState(true);

  const [error, setError] =
    useState("");


  useEffect(() => {

    const loadTopic = async () => {

      if (!slug) {

        setError(
          "Invalid topic."
        );

        setLoading(false);

        return;
      }


      try {

        setLoading(true);

        setError("");


        const [
          topicResponse,
          problemsResponse,
        ] = await Promise.all([

          getTopicBySlug(slug),

          getProblemsByTopic(slug),

        ]);


        setTopic(topicResponse);

        setProblems(problemsResponse);


      } catch (error) {

        console.error(error);

        setError(
          "Unable to load topic problems."
        );

      } finally {

        setLoading(false);

      }

    };


    loadTopic();

  }, [slug]);



  const difficultyStyle = (
    value: Difficulty
  ) => {

    if (value === "EASY") {

      return "bg-emerald-500/10 text-emerald-600 dark:text-emerald-400";

    }


    if (value === "MEDIUM") {

      return "bg-amber-500/10 text-amber-600 dark:text-amber-400";

    }


    return "bg-red-500/10 text-red-600 dark:text-red-400";

  };



  return (

    <main className="mx-auto w-full max-w-[1300px] px-4 py-8 sm:px-6 lg:py-10">


      {/* BACK BUTTON */}

      <button
        onClick={() =>
          navigate("/topics")
        }
        className="app-text-secondary app-hover flex items-center gap-2 rounded-md px-3 py-2 text-sm"
      >

        <FiArrowLeft />

        Topic Library

      </button>



      {/* ERROR */}

      {error && (

        <div className="mt-6 flex items-center gap-3 rounded-lg border border-red-500/30 bg-red-500/10 p-4 text-sm text-red-500">

          <FiAlertCircle size={19} />

          {error}

        </div>

      )}



      {/* LOADING */}

      {loading && (

        <section className="mt-6">

          <div className="app-surface-secondary h-10 w-72 animate-pulse rounded" />

          <div className="app-surface-secondary mt-4 h-5 w-full max-w-xl animate-pulse rounded" />


          <div className="app-surface app-border mt-8 h-96 animate-pulse rounded-lg border" />

        </section>

      )}



      {!loading &&
        !error &&
        topic && (

          <>


            {/* TOPIC HEADER */}

            <section className="mt-6">

              <p className="text-sm font-semibold text-blue-600 dark:text-blue-400">

                TOPIC

              </p>


              <h1 className="mt-2 text-3xl font-bold tracking-tight sm:text-4xl">

                {topic.name}

              </h1>


              <p className="app-text-secondary mt-3 max-w-2xl text-sm leading-6 sm:text-base">

                {topic.description}

              </p>


              <div className="app-text-secondary mt-5 flex items-center gap-2 text-sm">

                <FiBookOpen />

                {problems.length}{" "}

                {problems.length === 1
                  ? "problem"
                  : "problems"}

              </div>

            </section>



            {/* PROBLEM LIST */}

            <section className="app-surface app-border mt-8 overflow-hidden rounded-lg border">


              {/* HEADER */}

              <div className="app-surface-secondary app-border hidden grid-cols-[70px_1fr_220px_140px_40px] border-b px-5 py-3 text-xs font-semibold uppercase tracking-wide md:grid">

                <span className="app-text-muted">

                  ID

                </span>


                <span className="app-text-muted">

                  Problem

                </span>


                <span className="app-text-muted">

                  Tags

                </span>


                <span className="app-text-muted">

                  Difficulty

                </span>


                <span />

              </div>



              {/* EMPTY */}

              {problems.length === 0 ? (

                <div className="flex flex-col items-center justify-center px-5 py-20 text-center">


                  <div className="app-surface-secondary flex h-12 w-12 items-center justify-center rounded-full">

                    <FiCode size={21} />

                  </div>


                  <h2 className="mt-4 font-semibold">

                    No problems available yet

                  </h2>


                  <p className="app-text-secondary mt-2 max-w-md text-sm">

                    Problems for this topic will appear
                    here as the CodeMentor AI library grows.

                  </p>


                </div>

              ) : (

                <div>


                  {problems.map((problem) => (

                    <button
                      key={problem.id}
                      onClick={() =>
                        navigate(
                          `/problems/${problem.id}`
                        )
                      }
                      className="
                        app-hover
                        app-border
                        group
                        grid
                        w-full
                        gap-3
                        border-b
                        p-5
                        text-left
                        last:border-b-0
                        md:grid-cols-[70px_1fr_220px_140px_40px]
                        md:items-center
                      "
                    >


                      <span className="app-text-muted hidden text-sm md:block">

                        {problem.id}

                      </span>


                      <div>

                        <span className="app-text-muted text-xs md:hidden">

                          #{problem.id}

                        </span>


                        <h2 className="text-sm font-semibold group-hover:text-blue-600 dark:group-hover:text-blue-400">

                          {problem.title}

                        </h2>

                      </div>



                      <div className="flex flex-wrap gap-1.5">


                        {problem.tags
                          ?.slice(0, 3)
                          .map((tag) => (

                            <span
                              key={tag}
                              className="app-surface-secondary app-text-secondary rounded px-2 py-1 text-xs"
                            >

                              {tag}

                            </span>

                          ))}


                      </div>



                      <div>

                        <span
                          className={`
                            inline-flex
                            rounded-full
                            px-2.5
                            py-1
                            text-xs
                            font-semibold
                            ${difficultyStyle(
                              problem.difficulty
                            )}
                          `}
                        >

                          {problem.difficulty}

                        </span>

                      </div>



                      <FiChevronRight className="app-text-muted hidden transition-transform group-hover:translate-x-1 group-hover:text-blue-500 md:block" />


                    </button>

                  ))}


                </div>

              )}


            </section>


          </>

        )}


    </main>
  );
}