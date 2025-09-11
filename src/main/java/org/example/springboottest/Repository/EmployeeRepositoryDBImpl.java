package org.example.springboottest.Repository;

import org.example.springboottest.Entity.Employee;
import org.example.springboottest.Repository.Dao.EmployeeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class EmployeeRepositoryDBImpl implements EmployeeRepository {
   @Autowired
    private EmployeeJpaRepository employeeJpaRepository;

    @Override
    public void add(Employee employee) {
        employeeJpaRepository.save(employee);
    }

    @Override
    public List<Employee> getAll() {
        return employeeJpaRepository.findAll();
    }

    @Override
    public void clear() {
        employeeJpaRepository.deleteAll();
    }

    @Override
    public Employee getEmployeeById(int id) {
        if(employeeJpaRepository.findById(id).isEmpty()){
            return null;
        }
        return employeeJpaRepository.findById(id).get();
    }

    @Override
    public List<Employee> getAllEmployeesByGender(String gender) {
        return employeeJpaRepository.findEmployeeByGender(gender);
    }

    @Override
    public Employee updateEmployeeById(int id, Employee updatedEmployee) {
        if (employeeJpaRepository.findById(id).isEmpty()) {
            return null;
        }
        return employeeJpaRepository.save(updatedEmployee);
    }

    @Override
    public void deleteEmployeeById(int id) {
        employeeJpaRepository.deleteById(id);
    }

    @Override
    public List<Employee> getEmployeesByPage(int page, int pageSize) {
        return employeeJpaRepository.findAll(PageRequest.of(page-1,pageSize)).getContent();
    }
}
