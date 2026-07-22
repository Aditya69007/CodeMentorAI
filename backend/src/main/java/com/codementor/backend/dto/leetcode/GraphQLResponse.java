package com.codementor.backend.dto.leetcode;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class GraphQLResponse {

    private JsonNode data;

    private JsonNode errors;

}