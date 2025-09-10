package org.example.springboottest.Service;

public class EmployeeAlreadyInactiveException extends Throwable {
    public EmployeeAlreadyInactiveException(String s) {
        super(s);
    }
}
