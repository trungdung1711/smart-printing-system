package com.Anonymous.smart_printing_system.service;

import com.Anonymous.smart_printing_system.model.eenum.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReportProcessor
{
    private final ReportGeneratorService reportGeneratorService;

    // Run at 12:01 AM on the first day of every month
    //@Scheduled(cron = "0 1 0 1 * ?")
    @Scheduled(fixedRate = 10 * 60 * 1000)
    public void generateMonthlyReport()
    {
        System.out.println("Generating Monthly Report...");
        LocalDate reportDate = LocalDate.now();
        reportGeneratorService.generateReport(ReportType.MONTHLY, reportDate);
    }

    // Run at 12:01 AM on December 31 every year
    //@Scheduled(cron = "0 1 0 31 12 ?")
    @Scheduled(fixedRate = 15 * 60 * 1000)
    public void generateYearlyReport()
    {
        System.out.println("Generating Yearly Report...");
        LocalDate reportDate = LocalDate.now();
        reportGeneratorService.generateReport(ReportType.ANNUALLY, reportDate);
    }
}
