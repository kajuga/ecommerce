package com.edu.ecommerce.exceptions;

import javax.validation.ConstraintViolationException;

public class ArgumentNotValidException extends ConstraintViolationException {

    public ArgumentNotValidException(String message) {
        super(message, null);
    }
}