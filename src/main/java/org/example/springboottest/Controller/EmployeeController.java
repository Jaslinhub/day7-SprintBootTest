package org.example.springboottest.Controller;

import org.example.springboottest.Entity.Employee;
import org.example.springboottest.Service.EmployeeAlreadyExistsException;
import org.example.springboottest.Service.EmployeeNotQualifiedException;
import org.example.springboottest.Service.EmployeeService;
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
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employees")
    public Map<String,Object> createEmployee(@RequestBody Employee employee) throws EmployeeNotQualifiedException, EmployeeAlreadyExistsException {;
        return employeeService.addEmployee(employee);


    }
    @GetMapping("/employees/All")
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();

    }

    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable int id){
       return employeeService.getEmployeeById(id);

    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployeesByGender(@RequestParam String gender){
        return employeeService.getAllEmployeesByGender(gender);

    }
    @PutMapping("/employees/{id}")
    public Employee updateEmployeeById(@PathVariable int id,@RequestBody Employee updatedEmployee) {
        return employeeService.updateEmployeeById(id,updatedEmployee);



    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable int id) {
        return employeeService.deleteEmployeeById(id);


        }

        @GetMapping("/employees/page")
        public ResponseEntity<List<Employee>> getEmployeesByPage(@RequestParam int page,@RequestParam int pageSize){
        return employeeService.getEmployeesByPage(page,pageSize);

        }

    public void clearEmployees() {
        employeeService.clear();

    }
}
