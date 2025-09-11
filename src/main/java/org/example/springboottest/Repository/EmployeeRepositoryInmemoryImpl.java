package org.example.springboottest.Repository;

import org.example.springboottest.Entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

/*@Repository*/
public class EmployeeRepositoryInmemoryImpl implements EmployeeRepository {
    private List<Employee> employeeList=new ArrayList<>();
    private int currentId=1;

    public void add(Employee employee) {
        employee.setId(currentId);
        currentId++;
        employeeList.add(employee);
    }

    public List<Employee> getAll() {
        return employeeList;
    }

    public void clear() {
        employeeList.clear();
        currentId = 1;
    }

    public Employee getEmployeeById(int id) {
        for(Employee employee:employeeList){
            if(employee.getId()==id){
                return employee;
            }
        }
        return null;
    }

    public List<Employee> getAllEmployeesByGender(String gender) {
        List<Employee> filteredEmployees=new ArrayList<>();
        for(Employee employee:employeeList){
            if(employee.getGender().equals(gender)){
                filteredEmployees.add(employee);
            }
        }

        return filteredEmployees;
    }

    public Employee updateEmployeeById(int id, Employee updatedEmployee) {
        for (Employee employee : employeeList) {
            if (employee.getId()==id) {
                employee.setName(updatedEmployee.getName());
                employee.setGender(updatedEmployee.getGender());
                employee.setSalary(updatedEmployee.getSalary());
                return employee;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with ID: " + id);
    }

    public void deleteEmployeeById(int id) {
        for (Employee employee : employeeList) {
            if (employee.getId() == id) {
                employee.setStatus(false);
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with ID: " + id);
    }

    public List<Employee> getEmployeesByPage(int page, int pageSize) {
        int startIndex=(page-1)*pageSize;
        int endIndex=Math.min(startIndex+pageSize,employeeList.size());
        if(startIndex>=employeeList.size()){
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees found for page: " + page);
        }
        return employeeList.subList(startIndex,endIndex);
    }
}
