package com.edu.userservice.exceptions;

/**
 * Base class for all Huntflow CRM exceptions
 */
public class CrmException extends RuntimeException {
    public CrmException(Exception e) {
        super(e);
    }

    public CrmException(String msg) {
        super(msg);
    }

    public CrmException(String format, Object... args) {
        super(String.format(format, args));
    }

    public CrmException(String message, Exception e) {
        super(message, e);
    }
}