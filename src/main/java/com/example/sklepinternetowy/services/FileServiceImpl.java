package com.example.sklepinternetowy.services;

import antlr.StringUtils;
import com.example.sklepinternetowy.exception.FileStorageException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileServiceImpl implements FileService{
    @Override
    public String storeFile(MultipartFile file) {

        String fileName = file.getOriginalFilename();
        try {
            Files.copy(file.getInputStream(), Path.of("photo"), StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileStorageException("Nie można zapisać pliku "+fileName+ " spróbuj ponownie" );
        }

    }
}
