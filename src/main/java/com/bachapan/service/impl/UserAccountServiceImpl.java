package com.bachapan.service.impl;

import com.bachapan.service.UserAccountService;
import com.bachapan.domain.UserAccount;
import com.bachapan.repository.UserAccountRepository;
import com.bachapan.repository.search.UserAccountSearchRepository;
import com.bachapan.web.rest.dto.UserAccountDTO;
import com.bachapan.web.rest.mapper.UserAccountMapper;
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
 * Service Implementation for managing UserAccount.
 */
@Service
@Transactional
public class UserAccountServiceImpl implements UserAccountService{

    private final Logger log = LoggerFactory.getLogger(UserAccountServiceImpl.class);
    
    @Inject
    private UserAccountRepository userAccountRepository;
    
    @Inject
    private UserAccountMapper userAccountMapper;
    
    @Inject
    private UserAccountSearchRepository userAccountSearchRepository;
    
    /**
     * Save a userAccount.
     * @return the persisted entity
     */
    public UserAccountDTO save(UserAccountDTO userAccountDTO) {
        log.debug("Request to save UserAccount : {}", userAccountDTO);
        UserAccount userAccount = userAccountMapper.userAccountDTOToUserAccount(userAccountDTO);
        userAccount = userAccountRepository.save(userAccount);
        UserAccountDTO result = userAccountMapper.userAccountToUserAccountDTO(userAccount);
        userAccountSearchRepository.save(userAccount);
        return result;
    }

    /**
     *  get all the userAccounts.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<UserAccount> findAll(Pageable pageable) {
        log.debug("Request to get all UserAccounts");
        Page<UserAccount> result = userAccountRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one userAccount by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public UserAccountDTO findOne(Long id) {
        log.debug("Request to get UserAccount : {}", id);
        UserAccount userAccount = userAccountRepository.findOne(id);
        UserAccountDTO userAccountDTO = userAccountMapper.userAccountToUserAccountDTO(userAccount);
        return userAccountDTO;
    }

    /**
     *  delete the  userAccount by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserAccount : {}", id);
        userAccountRepository.delete(id);
        userAccountSearchRepository.delete(id);
    }

    /**
     * search for the userAccount corresponding
     * to the query.
     */
    @Transactional(readOnly = true) 
    public List<UserAccountDTO> search(String query) {
        
        log.debug("REST request to search UserAccounts for query {}", query);
        return StreamSupport
            .stream(userAccountSearchRepository.search(queryString(query)).spliterator(), false)
            .map(userAccountMapper::userAccountToUserAccountDTO)
            .collect(Collectors.toList());
    }
}
