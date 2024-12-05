package com.Anonymous.smart_printing_system.controller;


import com.Anonymous.smart_printing_system.dto.SemesterConfigurationGetConfigurationResponseDto;
import com.Anonymous.smart_printing_system.dto.SemesterConfigurationRequestDto;
import com.Anonymous.smart_printing_system.dto.mapper.SemesterConfigurationMapper;
import com.Anonymous.smart_printing_system.model.SemesterConfiguration;
import com.Anonymous.smart_printing_system.repository.SemesterConfigurationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/v1/configs")
public class SemesterConfigurationController {
    private final SemesterConfigurationRepository semesterConfigurationRepository;
    private final SemesterConfigurationMapper semesterConfigurationMapper;


    public SemesterConfigurationController(SemesterConfigurationRepository semesterConfigurationRepository, SemesterConfigurationMapper semesterConfigurationMapper) {
        this.semesterConfigurationRepository = semesterConfigurationRepository;
        this.semesterConfigurationMapper = semesterConfigurationMapper;
    }


    @PutMapping()
    public ResponseEntity<SemesterConfigurationGetConfigurationResponseDto> updateConfiguration(@RequestBody SemesterConfigurationRequestDto semesterConfigurationRequestDto) {
        SemesterConfiguration semesterConfiguration = semesterConfigurationRepository.findById(1L).orElse(null);

        semesterConfiguration.setSemesterName("HK1-241");
        semesterConfiguration.setDefaultPageNumber(semesterConfigurationRequestDto.getDefaultPageNumber());
        semesterConfiguration.setUpdateDate(LocalDateTime.now());
        semesterConfiguration.setAcceptedFileType(semesterConfigurationRequestDto.getAcceptedFileType());

        semesterConfigurationRepository.save(semesterConfiguration);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(semesterConfigurationMapper.toDto1(semesterConfiguration));
    }


    @GetMapping
    public ResponseEntity<SemesterConfigurationGetConfigurationResponseDto> getCurrentConfiguration()
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(semesterConfigurationMapper.toDto1(semesterConfigurationRepository.findById(1L).orElse(null)));
    }
}
