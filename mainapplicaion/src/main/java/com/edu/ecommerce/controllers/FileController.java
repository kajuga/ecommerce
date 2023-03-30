package com.edu.ecommerce.controllers;

import com.edu.ecommerce.model.File;
import com.edu.ecommerce.model.FileType;
import com.edu.ecommerce.model.Role;
import com.edu.ecommerce.security.AccessRole;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileSystemException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@CrossOrigin
@AccessRole(value = {Role.ADMINISTRATOR,
        Role.EXTERNAL, Role.MANAGER, Role.SPECIALIST})
public class FileController {

    private final RestTemplate restTemplate;

    @Autowired
    public FileController(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @GetMapping(path = "/download/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ApiOperation(value = "Download file by file ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get one file"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Resource> downloadFileByID(@NotNull @PathVariable("id") Long id) throws AccessDeniedException {
        return restTemplate.getForEntity("http://localhost:8081/file/download/"+ id, Resource.class);
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
        return restTemplate.getForEntity("http://localhost:8081/file/"+ id, File.class);
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
        ResponseEntity<File[]> responseEntity = restTemplate.getForEntity("http://localhost:8081/file", File[].class);
        List<File> files = Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));
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
        restTemplate.delete("http://localhost:8081/file/"+ id, File.class);
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
    public ResponseEntity<File> createFile(@RequestBody MultipartFile multipartFile, FileType type) throws FileSystemException {

            Resource multipartFileResource = multipartFile.getResource();

            LinkedMultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
            parts.add("file", multipartFileResource);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(parts, httpHeaders);

        return restTemplate.postForEntity("http://localhost:8081/file/" , httpEntity, File.class);
    }
}
