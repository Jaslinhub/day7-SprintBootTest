package org.example.springboottest.Repository.Dao;

import org.example.springboottest.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeJpaRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findEmployeeByGender(String gender);
}
