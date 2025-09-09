package org.example.springboottest;


import org.example.springboottest.Entity.Employee;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_return_employee_when_createEmployee() throws Exception {
        String requestBody="""
                {
                "name":"joe",
                "gender":"male",
                "salary":7000.0

}
""";
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(0))
                ;
    }

    @Test
    void should_return_employee_getEmployeeById() throws Exception {
        String requestBody="""
                {
                "name":"joe",
                "gender":"male",
                "salary":7000.0
                }
""";
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody))
                .andExpect(status().isOk());
        String requestBody2="""
                {
                "name":"jade",
                "gender":"female",
                "salary":78000.0
                }
""";
mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody2))
                .andExpect(status().isOk());
        mockMvc.perform(get("/employees/{id}",1)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("jade"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.gender").value("female"))
                .andExpect(jsonPath("$.salary").value(78000.0));
    }

    @Test
    void should_get_gender_employee_when_getAllEmployeesByGender() throws Exception {
        String requestBody="""
                {
                "name":"joe",
                "gender":"male",
                "salary":7000.0
                }
""";
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                        .andExpect(status().isOk());
        String requestBody2="""
                {
                "name":"jade",
                "gender":"female",
                "salary":78000.0
                }
""";
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody2))
                .andExpect(status().isOk());

        Employee expect=new Employee("joe","male",7000.0);
        mockMvc.perform(get("/employees?gender=male")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(expect.getName()))
                .andExpect(jsonPath("$[0].id").value(expect.getId()))
                .andExpect(jsonPath("$[0].gender").value(expect.getGender()))
                .andExpect(jsonPath("$[0].salary").value(expect.getSalary()));
    }

    @Test
    void should_get_all_employee_when_getAllEmployees() throws Exception {
        String requestBody="""
                {
                "name":"joe",
                "gender":"male",
                "salary":7000.0
                }
""";
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(status().isOk());
        String requestBody2="""
                {
                "name":"jade",
                "gender":"female",
                "salary":78000.0
                }
""";
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody2))
                .andExpect(status().isOk());
        mockMvc.perform(get("/employees/All")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("joe"))
                .andExpect(jsonPath("$[1].name").value("jade"));
    }
    @Test
    void should_update_employee_age_and_salary() throws Exception {
        String requestBody = """
                {
                "name":"joe",
                "gender":"male",
                "salary":7000.0
                }
        """;
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(status().isOk());

        String updateRequestBody = """
                {
                "gender":"female",
                "salary": 7500.0
                }
        """;
        mockMvc.perform(put("/employees/0")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(updateRequestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gender").value("female"))
                .andExpect(jsonPath("$.salary").value(7500.0));
    }
    @Test
    void should_delete_employee_by_id() throws Exception {
        String requestBody = """
                {
                "name":"joe",
                "gender":"male",
                "salary":7000.0
                }
        """;
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(status().isOk());
        mockMvc.perform(delete("/employees/0")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }
}