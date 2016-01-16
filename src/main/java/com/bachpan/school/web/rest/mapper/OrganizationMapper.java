package com.bachpan.school.web.rest.mapper;

import com.bachpan.school.domain.*;
import com.bachpan.school.web.rest.dto.OrganizationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Organization and its DTO OrganizationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrganizationMapper {

    @Mapping(source = "parentOrg.id", target = "parentOrgId")
    @Mapping(source = "parentOrg.name", target = "parentOrgName")
    OrganizationDTO organizationToOrganizationDTO(Organization organization);

    @Mapping(target = "childOrgs", ignore = true)
    @Mapping(source = "parentOrgId", target = "parentOrg")
    Organization organizationDTOToOrganization(OrganizationDTO organizationDTO);

    default Organization organizationFromId(Long id) {
        if (id == null) {
            return null;
        }
        Organization organization = new Organization();
        organization.setId(id);
        return organization;
    }
}
