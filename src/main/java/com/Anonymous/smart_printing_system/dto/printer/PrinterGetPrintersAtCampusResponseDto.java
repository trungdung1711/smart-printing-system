package com.Anonymous.smart_printing_system.dto.printer;

import com.Anonymous.smart_printing_system.model.eenum.PrinterStatus;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.Anonymous.smart_printing_system.model.Printer}
 */
@Value
public class PrinterGetPrintersAtCampusResponseDto implements Serializable {
    Long id;
    String description;
    String buildingName;
    String campusName;
    String roomNumber;
    PrinterStatus printerStatus;
}