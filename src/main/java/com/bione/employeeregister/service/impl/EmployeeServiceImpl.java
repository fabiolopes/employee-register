package com.bione.employeeregister.service.impl;

import com.bione.employeeregister.dto.EmployeeDTO;
import com.bione.employeeregister.dto.EmployeeDetailResponseDTO;
import com.bione.employeeregister.dto.EmployeeResponseDTO;
import com.bione.employeeregister.entity.Employee;
import com.bione.employeeregister.exceptions.ObjectNotFoundException;
import com.bione.employeeregister.repository.EmployeeRepository;
import com.bione.employeeregister.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public EmployeeResponseDTO insert(EmployeeDTO employeeDTO) {
        Employee employeeToCreate = modelMapper.map(employeeDTO, Employee.class);
        Employee employeeCreated = employeeRepository.save(employeeToCreate);
        return EmployeeResponseDTO.builder()
                .id(employeeCreated.getId())
                .name(employeeCreated.getName())
                .build();
    }

    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeDetailResponseDTO findById(Long id) {
        Employee employeeNotFound = employeeRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Employee not found"));
        return EmployeeDetailResponseDTO.builder()
                .id(employeeNotFound.getId())
                .name(employeeNotFound.getName())
                .phone(employeeNotFound.getPhone())
                .street(employeeNotFound.getAddress().getStreet())
                .number(employeeNotFound.getAddress().getNumber())
                .district(employeeNotFound.getAddress().getDistrict())
                .complement(employeeNotFound.getAddress().getComplement())
                .city(employeeNotFound.getAddress().getCity())
                .country(employeeNotFound.getAddress().getCountry())
                .zipcode(employeeNotFound.getAddress().getZipcode())
                .functionDescription(employeeNotFound.getDesignation().getFunctionDescription())
                .salary(employeeNotFound.getDesignation().getSalary())
                .build();
    }

    @Override
    public void update(Long id, EmployeeDTO employeeDTO) {
        Employee employeeToCreate = modelMapper.map(employeeDTO, Employee.class);
        Employee employeeFound = employeeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Cannot update. Employee not found"));
        if(employeeToCreate.getDesignation().getFunctionDescription()
                .equals(employeeFound.getDesignation().getFunctionDescription())){
            employeeToCreate.getDesignation().setId(employeeFound.getDesignation().getId());
        }
        employeeToCreate.setId(id);
        employeeRepository.save(employeeToCreate);
    }

}
