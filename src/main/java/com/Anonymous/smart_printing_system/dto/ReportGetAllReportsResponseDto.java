package com.Anonymous.smart_printing_system.dto;

import com.Anonymous.smart_printing_system.model.eenum.ReportType;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.Anonymous.smart_printing_system.model.Report}
 */
@Value
public class ReportGetAllReportsResponseDto implements Serializable {
    Long id;
    LocalDateTime reportDate;
    ReportType reportType;
    String url;
}