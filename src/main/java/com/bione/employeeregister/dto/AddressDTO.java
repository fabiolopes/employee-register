package com.bione.employeeregister.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private Long id;
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
}
