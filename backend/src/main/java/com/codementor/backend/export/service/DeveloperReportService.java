package com.codementor.backend.export.service;

import com.codementor.backend.export.dto.ExportDataResponse;

public interface DeveloperReportService {

    ExportDataResponse generateDeveloperReport(String email);

}