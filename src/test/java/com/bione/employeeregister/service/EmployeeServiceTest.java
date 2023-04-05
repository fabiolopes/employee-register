package com.bione.employeeregister.service;

import com.bione.employeeregister.dto.EmployeeDTO;
import com.bione.employeeregister.dto.EmployeeDetailResponseDTO;
import com.bione.employeeregister.dto.EmployeeResponseDTO;
import com.bione.employeeregister.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    @DisplayName("Create a employee with success")
    public void createEmployeeSuccessful(){
        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .name("Brasileiro da Silva")
                .phone("11995944444")
                .street("Rua Projetada")
                .number("85")
                .district("Centro")
                .zipcode("06243-020")
                .complement("")
                .city("São Paulo")
                .country("Brasil")
                .functionDescription("Analista Financeiro")
                .salary(new BigDecimal(10000.00)).build();
        EmployeeResponseDTO employeeResponseDTO = employeeService.insert(employeeDTO);
        Assertions.assertNotNull(employeeResponseDTO);
        Assertions.assertNotNull(employeeResponseDTO.getId());
        Assertions.assertNotNull(employeeResponseDTO.getName());
    }

    @Test
    @DisplayName("Delete a employee with success")
    public void deleteEmployeeSuccessful(){
        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .name("Brasileiro da Silva")
                .phone("11995944444")
                .street("Rua Projetada")
                .number("85")
                .district("Centro")
                .zipcode("06243-020")
                .complement("")
                .city("São Paulo")
                .country("Brasil")
                .functionDescription("Analista Financeiro")
                .salary(new BigDecimal(10000.00)).build();
        EmployeeResponseDTO employeeResponseDTO = employeeService.insert(employeeDTO);

        employeeService.delete(employeeResponseDTO.getId());

        Assertions.assertThrows(ObjectNotFoundException.class,
                ()-> employeeService.findById(employeeResponseDTO.getId()));
    }

    @Test
    @DisplayName("Delete a employee with success")
    public void errorOnTryDeleteInexistentEmployee(){
        Assertions.assertThrows(EmptyResultDataAccessException.class,
                ()-> employeeService.delete(1l));
    }

    @Test
    @DisplayName("Update some employee data with success")
    public void updateEmployeeDataWithSuccess(){
        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .name("Brasileiro da Silva")
                .phone("11995944444")
                .street("Rua Projetada")
                .number("85")
                .district("Centro")
                .zipcode("06243-020")
                .complement("")
                .city("São Paulo")
                .country("Brasil")
                .functionDescription("Analista Financeiro")
                .salary(new BigDecimal(10000.00)).build();
        EmployeeResponseDTO employeeResponseDTO = employeeService.insert(employeeDTO);

        EmployeeDetailResponseDTO employeeDetailResponseDTO = employeeService.findById(employeeResponseDTO.getId());
        Assertions.assertEquals(employeeDTO.getNumber(), employeeDetailResponseDTO.getNumber());
        employeeDTO.setNumber("96");

        employeeService.update(employeeDetailResponseDTO.getId(), employeeDTO);

        EmployeeDetailResponseDTO employeeDetailAfterUpdate = employeeService.findById(employeeResponseDTO.getId());
        Assertions.assertEquals(employeeDTO.getNumber(), employeeDetailAfterUpdate.getNumber());
    }

}
