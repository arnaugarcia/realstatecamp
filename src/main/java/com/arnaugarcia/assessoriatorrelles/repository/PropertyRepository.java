package com.arnaugarcia.assessoriatorrelles.repository;

import com.arnaugarcia.assessoriatorrelles.domain.Property;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Property entity.
 */
@SuppressWarnings("unused")
public interface PropertyRepository extends JpaRepository<Property,Long> {

    @Query("select property from Property property where property.user.login = ?#{principal.username}")
    List<Property> findByUserIsCurrentUser();

}
