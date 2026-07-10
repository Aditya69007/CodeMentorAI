package com.codementor.backend.ai;

public interface GeminiService {

    String analyzeCode(String prompt);

    String chat(String prompt);
}