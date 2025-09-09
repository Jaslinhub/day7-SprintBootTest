package org.example.springboottest.Controller;

import org.example.springboottest.Entity.Company;
import org.example.springboottest.Entity.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CompanyController {
    private List<Company> companies = new ArrayList<>();
    @PostMapping("/companies")
    public Map<String,Object> createCompany(@RequestBody Company company){
        int companyId=companies.size()+1;
        company.setId(companyId);
        companies.add(company);
        return Map.of("id",companyId,"name",company.getName());

    }
    @GetMapping("/companies")
    public List<Company> getAllCompanies(){
        return companies;
    }
    @GetMapping("/companies/{id}")
    public Company getCompanyById(@PathVariable int id){
        for(Company company:companies){
            if(companies.indexOf(company)==id){
                return company;
            }
        }
        return null;
    }

}
