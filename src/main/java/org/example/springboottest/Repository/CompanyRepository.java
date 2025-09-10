package org.example.springboottest.Repository;

import org.example.springboottest.Entity.Company;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class CompanyRepository {
    private List<Company> companyList=new ArrayList<>();
    private int currentId=1;

    public void add(Company company) {
        company.setId(currentId);
        currentId++;
        companyList.add(company);
    }

    public List<Company> getAllCompanies() {
        return companyList;
    }

    public Company getCompanyById(int id) {
        for(Company company:companyList){
            if(company.getId()==id){
                return company;
            }
        }
        return null;
    }

    public Company updateCompanyById(int id, Company updatedCompany) {
        for (Company company : companyList) {
            if (company.getId()==id) {
                company.setName(updatedCompany.getName());
                return company;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found with ID: " +id);
    }

    public ResponseEntity<Void> deleteCompanyById(int id) {
        Iterator<Company> iterator = companyList.iterator();
        while (iterator.hasNext()) {
            Company company = iterator.next();
            if (company.getId() == id) {
                iterator.remove();
                return ResponseEntity.noContent().build();
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found with ID: " +id);
    }

    public ResponseEntity<List<Company>> getCompaniesByPage(int page, int pageSize) {
        int startIndex=(page-1)*pageSize;
        int endIndex=Math.min(startIndex+pageSize,companyList.size());
        if(startIndex>=companyList.size()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(companyList.subList(startIndex,endIndex));
    }

    public void clear() {
        companyList.clear();
        currentId = 1;
    }
}
