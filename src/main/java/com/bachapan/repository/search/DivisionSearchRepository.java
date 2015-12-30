package com.bachapan.repository.search;

import com.bachapan.domain.Division;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Division entity.
 */
public interface DivisionSearchRepository extends ElasticsearchRepository<Division, Long> {
}
