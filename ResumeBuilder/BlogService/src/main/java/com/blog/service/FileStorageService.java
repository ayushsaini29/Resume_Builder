package com.blog.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

//    public String storeFile(MultipartFile file) {
//        try {
//            Path copyLocation = Paths.get(uploadDir + java.io.File.separator + file.getOriginalFilename());
//            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
//            return file.getOriginalFilename();
//        } catch (IOException ex) {
//            throw new RuntimeException("Could not store file " + file.getOriginalFilename() + ". Please try again!", ex);
//        }
//    }
    
    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            Path filePath = Paths.get(uploadDir + "/" + fileName);
            Files.write(filePath, file.getBytes());
            return filePath.toString();
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName, ex);
        }
    }
}

