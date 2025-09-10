package org.example.springboottest.Repository;

import org.example.springboottest.Entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class EmployeeRepository {
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

    public ResponseEntity<Void> deleteEmployeeById(int id) {
        Iterator<Employee> iterator = employeeList.iterator();
        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            if (employee.getId() == id) {
                iterator.remove();
                return ResponseEntity.noContent().build();
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with ID: " + id);
    }

    public ResponseEntity<List<Employee>> getEmployeesByPage(int page, int pageSize) {
        int startIndex=(page-1)*pageSize;
        int endIndex=Math.min(startIndex+pageSize,employeeList.size());
        if(startIndex>=employeeList.size()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(employeeList.subList(startIndex,endIndex));
    }
}
