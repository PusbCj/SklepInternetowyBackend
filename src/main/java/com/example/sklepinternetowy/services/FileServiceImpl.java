package com.example.sklepinternetowy.services;


import com.example.sklepinternetowy.exception.FileStorageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Service
public class FileServiceImpl implements FileService{
    @Override
    public String storeFile(MultipartFile file) {

        String fileName = file.getOriginalFilename();
        try {
            Files.copy(file.getInputStream(), Path.of("./photo/"+fileName), StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileStorageException("Nie można zapisać pliku "+fileName+ " spróbuj ponownie" );
        }

    }
}
