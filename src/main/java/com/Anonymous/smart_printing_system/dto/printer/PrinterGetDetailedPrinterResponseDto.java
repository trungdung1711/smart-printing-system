package com.Anonymous.smart_printing_system.dto.printer;

import com.Anonymous.smart_printing_system.model.eenum.PrinterStatus;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.Anonymous.smart_printing_system.model.Printer}
 */
@Value
public class PrinterGetDetailedPrinterResponseDto implements Serializable {
    Long id;
    String model;
    String description;
    String brand;
    String buildingName;
    String campusName;
    String roomNumber;
    PrinterStatus printerStatus;
    LocalDateTime recentMaintenanceDate;
    Long max;
    Long usageCount;
}