package com.edu.fileservice.service.interfaces;

import com.edu.fileservice.model.File;
import com.edu.fileservice.model.FileType;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileSystemException;
import java.util.List;

public interface FileService {

    File findById(Long fileId);

    File uploadFile(MultipartFile resource, FileType type) throws IOException;

    File uploadFileIcon(MultipartFile resource, FileType type) throws FileSystemException;

    Resource downloadFile(File file) throws AccessDeniedException;

    File deleteById(Long fileId);

    List<File> getAllFiles();

}
