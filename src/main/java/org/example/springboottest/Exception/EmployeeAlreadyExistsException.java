package org.example.springboottest.Exception;

public class EmployeeAlreadyExistsException extends Throwable {
    public EmployeeAlreadyExistsException(String s) {
        super(s);
    }
}
