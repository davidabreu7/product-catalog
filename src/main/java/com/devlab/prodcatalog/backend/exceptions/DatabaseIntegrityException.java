package com.devlab.prodcatalog.backend.exceptions;

public class DatabaseIntegrityException extends RuntimeException {

    public DatabaseIntegrityException(String message) {
        super(message);
    }
}
