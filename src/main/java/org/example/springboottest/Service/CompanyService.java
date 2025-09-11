package org.example.springboottest.Service;

import org.example.springboottest.Entity.Company;
import org.example.springboottest.Exception.CompanyNotFundException;
import org.example.springboottest.Repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;


    public Map<String,Object> addCompany(Company company) {
        companyRepository.add(company);
        return Map.of("id",company.getId(),"name",company.getName());
    }

    public List<Company> getAllCompanies() {
        return companyRepository.getAllCompanies();
    }

    public Company getCompanyById(int id) {
        return companyRepository.getCompanyById(id);
    }

    public Company updateCompanyById(int id, Company updatedCompany) {
        return companyRepository.updateCompanyById(id,updatedCompany);
    }

    public void deleteCompanyById(int id) {
        companyRepository.deleteCompanyById(id);
    }

    public List<Company> getCompaniesByPage(int page, int pageSize) throws CompanyNotFundException {
        List<Company> allCompanies = companyRepository.getCompaniesByPage(page, pageSize);
        if(allCompanies.isEmpty()){
            throw new CompanyNotFundException("No companies available");
        }
        return allCompanies;
    }

    public void clearCompanies() {
        companyRepository.clear();
    }
}
