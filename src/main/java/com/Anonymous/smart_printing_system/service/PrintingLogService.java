package com.Anonymous.smart_printing_system.service;


import com.Anonymous.smart_printing_system.dto.PrintingLogGetAllPrintingLogsDto;
import com.Anonymous.smart_printing_system.dto.PrintingLogPrintRequestDto;
import com.Anonymous.smart_printing_system.dto.mapper.PrintingLogMapper;
import com.Anonymous.smart_printing_system.exception.PaperNotEnough;
import com.Anonymous.smart_printing_system.exception.UserNotFoundException;
import com.Anonymous.smart_printing_system.model.*;
import com.Anonymous.smart_printing_system.model.eenum.FileType;
import com.Anonymous.smart_printing_system.model.eenum.PageOrientation;
import com.Anonymous.smart_printing_system.model.eenum.PrintingLogEnum;
import com.Anonymous.smart_printing_system.repository.PrinterRepository;
import com.Anonymous.smart_printing_system.repository.PrintingLogRepository;
import com.Anonymous.smart_printing_system.repository.SystemUserRepository;
import com.Anonymous.smart_printing_system.security.model.SystemUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
@Transactional
@RequiredArgsConstructor
public class PrintingLogService
{
    private final PrintingLogRepository printingLogRepository;
    private final PrintingLogMapper printingLogMapper;
    private final PrinterRepository printerRepository;
    private final FileService fileService;
    private final SystemUserRepository systemUserRepository;


    public void createPrintingLog(PrintingLogPrintRequestDto printingLogPrintRequestDto, MultipartFile file) throws IOException
    {
        Long id = ((SystemUserDetails)
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal())
                .getId();

        SystemUser systemUser = systemUserRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        /*
        Check the number of pages
         */
        Student student = (Student)systemUser;
        long requiredPage = (printingLogPrintRequestDto.getDocument().getEnd() - printingLogPrintRequestDto.getDocument().getStart() + 1) * printingLogPrintRequestDto.getNumberOfCopy();
        if (requiredPage > student.getStudentNumRemained())
        {
            throw new PaperNotEnough();
        }
        /*
        Map to the entity
         */
        PrintingLog printingLog = printingLogMapper.toEntity(printingLogPrintRequestDto);

        /*
        Set the basic of PrintingLog
         */
        printingLog.setLogStatus(PrintingLogEnum.PENDING);
        printingLog.setLogDescription(file.getOriginalFilename());
        printingLog.setPageOrientation(PageOrientation.LANDSCAPE);

        Document document = printingLog.getDocument();

        /*
        Set the basic of Document
         */
        document.setSize(file.getSize());
        document.setFileType(FileType.PDF);
        document.setName(file.getOriginalFilename());
        /*
        Save the file to the server's file system
        And set the URL for front-end to get
         */
        document.setUrl(fileService.uploadFile(file));
        document.setPageNumber(document.getEnd() - document.getStart() + 1);
        document.setPrintingLog(printingLog);
        /*
        Map the printer to this PrintingLog
         */
        Printer printer = printerRepository
                .findPrinterById(printingLog.getPrinter().getId());
        printingLog.setPrinter(printer);
        printer.getPrintingLogs().add(printingLog);
        /*
        Map the student to this PrintingLog
         */
        /*
        Set both sides of the PrintingLog and the Student
         */
        printingLog.setStudent((Student)systemUser);
        ((Student)systemUser).getPrintingLogs().add(printingLog);
        printingLogRepository.save(printingLog);
    }


    public Page<PrintingLogGetAllPrintingLogsDto> getAllPrintingLogsOfStudent(Long studentId, Long pageNumber, Long pageSize)
    {
        Pageable pageable = PageRequest.of(pageNumber.intValue(), pageSize.intValue());
        Page<PrintingLog> printingLogs = printingLogRepository.findPrintingLogsByStudentId(studentId, pageable);

        return printingLogs.map(printingLogMapper::toDto1);
    }
}