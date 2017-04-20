package com.arnaugarcia.assessoriatorrelles.repository;

import com.arnaugarcia.assessoriatorrelles.domain.Request;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Request entity.
 */
@SuppressWarnings("unused")
public interface RequestRepository extends JpaRepository<Request,Long> {

}
