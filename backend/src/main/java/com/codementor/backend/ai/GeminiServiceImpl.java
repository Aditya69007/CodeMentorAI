package com.codementor.backend.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeminiServiceImpl implements GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper;


    @Override
    public String analyzeCode(String prompt) {

        String url = buildUrl();

        Map<String, Object> generationConfig = Map.of(
                "responseMimeType", "application/json"
        );

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of(
                                "parts", List.of(
                                        Map.of(
                                                "text", prompt
                                        )
                                )
                        )
                ),
                "generationConfig", generationConfig
        );

        return callGemini(url, requestBody);
    }


    @Override
    public String chat(String prompt) {

        String url = buildUrl();

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of(
                                "parts", List.of(
                                        Map.of(
                                                "text", prompt
                                        )
                                )
                        )
                )
        );

        return callGemini(url, requestBody);
    }


    private String buildUrl() {

        return "https://generativelanguage.googleapis.com/v1beta/models/"
                + "gemini-2.5-flash:generateContent?key="
                + apiKey;
    }


    private String callGemini(
            String url,
            Map<String, Object> requestBody) {

        try {

            RestClient restClient = RestClient.create();

            String response = restClient
                    .post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(requestBody)
                    .retrieve()
                    .body(String.class);

            JsonNode root =
                    objectMapper.readTree(response);

            return root
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

        } catch (Exception exception) {

            throw new RuntimeException(
                    "Failed to get AI response: "
                            + exception.getMessage()
            );
        }
    }
}