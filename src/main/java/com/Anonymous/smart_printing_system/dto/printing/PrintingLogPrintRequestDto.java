package com.Anonymous.smart_printing_system.dto.printing;

import com.Anonymous.smart_printing_system.model.eenum.PageType;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.Anonymous.smart_printing_system.model.PrintingLog}
 */
@Value
public class PrintingLogPrintRequestDto implements Serializable {
    @NotNull(message = "Must be specified")
    LocalDateTime logDate;
    @NotNull(message = "Must be specified")
    Long pagePerSheet;
    @NotNull(message = "Must be specified")
    Long numberOfCopy;
    DocumentDto document;
    Long printerId;

    /**
     * DTO for {@link com.Anonymous.smart_printing_system.model.Document}
     */
    @Value
    public static class DocumentDto implements Serializable {
        @NotNull(message = "Must be specified")
        PageType pageType;
        @NotNull(message = "Must be specified")
        Long start;
        @NotNull(message = "Must be specified")
        Long end;
    }
}