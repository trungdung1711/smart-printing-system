package com.Anonymous.smart_printing_system.dto.mapper;

import com.Anonymous.smart_printing_system.dto.printing.PrintingLogGetAllPrintingLogsDto;
import com.Anonymous.smart_printing_system.dto.printing.PrintingLogPrintRequestDto;
import com.Anonymous.smart_printing_system.model.Document;
import com.Anonymous.smart_printing_system.model.PrintingLog;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PrintingLogMapper {
    @Mapping(source = "printerId", target = "printer.id")
    PrintingLog toEntity(PrintingLogPrintRequestDto printingLogPrintRequest);

    @AfterMapping
    default void linkDocument(@MappingTarget PrintingLog printingLog) {
        Document document = printingLog.getDocument();
        if (document != null) {
            document.setPrintingLog(printingLog);
        }
    }

    @Mapping(source = "printer.id", target = "printerId")
    PrintingLogPrintRequestDto toDto(PrintingLog printingLog);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "printerId", target = "printer.id")
    PrintingLog partialUpdate(PrintingLogPrintRequestDto printingLogPrintRequest, @MappingTarget PrintingLog printingLog);

    PrintingLog toEntity(PrintingLogGetAllPrintingLogsDto printingLogGetAllPrintingLogsDto);

    PrintingLogGetAllPrintingLogsDto toDto1(PrintingLog printingLog);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PrintingLog partialUpdate(PrintingLogGetAllPrintingLogsDto printingLogGetAllPrintingLogsDto, @MappingTarget PrintingLog printingLog);
}
