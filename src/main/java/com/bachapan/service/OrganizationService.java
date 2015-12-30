package com.bachapan.service;

import com.bachapan.domain.Organization;
import com.bachapan.web.rest.dto.OrganizationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Organization.
 */
public interface OrganizationService {

    /**
     * Save a organization.
     * @return the persisted entity
     */
    public OrganizationDTO save(OrganizationDTO organizationDTO);

    /**
     *  get all the organizations.
     *  @return the list of entities
     */
    public Page<Organization> findAll(Pageable pageable);

    /**
     *  get the "id" organization.
     *  @return the entity
     */
    public OrganizationDTO findOne(Long id);

    /**
     *  delete the "id" organization.
     */
    public void delete(Long id);

    /**
     * search for the organization corresponding
     * to the query.
     */
    public List<OrganizationDTO> search(String query);
}
