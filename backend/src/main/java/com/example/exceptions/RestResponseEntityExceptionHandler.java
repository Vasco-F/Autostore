package com.example.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler(value = {VehicleNotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundConflict(RuntimeException err, WebRequest req){
        String bodyOfResponse = err.getMessage();
        return handleExceptionInternal(err, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, req);
    }

    @ExceptionHandler(value = { InvalidRegistrationYearException.class, InvalidVehicleRegistrationInfo.class})
    protected ResponseEntity<Object> handleBadRequestConflict(RuntimeException err, WebRequest req){
        String bodyOfResponse = err.getMessage();
        return handleExceptionInternal(err, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, req);
    }

    @ExceptionHandler(value = {InvalidVehicleRegistration.class})
    protected ResponseEntity<Object> handleServerErrorConflict(RuntimeException err, WebRequest req){
        String bodyOfResponse = err.getMessage();
        return handleExceptionInternal(err, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, req);
    }
}
