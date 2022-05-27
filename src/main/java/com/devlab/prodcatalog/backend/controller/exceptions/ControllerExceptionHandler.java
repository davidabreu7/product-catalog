package com.devlab.prodcatalog.backend.controller.exceptions;

import com.devlab.prodcatalog.backend.exceptions.DatabaseIntegrityException;
import com.devlab.prodcatalog.backend.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        return getStandardErrorResponseEntity(e, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DatabaseIntegrityException.class)
    public ResponseEntity<StandardError> database(DatabaseIntegrityException e, HttpServletRequest request) {
        return getStandardErrorResponseEntity(e, request, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<StandardError> getStandardErrorResponseEntity(RuntimeException e, HttpServletRequest request, HttpStatus status) {
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setMessage(e.getMessage());
        err.setError("Resource not found");
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

}
