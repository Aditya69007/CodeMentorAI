package com.codementor.backend.dto.leetcode.graphql.recent;

import lombok.Data;

@Data
public class RecentSubmissionItem {

    private String id;

    private String title;

    private String titleSlug;

    private String timestamp;

}