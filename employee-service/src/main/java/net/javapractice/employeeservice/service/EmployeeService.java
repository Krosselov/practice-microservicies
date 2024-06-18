package net.javapractice.employeeservice.service;

import net.javapractice.employeeservice.dto.EmployeeDto;
import net.javapractice.employeeservice.entity.Employee;

public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto);
}
