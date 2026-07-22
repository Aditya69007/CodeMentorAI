package com.codementor.backend.dto.leetcode.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BadgeInfo {

    private String id;

    private String displayName;

    private String icon;

    private String creationDate;

    private String category;

}