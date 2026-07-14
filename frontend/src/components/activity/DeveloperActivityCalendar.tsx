import {
  useMemo,
  useState,
  type ReactNode,
} from "react";

import {
  FiActivity,
  FiCalendar,
  FiGitCommit,
  FiZap,
} from "react-icons/fi";

import type {
  DailyActivity,
  DeveloperActivity,
} from "../../types/developerActivity";


interface DeveloperActivityCalendarProps {
  data: DeveloperActivity;
}


/* =========================================================
   INTERNAL CALENDAR DAY TYPE
========================================================= */

interface CalendarDay {
  date: Date;
  activity?: DailyActivity;
}


/* =========================================================
   ACTIVITY COLOR
========================================================= */

const getActivityClass = (
  submissionCount: number
) => {

  if (submissionCount === 0) {
    return "bg-[var(--surface-secondary)]";
  }

  if (submissionCount <= 2) {
    return "bg-emerald-900";
  }

  if (submissionCount <= 4) {
    return "bg-emerald-700";
  }

  if (submissionCount <= 7) {
    return "bg-emerald-500";
  }

  return "bg-emerald-400";
};


/* =========================================================
   FORMAT DATE
========================================================= */

const formatDate = (
  date: Date
) => {

  return date.toLocaleDateString(
    undefined,
    {
      day: "numeric",
      month: "short",
      year: "numeric",
    }
  );
};


/* =========================================================
   DATE KEY
========================================================= */

const getDateKey = (
  date: Date
) => {

  const year =
    date.getFullYear();

  const month =
    String(
      date.getMonth() + 1
    ).padStart(
      2,
      "0"
    );

  const day =
    String(
      date.getDate()
    ).padStart(
      2,
      "0"
    );

  return `${year}-${month}-${day}`;
};


/* =========================================================
   MAIN COMPONENT
========================================================= */

export default function DeveloperActivityCalendar({
  data,
}: DeveloperActivityCalendarProps) {

  const [
    hoveredDay,
    setHoveredDay,
  ] =
    useState<CalendarDay | null>(
      null
    );


  /* =======================================================
     CREATE ACTIVITY MAP
  ======================================================= */

  const activityMap =
    useMemo(() => {

      const map =
        new Map<
          string,
          DailyActivity
        >();


      data.activity.forEach(
        (activity) => {

          map.set(
            activity.date,
            activity
          );

        }
      );


      return map;

    }, [data.activity]);


  /* =======================================================
     CREATE 1 YEAR CALENDAR

     IMPORTANT:

     We start from Sunday.

     We end on Saturday.

     This creates the same vertical week layout
     used by GitHub / LeetCode activity calendars.
  ======================================================= */

  const weeks =
    useMemo(() => {

      const today =
        new Date();


      today.setHours(
        0,
        0,
        0,
        0
      );


      /* -----------------------------------------------
         END DATE = END OF CURRENT WEEK
      ----------------------------------------------- */

      const endDate =
        new Date(today);


      endDate.setDate(
        endDate.getDate()
          +
        (
          6 - endDate.getDay()
        )
      );


      /* -----------------------------------------------
         START DATE = APPROXIMATELY ONE YEAR AGO
      ----------------------------------------------- */

      const startDate =
        new Date(endDate);


      startDate.setDate(
        startDate.getDate()
          -
        (52 * 7)
          -
        6
      );


      /* -----------------------------------------------
         BUILD WEEKS
      ----------------------------------------------- */

      const result:
        CalendarDay[][] = [];


      const currentDate =
        new Date(startDate);


      while (
        currentDate <= endDate
      ) {

        const week:
          CalendarDay[] = [];


        for (
          let dayIndex = 0;
          dayIndex < 7;
          dayIndex++
        ) {

          const date =
            new Date(
              currentDate
            );


          const dateKey =
            getDateKey(date);


          week.push({

            date,

            activity:
              activityMap.get(
                dateKey
              ),

          });


          currentDate.setDate(
            currentDate.getDate()
              +
            1
          );

        }


        result.push(
          week
        );

      }


      return result;

    }, [activityMap]);


  /* =======================================================
     MONTH LABELS
  ======================================================= */

  const monthLabels =
    useMemo(() => {

      const labels: {
        name: string;
        weekIndex: number;
      }[] = [];


      let previousMonth =
        -1;


      weeks.forEach(
        (
          week,
          weekIndex
        ) => {

          const firstDay =
            week[0]?.date;


          if (!firstDay) {
            return;
          }


          const month =
            firstDay.getMonth();


          if (
            month !==
            previousMonth
          ) {

            labels.push({

              name:
                firstDay
                  .toLocaleDateString(
                    undefined,
                    {
                      month:
                        "short",
                    }
                  ),

              weekIndex,

            });


            previousMonth =
              month;

          }

        }
      );


      return labels;

    }, [weeks]);


  /* =======================================================
     RENDER
  ======================================================= */

  return (

    <section
      className="
        app-surface
        app-border
        overflow-hidden
        rounded-2xl
        border
      "
    >

      {/* =================================================
          HEADER
      ================================================= */}

      <div
        className="
          app-border

          flex
          flex-col

          gap-4

          border-b

          px-5
          py-5

          sm:flex-row
          sm:items-center
          sm:justify-between

          lg:px-6
        "
      >

        <div>

          <div
            className="
              flex
              items-center
              gap-2
            "
          >

            <FiActivity
              className="
                text-emerald-500
              "
              size={18}
            />


            <h2
              className="
                app-text-primary

                text-base
                font-semibold
              "
            >

              Coding Activity

            </h2>

          </div>


          <p
            className="
              app-text-secondary

              mt-1

              text-sm
            "
          >

            Your coding consistency
            over the last year.

          </p>

        </div>


        <div
          className="
            app-text-secondary

            text-sm
          "
        >

          <span
            className="
              app-text-primary
              font-semibold
            "
          >

            {data.totalSubmissions}

          </span>

          {" "}

          submissions in the past year

        </div>

      </div>


      {/* =================================================
          ACTIVITY STATS
      ================================================= */}

      <div
        className="
          app-border

          grid

          grid-cols-2

          border-b

          md:grid-cols-4
        "
      >

        <CompactActivityStat

          icon={
            <FiZap
              size={16}
            />
          }

          label="Current Streak"

          value={

            `${data.currentStreak} ${
              data.currentStreak === 1
                ? "day"
                : "days"
            }`

          }

        />


        <CompactActivityStat

          icon={
            <FiActivity
              size={16}
            />
          }

          label="Longest Streak"

          value={

            `${data.longestStreak} ${
              data.longestStreak === 1
                ? "day"
                : "days"
            }`

          }

        />


        <CompactActivityStat

          icon={
            <FiCalendar
              size={16}
            />
          }

          label="Active Days"

          value={
            data.totalActiveDays
          }

        />


        <CompactActivityStat

          icon={
            <FiGitCommit
              size={16}
            />
          }

          label="
            Submissions
          "

          value={
            data.totalSubmissions
          }

        />

      </div>


      {/* =================================================
          CALENDAR SECTION
      ================================================= */}

      <div
        className="
          px-5
          py-5

          lg:px-6
        "
      >

        <div
          className="
            overflow-x-auto
            pb-2
          "
        >

          <div
            className="
              min-w-[850px]
            "
          >

            {/* ===========================================
                MONTH LABELS
            =========================================== */}

            <div
              className="
                app-text-secondary

                mb-2

                grid

                text-xs
              "

              style={{

                gridTemplateColumns:
                  "40px repeat(53, minmax(11px, 1fr))",

              }}
            >

              <div />


              {monthLabels.map(
                (month) => (

                  <span

                    key={
                      `${month.name}-${month.weekIndex}`
                    }

                    style={{

                      gridColumnStart:
                        month.weekIndex
                          +
                        2,

                    }}

                    className="
                      whitespace-nowrap
                    "
                  >

                    {month.name}

                  </span>

                )
              )}

            </div>


            {/* ===========================================
                CALENDAR BODY
            =========================================== */}

            <div
              className="
                flex
                gap-3
              "
            >

              {/* =========================================
                  WEEKDAY LABELS
              ========================================= */}

              <div
                className="
                  app-text-muted

                  grid

                  w-7

                  shrink-0

                  grid-rows-7

                  gap-[3px]

                  text-[10px]
                "
              >

                <div
                  className="
                    h-[13px]
                  "
                />


                <div
                  className="
                    flex
                    h-[13px]
                    items-center
                  "
                >

                  Mon

                </div>


                <div
                  className="
                    h-[13px]
                  "
                />


                <div
                  className="
                    flex
                    h-[13px]
                    items-center
                  "
                >

                  Wed

                </div>


                <div
                  className="
                    h-[13px]
                  "
                />


                <div
                  className="
                    flex
                    h-[13px]
                    items-center
                  "
                >

                  Fri

                </div>


                <div
                  className="
                    h-[13px]
                  "
                />

              </div>


              {/* =========================================
                  ACTIVITY GRID
              ========================================= */}

              <div
                className="
                  grid

                  flex-1

                  gap-[3px]
                "

                style={{

                  gridTemplateColumns:
                    `repeat(${weeks.length}, minmax(11px, 1fr))`,

                }}
              >

                {weeks.map(
                  (
                    week,
                    weekIndex
                  ) => (

                    <div

                      key={
                        weekIndex
                      }

                      className="
                        grid

                        grid-rows-7

                        gap-[3px]
                      "
                    >

                      {week.map(
                        (
                          calendarDay
                        ) => {

                          const count =

                            calendarDay
                              .activity
                              ?.submissionCount
                                ??
                            0;


                          return (

                            <button

                              type="button"

                              key={
                                calendarDay
                                  .date
                                  .toISOString()
                              }

                              aria-label={
                                `${formatDate(
                                  calendarDay.date
                                )}: ${count} submissions`
                              }

                              onMouseEnter={
                                () =>
                                  setHoveredDay(
                                    calendarDay
                                  )
                              }

                              onMouseLeave={
                                () =>
                                  setHoveredDay(
                                    null
                                  )
                              }

                              onFocus={
                                () =>
                                  setHoveredDay(
                                    calendarDay
                                  )
                              }

                              onBlur={
                                () =>
                                  setHoveredDay(
                                    null
                                  )
                              }

                              className={`

                                aspect-square

                                w-full

                                min-w-[11px]

                                max-w-[14px]

                                rounded-[3px]

                                border

                                border-black/5

                                transition-all

                                duration-150

                                hover:z-10

                                hover:scale-125

                                hover:ring-1

                                hover:ring-[var(--text-muted)]

                                focus:outline-none

                                focus:ring-1

                                focus:ring-[var(--text-muted)]

                                ${getActivityClass(
                                  count
                                )}

                              `}
                            />

                          );

                        }
                      )}

                    </div>

                  )
                )}

              </div>

            </div>


            {/* ===========================================
                FOOTER
            =========================================== */}

            <div
              className="
                mt-4

                flex

                min-h-[22px]

                items-center

                justify-between

                gap-4
              "
            >

              {/* =========================================
                  HOVER INFORMATION
              ========================================= */}

              <div
                className="
                  app-text-secondary

                  text-xs
                "
              >

                {hoveredDay ? (

                  <>

                    <span
                      className="
                        app-text-primary
                        font-medium
                      "
                    >

                      {
                        hoveredDay
                          .activity
                          ?.submissionCount
                            ??
                        0
                      }

                      {" "}

                      {
                        (
                          hoveredDay
                            .activity
                            ?.submissionCount
                              ??
                          0
                        ) === 1

                          ? "submission"

                          : "submissions"
                      }

                    </span>


                    {" on "}


                    {
                      formatDate(
                        hoveredDay.date
                      )
                    }


                    {hoveredDay.activity && (

                      <>

                        {" • "}

                        {
                          hoveredDay
                            .activity
                            .acceptedSubmissionCount
                        }

                        {" accepted"}

                      </>

                    )}

                  </>

                ) : (

                  <span>

                    Hover over the activity graph
                    to view details

                  </span>

                )}

              </div>


              {/* =========================================
                  LEGEND
              ========================================= */}

              <div
                className="
                  app-text-muted

                  flex

                  shrink-0

                  items-center

                  gap-2

                  text-xs
                "
              >

                <span>
                  Less
                </span>


                {[0, 1, 3, 5, 8].map(
                  (count) => (

                    <div

                      key={
                        count
                      }

                      className={`

                        h-[12px]

                        w-[12px]

                        rounded-[2px]

                        ${getActivityClass(
                          count
                        )}

                      `}
                    />

                  )
                )}


                <span>
                  More
                </span>

              </div>

            </div>

          </div>

        </div>

      </div>

    </section>

  );

}


/* =========================================================
   COMPACT ACTIVITY STAT
========================================================= */

interface CompactActivityStatProps {

  icon: ReactNode;

  label: string;

  value: ReactNode;

}


function CompactActivityStat({

  icon,

  label,

  value,

}: CompactActivityStatProps) {

  return (

    <div
      className="
        app-surface

        app-border

        border-r

        px-5

        py-4

        last:border-r-0
      "
    >

      <div
        className="
          app-text-secondary

          flex

          items-center

          gap-2

          text-xs
        "
      >

        {icon}

        {label}

      </div>


      <div
        className="
          app-text-primary

          mt-1.5

          text-lg

          font-semibold
        "
      >

        {value}

      </div>

    </div>

  );

}