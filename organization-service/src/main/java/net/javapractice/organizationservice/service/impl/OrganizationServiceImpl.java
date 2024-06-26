package net.javapractice.organizationservice.service.impl;

import lombok.AllArgsConstructor;
import net.javapractice.organizationservice.dto.OrganizationDto;
import net.javapractice.organizationservice.entity.Organization;
import net.javapractice.organizationservice.mapper.AutoOrganizationMapper;
import net.javapractice.organizationservice.repository.OrganizationRepository;
import net.javapractice.organizationservice.service.OrganizationService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private OrganizationRepository organizationRepository;
    @Override
    public OrganizationDto saveOrganization(OrganizationDto organizationDto) {

        Organization organization = AutoOrganizationMapper.MAPPER.mapToOrganization(organizationDto);
        Organization savedOrganization = organizationRepository.save(organization);
        return AutoOrganizationMapper.MAPPER.mapToOrganizationDto(savedOrganization);
    }

    public OrganizationDto getOrganizationByCode(String organizationCode){
        Organization organization = organizationRepository.findByOrganizationCode(organizationCode);
        return AutoOrganizationMapper.MAPPER.mapToOrganizationDto(organization);
    }
}
