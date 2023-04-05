package com.bione.employeeregister.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetailResponseDTO {
    private Long id;
    private String name;
    private String phone;
    private String street;
    private String number;
    private String district;
    private String complement;
    private String zipcode;
    private String city;
    private String country;
    private BigDecimal salary;
    private String functionDescription;
}
