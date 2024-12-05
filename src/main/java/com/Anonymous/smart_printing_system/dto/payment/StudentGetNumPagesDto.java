package com.Anonymous.smart_printing_system.dto.payment;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.Anonymous.smart_printing_system.model.Student}
 */
@Value
public class StudentGetNumPagesDto implements Serializable {
    Long studentNumRemained;
}