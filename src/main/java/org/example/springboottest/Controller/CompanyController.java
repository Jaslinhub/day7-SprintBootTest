package org.example.springboottest.Controller;

import org.example.springboottest.Entity.Company;
import org.example.springboottest.Entity.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
