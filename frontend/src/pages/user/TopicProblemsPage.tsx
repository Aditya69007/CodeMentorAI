import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import TopicHeader from "../../components/topic/TopicHeader";
import TopicSidebar from "../../components/topic/TopicSidebar";
import TopicSearch from "../../components/topic/TopicSearch";
import TopicProblemList from "../../components/topic/TopicProblemList";

import {
  getTopicBySlug,
  getProblemsByTopic,
  getTopicProgress,
  type TopicProgress,
} from "../../services/topicService";

import type {
  Topic,
  TopicProblem,
} from "../../types/topic";


export default function TopicProblemsPage() {

  const navigate = useNavigate();

  const { slug } = useParams<{ slug: string }>();

  const [topic, setTopic] =
    useState<Topic | null>(null);

  const [problems, setProblems] =
    useState<TopicProblem[]>([]);

  const [progress, setProgress] =
    useState<TopicProgress | null>(null);

  const [loading, setLoading] =
    useState(true);

  const [error, setError] =
    useState("");

  const [search, setSearch] =
    useState("");


  const loadData = async () => {

    if (!slug) return;

    try {

      setLoading(true);

      const [
        topicData,
        problemsData,
        progressData,
      ] = await Promise.all([

        getTopicBySlug(slug),

        getProblemsByTopic(slug),

        getTopicProgress(slug),

      ]);

      setTopic(topicData);

      setProblems(problemsData);

      setProgress(progressData);

    } catch (err) {

      console.error(err);

      setError("Failed to load topic.");

    } finally {

      setLoading(false);

    }

  };

  useEffect(() => {
    if (!slug) return;

    const fetchData = async () => {
      await loadData();
    };

    fetchData();
  }, [slug]);

  const filteredProblems =
    problems.filter(problem => {

      if (!search.trim()) return true;

      return problem.title
        .toLowerCase()
        .includes(search.toLowerCase());

    });

  const startRecommendedProblem = () => {

    if (!progress?.recommendedProblemId) return;

    navigate(`/problems/${progress.recommendedProblemId}`);

  };

  return (

    <main className="mx-auto max-w-7xl px-6 py-8">

      {loading && (

        <div className="py-20 text-center">

          Loading...

        </div>

      )}

      {error && (

        <div className="rounded-xl bg-red-500/10 border border-red-500/30 p-6">

          {error}

        </div>

      )}

      {!loading && !error && topic && progress && (

        <>

          <TopicHeader
            topic={topic}
            progress={progress}
            problemCount={problems.length}
            onBack={() => navigate("/topics")}
          />

          <div
            className="
              mt-8
              grid
              gap-6
              grid-cols-1
              lg:grid-cols-[320px_1fr]
            "
          >

            <TopicSidebar
              progress={progress}
              onStart={startRecommendedProblem}
            />

            <div className="space-y-5">

              <TopicSearch
                search={search}
                onSearchChange={setSearch}
              />

              <TopicProblemList
                problems={filteredProblems}
                onOpenProblem={(id) =>
                  navigate(`/problems/${id}`)
                }
              />

            </div>

          </div>

        </>

      )}

    </main>

  );

}