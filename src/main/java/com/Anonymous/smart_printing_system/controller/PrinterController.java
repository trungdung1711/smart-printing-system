package com.Anonymous.smart_printing_system.controller;


import com.Anonymous.smart_printing_system.dto.printer.PrinterAddPrinterRequestDto;
import com.Anonymous.smart_printing_system.dto.printer.PrinterGetDetailedPrinterResponseDto;
import com.Anonymous.smart_printing_system.dto.printer.PrinterGetPrintersAtCampusResponseDto;
import com.Anonymous.smart_printing_system.dto.printer.PrinterUpdatePrinterRequestDto;
import com.Anonymous.smart_printing_system.dto.printing.PrintingLogGetAllPrintingLogsDto;
import com.Anonymous.smart_printing_system.service.PrinterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/printers")
@RequiredArgsConstructor
public class PrinterController {
    private final PrinterService printerService;

    @PostMapping
    @PreAuthorize("hasRole('SPSO')")
    public ResponseEntity<Void> addPrinter(@RequestBody PrinterAddPrinterRequestDto printerAddPrinterRequestDto)
    {
        printerService.addPrinter(printerAddPrinterRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }
    /*
        Get all printers in a campus
     */
    @GetMapping("status")
    @PreAuthorize("hasAnyRole('ADMIN', 'SPSO', 'STUDENT')")
    public ResponseEntity<Page<PrinterGetPrintersAtCampusResponseDto>> getPrintersAtCampus(
            @RequestParam String location,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize
    )
    {
        Page<PrinterGetPrintersAtCampusResponseDto> printers = printerService.getPrintersByCampusName(location, pageNumber, pageSize);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(printers);
    }
    /*
        Get detailed printer using id
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN', 'SPSO')")
    public ResponseEntity<PrinterGetDetailedPrinterResponseDto> getDetailedPrinter(@PathVariable Long id)
    {
        return ResponseEntity.
                status(HttpStatus.OK)
                .body(printerService.getDetailedPrinter(id));
    }
    /*
        Update a printer
     */
    @PutMapping("{id}")
    @PreAuthorize("hasRole('SPSO')")
    public ResponseEntity<Void> updatePrinter(@PathVariable Long id, @RequestBody @Valid PrinterUpdatePrinterRequestDto printerUpdatePrinterRequestDto)
    {
        printerService.updatePrinter(id, printerUpdatePrinterRequestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('SPSO')")
    public ResponseEntity<Void> deletePrinter(@PathVariable Long id)
    {
        printerService.deletePrinter(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT) // Trả về mã 204 - Không có nội dung
                .build();
    }
}
