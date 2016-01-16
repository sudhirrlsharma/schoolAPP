package com.bachpan.school.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bachpan.school.domain.Organization;
import com.bachpan.school.repository.OrganizationRepository;
import com.bachpan.school.web.rest.util.HeaderUtil;
import com.bachpan.school.web.rest.util.PaginationUtil;
import com.bachpan.school.web.rest.dto.OrganizationDTO;
import com.bachpan.school.web.rest.mapper.OrganizationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Organization.
 */
@RestController
@RequestMapping("/api")
public class OrganizationResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationResource.class);

    @Inject
    private OrganizationRepository organizationRepository;

    @Inject
    private OrganizationMapper organizationMapper;

    /**
     * POST  /organizations -> Create a new organization.
     */
    @RequestMapping(value = "/organizations",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrganizationDTO> create(@RequestBody OrganizationDTO organizationDTO) throws URISyntaxException {
        log.debug("REST request to save Organization : {}", organizationDTO);
        if (organizationDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new organization cannot already have an ID").body(null);
        }
        Organization organization = organizationMapper.organizationDTOToOrganization(organizationDTO);
        Organization result = organizationRepository.save(organization);
        return ResponseEntity.created(new URI("/api/organizations/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("organization", result.getId().toString()))
                .body(organizationMapper.organizationToOrganizationDTO(result));
    }

    /**
     * PUT  /organizations -> Updates an existing organization.
     */
    @RequestMapping(value = "/organizations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrganizationDTO> update(@RequestBody OrganizationDTO organizationDTO) throws URISyntaxException {
        log.debug("REST request to update Organization : {}", organizationDTO);
        if (organizationDTO.getId() == null) {
            return create(organizationDTO);
        }
        Organization organization = organizationMapper.organizationDTOToOrganization(organizationDTO);
        Organization result = organizationRepository.save(organization);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("organization", organizationDTO.getId().toString()))
                .body(organizationMapper.organizationToOrganizationDTO(result));
    }

    /**
     * GET  /organizations -> get all the organizations.
     */
    @RequestMapping(value = "/organizations",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<OrganizationDTO>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Organization> page = organizationRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/organizations", offset, limit);
        return new ResponseEntity<>(page.getContent().stream()
            .map(organizationMapper::organizationToOrganizationDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /organizations/:id -> get the "id" organization.
     */
    @RequestMapping(value = "/organizations/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrganizationDTO> get(@PathVariable Long id) {
        log.debug("REST request to get Organization : {}", id);
        return Optional.ofNullable(organizationRepository.findOne(id))
            .map(organizationMapper::organizationToOrganizationDTO)
            .map(organizationDTO -> new ResponseEntity<>(
                organizationDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /organizations/:id -> delete the "id" organization.
     */
    @RequestMapping(value = "/organizations/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Organization : {}", id);
        organizationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("organization", id.toString())).build();
    }
}
