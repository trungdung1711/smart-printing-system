package com.Anonymous.smart_printing_system.dto.printer;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.Anonymous.smart_printing_system.model.Printer}
 */
@Value
public class PrinterAddPrinterRequestDto implements Serializable {
    @NotNull(message = "Model is not specified")
    String model;
    @NotNull(message = "Description is not specified")
    String description;
    @NotNull(message = "Brand is not specified")
    String brand;
    @NotNull(message = "Building is not specified")
    String buildingName;
    @NotNull(message = "Campus is not specified")
    String campusName;
    @NotNull(message = "Room is not specified")
    String roomNumber;
}