package com.arnaugarcia.realstatecamp.repository;

import com.arnaugarcia.realstatecamp.domain.Contact;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Contact entity.
 */
@SuppressWarnings("unused")
public interface ContactRepository extends JpaRepository<Contact,Long> {

}
