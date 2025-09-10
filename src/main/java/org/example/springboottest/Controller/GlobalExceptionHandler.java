package org.example.springboottest.Controller;

import org.example.springboottest.Service.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmployeeAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEmployeeAlreadyExistsException(EmployeeAlreadyExistsException ex) {
        return ex.getMessage();
    }
    @ExceptionHandler(EmployeeNotAmongLeaglAgeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEmployeeNotAmongLeaglAgeException(EmployeeNotAmongLeaglAgeException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(EmployeeNotQualifiedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEmployeeNotQualifiedException(EmployeeNotQualifiedException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(EmployeeAlreadyInactiveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEmployeeAlreadyInactiveException(EmployeeAlreadyInactiveException ex) {
        return ex.getMessage(); }
}
