package org.example.springboottest.Service;

public class EmployeeNotQualifiedException extends Throwable {
    public EmployeeNotQualifiedException(String s) {
        super(s);
    }
}
