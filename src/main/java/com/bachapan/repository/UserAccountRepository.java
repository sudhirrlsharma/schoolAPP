package com.bachapan.repository;

import com.bachapan.domain.UserAccount;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserAccount entity.
 */
public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {

}
