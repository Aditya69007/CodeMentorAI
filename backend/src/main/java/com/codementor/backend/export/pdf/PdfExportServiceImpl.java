package com.codementor.backend.export.pdf;

import com.codementor.backend.dto.ConceptGrowthResponse;
import com.codementor.backend.dto.DeveloperMistakeProfileResponse;
import com.codementor.backend.dto.PersonalizedLearningPlanResponse;
import com.codementor.backend.dto.PersonalizedRevisionPlanResponse;
import com.codementor.backend.export.dto.ExportDataResponse;
import com.codementor.backend.export.service.DeveloperReportService;

import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import java.io.InputStream;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.ListItem;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.Element;
import java.awt.Color;

import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class PdfExportServiceImpl implements PdfExportService {

    private final DeveloperReportService developerReportService;

    @Override
    public byte[] generateDeveloperReport(String email) {

        ExportDataResponse report =
                developerReportService.generateDeveloperReport(email);

        try {

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

                Document document = new Document(
                        PageSize.A4,
                        40,
                        40,
                        70,
                        60
                );

            PdfWriter writer = PdfWriter.getInstance(document, outputStream);

            writer.setPageEvent(new PdfPageEvent());

            document.open();

            document.addTitle("CodeMentorAI Developer Report");
            document.addAuthor("CodeMentorAI");
            document.addCreator("CodeMentorAI Backend");
            document.addSubject("Developer Growth Report");
            document.addKeywords("CodeMentorAI, Developer, AI, LeetCode, GitHub");

                Font titleFont =
                        FontFactory.getFont(
                                FontFactory.HELVETICA_BOLD,
                                24,
                                new java.awt.Color(25, 55, 109)
                        );

                Font normalFont =
                        FontFactory.getFont(
                                FontFactory.HELVETICA,
                                11,
                                java.awt.Color.DARK_GRAY
                        );

                Font sectionFont =
                        FontFactory.getFont(
                                FontFactory.HELVETICA_BOLD,
                                16,
                                new java.awt.Color(33, 97, 140)
                        );

                addCoverPage(
                        document,
                        report,
                        titleFont,
                        normalFont
                );

                addTitle(
                        document,
                        titleFont
                );

                addExecutiveSummary(
                        document,
                        report,
                        sectionFont,
                        normalFont
                );

            addProfile(
                    document,
                    report,
                    sectionFont,
                    normalFont
            );

            
            addGithub(
                    document,
                    report,
                    sectionFont,
                    normalFont
            );

            addLeetCode(
                    document,
                    report,
                    sectionFont,
                    normalFont
            );

            addGrowthReport(
                    document,
                    report,
                    sectionFont,
                    normalFont
            );

            addInterview(
                    document,
                    report,
                    sectionFont,
                    normalFont
            );

            addLearningPlan(
                    document,
                    report,
                    sectionFont,
                    normalFont
            );

            addRevisionPlan(
                    document,
                    report,
                    sectionFont,
                    normalFont
            );

            addMistakeMemory(
                    document,
                    report,
                    sectionFont,
                    normalFont
            );

            addAiLearning(
                    document,
                    report,
                    sectionFont,
                    normalFont
            );


            document.close();
            return outputStream.toByteArray();

        } catch (Exception exception) {
            
            throw new RuntimeException(
                "Failed to generate PDF.",
                exception
            );

        }

    }

    private void addTitle(
            Document document,
            Font titleFont
    ) throws Exception {

        document.add(
                new Paragraph(
                        "CodeMentorAI Developer Report",
                        titleFont
                )
        );

        document.add(new Paragraph(" "));

    }

        private void addCoverLogo(Document document) throws Exception {

        InputStream inputStream =
                getClass()
                        .getClassLoader()
                        .getResourceAsStream("static/images/codementor-logo.png");

        if (inputStream == null) {
                return;
        }

        byte[] bytes = inputStream.readAllBytes();

        Image logo = Image.getInstance(bytes);

        logo.scaleToFit(280, 120);

        logo.setAlignment(Image.ALIGN_CENTER);

        document.add(logo);

        document.add(new Paragraph(" "));
        }

        private void addCoverPage(
                Document document,
                ExportDataResponse report,
                Font titleFont,
                Font normalFont
        ) throws Exception {

        addCoverLogo(document);

        Paragraph subtitle = new Paragraph(
                "AI Powered Developer Report",
                FontFactory.getFont(
                        FontFactory.HELVETICA,
                        16,
                        java.awt.Color.GRAY
                )
        );

        subtitle.setAlignment(Element.ALIGN_CENTER);

        document.add(subtitle);

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        addFieldTable(
                document,
                new String[][]{
                        {
                                "Developer",
                                report.getProfile().getFirstName()
                                        + " "
                                        + report.getProfile().getLastName()
                        },
                        {
                                "Email",
                                report.getProfile().getEmail()
                        },
                        {
                                "GitHub",
                                report.getGithub() != null
                                        ? report.getGithub().getUsername()
                                        : "-"
                        },
                        {
                                "LeetCode",
                                report.getLeetcode() != null
                                        ? report.getLeetcode().getUsername()
                                        : "-"
                        },
                        {
                                "Role",
                                report.getProfile().getRole().toString()
                        }
                },
                normalFont
        );

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        Paragraph footer = new Paragraph(
                "Generated by CodeMentorAI AI Engine",
                FontFactory.getFont(
                        FontFactory.HELVETICA_OBLIQUE,
                        12,
                        java.awt.Color.GRAY
                )
        );

        footer.setAlignment(Element.ALIGN_CENTER);

        document.add(footer);

        document.newPage();
        }

        private void addExecutiveSummary(
                Document document,
                ExportDataResponse report,
                Font sectionFont,
                Font normalFont
        ) throws Exception {

        addSectionHeader(
                document,
                "Executive Summary",
                sectionFont
        );

        addFieldTable(
                document,
                new String[][]{

                        {
                                "Developer",
                                report.getProfile().getFirstName()
                                        + " "
                                        + report.getProfile().getLastName()
                        },

                        {
                                "Overall Growth Score",
                                report.getGrowthReport() != null
                                        ? String.valueOf(report.getGrowthReport().getOverallGrowthScore())
                                        : "-"
                        },

                        {
                                "Interview Readiness",
                                report.getInterview() != null
                                        ? String.valueOf(report.getInterview().getOverallReadinessScore())
                                        : "-"
                        },

                        {
                                "Developer Level",
                                report.getGrowthReport() != null
                                        ? report.getGrowthReport().getDeveloperLevel().toString()
                                        : "-"
                        },

                        {
                                "Weakest Concept",
                                report.getMistakeMemory() != null
                                        ? report.getMistakeMemory().getWeakestConcept()
                                        : "-"
                        },

                        {
                                "Most Common Mistake",
                                report.getMistakeMemory() != null
                                        && report.getMistakeMemory().getMostCommonMistake() != null
                                        ? report.getMistakeMemory().getMostCommonMistake().name()
                                        : "-"
                        }

                },
                normalFont
        );

        document.add(new Paragraph(" "));

        document.add(
                new Paragraph(
                        "AI Recommendation",
                        sectionFont
                )
        );

        String recommendation =
                report.getGrowthReport() != null
                        ? report.getGrowthReport().getRecommendedNextAction()
                        : "Keep practicing consistently.";

        document.add(
                new Paragraph(
                        recommendation,
                        normalFont
                )
        );

        document.add(new Paragraph(" "));

        }

        private void addSectionHeader(
                Document document,
                String title,
                Font sectionFont
        ) throws Exception {

        document.add(new Paragraph(" "));

        Paragraph heading = new Paragraph(title, sectionFont);
        heading.setSpacingAfter(6);

        document.add(heading);

        PdfPTable divider = new PdfPTable(1);
        divider.setWidthPercentage(100);

        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderColor(new Color(33, 97, 140));
        cell.setBorderWidth(2f);
        cell.setFixedHeight(3f);
        cell.setPadding(0);

        divider.addCell(cell);

        document.add(divider);

        document.add(new Paragraph(" "));

        }


    private void addFieldTable(
            Document document,
            String[][] data,
            Font normalFont
    ) throws Exception {

        PdfPTable table = new PdfPTable(2);
        table.setKeepTogether(true);
        table.setWidthPercentage(100);
        table.setSpacingBefore(8);
        table.setSpacingAfter(16);

        table.setWidths(new float[]{2f, 4f});

        for (String[] row : data) {

        PdfPCell keyCell = new PdfPCell(new Phrase(
                row[0],
                FontFactory.getFont(
                        FontFactory.HELVETICA_BOLD,
                        11,
                        Color.WHITE
                )
        ));

        keyCell.setBackgroundColor(new Color(33, 97, 140));
        keyCell.setBorderWidthBottom(0.5f);
        keyCell.setPaddingTop(6);
        keyCell.setPaddingBottom(6);
        keyCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        keyCell.setPadding(6);

        PdfPCell valueCell = new PdfPCell(
                new Phrase(
                        row[1] != null ? row[1] : "-",
                        normalFont
                )
        );

        valueCell.setBackgroundColor(Color.WHITE);
        valueCell.setBorderWidthBottom(0.5f);
        valueCell.setPaddingTop(6);
        valueCell.setPaddingBottom(6);
        valueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        valueCell.setPadding(6);

        // ✅ ADD THESE LINES HERE
        keyCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        valueCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        keyCell.setBorderColor(new Color(210, 210, 210));
        valueCell.setBorderColor(new Color(210, 210, 210));

        table.addCell(keyCell);
        table.addCell(valueCell);
        }
        document.add(table);
    }

    private void addProfile(
            Document document,
            ExportDataResponse report,
            Font sectionFont,
            Font normalFont
    ) throws Exception {

        addSectionHeader(
                document,
                "Profile",
                sectionFont
        );

        addFieldTable(
                document,
                new String[][]{
                        {
                                "Name",
                                report.getProfile().getFirstName()
                                        + " "
                                        + report.getProfile().getLastName()
                        },
                        {
                                "Email",
                                report.getProfile().getEmail()
                        },
                        {
                                "Role",
                                report.getProfile().getRole().toString()
                        },
                        {
                                "Provider",
                                report.getProfile().getProvider().toString()
                        }
                },
                normalFont
        );

    }

    private void addGithub(
            Document document,
            ExportDataResponse report,
            Font sectionFont,
            Font normalFont
    ) throws Exception {

        if (report.getGithub() == null) {
            return;
        }

        addSectionHeader(
                document,
                "GitHub",
                sectionFont
        );

        addFieldTable(
                document,
                new String[][]{
                        {
                                "Username",
                                report.getGithub().getUsername()
                        },
                        {
                                "Name",
                                report.getGithub().getName()
                        },
                        {
                                "Repositories",
                                String.valueOf(report.getGithub().getPublicRepositories())
                        },
                        {
                                "Followers",
                                String.valueOf(report.getGithub().getFollowers())
                        },
                        {
                                "Following",
                                String.valueOf(report.getGithub().getFollowing())
                        },
                        {
                                "Profile",
                                report.getGithub().getProfileUrl()
                        }
                },
                normalFont
        );

    }

    private void addLeetCode(
            Document document,
            ExportDataResponse report,
            Font sectionFont,
            Font normalFont
    ) throws Exception {

        if (report.getLeetcode() == null) {
            return;
        }

        addSectionHeader(
                document,
                "LeetCode",
                sectionFont
        );

        addFieldTable(
                document,
                new String[][]{
                        {
                                "Username",
                                report.getLeetcode().getUsername()
                        },
                        {
                                "Contest Rating",
                                String.format(
                                        "%.2f",
                                        report.getLeetcode()
                                                .getContest()
                                                .getRating()
                                )
                        },
                        {
                                "Global Rank",
                                String.valueOf(
                                        report.getLeetcode()
                                                .getContest()
                                                .getGlobalRanking()
                                )
                        },
                        {
                                "Problems Solved",
                                String.valueOf(
                                        report.getLeetcode()
                                                .getProblems()
                                                .getTotalSolved()
                                )
                        },
                        {
                                "Acceptance Rate",
                                String.format(
                                        "%.2f%%",
                                        report.getLeetcode()
                                                .getProblems()
                                                .getAcceptanceRate()
                                )
                        },
                        {
                                "Developer Score",
                                String.format(
                                        "%.2f",
                                        report.getLeetcode()
                                                .getAnalytics()
                                                .getDeveloperScore()
                                )
                        }
                },
                normalFont
        );

    }

    private void addGrowthReport(
            Document document,
            ExportDataResponse report,
            Font sectionFont,
            Font normalFont
    ) throws Exception {

        if (report.getGrowthReport() == null) {
            return;
        }

        addSectionHeader(
                document,
                "Growth Report",
                sectionFont
        );

        addFieldTable(
                document,
                new String[][]{
                        {
                                "Overall Growth Score",
                                String.valueOf(report.getGrowthReport().getOverallGrowthScore())
                        },
                        {
                                "Developer Level",
                                report.getGrowthReport().getDeveloperLevel().toString()
                        },
                        {
                                "Hint Dependency Score",
                                String.valueOf(report.getGrowthReport().getHintDependencyScore())
                        },
                        {
                                "Independent Solve Rate",
                                report.getGrowthReport().getIndependentSolveRate() + "%"
                        }
                },
                normalFont
        );

        document.add(
                new Paragraph(
                        "Growth Summary",
                        sectionFont
                )
        );

        document.add(
                new Paragraph(
                        report.getGrowthReport().getGrowthSummary(),
                        normalFont
                )
        );

        document.add(new Paragraph(" "));

        document.add(
                new Paragraph(
                        "Recommended Action",
                        sectionFont
                )
        );

        document.add(
                new Paragraph(
                        report.getGrowthReport().getRecommendedNextAction(),
                        normalFont
                )
        );

        document.add(new Paragraph(" "));

    }

    private void addInterview(
            Document document,
            ExportDataResponse report,
            Font sectionFont,
            Font normalFont
    ) throws Exception {

        if (report.getInterview() == null) {
            return;
        }

        addSectionHeader(
                document,
                "Interview Readiness",
                sectionFont
        );

        addFieldTable(
                document,
                new String[][]{
                        {
                                "Interview Level",
                                report.getInterview().getInterviewLevel().toString()
                        },
                        {
                                "Developer Level",
                                report.getInterview().getDeveloperLevel().toString()
                        },
                        {
                                "Overall Readiness Score",
                                String.valueOf(report.getInterview().getOverallReadinessScore())
                        },
                        {
                                "Hint Dependency Score",
                                String.valueOf(report.getInterview().getHintDependencyScore())
                        },
                        {
                                "Independent Solve Rate",
                                report.getInterview().getIndependentSolveRate() + "%"
                        }
                },
                normalFont
        );

        document.add(
                new Paragraph(
                        "Interview Strategy",
                        sectionFont
                )
        );

        document.add(
                new Paragraph(
                        report.getInterview().getInterviewStrategy(),
                        normalFont
                )
        );

        document.add(new Paragraph(" "));

        document.add(
                new Paragraph(
                        "Recommended Action",
                        sectionFont
                )
        );

        document.add(
                new Paragraph(
                        report.getInterview().getRecommendedAction(),
                        normalFont
                )
        );

        document.add(new Paragraph(" "));

    }

    private void addLearningPlan(
            Document document,
            ExportDataResponse report,
            Font sectionFont,
            Font normalFont
    ) throws Exception {

        if (report.getLearningPlan() == null) {
            return;
        }

        PersonalizedLearningPlanResponse learningPlan =
                report.getLearningPlan();

        addSectionHeader(
                document,
                "Personalized Learning Plan",
                sectionFont
        );

        addFieldTable(
                document,
                new String[][]{
                        {
                                "Learning Level",
                                learningPlan.getLearningLevel()
                        },
                        {
                                "Overall Readiness",
                                String.valueOf(learningPlan.getOverallReadinessScore())
                        },
                        {
                                "Hint Dependency Score",
                                String.valueOf(learningPlan.getHintDependencyScore())
                        },
                        {
                                "Independent Solve Rate",
                                learningPlan.getIndependentSolveRate() + "%"
                        }
                },
                normalFont
        );

        if (learningPlan.getMessage() != null) {
            document.add(new Paragraph("Message", sectionFont));
            document.add(new Paragraph(
                    learningPlan.getMessage(),
                    normalFont
            ));
            document.add(new Paragraph(" "));
        }

        if (learningPlan.getWeakConcepts() != null &&
                !learningPlan.getWeakConcepts().isEmpty()) {

            document.add(new Paragraph(
                    "Weak Concepts",
                    sectionFont
            ));

            com.lowagie.text.List weakList =
                    new com.lowagie.text.List(com.lowagie.text.List.UNORDERED);

            learningPlan.getWeakConcepts()
                    .forEach(item -> weakList.add(new ListItem(item, normalFont)));

            document.add(weakList);
            document.add(new Paragraph(" "));
        }

        if (learningPlan.getRevisionPriorities() != null &&
                !learningPlan.getRevisionPriorities().isEmpty()) {

            document.add(new Paragraph(
                    "Revision Priorities",
                    sectionFont
            ));

            com.lowagie.text.List revisionList =
                    new com.lowagie.text.List(com.lowagie.text.List.UNORDERED);

            learningPlan.getRevisionPriorities()
                    .forEach(item -> revisionList.add(new ListItem(item, normalFont)));

            document.add(revisionList);
            document.add(new Paragraph(" "));
        }

        if (learningPlan.getStrengths() != null &&
                !learningPlan.getStrengths().isEmpty()) {

            document.add(new Paragraph(
                    "Strengths",
                    sectionFont
            ));

            com.lowagie.text.List strengthList =
                    new com.lowagie.text.List(com.lowagie.text.List.UNORDERED);

            learningPlan.getStrengths()
                    .forEach(item -> strengthList.add(new ListItem(item, normalFont)));

            document.add(strengthList);
            document.add(new Paragraph(" "));
        }

        if (learningPlan.getRecommendedAction() != null) {

            document.add(new Paragraph(
                    "Recommended Action",
                    sectionFont
            ));

            document.add(new Paragraph(
                    learningPlan.getRecommendedAction(),
                    normalFont
            ));

            document.add(new Paragraph(" "));
        }

    }

    private void addRevisionPlan(
            Document document,
            ExportDataResponse report,
            Font sectionFont,
            Font normalFont
    ) throws Exception {

        if (report.getRevisionPlan() == null) {
            return;
        }

        PersonalizedRevisionPlanResponse revisionPlan =
                report.getRevisionPlan();

        addSectionHeader(
                document,
                "Personalized Revision Plan",
                sectionFont
        );

        addFieldTable(
                document,
                new String[][]{
                        {
                                "Revision Level",
                                revisionPlan.getRevisionLevel()
                        },
                        {
                                "Revision Score",
                                String.valueOf(revisionPlan.getRevisionScore())
                        }
                },
                normalFont
        );

        if (revisionPlan.getMessage() != null) {

            document.add(new Paragraph(
                    "Message",
                    sectionFont
            ));

            document.add(new Paragraph(
                    revisionPlan.getMessage(),
                    normalFont
            ));

            document.add(new Paragraph(" "));
        }

        if (revisionPlan.getUrgentConcepts() != null &&
                !revisionPlan.getUrgentConcepts().isEmpty()) {

            document.add(new Paragraph(
                    "Urgent Concepts",
                    sectionFont
            ));

            com.lowagie.text.List urgentList =
                    new com.lowagie.text.List(com.lowagie.text.List.UNORDERED);

            revisionPlan.getUrgentConcepts()
                    .forEach(item -> urgentList.add(new ListItem(item, normalFont)));

            document.add(urgentList);

            document.add(new Paragraph(" "));
        }

        if (revisionPlan.getImprovingConcepts() != null &&
                !revisionPlan.getImprovingConcepts().isEmpty()) {

            document.add(new Paragraph(
                    "Improving Concepts",
                    sectionFont
            ));

            com.lowagie.text.List improvingList =
                    new com.lowagie.text.List(com.lowagie.text.List.UNORDERED);

            revisionPlan.getImprovingConcepts()
                    .forEach(item -> improvingList.add(new ListItem(item, normalFont)));

            document.add(improvingList);

            document.add(new Paragraph(" "));
        }

        if (revisionPlan.getMasteredConcepts() != null &&
                !revisionPlan.getMasteredConcepts().isEmpty()) {

            document.add(new Paragraph(
                    "Mastered Concepts",
                    sectionFont
            ));

            com.lowagie.text.List masteredList =
                    new com.lowagie.text.List(com.lowagie.text.List.UNORDERED);

            revisionPlan.getMasteredConcepts()
                    .forEach(item -> masteredList.add(new ListItem(item, normalFont)));

            document.add(masteredList);

            document.add(new Paragraph(" "));
        }

        if (revisionPlan.getRecommendedAction() != null) {

            document.add(new Paragraph(
                    "Recommended Action",
                    sectionFont
            ));

            document.add(new Paragraph(
                    revisionPlan.getRecommendedAction(),
                    normalFont
            ));

            document.add(new Paragraph(" "));
        }

    }

    private void addMistakeMemory(
            Document document,
            ExportDataResponse report,
            Font sectionFont,
            Font normalFont
    ) throws Exception {

        if (report.getMistakeMemory() == null) {
            return;
        }

        DeveloperMistakeProfileResponse mistakeMemory =
                report.getMistakeMemory();

        addSectionHeader(
                document,
                "AI Mistake Memory",
                sectionFont
        );

        addFieldTable(
                document,
                new String[][]{
                        {
                                "Total Mistakes",
                                String.valueOf(mistakeMemory.getTotalMistakes())
                        },
                        {
                                "Most Common Mistake",
                                mistakeMemory.getMostCommonMistake() != null
                                        ? mistakeMemory.getMostCommonMistake().name()
                                        : "-"
                        },
                        {
                                "Weakest Concept",
                                mistakeMemory.getWeakestConcept() != null
                                        ? mistakeMemory.getWeakestConcept()
                                        : "-"
                        }
                },
                normalFont
        );

        if (mistakeMemory.getInsights() != null &&
                !mistakeMemory.getInsights().isEmpty()) {

            document.add(new Paragraph(
                    "Insights",
                    sectionFont
            ));

            com.lowagie.text.List insightList =
                    new com.lowagie.text.List(com.lowagie.text.List.UNORDERED);

            mistakeMemory.getInsights()
                    .forEach(insight ->
                            insightList.add(new ListItem(insight, normalFont)));

            document.add(insightList);

            document.add(new Paragraph(" "));
        }

    }

        private void addAiLearning(
                Document document,
                ExportDataResponse report,
                Font sectionFont,
                Font normalFont
        ) throws Exception {

        if (report.getAiLearning() == null ||
                report.getAiLearning().isEmpty()) {
                return;
        }

        addSectionHeader(
                document,
                "AI Learning Progress",
                sectionFont
        );

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(8);
        table.setSpacingAfter(16);
        table.setWidths(new float[]{2f, 1f, 1f, 1.5f, 4f});

        Font headerFont = FontFactory.getFont(
                FontFactory.HELVETICA_BOLD,
                10,
                Color.WHITE
        );

        Color headerColor = new Color(33, 97, 140);

        String[] headers = {
                "Concept",
                "Mistakes",
                "Accepted",
                "Status",
                "AI Feedback"
        };

        for (String header : headers) {

                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(headerColor);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(6);

                table.addCell(cell);
        }

        for (ConceptGrowthResponse concept : report.getAiLearning()) {

                table.addCell(new Phrase(concept.getConcept(), normalFont));

                table.addCell(new Phrase(
                        String.valueOf(concept.getTotalMistakes()),
                        normalFont
                ));

                table.addCell(new Phrase(
                        String.valueOf(concept.getAcceptedSubmissions()),
                        normalFont
                ));

                table.addCell(new Phrase(
                        concept.getGrowthStatus(),
                        normalFont
                ));

                table.addCell(new Phrase(
                        concept.getMessage() == null
                                ? "-"
                                : concept.getMessage(),
                        normalFont
                ));
        }

        document.add(table);

        }

}