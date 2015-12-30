package com.bachapan.web.rest.mapper;

import com.bachapan.domain.*;
import com.bachapan.web.rest.dto.UserAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserAccount and its DTO UserAccountDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserAccountMapper {

    @Mapping(source = "organization.id", target = "organizationId")
    @Mapping(source = "organization.name", target = "organizationName")
    UserAccountDTO userAccountToUserAccountDTO(UserAccount userAccount);

    @Mapping(source = "organizationId", target = "organization")
    UserAccount userAccountDTOToUserAccount(UserAccountDTO userAccountDTO);

    default Organization organizationFromId(Long id) {
        if (id == null) {
            return null;
        }
        Organization organization = new Organization();
        organization.setId(id);
        return organization;
    }
}
