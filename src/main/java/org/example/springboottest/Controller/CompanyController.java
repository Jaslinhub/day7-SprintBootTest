package org.example.springboottest.Controller;

import org.example.springboottest.Entity.Company;
import org.example.springboottest.Entity.Employee;
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
    private List<Company> companies = new ArrayList<>();
    private int currentId = 1;
    @PostMapping("/companies")
    public Map<String,Object> createCompany(@RequestBody Company company){
        company.setId(currentId);
        currentId++;
        companies.add(company);
        return Map.of("id",company.getId(),"name",company.getName());

    }
    @GetMapping("/companies")
    public List<Company> getAllCompanies(){
        return companies;
    }
    @GetMapping("/companies/{id}")
    public Company getCompanyById(@PathVariable int id){
        for(Company company:companies){
            if(company.getId()==id){
                return company;
            }
        }
        return null;
    }
    @PutMapping("/companies/{id}")
    public Company updateCompanyById(@PathVariable int id,@RequestBody Company updatedCompany) {
        for (Company company : companies) {
            if (company.getId()==id) {
                company.setName(updatedCompany.getName());
                return company;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found with ID: " +id);
    }
@DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompanyById(@PathVariable int id){
    Iterator<Company> iterator = companies.iterator();
    while (iterator.hasNext()) {
        Company company = iterator.next();
        if (company.getId() == id) {
            iterator.remove();
            return ResponseEntity.noContent().build();
        }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found with ID: " +id);
    }
@GetMapping("/companies/page")
    public ResponseEntity<List<Company>> getCompaniesByPage(@RequestParam int page,@RequestParam int pageSize){
        int startIndex=(page-1)*pageSize;
        int endIndex=Math.min(startIndex+pageSize,companies.size());
        if(startIndex>=companies.size()){
            return ResponseEntity.notFound().build();
        }
    return ResponseEntity.status(HttpStatus.OK).body(companies.subList(startIndex,endIndex));
    }

    public void clearCompanies() {
        companies.clear();
        currentId = 1;

    }
}
