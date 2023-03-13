package com.edu.ecommerce.service.impl;


import com.edu.ecommerce.service.ObjectReader;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class FileReader implements ObjectReader {
    @Override
    public String readObject() {
        return "fileReader";
    }
}
