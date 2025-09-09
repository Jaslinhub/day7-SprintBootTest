package org.example.springboottest.Controller;

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
                "id":1,
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
    void getEmployeeById() {
    }

    @Test
    void getAllEmployeesByGender() {
    }
}