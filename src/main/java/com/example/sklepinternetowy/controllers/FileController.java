package com.example.sklepinternetowy.controllers;

import com.example.sklepinternetowy.services.FileService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.google.common.io.ByteStreams.toByteArray;
import static org.apache.tomcat.util.http.fileupload.IOUtils.*;


@PreAuthorize("permitAll()")
@RestController
@RequestMapping
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PreAuthorize("hasAuthority('Admin')")
    @RequestMapping(value="api/v1/api/file/", method= RequestMethod.POST,
            consumes= {"multipart/form-data"})
    public ResponseEntity<String> save( @RequestPart("file") MultipartFile file){

        String filename = fileService.storeFile(file);

        return new ResponseEntity<>("http://195.80.229.73:443/photo/"+filename, HttpStatus.OK);
    }



    @GetMapping("/photo/{fileName:.+}")
    public ResponseEntity downloadFileFromLocal(@PathVariable String fileName) {
        Path path = Paths.get("./photo/" + fileName);
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
