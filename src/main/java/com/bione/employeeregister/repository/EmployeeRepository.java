package com.bione.employeeregister.repository;

import com.bione.employeeregister.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
