package org.example.springboottest.Controller;

import org.example.springboottest.Entity.Company;
import org.example.springboottest.Entity.Employee;
import org.example.springboottest.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
public class CompanyController {
    @Autowired
    private CompanyService companyService;


    @PostMapping("/companies")
    public Map<String,Object> createCompany(@RequestBody Company company){
        companyService.addCompany(company);
        return Map.of("id",company.getId(),"name",company.getName());

    }
    @GetMapping("/companies")
    public List<Company> getAllCompanies(){
        return companyService.getAllCompanies();
    }
    @GetMapping("/companies/{id}")
    public Company getCompanyById(@PathVariable int id){
        return companyService.getCompanyById(id);
    }
    @PutMapping("/companies/{id}")
    public Company updateCompanyById(@PathVariable int id,@RequestBody Company updatedCompany) {
        return companyService.updateCompanyById(id,updatedCompany);
    }
@DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompanyById(@PathVariable int id){
    return companyService.deleteCompanyById(id);
    }
@GetMapping("/companies/page")
    public ResponseEntity<List<Company>> getCompaniesByPage(@RequestParam int page,@RequestParam int pageSize){
        return companyService.getCompaniesByPage(page,pageSize);
    }

    public void clearCompanies() {
        companyService.clearCompanies();

    }
}
