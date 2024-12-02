package com.Anonymous.smart_printing_system.controller;

import com.Anonymous.smart_printing_system.dto.PrintingLogRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/printing-log")
@RequiredArgsConstructor
public class             PrintingLogController {
//    private final  printingLogService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getPrintingLogs(
            @RequestParam String userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        // Triển khai logic lấy nhật ký in
//        return ResponseEntity.ok();
        return null;
    }

    @PostMapping("/print-request")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createPrintRequest(
            @RequestBody PrintingLogRequestDto printRequestDto
    ) {
        // Triển khai logic tạo yêu cầu in
//        return ResponseEntity.status(HttpStatus.CREATED).body(printRequestResult);
        return null;
    }
}