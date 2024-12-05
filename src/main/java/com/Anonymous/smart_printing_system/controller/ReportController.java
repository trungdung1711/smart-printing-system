package com.Anonymous.smart_printing_system.controller;


import com.Anonymous.smart_printing_system.dto.ReportGetAllReportsResponseDto;
import com.Anonymous.smart_printing_system.dto.mapper.ReportMapper;
import com.Anonymous.smart_printing_system.model.Report;
import com.Anonymous.smart_printing_system.model.eenum.ReportType;
import com.Anonymous.smart_printing_system.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/reports")
public class ReportController
{
    private final ReportRepository reportRepository;


    private final ReportMapper reportMapper;


    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN', 'SPSO')")
    ResponseEntity<Page<ReportGetAllReportsResponseDto>> getAllReports(
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize,
            @RequestParam ReportType reportType
    )
    {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Report> reports = reportRepository.findReportsByReportType(reportType, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reports.map(reportMapper::toDto));
    }
}
