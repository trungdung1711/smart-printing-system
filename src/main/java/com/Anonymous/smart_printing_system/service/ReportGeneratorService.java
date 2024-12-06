package com.Anonymous.smart_printing_system.service;

import com.Anonymous.smart_printing_system.model.Printer;
import com.Anonymous.smart_printing_system.model.Report;
import com.Anonymous.smart_printing_system.model.eenum.ReportType;
import com.Anonymous.smart_printing_system.repository.PrinterRepository;
import com.Anonymous.smart_printing_system.repository.ReportRepository;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;


@Service
@RequiredArgsConstructor
@Transactional
public class ReportGeneratorService {
    private final PrinterRepository printerRepository;
    private final ReportRepository reportRepository;

    @Value("${com.sps.upload}")
    private String uploadPath;


    public void generateReport(ReportType reportType, LocalDate reportDate)
    {
        String fileName = reportType + "_Consolidated_Report_" + reportDate + ".pdf";
        Path filePath = Paths.get(uploadPath, fileName);

        try (PdfWriter writer = new PdfWriter(filePath.toString());
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // Add custom font
            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_BOLDITALIC);
            document.setFont(font);


            // Add Title Page with borders
            addTitlePage(document, reportType, reportDate);

            // Add General Summary Page with borders
            addGeneralSummaryPage(document, pdf);


            addFinancialSummarySection(document, getRandomDouble(), getRandomDouble());

            // Add Printer Details (one per page with borders)
            List<Printer> printers = printerRepository.findAll();
            for (Printer printer : printers) {
                addPrinterDetailsPage(document, pdf, printer);

                // Add a page break after each printer (except the last one)
                if (printers.indexOf(printer) != printers.size() - 1) {
                    document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                }
            }

            System.out.println("Consolidated report generated: " + fileName);

            // Save report metadata
            Report report = Report.builder()
                    .reportRevenue((long) printers.stream().mapToDouble(p -> p.getUsageCount() * 125.25).sum())
                    .reportType(reportType)
                    .reportDate(LocalDateTime.now())
                    .reportPrintCount(printers.stream().mapToLong(Printer::getUsageCount).sum())
                    .url("/file/" + fileName)
                    .build();

            reportRepository.save(report);
        } catch (IOException e) {
            System.err.println("Error generating consolidated report: " + e.getMessage());
        }
    }


    private Double getRandomDouble()
    {
        Random random = new Random();
        return 50 + (random.nextDouble() * (150 - 50));
    }

    private void addTitlePage(Document document, ReportType reportType, LocalDate reportDate) {
        // Path to the logo image
        String logoPath = "public/file/logo.png"; // Replace with the actual path to your logo

        try {
            // Load the logo image
            ImageData imageData = ImageDataFactory.create(logoPath);
            Image logo = new Image(imageData);

            // Set the logo's size and alignment
            logo.setWidth(100); // Adjust width as needed
            logo.setHeight(100); // Adjust height as needed
            logo.setHorizontalAlignment(HorizontalAlignment.CENTER);

            // Add the logo to the document
            document.add(logo);
        } catch (Exception e) {
            System.err.println("Error loading logo: " + e.getMessage());
        }

        // Add system name below the logo
        document.add(new Paragraph("SMART PRINTING SYSTEM")
                .setFontSize(30)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(10)); // Add some spacing between the logo and system name

        // Add the report title
        document.add(new Paragraph("CONSOLIDATED REPORT")
                .setFontSize(40)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20));

        // Add report type and date
        document.add(new Paragraph("Report Type: " + reportType)
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("Report Date: " + reportDate)
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("Generated On: " + LocalDate.now())
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(30));

        // Add a page break
        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
    }


    private void addGeneralSummaryPage(Document document, PdfDocument pdf) {
        // Add page borders
        addPageBorders(pdf);

        // Add General Summary Title
        document.add(new Paragraph("GENERAL SUMMARY")
                .setFontSize(24)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20));

        // Fetch data for summary
        List<Printer> printers = printerRepository.findAll();
        long totalPrinters = printers.size();
        long activePrinters = printers.stream().filter(p -> p.getPrinterStatus().toString().equalsIgnoreCase("ACTIVE")).count();
        long totalUsage = printers.stream().mapToLong(Printer::getUsageCount).sum();
        double totalRevenue = printers.stream().mapToDouble(p -> p.getUsageCount() * 125.25).sum();

        // Summary Table
        Table summaryTable = new Table(UnitValue.createPercentArray(new float[]{250, 350}))
                .setWidth(UnitValue.createPercentValue(80))
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setBorder(new SolidBorder(1));

        summaryTable.addCell(createHeaderCell("Total Printers"));
        summaryTable.addCell(createValueCell(String.valueOf(totalPrinters)));
        summaryTable.addCell(createHeaderCell("Active Printers"));
        summaryTable.addCell(createValueCell(String.valueOf(activePrinters)));
        summaryTable.addCell(createHeaderCell("Total Usage (Pages)"));
        summaryTable.addCell(createValueCell(String.valueOf(totalUsage)));
        summaryTable.addCell(createHeaderCell("Total Revenue (USD)"));
        summaryTable.addCell(createValueCell(String.format("%.2f", totalRevenue)));

        document.add(summaryTable);
        addHandwritingSpace(document);
        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE)); // Move to next page
    }


    private void addFinancialSummarySection(Document document, double totalRevenue, double expenses) {
        document.add(new Paragraph("FINANCIAL SUMMARY")
                .setFontSize(24)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20));

        double profit = totalRevenue - expenses;

        Table financeTable = new Table(UnitValue.createPercentArray(new float[]{250, 350}))
                .setWidth(UnitValue.createPercentValue(90))
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setBorder(new SolidBorder(1));

        financeTable.addCell(createHeaderCell("Total Revenue (USD)"));
        financeTable.addCell(createValueCell(String.format("%.2f", totalRevenue)));
        financeTable.addCell(createHeaderCell("Total Expenses (USD)"));
        financeTable.addCell(createValueCell(String.format("%.2f", expenses)));
        financeTable.addCell(createHeaderCell("Net Profit (USD)"));
        financeTable.addCell(createValueCell(String.format("%.2f", profit)));

        document.add(financeTable);
        addHandwritingSpace(document);
        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
    }


    private void addPrinterDetailsPage(Document document, PdfDocument pdf, Printer printer) {
        // Add page borders
        addPageBorders(pdf);

        // Printer Details Section
        document.add(new Paragraph("Printer Details: " + printer.getModel())
                .setFontSize(30)
                .setBold()
                .setTextAlignment(TextAlignment.LEFT)
                .setMarginBottom(15));

        // Printer Details Table
        Table printerTable = new Table(UnitValue.createPercentArray(new float[]{250, 350}))
                .setWidth(UnitValue.createPercentValue(100))
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setBorder(new SolidBorder(3));

        printerTable.addCell(createHeaderCell("INFORMATION"));
        printerTable.addCell(createHeaderCell("VALUE"));

        printerTable.addCell(createValueCell("Printer ID"));
        printerTable.addCell(createValueCell(String.valueOf(printer.getId())));
        printerTable.addCell(createValueCell("Model"));
        printerTable.addCell(createValueCell(printer.getModel()));
        printerTable.addCell(createValueCell("Description"));
        printerTable.addCell(createValueCell(printer.getDescription()));
        printerTable.addCell(createValueCell("Brand"));
        printerTable.addCell(createValueCell(printer.getBrand()));
        printerTable.addCell(createValueCell("Building Name"));
        printerTable.addCell(createValueCell(printer.getBuildingName()));
        printerTable.addCell(createValueCell("Campus"));
        printerTable.addCell(createValueCell(printer.getCampusName()));
        printerTable.addCell(createValueCell("Room Number"));
        printerTable.addCell(createValueCell(printer.getRoomNumber()));
        printerTable.addCell(createValueCell("Status"));
        printerTable.addCell(createValueCell(printer.getPrinterStatus().toString()));
        printerTable.addCell(createValueCell("Last Maintenance"));
        printerTable.addCell(createValueCell(printer.getRecentMaintenanceDate() == null ? "No data" : printer.getRecentMaintenanceDate().format(DateTimeFormatter.ISO_DATE)));
        printerTable.addCell(createValueCell("Usage (Pages)"));
        printerTable.addCell(createValueCell(String.valueOf(printer.getUsageCount())));
        printerTable.addCell(createValueCell("Managed By"));
        printerTable.addCell(createValueCell(printer.getSpso().getEmail()));
        printerTable.addCell(createValueCell("Revenue (USD)"));
        printerTable.addCell(createValueCell(String.format("%.2f", printer.getUsageCount() * 125.25)));

        document.add(printerTable);

        // Add a professional note at the bottom of the page
        document.add(new Paragraph("Note: Printer details are based on the data recorded in the system as of the report date.")
                .setFontSize(10)
                .setItalic()
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginTop(20));
        addHandwritingSpace(document);
    }


    private void addPageBorders(PdfDocument pdf) {
        Rectangle pageSize = pdf.getDefaultPageSize();
        PdfCanvas canvas = new PdfCanvas(pdf.getLastPage());
        canvas.setLineWidth(2);
        canvas.setColor(DeviceGray.BLACK, true);
        canvas.rectangle(pageSize.getLeft() + 20, pageSize.getBottom() + 20,
                pageSize.getWidth() - 40, pageSize.getHeight() - 40);
        canvas.stroke();
    }


    private Cell createHeaderCell(String content) {
        return new Cell()
                .add(new Paragraph(content).setBold().setFontColor(DeviceGray.WHITE))
                .setBackgroundColor(WebColors.getRGBColor("#4CAF50")) // Green Header
                .setTextAlignment(TextAlignment.CENTER)
                .setPadding(8);
    }


    private Cell createValueCell(String content) {
        return new Cell()
                .add(new Paragraph(content))
                .setPadding(8);
    }


    // Method to add handwriting space with a dashed line
    private void addHandwritingSpace(Document document) {
        document.add(new Paragraph("SPSO/ADMIN Notes: ______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________")
                .setFontSize(12)
                .setTextAlignment(TextAlignment.LEFT));
    }
}