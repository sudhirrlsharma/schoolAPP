package com.bachapan.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bachapan.domain.Contect;
import com.bachapan.service.ContectService;
import com.bachapan.web.rest.util.HeaderUtil;
import com.bachapan.web.rest.util.PaginationUtil;
import com.bachapan.web.rest.dto.ContectDTO;
import com.bachapan.web.rest.mapper.ContectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Contect.
 */
@RestController
@RequestMapping("/api")
public class ContectResource {

    private final Logger log = LoggerFactory.getLogger(ContectResource.class);
        
    @Inject
    private ContectService contectService;
    
    @Inject
    private ContectMapper contectMapper;
    
    /**
     * POST  /contects -> Create a new contect.
     */
    @RequestMapping(value = "/contects",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContectDTO> createContect(@RequestBody ContectDTO contectDTO) throws URISyntaxException {
        log.debug("REST request to save Contect : {}", contectDTO);
        if (contectDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("contect", "idexists", "A new contect cannot already have an ID")).body(null);
        }
        ContectDTO result = contectService.save(contectDTO);
        return ResponseEntity.created(new URI("/api/contects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("contect", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contects -> Updates an existing contect.
     */
    @RequestMapping(value = "/contects",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContectDTO> updateContect(@RequestBody ContectDTO contectDTO) throws URISyntaxException {
        log.debug("REST request to update Contect : {}", contectDTO);
        if (contectDTO.getId() == null) {
            return createContect(contectDTO);
        }
        ContectDTO result = contectService.save(contectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("contect", contectDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contects -> get all the contects.
     */
    @RequestMapping(value = "/contects",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ContectDTO>> getAllContects(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Contects");
        Page<Contect> page = contectService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contects");
        return new ResponseEntity<>(page.getContent().stream()
            .map(contectMapper::contectToContectDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /contects/:id -> get the "id" contect.
     */
    @RequestMapping(value = "/contects/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContectDTO> getContect(@PathVariable Long id) {
        log.debug("REST request to get Contect : {}", id);
        ContectDTO contectDTO = contectService.findOne(id);
        return Optional.ofNullable(contectDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /contects/:id -> delete the "id" contect.
     */
    @RequestMapping(value = "/contects/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteContect(@PathVariable Long id) {
        log.debug("REST request to delete Contect : {}", id);
        contectService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("contect", id.toString())).build();
    }

    /**
     * SEARCH  /_search/contects/:query -> search for the contect corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/contects/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ContectDTO> searchContects(@PathVariable String query) {
        log.debug("Request to search Contects for query {}", query);
        return contectService.search(query);
    }
}
