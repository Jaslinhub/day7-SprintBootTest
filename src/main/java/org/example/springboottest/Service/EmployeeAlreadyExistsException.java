package org.example.springboottest.Service;

public class EmployeeAlreadyExistsException extends Throwable {
    public EmployeeAlreadyExistsException(String s) {
        super(s);
    }
}
