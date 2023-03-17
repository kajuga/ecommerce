package com.edu.fileservice.exceptions;

import javax.validation.ConstraintViolationException;

public class ArgumentNotValidException extends ConstraintViolationException {

    public ArgumentNotValidException(String message) {
        super(message, null);
    }
}