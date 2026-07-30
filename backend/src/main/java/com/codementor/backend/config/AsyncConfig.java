package com.codementor.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class AsyncConfig {

    @Bean
    public Executor leetCodeExecutor() {

        return Executors.newFixedThreadPool(6);

    }

}