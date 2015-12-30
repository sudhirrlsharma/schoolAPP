package com.bachapan.service.impl;

import com.bachapan.service.OrganizationService;
import com.bachapan.domain.Organization;
import com.bachapan.repository.OrganizationRepository;
import com.bachapan.repository.search.OrganizationSearchRepository;
import com.bachapan.web.rest.dto.OrganizationDTO;
import com.bachapan.web.rest.mapper.OrganizationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Organization.
 */
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService{

    private final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);
    
    @Inject
    private OrganizationRepository organizationRepository;
    
    @Inject
    private OrganizationMapper organizationMapper;
    
    @Inject
    private OrganizationSearchRepository organizationSearchRepository;
    
    /**
     * Save a organization.
     * @return the persisted entity
     */
    public OrganizationDTO save(OrganizationDTO organizationDTO) {
        log.debug("Request to save Organization : {}", organizationDTO);
        Organization organization = organizationMapper.organizationDTOToOrganization(organizationDTO);
        organization = organizationRepository.save(organization);
        OrganizationDTO result = organizationMapper.organizationToOrganizationDTO(organization);
        organizationSearchRepository.save(organization);
        return result;
    }

    /**
     *  get all the organizations.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Organization> findAll(Pageable pageable) {
        log.debug("Request to get all Organizations");
        Page<Organization> result = organizationRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one organization by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public OrganizationDTO findOne(Long id) {
        log.debug("Request to get Organization : {}", id);
        Organization organization = organizationRepository.findOne(id);
        OrganizationDTO organizationDTO = organizationMapper.organizationToOrganizationDTO(organization);
        return organizationDTO;
    }

    /**
     *  delete the  organization by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Organization : {}", id);
        organizationRepository.delete(id);
        organizationSearchRepository.delete(id);
    }

    /**
     * search for the organization corresponding
     * to the query.
     */
    @Transactional(readOnly = true) 
    public List<OrganizationDTO> search(String query) {
        
        log.debug("REST request to search Organizations for query {}", query);
        return StreamSupport
            .stream(organizationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(organizationMapper::organizationToOrganizationDTO)
            .collect(Collectors.toList());
    }
}
