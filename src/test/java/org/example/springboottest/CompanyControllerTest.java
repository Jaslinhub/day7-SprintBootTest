package org.example.springboottest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CompanyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void createCompany() throws Exception {
        String requestBody="""
                {
                "name":"alibaba"
}
""";
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

    }

    @Test
    void getAllCompanies() throws Exception {
        String requestBody="""
                {
                "name":"alibaba"
}
""";
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(status().isOk());
        String requestBody2="""
                {
                "name":"bytedance"
}
""";
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody2))
                .andExpect(status().isOk());
        mockMvc.perform(get("/companies")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("alibaba"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("bytedance"));
    }
    @Test
    void getCompanyById() throws Exception {
        String requestBody = """
                                {
                                "name":"alibaba"
                }
                """;
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                    .andExpect(status().isOk());
       mockMvc.perform(get("/companies/{id}", 0)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("alibaba"))
                .andExpect(jsonPath("$.id").value(1));
    }
    @Test
    void updateCompanyById() throws Exception {
        String requestBody = """
                                {
                                "name":"alibaba"
                }
                """;
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                    .andExpect(status().isOk());
        String updateBody = """
                                {
                                "name":"oocl"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.put("/companies/0")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(updateBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("oocl"))
                .andExpect(jsonPath("$.id").value(1));

}
}
