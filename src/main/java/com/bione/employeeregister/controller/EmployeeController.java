package com.bione.employeeregister.controller;

import com.bione.employeeregister.dto.EmployeeDTO;
import com.bione.employeeregister.dto.EmployeeDetailResponseDTO;
import com.bione.employeeregister.dto.EmployeeResponseDTO;
import com.bione.employeeregister.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/employee")
@Tag(name = "Employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    @Operation(summary = "Create a new Employee", tags = "Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeResponseDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid fields",
                    content = @Content) })
    public ResponseEntity<EmployeeResponseDTO> insert(@Valid @RequestBody EmployeeDTO employee) {
        return new ResponseEntity<EmployeeResponseDTO>(employeeService.insert(employee), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a existent Employee by id", tags = "Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Employee",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDetailResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content) })
    public ResponseEntity<EmployeeDetailResponseDTO> find(@Parameter(description = "id of employee to be searched") @PathVariable Long id) {
        return ResponseEntity.ok().body(employeeService.findById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a existent Employee by id", tags = "Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee deleted",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content) })
    public ResponseEntity<Void> delete(@Parameter(description = "id of employee to be searched") @PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    @Operation(summary = "Update a existent Employee by id", tags = "Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee updated",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content) })
    public ResponseEntity<Void> update(@Parameter(description = "id of employee to be searched") @PathVariable Long id, @RequestBody EmployeeDTO employeeDTO){
        employeeService.update(id, employeeDTO);
        return ResponseEntity.noContent().build();
    }
}
