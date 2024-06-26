package net.javapractice.organizationservice.service;

import net.javapractice.organizationservice.dto.OrganizationDto;
import net.javapractice.organizationservice.entity.Organization;

public interface OrganizationService {
    OrganizationDto saveOrganization (OrganizationDto organizationDto);
    OrganizationDto getOrganizationByCode(String organizationCode);
}
