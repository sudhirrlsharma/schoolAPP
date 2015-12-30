package com.bachapan.service;

import com.bachapan.domain.UserAccount;
import com.bachapan.web.rest.dto.UserAccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing UserAccount.
 */
public interface UserAccountService {

    /**
     * Save a userAccount.
     * @return the persisted entity
     */
    public UserAccountDTO save(UserAccountDTO userAccountDTO);

    /**
     *  get all the userAccounts.
     *  @return the list of entities
     */
    public Page<UserAccount> findAll(Pageable pageable);

    /**
     *  get the "id" userAccount.
     *  @return the entity
     */
    public UserAccountDTO findOne(Long id);

    /**
     *  delete the "id" userAccount.
     */
    public void delete(Long id);

    /**
     * search for the userAccount corresponding
     * to the query.
     */
    public List<UserAccountDTO> search(String query);
}
