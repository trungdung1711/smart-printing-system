package com.Anonymous.smart_printing_system.dto;

import com.Anonymous.smart_printing_system.model.eenum.FileType;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link com.Anonymous.smart_printing_system.model.SemesterConfiguration}
 */
@Value
public class SemesterConfigurationGetConfigurationResponseDto implements Serializable {
    Long id;
    Long defaultPageNumber;
    LocalDateTime updateDate;
    Set<FileType> acceptedFileType;
    String semesterName;
}