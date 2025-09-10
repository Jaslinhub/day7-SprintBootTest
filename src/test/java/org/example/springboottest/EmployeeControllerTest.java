package org.example.springboottest;


import org.example.springboottest.Controller.EmployeeController;
import org.example.springboottest.Entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeController employeeController;

    Employee employee = new Employee("Tom", "male", 1000);
    Employee employee2 = new Employee("Tom", "female", 2000);
    Employee employee3 = new Employee("Mickey", "male", 3000);
    Employee employee4 = new Employee("Donald", "male", 4000);
    @BeforeEach
    public void setUp() {
        employeeController.clearEmployees();
    }

    @Test
    public void should_return_id_when_post_given_a_valid_employee() throws Exception {
        String requestBody = """
                {
                    "name": "Tom",
                    "gender": "male",
                    "salary": 1000
                }
                """;
        mockMvc.perform(post("/employees").
                        contentType(MediaType.APPLICATION_JSON_VALUE).
                        content(requestBody)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id").value(1));
    }
    @Test
    public void should_return_employees_when_get_all_given_null() throws Exception {
        employeeController.createEmployee(employee);
        mockMvc.perform(get("/employees/All").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value(employee.getName()))
                .andExpect(jsonPath("$[0].gender").value(employee.getGender()))
                .andExpect(jsonPath("$[0].salary").value(employee.getSalary()));
    }
    @Test
    public void should_return_employees_when_get_given_id() throws Exception {
        employeeController.createEmployee(employee);
        mockMvc.perform(get("/employees/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(employee.getName()))
                .andExpect(jsonPath("$.gender").value(employee.getGender()))
                .andExpect(jsonPath("$.salary").value(employee.getSalary()));
    }

    @Test
    public void should_return_employees_when_get_given_gender() throws Exception {
        employeeController.createEmployee(employee);
        employeeController.createEmployee(employee2);
        mockMvc.perform(get("/employees?gender=male")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(employee.getId()))
                .andExpect(jsonPath("$[0].name").value(employee.getName()))
                .andExpect(jsonPath("$[0].gender").value(employee.getGender()))
                .andExpect(jsonPath("$[0].salary").value(employee.getSalary()))
                .andExpect(jsonPath("$.length()").value(1));
    }
    @Test
    public void should_response_no_content_when_delete_given_employee_id() throws Exception {
        employeeController.createEmployee(employee);
        mockMvc.perform(delete("/employees/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    @Test
    public void should_return_employees_when_get_by_page_given_page_size() throws Exception {
        employeeController.createEmployee(employee);
        employeeController.createEmployee(employee2);
        employeeController.createEmployee(employee3);
        employeeController.createEmployee(employee4);
        mockMvc.perform(get("/employees/page?page=1&pageSize=5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value(employee.getName()))
                .andExpect(jsonPath("$[0].gender").value(employee.getGender()))
                .andExpect(jsonPath("$[0].salary").value(employee.getSalary()))
                .andExpect(jsonPath("$.length()").value(4));
    }
    @Test
    public void should_return_matching_code_when_update_by_id_given_age_salary() throws Exception {
        employeeController.createEmployee(employee);
        String requestBody = """
                {
                    "name": "Tom",
                    "salary": 1000,
                    "age": 20,
                    "gender": "male"
                }
                """;
        mockMvc.perform(put("/employees/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(employee.getName()))
                .andExpect(jsonPath("$.gender").value(employee.getGender()))
                .andExpect(jsonPath("$.salary").value(1000));
    }


    @Test
    void should_throw_exception_when_get_given_page_out_of_all() throws Exception {
        employeeController.createEmployee(employee);
        employeeController.createEmployee(employee2);
        employeeController.createEmployee(employee3);
        employeeController.createEmployee(employee4);
        mockMvc.perform(get("/employees/page?page=3&pageSize=5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                ;

    }
    @Test
    void should_throw_exception_when_get_given_employee_not_exsiting() throws Exception {
        employeeController.createEmployee(employee);
        String requestBody = """
                {
                    "name": "Tom",
                    "salary": 1000,
                    "age": 20,
                    "gender": "male"
                }
                """;
        mockMvc.perform(put("/employees/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound())
                ;

    }
    }
