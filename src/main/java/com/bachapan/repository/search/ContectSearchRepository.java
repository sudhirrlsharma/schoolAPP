package com.bachapan.repository.search;

import com.bachapan.domain.Contect;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Contect entity.
 */
public interface ContectSearchRepository extends ElasticsearchRepository<Contect, Long> {
}
