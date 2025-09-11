package org.example.springboottest.Repository;

import org.example.springboottest.Entity.Company;

import java.util.List;

public interface CompanyRepository {
    void add(Company company);

    List<Company> getAllCompanies();

    Company getCompanyById(int id);

    Company updateCompanyById(int id, Company updatedCompany);

    void deleteCompanyById(int id);

    List<Company> getCompaniesByPage(int page, int pageSize);

    void clear();
}
