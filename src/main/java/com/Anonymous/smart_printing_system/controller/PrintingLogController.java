package com.Anonymous.smart_printing_system.controller;

import com.Anonymous.smart_printing_system.dto.printing.PrintingLogGetAllPrintingLogsDto;
import com.Anonymous.smart_printing_system.dto.printing.PrintingLogPrintRequestDto;
import com.Anonymous.smart_printing_system.service.PrintingLogService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/printing")
@RequiredArgsConstructor
public class PrintingLogController
{
    private final PrintingLogService printingLogService;


    @GetMapping("logs")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Page<PrintingLogGetAllPrintingLogsDto>> getPrintingLogsOfStudent(
            @RequestParam(defaultValue = "0") Long pageNumber,
            @RequestParam(defaultValue = "10") Long pageSize
    )
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(printingLogService.getAllPrintingLogsOfStudent(pageNumber, pageSize));
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Void> createPrintingLog
            (
                    @RequestPart(name = "configuration") @Valid PrintingLogPrintRequestDto printingLogPrintRequestDto,
                    @RequestPart(name = "file") MultipartFile file
            )
            throws IOException
    {
        printingLogService.createPrintingLog(printingLogPrintRequestDto, file);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }


    @GetMapping("printers/{id}/logs")
    @PreAuthorize("hasAnyRole('ADMIN', 'SPSO')")
    public ResponseEntity<Page<PrintingLogGetAllPrintingLogsDto>> getPrintingLogsOfPrinter(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") Long pageNumber,
            @RequestParam(defaultValue = "10") Long pageSize
    )
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(printingLogService.getAllPrintingLogsOfPrinter(id, pageNumber, pageSize));
    }
}