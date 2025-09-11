package org.example.springboottest;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springboottest.Controller.EmployeeController;
import org.example.springboottest.Entity.Company;
import org.example.springboottest.Entity.Employee;
import org.example.springboottest.Exception.EmployeeAlreadyExistsException;
import org.example.springboottest.Exception.EmployeeNotQualifiedException;
import org.example.springboottest.Repository.CompanyRepository;
import org.example.springboottest.Repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;


    Employee employee = new Employee("Tom", "male", 1000);
    Employee employee2 = new Employee("Tom", "female", 2000);
    Employee employee3 = new Employee("Mickey", "male", 3000);
    Employee employee4 = new Employee("Donald", "male", 4000);
    Company testCompany;

    @BeforeEach
    public void setUp() {
        companyRepository.clear();
        employeeRepository.clear();
        testCompany = new Company("TestCompany");
        companyRepository.add(testCompany);
    }

    private ResultActions mockMvcPerformPost(String requestBody) throws Exception {
        return mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));
    }

    private int createEmployeeWithCompany(Employee employee) throws Exception {
        employee.setCompanyId(testCompany.getId());
        String requestBody = new ObjectMapper().writeValueAsString(employee);
        ResultActions resultActions = mockMvcPerformPost(requestBody);
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        return new ObjectMapper().readTree(contentAsString).get("id").asInt();
    }

    @Test
    void should_return_id_when_post_given_a_valid_employee() throws Exception {
        employee.setCompanyId(testCompany.getId());
        String requestBody = new ObjectMapper().writeValueAsString(employee);
        ResultActions resultActions = mockMvcPerformPost(requestBody);
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        long id = new ObjectMapper().readTree(contentAsString).get("id").asLong();
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void should_return_employees_when_get_all() throws Exception {
        int id = createEmployeeWithCompany(employee);
        mockMvc.perform(get("/employees/All").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(employee.getName()))
                .andExpect(jsonPath("$[0].gender").value(employee.getGender()))
                .andExpect(jsonPath("$[0].salary").value(employee.getSalary()));
    }

    @Test
    void should_return_employees_when_get_given_id() throws Exception {
        int id = createEmployeeWithCompany(employee);
        mockMvc.perform(get("/employees/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(employee.getName()))
                .andExpect(jsonPath("$.gender").value(employee.getGender()))
                .andExpect(jsonPath("$.salary").value(employee.getSalary()));
    }

    @Test
    void should_return_employees_when_get_given_gender() throws Exception {
        int id1 = createEmployeeWithCompany(employee);
        int id2 = createEmployeeWithCompany(employee2);
        mockMvc.perform(get("/employees?gender=male")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id1))
                .andExpect(jsonPath("$[0].name").value(employee.getName()))
                .andExpect(jsonPath("$[0].gender").value(employee.getGender()))
                .andExpect(jsonPath("$[0].salary").value(employee.getSalary()))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void should_response_no_content_when_delete_given_employee_id() throws Exception {
        int id = createEmployeeWithCompany(employee);
        mockMvc.perform(delete("/employees/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_return_employees_when_get_by_page_given_page_size() throws Exception {
        int id1 = createEmployeeWithCompany(employee);
        int id2 = createEmployeeWithCompany(employee2);
        int id3 = createEmployeeWithCompany(employee3);
        int id4 = createEmployeeWithCompany(employee4);
        mockMvc.perform(get("/employees/page?page=1&pageSize=5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id1))
                .andExpect(jsonPath("$[0].name").value(employee.getName()))
                .andExpect(jsonPath("$[0].gender").value(employee.getGender()))
                .andExpect(jsonPath("$[0].salary").value(employee.getSalary()))
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    public void should_update_employee_when_update_by_id() throws Exception {
        int id = createEmployeeWithCompany(employee);
        String requestBody = String.format("""
                {
                    "id": %d,
                    "name": "Tom",
                    "salary": 1000,
                    "age": 20,
                    "gender": "male",
                    "companyId": %d,
                    "status": false
                }
                """.formatted(id, testCompany.getId()));
        mockMvc.perform(put("/employees/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(employee.getName()))
                .andExpect(jsonPath("$.gender").value(employee.getGender()))
                .andExpect(jsonPath("$.salary").value(employee.getSalary()))
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    void should_throw_exception_when_get_given_page_out_of_all() throws Exception {
        int id1 = createEmployeeWithCompany(employee);
        int id2 = createEmployeeWithCompany(employee2);
        int id3 = createEmployeeWithCompany(employee3);
        int id4 = createEmployeeWithCompany(employee4);
        mockMvc.perform(get("/employees/page?page=3&pageSize=5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_throw_exception_when_get_given_employee_not_exsiting() throws Exception {
        String requestBody = String.format("""
                {
                    "name": "Tom",
                    "salary": 1000,
                    "age": 20,
                    "gender": "male",
                    "companyId": %d
                }
                """, testCompany.getId());
        mockMvc.perform(put("/employees/{id}", -1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_company_with_employee() throws Exception {
        Company company = new Company("alibaba");
        companyRepository.add(company);

        Employee employee = new Employee();
        employee.setSalary(80000);
        employee.setAge(35);
        employee.setGender("male");
        employee.setCompanyId(company.getId());
        employeeRepository.add(employee);

        mockMvc.perform(get("/companies/{id}", company.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(company.getId()))
                .andExpect(jsonPath("$.employee.length()").value(1));
    }


    }
