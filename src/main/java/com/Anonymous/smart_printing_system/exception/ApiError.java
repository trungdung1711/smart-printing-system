package com.Anonymous.smart_printing_system.exception;


import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ApiError
{
    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;
}
