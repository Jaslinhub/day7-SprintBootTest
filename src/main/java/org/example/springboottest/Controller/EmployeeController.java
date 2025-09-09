package org.example.springboottest.Controller;

import org.example.springboottest.Entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {
    private List<Employee> employeeList=new ArrayList<>();

    @PostMapping("/employees")
    public Map<String,Object> createEmployee(@RequestBody Employee employee){
        int employeeId=employeeList.size();
        employee.setId(employeeId);
        employeeList.add(employee);
        return Map.of("id",employeeId,"name",employee.getName());

    }
    @GetMapping("/employees/All")
    public List<Employee> getAllEmployees(){
        return employeeList;
    }
    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable int id){
        for(Employee employee:employeeList){
            if(employeeList.indexOf(employee)==id){
                return employee;
            }
        }
        return null;
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployeesByGender(@RequestParam String gender){
        List<Employee> filteredEmployees=new ArrayList<>();
        for(Employee employee:employeeList){
            if(employee.getGender().equals(gender)){
                filteredEmployees.add(employee);
            }
    }
        return filteredEmployees;
    }
    @PutMapping("/employees/{id}")
    public Employee updateEmployeeById(@PathVariable int id,@RequestBody Employee updatedEmployee) {
        for (Employee employee : employeeList) {
            if (employeeList.indexOf(employee) == id) {
                employee.setName(updatedEmployee.getName());
                employee.setGender(updatedEmployee.getGender());
                employee.setSalary(updatedEmployee.getSalary());
                return employee;
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with ID: " + id);
    }

}
