package com.arnaugarcia.assessoriatorrelles.repository;

import com.arnaugarcia.assessoriatorrelles.domain.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Spring Data JPA repository for the Request entity.
 */
@SuppressWarnings("unused")
public interface RequestRepository extends JpaRepository<Request,Long> {

    @Query("SELECT r FROM Request r WHERE r.state = com.arnaugarcia.assessoriatorrelles.domain.enumeration.Status.open OR r.state = com.arnaugarcia.assessoriatorrelles.domain.enumeration.Status.pending")
    Page<Request> findRequestsByState_OpenAndState_Pending(Pageable pageable);
}
