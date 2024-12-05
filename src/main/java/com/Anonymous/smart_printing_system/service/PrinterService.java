package com.Anonymous.smart_printing_system.service;


import com.Anonymous.smart_printing_system.dto.printer.PrinterAddPrinterRequestDto;
import com.Anonymous.smart_printing_system.dto.printer.PrinterGetDetailedPrinterResponseDto;
import com.Anonymous.smart_printing_system.dto.printer.PrinterGetPrintersAtCampusResponseDto;
import com.Anonymous.smart_printing_system.dto.mapper.PrinterMapper;
import com.Anonymous.smart_printing_system.dto.printer.PrinterUpdatePrinterRequestDto;
import com.Anonymous.smart_printing_system.model.Printer;
import com.Anonymous.smart_printing_system.model.Spso;
import com.Anonymous.smart_printing_system.model.eenum.PrinterStatus;
import com.Anonymous.smart_printing_system.repository.PrinterRepository;
import com.Anonymous.smart_printing_system.repository.SystemUserRepository;
import com.Anonymous.smart_printing_system.util.SystemUserUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class PrinterService
{
    private final SystemUserUtil systemUserUtil;

    private final PrinterMapper printerMapper;

    private final SystemUserRepository systemUserRepository;

    private final PrinterRepository printerRepository;

    public void addPrinter(PrinterAddPrinterRequestDto printerAddPrinterRequestDto)
    {
        Printer printer = printerMapper.toEntity(printerAddPrinterRequestDto);
        printer.setPrinterStatus(PrinterStatus.ON);
        printer.setUsageCount(0L);
        printer.setMax(1_000_000L);
        Long id = systemUserUtil.getAuthenticatedUserId();
        Spso spso = (Spso) systemUserRepository.findById(id).get();
        spso.getPrinters().add(printer);
        printer.setSpso(spso);
    }


    public Page<PrinterGetPrintersAtCampusResponseDto> getPrintersByCampusName(String campusName, Integer pageNumber, Integer pageSize)
    {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Printer> printers = printerRepository.findAllByCampusName(campusName, pageable);

        return printers.map(printerMapper::toPrinterGetPrintersAtCampusResponseDto);
    }


    public PrinterGetDetailedPrinterResponseDto getDetailedPrinter(Long id)
    {
        Printer printer = printerRepository.findById(id).get();
        return printerMapper.toPrinterGetDetailedPrinterResponseDto(printer);
    }


    public void updatePrinter(Long id, PrinterUpdatePrinterRequestDto printerUpdatePrinterRequestDto)
    {
        Printer printer = printerRepository.findById(id).get();
        printerMapper.partialUpdate(printerUpdatePrinterRequestDto, printer);
    }


    public void deletePrinter(Long id)
    {
        Printer printer = printerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Printer not found with id: " + id));

        printerRepository.delete(printer);
    }
}
