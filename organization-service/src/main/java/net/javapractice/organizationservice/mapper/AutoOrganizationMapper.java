package net.javapractice.organizationservice.mapper;

import net.javapractice.organizationservice.dto.OrganizationDto;
import net.javapractice.organizationservice.entity.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoOrganizationMapper {
    AutoOrganizationMapper MAPPER = Mappers.getMapper(AutoOrganizationMapper.class);

    OrganizationDto mapToOrganizationDto(Organization employee);
    Organization mapToOrganization(OrganizationDto employeeDto);
}
