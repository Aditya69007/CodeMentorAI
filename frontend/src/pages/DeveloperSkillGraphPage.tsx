import {
  useEffect,
  useMemo,
  useState,
} from "react";

import {
  FiActivity,
  FiAlertCircle,
  FiCheckCircle,
  FiRefreshCw,
  FiTarget,
  FiTrendingUp,
} from "react-icons/fi";

import {
  getHintDependencyScore,
  getMyDeveloperSkillGraph,
} from "../services/developerSkillService";

import HintDependencyCard
  from "../components/skills/HintDependencyCard";

import type {
  DeveloperSkill,
  DeveloperSkillLevel,
} from "../types/developerSkill";

import type {
  HintDependencyScore,
} from "../types/hintDependency";


// ==================================================
// FORMAT SKILL LEVEL
// ==================================================

const formatSkillLevel = (
  level: DeveloperSkillLevel
) => {

  return level
    .replaceAll("_", " ")
    .toLowerCase()
    .replace(
      /\b\w/g,
      (character) =>
        character.toUpperCase()
    );

};


// ==================================================
// SKILL LEVEL CLASSES
// ==================================================

const getSkillLevelClasses = (
  level: DeveloperSkillLevel
) => {

  switch (level) {

    case "MASTERED":
      return "border-emerald-500/30 bg-emerald-500/10 text-emerald-500";

    case "STRONG":
      return "border-blue-500/30 bg-blue-500/10 text-blue-500";

    case "DEVELOPING":
      return "border-amber-500/30 bg-amber-500/10 text-amber-500";

    case "NEEDS_PRACTICE":
      return "border-red-500/30 bg-red-500/10 text-red-500";

  }

};


// ==================================================
// SKILL BAR CLASSES
// ==================================================

const getSkillBarClasses = (
  level: DeveloperSkillLevel
) => {

  switch (level) {

    case "MASTERED":
      return "bg-emerald-500";

    case "STRONG":
      return "bg-blue-500";

    case "DEVELOPING":
      return "bg-amber-500";

    case "NEEDS_PRACTICE":
      return "bg-red-500";

  }

};


// ==================================================
// COMPONENT
// ==================================================

export default function DeveloperSkillGraphPage() {


  // ==================================================
  // STATE
  // ==================================================

  const [skills, setSkills] =
    useState<DeveloperSkill[]>([]);


  const [
    hintDependency,
    setHintDependency,
  ] =
    useState<HintDependencyScore | null>(
      null
    );


  const [loading, setLoading] =
    useState(true);


  const [error, setError] =
    useState("");



  // ==================================================
  // LOAD DEVELOPER INTELLIGENCE
  // ==================================================

  const loadDeveloperIntelligence =
    async () => {

      try {

        setLoading(true);

        setError("");


        const [
          skillsData,
          hintDependencyData,
        ] = await Promise.all([

          getMyDeveloperSkillGraph(),

          getHintDependencyScore(),

        ]);


        setSkills(
          skillsData
        );


        setHintDependency(
          hintDependencyData
        );


      } catch (error) {

        console.error(error);


        setError(
          "Unable to load your developer intelligence."
        );


      } finally {

        setLoading(false);

      }

    };



  // ==================================================
  // INITIAL LOAD
  // ==================================================

  useEffect(() => {

    let cancelled = false;


    const fetchDeveloperIntelligence =
      async () => {

        try {

          const [
            skillsData,
            hintDependencyData,
          ] = await Promise.all([

            getMyDeveloperSkillGraph(),

            getHintDependencyScore(),

          ]);


          if (!cancelled) {

            setSkills(
              skillsData
            );


            setHintDependency(
              hintDependencyData
            );


            setError("");

          }


        } catch (error) {

          console.error(error);


          if (!cancelled) {

            setError(
              "Unable to load your developer intelligence."
            );

          }


        } finally {

          if (!cancelled) {

            setLoading(false);

          }

        }

      };


    void fetchDeveloperIntelligence();


    return () => {

      cancelled = true;

    };


  }, []);



  // ==================================================
  // SUMMARY CALCULATION
  // ==================================================

  const summary = useMemo(() => {

    if (skills.length === 0) {

      return {

        averageSkillScore: 0,

        totalSubmissions: 0,

        acceptedSubmissions: 0,

        totalMistakes: 0,

      };

    }


    const totalSkillScore =
      skills.reduce(

        (sum, skill) =>
          sum + skill.skillScore,

        0

      );


    const totalSubmissions =
      skills.reduce(

        (sum, skill) =>
          sum + skill.totalSubmissions,

        0

      );


    const acceptedSubmissions =
      skills.reduce(

        (sum, skill) =>
          sum +
          skill.acceptedSubmissions,

        0

      );


    const totalMistakes =
      skills.reduce(

        (sum, skill) =>
          sum + skill.totalMistakes,

        0

      );


    return {

      averageSkillScore:
        Math.round(
          totalSkillScore /
          skills.length
        ),

      totalSubmissions,

      acceptedSubmissions,

      totalMistakes,

    };


  }, [skills]);



  // ==================================================
  // LOADING
  // ==================================================

  if (loading) {

    return (

      <div className="app-text-secondary flex min-h-[500px] items-center justify-center">

        Loading your developer intelligence...

      </div>

    );

  }



  // ==================================================
  // ERROR
  // ==================================================

  if (error) {

    return (

      <div className="mx-auto w-full max-w-7xl">


        <div className="rounded-2xl border border-red-500/30 bg-red-500/10 p-6">


          <div className="flex items-start gap-4">


            <FiAlertCircle
              className="mt-0.5 shrink-0 text-red-500"
              size={22}
            />


            <div className="min-w-0 flex-1">


              <h2 className="font-semibold text-red-500">

                Developer intelligence unavailable

              </h2>


              <p className="app-text-secondary mt-1 text-sm">

                {error}

              </p>


              <button

                type="button"

                onClick={() =>
                  void loadDeveloperIntelligence()
                }

                className="mt-4 inline-flex items-center gap-2 rounded-lg bg-red-500 px-4 py-2 text-sm font-semibold text-white transition hover:bg-red-600"

              >

                <FiRefreshCw />

                Try Again

              </button>


            </div>


          </div>


        </div>


      </div>

    );

  }



  // ==================================================
  // PAGE
  // ==================================================

  return (

    <div className="mx-auto w-full max-w-7xl px-6 py-8 lg:px-8">


      {/* ==================================================
          PAGE HEADER
      ================================================== */}

      <div>


        <p className="text-sm font-semibold uppercase tracking-wider text-blue-500">

          Developer Intelligence

        </p>


        <h1 className="mt-2 text-3xl font-bold tracking-tight">

          Developer Skill Graph

        </h1>


        <p className="app-text-secondary mt-2 max-w-3xl">

          Understand your coding strengths and improvement
          areas using your real submission performance,
          AI mistake history, and hint usage patterns.

        </p>


      </div>



      {/* ==================================================
          EMPTY STATE
      ================================================== */}

      {
        skills.length === 0
          ? (

            <section className="app-surface app-border mt-8 rounded-2xl border p-10 text-center">


              <div className="app-surface-secondary mx-auto flex h-14 w-14 items-center justify-center rounded-2xl">


                <FiActivity
                  className="text-blue-500"
                  size={24}
                />


              </div>


              <h2 className="mt-5 text-xl font-semibold">

                Your skill graph is waiting for data

              </h2>


              <p className="app-text-secondary mx-auto mt-2 max-w-lg text-sm">

                Solve coding problems across different topics.
                CodeMentor AI will use your submissions,
                mistake history, and AI hint usage to build
                your developer intelligence profile.

              </p>


            </section>

          )
          : (

            <>


              {/* ==================================================
                  SUMMARY CARDS
              ================================================== */}

              <div className="mt-8 grid gap-4 sm:grid-cols-2 xl:grid-cols-4">


                {/* AVERAGE SKILL SCORE */}

                <section className="app-surface app-border rounded-2xl border p-5">


                  <div className="flex items-start justify-between">


                    <div>


                      <p className="app-text-secondary text-sm font-medium">

                        Average Skill Score

                      </p>


                      <p className="mt-3 text-3xl font-bold">

                        {
                          summary.averageSkillScore
                        }

                        <span className="app-text-muted text-lg">

                          /100

                        </span>


                      </p>


                    </div>


                    <div className="app-surface-secondary flex h-11 w-11 items-center justify-center rounded-xl">


                      <FiTarget
                        className="text-blue-500"
                        size={20}
                      />


                    </div>


                  </div>


                </section>



                {/* TOPICS PRACTICED */}

                <section className="app-surface app-border rounded-2xl border p-5">


                  <div className="flex items-start justify-between">


                    <div>


                      <p className="app-text-secondary text-sm font-medium">

                        Topics Practiced

                      </p>


                      <p className="mt-3 text-3xl font-bold">

                        {skills.length}

                      </p>


                    </div>


                    <div className="app-surface-secondary flex h-11 w-11 items-center justify-center rounded-xl">


                      <FiTrendingUp
                        className="text-blue-500"
                        size={20}
                      />


                    </div>


                  </div>


                </section>



                {/* ACCEPTED SOLUTIONS */}

                <section className="app-surface app-border rounded-2xl border p-5">


                  <div className="flex items-start justify-between">


                    <div>


                      <p className="app-text-secondary text-sm font-medium">

                        Accepted Solutions

                      </p>


                      <p className="mt-3 text-3xl font-bold">

                        {
                          summary.acceptedSubmissions
                        }

                      </p>


                    </div>


                    <div className="app-surface-secondary flex h-11 w-11 items-center justify-center rounded-xl">


                      <FiCheckCircle
                        className="text-blue-500"
                        size={20}
                      />


                    </div>


                  </div>


                </section>



                {/* AI MISTAKES */}

                <section className="app-surface app-border rounded-2xl border p-5">


                  <div className="flex items-start justify-between">


                    <div>


                      <p className="app-text-secondary text-sm font-medium">

                        AI Mistakes Detected

                      </p>


                      <p className="mt-3 text-3xl font-bold">

                        {
                          summary.totalMistakes
                        }

                      </p>


                    </div>


                    <div className="app-surface-secondary flex h-11 w-11 items-center justify-center rounded-xl">


                      <FiAlertCircle
                        className="text-blue-500"
                        size={20}
                      />


                    </div>


                  </div>


                </section>


              </div>



              {/* ==================================================
                  HINT DEPENDENCY SCORE
              ================================================== */}

              {
                hintDependency && (

                  <div className="mt-7">


                    <HintDependencyCard
                      data={
                        hintDependency
                      }
                    />


                  </div>

                )
              }



              {/* ==================================================
                  SKILL GRAPH
              ================================================== */}

              <section className="app-surface app-border mt-7 rounded-2xl border">


                <div className="app-border border-b px-6 py-5">


                  <h2 className="text-lg font-semibold">

                    Topic Skill Profile

                  </h2>


                  <p className="app-text-secondary mt-1 text-sm">

                    Skill scores are calculated from submission
                    performance and recorded AI mistake patterns.

                  </p>


                </div>



                <div className="divide-y app-divide">


                  {
                    skills.map((skill) => (

                      <div
                        key={skill.topicId}
                        className="p-6"
                      >


                        <div className="flex flex-col gap-5 lg:flex-row lg:items-start lg:justify-between">


                          <div className="min-w-0 flex-1">


                            <div className="flex flex-wrap items-center gap-3">


                              <h3 className="text-lg font-semibold">

                                {
                                  skill.topicName
                                }

                              </h3>


                              <span

                                className={`
                                  rounded-full
                                  border
                                  px-2.5
                                  py-1
                                  text-xs
                                  font-semibold
                                  ${
                                    getSkillLevelClasses(
                                      skill.skillLevel
                                    )
                                  }
                                `}

                              >

                                {
                                  formatSkillLevel(
                                    skill.skillLevel
                                  )
                                }

                              </span>


                            </div>



                            <p className="app-text-secondary mt-2 text-sm">

                              {
                                skill.message
                              }

                            </p>



                            <div className="mt-5">


                              <div className="mb-2 flex items-center justify-between gap-4">


                                <span className="app-text-secondary text-sm">

                                  Skill Score

                                </span>


                                <span className="text-sm font-semibold">

                                  {
                                    skill.skillScore
                                  }

                                  /100

                                </span>


                              </div>



                              <div className="app-surface-secondary h-3 overflow-hidden rounded-full">


                                <div

                                  className={`
                                    h-full
                                    rounded-full
                                    transition-all
                                    duration-500
                                    ${
                                      getSkillBarClasses(
                                        skill.skillLevel
                                      )
                                    }
                                  `}

                                  style={{

                                    width:
                                      `${skill.skillScore}%`,

                                  }}

                                />


                              </div>


                            </div>


                          </div>



                          {/* ==================================================
                              SKILL STATISTICS
                          ================================================== */}

                          <div className="grid shrink-0 grid-cols-2 gap-3 sm:grid-cols-4 lg:w-[430px]">


                            <div className="app-surface-secondary rounded-xl p-4">


                              <p className="app-text-muted text-xs">

                                Submissions

                              </p>


                              <p className="mt-2 text-lg font-bold">

                                {
                                  skill.totalSubmissions
                                }

                              </p>


                            </div>



                            <div className="app-surface-secondary rounded-xl p-4">


                              <p className="app-text-muted text-xs">

                                Accepted

                              </p>


                              <p className="mt-2 text-lg font-bold">

                                {
                                  skill.acceptedSubmissions
                                }

                              </p>


                            </div>



                            <div className="app-surface-secondary rounded-xl p-4">


                              <p className="app-text-muted text-xs">

                                Acceptance

                              </p>


                              <p className="mt-2 text-lg font-bold">

                                {
                                  skill.acceptanceRate
                                    .toFixed(1)
                                }

                                %

                              </p>


                            </div>



                            <div className="app-surface-secondary rounded-xl p-4">


                              <p className="app-text-muted text-xs">

                                Mistakes

                              </p>


                              <p className="mt-2 text-lg font-bold">

                                {
                                  skill.totalMistakes
                                }

                              </p>


                            </div>


                          </div>


                        </div>


                      </div>

                    ))
                  }


                </div>


              </section>



              {/* ==================================================
                  EXPLANATION
              ================================================== */}

              <section className="app-surface app-border mt-7 rounded-2xl border p-6">


                <div className="flex items-start gap-4">


                  <div className="app-surface-secondary flex h-11 w-11 shrink-0 items-center justify-center rounded-xl">


                    <FiActivity
                      className="text-blue-500"
                      size={20}
                    />


                  </div>



                  <div>


                    <h2 className="font-semibold">

                      How your Developer Intelligence evolves

                    </h2>


                    <p className="app-text-secondary mt-1 max-w-3xl text-sm leading-6">

                      Your developer profile automatically evolves
                      as you solve problems across more topics.
                      Submission results improve performance signals,
                      AI mistake memory identifies concepts that need
                      additional practice, and hint dependency tracks
                      how independently you solve coding problems.

                    </p>


                  </div>


                </div>


              </section>


            </>

          )
      }


    </div>

  );

}