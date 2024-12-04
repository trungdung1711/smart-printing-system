package com.Anonymous.smart_printing_system.exception;


import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiError
{
    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;
}
