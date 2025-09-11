package org.example.springboottest.Exception;

public class CompanyNotFundException extends Throwable {
    public CompanyNotFundException(String noCompaniesAvailable) {
        super(noCompaniesAvailable);
    }
}
