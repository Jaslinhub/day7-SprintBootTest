package org.example.springboottest.Service;

import org.example.springboottest.Dto.UpdateEmployeeReq;
import org.example.springboottest.Entity.Employee;
import org.example.springboottest.Exception.*;
import org.example.springboottest.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;


    public Employee addEmployee(Employee employee) throws EmployeeNotQualifiedException, EmployeeAlreadyExistsException {
        int age=employee.getAge();
        if (age!=0) {
            if(age<18||age>65){
                throw new EmployeeNotAmongLeaglAgeException("Employee age must be between 18 and 65");
            }
            if(age>30&&employee.getSalary()<20000){
                throw new EmployeeNotQualifiedException("For employees older than 30, the salary must be at least 20000");
            }
        }
        List<Employee> sameNameGenderEmployees = employeeRepository.getAllEmployeesByGender(employee.getGender());
        for (Employee e : sameNameGenderEmployees) {
            if (e.getName().equals(employee.getName())) {
                throw new EmployeeAlreadyExistsException("Employee with same name and gender already exists");
            }
        }
        employee.setStatus(true);
        employeeRepository.add(employee);
        return employee;
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

    public Employee updateEmployeeById(int id, UpdateEmployeeReq updateEmployeeReq) throws EmployeeAlreadyInactiveException {
        Employee employee = employeeRepository.getEmployeeById(id);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee with id " + id + " not found");
        }
        if (!employee.getStatus()) {
            throw new EmployeeAlreadyInactiveException("Employee with id " + id + " is inactive");
        }
        employee.setName(updateEmployeeReq.getName());
        employee.setAge(updateEmployeeReq.getAge());
        employee.setGender(updateEmployeeReq.getGender());
        employee.setSalary(updateEmployeeReq.getSalary());
        return employeeRepository.updateEmployeeById(id,employee);
    }

    public void deleteEmployeeById(int id) throws EmployeeAlreadyInactiveException {
        Employee employee= employeeRepository.getEmployeeById(id);
        if(employee==null){
            throw new EmployeeNotFoundException("Employee with id "+id+" not found");
        }
        if(!employee.getStatus()){
            throw new EmployeeAlreadyInactiveException("Employee with id "+id+" is already inactive");
        }
        employeeRepository.deleteEmployeeById(id);
    }

    public List<Employee> getEmployeesByPage(int page, int pageSize) {
        List<Employee> result =  employeeRepository.getEmployeesByPage(page,pageSize);
        if(result.isEmpty()){
            throw new EmployeeNotFoundException("No employees found for the given page and page size");
        }
        return result;
    }

    public void clear() {
        employeeRepository.clear();

    }
}
