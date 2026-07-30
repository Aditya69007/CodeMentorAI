package com.codementor.backend.export.controller;

import com.codementor.backend.export.dto.ExportDataResponse;
import com.codementor.backend.export.service.DeveloperReportService;
import com.codementor.backend.export.pdf.PdfExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/export")
@RequiredArgsConstructor
public class ExportController {

    private final DeveloperReportService developerReportService;
    private final PdfExportService pdfExportService;

    @GetMapping("/report")
    public ResponseEntity<ExportDataResponse> getDeveloperReport(
            Authentication authentication) {

        String email = authentication.getName();

        ExportDataResponse report =
                developerReportService.generateDeveloperReport(email);

        return ResponseEntity.ok(report);
    }

    @GetMapping("/report/pdf")
    public ResponseEntity<byte[]> downloadDeveloperReportPdf(
            Authentication authentication
    ) {

        byte[] pdf =
                pdfExportService.generateDeveloperReport(
                        authentication.getName()
                );

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Developer_Report.pdf"
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}