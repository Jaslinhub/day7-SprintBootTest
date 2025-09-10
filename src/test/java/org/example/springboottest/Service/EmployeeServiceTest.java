package org.example.springboottest.Service;

import org.example.springboottest.Entity.Employee;
import org.example.springboottest.Repository.EmployeeRepository;
import org.example.springboottest.Service.EmployeeNotAmongLeaglAgeException;
import org.example.springboottest.Service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void should_not_create_employee_when_age_is_below_18() {
        Employee employee = new Employee();
        employee.setAge(17);
        employee.setName("John");
        employee.setGender("male");
        employee.setSalary(5000);
        assertThrows(EmployeeNotAmongLeaglAgeException.class, () -> {
            employeeService.addEmployee(employee);
        });
    }
    @Test
    void should_throw_exception_when_employee_is_not_existed() {
        when(employeeRepository.getEmployeeById(1)).thenReturn(null);
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.getEmployeeById(1);
        });
        verify(employeeRepository,times(1)).getEmployeeById(1);
    }
    @Test
    void should_not_create_employee_when_age_is_above_30_and_salary_below_20000() {
        Employee employee = new Employee();
        employee.setAge(47);
        employee.setName("John");
        employee.setGender("male");
        employee.setSalary(5000);
        assertThrows(EmployeeNotQualifiedException.class, () -> {
            employeeService.addEmployee(employee);
        });
        verify(employeeRepository, times(0)).add(any());
    }
    @Test
    void should_not_create_employee_when_age_is_below_30_and_salary_below_20000() throws EmployeeNotQualifiedException, EmployeeAlreadyExistsException {
        Employee employee = new Employee();
        employee.setAge(27);
        employee.setName("John");
        employee.setGender("male");
        employee.setSalary(5000);
        employeeService.addEmployee(employee);
        verify(employeeRepository, times(1)).add(any());
    }
    @Test
    void should_not_create_employee_when_age_is_below_30_and_salary_above_20000() throws EmployeeNotQualifiedException, EmployeeAlreadyExistsException {
        Employee employee = new Employee();
        employee.setAge(27);
        employee.setName("John");
        employee.setGender("male");
        employee.setSalary(25000);
        employeeService.addEmployee(employee);
        verify(employeeRepository, times(1)).add(any());
    }
    @Test
    void should_not_create_employee_when_age_is_above_30_and_salary_above_20000() throws EmployeeNotQualifiedException, EmployeeAlreadyExistsException {
        Employee employee = new Employee();
        employee.setAge(37);
        employee.setName("John");
        employee.setGender("male");
        employee.setSalary(25000);
        employeeService.addEmployee(employee);
        verify(employeeRepository, times(1)).add(any());
    }
    @Test
    void should_create_new_employee_when_status_is_true() throws EmployeeNotQualifiedException, EmployeeAlreadyExistsException {
        Employee employee = new Employee();
        employee.setAge(35);
        employee.setName("John");
        employee.setGender("male");
        employee.setSalary(25000);
        Employee existingEmployee = new Employee("John","female",3000);
        existingEmployee.setAge(28);
        employeeService.addEmployee(employee);
        employeeService.addEmployee(existingEmployee);
        assertTrue(employee.getStatus());
        verify(employeeRepository, times(2)).add(any());
    }
    @Test
    void should_not_create_new_employee_when_same_name_and_gender() throws EmployeeNotQualifiedException , EmployeeAlreadyExistsException {

        Employee existingEmployee = new Employee();
        existingEmployee.setName("John");
        existingEmployee.setGender("male");
        existingEmployee.setAge(30);
        existingEmployee.setSalary(25000);

        Employee newEmployee = new Employee();
        newEmployee.setName("John");
        newEmployee.setGender("male");
        newEmployee.setAge(35);
        newEmployee.setSalary(30000);

        when(employeeRepository.getAllEmployeesByGender("male"))
                .thenReturn(List.of(existingEmployee));
        assertThrows(EmployeeAlreadyExistsException.class, () -> {
            employeeService.addEmployee(newEmployee);
        });

        verify(employeeRepository, times(0)).add(any());
    }
    @Test
    void should_set_status_false_when_delete_employee() throws EmployeeNotFoundException, EmployeeNotQualifiedException, EmployeeAlreadyExistsException, EmployeeAlreadyInactiveException {
        Employee existingEmployee = new Employee();
        existingEmployee.setName("John");
        existingEmployee.setGender("male");
        existingEmployee.setAge(35);
        existingEmployee.setSalary(25000);
        employeeService.addEmployee(existingEmployee);
        when(employeeRepository.getEmployeeById(existingEmployee.getId())).thenReturn(existingEmployee);
        doAnswer(invocation -> {
                existingEmployee.setStatus(false);
            return null;
        }).when(employeeRepository).deleteEmployeeById(existingEmployee.getId());

        employeeService.deleteEmployeeById(existingEmployee.getId());

        assertFalse(existingEmployee.getStatus());
        verify(employeeRepository, times(1)).getEmployeeById(existingEmployee.getId());
        verify(employeeRepository, times(1)).deleteEmployeeById(existingEmployee.getId());
    }
    @Test
    void should_throw_exception_when_delete_already_inactive_employee() throws EmployeeNotQualifiedException, EmployeeAlreadyExistsException, EmployeeNotFoundException, EmployeeAlreadyInactiveException {
        Employee existingEmployee = new Employee();
        existingEmployee.setName("John");
        existingEmployee.setGender("male");
        existingEmployee.setAge(35);
        existingEmployee.setSalary(25000);
        employeeService.addEmployee(existingEmployee);


        when(employeeRepository.getEmployeeById(existingEmployee.getId())).thenReturn(existingEmployee);
        doAnswer(invocation -> {
            existingEmployee.setStatus(false);
            return null;
        }).when(employeeRepository).deleteEmployeeById(existingEmployee.getId());
        employeeService.deleteEmployeeById(existingEmployee.getId());


        assertThrows(EmployeeAlreadyInactiveException.class, () -> {
            employeeService.deleteEmployeeById(existingEmployee.getId());
        });
        verify(employeeRepository, times(2)).getEmployeeById(existingEmployee.getId());
    }
    @Test
    void should_throw_exception_when_update_inactive_employee() {
        Employee inactiveEmployee = new Employee();
        inactiveEmployee.setId(1);
        inactiveEmployee.setName("John");
        inactiveEmployee.setGender("male");
        inactiveEmployee.setAge(35);
        inactiveEmployee.setSalary(25000);
        inactiveEmployee.setStatus(false);

        when(employeeRepository.getEmployeeById(1)).thenReturn(inactiveEmployee);

        Employee updatedEmployee = new Employee();
        updatedEmployee.setName("John Updated");
        updatedEmployee.setGender("male");
        updatedEmployee.setAge(36);
        updatedEmployee.setSalary(26000);

        assertThrows(EmployeeAlreadyInactiveException.class, () -> {
            employeeService.updateEmployeeById(1, updatedEmployee);
        });
        verify(employeeRepository, times(1)).getEmployeeById(1);
    }
}