package net.javapractice.employeeservice.service.impl;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import net.javapractice.employeeservice.dto.APIResponseDto;
import net.javapractice.employeeservice.dto.DepartmentDto;
import net.javapractice.employeeservice.dto.EmployeeDto;
import net.javapractice.employeeservice.dto.OrganizationDto;
import net.javapractice.employeeservice.entity.Employee;
import net.javapractice.employeeservice.exception.EmailAlreadyExistsException;
import net.javapractice.employeeservice.exception.ResourceNotFoundException;
import net.javapractice.employeeservice.mapper.AutoEmployeeMapper;
import net.javapractice.employeeservice.repository.EmployeeRepository;
import net.javapractice.employeeservice.service.APIClient;
import net.javapractice.employeeservice.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private static Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private EmployeeRepository employeeRepository;

    // private RestTemplate restTemplate;
    private WebClient webClient;
    private APIClient apiClient;

    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(employeeDto.getEmail());
        if (optionalEmployee.isPresent()) {
            throw new EmailAlreadyExistsException("Email Already Exists for Employee");
        }
        Employee employee = AutoEmployeeMapper.MAPPER.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return AutoEmployeeMapper.MAPPER.mapToEmployeeDto(savedEmployee);
    }

    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Override
    public APIResponseDto getEmployeeById(Long employeeId) {

        LOGGER.info("inside getEmployeeById() method");
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", employeeId)
        );

        DepartmentDto departmentDto = webClient.get()
                .uri("http://localhost:8080/api/departments/" + employee.getDepartmentCode())
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();
        LOGGER.info("departmentDto after mapping: {}", departmentDto);

        OrganizationDto organizationDto = webClient.get()
                .uri("http://localhost:8083/api/organizations/" + employee.getOrganizationCode())
                .retrieve()
                .bodyToMono(OrganizationDto.class)
                .block();
        LOGGER.info("organizationDto after mapping: {}", organizationDto);

        EmployeeDto employeeDto = AutoEmployeeMapper.MAPPER.mapToEmployeeDto(employee);

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployee(employeeDto);
        apiResponseDto.setDepartment(departmentDto);
        apiResponseDto.setOrganization(organizationDto);
        return apiResponseDto;
    }

    public APIResponseDto getDefaultDepartment(Long employeeId, Exception exception) {
        LOGGER.info("inside getDefaultDepartment() method");

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", employeeId)
        );

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentName("R&D Department");
        departmentDto.setDepartmentCode("RD001");
        departmentDto.setDepartmentDescription("Research and Development Department");


        EmployeeDto employeeDto = AutoEmployeeMapper.MAPPER.mapToEmployeeDto(employee);
        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployee(employeeDto);
        apiResponseDto.setDepartment(departmentDto);
        return apiResponseDto;
    }
}
