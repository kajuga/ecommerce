package com.edu.userservice.service;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class Service {
    @Getter
    private final ObjectReader objectReader;

    public Service(ObjectReader objectReader) {
        this.objectReader = objectReader;
    }
}