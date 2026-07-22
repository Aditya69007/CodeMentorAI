package com.codementor.backend.dto.leetcode.graphql.badges;

import lombok.Data;

@Data
public class Badge {

    private String id;

    private String displayName;

    private String icon;

    private String creationDate;

    private String category;

}