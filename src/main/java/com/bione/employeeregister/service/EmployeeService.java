package com.bione.employeeregister.service;

import com.bione.employeeregister.dto.EmployeeDTO;
import com.bione.employeeregister.dto.EmployeeDetailResponseDTO;
import com.bione.employeeregister.dto.EmployeeResponseDTO;

public interface EmployeeService {

    EmployeeResponseDTO insert(EmployeeDTO employeeDTO);

    void delete(Long id);

    EmployeeDetailResponseDTO findById(Long id);

    void update(Long id, EmployeeDTO employeeDTO);
}
