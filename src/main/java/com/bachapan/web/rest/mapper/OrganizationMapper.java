package com.bachapan.web.rest.mapper;

import com.bachapan.domain.*;
import com.bachapan.web.rest.dto.OrganizationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Organization and its DTO OrganizationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrganizationMapper {

    OrganizationDTO organizationToOrganizationDTO(Organization organization);

    @Mapping(target = "parentOrgs", ignore = true)
    Organization organizationDTOToOrganization(OrganizationDTO organizationDTO);
}
