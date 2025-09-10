package org.example.springboottest.Service;

import org.example.springboottest.Entity.Company;
import org.example.springboottest.Repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Iterator;
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

    public ResponseEntity<Void> deleteCompanyById(int id) {
        return companyRepository.deleteCompanyById(id);
    }

    public ResponseEntity<List<Company>> getCompaniesByPage(int page, int pageSize) {
        return companyRepository.getCompaniesByPage(page,pageSize);
    }

    public void clearCompanies() {
        companyRepository.clear();
    }
}
