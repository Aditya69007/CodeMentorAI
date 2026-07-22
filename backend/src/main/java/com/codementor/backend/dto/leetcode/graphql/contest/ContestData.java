package com.codementor.backend.dto.leetcode.graphql.contest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ContestData {

    @JsonProperty("userContestRanking")
    private ContestRanking contestRanking;

    @JsonProperty("userContestRankingHistory")
    private List<ContestHistory> contestHistory;

}