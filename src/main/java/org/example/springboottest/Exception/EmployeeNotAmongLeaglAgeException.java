package org.example.springboottest.Exception;

public class EmployeeNotAmongLeaglAgeException extends RuntimeException {
    public EmployeeNotAmongLeaglAgeException(String s) {
        super(s);
    }
}
