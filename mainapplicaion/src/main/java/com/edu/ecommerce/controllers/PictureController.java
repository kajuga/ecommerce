package com.edu.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/pictures")
public class PictureController {

    @Value("${ecommerce.pictures.urls}")
    private String picturesUrl;

    @GetMapping("/category/{name}")
    public ResponseEntity<Resource> downloadCategory(@PathVariable("name")String name) throws IOException {
        String fileName = picturesUrl + "/category/" + name;


        InputStreamResource resource = new InputStreamResource(new FileInputStream(fileName));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/product/{name}")
    public ResponseEntity<Resource> downloadProduct(@PathVariable("name")String name) throws IOException {
        String fileName = picturesUrl + "/product/" + name;


        InputStreamResource resource = new InputStreamResource(new FileInputStream(fileName));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
