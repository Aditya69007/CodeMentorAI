package com.codementor.backend.dto.leetcode.analytics;

import com.codementor.backend.dto.leetcode.response.AnalyticsInfo;
import com.codementor.backend.dto.leetcode.response.CalendarInfo;
import com.codementor.backend.dto.leetcode.response.ContestInfo;
import com.codementor.backend.dto.leetcode.response.ProblemStats;
import com.codementor.backend.dto.leetcode.response.SkillStats;

public interface LeetCodeAnalyticsService {

    AnalyticsInfo generateAnalytics(

            ContestInfo contest,

            CalendarInfo calendar,

            ProblemStats problems,

            SkillStats skills

    );

}