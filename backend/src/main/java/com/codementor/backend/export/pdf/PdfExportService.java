package com.codementor.backend.export.pdf;

public interface PdfExportService {

    byte[] generateDeveloperReport(String email);

}