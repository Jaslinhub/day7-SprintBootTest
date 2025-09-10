package org.example.springboottest.Service;

public class EmployeeNotAmongLeaglAgeException extends RuntimeException {
    public EmployeeNotAmongLeaglAgeException(String s) {
        super(s);
    }
}
