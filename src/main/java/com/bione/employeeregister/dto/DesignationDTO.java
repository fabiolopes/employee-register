package com.bione.employeeregister.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DesignationDTO {

    private Long id;
    @NotNull
    private String functionDescription;
    @NotNull(message = "salary is required")
    private BigDecimal salary;

}
