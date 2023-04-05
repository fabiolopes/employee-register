package com.bione.employeeregister.integration;

import com.bione.employeeregister.controller.exceptionhandler.StandardError;
import com.bione.employeeregister.controller.exceptionhandler.ValidationError;
import com.bione.employeeregister.dto.EmployeeDTO;
import com.bione.employeeregister.dto.EmployeeDetailResponseDTO;
import com.bione.employeeregister.dto.EmployeeResponseDTO;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EmployeeControllerIT {

    private static final String HOST_PREFIX = "http://localhost:";
    private static final String ENDPOINT_EMPLOYEE = "/employee";


    @Autowired
    @Qualifier(value = "testRestTemplate")
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ModelMapper modelMapper;

    @TestConfiguration
    @Lazy
    static class Config {

        @Bean(name = "testRestTemplate")
        public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri(HOST_PREFIX + port);
            return new TestRestTemplate(restTemplateBuilder);
        }
    }

    @Test
    @DisplayName("Post returns new employee when successful")
    void insertUserWhenSuccessful() {
        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .name("Brasileiro da Silva")
                .phone("11995944444")
                .street("Rua Projetada")
                .number("85")
                .district("Centro")
                .complement("")
                .city("São Paulo")
                .zipcode("06243-020")
                .country("Brasil")
                .functionDescription("Analista Financeiro")
                .salary(new BigDecimal(10000.00)).build();
        ResponseEntity<EmployeeResponseDTO> responseEntity = testRestTemplate.postForEntity(
                ENDPOINT_EMPLOYEE, employeeDTO, EmployeeResponseDTO.class);

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(responseEntity.getBody()).isNotNull();

    }

    @Test
    @DisplayName("Insert return bad request for validation error by name empty")
    void insertWithoutNameReturnBadRequest() {
        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .phone("11995944444")
                .street("Rua Projetada")
                .number("85")
                .district("Centro")
                .complement("")
                .city("São Paulo")
                .country("Brasil")
                .functionDescription("Analista Financeiro")
                .salary(new BigDecimal(10000.00)).build();
        ResponseEntity<ValidationError> responseEntity = testRestTemplate.postForEntity(
                ENDPOINT_EMPLOYEE, employeeDTO, ValidationError.class);

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(responseEntity.getBody()).isNotNull();
        Assertions.assertThat(responseEntity.getBody().getErrors()
                .stream().findFirst().get().getMessage().contains("name"));

    }

    @Test
    @DisplayName("Insert return bad request for validation error by zipcode empty")
    void insertWithoutZipcodeReturnBadRequest() {
        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .name("Brasileiro da Silva")
                .phone("11995944444")
                .street("Rua Projetada")
                .number("85")
                .district("Centro")
                .complement("")
                .city("São Paulo")
                .country("Brasil")
                .functionDescription("Analista Financeiro")
                 .salary(new BigDecimal(10000.00)).build();
        ResponseEntity<ValidationError> responseEntity = testRestTemplate.postForEntity(
                ENDPOINT_EMPLOYEE, employeeDTO, ValidationError.class);

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(responseEntity.getBody()).isNotNull();
        Assertions.assertThat(responseEntity.getBody().getErrors()
                .stream().findFirst().get().getMessage().contains("zipcode"));

    }

    @Test
    @DisplayName("Get employee details with success")
    void getEmployeeDetailsWithSuccess() {
        String name = "Brasileiro da Silva";
        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .name(name)
                .phone("11995944444")
                .street("Rua Projetada")
                .number("85")
                .district("Centro")
                .complement("")
                .city("São Paulo")
                .zipcode("06243-020")
                .country("Brasil")
                .functionDescription("Analista Financeiro")
                .salary(new BigDecimal(10000.00)).build();
        ResponseEntity<EmployeeResponseDTO> responseEntity = testRestTemplate.postForEntity(
                ENDPOINT_EMPLOYEE, employeeDTO, EmployeeResponseDTO.class);

        ResponseEntity<EmployeeDetailResponseDTO> responseEntityFindById = testRestTemplate.exchange(
                ENDPOINT_EMPLOYEE+ "/{id}", HttpMethod.GET, new HttpEntity<>(new HttpHeaders()),  EmployeeDetailResponseDTO.class,
                responseEntity.getBody().getId());

        Assert.assertTrue(responseEntityFindById.getBody().getName().equals(name));
    }

    @Test
    @DisplayName("Get employee details not found with id")
    void getEmployeeDetailsFailNoId() {
        ResponseEntity<StandardError> responseEntityFindById = testRestTemplate.exchange(
                ENDPOINT_EMPLOYEE+ "/{id}", HttpMethod.GET, new HttpEntity<>(new HttpHeaders()),  StandardError.class,
                1l);
        Assert.assertTrue(responseEntityFindById.getStatusCode() == HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Delete employee with success")
    void deleteEmployeeWithSuccess() {
        String name = "Brasileiro da Silva";
        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .name(name)
                .phone("11995944444")
                .street("Rua Projetada")
                .number("85")
                .district("Centro")
                .complement("")
                .city("São Paulo")
                .zipcode("06243-020")
                .country("Brasil")
                .functionDescription("Analista Financeiro")
                .salary(new BigDecimal(10000.00)).build();
        ResponseEntity<EmployeeResponseDTO> responseEntity = testRestTemplate.postForEntity(
                ENDPOINT_EMPLOYEE, employeeDTO, EmployeeResponseDTO.class);

        ResponseEntity<EmployeeDetailResponseDTO> responseEntityFindById = testRestTemplate.exchange(
                ENDPOINT_EMPLOYEE+ "/{id}", HttpMethod.GET, new HttpEntity<>(new HttpHeaders()),  EmployeeDetailResponseDTO.class,
                responseEntity.getBody().getId());

        Assert.assertTrue(responseEntityFindById.getBody().getName().equals(name));

        ResponseEntity<Void> responseEntityDelete = testRestTemplate.exchange(
                ENDPOINT_EMPLOYEE+ "/{id}", HttpMethod.DELETE, new HttpEntity<>(new HttpHeaders()),  Void.class,
                responseEntity.getBody().getId());
        Assertions.assertThat(responseEntityDelete).isNotNull();
        Assertions.assertThat(responseEntityDelete.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Update employee with success")
    void updateEmployeeWithSuccess() {
        String name = "Brasileiro da Silva";
        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .name(name)
                .phone("11995944444")
                .street("Rua Projetada")
                .number("85")
                .district("Centro")
                .complement("")
                .city("São Paulo")
                .zipcode("06243-020")
                .country("Brasil")
                .functionDescription("Analista Financeiro")
                .salary(new BigDecimal(10000.00)).build();
        ResponseEntity<EmployeeResponseDTO> responseEntity = testRestTemplate.postForEntity(
                ENDPOINT_EMPLOYEE, employeeDTO, EmployeeResponseDTO.class);

        employeeDTO.setNumber("96");

        Map<String, String> params = Map.of("id", responseEntity.getBody().getId().toString());

        testRestTemplate.put(
                ENDPOINT_EMPLOYEE+ "/{id}", employeeDTO, params);

        ResponseEntity<EmployeeDetailResponseDTO> responseEntityFindById = testRestTemplate.exchange(
                ENDPOINT_EMPLOYEE+ "/{id}", HttpMethod.GET, new HttpEntity<>(new HttpHeaders()),  EmployeeDetailResponseDTO.class,
                responseEntity.getBody().getId());

        Assertions.assertThat(responseEntityFindById.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntityFindById.getBody().getNumber()).isEqualTo(employeeDTO.getNumber());
    }
}
