package com.arnaugarcia.realstatecamp.repository;

import com.arnaugarcia.realstatecamp.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
