package com.Anonymous.smart_printing_system.dto.mapper;


import com.Anonymous.smart_printing_system.dto.printer.PrinterAddPrinterRequestDto;
import com.Anonymous.smart_printing_system.dto.printer.PrinterGetDetailedPrinterResponseDto;
import com.Anonymous.smart_printing_system.dto.printer.PrinterGetPrintersAtCampusResponseDto;
import com.Anonymous.smart_printing_system.dto.printer.PrinterUpdatePrinterRequestDto;
import com.Anonymous.smart_printing_system.model.Printer;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PrinterMapper {
    Printer toEntity(PrinterAddPrinterRequestDto printerAddPrinterRequestDto);

    PrinterAddPrinterRequestDto toDto(Printer printer);

    Printer toEntity(PrinterGetPrintersAtCampusResponseDto printerGetPrintersAtCampusResponseDto);

    PrinterGetPrintersAtCampusResponseDto toPrinterGetPrintersAtCampusResponseDto(Printer printer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Printer partialUpdate(PrinterGetPrintersAtCampusResponseDto printerGetPrintersAtCampusResponseDto, @MappingTarget Printer printer);

    PrinterGetDetailedPrinterResponseDto toPrinterGetDetailedPrinterResponseDto(Printer printer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Printer partialUpdate(PrinterGetDetailedPrinterResponseDto printerGetDetailedPrinterResponseDto, @MappingTarget Printer printer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Printer partialUpdate(PrinterUpdatePrinterRequestDto printerUpdatePrinterRequestDto, @MappingTarget Printer printer);
}
