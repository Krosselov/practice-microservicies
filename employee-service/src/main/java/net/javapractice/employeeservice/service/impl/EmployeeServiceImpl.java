package net.javapractice.employeeservice.service.impl;

import lombok.AllArgsConstructor;
import net.javapractice.employeeservice.dto.EmployeeDto;
import net.javapractice.employeeservice.entity.Employee;
import net.javapractice.employeeservice.repository.EmployeeRepository;
import net.javapractice.employeeservice.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeDto saveEmployee(EmployeeDto employeeDto){

        // convert employee dto to employee jpa entity
        Employee employee = new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail()
        );

        Employee savedEmployee = employeeRepository.save(employee);

        EmployeeDto savedEmployeeDto = new EmployeeDto(
                savedEmployee.getId(),
                savedEmployee.getFirstName(),
                savedEmployee.getLastName(),
                savedEmployee.getEmail()
        );

        return savedEmployeeDto;
    }
}
