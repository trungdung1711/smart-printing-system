package com.Anonymous.smart_printing_system.service;

import com.Anonymous.smart_printing_system.model.PrintingLog;
import com.Anonymous.smart_printing_system.model.Student;
import com.Anonymous.smart_printing_system.model.SystemUser;
import com.Anonymous.smart_printing_system.model.eenum.PrintingLogEnum;
import com.Anonymous.smart_printing_system.repository.PrintingLogRepository;
import com.Anonymous.smart_printing_system.repository.SystemUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentProcessor
{
    private final PrintingLogRepository printingLogRepository;

    /*
    For every 60 s
     */
    @Scheduled(fixedRate = 60000)
    public void processDocuments()
    {
        /*
        Find all the printingLogs that are pending
         */
        System.out.println("CHECKING PRINT REQUESTS");
        List<PrintingLog> printingLogs =
                printingLogRepository
                        .findPrintingLogsByLogStatus(PrintingLogEnum.PENDING);

        for (PrintingLog printingLog : printingLogs)
        {
            if (isReadyToPrint(printingLog))
            {
                printingLog.setLogStatus(PrintingLogEnum.SUCCESSFUL);

                printingLog.setLogStartTime(LocalDateTime.now());
                printingLog.setLogEndTime(LocalDateTime.now().plusMinutes(5));

                Long usageCount = printingLog.getPrinter().getUsageCount();

                printingLog.getPrinter().setUsageCount(usageCount + 1);
            }
        }
    }


    private boolean isReadyToPrint(PrintingLog printingLog)
    {
        /*
        Get the current time, if this current time is before 6 hours
        than the time tha the student will come to take the document
        the printer will print it
         */
        LocalDateTime now = LocalDateTime.now();
        return printingLog
                .getLogDate()
                .minusHours(6)
                .isBefore(now);
    }
}