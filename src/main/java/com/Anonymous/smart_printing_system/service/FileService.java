package com.Anonymous.smart_printing_system.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@Service
public class FileService
{
    @Value("${com.sps.upload}")
    private String uploadDir;


    public String uploadFile(MultipartFile file) throws IOException
    {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        Path uploadPath = Paths.get(uploadDir);

        // Sanitize file name
        String sanitizedFileName = sanitizeFileName(file.getOriginalFilename());
        String fileName = UUID.randomUUID() + "_" + sanitizedFileName;

        Path targetPath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        // Return public path
        return "/file/" + fileName;
    }

    private String sanitizeFileName(String originalFileName)
    {
        return originalFileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
    }
}
