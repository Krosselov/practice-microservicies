package net.javapractice.departmentservice.service.impl;

import lombok.AllArgsConstructor;
import net.javapractice.departmentservice.dto.DepartmentDto;
import net.javapractice.departmentservice.entity.Department;
import net.javapractice.departmentservice.exception.DepartmentCodeAlreadyExistsException;
import net.javapractice.departmentservice.exception.ResourceNotFoundException;
import net.javapractice.departmentservice.mapper.AutoDepartmentMapper;
import net.javapractice.departmentservice.repository.DepartmentRepository;
import net.javapractice.departmentservice.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;

    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {

        Optional<Department> optionalDepartment = departmentRepository.findByDepartmentCode(departmentDto.getDepartmentCode());

        if(optionalDepartment.isPresent()){
            throw new DepartmentCodeAlreadyExistsException("Department Code Already Exists for Employee");
        }

        // convert department dto to department jpa entity
        Department department = AutoDepartmentMapper.MAPPER.mapToDepartment(departmentDto);

        Department savedDepartment = departmentRepository.save(department);

        DepartmentDto savedDepartmentDto = AutoDepartmentMapper.MAPPER.mapToDepartmentDto(savedDepartment);

        return savedDepartmentDto;
    }

    @Override
    public DepartmentDto getDepartmentByCode(String departmentCode) {
        Department department = departmentRepository.findByDepartmentCode(departmentCode).orElseThrow(
                ()-> new ResourceNotFoundException("Department", "departmentCode", departmentCode)
        );
        return AutoDepartmentMapper.MAPPER.mapToDepartmentDto(department);
    }
}
