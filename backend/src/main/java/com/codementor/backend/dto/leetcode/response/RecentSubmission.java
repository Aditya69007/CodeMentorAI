package com.codementor.backend.dto.leetcode.response;

import lombok.Data;

@Data
public class RecentSubmission {

    private String id;

    private String title;

    private String titleSlug;

    private Long timestamp;

}