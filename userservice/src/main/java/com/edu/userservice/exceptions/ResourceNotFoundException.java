package com.edu.userservice.exceptions;

public class ResourceNotFoundException extends CrmException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}