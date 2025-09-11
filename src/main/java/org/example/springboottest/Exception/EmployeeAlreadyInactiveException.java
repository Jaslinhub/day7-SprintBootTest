package org.example.springboottest.Exception;

public class EmployeeAlreadyInactiveException extends Throwable {
    public EmployeeAlreadyInactiveException(String s) {
        super(s);
    }
}
