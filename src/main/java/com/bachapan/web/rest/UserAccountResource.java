package com.bachapan.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bachapan.domain.UserAccount;
import com.bachapan.service.UserAccountService;
import com.bachapan.web.rest.util.HeaderUtil;
import com.bachapan.web.rest.util.PaginationUtil;
import com.bachapan.web.rest.dto.UserAccountDTO;
import com.bachapan.web.rest.mapper.UserAccountMapper;
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
 * REST controller for managing UserAccount.
 */
@RestController
@RequestMapping("/api")
public class UserAccountResource {

    private final Logger log = LoggerFactory.getLogger(UserAccountResource.class);
        
    @Inject
    private UserAccountService userAccountService;
    
    @Inject
    private UserAccountMapper userAccountMapper;
    
    /**
     * POST  /userAccounts -> Create a new userAccount.
     */
    @RequestMapping(value = "/userAccounts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserAccountDTO> createUserAccount(@RequestBody UserAccountDTO userAccountDTO) throws URISyntaxException {
        log.debug("REST request to save UserAccount : {}", userAccountDTO);
        if (userAccountDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userAccount", "idexists", "A new userAccount cannot already have an ID")).body(null);
        }
        UserAccountDTO result = userAccountService.save(userAccountDTO);
        return ResponseEntity.created(new URI("/api/userAccounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("userAccount", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /userAccounts -> Updates an existing userAccount.
     */
    @RequestMapping(value = "/userAccounts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserAccountDTO> updateUserAccount(@RequestBody UserAccountDTO userAccountDTO) throws URISyntaxException {
        log.debug("REST request to update UserAccount : {}", userAccountDTO);
        if (userAccountDTO.getId() == null) {
            return createUserAccount(userAccountDTO);
        }
        UserAccountDTO result = userAccountService.save(userAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("userAccount", userAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /userAccounts -> get all the userAccounts.
     */
    @RequestMapping(value = "/userAccounts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<UserAccountDTO>> getAllUserAccounts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UserAccounts");
        Page<UserAccount> page = userAccountService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/userAccounts");
        return new ResponseEntity<>(page.getContent().stream()
            .map(userAccountMapper::userAccountToUserAccountDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /userAccounts/:id -> get the "id" userAccount.
     */
    @RequestMapping(value = "/userAccounts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserAccountDTO> getUserAccount(@PathVariable Long id) {
        log.debug("REST request to get UserAccount : {}", id);
        UserAccountDTO userAccountDTO = userAccountService.findOne(id);
        return Optional.ofNullable(userAccountDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /userAccounts/:id -> delete the "id" userAccount.
     */
    @RequestMapping(value = "/userAccounts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUserAccount(@PathVariable Long id) {
        log.debug("REST request to delete UserAccount : {}", id);
        userAccountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userAccount", id.toString())).build();
    }

    /**
     * SEARCH  /_search/userAccounts/:query -> search for the userAccount corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/userAccounts/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<UserAccountDTO> searchUserAccounts(@PathVariable String query) {
        log.debug("Request to search UserAccounts for query {}", query);
        return userAccountService.search(query);
    }
}
