package org.example.springboottest;

import org.example.springboottest.Controller.CompanyController;
import org.example.springboottest.Entity.Company;
import org.example.springboottest.Repository.CompanyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CompanyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CompanyController companyController;
    @Autowired
    private CompanyRepository companyRepository;
    private Company company = new Company("cosco");
    private Company company2 = new Company("oocl");
    private Company company3 = new Company("sony");
    private Company company4 = new Company("sam");
    @BeforeEach
    public void setUp() {
        companyRepository.clear();
    }

    private int createCompanyAndGetId(Company company) throws Exception {
        String requestBody = new ObjectMapper().writeValueAsString(company);
        var result = mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();
        return new ObjectMapper().readTree(result.getResponse().getContentAsString()).get("id").asInt();
    }

    @Test
    public void should_return_company_when_post_given_a_valid_company() throws Exception {
        Company company = new Company("cosco");
        String requestBody = new ObjectMapper().writeValueAsString(company);
        var result = mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("cosco"))
                .andReturn();
        int id = new ObjectMapper().readTree(result.getResponse().getContentAsString()).get("id").asInt();
        mockMvc.perform(get("/companies/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("cosco"));
    }

    @Test
    public void should_return_companies_when_get_all_given_null() throws Exception{
        int id1 = createCompanyAndGetId(company);
        int id2 = createCompanyAndGetId(company2);
        mockMvc.perform(get("/companies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id1))
                .andExpect(jsonPath("$[0].name").value(company.getName()))
                .andExpect(jsonPath("$[1].id").value(id2))
                .andExpect(jsonPath("$[1].name").value(company2.getName()));
    }

    @Test
    public void should_return_company_when_get_given_id() throws Exception {
        int id = createCompanyAndGetId(company);
        mockMvc.perform(get("/companies/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(company.getName()));
    }

    @Test
    public void should_return_companies_when_get_by_page_given_page_size() throws Exception {
        int id1 = createCompanyAndGetId(company);
        int id2 = createCompanyAndGetId(company2);
        int id3 = createCompanyAndGetId(company3);
        int id4 = createCompanyAndGetId(company4);
        mockMvc.perform(get("/companies/page?page=1&pageSize=5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id1))
                .andExpect(jsonPath("$[0].name").value(company.getName()))
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    public void should_return_matching_code_when_update_by_id_given_name() throws Exception {
        int id = createCompanyAndGetId(company);
        String requestBody = """
                {
                    "id": %d,
                    "name": "oocl"
                }
                """.formatted(id);
        mockMvc.perform(put("/companies/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("oocl"));
    }

    @Test
    public void should_response_no_content_when_delete_given_company_id() throws Exception {
        int id = createCompanyAndGetId(company);
        mockMvc.perform(delete("/companies/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_throw_exception_when_get_given_page_out_of_all() throws Exception {
        companyController.createCompany(company);
        companyController.createCompany(company2);
        companyController.createCompany(company3);
        companyController.createCompany(company4);
        mockMvc.perform(get("/companies/page?page=3&pageSize=5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
