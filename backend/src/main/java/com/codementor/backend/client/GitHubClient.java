package com.codementor.backend.client;

import com.codementor.backend.dto.GitHubProfileResponse;
import com.codementor.backend.dto.GitHubRepositoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class GitHubClient {

    private static final String GITHUB_API =
            "https://api.github.com";

    private final RestClient restClient = RestClient.create();

    public GitHubProfileResponse getUserProfile(String username) {

        try {

            return restClient.get()
                    .uri(GITHUB_API + "/users/" + username)
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

    public GitHubRepositoryResponse[] getRepositories(String username) {

        try {

            return restClient.get()
                    .uri(GITHUB_API + "/users/" + username + "/repos")
                    .retrieve()
                    .body(GitHubRepositoryResponse[].class);

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