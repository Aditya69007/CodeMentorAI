package com.codementor.backend.dto.leetcode.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecentSubmission {

    private String id;

    private String title;

    private String titleSlug;

    private String timestamp;

}