package com.Anonymous.smart_printing_system.dto.printing;

import com.Anonymous.smart_printing_system.model.eenum.FileType;
import com.Anonymous.smart_printing_system.model.eenum.PageOrientation;
import com.Anonymous.smart_printing_system.model.eenum.PageType;
import com.Anonymous.smart_printing_system.model.eenum.PrintingLogEnum;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.Anonymous.smart_printing_system.model.PrintingLog}
 */
@Value
public class PrintingLogGetAllPrintingLogsDto implements Serializable {
    Long id;
    PrintingLogEnum logStatus;
    String logDescription;
    LocalDateTime logDate;
    LocalDateTime logStartTime;
    LocalDateTime logEndTime;
    Long pagePerSheet;
    Long numberOfCopy;
    PageOrientation pageOrientation;
    DocumentDto document;

    /**
     * DTO for {@link com.Anonymous.smart_printing_system.model.Document}
     */
    @Value
    public static class DocumentDto implements Serializable {
        Long id;
        PageType pageType;
        Long size;
        FileType fileType;
        String name;
        String url;
        Long pageNumber;
        Long start;
        Long end;
    }
}