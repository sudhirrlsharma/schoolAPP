package com.bachapan.service;

import com.bachapan.domain.Contect;
import com.bachapan.web.rest.dto.ContectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Contect.
 */
public interface ContectService {

    /**
     * Save a contect.
     * @return the persisted entity
     */
    public ContectDTO save(ContectDTO contectDTO);

    /**
     *  get all the contects.
     *  @return the list of entities
     */
    public Page<Contect> findAll(Pageable pageable);

    /**
     *  get the "id" contect.
     *  @return the entity
     */
    public ContectDTO findOne(Long id);

    /**
     *  delete the "id" contect.
     */
    public void delete(Long id);

    /**
     * search for the contect corresponding
     * to the query.
     */
    public List<ContectDTO> search(String query);
}
