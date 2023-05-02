package com.rrlira96.origininsurancebackend.controller.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidHandler(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String title = "Validation failed";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse(new ArrayList<>());
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

        for (ObjectError error : allErrors) {
            String field = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            String detail = String.format("Field %s %s", field, errorMessage);
            StandardError errorObject = new StandardError(Instant.now(), status.value(), title, detail, request.getRequestURI());
            errorResponse.getErrors().add(errorObject);
        }

        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> httpMessageNotReadableHandler(InvalidFormatException ex, HttpServletRequest request) {
        String title = "Validation failed";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = String.format("Invalid value: %s", ex.getValue());

        ErrorResponse errorResponse = new ErrorResponse(new ArrayList<>());
        StandardError errorObject = new StandardError(Instant.now(), status.value(), title, message, request.getRequestURI());
        errorResponse.getErrors().add(errorObject);

        return ResponseEntity.status(status).body(errorResponse);
    }


}
