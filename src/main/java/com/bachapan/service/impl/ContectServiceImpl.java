package com.bachapan.service.impl;

import com.bachapan.service.ContectService;
import com.bachapan.domain.Contect;
import com.bachapan.repository.ContectRepository;
import com.bachapan.repository.search.ContectSearchRepository;
import com.bachapan.web.rest.dto.ContectDTO;
import com.bachapan.web.rest.mapper.ContectMapper;
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
 * Service Implementation for managing Contect.
 */
@Service
@Transactional
public class ContectServiceImpl implements ContectService{

    private final Logger log = LoggerFactory.getLogger(ContectServiceImpl.class);
    
    @Inject
    private ContectRepository contectRepository;
    
    @Inject
    private ContectMapper contectMapper;
    
    @Inject
    private ContectSearchRepository contectSearchRepository;
    
    /**
     * Save a contect.
     * @return the persisted entity
     */
    public ContectDTO save(ContectDTO contectDTO) {
        log.debug("Request to save Contect : {}", contectDTO);
        Contect contect = contectMapper.contectDTOToContect(contectDTO);
        contect = contectRepository.save(contect);
        ContectDTO result = contectMapper.contectToContectDTO(contect);
        contectSearchRepository.save(contect);
        return result;
    }

    /**
     *  get all the contects.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Contect> findAll(Pageable pageable) {
        log.debug("Request to get all Contects");
        Page<Contect> result = contectRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one contect by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ContectDTO findOne(Long id) {
        log.debug("Request to get Contect : {}", id);
        Contect contect = contectRepository.findOne(id);
        ContectDTO contectDTO = contectMapper.contectToContectDTO(contect);
        return contectDTO;
    }

    /**
     *  delete the  contect by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Contect : {}", id);
        contectRepository.delete(id);
        contectSearchRepository.delete(id);
    }

    /**
     * search for the contect corresponding
     * to the query.
     */
    @Transactional(readOnly = true) 
    public List<ContectDTO> search(String query) {
        
        log.debug("REST request to search Contects for query {}", query);
        return StreamSupport
            .stream(contectSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(contectMapper::contectToContectDTO)
            .collect(Collectors.toList());
    }
}
