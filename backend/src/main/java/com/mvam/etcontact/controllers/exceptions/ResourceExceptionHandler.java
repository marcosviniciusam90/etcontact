package com.mvam.etcontact.controllers.exceptions;

import com.mvam.etcontact.services.exceptions.DatabaseIntegrityException;
import com.mvam.etcontact.services.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError standardError = createStandardError(request, status, "Resource not found", ex.getMessage());
        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(DatabaseIntegrityException.class)
    public ResponseEntity<StandardError> databaseIntegrityException(DatabaseIntegrityException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError standardError = createStandardError(request, status, "Database integrity violation", ex.getMessage());
        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError validationError = createValidationError(request, status, "Validation exception", ex);
        return ResponseEntity.status(status).body(validationError);
    }

    private StandardError createStandardError(HttpServletRequest request, HttpStatus status, String error, String message) {
        return StandardError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(error)
                .message(message)
                .path(request.getRequestURI())
                .build();
    }

    private ValidationError createValidationError(HttpServletRequest request, HttpStatus status, String error, MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        ValidationError validationError =
                ValidationError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(error)
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .errors(new ArrayList<>())
                .build();
        fieldErrors.forEach(f -> validationError.addError(f.getField(), f.getDefaultMessage()));
        return validationError;
    }
}
