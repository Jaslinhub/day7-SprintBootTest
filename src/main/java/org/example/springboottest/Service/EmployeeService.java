package org.example.springboottest.Service;

import org.example.springboottest.Entity.Employee;
import org.example.springboottest.Repository.EmployeeRepository;
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
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;


    public Map<String,Object> addEmployee(Employee employee) throws EmployeeNotQualifiedException, EmployeeAlreadyExistsException {
        int age=employee.getAge();
        if(age<18||age>65){
            throw new EmployeeNotAmongLeaglAgeException("Employee age must be between 18 and 65");
        }
        if(age>30&&employee.getSalary()<20000){
            throw new EmployeeNotQualifiedException("For employees older than 30, the salary must be at least 20000");
        }

        List<Employee> sameNameGenderEmployees = employeeRepository.getAllEmployeesByGender(employee.getGender());
        for (Employee e : sameNameGenderEmployees) {
            if (e.getName().equals(employee.getName())) {
                throw new EmployeeAlreadyExistsException("Employee with same name and gender already exists");
            }
        }
        employee.setStatus(true);
        employeeRepository.add(employee);

        return Map.of("id",employee.getId(),"name",employee.getName());

    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.getAll();
    }

    public Employee getEmployeeById(int id) {

        Employee employee= employeeRepository.getEmployeeById(id);
        if(employee==null){
            throw new EmployeeNotFoundException("Employee with id "+id+" not found");
        }
        return employee;

    }

    public List<Employee> getAllEmployeesByGender(String gender) {
        return employeeRepository.getAllEmployeesByGender(gender);

    }

    public Employee updateEmployeeById(int id, Employee updatedEmployee) {
        return employeeRepository.updateEmployeeById(id,updatedEmployee);
    }

    public ResponseEntity<Void> deleteEmployeeById(int id) {
        return employeeRepository.deleteEmployeeById(id);
    }

    public ResponseEntity<List<Employee>> getEmployeesByPage(int page, int pageSize) {
        return employeeRepository.getEmployeesByPage(page,pageSize);
    }

    public void clear() {
        employeeRepository.clear();

    }
}
