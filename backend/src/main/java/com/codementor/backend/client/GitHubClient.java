package com.codementor.backend.client;

import com.codementor.backend.dto.GitHubProfileResponse;
import com.codementor.backend.dto.GitHubRepositoryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@Component
public class GitHubClient {

    private static final String GITHUB_API =
            "https://api.github.com";

    @Value("${GITHUB_TOKEN}")
    private String githubToken;

    private final RestClient restClient = RestClient.create();

    public GitHubProfileResponse getUserProfile(String username) {

        try {

            return restClient.get()
                    .uri(GITHUB_API + "/users/" + username)
                    .header("Authorization", "Bearer " + githubToken)
                    .header("Accept", "application/vnd.github+json")
                    .retrieve()
                    .onStatus(
                            HttpStatusCode::isError,
                            (request, response) -> {
                                throw new ResponseStatusException(
                                        response.getStatusCode(),
                                        "GitHub user not found"
                                );
                            }
                    )
                    .body(GitHubProfileResponse.class);

        } catch (ResponseStatusException ex) {

            throw ex;

        } catch (Exception ex) {

            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(500),
                    "Unable to connect to GitHub"
            );

        }
    }

    public List<GitHubRepositoryResponse> getRepositories(
            String username
    ) {

        try {

            GitHubRepositoryResponse[] repositories =
                    restClient.get()
                            .uri(
                                    GITHUB_API
                                            + "/users/"
                                            + username
                                            + "/repos"
                            )
                            .header(
                                    "Authorization",
                                    "Bearer " + githubToken
                            )
                            .header(
                                    "Accept",
                                    "application/vnd.github+json"
                            )
                            .retrieve()
                            .body(
                                    GitHubRepositoryResponse[].class
                            );

            return repositories == null
                    ? List.of()
                    : Arrays.asList(repositories);

        } catch (ResponseStatusException ex) {

            throw ex;

        } catch (Exception ex) {

            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(500),
                    "Unable to fetch GitHub repositories"
            );

        }
    }
}