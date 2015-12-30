package com.bachapan.repository;

import com.bachapan.domain.Contect;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Contect entity.
 */
public interface ContectRepository extends JpaRepository<Contect,Long> {

}
