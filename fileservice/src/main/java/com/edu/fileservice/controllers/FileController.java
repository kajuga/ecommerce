package com.edu.fileservice.controllers;

import com.edu.fileservice.model.File;
import com.edu.fileservice.model.FileType;
import com.edu.fileservice.service.interfaces.FileService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileSystemException;
import java.util.List;


@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@CrossOrigin
public class FileController {

    private final FileService fileService;


    @GetMapping(path = "/download/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ApiOperation(value = "Download file by file ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get one file"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Resource> downloadFileByID(@NotNull @PathVariable("id") Long id) throws AccessDeniedException {
        var foundFile = fileService.findById(id);
        var resource = fileService.downloadFile(foundFile);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + foundFile.getName())
                .body(resource);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get file information by file ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get one file"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<File> getFileInfoByID(@NotNull @PathVariable("id") Long id) {
        var file = fileService.findById(id);
        return ResponseEntity.ok(file);
    }

    @GetMapping()
    @ApiOperation(value = "Get all files")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get one file"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<List<File>> getAllFiles() {
        var files = fileService.getAllFiles();

        return ResponseEntity.ok(files);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete file")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully delete file"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Void> delete(@NotNull @PathVariable("id") Long id) {
        fileService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Upload file to server")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully uploading file"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<File> createFile(@RequestBody MultipartFile file, FileType type) throws FileSystemException {
        var reqFile = fileService.uploadFile(file, type);
        return ResponseEntity.ok(reqFile);
    }
}
