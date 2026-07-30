package com.codementor.backend.dto.leetcode.graphql.recent;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RecentSubmissionData {

    @JsonProperty("recentAcSubmissionList")
    private List<RecentSubmissionItem> recentAcSubmissionList;

}