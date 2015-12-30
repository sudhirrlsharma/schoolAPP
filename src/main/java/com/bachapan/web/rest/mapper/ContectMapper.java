package com.bachapan.web.rest.mapper;

import com.bachapan.domain.*;
import com.bachapan.web.rest.dto.ContectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Contect and its DTO ContectDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContectMapper {

    ContectDTO contectToContectDTO(Contect contect);

    Contect contectDTOToContect(ContectDTO contectDTO);
}
