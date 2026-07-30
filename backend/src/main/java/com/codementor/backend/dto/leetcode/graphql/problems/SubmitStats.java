package com.codementor.backend.dto.leetcode.graphql.problems;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SubmitStats {

    @JsonProperty("acSubmissionNum")
    private List<SubmissionStat> acSubmissionNum;

    @JsonProperty("totalSubmissionNum")
    private List<SubmissionStat> totalSubmissionNum;

}