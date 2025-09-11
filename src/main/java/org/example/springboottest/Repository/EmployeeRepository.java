package org.example.springboottest.Repository;

import org.example.springboottest.Entity.Employee;

import java.util.List;

public interface EmployeeRepository {
    void add(Employee employee);

    List<Employee> getAll();

    Employee getEmployeeById(int id);

    List<Employee> getAllEmployeesByGender(String gender);

    Employee updateEmployeeById(int id, Employee updatedEmployee);

    void deleteEmployeeById(int id);

    List<Employee> getEmployeesByPage(int page, int pageSize);

    void clear();
}
