package com.edu.fileservice.service.util.impl;

import com.edu.fileservice.exceptions.ResourceNotFoundException;
import com.edu.fileservice.model.File;
import com.edu.fileservice.service.util.interfaces.FileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//@Profile(value = {"local"})
@Slf4j
@Component
public class LocalFileManager implements FileManager {


    private final String directoryPath;

    public LocalFileManager(@Value("${file.storage.path}") String directoryPath) {
        this.directoryPath = directoryPath;
    };

    @Override
    public void upload(byte[] resource, String filename) throws FileSystemException {
        log.info("Uploading file ");
        var path = Paths.get(directoryPath + "\\" + filename );
        try {
            //.createDirectories(path.getParent());
            Path file = Files.createFile(path);
            try (FileOutputStream stream = new FileOutputStream(file.toString())) {
                stream.write(resource);
            }
        } catch (IOException e) {
            log.error("Could not save file !!!");
            throw new FileSystemException("Could not save file !!!");
        }
        log.info("Uploading file successful completed !!!");
    }

    @Override
    public Resource download(File file) {
        log.info("Downloading file :{}", file);
        try {
            var path = Path.of(directoryPath + file.getName());
            var resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                log.info("Downloading file :{} successful completed !!!", file);
                return resource;
            } else {
                log.error("Could not read file: {} !!!", file);
                throw new ResourceNotFoundException("Could not read file: " + file.getName());
            }
        } catch (MalformedURLException e) {
            log.error("Could not read file: {} !!!", file);
            throw new ResourceNotFoundException("Could not read file: " + file.getName());
        }
    }

    @Override
    public void delete(File file) {
        var path = Path.of(directoryPath + file.getName());
        log.info("Deleting file :{}", file);
        try {
            Files.delete(path);
            log.info("Deleting file :{} successful completed !!!", file);
        } catch (IOException e) {
            log.error("Could not delete file: {}", file);
            throw new ResourceNotFoundException("Could not find file: " + file.getName());
        }
    }
}
