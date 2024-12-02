package com.Anonymous.smart_printing_system.dto.printer;

import com.Anonymous.smart_printing_system.model.eenum.PrinterStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.Anonymous.smart_printing_system.model.Printer}
 */
@Value
public class PrinterUpdatePrinterRequestDto implements Serializable {
    @NotBlank
    String buildingName;
    @NotBlank
    String campusName;
    @NotBlank
    String roomNumber;
    @NotNull
    PrinterStatus printerStatus;
    @NotNull
    LocalDateTime recentMaintenanceDate;
    @NotNull
    Long max;
}