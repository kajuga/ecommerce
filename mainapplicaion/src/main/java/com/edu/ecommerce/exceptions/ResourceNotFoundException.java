package com.edu.ecommerce.exceptions;

public class ResourceNotFoundException extends CrmException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}