package com.codementor.backend.controller;

import com.codementor.backend.publicportfolio.dto.PublicPortfolioResponse;
import com.codementor.backend.publicportfolio.service.PublicPortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/v1/portfolio")
@RequiredArgsConstructor
public class PublicPortfolioController {

    private final PublicPortfolioService publicPortfolioService;

    @GetMapping("/{username}")
    public PublicPortfolioResponse getPortfolio(
            @PathVariable String username
    ) {

        return publicPortfolioService
                .getPublicPortfolioByUsername(username);

    }

    @PostMapping("/refresh/{username}")
    public void refreshPortfolio(
            @PathVariable String username
    ) {

        publicPortfolioService.refreshPortfolio(username);
    }

    @GetMapping("/me")
    public PublicPortfolioResponse getMyPortfolio(
            Authentication authentication
    ) {

        return publicPortfolioService
                .getPublicPortfolio(authentication.getName());

    }

}