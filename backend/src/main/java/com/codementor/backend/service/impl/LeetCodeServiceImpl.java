package com.codementor.backend.service.impl;

import com.codementor.backend.client.LeetCodeGraphQLClient;
import com.codementor.backend.dto.LeetCodeProfileResponse;
import com.codementor.backend.mapper.LeetCodeMapper;
import com.codementor.backend.dto.leetcode.graphql.badges.BadgesResponse;
import com.codementor.backend.dto.leetcode.graphql.contest.ContestResponse;
import com.codementor.backend.dto.leetcode.response.BadgeInfo;
import com.codementor.backend.dto.leetcode.response.ContestInfo;
import com.codementor.backend.service.LeetCodeService;

import com.codementor.backend.dto.leetcode.graphql.calendar.CalendarResponse;
import com.codementor.backend.dto.leetcode.response.CalendarInfo;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeetCodeServiceImpl implements LeetCodeService {

    private final LeetCodeGraphQLClient leetCodeGraphQLClient;
    private final LeetCodeMapper leetCodeMapper;

@Override
public LeetCodeProfileResponse getProfile(String username) {

    ContestResponse contestResponse =
            leetCodeGraphQLClient.getContestRanking(username);

    ContestInfo contest =
            leetCodeMapper.mapContest(contestResponse);

    BadgesResponse badgesResponse =
            leetCodeGraphQLClient.getBadges(username);

    List<BadgeInfo> badges =
            leetCodeMapper.mapBadges(badgesResponse);

    CalendarResponse calendarResponse =
            leetCodeGraphQLClient.getCalendar(username);

    CalendarInfo calendar =
            leetCodeMapper.mapCalendar(calendarResponse);

    System.out.println("=========================");
    System.out.println(contest);
    System.out.println("=========================");
    System.out.println(badges);
    System.out.println("=========================");
    System.out.println(calendar);
    System.out.println("=========================");

    throw new RuntimeException("Check Badges");
}

}