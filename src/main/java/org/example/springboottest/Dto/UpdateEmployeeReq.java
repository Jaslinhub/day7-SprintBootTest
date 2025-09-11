package org.example.springboottest.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployeeReq {
    private Long id;
    private String name;
    private Integer age;
    private String gender;
    private Double salary;
}
