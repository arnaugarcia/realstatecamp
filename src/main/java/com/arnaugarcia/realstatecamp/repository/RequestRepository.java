package com.arnaugarcia.realstatecamp.repository;

import com.arnaugarcia.realstatecamp.domain.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Spring Data JPA repository for the Request entity.
 */
@SuppressWarnings("unused")
public interface RequestRepository extends JpaRepository<Request,Long> {

    @Query("SELECT r FROM Request r WHERE r.state = com.arnaugarcia.realstatecamp.domain.enumeration.Status.open OR r.state = com.arnaugarcia.realstatecamp.domain.enumeration.Status.pending")
    Page<Request> findRequestsByState_OpenAndState_Pending(Pageable pageable);

    @Query("SELECT new com.arnaugarcia.realstatecamp.service.dto.RequestDTO(r.id, r.name, r.phone, r.email, r.state, r.date, r.comment, r.property.id, r.property.ref) FROM Request r WHERE r.state = com.arnaugarcia.realstatecamp.domain.enumeration.Status.open OR r.state = com.arnaugarcia.realstatecamp.domain.enumeration.Status.pending order by r.date desc")
    Page<Request> findRequestsByState_OpenAndState_PendingOrderByDateDateDesc(Pageable pageable);
}
