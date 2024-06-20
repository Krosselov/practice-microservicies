package net.javapractice.employeeservice.service.impl;

import lombok.AllArgsConstructor;
import net.javapractice.employeeservice.dto.EmployeeDto;
import net.javapractice.employeeservice.entity.Employee;
import net.javapractice.employeeservice.exception.EmailAlreadyExistsException;
import net.javapractice.employeeservice.exception.ResourceNotFoundException;
import net.javapractice.employeeservice.mapper.AutoEmployeeMapper;
import net.javapractice.employeeservice.repository.EmployeeRepository;
import net.javapractice.employeeservice.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeDto saveEmployee(EmployeeDto employeeDto){

        // convert employee dto to employee jpa entity

        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(employeeDto.getEmail());

        if(optionalEmployee.isPresent()){
            throw new EmailAlreadyExistsException("Email Already Exists for Employee");
        }

        Employee employee = AutoEmployeeMapper.MAPPER.mapToEmployee(employeeDto);

        Employee savedEmployee = employeeRepository.save(employee);

        EmployeeDto savedEmployeeDto = AutoEmployeeMapper.MAPPER.mapToEmployeeDto(savedEmployee);

        return savedEmployeeDto;
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", employeeId)
        );
        return AutoEmployeeMapper.MAPPER.mapToEmployeeDto(employee);
    }
}
