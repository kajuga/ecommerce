package com.edu.fileservice.exceptions;

public class ResourceNotFoundException extends CrmException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}