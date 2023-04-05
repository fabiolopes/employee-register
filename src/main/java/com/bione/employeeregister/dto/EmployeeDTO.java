package com.bione.employeeregister.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
public class EmployeeDTO {
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "phone is required")
    private String phone;
    @NotBlank(message = "street is required")
    private String street;
    private String number;
    @NotBlank(message = "district is required")
    private String district;
    private String complement;
    @NotBlank(message = "zipcode is required")
    private String zipcode;
    @NotBlank(message = "city is required")
    private String city;
    @NotBlank(message = "country is required")
    private String country;
    @NotNull(message = "salary is required")
    private BigDecimal salary;
    @NotEmpty
    private String functionDescription;

    public AddressDTO getAddress(){
        return AddressDTO.builder()
                .street(this.street)
                .number(this.number)
                .complement(this.complement)
                .district(this.district)
                .zipcode(this.zipcode)
                .city(this.city)
                .country(this.country)
                .build();
    }

    public DesignationDTO getDesignation(){
        return DesignationDTO.builder()
                .salary(this.salary)
                .functionDescription(this.functionDescription)
                .build();
    }
}
