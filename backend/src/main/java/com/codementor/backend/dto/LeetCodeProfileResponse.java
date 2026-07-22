package com.codementor.backend.dto;

import com.codementor.backend.dto.leetcode.response.BadgeInfo;
import com.codementor.backend.dto.leetcode.response.CalendarInfo;
import com.codementor.backend.dto.leetcode.response.ContestInfo;
import com.codementor.backend.dto.leetcode.response.ProblemStats;
import com.codementor.backend.dto.leetcode.response.RecentSubmission;
import com.codementor.backend.dto.leetcode.response.SkillStats;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeetCodeProfileResponse {

    private String username;

    private String profileUrl;

    private ContestInfo contest;

    private ProblemStats problems;

    private CalendarInfo calendar;

    private SkillStats skills;

    private List<BadgeInfo> badges;

    private List<RecentSubmission> recentSubmissions;

}