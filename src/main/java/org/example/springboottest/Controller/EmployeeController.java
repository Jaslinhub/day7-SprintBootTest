package org.example.springboottest.Controller;

import org.example.springboottest.Dto.UpdateEmployeeReq;
import org.example.springboottest.Entity.Employee;
import org.example.springboottest.Exception.EmployeeAlreadyExistsException;
import org.example.springboottest.Exception.EmployeeAlreadyInactiveException;
import org.example.springboottest.Exception.EmployeeNotQualifiedException;
import org.example.springboottest.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employees")
    public Map<String,Object> createEmployee(@RequestBody Employee employee) throws EmployeeNotQualifiedException, EmployeeAlreadyExistsException {
        Employee created = employeeService.addEmployee(employee);
        return Map.of("id", created.getId(), "name", created.getName());
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
    public Employee updateEmployeeById(@PathVariable int id,@RequestBody UpdateEmployeeReq updatedEmployeeReq) throws EmployeeAlreadyInactiveException {
        return employeeService.updateEmployeeById(id,updatedEmployeeReq);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable int id) throws EmployeeAlreadyInactiveException {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employees/page")
    public ResponseEntity<List<Employee>> getEmployeesByPage(@RequestParam int page,@RequestParam int pageSize){
        List<Employee> employees = employeeService.getEmployeesByPage(page,pageSize);
        return ResponseEntity.ok(employees);
    }

    public void clearEmployees() {
        employeeService.clear();

    }
}
